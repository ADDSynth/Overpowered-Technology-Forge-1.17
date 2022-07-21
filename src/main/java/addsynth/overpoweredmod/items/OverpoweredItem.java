package addsynth.overpoweredmod.items;

import addsynth.overpoweredmod.OverpoweredTechnology;
import addsynth.overpoweredmod.assets.CreativeTabs;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;

public class OverpoweredItem extends Item {

  public OverpoweredItem(final String name){
    this(name, CreativeTabs.creative_tab);
  }

  public OverpoweredItem(final String name, final CreativeModeTab tab){
    super(new Item.Properties().tab(tab));
    OverpoweredTechnology.registry.register_item(this, name);
  }

  public OverpoweredItem(final String name, final Item.Properties properties){
    super(properties);
    OverpoweredTechnology.registry.register_item(this, name);
  }

}
