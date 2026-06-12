package io.bluebeaker.legacyponder;

import io.bluebeaker.legacyponder.command.CommandLegacyPonder;
import io.bluebeaker.legacyponder.command.CommandShowEntry;
import io.bluebeaker.legacyponder.command.CommandShowStructure;
import io.bluebeaker.legacyponder.compat.*;
import io.bluebeaker.legacyponder.demo.DemoEntries;
import io.bluebeaker.legacyponder.manual.GuiUnconfusion;
import io.bluebeaker.legacyponder.structure.StructureLoader;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.relauncher.Side;

import static io.bluebeaker.legacyponder.LegacyPonder.MODID;

@Mod.EventBusSubscriber(Side.CLIENT)
public class ClientHandler {

    @SubscribeEvent
    public static void onConfigChangedEvent(ConfigChangedEvent.OnConfigChangedEvent event) {
        if (event.getModID().equals(MODID)) {
            ConfigManager.sync(MODID, Config.Type.INSTANCE);
            EventHandlerCommon.updateConfig();
            DemoEntries.addDemoIfNeeded();
        }
    }
    @SubscribeEvent
    public static void onInput(InputEvent.KeyInputEvent event){
        if(Minecraft.getMinecraft().currentScreen==null && Keybinds.openManual.isPressed()){
            GuiUnconfusion gui = new GuiUnconfusion();
            gui.loadHistory();
            Minecraft.getMinecraft().displayGuiScreen(gui);
        }
    }

    static void preInit(FMLPreInitializationEvent event) {
        // Scan and register all @ModZenRegister classes with CraftTweaker.
        // Done manually instead of using @ZenRegister to avoid CraftTweaker
        // loading client-only classes on dedicated servers.
        ZenClassScanner.init(event.getAsmData());
        ZenClassScanner.registerAll();
        if(Loader.isModLoaded("zenutils")){
            MinecraftForge.EVENT_BUS.register(new ZenUtilsCompat());
        }
    }

    static void init() {
        Keybinds.init();
    }

    static void postInit() {
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

    static void serverStart(FMLServerStartingEvent event) {
        event.registerServerCommand(new CommandLegacyPonder());
        ClientCommandHandler.instance.registerCommand(new CommandShowEntry());
        ClientCommandHandler.instance.registerCommand(new CommandShowStructure());
    }
}
