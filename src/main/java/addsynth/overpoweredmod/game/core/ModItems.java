package addsynth.overpoweredmod.game.core;

import addsynth.overpoweredmod.Debug;
import addsynth.overpoweredmod.game.Names;
import addsynth.overpoweredmod.items.DimensionalAnchor;
import addsynth.overpoweredmod.items.FusionCore;
import addsynth.overpoweredmod.items.OverpoweredItem;
import addsynth.overpoweredmod.items.PlasmaItem;
import net.minecraft.world.item.Item;

public final class ModItems {

  static {
    Debug.log_setup_info("Begin loading Items class...");
  }

  public static final Item energized_power_core      = new OverpoweredItem(Names.ENERGIZED_POWER_CORE);
  public static final Item nullified_power_core      = new OverpoweredItem(Names.NULLIFIED_POWER_CORE);
  public static final Item energy_grid               = new OverpoweredItem(Names.ENERGY_GRID);
  public static final Item vacuum_container          = new OverpoweredItem(Names.VACUUM_CONTAINER);
  
  public static final Item beam_emitter              = new OverpoweredItem(Names.BEAM_EMITTER);
  public static final Item destructive_laser         = new OverpoweredItem(Names.DESTRUCTIVE_LASER);
  public static final Item heavy_light_emitter       = new OverpoweredItem(Names.HEAVY_LIGHT_EMITTER);
  public static final Item energy_stabilizer         = new OverpoweredItem(Names.ENERGY_STABILIZER);
  public static final Item scanning_laser            = new OverpoweredItem(Names.SCANNING_LASER);
  public static final Item matter_energy_transformer = new OverpoweredItem(Names.MATTER_ENERGY_TRANSFORMER);
  public static final Item high_frequency_beam       = new OverpoweredItem(Names.HIGH_FREQUENCY_BEAM);
  
  public static final Item plasma                    = new PlasmaItem();
  public static final Item fusion_core               = new FusionCore();
  public static final Item matter_energy_converter   = new OverpoweredItem(Names.MATTER_ENERGY_CONVERTER);
  public static final Item dimensional_flux          = new OverpoweredItem(Names.DIMENSIONAL_FLUX);
  public static final Item dimensional_anchor        = new DimensionalAnchor();
  
  static {
    Debug.log_setup_info("Finished loading Items class.");
  }

}
