package addsynth.overpoweredmod.items;

import addsynth.overpoweredmod.game.Names;
import net.minecraft.world.item.ItemStack;

public final class VoidCrystal extends OverpoweredItem {

  public VoidCrystal(){
    super(Names.VOID_CRYSTAL);
  }

  @Override
  public boolean isFoil(ItemStack stack){
    return true;
  }

}
