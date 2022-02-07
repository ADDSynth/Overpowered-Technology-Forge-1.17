package addsynth.overpoweredmod.items;

import net.minecraft.world.item.ItemStack;

public final class VoidCrystal extends OverpoweredItem {

  public VoidCrystal(final String name){
    super(name);
  }

  @Override
  public boolean isFoil(ItemStack stack){
    return true;
  }

}
