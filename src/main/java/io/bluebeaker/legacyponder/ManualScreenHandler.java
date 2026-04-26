package io.bluebeaker.legacyponder;

import io.bluebeaker.legacyponder.manual.GuiUnconfusion;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiYesNo;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
public class ManualScreenHandler {
    public static GuiUnconfusion lastManualScreen = null;
    protected static GuiScreen lastScreen = null;

    @SubscribeEvent
    public static void onScreenChange(GuiOpenEvent event){
        GuiScreen gui = event.getGui();
        if(gui instanceof GuiUnconfusion){
            lastManualScreen = (GuiUnconfusion) gui;
            lastManualScreen.setLastScreen(lastScreen);
        }
        if (lastScreen instanceof GuiYesNo && lastManualScreen.isLinkActive()) {
            event.setGui(lastManualScreen);
        }
        lastScreen = gui;
    }
}
