package addsynth.overpoweredmod.items.tools;

import addsynth.overpoweredmod.OverpoweredTechnology;
import addsynth.overpoweredmod.assets.CreativeTabs;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;

public class NullAxe extends AxeItem {

  public NullAxe(final String name){
    super(OverpoweredTiers.VOID, 14.0f, -3.0f, new Item.Properties().tab(CreativeTabs.tools_creative_tab));
    OverpoweredTechnology.registry.register_item(this, name);
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
