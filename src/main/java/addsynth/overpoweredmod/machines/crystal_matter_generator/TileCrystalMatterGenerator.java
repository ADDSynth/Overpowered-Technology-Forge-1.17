package addsynth.overpoweredmod.machines.crystal_matter_generator;

import java.util.Random;
import javax.annotation.Nullable;
import addsynth.core.game.inventory.IOutputInventory;
import addsynth.core.game.inventory.OutputInventory;
import addsynth.energy.lib.tiles.machines.TilePassiveMachine;
import addsynth.overpoweredmod.config.MachineValues;
import addsynth.overpoweredmod.game.core.Gems;
import addsynth.overpoweredmod.registers.Tiles;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;

public final class TileCrystalMatterGenerator extends TilePassiveMachine implements IOutputInventory, MenuProvider {

  private final OutputInventory output_inventory;

  public TileCrystalMatterGenerator(BlockPos position, BlockState blockstate){
    super(Tiles.CRYSTAL_MATTER_REPLICATOR, position, blockstate, MachineValues.crystal_matter_generator);
    output_inventory = OutputInventory.create(this, 8);
  }

  @Override
  protected final void perform_work(){
    final int slot = (new Random()).nextInt(8);
    final ItemStack stack = Gems.getShard(slot);
    output_inventory.insertItem(slot, stack, false);
  }

  @Override
  @Nullable
  public AbstractContainerMenu createMenu(int id, Inventory player_inventory, Player player){
    return new ContainerCrystalGenerator(id, player_inventory, this);
  }

  @Override
  public Component getDisplayName(){
    return getBlockState().getBlock().getName();
  }

  @Override
  public void onInventoryChanged(){
    // no need to react to inventory change
  }

  @Override
  public void drop_inventory(){
    output_inventory.drop_in_world(level, worldPosition);
  }

  @Override
  public OutputInventory getOutputInventory(){
    return output_inventory;
  }

}
