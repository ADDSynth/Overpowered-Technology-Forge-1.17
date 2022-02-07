package addsynth.overpoweredmod.blocks.dimension;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AirBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;

public final class BlockAirNoDestroy extends AirBlock {

  public BlockAirNoDestroy(String name) {
    super(Block.Properties.of(Material.AIR));
    // MAYBE: Registers.add(this, name, false);
  }

  @Override
  public void playerWillDestroy(Level world, BlockPos pos, BlockState state, Player player){
    if(world.isClientSide() == false){
      // world.setBlockState(pos, Init.custom_air_block.getDefaultState(), 2);
    }
  }

}
