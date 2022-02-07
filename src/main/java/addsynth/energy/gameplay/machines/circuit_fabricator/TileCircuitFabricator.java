package addsynth.energy.gameplay.machines.circuit_fabricator;

import javax.annotation.Nullable;
import addsynth.core.container.slots.InputSlot;
import addsynth.core.items.ItemUtil;
import addsynth.core.util.StringUtil;
import addsynth.core.util.constants.Constants;
import addsynth.core.util.java.ArrayUtil;
import addsynth.energy.gameplay.Config;
import addsynth.energy.gameplay.EnergyItems;
import addsynth.energy.gameplay.machines.circuit_fabricator.recipe.CircuitFabricatorRecipe;
import addsynth.energy.gameplay.machines.circuit_fabricator.recipe.CircuitFabricatorRecipes;
import addsynth.energy.lib.tiles.machines.TileStandardWorkMachine;
import addsynth.energy.registers.Tiles;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;

public final class TileCircuitFabricator extends TileStandardWorkMachine implements MenuProvider {

  private int circuit_id;
  private ItemStack[][] filter = new ItemStack[8][];

  private final InputSlot[] input_slot = {
    new InputSlot(this, 0,  76, 76),
    new InputSlot(this, 1,  94, 76),
    new InputSlot(this, 2, 112, 76),
    new InputSlot(this, 3, 130, 76),
    new InputSlot(this, 4,  76, 94),
    new InputSlot(this, 5,  94, 94),
    new InputSlot(this, 6, 112, 94),
    new InputSlot(this, 7, 130, 94)
  };

  public TileCircuitFabricator(BlockPos position, BlockState blockstate){
    super(Tiles.CIRCUIT_FABRICATOR, position, blockstate, 8, null, 1, Config.circuit_fabricator_data);
    inventory.setRecipeProvider(CircuitFabricatorRecipes.INSTANCE);
  }

  public final void change_circuit_craft(final int circuit_id, final boolean update){
   this.circuit_id = circuit_id;
   rebuild_filters();
   changed = update;
  }

  // Ideally, this should be rebuilt every recipe and tag reload, but this is the best I can do for now.
  public final void rebuild_filters(){
    // select ItemStack based on circuit ID.
    final ItemStack output = new ItemStack(EnergyItems.circuit[circuit_id], 1);
    // find recipe
    final CircuitFabricatorRecipe recipe = CircuitFabricatorRecipes.INSTANCE.find_recipe(output);
    // get ingredients, create filters
    filter = recipe.getItemStackIngredients();
    int i;
    for(i = 0; i < 8; i++){
      // apply filters
      if(i < filter.length){
        input_slot[i].setFilter(filter[i]);
        inventory.getInputInventory().setFilter(i, ItemUtil.toItemArray(filter[i]));
      }
      else{
        input_slot[i].setFilter(new Item[0]);
        inventory.getInputInventory().setFilter(i, new Item[0]);
      }
    }
    
    // update recipe in gui if on client side
    updateGui();
  }

  /** Go through all inventory slots and eject all items that don't match.
   *  This can only be called when the player clicks on a change recipe button on the gui,
   *  which then sends a network message to the server.
   */
  public final void ejectInvalidItems(final Player player){
    inventory.getInputInventory().ejectInvalidItems(player);
  }

  public final void updateGui(){
    if(level != null){
      if(level.isClientSide){
        CircuitFabricatorGui.updateRecipeDisplay(filter);
      }
    }
  }

  @Override
  public final CompoundTag save(final CompoundTag nbt){
    super.save(nbt);
    nbt.putInt("Circuit to Craft", circuit_id);
    return nbt;
  }

  @Override
  public final void load(final CompoundTag nbt){
    super.load(nbt);
    // this runs with a default value of 0 if the key doesn't exist. That's perfect.
    change_circuit_craft(nbt.getInt("Circuit to Craft"), false);
  }

  public final String getCircuitSelected(){
    if(onClientSide()){
      if(ArrayUtil.isInsideBounds(circuit_id, EnergyItems.circuit)){
        return StringUtil.translate(EnergyItems.circuit[circuit_id].getDescriptionId());
      }
      return Constants.null_error;
    }
    return "";
  }

  public final InputSlot[] getInputSlots(){
    return input_slot;
  }

  @Override
  @Nullable
  public AbstractContainerMenu createMenu(int id, Inventory player_inventory, Player player){
    return new CircuitFabricatorContainer(id, player_inventory, this);
  }

  @Override
  public Component getDisplayName(){
    return new TranslatableComponent(getBlockState().getBlock().getDescriptionId());
  }

}
