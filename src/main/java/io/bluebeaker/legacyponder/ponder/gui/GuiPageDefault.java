package io.bluebeaker.legacyponder.ponder.gui;

import io.bluebeaker.legacyponder.ponder.GuiUnconfusion;
import io.bluebeaker.legacyponder.ponder.page.PageBase;

public class GuiPageDefault<T extends PageBase> extends GuiInfoPage<T> {
    public GuiPageDefault(GuiUnconfusion parent, T page) {
        super(parent, page);
    }

    @Override
    public void draw(int mouseX, int mouseY, float partialTicks) {
        super.draw(mouseX, mouseY, partialTicks);
        page.draw(parent,mouseX,mouseY,partialTicks);
    }
}
