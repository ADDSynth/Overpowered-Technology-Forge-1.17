package addsynth.overpoweredmod.config;

import org.apache.commons.lang3.tuple.Pair;
import net.minecraftforge.common.ForgeConfigSpec;

public final class Values {

  private static final Pair<Values, ForgeConfigSpec> SPEC_PAIR = new ForgeConfigSpec.Builder().configure(Values::new);
  public static final Values INSTANCE = SPEC_PAIR.getLeft();
  public static final ForgeConfigSpec CONFIG_SPEC = SPEC_PAIR.getRight();

  private static final int DEFAULT_PORTAL_SPAWN_TIME = 40;
  private static final float DEFAULT_UNKNOWN_TREE_SPAWN_CHANCE = 0.0005f; // 1 / 2000

  public static ForgeConfigSpec.IntValue portal_spawn_time;
  public static ForgeConfigSpec.DoubleValue unknown_dimension_tree_spawn_chance;

  public Values(final ForgeConfigSpec.Builder builder){

    // float value, chance that ANYTHING will drop
    // all 4 ring weight values
    // all 5 armor weight values
    
    portal_spawn_time = builder.defineInRange("Portal Spawn Time (in seconds)", DEFAULT_PORTAL_SPAWN_TIME, 5, 3600);
    
    // Spawn chance of Unknown Trees in the weird dimension.
    builder.push("Other Values");
    unknown_dimension_tree_spawn_chance = builder.comment(
      "This float value determines the chance a weird tree will spawn for each chunk\nin the Unknown Dimension.")
      .defineInRange("Weird Tree Spawn Chance", DEFAULT_UNKNOWN_TREE_SPAWN_CHANCE, Float.MIN_NORMAL, 1.0f);
    builder.pop();
  }

}
