package addsynth.energy.gameplay.machines.generator;

import addsynth.core.gui.util.GuiUtil;
import addsynth.core.util.StringUtil;
import addsynth.energy.ADDSynthEnergy;
import addsynth.energy.lib.gui.GuiEnergyBase;
import addsynth.energy.lib.gui.widgets.EnergyProgressBar;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public final class GuiGenerator extends GuiEnergyBase<TileGenerator, ContainerGenerator> {

  private static final ResourceLocation generator_gui_texture = new ResourceLocation(ADDSynthEnergy.MOD_ID, "textures/gui/generator.png");

  private final String input_text   = StringUtil.translate("gui.addsynth_energy.generator.input");
  private final String extract_text = StringUtil.translate("gui.addsynth_energy.generator.max_extract");

  private final EnergyProgressBar energy_progress_bar = new EnergyProgressBar(8, 68, 168, 20, 8, 182);

  private static final int input_text_x = 52;
  private static final int input_text_y = 24;

  private static final int extract_text_x = 78;
  private static final int extract_text_line_1 = 24; // was 19 to accomodate line 2.
  // private static final int extract_text_line_2 = 31;

  private static final int energy_text_line_1 = 44;
  private static final int energy_text_line_2 = 56;

  public GuiGenerator(ContainerGenerator container, Inventory player_inventory, Component title){
    super(184, 176, container, player_inventory, title, generator_gui_texture);
  }

  @Override
  protected final void renderBg(PoseStack matrix, float partialTicks, int mouseX, int mouseY){
    guiUtil.draw_background_texture(matrix);
    energy_progress_bar.drawHorizontal(matrix, this, energy);
  }

  @Override
  protected final void renderLabels(PoseStack matrix, final int mouseX, final int mouseY){
    guiUtil.draw_title(matrix, this.title);
    GuiUtil.draw_text_right(matrix, input_text+":",input_text_x,input_text_y);
    
    GuiUtil.draw_text_left(matrix, extract_text+": " + energy.getMaxExtract(),extract_text_x,extract_text_line_1);
    // draw_text_left("Energy Draw: "+energy_draw,extract_text_x,extract_text_line_2);
    
    draw_energy(matrix, 6, energy_text_line_1);
    GuiUtil.draw_text_center(matrix, energy_progress_bar.getEnergyPercentage(), guiUtil.center_x, energy_text_line_2);
    draw_energy_difference(matrix, 82);
  }
}
