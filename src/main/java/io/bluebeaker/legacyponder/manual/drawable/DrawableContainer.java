package io.bluebeaker.legacyponder.manual.drawable;

import io.bluebeaker.legacyponder.manual.GuiUnconfusion;
import stanhebben.zenscript.annotations.ZenMethod;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public abstract class DrawableContainer extends DrawableBase {

    @Nullable
    abstract DrawableBase getFocusedChild();
    public void updateSizes(){}

    @ZenMethod
    @Override
    public DrawableBase setPosition(int x, int y) {
        super.setPosition(x, y);
        for (DrawableBase child : getChildren()) {
            child.updateParentPos();
        }
        return this;
    }

    protected abstract List<DrawableBase> getChildren();

    @Override
    public boolean onMouseClick(GuiUnconfusion parent, int x, int y, int button) {
        if(this.getFocusedChild()==null) return false;
        return this.getFocusedChild().onMouseClick(parent,x-getOffsetX(),y-getOffsetY(),button);
    }

    @Override
    public boolean onMouseRelease(GuiUnconfusion parent, int x, int y, int state) {
        if(this.getFocusedChild()==null) return false;
        return this.getFocusedChild().onMouseRelease(parent,x-getOffsetX(),y-getOffsetY(),state);
    }

    @Override
    public boolean onMouseScroll(GuiUnconfusion parent, int mouseX, int mouseY, int wheelDelta) {
        if(this.getFocusedChild()==null) return false;
        return getFocusedChild().onMouseScroll(parent, mouseX-getOffsetX(), mouseY-getOffsetY(), wheelDelta);
    }

    @Override
    public boolean onKeyTyped(GuiUnconfusion parent, char typedChar, int keyCode) {
        if(this.getFocusedChild()==null) return false;
        return this.getFocusedChild().onKeyTyped(parent,typedChar,keyCode);
    }

    public List<DrawableBase> getAllChildren(){
        List<DrawableBase> children = this.getChildren();
        List<DrawableBase> allChildren = new ArrayList<>(children);
        for (DrawableBase child : children) {
            if(child instanceof DrawableContainer){
                allChildren.addAll(((DrawableContainer) child).getAllChildren());
            }
        }
        return allChildren;
    }
}
