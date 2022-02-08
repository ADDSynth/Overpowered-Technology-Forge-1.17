package addsynth.energy.lib.blocks;

import addsynth.core.blocks.TileEntityBlock;
import addsynth.core.inventory.IInventoryUser;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;

/** <p>This is your typical machine-type block with a silver appearance and metallic properties.
 *  <p>THIS DOES NOT REGISTER YOUR BLOCKS! Mods that extend from this class must register
 *     their individual blocks using that mod's {@link addsynth.core.game.RegistryUtil} instance.
 * @author ADDSynth
 */
public abstract class MachineBlock extends TileEntityBlock {

  /** Specify your own Block Properties. Required if block is transparent! */
  public MachineBlock(final Block.Properties properties){
    super(properties);
  }

  /** Standard constructor. SoundType = Metal, and standard block hardness. */
  public MachineBlock(final MaterialColor color){
    super(Block.Properties.of(Material.METAL, color).sound(SoundType.METAL).strength(3.5f, 6.0f));
  }

  @Override
  @SuppressWarnings("deprecation")
   public void onRemove(BlockState state, Level world, BlockPos pos, BlockState newState, boolean isMoving){
    final BlockEntity tile = world.getBlockEntity(pos);
    if(tile != null){
      if(tile instanceof IInventoryUser){
        ((IInventoryUser)tile).drop_inventory();
      }
    }
    super.onRemove(state, world, pos, newState, isMoving);
  }
  
}
