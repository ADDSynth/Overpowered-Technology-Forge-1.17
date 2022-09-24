package addsynth.core.game.tiles;

import addsynth.core.block_network.BlockNetwork;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

/** This is the standard Base TileEntity that most TileEntities should derive from.
 *  It greatly simplifies syncing data to clients, and automatically handles world
 *  saving and loading. 
 *  @author ADDSynth
 */
public abstract class TileBase extends TileAbstractBase {

  public TileBase(final BlockEntityType type, BlockPos position, BlockState blockstate){
    super(type, position, blockstate);
  }

  // http://mcforge.readthedocs.io/en/latest/tileentities/tileentity/#synchronizing-the-data-to-the-client
  // https://github.com/TheGreyGhost/MinecraftByExample/blob/master/src/main/java/minecraftbyexample/mbe20_tileentity_data/TileEntityData.java
  // https://github.com/AppliedEnergistics/Applied-Energistics-2/blob/rv6-1.12/src/main/java/appeng/tile/AEBaseTile.java
  // https://github.com/Railcraft/Railcraft/blob/mc-1.12.2/src/main/java/mods/railcraft/common/blocks/RailcraftTileEntity.java

  // When the world loads from disk, the server needs to send the TileEntity information to the client
  //  it uses getUpdatePacket(), getUpdateTag(), onDataPacket(), and handleUpdateTag() to do this:
  //  getUpdatePacket() and onDataPacket() are used for one-at-a-time TileEntity updates
  //  getUpdateTag() and handleUpdateTag() are used by vanilla to collate together into a single chunk update packet.

  // NBTexplorer is a very useful tool to examine the structure of your NBT saved data and make sure it's correct:
  //   http://www.minecraftforum.net/forums/mapping-and-modding/minecraft-tools/1262665-nbtexplorer-nbt-editor-for-windows-and-mac

  @Override
  public final ClientboundBlockEntityDataPacket getUpdatePacket(){
    final CompoundTag nbtTag = new CompoundTag();
    save(nbtTag);
    return new ClientboundBlockEntityDataPacket(this.worldPosition, -1, nbtTag);
  }

  @Override
  public final void onDataPacket(final Connection net, final ClientboundBlockEntityDataPacket pkt){
    load(pkt.getTag());
  }

  @Override
  public final CompoundTag getUpdateTag(){
    final CompoundTag nbtTagCompound = new CompoundTag();
    save(nbtTagCompound);
    return nbtTagCompound;
  }

  @Override
  public final void handleUpdateTag(final CompoundTag tag){
    load(tag);
  }

  /** <p>Helper method to send TileEntity changes to the client.</p>
   *  <p>This should only be called on the server side and should be called when any data changes.
   *     For complex TileEntities that likely has data that changes every tick, we actually recommend
   *     setting a boolean variable to <code>true</code> when any data changes, then check that
   *     boolean variable at the end of the <code>tick()</code> method and call update_data().</p>
   *  <p>For TileEntities which are a part of a {@link BlockNetwork} it is required to override
   *     this so that you instead update the BlockNetwork which then updates each TileEntity manually.</p>
   */
  @SuppressWarnings("null")
  @Override
  public void update_data(){
    if(level != null){
      setChanged();
      final BlockState blockstate = getBlockState();
      level.sendBlockUpdated(worldPosition, blockstate, blockstate, Block.UPDATE_ALL);
    }
  }

}
