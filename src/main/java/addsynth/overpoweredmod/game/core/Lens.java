package addsynth.overpoweredmod.game.core;

import addsynth.core.game.items.ItemUtil;
import addsynth.overpoweredmod.Debug;
import addsynth.overpoweredmod.items.LensItem;
import net.minecraft.ChatFormatting;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.material.MaterialColor;

public enum Lens {

  WHITE  (0, "focus",   ChatFormatting.WHITE,        MaterialColor.SNOW),
  RED    (1, "red",     ChatFormatting.DARK_RED,     MaterialColor.COLOR_RED),
  ORANGE (2, "orange",  ChatFormatting.GOLD,         MaterialColor.COLOR_ORANGE),
  YELLOW (3, "yellow",  ChatFormatting.YELLOW,       MaterialColor.GOLD),
  GREEN  (4, "green",   ChatFormatting.DARK_GREEN,   MaterialColor.EMERALD),
  CYAN   (5, "cyan",    ChatFormatting.AQUA,         MaterialColor.DIAMOND),
  BLUE   (6, "blue",    ChatFormatting.BLUE,         MaterialColor.LAPIS),
  MAGENTA(7, "magenta", ChatFormatting.LIGHT_PURPLE, MaterialColor.COLOR_MAGENTA);

  static {
    Debug.log_setup_info("Begin loading Lens class...");
  }

  public final LensItem lens;
  public final MaterialColor color;

  private Lens(final int index, final String color, final ChatFormatting format_code, final MaterialColor material){
    lens = new LensItem(index, color+"_lens", format_code);
    this.color = material;
  }

  public static final Item focus_lens = WHITE.lens;
  public static final Item red        = RED.lens;
  public static final Item orange     = ORANGE.lens;
  public static final Item yellow     = YELLOW.lens;
  public static final Item green      = GREEN.lens;
  public static final Item cyan       = CYAN.lens;
  public static final Item blue       = BLUE.lens;
  public static final Item magenta    = MAGENTA.lens;

  public static final Item[] index = { focus_lens, red, orange, yellow, green, cyan, blue, magenta};
  
  public static final int get_index(final ItemStack stack){
    if(ItemUtil.itemStackExists(stack)){
      return get_index(stack.getItem());
    }
    return -1;
  }
  
  public static final int get_index(final Item lens){
    if(lens != null){
      if(lens instanceof LensItem){
        return ((LensItem)lens).index;
      }
    }
    return -1;
  }
  
  static {
    Debug.log_setup_info("Finished loading Lens class.");
  }

}
