package io.bluebeaker.legacyponder.ponder.page;

import io.bluebeaker.legacyponder.ponder.gui.GuiInfoPage;
import io.bluebeaker.legacyponder.ponder.gui.GuiScreenPonder;

public class PonderPageBlank extends PonderPageBase{
    public static final PonderPageBlank INSTANCE = new PonderPageBlank();
    protected PonderPageBlank() {
        super();
    }

    @Override
    public GuiInfoPage<? extends PonderPageBase> getGuiPage(GuiScreenPonder parent) {
        return new GuiInfoPage<>(parent, this);
    }
}
