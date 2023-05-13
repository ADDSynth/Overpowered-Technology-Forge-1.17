package addsynth.core.gameplay.compat;

import addsynth.core.ADDSynthCore;
import addsynth.core.gameplay.Core;
import addsynth.core.gameplay.reference.TextReference;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.registration.IRecipeRegistration;

@JeiPlugin
public final class CoreJEIPlugin  implements IModPlugin {

  public static final ResourceLocation id = new ResourceLocation(ADDSynthCore.MOD_ID, "jei_plugin");

  @Override
  public ResourceLocation getPluginUid(){
    return id;
  }

  @Override
  public void registerRecipes(IRecipeRegistration registration){
    add_information(registration);
  }

  private static final void add_information(IRecipeRegistration registry){
    registry.addIngredientInfo(new ItemStack(Core.music_box), VanillaTypes.ITEM, TextReference.music_box_description);
    registry.addIngredientInfo(new ItemStack(Core.music_sheet), VanillaTypes.ITEM, TextReference.music_sheet_description);
    // registry.addIngredientInfo(new ItemStack(Core.team_manager), VanillaTypes.ITEM, TextReference.team_manager_description);
  }

}
