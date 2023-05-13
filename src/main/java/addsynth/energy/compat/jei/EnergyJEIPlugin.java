package addsynth.energy.compat.jei;

import addsynth.energy.ADDSynthEnergy;
import addsynth.energy.gameplay.EnergyBlocks;
import addsynth.energy.gameplay.EnergyItems;
import addsynth.energy.gameplay.machines.circuit_fabricator.recipe.CircuitFabricatorRecipes;
import addsynth.energy.gameplay.machines.compressor.recipe.CompressorRecipes;
import addsynth.energy.gameplay.reference.TextReference;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.VanillaRecipeCategoryUid;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.helpers.IJeiHelpers;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
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
    registry.addIngredientInfo(new ItemStack(EnergyBlocks.wire),               VanillaTypes.ITEM, TextReference.wire_description);
    registry.addIngredientInfo(new ItemStack(EnergyBlocks.generator),          VanillaTypes.ITEM, TextReference.generator_description);
    registry.addIngredientInfo(new ItemStack(EnergyBlocks.energy_storage),     VanillaTypes.ITEM, TextReference.energy_storage_description);
    registry.addIngredientInfo(new ItemStack(EnergyBlocks.electric_furnace),   VanillaTypes.ITEM, TextReference.electric_furnace_description);
    registry.addIngredientInfo(new ItemStack(EnergyBlocks.compressor),         VanillaTypes.ITEM, TextReference.compressor_description);
    registry.addIngredientInfo(new ItemStack(EnergyBlocks.circuit_fabricator), VanillaTypes.ITEM, TextReference.circuit_fabricator_description);
    registry.addIngredientInfo(new ItemStack(EnergyBlocks.universal_energy_machine), VanillaTypes.ITEM, TextReference.energy_interface_description);
    registry.addIngredientInfo(new ItemStack(EnergyBlocks.energy_diagnostics_block), VanillaTypes.ITEM, TextReference.energy_diagnostics_description);
    
    registry.addIngredientInfo(new ItemStack(EnergyItems.power_regulator), VanillaTypes.ITEM, TextReference.power_regulator_description);
  }

  @Override
  public void registerRecipeCatalysts(IRecipeCatalystRegistration registration){
    registration.addRecipeCatalyst(new ItemStack(EnergyBlocks.compressor),         CompressorRecipeCategory.id);
    registration.addRecipeCatalyst(new ItemStack(EnergyBlocks.electric_furnace),   VanillaRecipeCategoryUid.FURNACE);
    registration.addRecipeCatalyst(new ItemStack(EnergyBlocks.circuit_fabricator), CircuitRecipeCategory.id);
  }

}
