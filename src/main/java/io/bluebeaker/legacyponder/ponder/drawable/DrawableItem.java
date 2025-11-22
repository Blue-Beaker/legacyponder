package io.bluebeaker.legacyponder.ponder.drawable;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.item.ItemStack;

public class DrawableItem extends DrawableBase {

    private final ItemStack itemStack;

    public DrawableItem(ItemStack itemStack){
        this.itemStack = itemStack;
    }
    @Override
    public void draw(GuiScreen screen, int mouseX, int mouseY) {
        RenderItem renderItem = Minecraft.getMinecraft().getRenderItem();

        RenderHelper.enableGUIStandardItemLighting();
        renderItem.renderItemAndEffectIntoGUI(itemStack,x,y);
        renderItem.renderItemOverlayIntoGUI(screen.mc.fontRenderer,itemStack,x,y,null);
        RenderHelper.disableStandardItemLighting();
    }
}
