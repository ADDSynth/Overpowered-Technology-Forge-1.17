package addsynth.core.block_network;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.function.Consumer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;

public final class NodeList extends HashSet<Node> {

  public NodeList(){
    super(100);
  }

  public NodeList(final int size){
    super(size);
  }

  public final void setFrom(final HashSet<Node> hash_set){
    addAll(hash_set);
  }

  public final ArrayList<BlockPos> getPositions(){
    final ArrayList<BlockPos> positions = new ArrayList<>(100);
    for(final Node node : this){
      if(node.isInvalid() == false){
        positions.add(node.position);
      }
    }
    return positions;
  }

  public final ArrayList<BlockEntity> getTileEntities(){
    final ArrayList<BlockEntity> tiles = new ArrayList<>(100);
    BlockEntity tile;
    for(final Node node : this){
      tile = node.getTile();
      if(tile != null){
        if(node.isInvalid() == false){
          tiles.add(tile);
        }
      }
    }
    return tiles;
  }

  public final boolean contains(final BlockEntity tile){
    for(final Node node : this){
      if(node.getTile() == tile){
        return true;
      }
    }
    return false;
  }

  public final boolean contains(final BlockPos position){
    for(final Node node : this){
      if(node.position.equals(position)){
        return true;
      }
    }
    return false;
  }

  public final void remove_invalid(){
    removeIf((Node n) -> n == null ? true : n.isInvalid());
  }

  public final void forAllTileEntities(final Consumer<BlockEntity> action){
    BlockEntity tile;
    for(Node node : this){
      tile = node.getTile();
      if(tile != null){
        action.accept(tile);
      }
    }
  }

  public final <T extends BlockEntity> void forAllTileEntities(final Class<T> tileEntity_type, final Consumer<T> action){
    BlockEntity tile;
    for(Node node : this){
      tile = node.getTile();
      if(tile != null){
        if(tileEntity_type.isInstance(tile)){
          action.accept(tileEntity_type.cast(tile));
        }
      }
    }
  }

  @SuppressWarnings({"unchecked", "null"})
  public final void setBlockNetwork(final BlockNetwork network){
    remove_invalid();
    forEach((Node node) -> {
      if(node.getTile() != null){
        ((IBlockNetworkUser)node.getTile()).setBlockNetwork(network);
      }
    });
  }

}
