package addsynth.overpoweredmod.machines.matter_compressor;

import addsynth.core.game.RegistryUtil;
import addsynth.core.game.inventory.SlotData;
import addsynth.core.game.tiles.TileMachine;
import addsynth.core.util.game.tileentity.ITickingTileEntity;
import addsynth.energy.lib.main.IEnergyConsumer;
import addsynth.energy.lib.main.Receiver;
import addsynth.overpoweredmod.config.Config;
import addsynth.overpoweredmod.game.reference.OverpoweredBlocks;
import addsynth.overpoweredmod.game.reference.OverpoweredItems;
import addsynth.overpoweredmod.registers.Tiles;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;

public final class TileMatterCompressor extends TileMachine implements ITickingTileEntity, IEnergyConsumer, MenuProvider {

  private boolean changed;
  private int matter;
  private final Receiver energy;

  public static final SlotData[] slot_data = {
    new SlotData(RegistryUtil.getItemBlock(OverpoweredBlocks.black_hole), 1),
    new SlotData()
  };

  public TileMatterCompressor(final BlockPos position, final BlockState blockstate){
    super(Tiles.MATTER_COMPRESSOR, position, blockstate, slot_data, 1);
    energy = new Receiver();
  }

  @Override
  public final void serverTick(){
    final ItemStack input = input_inventory.getStackInSlot(1);
    if(input.isEmpty() == false){
      final ItemStack unimatter = new ItemStack(OverpoweredItems.unimatter, 1);
      
      if(input.getItem() == OverpoweredItems.unimatter){
        if(output_inventory.can_add(0, unimatter)){
          output_inventory.add(0, unimatter);
          input_inventory.decrease(1);
          changed = true;
        }
      }
      else{
        if(input_inventory.getStackInSlot(0).getItem() == OverpoweredBlocks.black_hole.asItem()){
          matter += input.getCount();
          input_inventory.setStackInSlot(1, ItemStack.EMPTY);
          changed = true;
          final int max_matter = Config.max_matter.get();
          if(matter >= max_matter){
            if(output_inventory.can_add(0, unimatter)){
              output_inventory.add(0, unimatter);
              matter -= max_matter;
            }
          }
        }
      }
    }
    if(energy.tick()){
      changed = true;
    }
    if(changed){
      update_data();
      changed = false;
    }
  }

  @Override
  public final void onInventoryChanged(){
    changed = true;
  }

  /** Only used by the Gui. */
  public final String getMatter(){
    return Integer.toString(matter);
  }

  /** Only used by the Gui. */
  public final float getProgress(){
    return (float)matter / Config.max_matter.get();
  }

  @Override
  public final void load(final CompoundTag nbt){
    super.load(nbt);
    matter = nbt.getInt("Matter");
    energy.loadFromNBT(nbt);
  }

  @Override
  public final CompoundTag save(final CompoundTag nbt){
    super.save(nbt);
    nbt.putInt("Matter", matter);
    energy.saveToNBT(nbt);
    return nbt;
  }

  @Override
  public final Receiver getEnergy(){
    return energy;
  }

  @Override
  public AbstractContainerMenu createMenu(int id, Inventory player_inventory, Player player){
    return new MatterCompressorContainer(id, player_inventory, this);
  }
  
  @Override
  public Component getDisplayName(){
    return new TranslatableComponent(getBlockState().getBlock().getDescriptionId());
  }

}
