package addsynth.overpoweredmod.machines.laser.cannon;

import addsynth.core.Debug;
import addsynth.core.game.tiles.TileBase;
import addsynth.core.util.game.tileentity.ITickingTileEntity;
import addsynth.overpoweredmod.OverpoweredTechnology;
import addsynth.overpoweredmod.game.core.Laser;
import addsynth.overpoweredmod.registers.Tiles;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public final class TileLaser extends TileBase implements ITickingTileEntity {

  private boolean first_tick = true;

  private Laser laser;
  private Direction direction;
  private int distance;
  private int count;
  private boolean active;

  // TileEntities cannot have enums as an argument, otherwise Forge throws a InstantiationException.
  // https://stackoverflow.com/questions/41216000/why-newinstance-throws-instantiationexception-in-my-code
  // https://stackoverflow.com/questions/234600/can-i-use-class-newinstance-with-constructor-arguments
  // https://docs.oracle.com/javase/6/docs/api/java/lang/Class.html#getDeclaredConstructor%28java.lang.Class...%29
  // https://stackoverflow.com/questions/195321/why-is-class-newinstance-evil
  // https://docs.oracle.com/javase/tutorial/reflect/member/ctorInstance.html
  // 
  // https://mcforge.readthedocs.io/en/latest/tileentities/tileentity/#creating-a-tileentity
  // "It is important that your TileEntity have a default constructor so Minecraft can load it correctly."

  public TileLaser(BlockPos position, BlockState blockstate){
    super(Tiles.LASER, position, blockstate);
  }

  public final void activate(int distance){
    this.distance = distance;
    count = 1;
    active = true;
  }

  @Override
  public void onLoad(){
  }

  @Override
  @SuppressWarnings("null")
  public final void serverTick(){
    // FIX: Make TileLaser.tick better, first check level is not null, then save a local variable. Remove
    //      suppress null warnings. Possibly add a seperate Fusion Laser Block that doesn't spawn a TileEntity.
    if(first_tick){
      if(level.isClientSide == false){
        final BlockState block_state = getBlockState();
        final LaserCannon block = (LaserCannon)block_state.getBlock();
        this.laser = Laser.index[Math.max(0, block.color)];
        if(block.color < 0){
          OverpoweredTechnology.log.fatal(
            "Standard Lasers have a color index indicating the type of laser 0-"+(Laser.index.length-1)+", but this laser has an "+
            "index of "+block.color+". Non-standard lasers currently don't need a TileEntity. If you're receiving this message, "+
            "it's probably safe to continue playing, but this indicates a serious error. Please report this to the mod author.");
          Debug.block(block, worldPosition);
          Thread.dumpStack();
          setRemoved();
        }
        this.direction = block_state.getValue(LaserCannon.FACING);
      }
      first_tick = false;
    }
    if(level != null){
      if(level.isClientSide == false){
        if(active){
          final BlockPos position = worldPosition.relative(direction, count);
          final Block block = level.getBlockState(position).getBlock();
          if(block != Blocks.BEDROCK){
            level.destroyBlock(position, true);
            // entities won't catch on fire unless laser beam is also on server side.
            level.setBlock(position, laser.beam.defaultBlockState(), 2);
            count += 1;
            if(count > distance){
              active = false;
            }
          }
          else{
            active = false;
          }
        }
      }
    }
  }

}
