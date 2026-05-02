package io.bluebeaker.legacyponder;

import io.bluebeaker.legacyponder.command.CommandLegacyPonder;
import io.bluebeaker.legacyponder.command.CommandShowEntry;
import io.bluebeaker.legacyponder.compat.*;
import io.bluebeaker.legacyponder.demo.DemoEntries;
import io.bluebeaker.legacyponder.structure.StructureLoader;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Config.Type;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent.OnConfigChangedEvent;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.apache.logging.log4j.Logger;

@Mod(modid = Tags.MOD_ID, name = Tags.MOD_NAME, version = Tags.VERSION,acceptableRemoteVersions = "*",clientSideOnly = true)
public class LegacyPonder
{
    public static final String MODID = Tags.MOD_ID;
    public static final String NAME = Tags.MOD_NAME;
    public static final String VERSION = Tags.VERSION;
    
    public MinecraftServer server;

    private static Logger logger;
    
    public LegacyPonder() {
        MinecraftForge.EVENT_BUS.register(this);
    }
    
    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        logger = event.getModLog();
        if(Loader.isModLoaded("zenutils")){
            MinecraftForge.EVENT_BUS.register(new ZenUtilsCompat());
        }
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        Keybinds.init();
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        StructureLoader.loadTemplates();
        if(CommonConfig.compat.buildcraft && Loader.isModLoaded("buildcraftcore")){
            MinecraftForge.EVENT_BUS.register(new EventHandlerBC());
        }
        if(CommonConfig.compat.ic2 && Loader.isModLoaded("ic2")){
            MinecraftForge.EVENT_BUS.register(new EventHandlerIC2());
        }
        if(CommonConfig.compat.forgemultipart && Loader.isModLoaded("forgemultipartcbe")){
            MinecraftForge.EVENT_BUS.register(new EventHandlerFMP());
        }
        if(CommonConfig.compat.enderio && Loader.isModLoaded("enderioconduits")){
            MinecraftForge.EVENT_BUS.register(new EventHandlerEIOConduits());
        }
        MinecraftForge.EVENT_BUS.register(EventHandlerCommon.getInstance());

        EventHandlerCommon.updateConfig();
        DemoEntries.addDemoIfNeeded();
    }

    @EventHandler
    public void onServerStart(FMLServerStartingEvent event){
        this.server=event.getServer();
        event.registerServerCommand(new CommandLegacyPonder());
        ClientCommandHandler.instance.registerCommand(new CommandShowEntry());
    }

    @SubscribeEvent
    public void onConfigChangedEvent(OnConfigChangedEvent event) {
        if (event.getModID().equals(MODID)) {
            ConfigManager.sync(MODID, Type.INSTANCE);
            EventHandlerCommon.updateConfig();
            DemoEntries.addDemoIfNeeded();
        }
    }

    public static Logger getLogger(){
        return logger;
    }
}
