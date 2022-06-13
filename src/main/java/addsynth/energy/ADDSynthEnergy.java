package addsynth.energy;

import java.io.File;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import addsynth.core.game.RegistryUtil;
import addsynth.core.util.CommonUtil;
import addsynth.core.util.constants.DevStage;
import addsynth.energy.compat.ADDSynthEnergyCompat;
import addsynth.energy.gameplay.Config;
import addsynth.energy.gameplay.EnergyBlocks;
import addsynth.energy.gameplay.NetworkHandler;
import addsynth.energy.gameplay.machines.circuit_fabricator.CircuitFabricatorGui;
import addsynth.energy.gameplay.machines.circuit_fabricator.recipe.CircuitFabricatorRecipes;
import addsynth.energy.gameplay.machines.compressor.GuiCompressor;
import addsynth.energy.gameplay.machines.compressor.recipe.CompressorRecipes;
import addsynth.energy.gameplay.machines.electric_furnace.GuiElectricFurnace;
import addsynth.energy.gameplay.machines.energy_storage.GuiEnergyStorageContainer;
import addsynth.energy.gameplay.machines.generator.GuiGenerator;
import addsynth.energy.gameplay.machines.universal_energy_interface.GuiUniversalEnergyInterface;
import addsynth.energy.registers.Containers;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.config.ModConfigEvent;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLPaths;

@Mod(value = ADDSynthEnergy.MOD_ID)
public class ADDSynthEnergy {

  public static final String MOD_ID = "addsynth_energy";
  public static final String MOD_NAME = "ADDSynth Energy";
  public static final String VERSION = "1.0";
  public static final String VERSION_DATE = "June 3, 2022";
    
  public static final Logger log = LogManager.getLogger(MOD_NAME);

  public static final RegistryUtil registry = new RegistryUtil(MOD_ID);

  public static final CreativeModeTab creative_tab = new CreativeModeTab("addsynth_energy"){
    @Override
    public final ItemStack makeIcon(){
      return new ItemStack(registry.getItemBlock(EnergyBlocks.generator));
    }
  };

  public ADDSynthEnergy(){
    final FMLJavaModLoadingContext context = FMLJavaModLoadingContext.get();
    final IEventBus bus = context.getModEventBus();
    bus.addListener(ADDSynthEnergy::main_setup);
    bus.addListener(ADDSynthEnergyCompat::sendIMCMessages);
    bus.addListener(ADDSynthEnergy::client_setup);
    init_config();
  }

  private static final void init_config(){
    new File(FMLPaths.CONFIGDIR.get().toString(), MOD_NAME).mkdir();

    final ModLoadingContext context = ModLoadingContext.get();
    context.registerConfig(ModConfig.Type.COMMON, Config.CONFIG_SPEC,   MOD_NAME+File.separator+"main.toml");

    FMLJavaModLoadingContext.get().getModEventBus().addListener(ADDSynthEnergy::mod_config_event);
  }

  private static final void main_setup(final FMLCommonSetupEvent event){
    CommonUtil.displayModInfo(log, MOD_NAME, "ADDSynth", VERSION, DevStage.DEVELOPMENT, VERSION_DATE);
    NetworkHandler.registerMessages();
    CompressorRecipes.INSTANCE.registerResponders();
    CircuitFabricatorRecipes.INSTANCE.registerResponders();
  }

  private static final void client_setup(final FMLClientSetupEvent event){
    register_guis();
    set_block_render_types();
  }

  private static final void register_guis(){
    MenuScreens.register(Containers.GENERATOR,                  GuiGenerator::new);
    MenuScreens.register(Containers.ENERGY_STORAGE_CONTAINER,   GuiEnergyStorageContainer::new);
    MenuScreens.register(Containers.COMPRESSOR,                 GuiCompressor::new);
    MenuScreens.register(Containers.ELECTRIC_FURNACE,           GuiElectricFurnace::new);
    MenuScreens.register(Containers.CIRCUIT_FABRICATOR,         CircuitFabricatorGui::new);
    MenuScreens.register(Containers.UNIVERSAL_ENERGY_INTERFACE, GuiUniversalEnergyInterface::new);
  }

  private static final void set_block_render_types(){
    ItemBlockRenderTypes.setRenderLayer(EnergyBlocks.energy_storage, RenderType.translucent());
  }

  public static final void mod_config_event(final ModConfigEvent event){
    event.getConfig().save();
  }

}
