package addsynth.overpoweredmod.machines.black_hole;

import javax.annotation.Nullable;
import addsynth.core.blocks.TileEntityBlock;
import addsynth.overpoweredmod.OverpoweredTechnology;
import addsynth.overpoweredmod.registers.Tiles;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public final class BlackHoleBlock extends TileEntityBlock {

  public BlackHoleBlock(final String name){
    super(Block.Properties.of(Material.PORTAL, MaterialColor.COLOR_BLACK).noCollission());
    // setResistance(100.0f);
    OverpoweredTechnology.registry.register_block(this, name);
    OverpoweredTechnology.registry.register_ItemBlock(new BlackHoleItem(this));
  }

  @Override
  @SuppressWarnings("deprecation")
  public final VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context){
     return Shapes.empty();
  }

  @Override
  @Nullable
  public final BlockEntity newBlockEntity(BlockPos position, BlockState blockstate){
    return new TileBlackHole(position, blockstate);
  }

  @Override
  @Nullable
  public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level world, BlockState blockstate, BlockEntityType<T> type){
    return standardTicker(world, type, Tiles.BLACK_HOLE);
  }

  @Override
  public void setPlacedBy(Level world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack){
    // if(placer instanceof ServerPlayerEntity){
    //   ((ServerPlayerEntity)placer).addStat(CustomStats.BLACK_HOLE_EVENTS);
    // }
  }

}
