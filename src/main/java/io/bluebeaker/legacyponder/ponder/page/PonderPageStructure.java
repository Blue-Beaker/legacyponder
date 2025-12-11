package io.bluebeaker.legacyponder.ponder.page;

import crafttweaker.annotations.ZenRegister;
import io.bluebeaker.legacyponder.ponder.gui.GuiInfoPage;
import io.bluebeaker.legacyponder.ponder.gui.GuiPageStructure;
import io.bluebeaker.legacyponder.ponder.gui.GuiScreenPonder;
import stanhebben.zenscript.annotations.ZenClass;

@ZenClass("mods.legacyponder.PonderPageStructure")
@ZenRegister
public class PonderPageStructure extends PonderPageBase{
    public final String structureID;

    public PonderPageStructure(String id){
        this.structureID = id;
    }

    @Override
    public GuiInfoPage<PonderPageStructure> getGuiPage(GuiScreenPonder parent) {
        return new GuiPageStructure(parent,this);
    }

}
