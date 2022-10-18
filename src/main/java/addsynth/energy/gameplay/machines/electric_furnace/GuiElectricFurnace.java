package addsynth.energy.gameplay.machines.electric_furnace;

import addsynth.core.gui.util.GuiUtil;
import addsynth.core.gui.widgets.ProgressBar;
import addsynth.energy.gameplay.reference.GuiReference;
import addsynth.energy.lib.gui.GuiEnergyBase;
import addsynth.energy.lib.gui.widgets.WorkProgressBar;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public final class GuiElectricFurnace extends GuiEnergyBase<TileElectricFurnace, ContainerElectricFurnace> {

  private final WorkProgressBar work_progress_bar = new WorkProgressBar(80, 60, 14, 14, 200, 2);
  
  private static final int work_percentage_text_y = 65;
  private static final int time_left_y = 78;

  public GuiElectricFurnace(final ContainerElectricFurnace container, final Inventory player_inventory, final Component title){
    super(176, 172, container, player_inventory, title, GuiReference.electric_furnace);
  }

  @Override
  protected final void renderBg(PoseStack matrix, float partialTicks, int mouseX, int mouseY){
    guiUtil.draw_background_texture(matrix);
    work_progress_bar.draw(matrix, this, ProgressBar.Direction.BOTTOM_TO_TOP, tile);
  }

  @Override
  protected final void renderLabels(PoseStack matrix, int mouseX, int mouseY){
    guiUtil.draw_title(matrix, this.title);
    draw_energy_usage(matrix);
    draw_status(matrix, tile.getStatus());
    // RenderHelper.enableGUIStandardItemLighting();
    GuiUtil.drawItemStack(tile.getWorkingInventory().getStackInSlot(0), 80, 40);
    GuiUtil.draw_text_center(matrix, work_progress_bar.getWorkTimeProgress(), guiUtil.center_x + 21, work_percentage_text_y);
    draw_time_left(matrix, time_left_y);
  }

}
