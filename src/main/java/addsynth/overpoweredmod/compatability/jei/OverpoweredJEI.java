package addsynth.overpoweredmod.compatability.jei;

import java.util.ArrayList;
import addsynth.overpoweredmod.OverpoweredTechnology;
import addsynth.overpoweredmod.game.core.Laser;
import addsynth.overpoweredmod.game.reference.OverpoweredBlocks;
import addsynth.overpoweredmod.game.reference.OverpoweredItems;
import addsynth.overpoweredmod.game.reference.TextReference;
import addsynth.overpoweredmod.machines.advanced_ore_refinery.OreRefineryRecipes;
import addsynth.overpoweredmod.machines.gem_converter.GemConverterRecipe;
import addsynth.overpoweredmod.machines.inverter.InverterRecipe;
import addsynth.overpoweredmod.machines.magic_infuser.recipes.MagicInfuserRecipes;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.helpers.IJeiHelpers;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import mezz.jei.api.runtime.IJeiRuntime;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

@JeiPlugin
public final class OverpoweredJEI implements IModPlugin {

// https://github.com/micdoodle8/Galacticraft/blob/MC1.10/src/main/java/micdoodle8/mods/galacticraft/core/client/jei/GalacticraftJEI.java#L90

  @Override
  public void onRuntimeAvailable(IJeiRuntime jeiRuntime){
    // JEI builds the Ingredient list from items in the Creative Inventory.
    // See: mezz.jei.plugins.vanilla.ingredients.item.ItemStackListFactory.create()
    // Since the items we don't want players to access are not in the Creative Inventory,
    // there's no need to remove them from the JEI Ingredient list.
  }

  @Override
  public ResourceLocation getPluginUid(){
    return new ResourceLocation(OverpoweredTechnology.MOD_ID, "jei_plugin");
  }

  @Override
  public void registerCategories(IRecipeCategoryRegistration registration){
    final IJeiHelpers jei_helpers = registration.getJeiHelpers();
    final IGuiHelper gui_helper = jei_helpers.getGuiHelper();
    registration.addRecipeCategories(
      new GemConverterCategory(gui_helper),
      new AdvancedOreRefineryCategory(gui_helper),
      new InverterCategory(gui_helper),
      new MagicInfuserCategory(gui_helper)
    );
  }

  @Override
  public void registerRecipes(IRecipeRegistration registration){
    registration.addRecipes(GemConverterRecipe.getRecipes(), GemConverterCategory.id);
    registration.addRecipes(OreRefineryRecipes.recipes, AdvancedOreRefineryCategory.id);
    registration.addRecipes(InverterRecipe.get_recipes(), InverterCategory.id);
    registration.addRecipes(MagicInfuserRecipes.INSTANCE.getRecipes(), MagicInfuserCategory.id);
    add_information(registration);
  }

  @Override
  public void registerRecipeCatalysts(IRecipeCatalystRegistration registration){
    registration.addRecipeCatalyst(new ItemStack(OverpoweredBlocks.gem_converter), GemConverterCategory.id);
    registration.addRecipeCatalyst(new ItemStack(OverpoweredBlocks.advanced_ore_refinery), AdvancedOreRefineryCategory.id);
    registration.addRecipeCatalyst(new ItemStack(OverpoweredBlocks.inverter), InverterCategory.id);
    registration.addRecipeCatalyst(new ItemStack(OverpoweredBlocks.magic_infuser), MagicInfuserCategory.id);
  }

