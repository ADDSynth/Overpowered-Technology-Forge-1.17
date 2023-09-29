package addsynth.core.block_network;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Predicate;
import addsynth.core.util.block.BlockUtil;
import addsynth.core.util.java.ArrayUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;

/** This is the collection of BlockEntities that the BlockNetwork
 *  consists of. It is only meant to be used with blockNetworks. */
public final class BlockList<T extends BlockEntity & IBlockNetworkUser> {

  private final ArrayList<T> list;

  public BlockList(){
    list = new ArrayList<>(100);
  }

  public BlockList(final int size){
    list = new ArrayList<>(size);
  }

  public final boolean isFirstTile(final BlockEntity tile){
    if(list.size() > 0){
      return list.get(0) == tile;
    }
    return false;
  }

  /** This is the main function that finds all blocks belonging to this BlockNetwork.
   *  This is called by {@link BlockNetwork#updateBlockNetwork(Level, BlockPos)}. */
  @SuppressWarnings({"unchecked", "null"})
  public final void update(final Level world, final BlockPos from, final BlockNetwork network, final Predicate<Node> is_valid, final BiConsumer<Node, Level> custom_search){
    // get tiles
    final HashSet<Node> found = BlockUtil.find_blocks(from, world, is_valid, custom_search);
  
    // extract tiles
    final ArrayList<T> tiles = new ArrayList<>(100);
    for(final Node node : found){
      final T tile = (T)node.getTile();
      // set BlockNetwork
      tile.setBlockNetwork(network);
      tiles.add(tile);
    }

    // sync list
    DebugBlockNetwork.BLOCKS_CHANGED(network, list.size(), tiles.size());
    ArrayUtil.syncList(list, tiles);
  }

  public final void remove(final BlockEntity tile){
    list.remove(tile);
  }

  public final ArrayList<BlockPos> getBlockPositions(){
    final ArrayList<BlockPos> positions = new ArrayList<>(100);
    for(final BlockEntity tile : list){
      if(tile.isRemoved() == false){
        positions.add(tile.getBlockPos());
      }
    }
    return positions;
  }

  public final ArrayList<BlockEntity> getTileEntities(){
    return new ArrayList<>(list);
  }

  public final boolean contains(final BlockEntity tile){
    return list.contains(tile);
  }

  public final boolean contains(final BlockPos position){
    for(final BlockEntity tile : list){
      if(tile.getBlockPos().equals(position)){
        return true;
      }
    }
    return false;
  }

  public final void remove_invalid(){
    list.removeIf((BlockEntity tile) -> tile.isRemoved());
  }

  public final void forAllTileEntities(final Consumer<T> action){
    list.forEach(action);
  }

  public final int size(){
    return list.size();
  }

  public final BlockEntity[] toArray(){
    return list.toArray(new BlockEntity[list.size()]);
  }

}
