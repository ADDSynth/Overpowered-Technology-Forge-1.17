package addsynth.overpoweredmod.machines.laser.beam;

import javax.annotation.Nullable;
import addsynth.core.game.blocks.TileEntityBlock;
import addsynth.overpoweredmod.OverpoweredTechnology;
import addsynth.overpoweredmod.assets.DamageSources;
import addsynth.overpoweredmod.config.Config;
import addsynth.overpoweredmod.registers.Tiles;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public final class LaserBeam extends TileEntityBlock {

  public LaserBeam(String name){
    super(Block.Properties.of(Material.FIRE).noCollission().lightLevel((blockstate)->{return Config.laser_light_level.get();}));
    OverpoweredTechnology.registry.register_block(this, name);
  }

  @Override
  @SuppressWarnings("deprecation")
  public final VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context){
    return Shapes.empty();
  }

  // You cannot set this block isBurning(true) because this will also set fire to any
  //   item Entities that fall in it, negating the purpose of having a mining laser.
  //   also, that only does 1 damage at a time.
  @Override
  @SuppressWarnings("deprecation")
  public final void entityInside(final BlockState state, final Level world, final BlockPos pos, final Entity entity){
    if(Config.lasers_set_entities_on_fire.get()){
      if(entity instanceof ItemEntity == false){
        if(Config.laser_damage_depends_on_world_difficulty.get()){
          final int[] damage = new int[] {
            Config.LASER_DAMAGE_PEACEFUL_DIFFICULTY, Config.LASER_DAMAGE_EASY_DIFFICULTY,
            Config.LASER_DAMAGE_NORMAL_DIFFICULTY, Config.LASER_DAMAGE_HARD_DIFFICULTY};
          entity.hurt(DamageSources.laser, damage[world.getDifficulty().ordinal()]);
        }
        else{
          entity.hurt(DamageSources.laser, Config.LASER_DAMAGE_NORMAL_DIFFICULTY);
        }
        entity.setSecondsOnFire(8); // 8 seconds is the same time Vanilla sets players on fire when in contact with fire.
      }
    }
  }

  @Override
  @Nullable
  public final BlockEntity newBlockEntity(BlockPos position, BlockState blockstate){
    return new TileLaserBeam(position, blockstate);
  }

  @Override
  @Nullable
  public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level world, BlockState blockstate, BlockEntityType<T> type){
    return standardTicker(world, type, Tiles.LASER_BEAM);
  }

  @Override
  @SuppressWarnings("deprecation")
  public final boolean skipRendering(BlockState state, BlockState adjacentBlockState, Direction side){
    return adjacentBlockState.getBlock() instanceof LaserBeam ? true : super.skipRendering(state, adjacentBlockState, side);
  }

}
