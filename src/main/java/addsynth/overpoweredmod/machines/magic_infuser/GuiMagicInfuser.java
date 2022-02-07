package addsynth.overpoweredmod.machines.magic_infuser;

import addsynth.core.gui.util.GuiUtil;
import addsynth.energy.lib.gui.GuiEnergyBase;
import addsynth.energy.lib.gui.widgets.WorkProgressBar;
import addsynth.overpoweredmod.OverpoweredTechnology;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public final class GuiMagicInfuser extends GuiEnergyBase<TileMagicInfuser, ContainerMagicInfuser> {

  private static final ResourceLocation magic_infuser_gui_texture =
    new ResourceLocation(OverpoweredTechnology.MOD_ID,"textures/gui/magic_infuser.png");

  private static final int work_percentage_text_y = 72;

  private final WorkProgressBar work_progress_bar = new WorkProgressBar(8, 84, 160, 5, 8, 194);
  
  public GuiMagicInfuser(final ContainerMagicInfuser container, final Inventory player_inventory, final Component title){
    super(176, 187, container, player_inventory, title, magic_infuser_gui_texture);
  }

  @Override
  protected final void renderBg(PoseStack matrix, float partialTicks, int mouseX, int mouseY){
    guiUtil.draw_background_texture(matrix);
    work_progress_bar.draw(matrix, this, tile);
  }

  @Override
  protected final void renderLabels(PoseStack matrix, int mouseX, int mouseY){
    guiUtil.draw_title(matrix, this.title);
    draw_energy_usage(matrix);
    draw_status(matrix, tile.getStatus());
    GuiUtil.drawItemStack(tile.getWorkingInventory().getStackInSlot(0), 78, 44);
    GuiUtil.drawItemStack(tile.getWorkingInventory().getStackInSlot(1), 95, 44);
    GuiUtil.draw_text_center(matrix, work_progress_bar.getWorkTimeProgress(), guiUtil.center_x, work_percentage_text_y);
    draw_time_left(matrix, 93);
  }

}
