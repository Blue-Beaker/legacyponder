package io.bluebeaker.legacyponder.ponder.drawable;

import crafttweaker.annotations.ZenRegister;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

import java.util.ArrayList;
import java.util.List;

@ZenClass("mods.legacyponder.DrawableGroup")
@ZenRegister
public class DrawableGroup extends DrawableBase {
    final List<DrawableBase> children = new ArrayList<>();
    public DrawableGroup(){

    }

    @ZenMethod
    public void addChild(DrawableBase child){
        if(child!=this)
            children.add(child);
    }
    @ZenMethod
    public void addChild(DrawableBase child, int x, int y){
        addChild(child);
        child.setPosition(x,y);
    }
    @ZenMethod
    public void removeChild(DrawableBase child){
        children.remove(child);
    }
    @ZenMethod
    public void clear(){
        this.children.clear();
    }

    @Override
    public boolean onMouseHover(GuiScreen screen, int mouseX, int mouseY) {
        for (int i=this.children.size()-1;i>=0;i--) {
            DrawableBase child = this.children.get(i);
            boolean b = child.onMouseHover(screen, mouseX - this.x, mouseY - this.y);
            if(b){return true;}
        }
        return false;
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
}
