package addsynth.overpoweredmod.machines.fusion.chamber;

import addsynth.core.gui.GuiContainerBase;
import addsynth.overpoweredmod.OverpoweredTechnology;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public final class GuiFusionChamber extends GuiContainerBase<ContainerFusionChamber> {

  private static final ResourceLocation fusion_chamber_gui = new ResourceLocation(OverpoweredTechnology.MOD_ID,"textures/gui/portal_frame.png");

  public GuiFusionChamber(final ContainerFusionChamber container, final Inventory player_inventory, final Component title){
    super(container, player_inventory, title, fusion_chamber_gui);
  }

  @Override
  protected final void renderLabels(PoseStack matrix, final int mouseX, final int mouseY){
    guiUtil.draw_title(matrix, this.title);
  }

}
