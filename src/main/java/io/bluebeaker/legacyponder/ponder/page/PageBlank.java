package io.bluebeaker.legacyponder.ponder.page;

import io.bluebeaker.legacyponder.ponder.GuiUnconfusion;
import io.bluebeaker.legacyponder.ponder.gui.GuiInfoPage;

public class PageBlank extends PageBase{
    public static final PageBlank INSTANCE = new PageBlank();
    protected PageBlank() {
        super();
    }

    @Override
    public GuiInfoPage<? extends PageBase> getGuiPage(GuiUnconfusion parent) {
        return new GuiInfoPage<>(parent, this);
    }
}
