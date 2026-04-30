package io.bluebeaker.legacyponder.manual.page;

import crafttweaker.annotations.ZenRegister;
import io.bluebeaker.legacyponder.crafttweaker.IDrawableSupplier;
import io.bluebeaker.legacyponder.manual.drawable.DrawableBase;
import io.bluebeaker.legacyponder.manual.gui.GuiInfoPage;
import io.bluebeaker.legacyponder.manual.gui.GuiPageDrawable;
import io.bluebeaker.legacyponder.manual.GuiUnconfusion;
import io.bluebeaker.legacyponder.manual.hover.HoverComponent;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.legacyponder.PageDrawable")
@ZenRegister
public class PageDrawable extends PageBase{
    private final IDrawableSupplier drawableSupplier;
    public PageDrawable(IDrawableSupplier drawableSupplier){
        this.drawableSupplier = drawableSupplier;
    }

    /** Build a drawable from this page.
     * @param width width
     * @param height height
     * @return The drawable built for the specified page size
     */
    @ZenMethod
    public DrawableBase getDrawable(int width, int height){
        return drawableSupplier.process(width, height);
    }

    /**
     * Conventional method for adding a hover component to this structure page
     * @param x x
     * @param y y
     * @param drawableSupplier the method supplying the drawable for this hover component
     * @param rgb color in 0xRRGGBB format
     * @return this
     */
    @ZenMethod
    public PageDrawable addHoverComponent(float x, float y, int rgb, IDrawableSupplier drawableSupplier){
        this.hoverComponents.add(HoverComponent.build(x,y,0,drawableSupplier).setColor(rgb));
        return this;
    }

    /**
     * Conventional method for adding a hover component to this structure page
     * @param id the ID for the component
     * @param drawableSupplier the method supplying the drawable for this hover component
     * @param rgb color in 0xRRGGBB format
     * @return this
     */
    @ZenMethod
    public PageDrawable addHoverComponent(int id, int rgb, IDrawableSupplier drawableSupplier){
        this.hoverComponents.add(HoverComponent.build(-999,-999,0,drawableSupplier).setID(id).setColor(rgb));
        return this;
    }

    @Override
    public GuiInfoPage<PageDrawable> getGuiPage(GuiUnconfusion parent) {
        return new GuiPageDrawable(parent,this);
    }
}
