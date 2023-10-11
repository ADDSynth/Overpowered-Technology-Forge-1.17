package addsynth.overpoweredmod.machines.suspension_bridge;

import java.util.function.Predicate;
import javax.annotation.Nullable;
import addsynth.core.block_network.BlockNetwork;
import addsynth.core.block_network.IBlockNetworkUser;
import addsynth.core.game.inventory.SlotData;
import addsynth.core.util.constants.Constants;
import addsynth.core.util.constants.DirectionConstant;
import addsynth.energy.lib.main.Receiver;
import addsynth.energy.lib.tiles.TileBasicMachine;
import addsynth.overpoweredmod.config.Config;
import addsynth.overpoweredmod.game.core.Lens;
import addsynth.overpoweredmod.items.basic.LensItem;
import addsynth.overpoweredmod.registers.Tiles;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;

public final class TileSuspensionBridge extends TileBasicMachine implements IBlockNetworkUser<BridgeNetwork>, MenuProvider {

  public static final Predicate<ItemStack> filter = (ItemStack stack) -> {
    final Item item = stack.getItem();
    return item instanceof LensItem;
  };
  private static final SlotData[] slot_data = {new SlotData(filter, 1)};

  private BridgeNetwork network;

  private BridgeMessage bridge_message;
  
  // Each TileEntity must keep a copy of the data we need to save to disk.
  private final BridgeData[] bridge_data = {
    new BridgeData(0), new BridgeData(1), new BridgeData(2),
    new BridgeData(3), new BridgeData(4), new BridgeData(5)
  };
  
  private boolean powered;
  private int maximum_length = Config.energy_bridge_max_distance.get();

  public TileSuspensionBridge(BlockPos position, BlockState blockstate){
    super(Tiles.ENERGY_SUSPENSION_BRIDGE, position, blockstate, slot_data, new Receiver());
  }

  @Override
  public final void serverTick(){
    BlockNetwork.tick(network, level, this, BridgeNetwork::new);
  }

  @Override
  public final void load(final CompoundTag nbt){
    super.load(nbt);
    powered = nbt.getBoolean("Powered");
    if(nbt.contains("Maximum Length")){
      maximum_length = nbt.getInt("Maximum Length");
    }
    else{
      // legacy data won't have the maximum length saved, so we use the same value as legacy would.
      maximum_length = Config.energy_bridge_max_distance.get();
    }
    bridge_data[0].load(nbt);
    bridge_data[1].load(nbt);
    bridge_data[2].load(nbt);
    bridge_data[3].load(nbt);
    bridge_data[4].load(nbt);
    bridge_data[5].load(nbt);
  }

  @Override
  public final CompoundTag save(final CompoundTag nbt){
    super.save(nbt);
    nbt.putBoolean("Powered", powered);
    nbt.putInt("Maximum Length", maximum_length);
    bridge_data[0].save(nbt);
    bridge_data[1].save(nbt);
    bridge_data[2].save(nbt);
    bridge_data[3].save(nbt);
    bridge_data[4].save(nbt);
    bridge_data[5].save(nbt);
    return nbt;
  }

  @Override
  public void load_block_network_data(){
    network.load_data(Lens.get_index(inventory.getStackInSlot(0)), powered, bridge_data, maximum_length);
  }

  public final void save_block_network_data(final int lens_index, final boolean powered, final BridgeData[] data, final int maximum_length){
    inventory.setStackInSlot(0, lens_index < 0 ? ItemStack.EMPTY : new ItemStack(Lens.index[lens_index]));
    this.powered = powered;
    bridge_data[0].set(data[0]);
    bridge_data[1].set(data[1]);
    bridge_data[2].set(data[2]);
    bridge_data[3].set(data[3]);
    bridge_data[4].set(data[4]);
    bridge_data[5].set(data[5]);
    this.maximum_length = maximum_length;
    update_data();
  }

  @Override
  public final void onInventoryChanged(){
    if(onServerSide()){
      network.update_lens(level, Lens.get_index(inventory.getStackInSlot(0)));
    }
  }

  @Override
  public final void setBlockNetwork(BridgeNetwork network){
    this.network = network;
  }

  @Override
  @Nullable
  public final BridgeNetwork getBlockNetwork(){
    return network;
  }

  @Override
  public final Receiver getEnergy(){
    return energy;
  }

  public final void setMessages(final BridgeMessage bridge_message, final BridgeMessage[] messages){
    this.bridge_message = bridge_message;
    bridge_data[0].message = messages[0];
    bridge_data[1].message = messages[1];
    bridge_data[2].message = messages[2];
    bridge_data[3].message = messages[3];
    bridge_data[4].message = messages[4];
    bridge_data[5].message = messages[5];
  }

  public final String getBridgeMessage(){
    if(bridge_message == null){ return Constants.null_error; }
    return bridge_message.getMessage();
  }

  public final String getMessage(final int index){
    if(bridge_data[index].message == null){ return Constants.null_error; }
    return bridge_data[index].message.getMessage();
  }

  public final int getLength(final int direction){
    return bridge_data[direction].length;
  }

  public final boolean can_rotate(){
    return bridge_data[DirectionConstant.UP  ].message.is_valid() ||
           bridge_data[DirectionConstant.DOWN].message.is_valid();
  }

  @Override
  public final void drop_inventory(){
    if(network.getCount() == 1){
      super.drop_inventory();
    }
  }

  @Override
  @Nullable
  public AbstractContainerMenu createMenu(int id, Inventory player_inventory, Player player){
    return new ContainerSuspensionBridge(id, player_inventory, this);
  }

  @Override
  public Component getDisplayName(){
    return getBlockState().getBlock().getName();
  }

}
