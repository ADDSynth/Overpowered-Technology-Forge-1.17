package addsynth.core.game.inventory.filter;

import java.util.function.Predicate;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.Tag;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.Tags.Items;

/** This filter uses Item tags. Pass in any number of tags.
 *  You can use tags from {@link ItemTags} or {@link Items Tags.Items}
 *  or your own defined tags. Tags are automatically updated when
 *  resources are reloaded or when clients join a world.
 */
public final class TagFilter implements Predicate<ItemStack> {

  private final Tag.Named<Item>[] tag_filter;

  @SafeVarargs
  public TagFilter(final Tag.Named<Item> ... tags){
    this.tag_filter = tags;
  }

  @Override
  public final boolean test(final ItemStack stack){
    final Item item = stack.getItem();
    for(final Tag.Named<Item> item_tag : tag_filter){
      if(item_tag.contains(item)){
        return true;
      }
    }
    return false;
  }

}