  private static final void add_information(IRecipeRegistration registry){
    // Celestial Gem, Energy Crystal, Void Crystal
    registry.addIngredientInfo(new ItemStack(OverpoweredItems.celestial_gem),         VanillaTypes.ITEM, TextReference.celestial_gem_description);
    registry.addIngredientInfo(new ItemStack(OverpoweredItems.energy_crystal_shards), VanillaTypes.ITEM, TextReference.energy_crystal_shards_description);
    registry.addIngredientInfo(new ItemStack(OverpoweredItems.energy_crystal),        VanillaTypes.ITEM, TextReference.energy_crystal_description);
    registry.addIngredientInfo(new ItemStack(OverpoweredBlocks.light_block),          VanillaTypes.ITEM, TextReference.light_block_description);
    registry.addIngredientInfo(new ItemStack(OverpoweredItems.void_crystal),          VanillaTypes.ITEM, TextReference.void_crystal_description);
    registry.addIngredientInfo(new ItemStack(OverpoweredBlocks.null_block),           VanillaTypes.ITEM, TextReference.null_block_description);
    
    // Celestial Tools
    final ArrayList<ItemStack> celestial_tools = new ArrayList<>(6);
    celestial_tools.add(new ItemStack(OverpoweredItems.celestial_sword));
    celestial_tools.add(new ItemStack(OverpoweredItems.celestial_shovel));
    celestial_tools.add(new ItemStack(OverpoweredItems.celestial_axe));
    celestial_tools.add(new ItemStack(OverpoweredItems.celestial_pickaxe));
    celestial_tools.add(new ItemStack(OverpoweredItems.celestial_hoe));
    registry.addIngredientInfo(celestial_tools, VanillaTypes.ITEM, TextReference.celestial_tools_description);

    // Void Tools
    final ArrayList<ItemStack> void_tools = new ArrayList<>(5);
    void_tools.add(new ItemStack(OverpoweredItems.void_sword));
    void_tools.add(new ItemStack(OverpoweredItems.void_shovel));
    void_tools.add(new ItemStack(OverpoweredItems.void_axe));
    void_tools.add(new ItemStack(OverpoweredItems.void_pickaxe));
    void_tools.add(new ItemStack(OverpoweredItems.void_hoe));
    registry.addIngredientInfo(void_tools, VanillaTypes.ITEM, TextReference.void_tools_description);

    // Beam Items
    registry.addIngredientInfo(new ItemStack(OverpoweredItems.scanning_laser),            VanillaTypes.ITEM, TextReference.scanning_laser_description);
    registry.addIngredientInfo(new ItemStack(OverpoweredItems.destructive_laser),         VanillaTypes.ITEM, TextReference.destructive_laser_description);
    registry.addIngredientInfo(new ItemStack(OverpoweredItems.energy_stabilizer),         VanillaTypes.ITEM, TextReference.energy_stabilizer_description);
    registry.addIngredientInfo(new ItemStack(OverpoweredItems.heavy_light_emitter),       VanillaTypes.ITEM, TextReference.heavy_light_emitter_description);
    registry.addIngredientInfo(new ItemStack(OverpoweredItems.matter_energy_transformer), VanillaTypes.ITEM, TextReference.matter_energy_transformer_description);

    // Items
    registry.addIngredientInfo(new ItemStack(OverpoweredItems.plasma),                  VanillaTypes.ITEM, TextReference.plasma_description);
    registry.addIngredientInfo(new ItemStack(OverpoweredItems.matter_energy_converter), VanillaTypes.ITEM, TextReference.matter_energy_converter_description);
    registry.addIngredientInfo(new ItemStack(OverpoweredItems.vacuum_container),        VanillaTypes.ITEM, TextReference.vacuum_container_description);
    registry.addIngredientInfo(new ItemStack(OverpoweredItems.reinforced_container),    VanillaTypes.ITEM, TextReference.reinforced_container_description);
    registry.addIngredientInfo(new ItemStack(OverpoweredItems.dimensional_flux),        VanillaTypes.ITEM, TextReference.dimensional_flux_description);
    registry.addIngredientInfo(new ItemStack(OverpoweredItems.unimatter),               VanillaTypes.ITEM, TextReference.unimatter_description);
    registry.addIngredientInfo(new ItemStack(OverpoweredItems.dimensional_anchor),      VanillaTypes.ITEM, TextReference.dimensional_anchor_description);

    // Machines 1
    registry.addIngredientInfo(new ItemStack(OverpoweredBlocks.crystal_energy_extractor), VanillaTypes.ITEM, TextReference.crystal_energy_extractor_description);
    registry.addIngredientInfo(new ItemStack(OverpoweredBlocks.data_cable),               VanillaTypes.ITEM, TextReference.data_cable_description);
    registry.addIngredientInfo(new ItemStack(OverpoweredBlocks.gem_converter),            VanillaTypes.ITEM, TextReference.gem_converter_description);
    registry.addIngredientInfo(new ItemStack(OverpoweredBlocks.inverter),                 VanillaTypes.ITEM, TextReference.inverter_description);
    registry.addIngredientInfo(new ItemStack(OverpoweredBlocks.magic_infuser),            VanillaTypes.ITEM, TextReference.magic_infuser_description);
    registry.addIngredientInfo(new ItemStack(OverpoweredBlocks.identifier),               VanillaTypes.ITEM, TextReference.identifier_description);
    registry.addIngredientInfo(new ItemStack(OverpoweredBlocks.portal_control_panel),     VanillaTypes.ITEM, TextReference.portal_control_panel_description);
    registry.addIngredientInfo(new ItemStack(OverpoweredBlocks.portal_frame),             VanillaTypes.ITEM, TextReference.portal_frame_description);
    
    // Lasers
    registry.addIngredientInfo(new ItemStack(OverpoweredBlocks.laser_housing), VanillaTypes.ITEM, TextReference.laser_housing_description);
    final ArrayList<ItemStack> lasers = new ArrayList<>(8);
    lasers.add(new ItemStack(Laser.RED.cannon));
    lasers.add(new ItemStack(Laser.ORANGE.cannon));
    lasers.add(new ItemStack(Laser.YELLOW.cannon));
    lasers.add(new ItemStack(Laser.GREEN.cannon));
    lasers.add(new ItemStack(Laser.CYAN.cannon));
    lasers.add(new ItemStack(Laser.BLUE.cannon));
    lasers.add(new ItemStack(Laser.MAGENTA.cannon));
    lasers.add(new ItemStack(Laser.WHITE.cannon));
    registry.addIngredientInfo(lasers, VanillaTypes.ITEM, new TranslatableComponent("gui.overpowered.jei_description.laser"));
    
    // Machines 2
    registry.addIngredientInfo(new ItemStack(OverpoweredBlocks.energy_suspension_bridge), VanillaTypes.ITEM, TextReference.energy_suspension_bridge_description);
    registry.addIngredientInfo(new ItemStack(OverpoweredBlocks.advanced_ore_refinery),    VanillaTypes.ITEM, TextReference.advanced_ore_refinery_description);
    registry.addIngredientInfo(new ItemStack(OverpoweredBlocks.plasma_generator),         VanillaTypes.ITEM, TextReference.plasma_generator_description);
    registry.addIngredientInfo(new ItemStack(OverpoweredBlocks.matter_compressor),        VanillaTypes.ITEM, TextReference.matter_compressor_description);
    registry.addIngredientInfo(new ItemStack(OverpoweredBlocks.crystal_matter_generator), VanillaTypes.ITEM, TextReference.crystal_matter_generator_description);
    registry.addIngredientInfo(new ItemStack(OverpoweredBlocks.black_hole),               VanillaTypes.ITEM, TextReference.black_hole_description);
    
    // Fusion Machines
    registry.addIngredientInfo(new ItemStack(OverpoweredBlocks.fusion_chamber),       VanillaTypes.ITEM, TextReference.fusion_chamber_description);
    registry.addIngredientInfo(new ItemStack(OverpoweredBlocks.fusion_control_unit),  VanillaTypes.ITEM, TextReference.fusion_control_unit_description);
    registry.addIngredientInfo(new ItemStack(OverpoweredBlocks.fusion_control_laser), VanillaTypes.ITEM, TextReference.fusion_control_laser_description);
    registry.addIngredientInfo(new ItemStack(OverpoweredBlocks.fusion_converter),     VanillaTypes.ITEM, TextReference.fusion_converter_description);
  }

}
