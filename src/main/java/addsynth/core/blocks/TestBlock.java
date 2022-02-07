package addsynth.core.blocks;

import addsynth.core.ADDSynthCore;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;

public final class TestBlock extends Block {

  public TestBlock(){
    super(Block.Properties.of(Material.STONE, MaterialColor.SNOW).strength(0.2f, 6.0f));
    ADDSynthCore.registry.register_block(this, "test_block", new Item.Properties().tab(ADDSynthCore.creative_tab));
  }

  public TestBlock(final String name){
    super(Block.Properties.of(Material.STONE, MaterialColor.SNOW).strength(0.2f, 6.0f));
    ADDSynthCore.registry.register_block(this, name, new Item.Properties().tab(ADDSynthCore.creative_tab));
  }

}
