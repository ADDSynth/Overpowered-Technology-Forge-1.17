package addsynth.overpoweredmod.machines.magic_infuser.recipes;

import java.util.ArrayList;
import java.util.Random;
import addsynth.core.Debug;
import addsynth.core.util.StringUtil;
import addsynth.material.MaterialsUtil;
import addsynth.overpoweredmod.OverpoweredTechnology;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeType;

public final class MagicInfuserRecipes {

  public static final class MagicInfuserRecipeType implements RecipeType<MagicInfuserRecipe> {
  }

  public static final MagicInfuserRecipeType recipe_type = new MagicInfuserRecipeType();
  public static final MagicInfuserRecipeSerializer serializer = new MagicInfuserRecipeSerializer();

  @Deprecated
  public static final ArrayList<MagicInfuserRecipe> recipes = new ArrayList<>();

  @Deprecated
  public static final void addRecipe(final MagicInfuserRecipe recipe){
    if(recipes.contains(recipe) == false){
      recipes.add(recipe);
      Debug.log_recipe(recipe);
    }
  }

  public static final Item[] getFilter(){
    return MaterialsUtil.getFilter(
      MaterialsUtil.getRubies(), MaterialsUtil.getTopaz(), MaterialsUtil.getCitrine(), MaterialsUtil.getEmeralds(),
      MaterialsUtil.getDiamonds(), MaterialsUtil.getSapphires(), MaterialsUtil.getAmethysts(), MaterialsUtil.getQuartz()
    );
  }

  public static final ItemStack getResult(final ItemStack input){
    final ArrayList<ItemStack> output = new ArrayList<>(recipes.size());
    NonNullList<Ingredient> ingredients;
    for(MagicInfuserRecipe recipe : recipes){
      ingredients = recipe.getIngredients();
      if(ingredients.get(1).test(input)){
        output.add(recipe.getResultItem());
      }
    }
    if(output.size() == 0){
      OverpoweredTechnology.log.error("Invalid Magic Infuser recipe for input: "+StringUtil.getName(input.getItem())+"!");
      return ItemStack.EMPTY;
    }
    final Random random = new Random();
    return output.get(random.nextInt(output.size()));
  }

}
