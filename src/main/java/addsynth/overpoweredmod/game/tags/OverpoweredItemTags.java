package addsynth.overpoweredmod.game.tags;

import addsynth.overpoweredmod.OverpoweredTechnology;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.Tag;
import net.minecraft.world.item.Item;

public final class OverpoweredItemTags {

  public static final Tag.Named<Item> advanced_ore_refinery = create("advanced_ore_refinery_recipes");
  /** An Item Tag that contains all of the 8 different types of gems.
   *  Since this is a tag, it will automatically be updated when resources are reloaded. */
  public static final Tag.Named<Item> convertable_gems      = create("convertable_gems");
  public static final Tag.Named<Item> portal_fuel           = create("portal_fuel");

  private static final Tag.Named<Item> create(final String name){
    return ItemTags.bind(OverpoweredTechnology.MOD_ID+":"+name);
  }

}
