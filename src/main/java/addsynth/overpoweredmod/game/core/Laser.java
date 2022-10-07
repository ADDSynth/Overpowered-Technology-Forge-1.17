package addsynth.overpoweredmod.game.core;

import addsynth.overpoweredmod.Debug;
import addsynth.overpoweredmod.game.Names;
import addsynth.overpoweredmod.machines.laser.beam.LaserBeam;
import addsynth.overpoweredmod.machines.laser.cannon.LaserCannon;
import net.minecraft.world.level.block.Block;

public enum Laser {

  WHITE  (0),
  RED    (1),
  ORANGE (2),
  YELLOW (3),
  GREEN  (4),
  CYAN   (5),
  BLUE   (6),
  MAGENTA(7);

  static {
    Debug.log_setup_info("Begin loading Lasers class...");
  }

  public final LaserCannon cannon;
  public final Block beam;

  public static final Laser[] index = Laser.values();
  public static final Block[] cannons = {WHITE.cannon, RED.cannon, ORANGE.cannon, YELLOW.cannon, GREEN.cannon, CYAN.cannon, BLUE.cannon, MAGENTA.cannon};
  public static final Block[] beams = {WHITE.beam, RED.beam, ORANGE.beam, YELLOW.beam, GREEN.beam, CYAN.beam, BLUE.beam, MAGENTA.beam};
  
  private Laser(final int color){
    this.cannon = new LaserCannon(Names.LASER[color], color);
    this.beam   = new LaserBeam(Names.LASER_BEAM[color]);
  }

  static {
    Debug.log_setup_info("Finished loading Lasers class.");
  }

}
