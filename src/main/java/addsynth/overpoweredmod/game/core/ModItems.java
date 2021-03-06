package addsynth.overpoweredmod.game.core;

import addsynth.overpoweredmod.Debug;
import addsynth.overpoweredmod.items.DimensionAnchor;
import addsynth.overpoweredmod.items.FusionCore;
import addsynth.overpoweredmod.items.OverpoweredItem;
import addsynth.overpoweredmod.items.PlasmaItem;
import net.minecraft.world.item.Item;

public final class ModItems {

  static {
    Debug.log_setup_info("Begin loading Items class...");
  }

  public static final Item energized_power_core      = new OverpoweredItem("energized_power_core");
  public static final Item nullified_power_core      = new OverpoweredItem("nullified_power_core");
  public static final Item energy_grid               = new OverpoweredItem("energy_grid");
  public static final Item vacuum_container          = new OverpoweredItem("vacuum_container");
  
  public static final Item beam_emitter              = new OverpoweredItem("beam_emitter");
  public static final Item destructive_laser         = new OverpoweredItem("destructive_laser");
  public static final Item heavy_light_emitter       = new OverpoweredItem("heavy_light_emitter");
  public static final Item energy_stabilizer         = new OverpoweredItem("energy_stabilizer");
  public static final Item scanning_laser            = new OverpoweredItem("scanning_laser");
  public static final Item matter_energy_transformer = new OverpoweredItem("matter_energy_transformer");
  public static final Item high_frequency_beam       = new OverpoweredItem("high_frequency_beam");
  
  public static final Item plasma                    = new PlasmaItem("plasma");
  public static final Item fusion_core               = new FusionCore("fusion_core");
  public static final Item matter_energy_converter   = new OverpoweredItem("matter_energy_converter");
  public static final Item dimensional_flux          = new OverpoweredItem("dimensional_flux");
  public static final Item dimensional_anchor        = new DimensionAnchor("dimensional_anchor");
  
  static {
    Debug.log_setup_info("Finished loading Items class.");
  }

}
