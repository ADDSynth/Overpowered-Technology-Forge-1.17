package addsynth.energy.gameplay.machines.generator;

import javax.annotation.Nullable;
import addsynth.energy.lib.tiles.energy.TileStandardGenerator;
import addsynth.energy.registers.Tiles;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.ForgeHooks;

public final class TileGenerator extends TileStandardGenerator implements MenuProvider {

  public TileGenerator(BlockPos position, BlockState blockstate){
    super(Tiles.GENERATOR, position, blockstate, null);
    input_inventory.isItemStackValid = (Integer slot, ItemStack stack) -> {
      return AbstractFurnaceBlockEntity.isFuel(stack) && stack.getItem() != Items.LAVA_BUCKET;
    };
  }

  @Override
  protected void setGeneratorData(){
    final ItemStack item = input_inventory.extractItem(0, 1, false);
    final int burn_time = ForgeHooks.getBurnTime(item, RecipeType.SMELTING);
    if(burn_time > 0){
      // 1 Coal/Charcoal should provide 8,000 units of energy and take 80 seconds to use up.
      energy.setEnergyAndCapacity(burn_time * 5);
      // Therefore, we should use up 5 energy each tick.
      energy.setMaxExtract(Math.max(5, (double)burn_time / 320));
    }
  }

  @Override
  @Nullable
  public AbstractContainerMenu createMenu(int id, Inventory player_inventory, Player player){
    return new ContainerGenerator(id, player_inventory, this);
  }

  @Override
  public Component getDisplayName(){
    return new TranslatableComponent(getBlockState().getBlock().getDescriptionId());
  }

}
