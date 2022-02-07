package addsynth.energy.gameplay.machines.circuit_fabricator;

import java.util.function.Supplier;
import addsynth.core.util.game.MinecraftUtility;
import addsynth.energy.ADDSynthEnergy;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.fmllegacy.network.NetworkEvent;

public final class ChangeCircuitCraftType {

  private final BlockPos position;
  private final int circuit_id;

  public ChangeCircuitCraftType(final BlockPos position, final int circuit_id){
    this.position = position;
    this.circuit_id = circuit_id;
  }

  public static final void encode(final ChangeCircuitCraftType message, final FriendlyByteBuf buf){
    buf.writeInt(message.position.getX());
    buf.writeInt(message.position.getY());
    buf.writeInt(message.position.getZ());
    buf.writeInt(message.circuit_id);
  }

  public static final ChangeCircuitCraftType decode(final FriendlyByteBuf buf){
    return new ChangeCircuitCraftType(new BlockPos(buf.readInt(),buf.readInt(),buf.readInt()), buf.readInt());
  }

  public static void handle(final ChangeCircuitCraftType message, final Supplier<NetworkEvent.Context> context_supplier){
    final NetworkEvent.Context context = context_supplier.get();
    final ServerPlayer player = context.getSender();
    if(player != null){
      @SuppressWarnings("resource")
      final ServerLevel world = player.getLevel();
      context.enqueueWork(() -> {
        if(world.isAreaLoaded(message.position, 0)){
          final TileCircuitFabricator tile = MinecraftUtility.getTileEntity(message.position, world, TileCircuitFabricator.class);
          if(tile != null){
            tile.change_circuit_craft(message.circuit_id, true);
            tile.ejectInvalidItems(player);
          }
          else{
            ADDSynthEnergy.log.warn(new NullPointerException("No TileEntity exists at location: "+message.position+"."));
          }
        }
      });
      context.setPacketHandled(true);
    }
  }

}
