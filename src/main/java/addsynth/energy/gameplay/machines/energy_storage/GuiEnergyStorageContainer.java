package addsynth.energy.gameplay.machines.energy_storage;

import addsynth.core.gui.util.GuiUtil;
import addsynth.core.util.StringUtil;
import addsynth.energy.ADDSynthEnergy;
import addsynth.energy.lib.gui.GuiEnergyBase;
import addsynth.energy.lib.gui.widgets.EnergyProgressBar;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public final class GuiEnergyStorageContainer extends GuiEnergyBase<TileEnergyStorage, ContainerEnergyStorage> {

  private static final ResourceLocation energy_storage_gui_texture =
    new ResourceLocation(ADDSynthEnergy.MOD_ID,"textures/gui/energy_storage.png");

  private final String energy_stored_text = StringUtil.translate("gui.addsynth_energy.common.energy_stored");

  private static final int draw_energy_text_y  = 25;
  private static final int draw_energy_level_y = 36;
  private static final int draw_energy_x   = 88;
  private static final int draw_capacity_x = 93;
  private static final int draw_energy_percentage_y = 47;
  private final EnergyProgressBar energy_bar = new EnergyProgressBar(9, 59, 174, 17, 9, 106);

  public GuiEnergyStorageContainer(final ContainerEnergyStorage container, final Inventory player_inventory, final Component title){
    super(190, 94, container, player_inventory, title, energy_storage_gui_texture);
  }

  @Override
  protected final void renderBg(PoseStack matrix, float partialTicks, int mouseX, int mouseY){
    guiUtil.draw_background_texture(matrix);
    energy_bar.drawHorizontal(matrix, this, energy);
  }

  @Override
  protected final void renderLabels(PoseStack matrix, final int mouseX, final int mouseY){
    guiUtil.draw_title(matrix, this.title);
    GuiUtil.draw_text_center(matrix, energy_stored_text+":", guiUtil.center_x, draw_energy_text_y);
    GuiUtil.draw_text_right(matrix, String.format("%.2f", energy.getEnergy()), draw_energy_x, draw_energy_level_y);
    GuiUtil.draw_text_left(matrix, "/ "+energy.getCapacity(), draw_capacity_x, draw_energy_level_y);
    GuiUtil.draw_text_center(matrix, energy_bar.getEnergyPercentage(), guiUtil.center_x, draw_energy_percentage_y);
    draw_energy_difference(matrix, 80);
  }

}
