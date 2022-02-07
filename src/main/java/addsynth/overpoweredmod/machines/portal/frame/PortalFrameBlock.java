package addsynth.overpoweredmod.machines.portal.frame;

import javax.annotation.Nullable;
import addsynth.core.util.game.MinecraftUtility;
import addsynth.energy.lib.blocks.MachineBlock;
import addsynth.overpoweredmod.OverpoweredTechnology;
import addsynth.overpoweredmod.assets.CreativeTabs;
import addsynth.overpoweredmod.machines.data_cable.DataCable;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.fmllegacy.network.NetworkHooks;

public final class PortalFrameBlock extends MachineBlock {

  public PortalFrameBlock(final String name){
    super(MaterialColor.WOOL);
    OverpoweredTechnology.registry.register_block(this, name, new Item.Properties().tab(CreativeTabs.creative_tab));
    DataCable.addAttachableBlock(this);
  }

  @Override
  @Nullable
  public final BlockEntity newBlockEntity(BlockPos position, BlockState blockstate){
    return new TilePortalFrame(position, blockstate);
  }

  @Override
  @SuppressWarnings("deprecation")
  public final InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult hit){
    if(world.isClientSide == false){
      final TilePortalFrame tile = MinecraftUtility.getTileEntity(pos, world, TilePortalFrame.class);
      if(tile != null){
        NetworkHooks.openGui((ServerPlayer)player, tile, pos);
      }
    }
    return InteractionResult.SUCCESS;
  }

}
