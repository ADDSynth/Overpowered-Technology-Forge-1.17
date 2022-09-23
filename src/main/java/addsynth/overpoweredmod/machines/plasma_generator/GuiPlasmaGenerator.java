package addsynth.overpoweredmod.machines.plasma_generator;

import addsynth.core.gui.util.GuiUtil;
import addsynth.energy.lib.gui.GuiEnergyBase;
import addsynth.energy.lib.gui.widgets.OnOffSwitch;
import addsynth.energy.lib.gui.widgets.WorkProgressBar;
import addsynth.overpoweredmod.OverpoweredTechnology;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public final class GuiPlasmaGenerator extends GuiEnergyBase<TilePlasmaGenerator, ContainerPlasmaGenerator> {

  private static final ResourceLocation crystal_matter_generator_gui_texture =
    new ResourceLocation(OverpoweredTechnology.MOD_ID,"textures/gui/plasma_generator.png");

  private final WorkProgressBar work_progress_bar = new WorkProgressBar(8, 73, 166, 5, 7, 184);
  
  public GuiPlasmaGenerator(final ContainerPlasmaGenerator container, final Inventory player_inventory, final Component title){
    super(183, 176, container, player_inventory, title, crystal_matter_generator_gui_texture);
  }

  @Override
  public final void init(){
    super.init();
    addRenderableWidget(new OnOffSwitch<>(this, tile));
  }

  @Override
  protected final void renderBg(PoseStack matrix, float partialTicks, int mouseX, int mouseY){
    guiUtil.draw_background_texture(matrix);
    work_progress_bar.draw(matrix, this, tile);
  }

  @Override
  protected final void renderLabels(PoseStack matrix, int mouseX, int mouseY){
    guiUtil.draw_title(matrix, this.title);
    draw_status_after_switch(matrix, tile.getStatus());
    draw_energy_usage(matrix, 6, 38);
    GuiUtil.draw_text_center(matrix, work_progress_bar.getWorkTimeProgress(), 40, 62);
    draw_time_left(matrix, 82);
  }

}
