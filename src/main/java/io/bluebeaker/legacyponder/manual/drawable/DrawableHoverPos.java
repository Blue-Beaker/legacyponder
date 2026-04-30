package io.bluebeaker.legacyponder.manual.drawable;

import crafttweaker.annotations.ZenRegister;
import io.bluebeaker.legacyponder.manual.GuiUnconfusion;
import net.minecraft.client.gui.GuiScreen;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

/**
 * Helper drawable to help positioning HoverComponent for PageDrawable.
 * Doesn't do anything on its own, but you can attach a HoverComponent with same ID to its position.
 */
@ZenClass("mods.legacyponder.DrawableHoverPos")
@ZenRegister
public class DrawableHoverPos extends DrawableBase {
    public final int id;
    public DrawableHoverPos(int id){
        super();
        this.id=id;
        this.w=0;
        this.h=0;
    }

    @Override
    public void draw(GuiUnconfusion screen, int mouseX, int mouseY) {

    }

    @Override
    public boolean isFocused(GuiScreen screen, int mouseX, int mouseY) {
        return false;
    }

    @ZenMethod
    @Override
    public DrawableBase setSize(int w, int h) {
        return this;
    }
}
