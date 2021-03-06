package addsynth.overpoweredmod.machines.crystal_matter_generator;

import addsynth.energy.lib.gui.GuiEnergyBase;
import addsynth.energy.lib.gui.widgets.OnOffSwitch;
import addsynth.energy.lib.gui.widgets.WorkProgressBar;
import addsynth.overpoweredmod.OverpoweredTechnology;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public final class GuiCrystalMatterGenerator extends GuiEnergyBase<TileCrystalMatterGenerator, ContainerCrystalGenerator> {

  private static final ResourceLocation crystal_matter_generator_gui_texture =
    new ResourceLocation(OverpoweredTechnology.MOD_ID,"textures/gui/crystal_matter_generator.png");

  private final WorkProgressBar work_progress_bar = new WorkProgressBar(8, 89, 160, 5, 11, 194);
  
  private static final int work_percentage_text_y = 77; // OPTIMIZE any guis that have this variable and only use it once.

  public GuiCrystalMatterGenerator(final ContainerCrystalGenerator container, final Inventory player_inventory, final Component title){
    super(176, 192, container, player_inventory, title, crystal_matter_generator_gui_texture);
  }

  @Override
  public final void init(){
    super.init();
    addRenderableWidget(new OnOffSwitch<>(this.leftPos + 6, this.topPos + 17, tile));
  }

  @Override
  protected final void renderBg(PoseStack matrix, float partialTicks, int mouseX, int mouseY){
    guiUtil.draw_background_texture(matrix);
    work_progress_bar.draw(matrix, this, tile);
  }

  @Override
  protected final void renderLabels(PoseStack matrix, int mouseX, int mouseY){
    guiUtil.draw_title(matrix, this.title);
    draw_status(matrix, tile.getStatus(), 44, 21);
    draw_energy_usage(matrix, 6, 38);
    guiUtil.draw_text_center(matrix, work_progress_bar.getWorkTimeProgress(), work_percentage_text_y);
    draw_time_left(matrix, 98);
  }

}
