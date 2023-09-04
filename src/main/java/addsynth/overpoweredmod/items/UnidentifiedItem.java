package addsynth.overpoweredmod.items;

import javax.annotation.Nonnull;
import addsynth.core.game.item.constants.ArmorMaterial;
import addsynth.core.game.item.constants.EquipmentType;
import addsynth.overpoweredmod.OverpoweredTechnology;
import addsynth.overpoweredmod.game.reference.Names;
import addsynth.overpoweredmod.items.register.OverpoweredItem;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

public final class UnidentifiedItem extends OverpoweredItem {

  public final int ring_id;
  public final ArmorMaterial armor_material;
  public final EquipmentType equipment_type;

  /** Use this constructor to create unidentified rings. */
  public UnidentifiedItem(final int ring_id){
    super(Names.UNIDENTIFIED_RING[ring_id]);
    this.ring_id = ring_id;
    armor_material = null;
    equipment_type = null;
  }

  /** Use this constructor to create unidentified armor pieces. */
  public UnidentifiedItem(@Nonnull final ArmorMaterial material, @Nonnull final EquipmentType type){
    super(new ResourceLocation(OverpoweredTechnology.MOD_ID, "unidentified_"+material.name+"_"+type.name));
    this.ring_id = -1;
    this.armor_material = material;
    this.equipment_type = type;
  }

  @Override
  public final Component getName(final ItemStack stack){
    return ((MutableComponent)super.getName(stack)).withStyle(ChatFormatting.ITALIC);
  }

}
