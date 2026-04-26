package io.bluebeaker.legacyponder.manual.gui;

import io.bluebeaker.legacyponder.manual.GuiUnconfusion;
import io.bluebeaker.legacyponder.manual.drawable.DrawableBase;
import io.bluebeaker.legacyponder.manual.page.PageDrawable;
import io.bluebeaker.legacyponder.utils.RenderUtils;

import javax.annotation.Nullable;

public class GuiPageDrawable extends GuiInfoPage<PageDrawable> {
    /**
     * Cached drawable instance to be drawn. null if not initialized
     */
    @Nullable
    private DrawableBase drawableBase = null;
    public GuiPageDrawable(GuiUnconfusion parent, PageDrawable page) {
        super(parent, page);
    }

    @Override
    public void onResize() {
        super.onResize();
        drawableBase=page.getDrawable(pageBounds.w,pageBounds.h);
    }

    @Override
    public void onKeyTyped(char typedChar, int keyCode) {
        if(drawableBase == null) return;
        drawableBase.onKeyTyped(parent,typedChar,keyCode);
        super.onKeyTyped(typedChar, keyCode);
    }
    @Override
    public void draw(int mouseX, int mouseY, float partialTicks) {
        super.draw(mouseX, mouseY, partialTicks);
        if(drawableBase == null) return;
        RenderUtils.setViewPort(pageBounds);
        drawableBase.draw(parent,mouseX,mouseY);
        if(drawableBase.isFocused(parent,mouseX,mouseY))
            drawableBase.onMouseHover(parent,mouseX,mouseY);
        RenderUtils.endViewPort();
    }

    @Override
    public boolean onMouseClick(int x, int y, int button) {
        if(drawableBase == null) return false;
        return drawableBase.onMouseClick(parent,x,y,button);
    }
    @Override
    public boolean onMouseRelease(int x, int y, int state) {
        if(drawableBase == null) return false;
        return drawableBase.onMouseRelease(parent,x,y,state);
    }

    @Override
    public boolean mouseScroll(int mouseX, int mouseY, int wheelDelta) {
        super.mouseScroll(mouseX, mouseY, wheelDelta);
        if(drawableBase == null) return false;
        return drawableBase.onMouseScroll(mouseX, mouseY, wheelDelta);
    }
}
