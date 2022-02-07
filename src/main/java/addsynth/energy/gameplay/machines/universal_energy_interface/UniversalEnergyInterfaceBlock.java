package addsynth.energy.gameplay.machines.universal_energy_interface;

import java.util.List;
import javax.annotation.Nullable;
import addsynth.core.util.game.MinecraftUtility;
import addsynth.core.util.game.tileentity.TileEntityUtil;
import addsynth.energy.ADDSynthEnergy;
import addsynth.energy.lib.blocks.MachineBlock;
import addsynth.energy.registers.Tiles;
import net.minecraft.core.BlockPos;
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
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.fmllegacy.network.NetworkHooks;

public final class UniversalEnergyInterfaceBlock extends MachineBlock {

  public UniversalEnergyInterfaceBlock(final String name){
    super(MaterialColor.WOOL);
    ADDSynthEnergy.registry.register_block(this, name, new Item.Properties().tab(ADDSynthEnergy.creative_tab));
  }

  @Override
  public final void appendHoverText(ItemStack stack, @Nullable BlockGetter worldIn, List<Component> tooltip, TooltipFlag flagIn){
    tooltip.add(new TranslatableComponent("gui.addsynth_energy.tooltip.energy_machine"));
  }

  @Override
  @Nullable
  public final BlockEntity newBlockEntity(BlockPos position, BlockState blockstate){
    return new TileUniversalEnergyInterface(position, blockstate);
  }

  @Override
  @Nullable
  public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level world, BlockState blockstate, BlockEntityType<T> type){
    if(world.isClientSide == false){
      return createTickerHelper(type, Tiles.UNIVERSAL_ENERGY_INTERFACE, TileEntityUtil::tick);
    }
    return null;
  }

  @Override
  @SuppressWarnings("deprecation")
  public final InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult hit){
    if(world.isClientSide == false){
      final TileUniversalEnergyInterface tile = MinecraftUtility.getTileEntity(pos, world, TileUniversalEnergyInterface.class);
      if(tile != null){
        NetworkHooks.openGui((ServerPlayer)player, tile, pos);
      }
    }
    return InteractionResult.SUCCESS;
  }

}
