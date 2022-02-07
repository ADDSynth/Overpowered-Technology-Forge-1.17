package addsynth.overpoweredmod.machines.inverter;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import addsynth.energy.lib.tiles.machines.TileStandardWorkMachine;
import addsynth.overpoweredmod.config.MachineValues;
import addsynth.overpoweredmod.game.core.Init;
import addsynth.overpoweredmod.registers.Tiles;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;

public final class TileInverter extends TileStandardWorkMachine implements MenuProvider {

  public static final Item[] input_filter = new Item[] {Init.energy_crystal, Init.void_crystal};

  public TileInverter(BlockPos position, BlockState blockstate){
    super(Tiles.INVERTER, position, blockstate, 1, input_filter, 1, MachineValues.inverter);
    inventory.setRecipeProvider(TileInverter::getInverted);
  }

  @Nonnull
  public static final ItemStack getInverted(final ItemStack input_stack){
    final Item item = input_stack.getItem();
    if(item == Init.energy_crystal){ return new ItemStack(Init.void_crystal,   1); }
    if(item == Init.void_crystal){   return new ItemStack(Init.energy_crystal, 1); }
    return ItemStack.EMPTY;
  }

  @Override
  @Nullable
  public AbstractContainerMenu createMenu(int id, Inventory player_inventory, Player player){
    return new ContainerInverter(id, player_inventory, this);
  }

  @Override
  public Component getDisplayName(){
    return new TranslatableComponent(getBlockState().getBlock().getDescriptionId());
  }

}
