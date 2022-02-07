package addsynth.core.util.constants;

import javax.annotation.Nonnegative;
import net.minecraft.core.Direction;

public final class DirectionConstant {

  public static final int DOWN  = Direction.DOWN.get3DDataValue();
  public static final int UP    = Direction.UP.get3DDataValue();
  public static final int NORTH = Direction.NORTH.get3DDataValue();
  public static final int SOUTH = Direction.SOUTH.get3DDataValue();
  public static final int WEST  = Direction.WEST.get3DDataValue();
  public static final int EAST  = Direction.EAST.get3DDataValue();

  public static final int getOppositeDirection(@Nonnegative final int direction){
    return Direction.from3DDataValue(direction).getOpposite().get3DDataValue(); // OPTIMIZE: Update ealier versions if possible
  }

}
