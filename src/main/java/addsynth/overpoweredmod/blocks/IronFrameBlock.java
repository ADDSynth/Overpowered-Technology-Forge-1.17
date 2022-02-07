package addsynth.overpoweredmod.blocks;

import addsynth.overpoweredmod.OverpoweredTechnology;
import addsynth.overpoweredmod.assets.CreativeTabs;
import addsynth.overpoweredmod.machines.data_cable.DataCable;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;

public final class IronFrameBlock extends Block {

  public IronFrameBlock(final String name){
    super(Block.Properties.of(Material.METAL, MaterialColor.WOOL).strength(3.5f, 300.0f));
    OverpoweredTechnology.registry.register_block(this, name, new Item.Properties().tab(CreativeTabs.creative_tab));
    DataCable.addAttachableBlock(this);
  }

}
