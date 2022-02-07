package addsynth.material.blocks;

import addsynth.material.ADDSynthMaterials;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;

// UNUSED Material Generic Storage Block
// Based off of the vanilla Coal Block
public final class GenericStorageBlock extends Block {

  public GenericStorageBlock(final String name, final MaterialColor color){
    super(Block.Properties.of(Material.STONE, color).strength(5.0f, 6.0f).sound(SoundType.STONE));
    ADDSynthMaterials.registry.register_block(this, name, new Item.Properties().tab(ADDSynthMaterials.creative_tab));
  }

}
