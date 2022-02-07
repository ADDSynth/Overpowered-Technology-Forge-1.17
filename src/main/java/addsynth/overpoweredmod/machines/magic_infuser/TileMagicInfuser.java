package addsynth.overpoweredmod.machines.magic_infuser;

import java.util.Random;
import javax.annotation.Nullable;
import addsynth.core.inventory.SlotData;
import addsynth.core.recipe.jobs.JobSystem;
import addsynth.core.util.StringUtil;
import addsynth.core.util.math.random.RandomUtil;
import addsynth.energy.lib.tiles.machines.TileStandardWorkMachine;
import addsynth.material.MaterialsUtil;
import addsynth.overpoweredmod.OverpoweredTechnology;
import addsynth.overpoweredmod.config.MachineValues;
import addsynth.overpoweredmod.game.core.Init;
import addsynth.overpoweredmod.machines.Filters;
import addsynth.overpoweredmod.registers.Tiles;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.EnchantedBookItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentInstance;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.state.BlockState;

public final class TileMagicInfuser extends TileStandardWorkMachine implements MenuProvider {

  // TODO: Change these into actual recipes, and show them in JEI. And once I do that, I can get rid of these special-case function overrides. The recipe resultProvider can return a random Enchanted book.
  private static final Enchantment[] ruby_enchantments = new Enchantment[]{
    Enchantments.POWER_ARROWS,
    Enchantments.PUNCH_ARROWS
  };
  private static final Enchantment[] topaz_enchantments = new Enchantment[]{
    Enchantments.BLAST_PROTECTION,
    Enchantments.FIRE_ASPECT,
    Enchantments.FIRE_PROTECTION,
    Enchantments.FLAMING_ARROWS
  };
  private static final Enchantment[] citrine_enchantments = new Enchantment[]{
    Enchantments.SHARPNESS,
    Enchantments.BANE_OF_ARTHROPODS,
    Enchantments.SMITE,
    Enchantments.QUICK_CHARGE,
    Enchantments.PIERCING
  };
  private static final Enchantment[] emerald_enchantments = new Enchantment[]{
    Enchantments.FALL_PROTECTION,
    Enchantments.RESPIRATION,
    Enchantments.LOYALTY,
    Enchantments.CHANNELING
  };
  private static final Enchantment[] diamond_enchantments = new Enchantment[]{
    Enchantments.PROJECTILE_PROTECTION,
    Enchantments.ALL_DAMAGE_PROTECTION,
    Enchantments.THORNS
  };
  private static final Enchantment[] sapphire_enchantments = new Enchantment[]{
    Enchantments.DEPTH_STRIDER,
    Enchantments.AQUA_AFFINITY,
    Enchantments.FROST_WALKER,
    Enchantments.FISHING_LUCK,
    Enchantments.FISHING_SPEED,
    Enchantments.RIPTIDE,
    Enchantments.IMPALING
  };
  private static final Enchantment[] amethyst_enchantments = new Enchantment[]{
    Enchantments.MOB_LOOTING,
    Enchantments.KNOCKBACK,
    Enchantments.SWEEPING_EDGE,
    Enchantments.MULTISHOT
  };
  private static final Enchantment[] quartz_enchantments = new Enchantment[]{
    Enchantments.BLOCK_EFFICIENCY,
    Enchantments.UNBREAKING
  };
  private static final Enchantment[] celestial_enchantments = new Enchantment[]{
    Enchantments.BLOCK_FORTUNE,
    Enchantments.INFINITY_ARROWS,
    Enchantments.SILK_TOUCH,
    Enchantments.MENDING
  };
  private static final Enchantment[] void_crystal_enchantments = new Enchantment[]{
    Enchantments.BINDING_CURSE,
    Enchantments.VANISHING_CURSE
  };

  public TileMagicInfuser(BlockPos position, BlockState blockstate){
    super(Tiles.MAGIC_INFUSER, position, blockstate,
      new SlotData[]{ // TODO: !!! Separate this out into a function, that rebuilds both slots every time. No, I got it! Base TileEntities, must get SlotData from an abastract function, instead of the constructor. Derived TileEntities must implement this function.
        new SlotData(Items.BOOK),
        new SlotData(Filters.magic_infuser)
      },
      1,
      MachineValues.magic_infuser
    );
    inventory.setResponder(this);
  }

  @Override
  public final void onInventoryChanged(){
    changed = true;
  }

  @Override
  protected final boolean can_work(){
    return !inventory.getInputInventory().getStackInSlot(0).isEmpty() &&
           !inventory.getInputInventory().getStackInSlot(1).isEmpty() &&
            inventory.getOutputInventory().getStackInSlot(0).isEmpty();
  }

  @Override
  protected final void perform_work(){
    final Enchantment enchantment = get_enchantment();
    if(enchantment != null){
      final ItemStack enchant_book = new ItemStack(Items.ENCHANTED_BOOK, 1);
      final EnchantmentInstance enchantment_data = new EnchantmentInstance(enchantment, 1);
      EnchantedBookItem.addEnchantment(enchant_book, enchantment_data);
      inventory.getOutputInventory().setStackInSlot(0, enchant_book);
    }
    inventory.getWorkingInventory().setEmpty();
    inventory.recheck();
  }

  @Override
  public final int getJobs(){
    return JobSystem.getMaxNumberOfJobs(inventory.getInputInventory().getItemStacks(), true);
  }

  private final Enchantment get_enchantment(){
    // https://minecraft.gamepedia.com/Enchanting#Summary_of_enchantments
    final Item item = inventory.getWorkingInventory().getStackInSlot(1).getItem();
    final Random random = new Random();
    if(MaterialsUtil.match(item, MaterialsUtil.getRubies())){
      return RandomUtil.choose(random, ruby_enchantments);
    }
    if(MaterialsUtil.match(item, MaterialsUtil.getTopaz())){
      return RandomUtil.choose(random, topaz_enchantments);
    }
    if(MaterialsUtil.match(item, MaterialsUtil.getCitrine())){
      return RandomUtil.choose(random, citrine_enchantments);
    }
    if(MaterialsUtil.match(item, MaterialsUtil.getEmeralds())){
      return RandomUtil.choose(random, emerald_enchantments);
    }
    if(MaterialsUtil.match(item, MaterialsUtil.getDiamonds())){
      return RandomUtil.choose(random, diamond_enchantments);
    }
    if(MaterialsUtil.match(item, MaterialsUtil.getSapphires())){
      return RandomUtil.choose(random, sapphire_enchantments);
    }
    if(MaterialsUtil.match(item, MaterialsUtil.getAmethysts())){
      return RandomUtil.choose(random, amethyst_enchantments);
    }
    if(MaterialsUtil.match(item, MaterialsUtil.getQuartz())){
      return RandomUtil.choose(random, quartz_enchantments);
    }
    if(item == Init.celestial_gem){
      return RandomUtil.choose(random, celestial_enchantments);
    }
    if(item == Init.void_crystal){
      return RandomUtil.choose(random, void_crystal_enchantments);
    }
    OverpoweredTechnology.log.error("function get_enchantment() in "+TileMagicInfuser.class.getSimpleName()+" returned a null enchantment! With "+StringUtil.getName(item)+" as input.");
    return null;
  }

  @Override
  @Nullable
  public AbstractContainerMenu createMenu(int id, Inventory player_inventory, Player player){
    return new ContainerMagicInfuser(id, player_inventory, this);
  }

  @Override
  public Component getDisplayName(){
    return new TranslatableComponent(getBlockState().getBlock().getDescriptionId());
  }

}
