package addsynth.overpoweredmod.items.tools;

import addsynth.overpoweredmod.assets.CreativeTabs;
import addsynth.overpoweredmod.game.Names;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;

public class NullAxe extends AxeItem {

  public NullAxe(){
    super(OverpoweredTiers.VOID, 14.0f, -3.0f, new Item.Properties().tab(CreativeTabs.tools_creative_tab));
    setRegistryName(Names.VOID_AXE);
  }

  @Override
  public boolean canBeDepleted(){ // TODO: delete this, Null Tools can be used up
    return false;
  }

  @Override
  public boolean isFoil(ItemStack stack){
      return true;
  }
  
  @Override
  public boolean isEnchantable(ItemStack stack){
    return false;
  }

  @Override
  public Rarity getRarity(ItemStack stack){
    return Rarity.EPIC;
  }

}
