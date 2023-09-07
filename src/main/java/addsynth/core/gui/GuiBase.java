package addsynth.core.gui;

import addsynth.core.gui.section.FixedSizeGuiSection;
import addsynth.core.gui.util.GuiUtil;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

/** Extend from this class if you just want your own gui screen. */
public abstract class GuiBase extends Screen {

  protected final ResourceLocation GUI_TEXTURE;
  /** This contains all the dimensions of the main gui image.
   *  It must be set in the init method. */
  protected final FixedSizeGuiSection guiBox;

  /** Center of gui. Used for drawing text. */
  protected final int center_x;
  /** Right edge of gui. Used for drawing text against the right edge.
   *  This is equivalent to <code>{@link GuiBase#guiWidth guiWidth} - 6</code>. */
  protected final int right_edge;

  protected GuiBase(final int width, final int height, final Component title, final ResourceLocation gui_texture){
    super(title);
    GUI_TEXTURE = gui_texture;
    guiBox = new FixedSizeGuiSection(width, height);
    center_x = width / 2;
    right_edge = width - 6;
  }

// ========================================================================================================
  
  /** Draws entire gui texture. (at default texture width and height of 256x256.) */
  protected final void draw_background_texture(final PoseStack matrix){
    RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
    RenderSystem.setShaderTexture(0, GUI_TEXTURE);
    GuiComponent.blit(matrix, guiBox.left, guiBox.top, guiBox.width, guiBox.height, 0, 0, guiBox.width, guiBox.height, 256, 256);
  }

  /** Draws the background texture with custom scaled width and height. Use this
   *  if you have a background texture that is not the default size of 256x256.
   * @param texture_width
   * @param texture_height
   */
  protected final void draw_custom_background_texture(final PoseStack matrix, final int texture_width, int texture_height){
    RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
    RenderSystem.setShaderTexture(0, GUI_TEXTURE);
    GuiComponent.blit(matrix, guiBox.left, guiBox.top, guiBox.width, guiBox.height, 0, 0, guiBox.width, guiBox.height, texture_width, texture_height);
  }

  /** Draws another part of the gui texture at the coordinates you specify.
   *  This draws the texture portion the same size that is drawn on the gui.
   *  Assumes a default texture size of 256x256. */
  protected final void draw(final PoseStack matrix, final int x, final int y, final int u, final int v, final int width, final int height){
    RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
    RenderSystem.setShaderTexture(0, GUI_TEXTURE);
    GuiComponent.blit(matrix, guiBox.left + x, guiBox.top + y, width, height, u, v, width, height, 256, 256);
  }

  /** Draws another part of the gui texture at the coordinates you specify.
   *  If you need more control over how it's drawn, you might as well use the
   *  vanilla blit() function. */
  protected final void draw(final PoseStack matrix, int x, int y, int u, int v, int width, int height, int texture_width, int texture_height){
    RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
    RenderSystem.setShaderTexture(0, GUI_TEXTURE);
    GuiComponent.blit(matrix, guiBox.left + x, guiBox.top + y, width, height, u, v, texture_width, texture_height, 256, 256);
  }

// ========================================================================================================
  
  protected final void draw_title(final PoseStack matrix){
    font.draw(matrix, title, center_x - (font.width(title) / 2), 6, GuiUtil.text_color);
  }

  /** This will render the string in a different color if you prefix the string with
   *  {@link net.minecraft.ChatFormatting ChatFormatting.COLOR.toString()}. */
  protected final void draw_text_left(final PoseStack matrix, final String text, final int x, final int y){
    font.draw(matrix, text, x, y, GuiUtil.text_color);
  }

  /** Draws center-aligned text at the center of the gui.<br />
   *  This will render the string in a different color if you prefix the string with
   *  {@link net.minecraft.ChatFormatting ChatFormatting.COLOR.toString()}. */
  protected final void draw_text_center(final PoseStack matrix, final String text, final int y){
    font.draw(matrix, text, center_x - (font.width(text) / 2), y, GuiUtil.text_color);
  }

  /** Vanilla has their own method but mine assumes a few arguments to make it easier.<br />
   *  This will render the string in a different color if you prefix the string with
   *  {@link net.minecraft.ChatFormatting ChatFormatting.COLOR.toString()}.
   * @see net.minecraft.client.gui.GuiComponent#drawCenteredString
   * @param text
   * @param x
   * @param y
   */
  protected final void draw_text_center(final PoseStack matrix, final String text, final int x, final int y){
    font.draw(matrix, text, x - (font.width(text) / 2), y, GuiUtil.text_color);
  }

  /** Draws along the right-edge of the gui.<br />
   *  This will render the string in a different color if you prefix the string with
   *  {@link net.minecraft.ChatFormatting ChatFormatting.COLOR.toString()}. */
  protected final void draw_text_right(final PoseStack matrix, final String text, final int y){
    font.draw(matrix, text, right_edge - font.width(text), y, GuiUtil.text_color);
  }

  /** Draws the text right-aligned.
   *  This will render the string in a different color if you prefix the string with
   *  {@link net.minecraft.ChatFormatting ChatFormatting.COLOR.toString()}. */
  protected final void draw_text_right(final PoseStack matrix, final String text, final int x, final int y){
    font.draw(matrix, text, x - font.width(text), y, GuiUtil.text_color);
  }

// ========================================================================================================

  @Override
  protected void init(){
    guiBox.screenResize(width, height);
  }

  @Override
  public void render(PoseStack matrix, int mouse_x, int mouse_y, float partialTicks){
    // REPLICA: This is a replica of AbstractContainerScreen.render() but without drawing any ItemStacks. Make sure it matches whenever we update Forge.
    super.renderBackground(matrix);
    drawGuiBackgroundLayer(matrix, partialTicks, mouse_x, mouse_y);
    // net.minecraftforge.common.MinecraftForge.EVENT_BUS.pose(new net.minecraftforge.client.event.GuiContainerEvent.DrawBackground(this, mouse_x, mouse_y));
    RenderSystem.disableDepthTest();
    super.render(matrix, mouse_x, mouse_y, partialTicks);
    PoseStack posestack = RenderSystem.getModelViewStack();
    posestack.pushPose();
    posestack.translate(guiBox.left, guiBox.top, 0.0);
    RenderSystem.applyModelViewMatrix();
    RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
    
    drawGuiForegroundLayer(matrix, mouse_x, mouse_y);
    posestack.popPose();
    RenderSystem.applyModelViewMatrix();
    RenderSystem.enableDepthTest();
  }

  @Override
  public final boolean isPauseScreen(){
    return false;
  }

  protected void drawGuiBackgroundLayer(PoseStack matrix, float partialTicks, int mouse_x, int mouse_y){
    draw_background_texture(matrix);
  }
  
  protected void drawGuiForegroundLayer(PoseStack matrix, int mouse_x, int mouse_y){
  }

}
