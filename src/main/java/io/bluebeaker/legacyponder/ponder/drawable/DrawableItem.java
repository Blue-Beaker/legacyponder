package io.bluebeaker.legacyponder.ponder.drawable;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import io.bluebeaker.legacyponder.jeiplugin.JEIUtils;
import io.bluebeaker.legacyponder.ponder.GuiScreenPonder;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.item.ItemStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.legacyponder.DrawableItem")
@ZenRegister
public class DrawableItem extends DrawableInteractive {

    private final ItemStack itemStack;

    public DrawableItem(ItemStack itemStack){
        this.itemStack = itemStack;
    }

    @ZenMethod
    public static DrawableItem build(IItemStack item) {
        return new DrawableItem(CraftTweakerMC.getItemStack(item));
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
    public boolean onMouseClick(GuiScreenPonder parent, int x, int y, int button) {
        if(button==0 || button==1){
            JEIUtils.JEIAction action = button==0 ? JEIUtils.JEIAction.RECIPE : JEIUtils.JEIAction.USAGE;
            JEIUtils.handleJEIAction(itemStack, action);
            return true;
        }
        return super.onMouseClick(parent, x, y, button);
    }

    @Override
    public void onKeyTyped(GuiScreenPonder parent, char typedChar, int keyCode) {
        JEIUtils.JEIAction action = JEIUtils.getJEIAction(keyCode);
        if(action!= JEIUtils.JEIAction.NONE){
            JEIUtils.handleJEIAction(itemStack, action);
        }
    }

    @Override
    public boolean onMouseHover(GuiScreen screen, int mouseX, int mouseY) {
        GlStateManager.translate(0,0,100);
        screen.drawHoveringText(screen.getItemToolTip(itemStack),mouseX+parentX,mouseY+parentY);
        RenderHelper.disableStandardItemLighting();
        GlStateManager.translate(0,0,-100);
        return true;
    }



    @ZenMethod
    @Override
    public int getWidth() {
        return 16;
    }
    @ZenMethod
    @Override
    public int getHeight() {
        return 16;
    }
}
