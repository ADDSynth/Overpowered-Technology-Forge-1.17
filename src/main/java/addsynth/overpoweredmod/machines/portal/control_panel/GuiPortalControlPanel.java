package addsynth.overpoweredmod.machines.portal.control_panel;

import addsynth.core.gui.util.GuiUtil;
import addsynth.core.gui.widgets.buttons.AdjustableButton;
import addsynth.core.util.StringUtil;
import addsynth.energy.lib.gui.GuiEnergyBase;
import addsynth.energy.lib.gui.widgets.AutoShutoffCheckbox;
import addsynth.energy.lib.gui.widgets.EnergyProgressBar;
import addsynth.energy.lib.gui.widgets.OnOffSwitch;
import addsynth.energy.lib.gui.widgets.WorkProgressBar;
import addsynth.overpoweredmod.OverpoweredTechnology;
import addsynth.overpoweredmod.game.NetworkHandler;
import addsynth.overpoweredmod.game.core.Gems;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;

public final class GuiPortalControlPanel extends GuiEnergyBase<TilePortalControlPanel, ContainerPortalControlPanel> {

  private static final ResourceLocation portal_control_panel_gui_texture =
    new ResourceLocation(OverpoweredTechnology.MOD_ID,"textures/gui/portal_control_panel.png");

  // It's efficient to create each ItemStack only ONCE when the gui is constructed then pass it as a reference when drawing the ItemStack.
  private static final ItemStack[] gem_block = new ItemStack[] {
    new ItemStack(Gems.RUBY.block_item,1),
    new ItemStack(Gems.TOPAZ.block_item,1),
    new ItemStack(Gems.CITRINE.block_item,1),
    new ItemStack(Gems.EMERALD.block_item,1),
    new ItemStack(Gems.DIAMOND.block_item,1),
    new ItemStack(Gems.SAPPHIRE.block_item,1),
    new ItemStack(Gems.AMETHYST.block_item,1),
    new ItemStack(Gems.QUARTZ.block_item,1)
  };

  private static final int checkbox_x = 80;
  private static final int checkbox_y = 19;

  private static final int energy_percentage_y = 46;
  private static final int energy_change_y = 58;

  private final EnergyProgressBar energy_bar = new EnergyProgressBar(193, 57, 17, 64, 227, 24);

  private static final ResourceLocation gui_icons = new ResourceLocation(OverpoweredTechnology.MOD_ID,"textures/gui/gui_textures.png");
  private static final int image_x = 14;
  private static final int image_y = 69;
  private static final int space_x = 44;
  private static final int space_y = 18;

  private static final int button_x = 27;
  private static final int button_y = 106;
  private static final int button_width = 136;
  private static final int button_height = 16;
  
  private static final int status_message_y = button_y + button_height + 6;

  public GuiPortalControlPanel(final ContainerPortalControlPanel container, final Inventory player_inventory, final Component title){
    super(218, 144, container, player_inventory, title, portal_control_panel_gui_texture);
  }

  private static final class GeneratePortalButton extends AdjustableButton {

    private final TilePortalControlPanel tile;

    public GeneratePortalButton(final int x, final int y, final TilePortalControlPanel tile){
      super(x, y, button_width, button_height, StringUtil.translate("gui.overpowered.portal_control_panel.generate_portal"));
      this.tile = tile;
    }

    @Override
    public void renderButton(PoseStack matrix, int p_renderButton_1_, int p_renderButton_2_, float p_renderButton_3_){
      this.active = tile.isValid();
      super.renderButton(matrix, p_renderButton_1_, p_renderButton_2_, p_renderButton_3_);
    }

    @Override
    public void onPress(){
      NetworkHandler.INSTANCE.sendToServer(new GeneratePortalMessage(tile.getBlockPos()));
    }

  }

  @Override
  public final void init(){
    super.init();
    addRenderableWidget(new OnOffSwitch<>(this.leftPos + 6, this.topPos + 19, tile));
    addRenderableWidget(new AutoShutoffCheckbox<TilePortalControlPanel>(this.leftPos + checkbox_x, this.topPos + checkbox_y, tile));
    addRenderableWidget(new GeneratePortalButton(this.leftPos + button_x, this.topPos + button_y, tile));
  }

  @Override
  protected final void renderBg(PoseStack matrix, float partialTicks, int mouseX, int mouseY){
    guiUtil.draw_background_texture(matrix);
    energy_bar.drawVertical(matrix, this, energy);
    draw_portal_items(matrix);
  }

  @Override
  protected final void renderLabels(PoseStack matrix, final int mouseX, final int mouseY){
    guiUtil.draw_title(matrix, this.title);
    draw_energy(matrix, 44, 34);
    draw_status(matrix, tile.getStatus(), energy_percentage_y);
    guiUtil.draw_text_right(matrix, WorkProgressBar.getWorkTimeProgress(tile), energy_percentage_y);
    draw_energy_difference(matrix, energy_change_y);
    guiUtil.draw_text_center(matrix, tile.getMessage(), status_message_y);
  }
  
  /**
   * This is used to show non-interactable slots on the Gui showing each of the 8 gem blocks, and shows a
   * check mark or X by each of them indicating if the portal frame has it or not.
   */
  private final void draw_portal_items(final PoseStack matrix){
    // GuiContainer class -> drawScreen() method -> Line 100
    // GuiContainer class -> drawSlot() method -> Line 298
    int i;
    int j;
    int index;
    int x;
    int y;
    // RenderHelper.enableGUIStandardItemLighting();
    for(j = 0; j < 2; j++){
      for(i = 0; i < 4; i++){
        index = (j*4) + i;
        x = this.leftPos + image_x + (i * space_x);
        y = this.topPos + image_y + (j * space_y);
        GuiUtil.drawItemStack(gem_block[index], x, y);
      }
    }
    RenderSystem.setShaderTexture(0, gui_icons);
    for(j = 0; j < 2; j++){
      for(i = 0; i < 4; i++){
        index = (j*4) + i;
        x = this.leftPos + image_x + (i * space_x) + 16;
        y = this.topPos + image_y + (j * space_y);
        if(tile.getPortalItem(index)){
          blit(matrix, x, y, 64, 0, 16, 16);
        }
        else{
          blit(matrix, x, y, 80, 0, 16, 16);
        }
      }
    }
  }

}
