package addsynth.core.game.tiles;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

/** This is our abstract BlockEntity class which only contains a few
 *  basic stuff. Do not override this directly. Either create your own
 *  BlockEntity or override {@link TileBase} or {@link TileBaseNoData}.
 * @author ADDSynth
 */
public abstract class TileAbstractBase extends BlockEntity {

  public TileAbstractBase(final BlockEntityType type, BlockPos position, BlockState blockstate){
    super(type, position, blockstate);
  }

  @SuppressWarnings("null")
  protected final boolean onServerSide(){
    return !level.isClientSide;
  }

  @SuppressWarnings("null")
  protected final boolean onClientSide(){
    return level.isClientSide;
  }

  public abstract void update_data();

}
