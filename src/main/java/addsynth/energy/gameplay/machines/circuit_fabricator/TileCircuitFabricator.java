package addsynth.energy.gameplay.machines.circuit_fabricator;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import addsynth.core.container.slots.InputSlot;
import addsynth.core.game.items.ItemUtil;
import addsynth.energy.ADDSynthEnergy;
import addsynth.energy.gameplay.Config;
import addsynth.energy.gameplay.EnergyItems;
import addsynth.energy.gameplay.machines.circuit_fabricator.recipe.CircuitFabricatorRecipe;
import addsynth.energy.gameplay.machines.circuit_fabricator.recipe.CircuitFabricatorRecipes;
import addsynth.energy.lib.tiles.machines.TileStandardWorkMachine;
import addsynth.energy.registers.Tiles;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.registries.ForgeRegistries;

public final class TileCircuitFabricator extends TileStandardWorkMachine implements MenuProvider {

  @Nonnull // It's better to keep this as a string, so we can save and load it no problem.
  private String output_itemStack = "";
  private ItemStack[][] filter = new ItemStack[8][];

  private final InputSlot[] input_slot = {
    new InputSlot(this, 0, 162, 76),
    new InputSlot(this, 1, 180, 76),
    new InputSlot(this, 2, 198, 76),
    new InputSlot(this, 3, 216, 76),
    new InputSlot(this, 4, 162, 94),
    new InputSlot(this, 5, 180, 94),
    new InputSlot(this, 6, 198, 94),
    new InputSlot(this, 7, 216, 94)
  };

  private static final String legacyNBTSaveTag = "Circuit to Craft";
  private static final String saveTag = "Recipe";
  @SuppressWarnings("null")
  private static final String defaultRecipe = EnergyItems.circuit_tier_1.getRegistryName().toString();

  public TileCircuitFabricator(BlockPos position, BlockState blockstate){
    super(Tiles.CIRCUIT_FABRICATOR, position, blockstate, 8, null, 1, Config.circuit_fabricator_data);
    inventory.setRecipeProvider(CircuitFabricatorRecipes.INSTANCE);
  }

  public final void change_recipe(final String new_recipe){
    if(output_itemStack.equals(new_recipe) == false){
     output_itemStack = new_recipe;
     rebuild_filters();
     changed = true;
    }
  }

  // TODO: Ideally, this should be rebuilt every recipe and tag reload, but this is the best I can do for now.
  public final void rebuild_filters(){
    // find recipe
    final ResourceLocation recipe_id = new ResourceLocation(output_itemStack);
    final CircuitFabricatorRecipe recipe = CircuitFabricatorRecipes.INSTANCE.find_recipe(recipe_id);
    // get ingredients, create filters
    filter = recipe.getItemStackIngredients();
    int i;
    for(i = 0; i < 8; i++){
      // apply filters
      if(i < filter.length){
        input_slot[i].setFilter(filter[i]);
        inventory.getInputInventory().setFilter(i, ItemUtil.toItemArray(filter[i]));
      }
      else{
        input_slot[i].setFilter(new Item[0]);
        inventory.getInputInventory().setFilter(i, new Item[0]);
      }
    }
    
    // update recipe in gui if on client side
    updateGui();
  }

  /** Go through all inventory slots and eject all items that don't match.
   *  This can only be called when the player clicks on a change recipe button on the gui,
   *  which then sends a network message to the server.
   */
  public final void ejectInvalidItems(final Player player){
    inventory.getInputInventory().ejectInvalidItems(player);
  }

  @SuppressWarnings("null")
  public final void updateGui(){
    if(level != null){
      if(level.isClientSide){
        CircuitFabricatorGui.updateRecipeDisplay(filter);
      }
    }
  }

  public final ItemStack getRecipeOutput(){
    return new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation(output_itemStack)));
  }

  @Override
  public final CompoundTag save(final CompoundTag nbt){
    super.save(nbt);
    nbt.putString(saveTag, output_itemStack);
    return nbt;
  }

  @Override
  @SuppressWarnings("null")
  public final void load(final CompoundTag nbt){
    super.load(nbt);
    
    // handle old saves
    if(nbt.contains(legacyNBTSaveTag)){
      final int circuit = nbt.getInt(legacyNBTSaveTag);
      change_recipe(EnergyItems.circuit[circuit].getRegistryName().toString());
      return;
    }
    
    // handle new saves
    final String recipe = nbt.getString(saveTag);
    // handle if tag doesn't exist
    if(recipe.equals("")){
      change_recipe(defaultRecipe);
      return;
    }
    // handle if recipe doesn't exist
    if(CircuitFabricatorRecipes.INSTANCE.find_recipe(new ResourceLocation(recipe)) == null){
      ADDSynthEnergy.log.warn("Circuit Fabricator recipe for "+recipe+" doesn't exist anymore.");
      // PRIORITY: add a resetMachine() function. Add a call here. Check how we currently handle unexpected machine state errors.
      change_recipe(defaultRecipe);
      return;
    }
    // load normally
    change_recipe(recipe);
  }

  public final InputSlot[] getInputSlots(){
    return input_slot;
  }

  @Override
  @Nullable
  public AbstractContainerMenu createMenu(int id, Inventory player_inventory, Player player){
    return new CircuitFabricatorContainer(id, player_inventory, this);
  }

  @Override
  public Component getDisplayName(){
    return new TranslatableComponent(getBlockState().getBlock().getDescriptionId());
  }

}
