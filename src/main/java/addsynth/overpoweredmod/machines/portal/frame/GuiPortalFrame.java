package addsynth.overpoweredmod.machines.portal.frame;

import addsynth.core.gui.GuiContainerBase;
import addsynth.overpoweredmod.OverpoweredTechnology;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public final class GuiPortalFrame extends GuiContainerBase<ContainerPortalFrame> {

  private static final ResourceLocation portal_frame_gui_texture =
    new ResourceLocation(OverpoweredTechnology.MOD_ID,"textures/gui/portal_frame.png");

  public GuiPortalFrame(final ContainerPortalFrame container, final Inventory player_inventory, final Component title){
    super(container, player_inventory, title, portal_frame_gui_texture);
  }

  @Override
  protected final void renderLabels(PoseStack matrix, final int mouseX, final int mouseY){
    guiUtil.draw_title(matrix, this.title);
  }

}
