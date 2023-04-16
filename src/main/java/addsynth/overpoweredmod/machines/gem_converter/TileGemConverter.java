package addsynth.overpoweredmod.machines.gem_converter;

import javax.annotation.Nullable;
import addsynth.core.util.game.data.AdvancementUtil;
import addsynth.core.util.player.PlayerUtil;
import addsynth.energy.lib.tiles.machines.TileStandardWorkMachine;
import addsynth.material.Material;
import addsynth.material.util.MaterialTag;
import addsynth.material.util.MaterialsUtil;
import addsynth.overpoweredmod.assets.CustomAdvancements;
import addsynth.overpoweredmod.assets.CustomStats;
import addsynth.overpoweredmod.config.MachineValues;
import addsynth.overpoweredmod.game.core.Gems;
import addsynth.overpoweredmod.machines.Filters;
import addsynth.overpoweredmod.registers.Tiles;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stat;
import net.minecraft.stats.Stats;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;

public final class TileGemConverter extends TileStandardWorkMachine implements MenuProvider {

  private byte selection;
  private ItemStack gem_selected = new ItemStack(Material.RUBY.getGem(), 1);
  private byte converting_to;
  
  public TileGemConverter(BlockPos position, BlockState blockstate){
    super(Tiles.GEM_CONVERTER, position, blockstate, 1, Filters.gem_converter, 1, MachineValues.gem_converter);
  }

  public final void cycle(final boolean direction){
    if(onServerSide()){
      if(direction){
        selection += 1;
        if(selection == 8){
          selection = 0;
        }
      }
      else{
        selection -= 1;
        if(selection < 0){
          selection = 7;
        }
      }
      gem_selected = Gems.getItemStack(selection); // updates on server-side
      changed = true;
    }
  }

  @Override
  public final CompoundTag save(final CompoundTag nbt){
    super.save(nbt);
    nbt.putByte("Gem Selected", selection);
    nbt.putByte("Converting To", converting_to);
    savePlayerData(nbt);
    return nbt;
  }

  @Override
  public final void load(final CompoundTag nbt){
    super.load(nbt);
    selection = nbt.getByte("Gem Selected");
    gem_selected = Gems.getItemStack(selection); // updates on client-side and server-side
    converting_to = nbt.getByte("Converting To");
    loadPlayerData(nbt);
  }

  @Override
  protected final boolean can_work(){
    if(quick_transfer()){
      return false;
    }
    return inventory.getInputInventory().getStackInSlot(0).isEmpty() ? false : inventory.getOutputInventory().can_add(0, gem_selected);
  }

  /** Checks if the Input gem matches the gem we're converting to, and if that is the case,
   *  then this just quickly transfers 1 input gem to the output slot.<br />
   *  <b>Note:</b> If the input gem DOES match the output gem, but happens to be from another mod,
   *  we'll convert the gem to OUR gem.
   */
  private final boolean quick_transfer(){
    final ItemStack input_stack = inventory.getInputInventory().getStackInSlot(0);
    if(input_stack.isEmpty() == false){
      if(match(input_stack.getItem(), selection) && inventory.getOutputInventory().can_add(0, gem_selected)){
        final ItemStack insert = inventory.getInputInventory().extractItem(0, 1, false);
        inventory.getOutputInventory().insertItem(0, insert, false);
        return true;
      }
    }
    return false;
  }

  /** Returns whether the input ItemStack matches the specified Gem Index. */
  private static final boolean match(final Item item, final int id){
    if(id == 0){ return MaterialsUtil.match(item, MaterialTag.RUBY.GEMS);     }
    if(id == 1){ return MaterialsUtil.match(item, MaterialTag.TOPAZ.GEMS);    }
    if(id == 2){ return MaterialsUtil.match(item, MaterialTag.CITRINE.GEMS);  }
    if(id == 3){ return MaterialsUtil.match(item, MaterialTag.EMERALD.GEMS);  }
    if(id == 4){ return MaterialsUtil.match(item, MaterialTag.DIAMOND.GEMS);  }
    if(id == 5){ return MaterialsUtil.match(item, MaterialTag.SAPPHIRE.GEMS); }
    if(id == 6){ return MaterialsUtil.match(item, MaterialTag.AMETHYST.GEMS); }
    if(id == 7){ return MaterialsUtil.match(item, MaterialTag.QUARTZ.GEMS);   }
    return false;
  }

  @Override
  protected final void begin_work(){
    inventory.begin_work();
    converting_to = selection;
  }

  @Override
  protected final void perform_work(){
    inventory.getOutputInventory().insertItem(0, Gems.getItemStack(converting_to), false);
    
    increment_gems_stat(last_used_by);
    
    inventory.getWorkingInventory().setEmpty();
    // inventory.recheck();    recheck is called every tick when the inventory changes.
  }

  private final void increment_gems_stat(final String player_name){
    final ServerPlayer player = PlayerUtil.getPlayer(level, player_name);
    if(player != null){
      final Stat gems_converted_stat = Stats.CUSTOM.get(CustomStats.GEMS_CONVERTED);
      player.awardStat(gems_converted_stat);
      if(player.getStats().getValue(gems_converted_stat) >= 1000){
        AdvancementUtil.grantAdvancement(player, CustomAdvancements.CONVERT_A_THOUSAND_GEMS);
      }
    }
  }

  public final int get_gem_selected(){
    return selection;
  }

  public final int getConvertingStack(){
    return converting_to;
  }

  @Override
  @Nullable
  public AbstractContainerMenu createMenu(int id, Inventory player_inventory, Player player){
    setPlayerAccessed(player);
    return new ContainerGemConverter(id, player_inventory, this);
  }

  @Override
  public Component getDisplayName(){
    return new TranslatableComponent(getBlockState().getBlock().getDescriptionId()); // OPTIMIZE replace all these with Block.getName(), in previous versions as well.
  }

}
