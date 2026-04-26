package io.bluebeaker.legacyponder.manual.page;

import io.bluebeaker.legacyponder.manual.GuiUnconfusion;
import io.bluebeaker.legacyponder.manual.gui.GuiInfoPage;

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
