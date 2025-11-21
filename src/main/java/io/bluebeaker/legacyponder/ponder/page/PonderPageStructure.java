package io.bluebeaker.legacyponder.ponder.page;

import io.bluebeaker.legacyponder.ponder.gui.GuiInfoPage;
import io.bluebeaker.legacyponder.ponder.gui.GuiPageStructure;
import io.bluebeaker.legacyponder.ponder.gui.GuiScreenPonder;

public class PonderPageStructure extends PonderPageBase{
    public final String structureID;

    public PonderPageStructure(String id){
        this.structureID = id;
    }

    @Override
    public GuiInfoPage<? extends PonderPageBase> getGuiPage(GuiScreenPonder parent) {
        return new GuiPageStructure(parent,this);
    }
    
}
