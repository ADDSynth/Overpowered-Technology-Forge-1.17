package addsynth.overpoweredmod.assets;

import addsynth.core.game.item.constants.ArmorMaterial;
import addsynth.overpoweredmod.OverpoweredTechnology;
import addsynth.overpoweredmod.compatability.CompatabilityManager;
import addsynth.overpoweredmod.config.Config;
import addsynth.overpoweredmod.config.Features;
import addsynth.overpoweredmod.game.reference.OverpoweredItems;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.predicates.LootItemKilledByPlayerCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = OverpoweredTechnology.MOD_ID)
public final class LootTables {

// https://mcforge.readthedocs.io/en/latest/items/loot_tables/
// https://minecraft.gamepedia.com/Loot_table
// https://minecraft.gamepedia.com/Status_effect#Luck
// https://github.com/Vazkii/Botania/blob/master/src/main/java/vazkii/botania/common/core/loot/LootHandler.java

  private static final float default_spawn_chance = 1.0f / 100.0f; // before, it was just with rings and it was 1 / 15 chance.
  private static final float spawn_chance = default_spawn_chance;

  private static final int armor_weight = 3;
  private static final int leather_weight   = armor_weight * 10;
  private static final int gold_weight      = armor_weight * 6;
  private static final int chainmail_weight = armor_weight * 3;
  private static final int iron_weight      = armor_weight * 3;
  private static final int diamond_weight   = armor_weight * 1;
  private static final int ring_weight      = 1;
  /*
  Without Rings:
      Leather: 10 / 23 (43%)
         Gold:  6 / 23 (26%)
    Chainmail:  3 / 23 (13%)
         Iron:  3 / 23 (13%)
      Diamond:  1 / 23 ( 4%)
  With Rings:
    Armor: 69 / 73 (95%)
        Leather: 30 / 73 (41%)
           Gold: 18 / 73 (25%)
      Chainmail:  9 / 73 (12%)
           Iron:  9 / 73 (12%)
        Diamond:  3 / 73 ( 4%)
    Rings:  4 / 73 (5%)
  You get 1 custom loot drop every 100 mobs on average.
  You get 1 Ring every 1,825 mobs on average.
  */

  private static final LootPool custom_loot_pool = build_loot_pool();
  
  private static final LootPool build_loot_pool(){
    final LootPool.Builder loot = new LootPool.Builder();
    loot.add(LootItem.lootTableItem(OverpoweredItems.unidentified_armor[ArmorMaterial.LEATHER.ordinal()][0]).setWeight(leather_weight));
    loot.add(LootItem.lootTableItem(OverpoweredItems.unidentified_armor[ArmorMaterial.LEATHER.ordinal()][1]).setWeight(leather_weight));
    loot.add(LootItem.lootTableItem(OverpoweredItems.unidentified_armor[ArmorMaterial.LEATHER.ordinal()][2]).setWeight(leather_weight));
    loot.add(LootItem.lootTableItem(OverpoweredItems.unidentified_armor[ArmorMaterial.LEATHER.ordinal()][3]).setWeight(leather_weight));
    loot.add(LootItem.lootTableItem(OverpoweredItems.unidentified_armor[ArmorMaterial.GOLD.ordinal()][0]).setWeight(gold_weight));
    loot.add(LootItem.lootTableItem(OverpoweredItems.unidentified_armor[ArmorMaterial.GOLD.ordinal()][1]).setWeight(gold_weight));
    loot.add(LootItem.lootTableItem(OverpoweredItems.unidentified_armor[ArmorMaterial.GOLD.ordinal()][2]).setWeight(gold_weight));
    loot.add(LootItem.lootTableItem(OverpoweredItems.unidentified_armor[ArmorMaterial.GOLD.ordinal()][3]).setWeight(gold_weight));
    loot.add(LootItem.lootTableItem(OverpoweredItems.unidentified_armor[ArmorMaterial.CHAINMAIL.ordinal()][0]).setWeight(chainmail_weight));
    loot.add(LootItem.lootTableItem(OverpoweredItems.unidentified_armor[ArmorMaterial.CHAINMAIL.ordinal()][1]).setWeight(chainmail_weight));
    loot.add(LootItem.lootTableItem(OverpoweredItems.unidentified_armor[ArmorMaterial.CHAINMAIL.ordinal()][2]).setWeight(chainmail_weight));
    loot.add(LootItem.lootTableItem(OverpoweredItems.unidentified_armor[ArmorMaterial.CHAINMAIL.ordinal()][3]).setWeight(chainmail_weight));
    loot.add(LootItem.lootTableItem(OverpoweredItems.unidentified_armor[ArmorMaterial.IRON.ordinal()][0]).setWeight(iron_weight));
    loot.add(LootItem.lootTableItem(OverpoweredItems.unidentified_armor[ArmorMaterial.IRON.ordinal()][1]).setWeight(iron_weight));
    loot.add(LootItem.lootTableItem(OverpoweredItems.unidentified_armor[ArmorMaterial.IRON.ordinal()][2]).setWeight(iron_weight));
    loot.add(LootItem.lootTableItem(OverpoweredItems.unidentified_armor[ArmorMaterial.IRON.ordinal()][3]).setWeight(iron_weight));
    loot.add(LootItem.lootTableItem(OverpoweredItems.unidentified_armor[ArmorMaterial.DIAMOND.ordinal()][0]).setWeight(diamond_weight));
    loot.add(LootItem.lootTableItem(OverpoweredItems.unidentified_armor[ArmorMaterial.DIAMOND.ordinal()][1]).setWeight(diamond_weight));
    loot.add(LootItem.lootTableItem(OverpoweredItems.unidentified_armor[ArmorMaterial.DIAMOND.ordinal()][2]).setWeight(diamond_weight));
    loot.add(LootItem.lootTableItem(OverpoweredItems.unidentified_armor[ArmorMaterial.DIAMOND.ordinal()][3]).setWeight(diamond_weight));
    if(CompatabilityManager.are_rings_enabled()){
      loot.add(LootItem.lootTableItem(OverpoweredItems.ring_0).setWeight(ring_weight));
      loot.add(LootItem.lootTableItem(OverpoweredItems.ring_1).setWeight(ring_weight));
      loot.add(LootItem.lootTableItem(OverpoweredItems.ring_2).setWeight(ring_weight));
      loot.add(LootItem.lootTableItem(OverpoweredItems.ring_3).setWeight(ring_weight));
    }
    loot.when(LootItemKilledByPlayerCondition.killedByPlayer());
    loot.when(LootItemRandomChanceCondition.randomChance(spawn_chance));
    loot.name("overpowered_custom_loot_table");
    return loot.build();
  }

