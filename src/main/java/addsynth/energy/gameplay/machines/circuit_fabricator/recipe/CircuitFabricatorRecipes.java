package addsynth.energy.gameplay.machines.circuit_fabricator.recipe;

import addsynth.core.recipe.shapeless.RecipeCollection;
import addsynth.core.recipe.shapeless.ShapelessRecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;

public final class CircuitFabricatorRecipes {

  public static final class CircuitFabricatorRecipeType implements RecipeType<CircuitFabricatorRecipe> {
  }
  
  public static final RecipeCollection<CircuitFabricatorRecipe> INSTANCE = new RecipeCollection<CircuitFabricatorRecipe>(
    new CircuitFabricatorRecipeType(), new ShapelessRecipeSerializer<CircuitFabricatorRecipe>(CircuitFabricatorRecipe.class, 8)
  );

}
