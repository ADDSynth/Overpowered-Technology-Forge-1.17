package addsynth.material.blocks;

import addsynth.material.ADDSynthMaterials;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;

/** All Metal Blocks will have the same MapColor as Vanilla Iron Block,
 *  unless you specify a color yourself.
 */
public final class MetalBlock extends Block {

  public MetalBlock(final String name){
    this(name, MaterialColor.METAL);
  }

  public MetalBlock(final String name, final MaterialColor color){
    super(Block.Properties.of(Material.METAL, color).strength(5.0f, 6.0f));
    ADDSynthMaterials.registry.register_block(this, name, new Item.Properties().tab(ADDSynthMaterials.creative_tab));
  }

}
