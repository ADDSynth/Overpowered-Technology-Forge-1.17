package addsynth.energy.compat.jei;

import addsynth.energy.ADDSynthEnergy;
import addsynth.energy.gameplay.EnergyBlocks;
import addsynth.energy.gameplay.machines.circuit_fabricator.recipe.CircuitFabricatorRecipes;
import addsynth.energy.gameplay.machines.compressor.recipe.CompressorRecipes;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.VanillaRecipeCategoryUid;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.helpers.IJeiHelpers;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

@JeiPlugin
public final class EnergyJEIPlugin implements IModPlugin {

  public static final ResourceLocation id = new ResourceLocation(ADDSynthEnergy.MOD_ID, "jei_plugin");

  @Override
  public ResourceLocation getPluginUid(){
    return id;
  }

  @Override
  public void registerCategories(IRecipeCategoryRegistration registration){
    final IJeiHelpers jei_helpers = registration.getJeiHelpers();
    final IGuiHelper gui_helper = jei_helpers.getGuiHelper();
    registration.addRecipeCategories(
      new CompressorRecipeCategory(gui_helper),
      new CircuitRecipeCategory(gui_helper)
    );
  }

  @Override
  public void registerRecipes(IRecipeRegistration registration){
    registration.addRecipes(CompressorRecipes.INSTANCE.recipes, CompressorRecipeCategory.id);
    registration.addRecipes(CircuitFabricatorRecipes.INSTANCE.recipes, CircuitRecipeCategory.id);
    add_information(registration);
  }

  private static void add_information(IRecipeRegistration registry){
    registry.addIngredientInfo(new ItemStack(EnergyBlocks.wire),               VanillaTypes.ITEM, new TranslatableComponent("gui.addsynth_energy.jei_description.wire"));
    registry.addIngredientInfo(new ItemStack(EnergyBlocks.generator),          VanillaTypes.ITEM, new TranslatableComponent("gui.addsynth_energy.jei_description.generator"));
    registry.addIngredientInfo(new ItemStack(EnergyBlocks.energy_storage),     VanillaTypes.ITEM, new TranslatableComponent("gui.addsynth_energy.jei_description.energy_storage"));
    registry.addIngredientInfo(new ItemStack(EnergyBlocks.electric_furnace),   VanillaTypes.ITEM, new TranslatableComponent("gui.addsynth_energy.jei_description.electric_furnace"));
    registry.addIngredientInfo(new ItemStack(EnergyBlocks.compressor),         VanillaTypes.ITEM, new TranslatableComponent("gui.addsynth_energy.jei_description.compressor"));
    registry.addIngredientInfo(new ItemStack(EnergyBlocks.circuit_fabricator), VanillaTypes.ITEM, new TranslatableComponent("gui.addsynth_energy.jei_description.circuit_fabricator"));
    registry.addIngredientInfo(new ItemStack(EnergyBlocks.universal_energy_machine), VanillaTypes.ITEM, new TranslatableComponent("gui.addsynth_energy.jei_description.universal_energy_interface"));
  }

  @Override
  public void registerRecipeCatalysts(IRecipeCatalystRegistration registration){
    registration.addRecipeCatalyst(new ItemStack(EnergyBlocks.compressor),         CompressorRecipeCategory.id);
    registration.addRecipeCatalyst(new ItemStack(EnergyBlocks.electric_furnace),   VanillaRecipeCategoryUid.FURNACE);
    registration.addRecipeCatalyst(new ItemStack(EnergyBlocks.circuit_fabricator), CircuitRecipeCategory.id);
  }

}
