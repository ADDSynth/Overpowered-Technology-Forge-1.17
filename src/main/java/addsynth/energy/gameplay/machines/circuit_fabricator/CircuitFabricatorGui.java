package addsynth.energy.gameplay.machines.circuit_fabricator;

import addsynth.core.gui.util.GuiUtil;
import addsynth.core.gui.widgets.WidgetUtil;
import addsynth.core.gui.widgets.item.IngredientWidgetGroup;
import addsynth.core.util.StringUtil;
import addsynth.energy.ADDSynthEnergy;
import addsynth.energy.gameplay.EnergyItems;
import addsynth.energy.gameplay.NetworkHandler;
import addsynth.energy.lib.gui.GuiEnergyBase;
import addsynth.energy.lib.gui.widgets.WorkProgressBar;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.components.AbstractButton;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;

public final class CircuitFabricatorGui extends GuiEnergyBase<TileCircuitFabricator, CircuitFabricatorContainer> {

  private static final ResourceLocation gui_texture = new ResourceLocation(ADDSynthEnergy.MOD_ID, "textures/gui/circuit_fabricator.png");

  private final String selected_text = StringUtil.translate("gui.addsynth_energy.common.selected");

  private static final ItemStack[] circuit_stack = {
    new ItemStack(EnergyItems.circuit_tier_1), new ItemStack(EnergyItems.circuit_tier_2),
    new ItemStack(EnergyItems.circuit_tier_3), new ItemStack(EnergyItems.circuit_tier_4),
    new ItemStack(EnergyItems.circuit_tier_5), new ItemStack(EnergyItems.circuit_tier_6),
    new ItemStack(EnergyItems.circuit_tier_7), new ItemStack(EnergyItems.circuit_tier_8)
  };

  private final WorkProgressBar work_progress_bar = new WorkProgressBar(153, 125, 58, 5, 8, 239);

  private static final int circuit_button_x = 10;
  private static final int circuit_button_y = 54;
  private static final int spacing = 2;

  private static final int down_arrow_texture_x = 228;
  private static final int down_arrow_texture_y = 201;
  private static final int up_arrow_texture_x = 228;
  private static final int up_arrow_texture_y = 217;
  private static final int[] arrow_draw_x = {77, 95, 113, 131};
  private static final int[] arrow_draw_y = {65, 113};
  private static final int[] ingredient_draw_x = {76, 94, 112, 130, 76, 94, 112, 130};
  private static final int[] ingredient_draw_y = {48, 48, 48, 48, 122, 122, 122, 122};
  
  private static final IngredientWidgetGroup recipe_ingredients = new IngredientWidgetGroup(8);
  
  public CircuitFabricatorGui(final CircuitFabricatorContainer container, Inventory player_inventory, Component title){
    super(221, 233, container, player_inventory, title, gui_texture);
    tile.updateGui(); // update displayed recipe, in case player opens another Circuit Fabricator
  }

  private final class CircuitButton extends AbstractButton {

    private final ResourceLocation button_texture = new ResourceLocation(ADDSynthEnergy.MOD_ID, "textures/gui/gui_textures.png");
    public static final int button_size = 18;
    private static final int button_texture_size = 36;
    // private final TileCircuitFabricator tile;
    // private final ItemStack circuit;
    private final int circuit_id;

    public CircuitButton(int x, int y, TileCircuitFabricator tile, int circuit_id){
      super(x, y, button_size, button_size, new TextComponent(""));
      // this.circuit = new ItemStack(EnergyItems.circuit[circuit_id], 1);
      this.circuit_id = circuit_id;
    }

    @Override
    public void renderButton(PoseStack matrix, int p_renderButton_1_, int p_renderButton_2_, float p_renderButton_3_){
      WidgetUtil.renderButton(matrix, this, button_texture, 64, 80, button_size, button_size, button_texture_size, button_texture_size);
      GuiUtil.drawItemStack(circuit_stack[circuit_id], x+1, y+1);
    }

    @Override
    public void onPress(){
      NetworkHandler.INSTANCE.sendToServer(new ChangeCircuitCraftType(tile.getBlockPos(), circuit_id));
    }

    @Override
    public void updateNarration(NarrationElementOutput p_169152_){
    }
  
  }

  @Override
  protected final void init(){
    super.init();
    
    // circuit buttons
    int x;
    int y;
    int circuit_id;
    for(y = 0; y < 3; y++){
      for(x = 0; x < 3; x++){
        circuit_id = x + (y * 3);
        if(circuit_id < 8){
          addRenderableWidget(new CircuitButton(
            guiUtil.guiLeft + circuit_button_x + (x * (CircuitButton.button_size + spacing)),
            guiUtil.guiTop  + circuit_button_y + (y * (CircuitButton.button_size + spacing)), this.tile, circuit_id
          ));
        }
      }
    }
  }

