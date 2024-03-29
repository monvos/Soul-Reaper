package dev.monvos.soulreaper;

import com.mojang.logging.LogUtils;

import dev.monvos.soulreaper.items.Empty_Soul_Jar;
import dev.monvos.soulreaper.items.TheReaper_Sword;
import net.minecraft.client.Minecraft;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
// import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.GlassBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
// import net.minecraft.world.level.block.state.BlockBehaviour;
// import net.minecraft.world.level.material.MapColor;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(SoulReaper.MODID)
public class SoulReaper {
  // Define mod id in a common place for everything to reference
  public static final String MODID = "soulreaper";
  // Directly reference a slf4j logger
  private static final Logger LOGGER = LogUtils.getLogger();
  // Create a Deferred Register to hold Blocks which will all be registered under
  // the "examplemod" namespace
  public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MODID);
  // Create a Deferred Register to hold Items which will all be registered under
  // the "examplemod" namespace
  public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MODID);
  // Create a Deferred Register to hold CreativeModeTabs which will all be
  // registered under the "examplemod" namespace
  public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister
      .create(Registries.CREATIVE_MODE_TAB, MODID);

  // Creates a new Block with the id "examplemod:example_block", combining the
  // namespace and path
  // public static final RegistryObject<Block> EXAMPLE_BLOCK =
  // BLOCKS.register("example_block",
  // () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.STONE)));
  // // Creates a new BlockItem with the id "examplemod:example_block", combining
  // the
  // // namespace and path
  // public static final RegistryObject<Item> EXAMPLE_BLOCK_ITEM =
  // ITEMS.register("example_block",
  // () -> new BlockItem(EXAMPLE_BLOCK.get(), new Item.Properties()));

  // // Creates a new food item with the id "examplemod:example_id", nutrition 1
  // and
  // // saturation 2
  // public static final RegistryObject<Item> EXAMPLE_ITEM =
  // ITEMS.register("example_item",
  // () -> new Item(new Item.Properties().food(new FoodProperties.Builder()
  // .alwaysEat().nutrition(1).saturationMod(2f).build())));
  public static final RegistryObject<GlassBlock> SOUL_GLASS = BLOCKS.register("soul_glass",
      () -> new GlassBlock(BlockBehaviour.Properties.copy(Blocks.GLASS)));

  public static final RegistryObject<Item> SOUL_GLASS_ITEM = ITEMS.register("soul_glass",
      () -> new BlockItem(SOUL_GLASS.get(), new Item.Properties()));

  public static final RegistryObject<SwordItem> THEREAPER_SWORD = ITEMS.register("thereaper_sword",
      () -> new TheReaper_Sword(Tiers.NETHERITE, 3, -2.4F, (new Item.Properties()).fireResistant()));

  public static final RegistryObject<Item> EMPTY_SOUL_JAR = ITEMS.register("empty_soul_jar",
      () -> new Empty_Soul_Jar(new Item.Properties().stacksTo(1)));

  public static final RegistryObject<Item> FILLED_SOUL_JAR = ITEMS.register("filled_soul_jar",
      () -> new Item(new Item.Properties().stacksTo(1)));
  // Creates a creative tab with the id "examplemod:example_tab" for the example
  // item, that is placed after the combat tab
  public static final RegistryObject<CreativeModeTab> SOULREAPER_TAB = CREATIVE_MODE_TABS.register("soulreaper_tab",
      () -> CreativeModeTab.builder()
          .withTabsBefore(CreativeModeTabs.COMBAT)
          .icon(() -> THEREAPER_SWORD.get().getDefaultInstance())
          .title(Component.translatable("creativetab.soulreaper_tab"))
          .displayItems((parameters, output) -> {
            output.accept(THEREAPER_SWORD.get());
            output.accept(EMPTY_SOUL_JAR.get());
            output.accept(FILLED_SOUL_JAR.get());
            output.accept(SOUL_GLASS.get());
            // output.accept(EXAMPLE_ITEM.get()); // Add the example item to the tab. For
            // your own tabs, this method is
            // preferred over the event
          }).build());

  public SoulReaper() {
    IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

    // Register the commonSetup method for modloading
    modEventBus.addListener(this::commonSetup);

    // Register the Deferred Register to the mod event bus so blocks get registered
    BLOCKS.register(modEventBus);
    // Register the Deferred Register to the mod event bus so items get registered
    ITEMS.register(modEventBus);
    // Register the Deferred Register to the mod event bus so tabs get registered
    CREATIVE_MODE_TABS.register(modEventBus);

    // Register ourselves for server and other game events we are interested in
    MinecraftForge.EVENT_BUS.register(this);

    // Register the item to a creative tab
    modEventBus.addListener(this::addCreative);

    // Register our mod's ForgeConfigSpec so that Forge can create and load the
    // config file for us
    ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.SPEC);
  }

  private void commonSetup(final FMLCommonSetupEvent event) {
    // Some common setup code
    LOGGER.info("HELLO FROM COMMON SETUP");

    if (Config.logDirtBlock)
      LOGGER.info("DIRT BLOCK >> {}", ForgeRegistries.BLOCKS.getKey(Blocks.DIRT));

    LOGGER.info(Config.magicNumberIntroduction + Config.magicNumber);

    Config.items.forEach((item) -> LOGGER.info("ITEM >> {}", item.toString()));
  }

  // Add the example block item to the building blocks tab
  private void addCreative(BuildCreativeModeTabContentsEvent event) {
    if (event.getTabKey() == CreativeModeTabs.COMBAT)
      event.accept(THEREAPER_SWORD);
  }

  // You can use SubscribeEvent and let the Event Bus discover methods to call
  @SubscribeEvent
  public void onServerStarting(ServerStartingEvent event) {
    // Do something when the server starts
    LOGGER.info("HELLO from server starting");
  }

  // You can use EventBusSubscriber to automatically register all static methods
  // in the class annotated with @SubscribeEvent
  @Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
  public static class ClientModEvents {
    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
      // Some client setup code
      LOGGER.info("HELLO FROM CLIENT SETUP");
      LOGGER.info("MINECRAFT NAME >> {}", Minecraft.getInstance().getUser().getName());
    }
  }
}