  private static final boolean debug_loot_tables = false;
  
  @SubscribeEvent
  public static final void inject_loot(final LootTableLoadEvent event){
    if(Features.identifier.get()){
      final String prefix = "minecraft:entities/";
      final String name = event.getName().toString();
      if(name.startsWith(prefix)){
        if(debug_loot_tables){
          OverpoweredTechnology.log.info("Loading Loot Table: "+name);
        }
        final String mob = name.substring(prefix.length());
        boolean add_loot_drops = false;
        if(mob.equals("zombie")            && Config.drop_for_zombie.get()){            add_loot_drops = true; }
        if(mob.equals("zombie_villager")   && Config.drop_for_zombie_villager.get()){   add_loot_drops = true; }
        if(mob.equals("husk")              && Config.drop_for_husk.get()){              add_loot_drops = true; }
        if(mob.equals("spider")            && Config.drop_for_spider.get()){            add_loot_drops = true; }
        if(mob.equals("cave_spider")       && Config.drop_for_cave_spider.get()){       add_loot_drops = true; }
        if(mob.equals("creeper")           && Config.drop_for_creeper.get()){           add_loot_drops = true; }
        if(mob.equals("skeleton")          && Config.drop_for_skeleton.get()){          add_loot_drops = true; }
        if(mob.equals("zombie_pigman")     && Config.drop_for_zombie_pigman.get()){     add_loot_drops = true; }
        if(mob.equals("blaze")             && Config.drop_for_blaze.get()){             add_loot_drops = true; }
        if(mob.equals("witch")             && Config.drop_for_witch.get()){             add_loot_drops = true; }
        if(mob.equals("ghast")             && Config.drop_for_ghast.get()){             add_loot_drops = true; }
        if(mob.equals("enderman")          && Config.drop_for_enderman.get()){          add_loot_drops = true; }
        if(mob.equals("stray")             && Config.drop_for_stray.get()){             add_loot_drops = true; }
        if(mob.equals("guardian")          && Config.drop_for_guardian.get()){          add_loot_drops = true; }
        if(mob.equals("elder_guardian")    && Config.drop_for_elder_guardian.get()){    add_loot_drops = true; }
        if(mob.equals("wither_skeleton")   && Config.drop_for_wither_skeleton.get()){   add_loot_drops = true; }
        if(mob.equals("magma_cube")        && Config.drop_for_magma_cube.get()){        add_loot_drops = true; }
        if(mob.equals("shulker")           && Config.drop_for_shulker.get()){           add_loot_drops = true; }
        if(mob.equals("vex")               && Config.drop_for_vex.get()){               add_loot_drops = true; }
        if(mob.equals("evoker")            && Config.drop_for_evoker.get()){            add_loot_drops = true; }
        if(mob.equals("vindicator")        && Config.drop_for_vindicator.get()){        add_loot_drops = true; }
        if(mob.equals("illusioner")        && Config.drop_for_illusioner.get()){        add_loot_drops = true; }
        if(mob.equals("drowned")           && Config.drop_for_drowned.get()){           add_loot_drops = true; }
        if(mob.equals("phantom")           && Config.drop_for_phantom.get()){           add_loot_drops = true; }
        if(mob.equals("skeleton_horse")    && Config.drop_for_skeleton_horse.get()){    add_loot_drops = true; }
        if(mob.equals("pillager")          && Config.drop_for_pillager.get()){          add_loot_drops = true; }
        if(mob.equals("ravager")           && Config.drop_for_ravager.get()){           add_loot_drops = true; }

        if(add_loot_drops){
          event.getTable().addPool(custom_loot_pool);
          if(debug_loot_tables){
            OverpoweredTechnology.log.info("Successfully injected custom loot pool into Loot Table for: "+mob);
          }
        }
      }
    }
  }

}
