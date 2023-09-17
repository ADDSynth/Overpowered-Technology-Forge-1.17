package addsynth.material.worldgen;

import addsynth.core.config.WorldgenOreConfig;
import addsynth.core.config.WorldgenSingleOreConfig;
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

  public static final ConfiguredFeature<?, ?>     RUBY_ORE_FEATURE = gen_single_ore(Material.RUBY,     WorldgenConfig.ruby);
  public static final ConfiguredFeature<?, ?>    TOPAZ_ORE_FEATURE = gen_single_ore(Material.TOPAZ,    WorldgenConfig.topaz);
  public static final ConfiguredFeature<?, ?>  CITRINE_ORE_FEATURE = gen_single_ore(Material.CITRINE,  WorldgenConfig.citrine);
  public static final ConfiguredFeature<?, ?>  EMERALD_ORE_FEATURE = gen_single_ore(Material.EMERALD,  WorldgenConfig.emerald);
  public static final ConfiguredFeature<?, ?> SAPPHIRE_ORE_FEATURE = gen_single_ore(Material.SAPPHIRE, WorldgenConfig.sapphire);
  public static final ConfiguredFeature<?, ?> AMETHYST_ORE_FEATURE = gen_single_ore(Material.AMETHYST, WorldgenConfig.amethyst);
  
  public static final ConfiguredFeature<?, ?>      TIN_ORE_FEATURE = gen_standard_ore(Material.TIN,      WorldgenConfig.tin);
  public static final ConfiguredFeature<?, ?> ALUMINUM_ORE_FEATURE = gen_standard_ore(Material.ALUMINUM, WorldgenConfig.aluminum);
  public static final ConfiguredFeature<?, ?>   SILVER_ORE_FEATURE = gen_standard_ore(Material.SILVER,   WorldgenConfig.silver);
  public static final ConfiguredFeature<?, ?> PLATINUM_ORE_FEATURE = gen_standard_ore(Material.PLATINUM, WorldgenConfig.platinum);
  public static final ConfiguredFeature<?, ?> TITANIUM_ORE_FEATURE = gen_standard_ore(Material.TITANIUM, WorldgenConfig.titanium);
  
  public static final ConfiguredFeature<?, ?>     SILICON_ORE_FEATURE = gen_standard_ore(Material.SILICON, WorldgenConfig.silicon);
  public static final ConfiguredFeature<?, ?> ROSE_QUARTZ_ORE_FEATURE = gen_single_ore(Material.ROSE_QUARTZ, WorldgenConfig.rose_quartz);

  private static final ConfiguredFeature<?, ?> gen_single_ore(final OreMaterial material, final WorldgenSingleOreConfig ore_config){
    final ReplaceBlockConfiguration replace_block_config = new ReplaceBlockConfiguration(Blocks.STONE.defaultBlockState(), material.getOre().defaultBlockState());
    final VerticalAnchor bottom = VerticalAnchor.absolute(ore_config.min_height.get());
    final VerticalAnchor top    = VerticalAnchor.absolute(ore_config.max_height.get());
    return Feature.REPLACE_SINGLE_BLOCK.configured(replace_block_config).rangeUniform(bottom, top).squared().count(ore_config.tries.get());
  }

  private static final ConfiguredFeature<?, ?> gen_standard_ore(final OreMaterial material, final WorldgenOreConfig ore_config){
    final OreConfiguration ore_feature = new OreConfiguration(OreConfiguration.Predicates.STONE_ORE_REPLACEABLES, material.getOre().defaultBlockState(), ore_config.ore_size.get());
    final VerticalAnchor bottom = VerticalAnchor.absolute(ore_config.min_height.get());
    final VerticalAnchor top    = VerticalAnchor.absolute(ore_config.max_height.get());
    return Feature.ORE.configured(ore_feature).rangeUniform(bottom, top).squared().count(ore_config.tries.get());
  }

}
