package io.bluebeaker.legacyponder;

import io.bluebeaker.legacyponder.ponder.GuiUnconfusion;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiYesNo;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
public class PonderScreenHandler {
    public static GuiUnconfusion lastPonderScreen = null;
    protected static GuiScreen lastScreen = null;

    @SubscribeEvent
    public static void onScreenChange(GuiOpenEvent event){
        GuiScreen gui = event.getGui();
        if(gui instanceof GuiUnconfusion){
            lastPonderScreen = (GuiUnconfusion) gui;
            lastPonderScreen.setLastScreen(lastScreen);
        }
        if (lastScreen instanceof GuiYesNo && lastPonderScreen.isLinkActive()) {
            event.setGui(lastPonderScreen);
        }
        lastScreen = gui;
    }
}
