package addsynth.overpoweredmod.machines.magic_infuser;

import addsynth.core.container.TileEntityContainer;
import addsynth.core.container.slots.InputSlot;
import addsynth.core.container.slots.OutputSlot;
import addsynth.overpoweredmod.machines.Filters;
import addsynth.overpoweredmod.registers.Containers;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

public final class ContainerMagicInfuser extends TileEntityContainer<TileMagicInfuser> {

  public ContainerMagicInfuser(final int id, final Inventory player_inventory, final TileMagicInfuser tile){
    super(Containers.MAGIC_INFUSER, id, player_inventory, tile);
    common_setup(player_inventory);
  }

  public ContainerMagicInfuser(final int id, final Inventory player_inventory, final FriendlyByteBuf data){
    super(Containers.MAGIC_INFUSER, id, player_inventory, data);
    common_setup(player_inventory);
  }

  private final void common_setup(final Inventory player_inventory){
    make_player_inventory(player_inventory,8,105);
    addSlot(new InputSlot(tile,0,new Item[]{Items.BOOK},12,44));
    addSlot(new InputSlot(tile,1,Filters.magic_infuser,30,44));
    addSlot(new OutputSlot(tile, 0, 143, 44));
  }

}
