package addsynth.overpoweredmod.machines.energy_extractor;

import java.util.List;
import javax.annotation.Nullable;
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
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.fmllegacy.network.NetworkHooks;

public final class CrystalEnergyExtractorBlock extends MachineBlock {

  public CrystalEnergyExtractorBlock(final String name){
    super(MaterialColor.COLOR_BLACK);
    OverpoweredTechnology.registry.register_block(this, name, new Item.Properties().tab(CreativeTabs.creative_tab));
  }

  @Override
  public final void appendHoverText(ItemStack stack, @Nullable BlockGetter worldIn, List<Component> tooltip, TooltipFlag flagIn){
    tooltip.add(new TranslatableComponent("gui.addsynth_energy.tooltip.energy_source"));
  }

  @Override
  @Nullable
  public final BlockEntity newBlockEntity(BlockPos position, BlockState blockstate){
    return new TileCrystalEnergyExtractor(position, blockstate);
  }

  /** @deprecated Call via {@link BlockState#onBlockActivated(Level, Player, InteractionHand, BlockHitResult)} whenever possible.
    * Implementing/overriding is fine.
  */
  @Override
  public final InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult hit){
    if(world.isClientSide == false){
      final TileCrystalEnergyExtractor tile = MinecraftUtility.getTileEntity(pos, world, TileCrystalEnergyExtractor.class);
      if(tile != null){
        NetworkHooks.openGui((ServerPlayer)player, tile, pos);
      }
    }
    return InteractionResult.SUCCESS;
  }

}
