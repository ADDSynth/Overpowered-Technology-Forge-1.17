package addsynth.overpoweredmod.game.core;

import addsynth.material.Material;
import addsynth.material.types.gem.Gem;
import addsynth.material.util.MaterialTag;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.Tags;

public final class Gems {

  private static final Gem[] index = {
    Material.RUBY,    Material.TOPAZ,    Material.CITRINE,  Material.EMERALD,
    Material.DIAMOND, Material.SAPPHIRE, Material.AMETHYST, Material.QUARTZ
  };

  public static final ItemStack getGem(final int gem_id){
    return new ItemStack(index[gem_id].getGem());
  }

  public static final ItemStack getShard(final int gem_id){
    return new ItemStack(index[gem_id].getGemShard());
  }

  public static final int getGemIndex(final ItemStack gem){
    return getGemIndex(gem.getItem());
  }
  
  public static final int getGemIndex(final Item gem){
    if(MaterialTag.RUBY.GEMS.contains(gem)    ){return 0;}
    if(MaterialTag.TOPAZ.GEMS.contains(gem)   ){return 1;}
    if(MaterialTag.CITRINE.GEMS.contains(gem) ){return 2;}
    if(Tags.Items.GEMS_EMERALD.contains(gem)  ){return 3;}
    if(Tags.Items.GEMS_DIAMOND.contains(gem)  ){return 4;}
    if(MaterialTag.SAPPHIRE.GEMS.contains(gem)){return 5;}
    if(MaterialTag.AMETHYST.GEMS.contains(gem)){return 6;}
    if(Tags.Items.GEMS_QUARTZ.contains(gem)   ){return 7;}
    return -1;
  }

}
