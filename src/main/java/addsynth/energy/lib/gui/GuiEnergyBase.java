package addsynth.energy.lib.gui;

import addsynth.core.container.TileEntityContainer;
import addsynth.core.game.inventory.machine.IMachineInventory;
import addsynth.core.gui.GuiContainerBase;
import addsynth.core.gui.util.GuiUtil;
import addsynth.core.util.StringUtil;
import addsynth.energy.lib.main.Energy;
import addsynth.energy.lib.main.IEnergyUser;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.level.block.entity.BlockEntity;

/** Base Gui class for machines that use {@link Energy}. Contains helper
 *  functions for drawing energy variables and machine status.
 * @author ADDSynth
 * @param <T> BlockEntity that implements the {@link IEnergyUser} interface
 * @param <C> The Container class object for the machine
 */
public abstract class GuiEnergyBase<T extends BlockEntity & IEnergyUser, C extends TileEntityContainer<T>> extends GuiContainerBase<C> {

  protected final T tile;
  protected final Energy energy;

  private final String energy_text           = StringUtil.translate("gui.addsynth_energy.common.energy");
  private final String energy_usage_text     = StringUtil.translate("gui.addsynth_energy.common.energy_usage");
  private final String tick_text             = StringUtil.translate("gui.addsynth_energy.common.tick");
  private final String efficiency_text       = StringUtil.translate("gui.addsynth_energy.common.efficiency");
  /** The word 'Status' translated. */
  private final String status_text           = StringUtil.translate("gui.addsynth_energy.common.status");
  private final String time_left_text        = StringUtil.translate("gui.addsynth_energy.common.time_remaining");
  private final String charge_remaining_text = StringUtil.translate("gui.addsynth_energy.common.charge_time_remaining");
  private final String full_charge_time_text = StringUtil.translate("gui.addsynth_energy.common.time_to_full_charge");
  private final String no_energy_change_text = StringUtil.translate("gui.addsynth_energy.common.no_energy_change");
  private final String null_energy_reference = "[Error: Null Energy Reference]";

  public GuiEnergyBase(final C container, final Inventory player_inventory, final Component title, final ResourceLocation gui_texture_location){
    super(container, player_inventory, title, gui_texture_location);
    this.tile = (T)container.getTileEntity();
    this.energy = tile.getEnergy();
  }

  public GuiEnergyBase(int width, int height, C container, Inventory player_inventory, Component title, ResourceLocation gui_texture_location){
    super(width, height, container, player_inventory, title, gui_texture_location);
    this.tile = (T)container.getTileEntity();
    this.energy = tile.getEnergy();
  }

  /** Draws Energy: Level / Capacity in the standard location, just below the title, at y = 17 pixels. */
  protected final void draw_energy(final PoseStack matrix){
    this.draw_energy(matrix, 6, 17);
  }

  protected final void draw_energy_after_switch(final PoseStack matrix){
    this.draw_energy(matrix, 50, 21);
  }

  protected final void draw_energy_below_switch(final PoseStack matrix){
    this.draw_energy(matrix, 6, 37);
  }

  protected final void draw_energy(final PoseStack matrix, final int draw_x, final int draw_y){
    if(energy != null){
      GuiUtil.draw_text_left(matrix, energy_text+":",draw_x,draw_y);
      GuiUtil.draw_text_right(matrix, String.format("%.2f", energy.getEnergy()) + " / " + energy.getCapacity(), guiUtil.right_edge, draw_y);
    }
    else{
      GuiUtil.draw_text_center(matrix, null_energy_reference, (draw_x + guiUtil.right_edge) / 2, draw_y);
    }
  }

  /** Draws the energy usage after the title. */
  protected final void draw_energy_usage(final PoseStack matrix){
    this.draw_energy_usage(matrix, 6, 17);
  }
  
  /** Draws the Energy Usage to the right of the Power Switch. */
  protected final void draw_energy_usage_after_switch(final PoseStack matrix){
    this.draw_energy_usage(matrix, 50, 21);
  }
  
