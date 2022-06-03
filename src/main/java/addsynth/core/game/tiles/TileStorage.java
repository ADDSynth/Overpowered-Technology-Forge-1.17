package addsynth.core.game.tiles;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import addsynth.core.game.inventory.CommonInventory;
import addsynth.core.game.inventory.IStorageInventory;
import addsynth.core.game.inventory.InventoryUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;

/** This is a TileEntity that has a single storage inventory.
 *  It has no Item filter and machines can insert and extract items. */
public abstract class TileStorage extends TileBase implements IStorageInventory {

  protected final CommonInventory inventory;

  public TileStorage(final BlockEntityType type, BlockPos position, BlockState blockstate, final int number_of_slots){
    super(type, position, blockstate);
    this.inventory = CommonInventory.create(this, number_of_slots);
  }

  @Override
  public void load(final CompoundTag nbt){
    super.load(nbt);
    if(inventory != null){inventory.load(nbt);}
  }

  @Override
  public CompoundTag save(final CompoundTag nbt){
    super.save(nbt);
    if(inventory != null){inventory.save(nbt);}
    return nbt;
  }

  @Override
  @Nonnull
  public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> capability, @Nullable Direction direction){
    if(remove == false){
      if(capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY){
        return InventoryUtil.getInventoryCapability(inventory);
      }
      return super.getCapability(capability, direction);
    }
    return LazyOptional.empty();
  }

  @Override
  public void onInventoryChanged(){
    update_data();
  }

  @Override
  public void drop_inventory(){
    InventoryUtil.drop_inventories(worldPosition, level, inventory);
  }

  @Override
  public CommonInventory getInventory(){
    return inventory;
  }

}
