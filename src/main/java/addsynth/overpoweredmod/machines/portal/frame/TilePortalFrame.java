package addsynth.overpoweredmod.machines.portal.frame;

import javax.annotation.Nullable;
import addsynth.core.game.inventory.SlotData;
import addsynth.core.game.inventory.filter.TagFilter;
import addsynth.core.game.tiles.TileStorageMachine;
import addsynth.material.util.MaterialTag;
import addsynth.overpoweredmod.game.tags.OverpoweredItemTags;
import addsynth.overpoweredmod.registers.Tiles;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.Tags;

public final class TilePortalFrame extends TileStorageMachine implements MenuProvider {

  public static final TagFilter filter = new TagFilter(OverpoweredItemTags.portal_fuel);

  public TilePortalFrame(BlockPos position, BlockState blockstate){
    super(Tiles.PORTAL_FRAME, position, blockstate, new SlotData[]{new SlotData(filter, 1)});
  }

  public final int check_item(){
    final ItemStack stack = input_inventory.getStackInSlot(0);
    if(stack.isEmpty()){ return -1; }
    final Item item = stack.getItem();
    if(MaterialTag.RUBY.BLOCKS.contains(item)){            return 0; }
    if(MaterialTag.TOPAZ.BLOCKS.contains(item)){           return 1; }
    if(MaterialTag.CITRINE.BLOCKS.contains(item)){         return 2; }
    if(Tags.Items.STORAGE_BLOCKS_EMERALD.contains(item)){  return 3; }
    if(Tags.Items.STORAGE_BLOCKS_DIAMOND.contains(item)){  return 4; }
    if(MaterialTag.SAPPHIRE.BLOCKS.contains(item)){        return 5; }
    if(MaterialTag.AMETHYST.BLOCKS.contains(item)){ return 6; }
    if(Tags.Items.STORAGE_BLOCKS_QUARTZ.contains(item)){   return 7; }
    return -1;
  }

  @Override
  @Nullable
  public AbstractContainerMenu createMenu(int id, Inventory player_inventory, Player player){
    return new ContainerPortalFrame(id, player_inventory, this);
  }

  @Override
  public Component getDisplayName(){
    return getBlockState().getBlock().getName();
  }

}
