package addsynth.overpoweredmod.items;

import addsynth.core.items.ArmorMaterial;
import addsynth.core.items.EquipmentType;
import addsynth.overpoweredmod.assets.CreativeTabs;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.item.ItemStack;

public final class UnidentifiedItem extends OverpoweredItem {

  public final ArmorMaterial armor_material;
  public final EquipmentType equipment_type;

  public UnidentifiedItem(final ArmorMaterial material, final EquipmentType type){
    super("unidentified_"+material.name+"_"+type.name, CreativeTabs.tools_creative_tab);
    this.armor_material = material;
    this.equipment_type = type;
  }

  @Override
  public final Component getName(final ItemStack stack){
    return ((MutableComponent)super.getName(stack)).withStyle(ChatFormatting.ITALIC);
  }

}
