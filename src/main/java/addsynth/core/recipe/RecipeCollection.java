package addsynth.core.recipe;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import addsynth.core.ADDSynthCore;
import addsynth.core.game.inventory.filter.MachineFilter;
import addsynth.core.util.java.StringUtil;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.StackedContents;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraftforge.client.event.RecipesUpdatedEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TagsUpdatedEvent;
import net.minecraftforge.fmllegacy.server.ServerLifecycleHooks;
import net.minecraftforge.fmlserverevents.FMLServerAboutToStartEvent;
import net.minecraftforge.registries.ForgeRegistries;

/** The RecipeCollection keeps a list of recipes of the {@link RecipeType}
 *  that you passed into the constructor. It also maintains an item
 *  filter that only allows an item in that slot if there's a recipe that
 *  needs an item in that slot. The recipe cache and item filter are
 *  automatically updated whenever resources are reloaded.
 *  @see FurnaceRecipes
 *  @see addsynth.energy.gameplay.machines.compressor.recipe.CompressorRecipes
 *  @see addsynth.energy.gameplay.machines.circuit_fabricator.recipe.CircuitFabricatorRecipes
 *  @see addsynth.overpoweredmod.machines.magic_infuser.recipes.MagicInfuserRecipes
 */
public class RecipeCollection<T extends Recipe<Container>> {

  public final RecipeType<T> type;
  public final RecipeSerializer<T> serializer;
  private final ArrayList<T> recipes = new ArrayList<T>();
  private final MachineFilter filter;

  public RecipeCollection(RecipeType<T> type, RecipeSerializer<T> serializer, int recipe_max_size){
    this.type = type;
    this.serializer = serializer;
    filter = new MachineFilter(recipe_max_size);
  }

  /** <p>RecipeCollections should be registered in your Mod's class constructor or main setup
   *  function. This will ensure they get rebuilt on server and client whenever resources
   *  are reloaded.
   *  <p>I've discovered that the server isn't available when the {@link TagsUpdatedEvent} is
   *  first sent on the server side, so we try again in the {@link FMLServerAboutToStartEvent}.
   *  This is literally the first moment the server is available, before a world loads.
   *  This fixes the problem of not having RecipeCollections built before you need them in
   *  either the TileEntity constructor or loading TileEntity data.
   */
  public final void register(){
    MinecraftForge.EVENT_BUS.addListener((TagsUpdatedEvent event) -> {
      // Usually fires after all other resources have been loaded, on the server side.
      @SuppressWarnings("resource")
      final MinecraftServer server = ServerLifecycleHooks.getCurrentServer();
      if(server != null){
        rebuild(server.getRecipeManager());
      }
    });
    MinecraftForge.EVENT_BUS.addListener((RecipesUpdatedEvent event) -> {
      // Fires on the client side when Recipe data is sent from the server.
      rebuild(event.getRecipeManager());
    });
    MinecraftForge.EVENT_BUS.addListener((FMLServerAboutToStartEvent event) -> {
      // First time the TagsUpdatedEvent fires is before the server loads, so we get the
      // server as soon as we can and rebuild the recipe cache, before the world loads.
      if(recipes.isEmpty()){
        @SuppressWarnings("resource")
        final MinecraftServer server = event.getServer();
        rebuild(server.getRecipeManager());
      }
    });
  }

  /** This rebuilds the recipe cache and ingredient filter. */
  public final void rebuild(final RecipeManager recipe_manager){
    if(recipe_manager != null){
      // rebuild recipe cache
      recipes.clear();
      final List<T> final_recipes = alter_recipes(recipe_manager.getAllRecipesFor(type));
      recipes.addAll(final_recipes);
      if(recipes.isEmpty()){
        filter.clear();
        ADDSynthCore.log.error("No recipes of type "+getRecipeTypeName()+" exist!");
        return;
      }
      filter.set(recipes);
      ADDSynthCore.log.info(this.toString()+" was rebuilt.");
    }
    else{
      ADDSynthCore.log.error(this.toString()+" failed to build recipes because the supplied RecipeManager was null.");
    }
  }

  /** Override this to customize recipes. This will be
   *  called everytime we need to rebuild the recipe cache.
   * @param recipes
   * @return
   */
  protected List<T> alter_recipes(final List<T> recipes){
    return recipes;
  }

  /** Gets the filter for the first slot. Useful for recipes that only have 1 ingredient. */
  public final Predicate<ItemStack> getFilter(){
    return filter.get(0);
  }

  public final Predicate<ItemStack> getFilter(final int slot){
    return filter.get(slot);
  }

  /** This is mainly used for adding the recipes to JEI. */
  public final ArrayList<T> getRecipes(){
    return new ArrayList<T>(recipes);
  }

  /** Returns whether the input items matches a recipe in this collection. */
  public final boolean match(final ItemStack[] input, final Level world){
    final SimpleContainer inventory = new SimpleContainer(input);
    for(final T recipe : recipes){
      if(recipe.matches(inventory, world)){
        return true;
      }
    }
    return false;
  }

  /** Returns the recipe output, or ItemStack.EMPTY if there is no matching recipe. */
  @Nonnull
  public final ItemStack getResult(final ItemStack input){
    return getResult(new ItemStack[] {input});
  }

  /** Returns the recipe output, or ItemStack.EMPTY if there is no matching recipe. */
  @Nonnull
  public final ItemStack getResult(final ItemStack[] input){
    final StackedContents recipe_item_helper = new StackedContents();
    int count;
    
    for(final T recipe : recipes){
    
      recipe_item_helper.clear();
      count = 0;
      
      for(final ItemStack stack : input){
        if(stack.isEmpty() == false){
          count += 1;
          recipe_item_helper.accountStack(stack);
        }
      }
      if(count == recipe.getIngredients().size() && recipe_item_helper.canCraft(recipe, null)){
        return recipe.getResultItem().copy();
      }
    }
    return ItemStack.EMPTY;
  }
  
  /** Finds the recipe with an output that matches the passed in ItemStack.
   *  Warning: Finds the FIRST recipe with a matching output. There may be
   *  more than one recipe that makes that item. Returns null if no recipe was found.
   */
  @Nullable
  public final T find_recipe(final ItemStack output){
    T recipe = null;
    for(T r : recipes){
      if(r.getResultItem().sameItem(output)){
        recipe = r;
        break;
      }
    }
    return recipe;
  }

  @Nullable
  public final T find_recipe(final ResourceLocation registry_key){
    final Item output = ForgeRegistries.ITEMS.getValue(registry_key);
    if(output == null){
      return null;
    }
    T recipe = null;
    for(T r : recipes){
      if(r.getResultItem().getItem() == output){
        recipe = r;
        break;
      }
    }
    return recipe;
  }

  public final String getRecipeTypeName(){
    if(type == null){
      return "null";
    }
    final String type_name = type.getClass().getSimpleName();
    return type_name.isEmpty() ? StringUtil.Capitalize(type.toString()) : type_name;
  }

  @Override
  public String toString(){
    return StringUtil.build(getRecipeTypeName(), ' ', RecipeCollection.class.getSimpleName());
  }

}
