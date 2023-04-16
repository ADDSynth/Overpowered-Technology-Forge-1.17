package addsynth.material.worldgen;

import addsynth.material.Material;
import addsynth.material.config.WorldgenConfig;
import addsynth.material.types.OreMaterial;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.ReplaceBlockConfiguration;

public final class GenFeatures {

   public static final ConfiguredFeature<?, ?> RUBY_ORE_FEATURE = gen_single_ore(
    Material.RUBY,
    WorldgenConfig.ruby_spawn_tries.get(),
    WorldgenConfig.ruby_min_height.get(),
    WorldgenConfig.ruby_max_height.get()
  );

  public static final ConfiguredFeature<?, ?> TOPAZ_ORE_FEATURE = gen_single_ore(
    Material.TOPAZ,
    WorldgenConfig.topaz_spawn_tries.get(),
    WorldgenConfig.topaz_min_height.get(),
    WorldgenConfig.topaz_max_height.get()
  );

  public static final ConfiguredFeature<?, ?> CITRINE_ORE_FEATURE = gen_single_ore(
    Material.CITRINE,
    WorldgenConfig.citrine_spawn_tries.get(),
    WorldgenConfig.citrine_min_height.get(),
    WorldgenConfig.citrine_max_height.get()
  );
  
  public static final ConfiguredFeature<?, ?> EMERALD_ORE_FEATURE = gen_single_ore(
    Material.EMERALD,
    WorldgenConfig.emerald_spawn_tries.get(),
    WorldgenConfig.emerald_min_height.get(),
    WorldgenConfig.emerald_max_height.get()
  );
  
  public static final ConfiguredFeature<?, ?> SAPPHIRE_ORE_FEATURE = gen_single_ore(
    Material.SAPPHIRE,
    WorldgenConfig.sapphire_spawn_tries.get(),
    WorldgenConfig.sapphire_min_height.get(),
    WorldgenConfig.sapphire_max_height.get()
  );
  
  public static final ConfiguredFeature<?, ?> AMETHYST_ORE_FEATURE = gen_single_ore(
    Material.AMETHYST,
    WorldgenConfig.amethyst_spawn_tries.get(),
    WorldgenConfig.amethyst_min_height.get(),
    WorldgenConfig.amethyst_max_height.get()
  );
  
  public static final ConfiguredFeature<?, ?> TIN_ORE_FEATURE = gen_standard_ore(
    Material.TIN,
    WorldgenConfig.tin_ore_size.get(),
    WorldgenConfig.tin_spawn_tries.get(),
    WorldgenConfig.tin_min_height.get(),
    WorldgenConfig.tin_max_height.get()
  );
  
  public static final ConfiguredFeature<?, ?> ALUMINUM_ORE_FEATURE = gen_standard_ore(
    Material.ALUMINUM,
    WorldgenConfig.aluminum_ore_size.get(),
    WorldgenConfig.aluminum_spawn_tries.get(),
    WorldgenConfig.aluminum_min_height.get(),
    WorldgenConfig.aluminum_max_height.get()
  );
  
  public static final ConfiguredFeature<?, ?> SILVER_ORE_FEATURE = gen_standard_ore(
    Material.SILVER,
    WorldgenConfig.silver_ore_size.get(),
    WorldgenConfig.silver_spawn_tries.get(),
    WorldgenConfig.silver_min_height.get(),
    WorldgenConfig.silver_max_height.get()
  );
  
  public static final ConfiguredFeature<?, ?> PLATINUM_ORE_FEATURE = gen_standard_ore(
    Material.PLATINUM,
    WorldgenConfig.platinum_ore_size.get(),
    WorldgenConfig.platinum_spawn_tries.get(),
    WorldgenConfig.platinum_min_height.get(),
    WorldgenConfig.platinum_max_height.get()
  );
  
  public static final ConfiguredFeature<?, ?> TITANIUM_ORE_FEATURE = gen_standard_ore(
    Material.TITANIUM,
    WorldgenConfig.titanium_ore_size.get(),
    WorldgenConfig.titanium_spawn_tries.get(),
    WorldgenConfig.titanium_min_height.get(),
    WorldgenConfig.titanium_max_height.get()
  );
  
  public static final ConfiguredFeature<?, ?> SILICON_ORE_FEATURE = gen_standard_ore(
    Material.SILICON,
    WorldgenConfig.silicon_ore_size.get(),
    WorldgenConfig.silicon_spawn_tries.get(),
    WorldgenConfig.silicon_min_height.get(),
    WorldgenConfig.silicon_max_height.get()
  );
  
  public static final ConfiguredFeature<?, ?> ROSE_QUARTZ_ORE_FEATURE = gen_single_ore(
    Material.ROSE_QUARTZ,
    WorldgenConfig.rose_quartz_spawn_tries.get(),
    WorldgenConfig.rose_quartz_min_height.get(),
    WorldgenConfig.rose_quartz_max_height.get()
  );

  private static final ConfiguredFeature<?, ?> gen_single_ore(final OreMaterial material, int tries, int min_level, int max_level){
    final ReplaceBlockConfiguration replace_block_config = new ReplaceBlockConfiguration(Blocks.STONE.defaultBlockState(), material.getOre().defaultBlockState());
    return Feature.REPLACE_SINGLE_BLOCK.configured(replace_block_config).rangeUniform(VerticalAnchor.absolute(min_level), VerticalAnchor.absolute(max_level)).squared().count(tries);
  }

  private static final ConfiguredFeature<?, ?> gen_standard_ore(final OreMaterial material, int size, int tries, int min_level, int max_level){
    final OreConfiguration ore_feature = new OreConfiguration(OreConfiguration.Predicates.STONE_ORE_REPLACEABLES, material.getOre().defaultBlockState(), size);
    return Feature.ORE.configured(ore_feature).rangeUniform(VerticalAnchor.absolute(min_level), VerticalAnchor.absolute(max_level)).squared().count(tries);
  }

}
