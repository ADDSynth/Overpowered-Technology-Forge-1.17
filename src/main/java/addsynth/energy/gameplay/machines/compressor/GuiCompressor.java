package addsynth.energy.gameplay.machines.compressor;

import addsynth.core.gui.util.GuiUtil;
import addsynth.energy.ADDSynthEnergy;
import addsynth.energy.lib.gui.GuiEnergyBase;
import addsynth.energy.lib.gui.widgets.WorkProgressBar;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public final class GuiCompressor extends GuiEnergyBase<TileCompressor, ContainerCompressor> {

  private static final ResourceLocation compressor_gui_texture = new ResourceLocation(ADDSynthEnergy.MOD_ID,"textures/gui/compressor.png");

  private static final int work_percentage_text_y = 67;
  private static final int time_left_y = 88;

  private final WorkProgressBar work_progress_bar = new WorkProgressBar(8, 79, 160, 5, 8, 194);
  
  public GuiCompressor(final ContainerCompressor container, final Inventory player_inventory, final Component title){
    super(176, 182, container, player_inventory, title, compressor_gui_texture);
  }

  @Override
  protected final void renderBg(PoseStack matrix, final float partialTicks, final int mouseX, final int mouseY){
    guiUtil.draw_background_texture(matrix);
    work_progress_bar.draw(matrix, this, tile);
  }

  @Override
  protected final void renderLabels(PoseStack matrix, final int mouseX, final int mouseY){
    guiUtil.draw_title(matrix, this.title);
    draw_energy_usage(matrix);
    draw_status(matrix, tile.getStatus());
    GuiUtil.drawItemStack(tile.getWorkingInventory().getStackInSlot(0), 80, 42);
    GuiUtil.draw_text_center(matrix, work_progress_bar.getWorkTimeProgress(), guiUtil.center_x, work_percentage_text_y);
    draw_time_left(matrix, time_left_y);
  }

}
