package addsynth.overpoweredmod.items.tools;

import addsynth.core.game.items.ToolConstants;
import addsynth.overpoweredmod.OverpoweredTechnology;
import addsynth.overpoweredmod.assets.CreativeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.ShovelItem;

public final class OverpoweredShovel extends ShovelItem {

  public OverpoweredShovel(final String name){
    super(OverpoweredTiers.CELESTIAL, ToolConstants.axe_damage, ToolConstants.axe_speed, new Item.Properties().tab(CreativeTabs.tools_creative_tab));
    OverpoweredTechnology.registry.register_item(this, name);
  }

  @Override
  public boolean isEnchantable(ItemStack stack){
    return false;
  }

  @Override
  public Rarity getRarity(ItemStack stack){
    return Rarity.RARE;
  }
}
