package addsynth.core.game.tiles;

import addsynth.core.game.inventory.IInputInventory;
import addsynth.core.game.inventory.IOutputInventory;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

/** This is for TileEntities that have an Input Inventory and an Output Inventory,
 *  and possibly a Working Inventory as well. This is a machine that works on items
 *  without using any Energy.
 * @author ADDSynth
 */
public abstract class TileMachine extends TileBase implements IInputInventory, IOutputInventory {

  public TileMachine(BlockEntityType type, BlockPos position, BlockState blockstate){
    super(type, position, blockstate);
  }

}
