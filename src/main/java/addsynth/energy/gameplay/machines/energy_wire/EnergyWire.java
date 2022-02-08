package addsynth.energy.gameplay.machines.energy_wire;

import javax.annotation.Nullable;
import addsynth.core.block_network.BlockNetworkUtil;
import addsynth.energy.ADDSynthEnergy;
import addsynth.energy.lib.blocks.Wire;
import addsynth.energy.lib.energy_network.tiles.AbstractEnergyNetworkTile;
import addsynth.energy.lib.main.IEnergyUser;
import addsynth.energy.registers.Tiles;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;

public final class EnergyWire extends Wire {

  public EnergyWire(final String name){
    super(Block.Properties.of(Material.WOOL, MaterialColor.COLOR_GRAY).strength(0.1f, 0.0f));
    ADDSynthEnergy.registry.register_block(this, name, new Item.Properties().tab(ADDSynthEnergy.creative_tab));
  }

  @Override
  protected final boolean[] get_valid_sides(final BlockGetter world, final BlockPos pos){
    final boolean[] valid_sides = new boolean[6];
    for(Direction side : Direction.values()){
      valid_sides[side.ordinal()] = false;
      final BlockEntity tile = world.getBlockEntity(pos.relative(side));
      if(tile != null){
        if(tile instanceof AbstractEnergyNetworkTile || tile instanceof IEnergyUser){
          valid_sides[side.ordinal()] = true;
        }
      }
    }
    return valid_sides;
  }

  @Override
  @Nullable
  public final BlockEntity newBlockEntity(BlockPos position, BlockState blockstate){
    return new TileEnergyWire(position, blockstate);
  }

  @Override
  @Nullable
  public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level world, BlockState blockstate, BlockEntityType<T> type){
    return standardTicker(world, type, Tiles.ENERGY_WIRE);
  }

  /** Starting in Minecraft 1.11, {@link Level#setBlockEntity(BlockEntity)} no longer calls
   *  {@link Level#updateComparatorOutputLevel(BlockPos, Block)} at the end of the function.
   *  For this reason we have to use {@link #neighborChanged(BlockState, Level, BlockPos, Block, BlockPos, boolean)}
   *  instead of {@link #onNeighborChange(BlockState, BlockGetter, BlockPos, BlockPos)} like we do in Minecraft 1.10.
   *  As it turns out, not even Vanilla Minecraft uses the <code>onNeighborChange()</code> function a whole lot.
   */
  @Override
  @SuppressWarnings("deprecation")
  public final void neighborChanged(BlockState state, Level world, BlockPos pos, Block blockIn, BlockPos neighbor, boolean isMoving){
    BlockNetworkUtil.neighbor_changed(world, pos, neighbor);
  }

}
