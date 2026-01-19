package io.bluebeaker.legacyponder.ponder.page;

import crafttweaker.annotations.ZenRegister;
import io.bluebeaker.legacyponder.ponder.GuiScreenPonder;
import io.bluebeaker.legacyponder.ponder.gui.GuiInfoPage;
import io.bluebeaker.legacyponder.ponder.gui.GuiPageStructure;
import io.bluebeaker.legacyponder.ponder.hover.HighlightArea;
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
}
