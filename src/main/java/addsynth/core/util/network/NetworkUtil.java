package addsynth.core.util.network;

import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.level.Level;
import net.minecraftforge.fmllegacy.network.PacketDistributor;
import net.minecraftforge.fmllegacy.network.simple.SimpleChannel;

public final class NetworkUtil {

  public static final void writeBlockPos(final FriendlyByteBuf data, final BlockPos pos){
    data.writeInt(pos.getX());
    data.writeInt(pos.getY());
    data.writeInt(pos.getZ());
  }

  public static final BlockPos readBlockPos(final FriendlyByteBuf data){
    return new BlockPos(data.readInt(), data.readInt(), data.readInt());
  }

  public static final void writeBlockPositions(final FriendlyByteBuf data, final BlockPos[] positions){
    data.writeInt(positions.length);
    for(final BlockPos pos : positions){
      writeBlockPos(data, pos);
    }
  }

  public static final BlockPos[] readBlockPositions(final FriendlyByteBuf data){
    int i;
    final int length = data.readInt();
    final BlockPos[] positions = new BlockPos[length];
    for(i = 0; i < length; i++){
      positions[i] = readBlockPos(data);
    }
    return positions;
  }

  public static final void writeString(final FriendlyByteBuf data, final String string){
    data.writeUtf(string);
  }

  /** You CANNOT use the vanilla method {@link FriendlyByteBuf#readString()} because it has
   *  the ClientOnly annotation, and thus will crash the server if called on that side!
   *  You can call this to get around that. This does exactly what the vanilla method does.
   */
  public static final String readString(final FriendlyByteBuf data){ // OPTIMIZE: our own read/writeString functions are no longer needed for Minecraft 1.16
    return data.readUtf();
  }

  public static final void writeStringArray(final FriendlyByteBuf data, final String[] string_array){
    data.writeInt(string_array.length);
    for(final String s : string_array){
      data.writeUtf(s);
    }
  }

  public static final String[] readStringArray(final FriendlyByteBuf data){
    int i;
    final int length = data.readInt();
    final String[] strings = new String[length];
    for(i = 0; i < length; i++){
      strings[i] = data.readUtf();
    }
    return strings;
  }

  public static final void writeTextComponentArray(final FriendlyByteBuf data, final Component[] text_component_array){
    data.writeInt(text_component_array.length);
    for(final Component t : text_component_array){
      data.writeUtf(t.getString());
    }
  }

  public static final Component[] readTextComponentArray(final FriendlyByteBuf data){
    int i;
    final int length = data.readInt();
    final Component[] text_components = new Component[length];
    for(i = 0; i < length; i++){
      text_components[i] = new TextComponent(readString(data));
    }
    return text_components;
  }

  /** Sends the Network message to all clients in the world you specify.
   *  Must be called on the server side.
   * @param network
   * @param world
   * @param message
   */
  public static final void send_to_clients_in_world(final SimpleChannel network, final Level world, final Object message){
    network.send(PacketDistributor.DIMENSION.with(() -> world.dimension()), message);
  }

}
