package addsynth.core.gameplay.blocks;

import addsynth.core.ADDSynthCore;
import addsynth.core.util.constants.Constants;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;

public final class CautionBlock extends Block {

  public CautionBlock(final String name){
    super(Block.Properties.of(Material.STONE, MaterialColor.COLOR_YELLOW).sound(SoundType.STONE).strength(2.0f, Constants.block_resistance));
    ADDSynthCore.registry.register_block(this, name, new Item.Properties().tab(ADDSynthCore.creative_tab));
  }

}
