package io.bluebeaker.legacyponder.ponder.gui;

import io.bluebeaker.legacyponder.ponder.GuiScreenPonder;
import io.bluebeaker.legacyponder.ponder.drawable.DrawableBase;
import io.bluebeaker.legacyponder.ponder.page.PonderPageDrawable;
import io.bluebeaker.legacyponder.utils.RenderUtils;

public class GuiPageDrawable extends GuiInfoPage<PonderPageDrawable> {
    private DrawableBase drawableBase;
    public GuiPageDrawable(GuiScreenPonder parent, PonderPageDrawable page) {
        super(parent, page);
        drawableBase=page.getDrawable(pageBounds.w,pageBounds.h);
    }

    @Override
    public void onResize() {
        super.onResize();
        drawableBase=page.getDrawable(pageBounds.w,pageBounds.h);
    }

    @Override
    public void draw(int mouseX, int mouseY, float partialTicks) {
        super.draw(mouseX, mouseY, partialTicks);
        RenderUtils.setViewPort(pageBounds);
        drawableBase.draw(parent,mouseX,mouseY);
        drawableBase.onMouseHover(parent,mouseX,mouseY);
        RenderUtils.endViewPort();
    }
}
