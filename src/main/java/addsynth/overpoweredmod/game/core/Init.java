package addsynth.overpoweredmod.game.core;

import addsynth.overpoweredmod.Debug;
import addsynth.overpoweredmod.blocks.IronFrameBlock;
import addsynth.overpoweredmod.blocks.LightBlock;
import addsynth.overpoweredmod.blocks.NullBlock;
import addsynth.overpoweredmod.game.Names;
import addsynth.overpoweredmod.items.EnergyCrystal;
import addsynth.overpoweredmod.items.EnergyCrystalShards;
import addsynth.overpoweredmod.items.OverpoweredItem;
import addsynth.overpoweredmod.items.VoidCrystal;
import addsynth.overpoweredmod.machines.black_hole.BlackHoleBlock;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public final class Init {

  static {
    Debug.log_setup_info("Begin loading Init class...");
  }

  public static final Item                    celestial_gem            = new OverpoweredItem(Names.CELESTIAL_GEM);
  public static final Item                    energy_crystal_shards    = new EnergyCrystalShards();
  public static final Item                    energy_crystal           = new EnergyCrystal();
  public static final Block                   light_block              = new LightBlock();

  public static final Item                    void_crystal             = new VoidCrystal();
  public static final Block                   null_block               = new NullBlock();

  public static final Block                   iron_frame_block         = new IronFrameBlock();
  public static final BlackHoleBlock          black_hole               = new BlackHoleBlock();

  static {
    Debug.log_setup_info("Finished loading Init class.");
  }

}
