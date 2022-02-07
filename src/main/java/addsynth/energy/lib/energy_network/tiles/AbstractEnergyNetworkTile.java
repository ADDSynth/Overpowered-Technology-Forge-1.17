package addsynth.energy.lib.energy_network.tiles;

import addsynth.core.block_network.IBlockNetworkUser;
import addsynth.core.tiles.TileBase;
import addsynth.core.util.game.tileentity.ITickingTileEntity;
import addsynth.energy.lib.energy_network.EnergyNetwork;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public abstract class AbstractEnergyNetworkTile extends TileBase
  implements IBlockNetworkUser<EnergyNetwork>, ITickingTileEntity {

  protected EnergyNetwork network;

  public AbstractEnergyNetworkTile(final BlockEntityType type, BlockPos position, BlockState blockstate){
    super(type, position, blockstate);
  }

}
