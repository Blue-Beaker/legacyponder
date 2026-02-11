package io.bluebeaker.legacyponder.ponder.drawable;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import io.bluebeaker.legacyponder.jeiplugin.JEIUtils;
import io.bluebeaker.legacyponder.ponder.GuiScreenPonder;
import io.bluebeaker.legacyponder.utils.ItemUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.item.ItemStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

import java.util.*;

@ZenClass("mods.legacyponder.DrawableItem")
@ZenRegister
public class DrawableItem extends DrawableInteractive {

    private final List<ItemStack> itemStacks;

    public DrawableItem(ItemStack itemStack){
        this.itemStacks = ItemUtils.expandWildcard(Collections.singletonList(itemStack));
    }
    public DrawableItem(Collection<ItemStack> stacks){
        this.itemStacks = ItemUtils.expandWildcard(stacks);
    }

    @ZenMethod
    public static DrawableItem build(IItemStack item) {
        return new DrawableItem(CraftTweakerMC.getItemStack(item));
    }
    @ZenMethod
    public static DrawableItem build(IItemStack[] items) {
        return new DrawableItem(Arrays.asList(CraftTweakerMC.getItemStacks(items)));
    }
    @ZenMethod
    public static DrawableItem build(IIngredient item) {
        return build(item.getItemArray());
    }

    @Override
    public void draw(GuiScreen screen, int mouseX, int mouseY) {
        RenderItem renderItem = Minecraft.getMinecraft().getRenderItem();
        RenderHelper.enableGUIStandardItemLighting();

        ItemStack activeStack = getActiveStack();
        renderItem.renderItemAndEffectIntoGUI(activeStack,x,y);
        renderItem.renderItemOverlayIntoGUI(screen.mc.fontRenderer, activeStack,x,y,null);
        RenderHelper.disableStandardItemLighting();

    }

    @Override
    public boolean onMouseClick(GuiScreenPonder parent, int x, int y, int button) {
        if(button==0 || button==1){
            JEIUtils.JEIAction action = button==0 ? JEIUtils.JEIAction.RECIPE : JEIUtils.JEIAction.USAGE;
            JEIUtils.handleJEIAction(getActiveStack(), action);
            return true;
        }
        return super.onMouseClick(parent, x, y, button);
    }

    @Override
    public void onKeyTyped(GuiScreenPonder parent, char typedChar, int keyCode) {
        JEIUtils.JEIAction action = JEIUtils.getJEIAction(keyCode);
        if(action!= JEIUtils.JEIAction.NONE){
            JEIUtils.handleJEIAction(getActiveStack(), action);
        }
    }

    @Override
    public boolean onMouseHover(GuiScreen screen, int mouseX, int mouseY) {
        GlStateManager.translate(0,0,100);
        screen.drawHoveringText(screen.getItemToolTip(getActiveStack()),mouseX+parentX,mouseY+parentY);
        RenderHelper.disableStandardItemLighting();
        GlStateManager.translate(0,0,-100);
        return true;
    }

    protected ItemStack getActiveStack(){
        if(itemStacks.isEmpty()) {
            return ItemStack.EMPTY;
        } else if (itemStacks.size()==1) {
            return itemStacks.get(0);
        }else {
            return itemStacks.get((int) (System.currentTimeMillis()/1000%itemStacks.size()));
        }
    }

    @ZenMethod
    @Override
    public DrawableBase setSize(int w, int h) {
        return this;
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
