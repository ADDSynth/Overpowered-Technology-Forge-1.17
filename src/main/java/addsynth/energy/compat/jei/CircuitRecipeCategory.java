package addsynth.energy.compat.jei;

import addsynth.energy.ADDSynthEnergy;
import addsynth.energy.gameplay.EnergyBlocks;
import addsynth.energy.gameplay.machines.circuit_fabricator.recipe.CircuitFabricatorRecipe;
import addsynth.energy.registers.Names;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.drawable.IDrawableBuilder;
import mezz.jei.api.gui.ingredient.IGuiItemStackGroup;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.category.IRecipeCategory;

public final class CircuitRecipeCategory implements IRecipeCategory<CircuitFabricatorRecipe> {

  public static final ResourceLocation id = Names.CIRCUIT_FABRICATOR;
  private static final ResourceLocation gui_texture = new ResourceLocation(ADDSynthEnergy.MOD_ID, "textures/gui/circuit_fabricator.png");
  private final IDrawable background;
  private final IDrawable icon;

  public CircuitRecipeCategory(final IGuiHelper gui_helper){
    final IDrawableBuilder drawable_builder = gui_helper.drawableBuilder(gui_texture, 153, 67, 140, 54);
    drawable_builder.setTextureSize(384, 256);
    background = drawable_builder.build();
    icon = gui_helper.createDrawableIngredient(new ItemStack(EnergyBlocks.circuit_fabricator));
  }

  @Override
  public ResourceLocation getUid(){
    return id;
  }

  @Override
  public Class<? extends CircuitFabricatorRecipe> getRecipeClass(){
    return CircuitFabricatorRecipe.class;
  }

  @Override
  public Component getTitle(){
    return new TranslatableComponent(EnergyBlocks.circuit_fabricator.getDescriptionId());
  }

  @Override
  public IDrawable getBackground(){
    return background;
  }

  @Override
  public IDrawable getIcon(){
    return icon;
  }

  @Override
  public void setIngredients(CircuitFabricatorRecipe recipe, IIngredients ingredients){
    ingredients.setInputIngredients(recipe.getIngredients());
    ingredients.setOutput(VanillaTypes.ITEM, recipe.getResultItem());
  }

  @Override
  public void setRecipe(IRecipeLayout recipeLayout, CircuitFabricatorRecipe recipe, IIngredients ingredients){
    final IGuiItemStackGroup itemStackgroup = recipeLayout.getItemStacks();
    itemStackgroup.init(0, true,  8,  8);
    itemStackgroup.init(1, true, 26,  8);
    itemStackgroup.init(2, true, 44,  8);
    itemStackgroup.init(3, true, 62,  8);
    itemStackgroup.init(4, true,  8, 26);
    itemStackgroup.init(5, true, 26, 26);
    itemStackgroup.init(6, true, 44, 26);
    itemStackgroup.init(7, true, 62, 26);
    itemStackgroup.init(8, false, 114, 17);
    itemStackgroup.set(ingredients);
  }

}
