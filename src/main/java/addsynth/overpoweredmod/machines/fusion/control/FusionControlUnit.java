package addsynth.overpoweredmod.machines.fusion.control;

import java.util.List;
import javax.annotation.Nullable;
import addsynth.energy.lib.blocks.MachineBlock;
import addsynth.overpoweredmod.OverpoweredTechnology;
import addsynth.overpoweredmod.assets.CreativeTabs;
import addsynth.overpoweredmod.machines.data_cable.DataCable;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.MaterialColor;

public final class FusionControlUnit extends MachineBlock {

  public FusionControlUnit(final String name){
    super(MaterialColor.WOOL);
    OverpoweredTechnology.registry.register_block(this, name, new Item.Properties().tab(CreativeTabs.creative_tab));
    DataCable.addAttachableBlock(this);
  }

  @Override
  public final void appendHoverText(ItemStack stack, @Nullable BlockGetter worldIn, List<Component> tooltip, TooltipFlag flagIn){
    tooltip.add(new TranslatableComponent("gui.overpowered.tooltip.fusion_machine"));
  }

  @Override
  @Nullable
  public BlockEntity newBlockEntity(BlockPos p_153215_, BlockState p_153216_){
    return null;
  }

}
