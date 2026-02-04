package io.bluebeaker.legacyponder.ponder.drawable;

import crafttweaker.annotations.ZenRegister;
import io.bluebeaker.legacyponder.ponder.GuiScreenPonder;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

@ZenClass("mods.legacyponder.DrawableGroup")
@ZenRegister
public class DrawableGroup extends DrawableInteractive {
    final List<DrawableBase> children = new ArrayList<>();

    @Nullable
    private DrawableBase focusedChild = null;

    public DrawableGroup(){

    }

    @ZenMethod
    @Override
    public DrawableBase setPosition(int x, int y) {
        super.setPosition(x, y);
        for (DrawableBase child : children) {
            setChildParentPos(child);
        }
        return this;
    }

    @ZenMethod
    public void addChild(DrawableBase child){
        if(child!=this){
            setChildParentPos(child);
            children.add(child);
            updateSizes();
        }
    }

    @ZenMethod
    public void addChild(DrawableBase child, int x, int y){
        addChild(child);
        child.setPosition(x,y);
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
    public void draw(GuiScreen screen, int mouseX, int mouseY) {
        GlStateManager.pushMatrix();
        GlStateManager.translate(x,y,0);
        for (DrawableBase child : children) {
            child.draw(screen,mouseX-this.x,mouseY-this.y);
        }
        GlStateManager.popMatrix();
    }

    private void setChildParentPos(DrawableBase child) {
        child.parentX=this.x+this.parentX;
        child.parentY=this.y+this.parentY;
    }

    private void updateSizes(){
        w=0;
        h=0;
        for (DrawableBase child : children) {
            w=Math.max(w,child.getX()+child.getWidth());
            h=Math.max(h,child.getY()+child.getHeight());
        }
    }

    @Override
    public boolean onMouseClick(GuiScreenPonder parent, int x, int y, int button) {
        if(this.focusedChild==null) return false;
        return this.focusedChild.onMouseClick(parent,x,y,button);
    }

    @Override
    public boolean onMouseRelease(GuiScreenPonder parent, int x, int y, int state) {
        if(this.focusedChild==null) return false;
        return this.focusedChild.onMouseClick(parent,x,y,state);
    }

    @Override
    public void onKeyTyped(GuiScreenPonder parent, char typedChar, int keyCode) {
        if(this.focusedChild==null) return;
        this.focusedChild.onKeyTyped(parent,typedChar,keyCode);
    }

    @Override
    public boolean onMouseHover(GuiScreen screen, int mouseX, int mouseY) {
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

}
