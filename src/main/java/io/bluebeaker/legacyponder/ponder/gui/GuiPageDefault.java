package io.bluebeaker.legacyponder.ponder.gui;

import io.bluebeaker.legacyponder.ponder.page.PonderPageBase;

public class GuiPageDefault<T extends PonderPageBase> extends GuiInfoPage<T> {
    public GuiPageDefault(GuiScreenPonder parent, T page) {
        super(parent, page);
    }

    @Override
    public void draw(int mouseX, int mouseY, float partialTicks) {
        super.draw(mouseX, mouseY, partialTicks);
        page.draw(parent,mouseX,mouseY,partialTicks);
    }
}
