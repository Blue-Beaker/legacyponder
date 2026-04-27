package io.bluebeaker.legacyponder.manual.page;

import crafttweaker.annotations.ZenRegister;
import io.bluebeaker.legacyponder.crafttweaker.IDrawableSupplier;
import io.bluebeaker.legacyponder.manual.GuiUnconfusion;
import io.bluebeaker.legacyponder.manual.drawable.DrawableBase;
import io.bluebeaker.legacyponder.manual.gui.GuiInfoPage;
import io.bluebeaker.legacyponder.manual.gui.GuiPageStructure;
import io.bluebeaker.legacyponder.manual.hover.HighlightArea;
import io.bluebeaker.legacyponder.manual.hover.HoverComponent;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenGetter;
import stanhebben.zenscript.annotations.ZenMethod;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

@ZenClass("mods.legacyponder.PageStructure")
@ZenRegister
public class PageStructure extends PageBase{

    protected final List<HighlightArea> highlightAreas = new ArrayList<>();

    @Nullable
    protected IDrawableSupplier overlaySupplier;

    /**
     * @return Structure ID of this page
     */
    @ZenMethod
    @ZenGetter("structureID")
    public String getStructureID() {
        return structureID;
    }

    public final String structureID;

    public PageStructure(String id){
        this.structureID = id;
    }

    @Override
    public GuiInfoPage<PageStructure> getGuiPage(GuiUnconfusion parent) {
        return new GuiPageStructure(parent,this);
    }

    @ZenMethod
    public PageStructure addHighlightArea(HighlightArea area){
        this.highlightAreas.add(area);
        return this;
    }
    public List<HighlightArea> getHighlightAreas() {
        return highlightAreas;
    }

    /**
     * Conventional method for adding a highlight area to this structure page
     * @param x1 x1
     * @param y1 y1
     * @param z1 z1
     * @param x2 x2
     * @param y2 y2
     * @param z2 z2
     * @param rgb color
     * @return this
     */
    @ZenMethod
    public PageStructure addHighlightArea(double x1, double y1, double z1, double x2, double y2, double z2, int rgb){
        this.highlightAreas.add(HighlightArea.build(x1,y1,z1,x2,y2,z2).setColor(rgb));
        return this;
    }

    /**
     * Conventional method for adding a highlight area to this structure page
     * @param x1 x1
     * @param y1 y1
     * @param z1 z1
     * @param rgb color
     * @return this
     */
    @ZenMethod
    public PageStructure addHighlightArea(double x1, double y1, double z1, int rgb){
        this.highlightAreas.add(HighlightArea.build(x1,y1,z1,x1+1,y1+1,z1+1).setColor(rgb));
        return this;
    }

    /**
     * Conventional method for adding a hover component to this structure page
     * @param x x
     * @param y y
     * @param z z
     * @param drawableSupplier the method supplying the drawable for this hover component
     * @param rgb color in 0xRRGGBB format
     * @return this
     */
    @ZenMethod
    public PageStructure addHoverComponent(float x, float y, float z, int rgb, IDrawableSupplier drawableSupplier){
        this.hoverComponents.add(HoverComponent.build(x,y,z,drawableSupplier).setColor(rgb));
        return this;
    }

    /** Add an overlay in this page.
     * @param overlaySupplier {@link IDrawableSupplier} to provide the drawable
     * @return this
     */
    @ZenMethod
    public PageStructure setOverlay(IDrawableSupplier overlaySupplier){
        this.overlaySupplier = overlaySupplier;
        return this;
    }

    /** Build a drawable from this page.
     * @param width width
     * @param height height
     * @return The drawable built for the specified page size
     */
    @ZenMethod
    @Nullable
    public DrawableBase getOverlay(int width, int height){
        if(overlaySupplier==null) return null;
        return overlaySupplier.process(width, height);
    }
}
