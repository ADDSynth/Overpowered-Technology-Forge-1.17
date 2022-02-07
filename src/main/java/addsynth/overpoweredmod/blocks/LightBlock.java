package addsynth.overpoweredmod.blocks;

import java.text.NumberFormat;
import java.util.List;
import javax.annotation.Nullable;
import addsynth.overpoweredmod.OverpoweredTechnology;
import addsynth.overpoweredmod.assets.CreativeTabs;
import addsynth.overpoweredmod.config.MachineValues;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;

public final class LightBlock extends Block {

  public LightBlock(final String name){
    super(Block.Properties.of(Material.STONE, MaterialColor.QUARTZ).lightLevel((blockstate)->{return 15;}).strength(5.0f, 6.0f).requiresCorrectToolForDrops());
    OverpoweredTechnology.registry.register_block(this, name, new Item.Properties().tab(CreativeTabs.creative_tab));
  }

  @Override
  public void appendHoverText(ItemStack stack, @Nullable BlockGetter worldIn, List<Component> tooltip, TooltipFlag flagIn){
    final String energy = NumberFormat.getIntegerInstance().format(MachineValues.light_block_energy.get());
    tooltip.add(new TranslatableComponent("gui.addsynth_energy.tooltip.energy", energy).withStyle(ChatFormatting.AQUA));
  }

}
