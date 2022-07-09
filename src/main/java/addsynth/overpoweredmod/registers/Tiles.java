package addsynth.overpoweredmod.registers;

import addsynth.overpoweredmod.Debug;
import addsynth.overpoweredmod.game.core.Init;
import addsynth.overpoweredmod.game.core.Laser;
import addsynth.overpoweredmod.game.core.Machines;
import addsynth.overpoweredmod.game.core.Portal;
import addsynth.overpoweredmod.game.core.Wires;
import addsynth.overpoweredmod.machines.advanced_ore_refinery.TileAdvancedOreRefinery;
import addsynth.overpoweredmod.machines.black_hole.TileBlackHole;
import addsynth.overpoweredmod.machines.crystal_matter_generator.TileCrystalMatterGenerator;
import addsynth.overpoweredmod.machines.data_cable.TileDataCable;
import addsynth.overpoweredmod.machines.energy_extractor.TileCrystalEnergyExtractor;
import addsynth.overpoweredmod.machines.fusion.chamber.TileFusionChamber;
import addsynth.overpoweredmod.machines.fusion.converter.TileFusionEnergyConverter;
import addsynth.overpoweredmod.machines.gem_converter.TileGemConverter;
import addsynth.overpoweredmod.machines.identifier.TileIdentifier;
import addsynth.overpoweredmod.machines.inverter.TileInverter;
import addsynth.overpoweredmod.machines.laser.beam.TileLaserBeam;
import addsynth.overpoweredmod.machines.laser.cannon.TileLaser;
import addsynth.overpoweredmod.machines.laser.machine.TileLaserHousing;
import addsynth.overpoweredmod.machines.magic_infuser.TileMagicInfuser;
import addsynth.overpoweredmod.machines.plasma_generator.TilePlasmaGenerator;
import addsynth.overpoweredmod.machines.portal.control_panel.TilePortalControlPanel;
import addsynth.overpoweredmod.machines.portal.frame.TilePortalFrame;
import addsynth.overpoweredmod.machines.portal.rift.TilePortal;
import addsynth.overpoweredmod.machines.suspension_bridge.TileSuspensionBridge;
import net.minecraft.world.level.block.entity.BlockEntityType;

public final class Tiles {

  static {
    Debug.log_setup_info(Tiles.class.getName()+" class was loaded...");
  }

  public static final BlockEntityType<TileCrystalEnergyExtractor> CRYSTAL_ENERGY_EXTRACTOR =
    BlockEntityType.Builder.of(TileCrystalEnergyExtractor::new, Machines.crystal_energy_extractor).build(null);

  public static final BlockEntityType<TileGemConverter> GEM_CONVERTER =
    BlockEntityType.Builder.of(TileGemConverter::new, Machines.gem_converter).build(null);

  public static final BlockEntityType<TileInverter> INVERTER =
    BlockEntityType.Builder.of(TileInverter::new, Machines.inverter).build(null);

  public static final BlockEntityType<TileMagicInfuser> MAGIC_INFUSER =
    BlockEntityType.Builder.of(TileMagicInfuser::new, Machines.magic_infuser).build(null);

  public static final BlockEntityType<TileIdentifier> IDENTIFIER =
    BlockEntityType.Builder.of(TileIdentifier::new, Machines.identifier).build(null);

  public static final BlockEntityType<TileAdvancedOreRefinery> ADVANCED_ORE_REFINERY =
    BlockEntityType.Builder.of(TileAdvancedOreRefinery::new, Machines.advanced_ore_refinery).build(null);

  public static final BlockEntityType<TileCrystalMatterGenerator> CRYSTAL_MATTER_REPLICATOR =
    BlockEntityType.Builder.of(TileCrystalMatterGenerator::new, Machines.crystal_matter_generator).build(null);

  public static final BlockEntityType<TileSuspensionBridge> ENERGY_SUSPENSION_BRIDGE =
    BlockEntityType.Builder.of(TileSuspensionBridge::new, Machines.energy_suspension_bridge).build(null);

  public static final BlockEntityType<TileDataCable> DATA_CABLE =
    BlockEntityType.Builder.of(TileDataCable::new, Wires.data_cable).build(null);

  public static final BlockEntityType<TilePortalControlPanel> PORTAL_CONTROL_PANEL =
    BlockEntityType.Builder.of(TilePortalControlPanel::new, Machines.portal_control_panel).build(null);

  public static final BlockEntityType<TilePortalFrame> PORTAL_FRAME =
    BlockEntityType.Builder.of(TilePortalFrame::new, Machines.portal_frame).build(null);

  public static final BlockEntityType<TilePortal> PORTAL_BLOCK =
    BlockEntityType.Builder.of(TilePortal::new, Portal.portal).build(null);

  public static final BlockEntityType<TileLaserHousing> LASER_MACHINE =
    BlockEntityType.Builder.of(TileLaserHousing::new, Machines.laser_housing).build(null);

  public static final BlockEntityType<TileLaser> LASER =
    BlockEntityType.Builder.of(TileLaser::new, Laser.cannons).build(null);

  public static final BlockEntityType<TileLaserBeam> LASER_BEAM =
    BlockEntityType.Builder.of(TileLaserBeam::new, Laser.beams).build(null);

  public static final BlockEntityType<TileFusionEnergyConverter> FUSION_ENERGY_CONVERTER =
    BlockEntityType.Builder.of(TileFusionEnergyConverter::new, Machines.fusion_converter).build(null);

  public static final BlockEntityType<TileFusionChamber> FUSION_CHAMBER =
    BlockEntityType.Builder.of(TileFusionChamber::new, Machines.fusion_chamber).build(null);

  public static final BlockEntityType<TileBlackHole> BLACK_HOLE =
    BlockEntityType.Builder.of(TileBlackHole::new, Init.black_hole).build(null);

  public static final BlockEntityType<TilePlasmaGenerator> PLASMA_GENERATOR =
    BlockEntityType.Builder.of(TilePlasmaGenerator::new, Machines.plasma_generator).build(null);

  static {
    Debug.log_setup_info(Tiles.class.getName()+" class finished loading.");
  }

}
