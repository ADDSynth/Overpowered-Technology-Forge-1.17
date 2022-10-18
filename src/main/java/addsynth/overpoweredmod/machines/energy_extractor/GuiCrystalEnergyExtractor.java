package addsynth.overpoweredmod.machines.energy_extractor;

import addsynth.core.gui.util.GuiUtil;
import addsynth.core.util.StringUtil;
import addsynth.energy.lib.gui.GuiEnergyBase;
import addsynth.energy.lib.gui.widgets.EnergyProgressBar;
import addsynth.overpoweredmod.game.reference.GuiReference;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public final class GuiCrystalEnergyExtractor extends GuiEnergyBase<TileCrystalEnergyExtractor, ContainerCrystalEnergyExtractor> {

  private final String input_text   = StringUtil.translate("gui.overpowered.crystal_energy_generator.input");
  private final EnergyProgressBar energy_progress_bar = new EnergyProgressBar(8, 80, 168, 20, 8, 194);

  private static final int input_text_x = 79;
  private static final int input_text_y = 24;

  private static final int line_1 = 44;
  private static final int line_2 = 56;
  private static final int line_3 = 68;

  public GuiCrystalEnergyExtractor(final ContainerCrystalEnergyExtractor container, final Inventory player_inventory, final Component title){
    super(184, 188, container, player_inventory, title, GuiReference.crystal_energy_extractor);
  }

  @Override
  protected final void renderBg(PoseStack matrix, float partialTicks, int mouseX, int mouseY){
    guiUtil.draw_background_texture(matrix);
    energy_progress_bar.drawHorizontal(matrix, this, energy);
  }

  @Override
  protected final void renderLabels(PoseStack matrix, final int mouseX, final int mouseY){
    guiUtil.draw_title(matrix, this.title);
    GuiUtil.draw_text_right(matrix, input_text+":", input_text_x, input_text_y);
    
    draw_energy(matrix, 6, line_1);
    draw_energy_extraction(matrix, line_2);
    GuiUtil.draw_text_center(matrix, energy_progress_bar.getEnergyPercentage(), guiUtil.center_x, line_3);
    draw_energy_difference(matrix, 94);
  }

}