  protected final void draw_energy_usage(PoseStack matrix, final int draw_x, final int draw_y){
    if(energy != null){
      GuiUtil.draw_text_left(matrix, efficiency_text+":", draw_x, draw_y);
      final String energy_usage = StringUtil.build(
        String.format("%.2f", energy.get_energy_in()),
        " / ",
        String.format("%.2f", energy.getMaxReceive()),
        "  ",
        StringUtil.toPercentageString(energy.get_energy_in() / energy.getMaxReceive())
      );
      GuiUtil.draw_text_right(matrix, energy_usage, guiUtil.right_edge, draw_y);
    }
    else{
      GuiUtil.draw_text_left(matrix, null_energy_reference, draw_x, draw_y);
    }
  }

  /** Draws the status at the default location, below the energy capacity line. */
  protected final void draw_status(PoseStack matrix, final String status){
    GuiUtil.draw_text_left(matrix, status_text+": "+status, 6, 28);
  }

  protected final void draw_status(PoseStack matrix, final String status, final int y){
    GuiUtil.draw_text_left(matrix, status_text+": "+status, 6, y);
  }

  protected final void draw_status(PoseStack matrix, final String status, final int x, final int y){
    GuiUtil.draw_text_left(matrix, status_text+": "+status, x, y);
  }

  protected final void draw_status_after_switch(PoseStack matrix, final String status){
    GuiUtil.draw_text_left(matrix, status_text+": "+status, 50, 21);
  }

  protected final void draw_status_below_switch(PoseStack matrix, final String status){
    GuiUtil.draw_text_left(matrix, status_text+": "+status, 6, 37);
  }

  protected final void draw_time_left(PoseStack matrix, final int draw_y){
    if(energy != null){
      final double rate = energy.getDifference();
      final String time_left; // it let's me do this?
      if(tile instanceof IMachineInventory){
        time_left = StringUtil.print_time((((IMachineInventory)tile).getJobs() * energy.getCapacity()) + energy.getEnergyNeeded(), rate);
      }
      else{
        time_left = StringUtil.print_time(energy.getEnergyNeeded(), rate);
      }
      GuiUtil.draw_text_left(matrix, time_left_text+": "+time_left, 6, draw_y);
    }
    else{
      GuiUtil.draw_text_left(matrix, time_left_text+": "+null_energy_reference, 6, draw_y);
    }
  }

  /** Draws charge time at bottom-left of gui. */
  protected final void draw_energy_difference(PoseStack matrix, final int draw_y){
    if(energy == null){
      GuiUtil.draw_text_left(matrix, null_energy_reference, 6, draw_y);
      return;
    }
    final double difference = energy.getDifference();
    switch((int)Math.signum(difference)){
    case 1:
      GuiUtil.draw_text_left(matrix, full_charge_time_text+": "+StringUtil.print_time((int)Math.ceil(energy.getEnergyNeeded() / difference)), 6, draw_y);
      break;
    case -1:
      GuiUtil.draw_text_left(matrix, charge_remaining_text+": "+StringUtil.print_time((int)Math.ceil(energy.getEnergy() / (-difference))), 6, draw_y);
      break;
    case 0:
      GuiUtil.draw_text_left(matrix, no_energy_change_text, 6, draw_y);
      break;
    }
  }

  /** Draws charge time at bottom-center of gui. I think this is generally not used. */
  protected final void draw_energy_difference_center(PoseStack matrix, final int draw_y){
    final int draw_x = imageWidth/2;
    if(energy == null){
      GuiUtil.draw_text_center(matrix, null_energy_reference, draw_x, draw_y);
      return;
    }
    final double difference = energy.getDifference();
    switch((int)Math.signum(difference)){
    case 1:
      GuiUtil.draw_text_center(matrix, full_charge_time_text+": "+StringUtil.print_time((int)Math.ceil(energy.getEnergyNeeded() / difference)), draw_x, draw_y);
      break;
    case -1:
      GuiUtil.draw_text_center(matrix, charge_remaining_text+": "+StringUtil.print_time((int)Math.ceil(energy.getEnergy() / (-difference))), draw_x, draw_y);
      break;
    case 0:
      GuiUtil.draw_text_center(matrix, no_energy_change_text, draw_x, draw_y);
      break;
    }
  }

}
