package addsynth.overpoweredmod.items.tools;

import addsynth.core.game.items.ToolConstants;
import addsynth.overpoweredmod.OverpoweredTechnology;
import addsynth.overpoweredmod.assets.CreativeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.Rarity;

public class OverpoweredPickaxe extends PickaxeItem {

  public OverpoweredPickaxe(final String name){
    super(OverpoweredTiers.CELESTIAL_PICKAXE, ToolConstants.pickaxe_damage, ToolConstants.pickaxe_speed,
      new Item.Properties().tab(CreativeTabs.tools_creative_tab));
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
