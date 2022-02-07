package addsynth.overpoweredmod.items;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.item.ItemStack;

public final class FusionCore extends OverpoweredItem {

  public FusionCore(final String name){
    super(name);
  }

  @Override
  public Component getName(ItemStack stack){
    return ((MutableComponent)super.getName(stack)).withStyle(ChatFormatting.GOLD);
  }

}
