package addsynth.overpoweredmod.machines.laser.machine;

import java.util.List;
import javax.annotation.Nullable;
import addsynth.core.block_network.BlockNetworkUtil;
import addsynth.core.util.game.MinecraftUtility;
import addsynth.energy.lib.blocks.MachineBlock;
import addsynth.overpoweredmod.OverpoweredTechnology;
import addsynth.overpoweredmod.assets.CreativeTabs;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.fmllegacy.network.NetworkHooks;

public final class LaserHousingBlock extends MachineBlock {

  public LaserHousingBlock(final String name){
    super(MaterialColor.SNOW);
    OverpoweredTechnology.registry.register_block(this, name, new Item.Properties().tab(CreativeTabs.creative_tab));
  }

  @Override
  public final void appendHoverText(ItemStack stack, @Nullable BlockGetter worldIn, List<Component> tooltip, TooltipFlag flagIn){
    tooltip.add(new TranslatableComponent("gui.overpowered.tooltip.laser_machine"));
  }

  @Override
  @Nullable
  public final BlockEntity newBlockEntity(BlockPos position, BlockState blockstate){
    return new TileLaserHousing(position, blockstate);
  }

  @Override
  @SuppressWarnings("deprecation")
  public final InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult hit){
    if(world.isClientSide == false){
      final TileLaserHousing tile = MinecraftUtility.getTileEntity(pos, world, TileLaserHousing.class);
      if(tile != null){
        final LaserNetwork network = tile.getBlockNetwork();
        if(network != null){
          network.updateClient();
          NetworkHooks.openGui((ServerPlayer)player, tile, pos);
        }
        else{
          OverpoweredTechnology.log.error(new NullPointerException("Laser Machine at "+pos.toString()+" has no LaserNetwork!"));
        }
      }
    }
    return InteractionResult.SUCCESS;
  }

  @Override
  @SuppressWarnings("deprecation")
  public final void neighborChanged(BlockState state, Level world, BlockPos pos, Block blockIn, BlockPos neighbor, boolean isMoving){
    BlockNetworkUtil.neighbor_changed(world, pos, neighbor);
  }

  @Override
  public void setPlacedBy(Level world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack){
    if(placer instanceof ServerPlayer){ // ensures block was placed by player and on server side
      final TileLaserHousing tile = MinecraftUtility.getTileEntity(pos, world, TileLaserHousing.class);
      if(tile != null){
        tile.setPlayer((ServerPlayer)placer);
      }
    }
  }

}
