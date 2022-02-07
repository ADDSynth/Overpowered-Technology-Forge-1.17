package addsynth.overpoweredmod.machines.identifier;

import addsynth.core.gui.util.GuiUtil;
import addsynth.energy.lib.gui.GuiEnergyBase;
import addsynth.energy.lib.gui.widgets.WorkProgressBar;
import addsynth.overpoweredmod.OverpoweredTechnology;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public final class GuiIdentifier extends GuiEnergyBase<TileIdentifier, ContainerIdentifier> {

  private static final ResourceLocation identifier_gui_texture = new ResourceLocation(OverpoweredTechnology.MOD_ID,"textures/gui/identifier.png");

  private static final int work_percentage_y = 63;

  private final WorkProgressBar work_progress_bar = new WorkProgressBar(8, 75, 160, 5, 11, 184);

  public GuiIdentifier(final ContainerIdentifier container, final Inventory player_inventory, final Component title){
    super(176, 169, container, player_inventory, title, identifier_gui_texture);
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
    GuiUtil.drawItemStack(tile.getWorkingInventory().getStackInSlot(0), 76, 41);
    GuiUtil.draw_text_center(matrix, work_progress_bar.getWorkTimeProgress(), guiUtil.center_x, work_percentage_y);
  }

}
