package addsynth.core.game.inventory.filter;

import java.util.function.Predicate;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

/** Allows you to pass in any number of Items to use as a filter. */
public final class BasicFilter implements Predicate<ItemStack> {

  private final Item[] filter;

  @SafeVarargs
  public BasicFilter(final Item ... input){
    this.filter = input;
  }

  @Override
  public final boolean test(final ItemStack stack){
    final Item item = stack.getItem();
    for(final Item holder : filter){
      if(holder == item){
        return true;
      }
    }
    return false;
  }

}
