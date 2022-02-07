package addsynth.core.gameplay.items;

import addsynth.core.ADDSynthCore;
import net.minecraft.world.item.Item;

public class CoreItem extends Item {

  public CoreItem(final String name){
    super(new Item.Properties().tab(ADDSynthCore.creative_tab));
    ADDSynthCore.registry.register_item(this, name);
  }

  public CoreItem(final Item.Properties properties, final String name){
    super(properties.tab(ADDSynthCore.creative_tab));
    ADDSynthCore.registry.register_item(this, name);
  }

}
