package addsynth.core.game.tiles;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import addsynth.core.game.inventory.IInputInventory;
import addsynth.core.game.inventory.IOutputInventory;
import addsynth.core.game.inventory.InputInventory;
import addsynth.core.game.inventory.InventoryUtil;
import addsynth.core.game.inventory.OutputInventory;
import addsynth.core.game.inventory.SlotData;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;

/** This is for TileEntities that have an Input Inventory and an Output Inventory,
 *  and possibly a Working Inventory as well. This is a machine that works on items
 *  without using any Energy.
 * @author ADDSynth
 */
public abstract class TileMachine extends TileBase implements IInputInventory, IOutputInventory {

  protected final InputInventory input_inventory;
  protected final OutputInventory output_inventory;

  public TileMachine(BlockEntityType type, BlockPos position, BlockState blockstate, SlotData[] slot_data, int output_slots){
    super(type, position, blockstate);
    input_inventory = InputInventory.create(this, slot_data);
    output_inventory = OutputInventory.create(this, output_slots);
  }

  @Override
  public void load(final CompoundTag nbt){
    super.load(nbt);
    if(input_inventory != null){ input_inventory.load(nbt);}
    if(output_inventory != null){ output_inventory.load(nbt);}
  }

  @Override
  public CompoundTag save(final CompoundTag nbt){
    super.save(nbt);
    if(input_inventory != null){ input_inventory.save(nbt);}
    if(output_inventory != null){ output_inventory.save(nbt);}
    return nbt;
  }

  @Override
  @Nonnull
  public <T> LazyOptional<T> getCapability(final @Nonnull Capability<T> capability, final @Nullable Direction side){
    if(remove == false){
      if(capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY){
        return InventoryUtil.getInventoryCapability(input_inventory, output_inventory, side);
      }
      return super.getCapability(capability, side);
    }
    return LazyOptional.empty();
  }

  @Override
  public void drop_inventory(){
    InventoryUtil.drop_inventories(worldPosition, level, input_inventory, output_inventory);
  }

  @Override
  public InputInventory getInputInventory(){
    return input_inventory;
  }

  @Override
  public OutputInventory getOutputInventory(){
    return output_inventory;
  }

}
