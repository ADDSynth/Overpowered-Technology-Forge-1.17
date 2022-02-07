package addsynth.core.gameplay;

import addsynth.core.blocks.TestBlock;
import addsynth.core.gameplay.blocks.CautionBlock;
import addsynth.core.gameplay.music_box.MusicBox;
import addsynth.core.gameplay.music_box.MusicSheet;
import addsynth.core.gameplay.team_manager.TeamManagerBlock;
import net.minecraft.world.level.block.Block;

public final class Core {

  public static final TestBlock        test_block              = new TestBlock();

  public static final Block            caution_block           = new CautionBlock("caution_block");
  public static final MusicBox         music_box               = new MusicBox("music_box");
  public static final MusicSheet       music_sheet             = new MusicSheet("music_sheet");
  public static final TeamManagerBlock team_manager            = new TeamManagerBlock();

}
