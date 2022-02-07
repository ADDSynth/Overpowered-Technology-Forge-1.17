package addsynth.core.gui;

import addsynth.core.gui.util.GuiUtil;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;

/** Extend from this class if your gui screen contains item slots. */
public abstract class GuiContainerBase<T extends AbstractContainerMenu> extends AbstractContainerScreen<T> {

  protected final GuiUtil guiUtil;
  
  public GuiContainerBase(final T container, final Inventory player_inventory, final Component title, final ResourceLocation gui_texture){
    super(container, player_inventory, title);
    guiUtil = new GuiUtil(gui_texture, 176, 166);
  }

  public GuiContainerBase(int width, int height, T container, Inventory player_inventory, Component title, ResourceLocation gui_texture){
    super(container, player_inventory, title);
    guiUtil = new GuiUtil(gui_texture, width, height);
    this.imageWidth = width;
    this.imageHeight = height;
  }

  @Override
  public void render(PoseStack matrix, final int mouseX, final int mouseY, final float partialTicks){
    this.renderBackground(matrix);
    super.render(matrix, mouseX, mouseY, partialTicks);
    this.renderTooltip(matrix, mouseX, mouseY);
  }

  @Override
  protected void renderBg(PoseStack matrix, final float partialTicks, final int mouseX, final int mouseY){
    guiUtil.draw_background_texture(matrix);
  }

}
