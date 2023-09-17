package addsynth.energy.gameplay.machines.energy_storage;

import javax.annotation.Nullable;
import addsynth.energy.gameplay.config.Config;
import addsynth.energy.lib.main.Energy;
import addsynth.energy.lib.tiles.energy.TileEnergyBattery;
import addsynth.energy.registers.Tiles;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.block.state.BlockState;

public final class TileEnergyStorage extends TileEnergyBattery implements MenuProvider {

  public TileEnergyStorage(BlockPos position, BlockState blockstate){
    super(Tiles.ENERGY_CONTAINER, position, blockstate,
      new Energy(
        Config.energy_storage.getCapacity(),
        Config.energy_storage.getMaxTransferRate()
      )
    );
  }

  @Override
  @Nullable
  public AbstractContainerMenu createMenu(int id, Inventory player_inventory, Player player){
    return new ContainerEnergyStorage(id, player_inventory, this);
  }

  @Override
  public Component getDisplayName(){
    return getBlockState().getBlock().getName();
  }

}
