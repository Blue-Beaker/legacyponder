package io.bluebeaker.legacyponder;

import io.bluebeaker.legacyponder.quests.FTBQuestsIntegration;
import net.minecraft.crash.CrashReport;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ReportedException;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.relauncher.Side;
import org.apache.logging.log4j.Logger;

import java.io.PrintWriter;
import java.io.StringWriter;

import static io.bluebeaker.legacyponder.CommonConfig.ftbquests_integration;

@Mod(modid = Tags.MOD_ID, name = Tags.MOD_NAME, version = Tags.VERSION,acceptableRemoteVersions = "*")
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
        if(event.getSide() == Side.CLIENT) {
            ClientHandler.preInit(event);
        }
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        if(event.getSide() == Side.CLIENT) {
            ClientHandler.init();
        }
        if(Loader.isModLoaded("ftbquests") && ftbquests_integration) {
            MinecraftForge.EVENT_BUS.register(FTBQuestsIntegration.class);
        }
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        if(event.getSide() == Side.CLIENT) {
            ClientHandler.postInit();
        }
    }

    @EventHandler
    public void onServerStart(FMLServerStartingEvent event){
        this.server= event.getServer();
        if(event.getSide() == Side.CLIENT) {
            ClientHandler.serverStart(event);
        }
    }


    public static Logger getLogger(){
        return logger;
    }
    public static void logException(Throwable e){
        if (CommonConfig.debug_verbosity>=1){
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            if (CommonConfig.debug_verbosity>=2 && e instanceof ReportedException){
                CrashReport crashReport = ((ReportedException) e).getCrashReport();
                sw.write(crashReport.getCompleteReport());
            }
            e.printStackTrace(pw);
            logger.error(sw.toString());
        }else {
            logger.error(e.getMessage());
        }
    }
}
