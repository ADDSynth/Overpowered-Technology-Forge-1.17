package addsynth.core.gui.widgets.scrollbar;

import addsynth.core.ADDSynthCore;
import addsynth.core.util.StringUtil;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.components.AbstractButton;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.sounds.SoundManager;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.resources.ResourceLocation;

public abstract class AbstractListEntry<E> extends AbstractButton {

  private final ResourceLocation highlight_texture = new ResourceLocation(ADDSynthCore.MOD_ID, "textures/gui/gui_textures.png");
  private final int texture_x = 0;
  private final int texture_y = 224;

  protected int entry_id;
  // private String text; // not much of a performance benefit
  protected boolean selected;
  protected AbstractScrollbar responder;

  public AbstractListEntry(int x, int y, int width, int height){
    super(x, y, width, height, new TextComponent(""));
  }

  public final void setScrollbar(final AbstractScrollbar scrollbar){
    this.responder = scrollbar;
  }

  /** This is the code that draws a transparent white box under the list entry
   *  whenever the player mouses over this list entry or it is selected.   */
  protected final void drawListEntryHighlight(final PoseStack matrix){
    if((isHovered && StringUtil.StringExists(getMessage().getString())) || selected){
      RenderSystem.setShaderTexture(0, highlight_texture);
      RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
      RenderSystem.enableBlend();
      RenderSystem.defaultBlendFunc();
      RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
      blit(matrix, x, y, texture_x, texture_y, width, height);
    }
  }

  @Override
  public void onPress(){
    if(StringUtil.StringExists(getMessage().getString())){
      responder.setSelected(entry_id, true, false);
    }
  }

  public abstract void set(final int entry_id, final E value);

  /** Do not use this to set the selected entry. Use Scrollbar.setSelected(). */
  public void setSelected(final int selected_entry_id){
    this.selected = selected_entry_id >= 0 && entry_id == selected_entry_id;
  }

  public abstract void setNull();

  @Override
  public final void playDownSound(SoundManager p_playDownSound_1_){
  }

  @Override
  public final void updateNarration(NarrationElementOutput p_169152_){
  }

}
