package addsynth.overpoweredmod.machines.magic_infuser.recipes;

import addsynth.core.recipe.shapeless.AbstractRecipe;
import addsynth.core.util.java.StringUtil;
import addsynth.material.util.MaterialTag;
import addsynth.overpoweredmod.game.reference.OverpoweredBlocks;
import addsynth.overpoweredmod.game.reference.OverpoweredItems;
import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.item.EnchantedBookItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentInstance;
import net.minecraftforge.common.Tags;
import net.minecraftforge.registries.ForgeRegistries;

public final class MagicInfuserRecipe extends AbstractRecipe {

  public final Ingredient main_ingredient;
  public final ResourceLocation enchantment_id;
  public final Enchantment enchantment;

  public MagicInfuserRecipe(ResourceLocation id, String group, Enchantment enchantment, Ingredient ingredient){
    super(id, group, null, null);
    main_ingredient = ingredient;
    enchantment_id = enchantment.getRegistryName();
    this.enchantment = enchantment;
  }

  public MagicInfuserRecipe(ResourceLocation id, String group, ResourceLocation enchantment_id, Ingredient ingredient){
    super(id, group, null, null);
    main_ingredient = ingredient;
    this.enchantment_id = enchantment_id;
    enchantment = ForgeRegistries.ENCHANTMENTS.getValue(enchantment_id);
  }

  public MagicInfuserRecipe(ResourceLocation id, String group, String enchantment_id, Ingredient ingredient){
    super(id, group, null, null);
    main_ingredient = ingredient;
    this.enchantment_id = new ResourceLocation(enchantment_id);
    enchantment = ForgeRegistries.ENCHANTMENTS.getValue(this.enchantment_id);
  }

  public final ItemStack getEnchantedBook(){
    return EnchantedBookItem.createForEnchantment(new EnchantmentInstance(enchantment, 1));
  }

  @Override
  public final NonNullList<Ingredient> getIngredients(){
    final NonNullList<Ingredient> nonnulllist = NonNullList.createWithCapacity(2);
    nonnulllist.add(Ingredient.of(new ItemStack(Items.BOOK, 1)));
    nonnulllist.add(main_ingredient);
    return nonnulllist;
  }

  @Override
  public ItemStack assemble(Container inv){
    return getEnchantedBook();
  }

  @Override
  public ItemStack getResultItem(){
    return getEnchantedBook();
  }

  @Override
  public ItemStack getToastSymbol(){
    return new ItemStack(OverpoweredBlocks.magic_infuser, 1);
  }

  @Override
  public RecipeSerializer<MagicInfuserRecipe> getSerializer(){
    return MagicInfuserRecipes.INSTANCE.serializer;
  }

  @Override
  public RecipeType<?> getType(){
    return MagicInfuserRecipes.INSTANCE.type;
  }

  @Override
  public final String toString(){
    final String name = MagicInfuserRecipe.class.getSimpleName();
    try{
      final Item gem = main_ingredient.getItems()[0].getItem();
      final String gem_name = getGemName(gem);
      return StringUtil.build(name, '(', gem_name, ": ", enchantment_id.toString(), ')');
    }
    catch(Exception e){
      return StringUtil.build(name, '(', "Error", ')');
    }
  }

  private static final String getGemName(final Item item){
    if(MaterialTag.RUBY.GEMS.contains(item)    ){return "Ruby";}
    if(MaterialTag.TOPAZ.GEMS.contains(item)   ){return "Topaz";}
    if(MaterialTag.CITRINE.GEMS.contains(item) ){return "Citrine";}
    if(Tags.Items.GEMS_EMERALD.contains(item)  ){return "Emerald";}
    if(Tags.Items.GEMS_DIAMOND.contains(item)  ){return "Diamond";}
    if(MaterialTag.SAPPHIRE.GEMS.contains(item)){return "Sapphire";}
    if(MaterialTag.AMETHYST.GEMS.contains(item)){return "Amethyst";}
    if(Tags.Items.GEMS_QUARTZ.contains(item)   ){return "Quartz";}
    if(item == OverpoweredItems.celestial_gem  ){return "Celestial"; }
    if(item == OverpoweredItems.void_crystal   ){return "Void"; }
    final ResourceLocation id = item.getRegistryName();
    if(id != null){
      return id.toString();
    }
    return "Invalid";
  }

}
