package addsynth.energy.gameplay.machines.compressor;

import javax.annotation.Nullable;
import addsynth.energy.gameplay.Config;
import addsynth.energy.gameplay.machines.compressor.recipe.CompressorRecipes;
import addsynth.energy.lib.tiles.machines.TileStandardWorkMachine;
import addsynth.energy.registers.Tiles;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.block.state.BlockState;

public final class TileCompressor extends TileStandardWorkMachine implements MenuProvider {

  public TileCompressor(BlockPos position, BlockState blockstate){
    super(Tiles.COMPRESSOR, position, blockstate, 1, CompressorRecipes.INSTANCE.getFilter(), 1, Config.compressor_data);
    inventory.setRecipeProvider(CompressorRecipes.INSTANCE);
  }

  @Override
  protected final void perform_work(){
    level.playSound(null, worldPosition, SoundEvents.ANVIL_PLACE, SoundSource.BLOCKS, 0.7f, 0.5f); // lowest pitch can be
    inventory.finish_work();
  }

  @Override
  @Nullable
  public AbstractContainerMenu createMenu(int id, Inventory player_inventory, Player player){
    return new ContainerCompressor(id, player_inventory, this);
  }

  @Override
  public Component getDisplayName(){
    return new TranslatableComponent(getBlockState().getBlock().getDescriptionId());
  }

}
