package addsynth.overpoweredmod.machines.data_cable;

import java.util.ArrayList;
import addsynth.core.block_network.BlockNetwork;
import addsynth.core.block_network.Node;
import addsynth.overpoweredmod.config.MachineValues;
import addsynth.overpoweredmod.game.reference.OverpoweredBlocks;
import addsynth.overpoweredmod.machines.fusion.chamber.TileFusionChamber;
import addsynth.overpoweredmod.machines.fusion.converter.TileFusionEnergyConverter;
import addsynth.overpoweredmod.machines.laser.cannon.LaserCannon;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public final class DataCableNetwork extends BlockNetwork<TileDataCable> {

  // TODO: We could probably handle these lists better if we were using Nodes.
  /** All scanning units that are connected to this Data Cable network. */
  private final ArrayList<BlockPos> scanning_units = new ArrayList<>(6);
  private final ArrayList<BlockPos> fusion_energy_converters = new ArrayList<>(1);

  private static final class FusionEnergyStructure {
    public final BlockPos position;
    private boolean[] side = new boolean[] {false, false, false, false, false, false};
    public FusionEnergyStructure(final BlockPos position_of_singularity_container){
      position = position_of_singularity_container;
    }
    public final void add_scanning_unit(final Direction direction){
      // yeah it's actually the opposite side, but it's still only valid if it has all 6 sides.
      side[direction.ordinal()] = true;
    }
    public final boolean is_valid(){
      return side[0] && side[1] && side[2] && side[3] && side[4] && side[5];
    }
  }

  public DataCableNetwork(final Level world, final TileDataCable tile){
    super(world, tile);
  }

  @Override
  protected final void clear_custom_data(){
    scanning_units.clear();
    fusion_energy_converters.clear();
  }

  @Override
  protected final void customSearch(final Node node, final Level world){
    if(node.block == OverpoweredBlocks.fusion_control_unit){
      if(scanning_units.contains(node.position) == false){
        scanning_units.add(node.position);
      }
    }
    if(node.block == OverpoweredBlocks.fusion_converter){
      if(fusion_energy_converters.contains(node.position) == false){
        fusion_energy_converters.add(node.position);
      }
    }
  }

  @Override
  public final void neighbor_was_changed(final Level world, final BlockPos current_position, final BlockPos position_of_neighbor){
    // Is this optimized? Wouldn't it be better just to call updateBlockNetwork() regardless?
    Block block = world.getBlockState(position_of_neighbor).getBlock();
    if(block == OverpoweredBlocks.fusion_converter || block == OverpoweredBlocks.fusion_control_unit){
      // If a Fusion Converter or Fusion Control Unit was added.
      updateBlockNetwork(world, current_position);
      return;
    }
    boolean update = false;
    // Check all positions of scanning units and fusion energy converters and make sure they still exist there.
    if(update == false){
      for(BlockPos position : scanning_units){
        if(world.getBlockState(position).getBlock() != OverpoweredBlocks.fusion_control_unit){
          update = true;
          break;
        }
      }
    }
    if(update == false){
      for(BlockPos position : fusion_energy_converters){
        if(world.getBlockState(position).getBlock() != OverpoweredBlocks.fusion_converter){
          update = true;
          break;
        }
      }
    }
    if(update){
      updateBlockNetwork(world, current_position); // run update outside of for loop to avoid ConcurrentModificationException's.
    }
  }

  @Override
  protected final void onUpdateNetworkFinished(final Level world){
    // What we're doing here is, even if the player has a valid fusion chamber constructed properly,
    //   its energy output can be divided amongst multiple Fusion Energy Converter machines.
    // check_singularity_container();
    if(fusion_energy_converters.size() > 0){
      final int actual_energy = Math.round((float)MachineValues.fusion_energy_output_per_tick.get() / fusion_energy_converters.size());
      TileFusionEnergyConverter tile;
      for(BlockPos tile_position : fusion_energy_converters){
        tile = (TileFusionEnergyConverter)world.getBlockEntity(tile_position);
        if(tile != null){
          tile.getEnergy().setCapacity(actual_energy); // MAYBE: merhaps I can move this to the TileFusionEnergyConverter class.
        }
      }
    }
  }

  /** THIS, is the algorithm that goes through all the scanning units, and performs an arsenal of
   *  validity checks on them! It ends as soon as it finds 1 valid Singularity Containment structure!
   */
  public final BlockPos get_valid_fusion_container(final Level world){
    BlockPos valid_fusion_chamber = null;
    if(scanning_units.size() >= 6){
      boolean found_valid = false;
      FusionEnergyStructure structure = null;
      BlockState block_state;
      BlockPos position;
      for(BlockPos scanning_unit : scanning_units){
        for(Direction side : Direction.values()){
          block_state = world.getBlockState(scanning_unit.relative(side));
          if(block_state.getBlock() == OverpoweredBlocks.fusion_control_laser){
            if(block_state.getValue(LaserCannon.FACING) == side){
              position = scanning_unit.relative(side, TileFusionChamber.container_radius);
              if(world.getBlockState(position).getBlock() == OverpoweredBlocks.fusion_chamber){
                // FIX: we need to keep a list of singularity containers, otherwise, if the scanning units are out of order, we immediately replace the current one.
                if(structure == null){
                  structure = new FusionEnergyStructure(position);
                }
                else{
                  if(position.equals(structure.position) == false){
                    structure = new FusionEnergyStructure(position);
                  }
                }
                structure.add_scanning_unit(side);
                if(structure.is_valid()){
                  found_valid = true;
                  valid_fusion_chamber = structure.position;
                  break;
                }
              }
            }
          }
        }
        if(found_valid){
          break;
        }
      }
    }
    return valid_fusion_chamber;
  }

}
