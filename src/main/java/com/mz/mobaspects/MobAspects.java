package com.mz.mobaspects;

import com.mz.mobaspects.aspect.core.AspectManager;
import com.mz.mobaspects.capability.AspectCapabilityRegisterHelper;
import com.mz.mobaspects.config.ServerConfig;
import com.mz.mobaspects.entity.CustomEntityRegister;
import com.mz.mobaspects.entity.renderer.GhastBuddyRenderer;
import com.mz.mobaspects.entity.renderer.TotemOfUndyingRenderer;
import com.mz.mobaspects.events.AspectEventHandler;
import com.mz.mobaspects.events.gui.UiEventHandler;
import com.mz.mobaspects.network.NetworkHandler;
import net.minecraft.block.Block;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.stream.Collectors;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(MobAspects.MOD_ID)
public class MobAspects
{
    public static final String MOD_ID = "mobaspects";
    // Directly reference a log4j logger.
    public static final Logger LOGGER = LogManager.getLogger();

    public MobAspects() {
        final IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        CustomEntityRegister.ENTITY_TYPES.register(modEventBus);

        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, ServerConfig.SPEC , MOD_ID + ".server.toml");
        // Register the setup method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        // Register the enqueueIMC method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::enqueueIMC);
        // Register the processIMC method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::processIMC);
        // Register the doClientStuff method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doClientStuff);

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);


    }

    // Setup both Server + Client
    private void setup(final FMLCommonSetupEvent event)
    {
        AspectManager.INSTANCE.InitializeAspectList();

        NetworkHandler.init();
        AspectCapabilityRegisterHelper.register();
        MinecraftForge.EVENT_BUS.register(new AspectEventHandler());
    }

    private void doClientStuff(final FMLClientSetupEvent event) {
        MinecraftForge.EVENT_BUS.register(new UiEventHandler());
        // do something that can only be done on the client
        // e.g ClientRegistry.registerKeyBinding(keyBinding);
        // LOGGER.info("Got game settings {}", event.getMinecraftSupplier().get().options);

        RenderingRegistry.registerEntityRenderingHandler(CustomEntityRegister.GHAST_BUDDY.get(), GhastBuddyRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(CustomEntityRegister.UNDYING_TOTEM.get(), TotemOfUndyingRenderer::new);
    }

    private void enqueueIMC(final InterModEnqueueEvent event)
    {
        // some example code to dispatch IMC to another mod
        // InterModComms.sendTo("examplemod", "helloworld", () -> { LOGGER.info("Hello world from the MDK"); return "Hello world";});
    }

    private void processIMC(final InterModProcessEvent event)
    {
        // some example code to receive and process InterModComms from other mods
        LOGGER.info("Got IMC {}", event.getIMCStream().
                map(m->m.getMessageSupplier().get()).
                collect(Collectors.toList()));
    }
    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(FMLServerStartingEvent event) {
        // do something when the server starts
        // LOGGER.info("HELLO from server starting");

    }

    // You can use EventBusSubscriber to automatically subscribe events on the contained class (this is subscribing to the MOD
    // Event bus for receiving Registry Events)
    @Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD)
    public static class RegistryEvents {
        @SubscribeEvent
        public static void onBlocksRegistry(final RegistryEvent.Register<Block> blockRegistryEvent) {
            // register a new block here
            // LOGGER.info("HELLO from Register Block");
        }
    }
}
