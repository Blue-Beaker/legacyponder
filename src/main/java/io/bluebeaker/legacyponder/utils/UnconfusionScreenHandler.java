package io.bluebeaker.legacyponder.utils;

import io.bluebeaker.legacyponder.crafttweaker.ManualRegistry;
import io.bluebeaker.legacyponder.manual.Entry;
import io.bluebeaker.legacyponder.manual.GuiUnconfusion;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.relauncher.Side;

public class UnconfusionScreenHandler {
    public static boolean openUnconfusionScreen(String entryID, int page) {
        if(FMLCommonHandler.instance().getSide()!= Side.CLIENT) {
            return false;
        }
        Entry entry = ManualRegistry.get(entryID);
        if(entry==null){
            return false;
        }
        GuiUnconfusion screen = new GuiUnconfusion();
        screen.clearHistory();
        screen.jumpTo(entryID);
        if(page!=1)
            screen.setCurrentPageID(page);
        Minecraft.getMinecraft().displayGuiScreen(screen);
        return true;
    }
}
