package addsynth.overpoweredmod.machines.fusion.chamber;

import javax.annotation.Nullable;
import addsynth.core.game.inventory.SlotData;
import addsynth.core.game.tiles.TileStorageMachine;
import addsynth.core.util.data.AdvancementUtil;
import addsynth.overpoweredmod.assets.CustomAdvancements;
import addsynth.overpoweredmod.game.core.Machines;
import addsynth.overpoweredmod.game.core.ModItems;
import addsynth.overpoweredmod.registers.Tiles;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.block.state.BlockState;

public final class TileFusionChamber extends TileStorageMachine implements MenuProvider {

  public static final Item[] input_filter = new Item[]{ModItems.fusion_core};

  /** A standard TNT explosion is size of 4. */
  private static final float FUSION_CHAMBER_EXPLOSION_SIZE = 10.0f;

  public static final byte container_radius = 5;
  private boolean on;

  public TileFusionChamber(BlockPos position, BlockState blockstate){
    super(Tiles.FUSION_CHAMBER, position, blockstate, new SlotData[]{new SlotData(input_filter,1)}); // OPTIMIZE: This can be simplified too.
  }

  public final boolean has_fusion_core(){
    final ItemStack stack = input_inventory.getStackInSlot(0);
    if(stack.isEmpty()){
      return false;
    }
    return stack.getCount() > 0;
  }

  public final boolean is_on(){
    return on;
  }

  public final void set_state(final boolean state, final ServerPlayer player){
    if(on != state){ // Only run on state change
      int i;
      BlockPos position;
      for(Direction side: Direction.values()){
        for(i = 1; i < container_radius - 1; i++){
          position = worldPosition.relative(side, i);
          if(state){
            // TODO: DO NOT INSERT a Laser Effect block in the world! Replace this with some sort of
            //       OpenGL special effects that doesn't touch the world and immune to player interference.
            level.setBlock(position, Machines.fusion_control_laser_beam.defaultBlockState(), 3);
            // TEST why would we need block updates for this? Can this just be set to 2 for Client updates?
            if(player != null){
              AdvancementUtil.grantAdvancement(player, CustomAdvancements.FUSION_ENERGY);
            }
          }
          else{
            level.removeBlock(position, false);
          }
        }
      }
      on = state;
    }
  }

  public final void explode(){
    set_state(false, null);
    level.removeBlock(worldPosition, false);
    level.explode(null, worldPosition.getX()+0.5, worldPosition.getY()+0.5, worldPosition.getZ()+0.5, FUSION_CHAMBER_EXPLOSION_SIZE, true, Explosion.BlockInteraction.DESTROY);
  }

  @Override
  @Nullable
  public AbstractContainerMenu createMenu(int id, Inventory player_inventory, Player player){
    return new ContainerFusionChamber(id, player_inventory, this);
  }

  @Override
  public Component getDisplayName(){
    return new TranslatableComponent(getBlockState().getBlock().getDescriptionId());
  }

}
