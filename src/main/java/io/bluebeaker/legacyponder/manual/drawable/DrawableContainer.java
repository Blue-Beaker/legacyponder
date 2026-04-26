package io.bluebeaker.legacyponder.manual.drawable;

import io.bluebeaker.legacyponder.manual.GuiUnconfusion;

import javax.annotation.Nullable;

public abstract class DrawableContainer extends DrawableBase {

    @Nullable
    abstract DrawableBase getFocusedChild();
    public void updateSizes(){}

    @Override
    public boolean onMouseClick(GuiUnconfusion parent, int x, int y, int button) {
        if(this.getFocusedChild()==null) return false;
        return this.getFocusedChild().onMouseClick(parent,x-this.x,y-this.y,button);
    }

    @Override
    public boolean onMouseRelease(GuiUnconfusion parent, int x, int y, int state) {
        if(this.getFocusedChild()==null) return false;
        return this.getFocusedChild().onMouseRelease(parent,x-this.x,y-this.y,state);
    }

    @Override
    public boolean onMouseScroll(int mouseX, int mouseY, int wheelDelta) {
        if(this.getFocusedChild()==null) return false;
        return getFocusedChild().onMouseScroll(mouseX, mouseY, wheelDelta);
    }

    @Override
    public boolean onKeyTyped(GuiUnconfusion parent, char typedChar, int keyCode) {
        if(this.getFocusedChild()==null) return false;
        return this.getFocusedChild().onKeyTyped(parent,typedChar,keyCode);
    }
}
