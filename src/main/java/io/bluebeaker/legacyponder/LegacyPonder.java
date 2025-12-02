package io.bluebeaker.legacyponder;

import io.bluebeaker.legacyponder.command.CommandLegacyPonder;
import io.bluebeaker.legacyponder.structure.StructureLoader;
import io.bluebeaker.legacyponder.world.handler.EventHandlerBC;
import io.bluebeaker.legacyponder.world.handler.EventHandlerFMP;
import io.bluebeaker.legacyponder.world.handler.EventHandlerIC2;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Config.Type;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent.OnConfigChangedEvent;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.apache.logging.log4j.Logger;
import youyihj.zenutils.api.reload.ScriptReloadEvent;

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
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        StructureLoader.loadTemplates();
//        MinecraftForge.EVENT_BUS.register(new EventHandlerBase());
        if(Loader.isModLoaded("buildcraftcore")){
            MinecraftForge.EVENT_BUS.register(new EventHandlerBC());
        }
        if(Loader.isModLoaded("ic2")){
            MinecraftForge.EVENT_BUS.register(new EventHandlerIC2());
        }
        if(Loader.isModLoaded("forgemultipartcbe")){
            MinecraftForge.EVENT_BUS.register(new EventHandlerFMP());
        }
    }

    @SubscribeEvent
    public void onReload(ScriptReloadEvent event) {
        StructureLoader.loadTemplates();
    }

    @EventHandler
    public void onServerStart(FMLServerStartingEvent event){
        this.server=event.getServer();
        event.registerServerCommand(new CommandLegacyPonder());
    }

    @SubscribeEvent
    public void onConfigChangedEvent(OnConfigChangedEvent event) {
        if (event.getModID().equals(MODID)) {
            ConfigManager.sync(MODID, Type.INSTANCE);
        }
    }

    public static Logger getLogger(){
        return logger;
    }
}
