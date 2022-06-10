package addsynth.energy.lib.gui.widgets;

import addsynth.core.util.StringUtil;
import addsynth.energy.ADDSynthEnergy;
import addsynth.energy.gameplay.NetworkHandler;
import addsynth.energy.lib.network_messages.SwitchMachineMessage;
import addsynth.energy.lib.tiles.machines.ISwitchableMachine;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.components.AbstractButton;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.entity.BlockEntity;

/**
 * Draws a custom button which displays an on/off switch depending on the Machine's running state.
 * Currently, we only use this to toggle the running state of an EnergyReceiver machine.
 */
public final class OnOffSwitch<T extends BlockEntity & ISwitchableMachine> extends AbstractButton {

  private final T tile;
  private static final ResourceLocation gui_switch = new ResourceLocation(ADDSynthEnergy.MOD_ID,"textures/gui/gui_textures.png");

  private final String on_text  = StringUtil.translate("gui.addsynth_energy.switch.on");
  private final String off_text = StringUtil.translate("gui.addsynth_energy.switch.off");

  /**
   * Call with guiLeft + standard x = 6 and guiTop + standard y = 17.
   * @param x
   * @param y
   * @param tile
   */
  public OnOffSwitch(final int x, final int y, final T tile){
    super(x, y, 34, 16, new TextComponent(""));
    this.tile = tile;
  }

  /**
   * Draws the button depending on the running boolean of the TileEnergyReceiver, otherwise it contains the same code
   * as Vanilla draws a GuiButton.
   */
  @Override
  @SuppressWarnings("resource")
  public final void renderButton(PoseStack matrix, final int mouseX, final int mouseY, final float partial_ticks){
    final Minecraft minecraft = Minecraft.getInstance();
    int texture_y = 0;

    if(tile != null){
      if(tile.get_switch_state() == false){
        texture_y = 16;
      }
    }

    RenderSystem.setShaderTexture(0, gui_switch);
    RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);

    // this.hovered = mouseX >= this.xPosition && mouseY >= this.yPosition && mouseX < this.xPosition + this.width && mouseY < this.yPosition + this.height;
    // final int hover_state = this.getHoverState(this.hovered);
    
      RenderSystem.enableBlend();
      RenderSystem.defaultBlendFunc();
      RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);

    this.blit(matrix, x, y, 0, texture_y, width, height);

    final Font fontrenderer = minecraft.font;
    final int text_color = 14737632;
    if(tile != null){
      if(tile.get_switch_state()){
        setMessage(new TextComponent(on_text));
        drawCenteredString(matrix, fontrenderer, on_text, x + 20, y + 4, text_color);
        // TODO: detect state changes and call setMessage() to change what the narrator says when players mouse over this button.
      }
      else{
        setMessage(new TextComponent(off_text));
        drawCenteredString(matrix, fontrenderer, off_text, x + 14, y + 4, text_color);
      }
    }
    else{
      drawCenteredString(matrix, fontrenderer, "[null]", x + (this.width / 2), y + 4, text_color);
    }
  }

  @Override
  public final void onPress(){
    if(tile != null){
      NetworkHandler.INSTANCE.sendToServer(new SwitchMachineMessage(tile.getBlockPos()));
    }
  }

  @Override
  public void updateNarration(NarrationElementOutput p_169152_){
  }

}
