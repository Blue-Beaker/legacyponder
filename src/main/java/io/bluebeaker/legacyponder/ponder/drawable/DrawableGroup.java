package io.bluebeaker.legacyponder.ponder.drawable;

import crafttweaker.annotations.ZenRegister;
import io.bluebeaker.legacyponder.ponder.GuiUnconfusion;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

@ZenClass("mods.legacyponder.DrawableGroup")
@ZenRegister
public class DrawableGroup extends DrawableContainer {
    final List<DrawableBase> children = new ArrayList<>();

    @Nullable
    private DrawableBase focusedChild = null;
    protected int x1;
    protected int y1;
    protected int x2;
    protected int y2;

    public DrawableGroup(){

    }

    @ZenMethod
    @Override
    public DrawableBase setPosition(int x, int y) {
        super.setPosition(x, y);
        for (DrawableBase child : children) {
            child.updateParentPos();
        }
        return this;
    }

    @Override
    public void updateParentPos() {
        super.updateParentPos();
        for (DrawableBase child : children) {
            child.updateParentPos();
        }
    }

    @ZenMethod
    public void addChild(DrawableBase child){
        if(child!=this){
            child.setParent(this);
            child.updateParentPos();
            children.add(child);
            updateSizes();
        }
    }

    @ZenMethod
    public void addChild(DrawableBase child, int x, int y){
        child.setPosition(x,y);
        addChild(child);
    }
    @ZenMethod
    public void removeChild(DrawableBase child){
        children.remove(child);
        updateSizes();
    }
    @ZenMethod
    public void clear(){
        this.children.clear();
        updateSizes();
    }
    @Override
    public void draw(GuiUnconfusion screen, int mouseX, int mouseY) {
        GlStateManager.pushMatrix();
        GlStateManager.translate(x,y,0);
        for (DrawableBase child : children) {
            child.draw(screen,mouseX-this.x,mouseY-this.y);
        }
        GlStateManager.popMatrix();
    }

    @Nullable
    @Override
    public DrawableBase getFocusedChild() {
        return focusedChild;
    }
    @Override
    public void updateSizes(){
        x1=0;
        y1=0;
        x2=0;
        y2=0;
        for (DrawableBase child : children) {
            x1 =Math.min(x1,child.getXMin());
            y1 =Math.min(y1,child.getYMin());
            x2 =Math.max(x2,child.getXMax());
            y2 =Math.max(y2,child.getYMax());
        }
        w=x2-x1;
        h=y2-y1;
        if(this.parent instanceof DrawableContainer){
            ((DrawableContainer) this.parent).updateSizes();
        }
    }

    @Override
    public boolean onMouseHover(GuiUnconfusion screen, int mouseX, int mouseY) {
        if(!this.isInteractable()){
            return false;
        }
        for (int i=this.children.size()-1;i>=0;i--) {
            DrawableBase child = this.children.get(i);
            if(child.isFocused(screen, mouseX - this.x, mouseY - this.y)){
                focusedChild=child;
                return child.onMouseHover(screen, mouseX - this.x, mouseY - this.y);
            }
        }
        focusedChild=null;
        return false;
    }

    @Override
    public boolean isFocused(GuiScreen screen, int mouseX, int mouseY) {
        this.focusedChild=null;
        for (DrawableBase child : children) {
            if(child.isFocused(screen, mouseX - this.x, mouseY - this.y)){
                this.focusedChild=child;
                return true;
            }
        }
        return false;
    }

    @ZenMethod
    public int getXMin() {return x+x1;}
    @ZenMethod
    public int getYMin() {return y+y1;}
    @ZenMethod
    public int getXMax(){return x+x2;}
    @ZenMethod
    public int getYMax(){return y+y2;}

    @ZenMethod
    @Override
    public DrawableBase setSize(int w, int h) {
        return this;
    }

}
