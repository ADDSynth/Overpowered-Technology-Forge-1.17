package addsynth.core.gameplay.compat;

// REMOVE CompatabilityManager class.
public final class CompatabilityManager {

  /** Fires once after all mods have been loaded. */
  @Deprecated
  public static final void init(){
  }

  /** Must be executed every time the data packs are loaded or reloaded. */
  @Deprecated
  public static final void run_data_compatability(){ // DELETE
  }

  // PRIORITY All versions. Use the Set&lt;Block&gt; field in the Scythe Tool class, that is exactly how vanilla defines blocks.
}
