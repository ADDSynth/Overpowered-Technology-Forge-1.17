package addsynth.overpoweredmod.compatability.jei;

import addsynth.overpoweredmod.game.reference.GuiReference;
import addsynth.overpoweredmod.game.reference.Names;
import addsynth.overpoweredmod.game.reference.OverpoweredBlocks;
import addsynth.overpoweredmod.machines.advanced_ore_refinery.OreRefineryRecipe;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IGuiItemStackGroup;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

public final class AdvancedOreRefineryCategory  implements IRecipeCategory<OreRefineryRecipe> {

  public static final ResourceLocation id = Names.ADVANCED_ORE_REFINERY;
  private final IDrawable background;
  private final IDrawable icon;

  public AdvancedOreRefineryCategory(final IGuiHelper gui_helper){
    background = gui_helper.createDrawable(GuiReference.jei_recipe_background, 18, 16, 74, 18);
    icon = gui_helper.createDrawableIngredient(new ItemStack(OverpoweredBlocks.advanced_ore_refinery));
  }

  @Override
  public ResourceLocation getUid(){
    return id;
  }

  @Override
  public Class<? extends OreRefineryRecipe> getRecipeClass(){
    return OreRefineryRecipe.class;
  }

  @Override
  public Component getTitle(){
    return new TranslatableComponent(OverpoweredBlocks.advanced_ore_refinery.getDescriptionId());
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
  public void setIngredients(OreRefineryRecipe recipe, IIngredients ingredients){
    ingredients.setInput(VanillaTypes.ITEM, recipe.itemStack);
    ingredients.setOutput(VanillaTypes.ITEM, recipe.output);
  }

  @Override
  public void setRecipe(IRecipeLayout recipeLayout, OreRefineryRecipe recipe, IIngredients ingredients){
    final IGuiItemStackGroup gui_item_stacks =  recipeLayout.getItemStacks();
    gui_item_stacks.init(0, true,   0, 0);
    gui_item_stacks.init(1, false, 56, 0);
    gui_item_stacks.set(ingredients);
  }

}
