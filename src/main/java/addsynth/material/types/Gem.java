package addsynth.material.types;

import addsynth.material.MaterialItem;
import addsynth.material.MiningStrength;
import addsynth.material.blocks.GemBlock;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.MaterialColor;

public final class Gem extends OreMaterial {

  public final Item gem;
  public final Item shard;

  /** Custom Gem Material */
  public Gem(final String unlocalized_name, final MaterialColor color, final int min_experience, final int max_experience){
    super(unlocalized_name,
      new MaterialItem(unlocalized_name),
      new GemBlock(unlocalized_name+"_block", color),
      MiningStrength.IRON, min_experience, max_experience);
    this.gem = this.item;
    this.shard = new MaterialItem(unlocalized_name+"_shard");
  }

  /** Vanilla Material */
  public Gem(final String unlocalized_name, final Item gem, final Block block, final Block ore){
    super(unlocalized_name, gem, block, ore);
    this.gem = this.item;
    this.shard = new MaterialItem(unlocalized_name+"_shard");
  }

}
