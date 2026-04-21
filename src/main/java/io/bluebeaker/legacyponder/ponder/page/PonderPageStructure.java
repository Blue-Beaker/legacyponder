package io.bluebeaker.legacyponder.ponder.page;

import crafttweaker.annotations.ZenRegister;
import io.bluebeaker.legacyponder.crafttweaker.IDrawableSupplier;
import io.bluebeaker.legacyponder.ponder.GuiScreenPonder;
import io.bluebeaker.legacyponder.ponder.gui.GuiInfoPage;
import io.bluebeaker.legacyponder.ponder.gui.GuiPageStructure;
import io.bluebeaker.legacyponder.ponder.hover.HighlightArea;
import io.bluebeaker.legacyponder.ponder.hover.HoverComponent;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenGetter;
import stanhebben.zenscript.annotations.ZenMethod;

import java.util.ArrayList;
import java.util.List;

@ZenClass("mods.legacyponder.PonderPageStructure")
@ZenRegister
public class PonderPageStructure extends PonderPageBase{

    protected final List<HighlightArea> highlightAreas = new ArrayList<>();

    /**
     * @return Structure ID of this page
     */
    @ZenMethod
    @ZenGetter("structureID")
    public String getStructureID() {
        return structureID;
    }

    public final String structureID;

    public PonderPageStructure(String id){
        this.structureID = id;
    }

    @Override
    public GuiInfoPage<PonderPageStructure> getGuiPage(GuiScreenPonder parent) {
        return new GuiPageStructure(parent,this);
    }

    @ZenMethod
    public PonderPageStructure addHighlightArea(HighlightArea area){
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
     * @param r red
     * @param g green
     * @param b blue
     * @return this
     */
    @ZenMethod
    public PonderPageStructure addHighlightArea(double x1, double y1, double z1, double x2, double y2, double z2, int r, int g, int b){
        this.highlightAreas.add(HighlightArea.build(x1,y1,z1,x2,y2,z2).setColor(r,g,b));
        return this;
    }

    /**
     * Conventional method for adding a highlight area to this structure page
     * @param x1 x1
     * @param y1 y1
     * @param z1 z1
     * @param r red
     * @param g green
     * @param b blue
     * @return this
     */
    @ZenMethod
    public PonderPageStructure addHighlightArea(double x1, double y1, double z1, int r, int g, int b){
        this.highlightAreas.add(HighlightArea.build(x1,y1,z1,x1+1,y1+1,z1+1).setColor(r,g,b));
        return this;
    }

    /**
     * Conventional method for adding a hover component to this structure page
     * @param x x
     * @param y y
     * @param z z
     * @param drawableSupplier the method supplying the drawable for this hover component
     * @param r red
     * @param g green
     * @param b blue
     * @return this
     */
    @ZenMethod
    public PonderPageStructure addHoverComponent(float x, float y, float z, int r, int g, int b, IDrawableSupplier drawableSupplier){
        this.hoverComponents.add(HoverComponent.build(x,y,z,drawableSupplier).setColor(r,g,b));
        return this;
    }
}
