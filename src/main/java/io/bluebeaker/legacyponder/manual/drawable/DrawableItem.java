package io.bluebeaker.legacyponder.manual.drawable;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import io.bluebeaker.legacyponder.manual.GuiUnconfusion;
import io.bluebeaker.legacyponder.manual.link.LinkBase;
import io.bluebeaker.legacyponder.manual.link.LinkItemBase;
import io.bluebeaker.legacyponder.utils.ItemUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.client.config.GuiUtils;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@ZenClass("mods.legacyponder.DrawableItem")
@ZenRegister
public class DrawableItem extends DrawableBase {

    private final List<ItemStack> itemStacks;

    public DrawableItem(ItemStack itemStack){
        this(Collections.singletonList(itemStack));
    }
    public DrawableItem(Collection<ItemStack> stacks){
        this.itemStacks = ItemUtils.expandWildcard(stacks);
        this.w=16;
        this.h=16;
        resetLink();
    }


    /** Builds a drawable item from the given IItemStack, array of IItemStack, or IIngredient. If the item parameter contains multiple items, all of them will be included in the drawable and displayed in a cycle.
     * @param item the IItemStack, array of IItemStack or IIngredient to be displayed as a drawable item
     * @return A DrawableItem instance representing the given item(s)
     */
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
    public void draw(GuiUnconfusion screen, int mouseX, int mouseY) {
        RenderItem renderItem = Minecraft.getMinecraft().getRenderItem();
        RenderHelper.enableGUIStandardItemLighting();

        ItemStack activeStack = getActiveStack();
        renderItem.renderItemAndEffectIntoGUI(activeStack,x,y);
        renderItem.renderItemOverlayIntoGUI(screen.mc.fontRenderer, activeStack,x,y,null);
        RenderHelper.disableStandardItemLighting();

        if(isLinkClickable() && isLastHovered()){
            GlStateManager.enableDepth();
            GuiUtils.drawGradientRect(300, x, y, x + 16, y + 16, 0x80ffffff, 0x80ffffff);
            GlStateManager.disableDepth();
        }

    }

    @Nullable
    @Override
    public LinkBase getDefaultLink() {
        return new LinkItems(this);
    }

    protected ItemStack getActiveStack(){
        if(itemStacks==null || itemStacks.isEmpty()) {
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

    public static class LinkItems implements LinkItemBase {
        public final DrawableItem parent;
        public LinkItems(DrawableItem parent) {
            this.parent = parent;
        }
        @Override
        public ItemStack getIngredient() {
            return parent.getActiveStack();
        }
    }
}
