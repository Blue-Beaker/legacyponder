package io.bluebeaker.legacyponder.ponder.drawable;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
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

    @Override
    public boolean onMouseHover(GuiScreen screen, int mouseX, int mouseY) {
        if(this.getBoundingBox().contains(mouseX,mouseY)){
            GlStateManager.translate(0,0,100);
            screen.drawHoveringText(screen.getItemToolTip(itemStack),mouseX,mouseY);
            RenderHelper.disableStandardItemLighting();
            GlStateManager.translate(0,0,-100);
            return true;
        };
        return super.onMouseHover(screen, mouseX, mouseY);
    }

    @Override
    public int getWidth() {
        return 16;
    }

    @Override
    public int getHeight() {
        return 16;
    }
}