  public static final void updateRecipeDisplay(final ItemStack[][] recipe){
    recipe_ingredients.setRecipe(recipe);
  }

  @Override
  public final void containerTick(){
    recipe_ingredients.tick();
  }

  @Override
  protected final void renderBg(PoseStack matrix, float partialTicks, int mouseX, int mouseY){
    guiUtil.draw_background_texture(matrix);
    work_progress_bar.draw(matrix, this, tile);
    // draw arrows and ingredients
    final int length = recipe_ingredients.getLength();
    if(length >= 1){
      guiUtil.draw(matrix, arrow_draw_x[0], arrow_draw_y[0], down_arrow_texture_x, down_arrow_texture_y, 14, 8, 28, 16);
      recipe_ingredients.drawIngredient(0, leftPos + ingredient_draw_x[0], topPos + ingredient_draw_y[0]);
    }
    if(length >= 2){
      guiUtil.draw(matrix, arrow_draw_x[1], arrow_draw_y[0], down_arrow_texture_x, down_arrow_texture_y, 14, 8, 28, 16);
      recipe_ingredients.drawIngredient(1, leftPos + ingredient_draw_x[1], topPos + ingredient_draw_y[1]);
    }
    if(length >= 3){
      guiUtil.draw(matrix, arrow_draw_x[2], arrow_draw_y[0], down_arrow_texture_x, down_arrow_texture_y, 14, 8, 28, 16);
      recipe_ingredients.drawIngredient(2, leftPos + ingredient_draw_x[2], topPos + ingredient_draw_y[2]);
    }
    if(length >= 4){
      guiUtil.draw(matrix, arrow_draw_x[3], arrow_draw_y[0], down_arrow_texture_x, down_arrow_texture_y, 14, 8, 28, 16);
      recipe_ingredients.drawIngredient(3, leftPos + ingredient_draw_x[3], topPos + ingredient_draw_y[3]);
    }
    if(length >= 5){
      guiUtil.draw(matrix, arrow_draw_x[0], arrow_draw_y[1],   up_arrow_texture_x,   up_arrow_texture_y, 14, 8, 28, 16);
      recipe_ingredients.drawIngredient(4, leftPos + ingredient_draw_x[4], topPos + ingredient_draw_y[4]);
    }
    if(length >= 6){
      guiUtil.draw(matrix, arrow_draw_x[1], arrow_draw_y[1],   up_arrow_texture_x,   up_arrow_texture_y, 14, 8, 28, 16);
      recipe_ingredients.drawIngredient(5, leftPos + ingredient_draw_x[5], topPos + ingredient_draw_y[5]);
    }
    if(length >= 7){
      guiUtil.draw(matrix, arrow_draw_x[2], arrow_draw_y[1],   up_arrow_texture_x,   up_arrow_texture_y, 14, 8, 28, 16);
      recipe_ingredients.drawIngredient(6, leftPos + ingredient_draw_x[6], topPos + ingredient_draw_y[6]);
    }
    if(length >= 8){
      guiUtil.draw(matrix, arrow_draw_x[3], arrow_draw_y[1],   up_arrow_texture_x,   up_arrow_texture_y, 14, 8, 28, 16);
      recipe_ingredients.drawIngredient(7, leftPos + ingredient_draw_x[7], topPos + ingredient_draw_y[7]);
    }
  }

  @Override
  protected final void renderLabels(PoseStack matrix, int mouseX, int mouseY){
    guiUtil.draw_title(matrix, this.title);
    draw_energy_usage(matrix);
    draw_status(matrix, tile.getStatus());
    GuiUtil.draw_text_left(matrix, selected_text+": "+tile.getCircuitSelected(), 6, 39);
    // GuiUtil.drawItemStack(circuit_stack[tile.getCircuitID()], 102, 29);
    GuiUtil.draw_text_center(matrix, work_progress_bar.getWorkTimeProgress(), 184, 113);
    draw_time_left(matrix, 140);
  }

  @Override
  protected final void renderTooltip(PoseStack matrix, int mouseX, int mouseY){
    super.renderTooltip(matrix, mouseX, mouseY);
    // draw ingredient tooltips
    recipe_ingredients.drawTooltips(matrix, this, leftPos, ingredient_draw_x, topPos, ingredient_draw_y, mouseX, mouseY);
  }

}
