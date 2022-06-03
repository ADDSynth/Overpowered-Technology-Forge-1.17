package addsynth.energy.lib.tiles;

import addsynth.core.game.tiles.TileBase;
import addsynth.core.util.game.tileentity.ITickingTileEntity;
import addsynth.energy.lib.main.IEnergyConsumer;
import addsynth.energy.lib.main.Receiver;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

/** All machines that only receive energy to do work derive from this class. */
public abstract class TileAbstractMachine extends TileBase implements ITickingTileEntity, IEnergyConsumer {

  protected final Receiver energy;

  public TileAbstractMachine(final BlockEntityType type, BlockPos position, BlockState blockstate, final Receiver energy){
    super(type, position, blockstate);
    this.energy = energy;
  }

  @Override
  public void load(final CompoundTag nbt){
    super.load(nbt);
    if(energy != null){ energy.loadFromNBT(nbt);}
  }

  @Override
  public CompoundTag save(final CompoundTag nbt){
    super.save(nbt);
    if(energy != null){ energy.saveToNBT(nbt);}
    return nbt;
  }

  @Override
  public Receiver getEnergy(){
    return energy;
  }

}
