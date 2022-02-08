package addsynth.core.util.game.tileentity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class TileEntityUtil {

  /**
   * Porting over my code from Minecraft 1.16 to Minecraft 1.17, all the TileEntities that had a tick method, only
   * had that method because they implement the {@link ITickingTileEntity} interface, and was an instance method.
   * Well starting in Minecraft 1.17, the tick method must now be a static method. I use this as an abstraction
   * to ease the transition. Many tick functions use instance variables because they were designed to be called
   * as an instance method.
   * Using this, your TileEntity is only expected to have the {@link ITickingTileEntity#serverTick() serverTick()} method.
   * @param <T>
   * @param world
   * @param position
   * @param state
   * @param tile
   * @since Minecraft 1.17
   */
  public static final <T extends BlockEntity & ITickingTileEntity> void tick(Level world, BlockPos position, BlockState state, T tile){
    if(tile != null){
      tile.serverTick();
    }
  }

  public static final <T extends BlockEntity & IClientTick> void clientTick(Level world, BlockPos position, BlockState state, T tile){
    if(tile != null){
      tile.clientTick();
    }
  }

}
