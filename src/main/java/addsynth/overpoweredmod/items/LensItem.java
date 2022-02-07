package addsynth.overpoweredmod.items;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.item.ItemStack;

public final class LensItem extends OverpoweredItem {

  public final int index;
  private final ChatFormatting color_code;
  // https://minecraft.gamepedia.com/Formatting_codes

  public LensItem(final int index, final String name, final ChatFormatting format_code){
    super(name);
    this.index = index;
    color_code = format_code;
  }

  @Override
  public Component getName(final ItemStack stack){
    return ((MutableComponent)super.getName(stack)).withStyle(color_code);
  }

}
