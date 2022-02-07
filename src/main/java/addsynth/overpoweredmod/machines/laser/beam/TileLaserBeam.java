package addsynth.overpoweredmod.machines.laser.beam;

import addsynth.core.util.game.tileentity.ITickingTileEntity;
import addsynth.overpoweredmod.registers.Tiles;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public final class TileLaserBeam extends BlockEntity implements ITickingTileEntity {

  private static final int max_life = 10;
  private int life = max_life;

  // I tested whether I could do the same function as this TileEntity with just a Blockstate Integer
  //   Property. In short, yes I can, but you have to manually schedule a block update using the
  //   world.scheduleUpdate() function. In retrospect, doing it this way does not reduce any lag caused
  //   by the LaserBeam block having light. So it's just easier to keep this as a TileEntity for now.

  public TileLaserBeam(BlockPos position, BlockState blockstate){
    super(Tiles.LASER_BEAM, position, blockstate);
  }

  @Override
  public final void serverTick(){
    life -= 1;
    if(life <= 0){
      level.removeBlock(this.worldPosition, false);
    }
  }

}
