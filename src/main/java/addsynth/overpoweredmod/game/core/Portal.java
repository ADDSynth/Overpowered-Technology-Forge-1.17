package addsynth.overpoweredmod.game.core;

import addsynth.core.game.RegistryUtil;
import addsynth.overpoweredmod.Debug;
import addsynth.overpoweredmod.blocks.dimension.tree.*;
import addsynth.overpoweredmod.machines.portal.rift.PortalEnergyBlock;
import net.minecraft.world.item.Item;

public final class Portal {

  static {
    Debug.log_setup_info("Begin loading Portal class...");
  }

  public static final PortalEnergyBlock   portal             = new PortalEnergyBlock();
  /** Item form of Portal, used only for Achievement icon. Does not show up in jei or creative tab.
   *  But players can still get it by using the /give command.
   */
  public static final Item                portal_image       = RegistryUtil.getItemBlock(portal);
  
  
  // public static final BlockGrassNoDestroy custom_grass_block = new BlockGrassNoDestroy();
  // public static final BlockAirNoDestroy   custom_air_block   = new BlockAirNoDestroy();


  public static final UnknownWood         unknown_wood       = new UnknownWood();
  public static final UnknownLeaves       unknown_leaves     = new UnknownLeaves();


  static {
    Debug.log_setup_info("Finished loading Portal class.");
  }

}
