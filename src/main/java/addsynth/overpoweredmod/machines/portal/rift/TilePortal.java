package addsynth.overpoweredmod.machines.portal.rift;

import addsynth.core.tiles.TileBase;
import addsynth.core.util.game.tileentity.ITickingTileEntity;
import addsynth.overpoweredmod.registers.Tiles;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public final class TilePortal extends TileBase implements ITickingTileEntity {

  private int count = 0;
  private static final int life = 800;

  public TilePortal(BlockPos position, BlockState blockstate){
    super(Tiles.PORTAL_BLOCK, position, blockstate);
  }

  @Override
  public final void serverTick(){
    count += 1;
    if(count >= life){
      level.removeBlock(worldPosition, false);
    }
  }

}
