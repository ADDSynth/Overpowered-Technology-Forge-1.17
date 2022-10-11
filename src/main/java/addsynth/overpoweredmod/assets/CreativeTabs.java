package addsynth.overpoweredmod.assets;

import addsynth.overpoweredmod.config.Config;
import addsynth.overpoweredmod.config.Features;
import addsynth.overpoweredmod.game.reference.OverpoweredItems;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public final class CreativeTabs {

  public static final CreativeModeTab creative_tab = new CreativeModeTab("overpowered")
  {
    @Override
    public final ItemStack makeIcon(){
      return new ItemStack(OverpoweredItems.celestial_gem, 1);
    }
  };

  /* DELETE
  public static final ItemGroup machines_creative_tab =
    Config.creative_tab_machines.get() ? new ItemGroup("overpowered_machines")
    {
      @Override
      public final ItemStack createIcon(){
        return new ItemStack(OverpoweredMod.registry.getItemBlock(Machines.generator), 1);
      }
    }
  : creative_tab;
  */

  public static final CreativeModeTab tools_creative_tab =
    Config.creative_tab_tools.get() ? new CreativeModeTab("overpowered_tools")
    {
      @Override
      public final ItemStack makeIcon(){
        return Features.celestial_tools.get() ? new ItemStack(OverpoweredItems.celestial_pickaxe, 1) :
               Features.identifier.get()      ? new ItemStack(OverpoweredItems.unidentified_armor[2][0], 1) :
               Features.void_tools.get()      ? new ItemStack(OverpoweredItems.void_sword, 1) :
               new ItemStack(Items.STONE_SHOVEL, 1);
      }
    }
  : creative_tab;

}
