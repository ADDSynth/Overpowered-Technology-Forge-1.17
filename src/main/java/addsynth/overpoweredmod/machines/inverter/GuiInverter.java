package addsynth.overpoweredmod.machines.inverter;

import addsynth.core.gui.util.GuiUtil;
import addsynth.energy.lib.gui.GuiEnergyBase;
import addsynth.energy.lib.gui.widgets.WorkProgressBar;
import addsynth.overpoweredmod.OverpoweredTechnology;
import addsynth.overpoweredmod.config.Config;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;

public final class GuiInverter extends GuiEnergyBase<TileInverter, ContainerInverter> {

  private static final ResourceLocation inverter_gui_texture = new ResourceLocation(OverpoweredTechnology.MOD_ID,"textures/gui/inverter.png");

  private final WorkProgressBar work_progress_bar = new WorkProgressBar(8, 84, 160, 5, 8, 194);
  
  private static final int work_percentage_text_y = 70;

  public GuiInverter(final ContainerInverter container, final Inventory player_inventory, final Component title){
    super(176, 187, container, player_inventory, title, inverter_gui_texture);
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
    
    final ItemStack s1 = tile.getWorkingInventory().getStackInSlot(0);
    if(Config.blend_working_item.get()){
      final ItemStack s2 = TileInverter.getInverted(s1);
      GuiUtil.blendItemStacks(s1, s2, 77, 44, work_progress_bar.getWorkTime());
    }
    else{
      GuiUtil.drawItemStack(s1, 77, 44);
    }
    
    GuiUtil.draw_text_center(matrix, work_progress_bar.getWorkTimeProgress(), guiUtil.center_x, work_percentage_text_y);
    draw_time_left(matrix, 93);
  }

}
