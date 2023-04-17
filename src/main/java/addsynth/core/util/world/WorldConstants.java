package addsynth.core.util.world;

import net.minecraft.world.level.Level;

public final class WorldConstants {

  public static final int chunk_size = 16;
  
  /** @deprecated Despite what you may think, this is not constant! Use {@link Level#getMaxBuildHeight} instead. */
  public static final int world_height    = 256;
  /** @deprecated Despite what you may think, this is not constant! Use {@link Level#getMinBuildHeight} instead. */
  public static final int bottom_level    =   0;
  
  public static final int cloud_level     = 120;
  public static final int sea_level       =  63;
  // https://minecraft.fandom.com/wiki/Snowfall
  // I was going to add the level that causes snowfall to occur, but there's 3 different levels and it depends on the Biome.
  
}
