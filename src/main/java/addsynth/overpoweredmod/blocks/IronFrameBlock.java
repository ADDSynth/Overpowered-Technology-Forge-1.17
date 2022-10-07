package addsynth.overpoweredmod.blocks;

import addsynth.core.game.RegistryUtil;
import addsynth.overpoweredmod.assets.CreativeTabs;
import addsynth.overpoweredmod.game.Names;
import addsynth.overpoweredmod.machines.data_cable.DataCable;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;

public final class IronFrameBlock extends Block {

  public IronFrameBlock(){
    super(Block.Properties.of(Material.METAL, MaterialColor.WOOL).strength(3.5f, 300.0f));
    RegistryUtil.register_block(this, Names.IRON_FRAME_BLOCK, CreativeTabs.creative_tab);
    DataCable.addAttachableBlock(this);
  }

}
