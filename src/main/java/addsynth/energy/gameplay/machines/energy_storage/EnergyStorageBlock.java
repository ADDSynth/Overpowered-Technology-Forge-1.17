package addsynth.energy.gameplay.machines.energy_storage;

import java.util.List;
import javax.annotation.Nullable;
import addsynth.core.util.game.MinecraftUtility;
import addsynth.energy.ADDSynthEnergy;
import addsynth.energy.lib.blocks.MachineBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.fmllegacy.network.NetworkHooks;

public final class EnergyStorageBlock extends MachineBlock {

  public EnergyStorageBlock(final String name){
    super(Block.Properties.of(Material.METAL, MaterialColor.SNOW).noOcclusion().strength(3.5f, 6.0f));
    ADDSynthEnergy.registry.register_block(this, name, new Item.Properties().tab(ADDSynthEnergy.creative_tab));
  }

  @Override
  public final void appendHoverText(ItemStack stack, @Nullable BlockGetter worldIn, List<Component> tooltip, TooltipFlag flagIn){
    tooltip.add(new TranslatableComponent("gui.addsynth_energy.tooltip.battery"));
  }

  @Override
  @Nullable
  public final BlockEntity newBlockEntity(BlockPos position, BlockState blockstate){
    return new TileEnergyStorage(position, blockstate);
  }

  @Override
  @SuppressWarnings("deprecation")
  public float getShadeBrightness(BlockState state, BlockGetter worldIn, BlockPos pos){
    return 1.0f;
  }

  @Override
  @SuppressWarnings("deprecation")
  public final InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult hit){
    if(world.isClientSide == false){
      final TileEnergyStorage tile = MinecraftUtility.getTileEntity(pos, world, TileEnergyStorage.class);
      if(tile != null){
        NetworkHooks.openGui((ServerPlayer)player, tile, pos);
      }
    }
    return InteractionResult.SUCCESS;
  }

  @Override
  @SuppressWarnings("deprecation")
  public final boolean skipRendering(BlockState state, BlockState adjacentBlockState, Direction side){
    return adjacentBlockState.getBlock() == this ? true : super.skipRendering(state, adjacentBlockState, side);
  }

}
