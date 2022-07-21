package addsynth.overpoweredmod.game.core;

import addsynth.core.game.items.ArmorMaterial;
import addsynth.core.game.items.EquipmentType;
import addsynth.core.game.items.Toolset;
import addsynth.overpoweredmod.Debug;
import addsynth.overpoweredmod.compatability.CompatabilityManager;
import addsynth.overpoweredmod.items.tools.*;
import addsynth.overpoweredmod.items.Ring;
import addsynth.overpoweredmod.items.UnidentifiedItem;
import net.minecraft.world.item.Item;

public final class Tools {

  static {
    Debug.log_setup_info("Begin loading Tools class...");
  }

  public static final Toolset overpowered_tools = new Toolset( // MAYBE: should I automatically assign tools their names? and just provide the base name? I should also pass the Mod ID to register translation keys as well.
    new OverpoweredSword("celestial_sword"),
    new OverpoweredShovel("celestial_shovel"),
    new OverpoweredPickaxe("celestial_pickaxe"),
    new OverpoweredAxe("celestial_axe"),
    new OverpoweredHoe("celestial_hoe"),
    Init.celestial_gem
  );
  
  public static final Toolset void_toolset = new Toolset(
    new NullSword("void_sword"),
    new NullShovel("void_shovel"),
    new NullPickaxe("void_pickaxe"),
    new NullAxe("void_axe"),
    new NullHoe("void_hoe"),
    Init.void_crystal
  );

  public static final Item[][] unidentified_armor = new Item[5][4];

  static {
    for(ArmorMaterial material : ArmorMaterial.values()){
      for(EquipmentType type : EquipmentType.values()){
        unidentified_armor[material.ordinal()][type.ordinal()] = new UnidentifiedItem(material, type);
      }
    }
  }

  public static final Item[] ring = CompatabilityManager.are_rings_enabled() ? new Item[] {
    new UnidentifiedItem(0),
    new UnidentifiedItem(1),
    new UnidentifiedItem(2),
    new UnidentifiedItem(3)
  } : null;

  public static final Item[] magic_ring = CompatabilityManager.are_rings_enabled() ? new Item[] {
    new Ring(0),
    new Ring(1),
    new Ring(2),
    new Ring(3)
  } : null;

  static {
    Debug.log_setup_info("Finished loading Tools class.");
  }

}
