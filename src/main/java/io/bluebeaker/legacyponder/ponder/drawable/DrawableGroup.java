package io.bluebeaker.legacyponder.ponder.drawable;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;

import java.util.ArrayList;
import java.util.List;

public class DrawableGroup extends DrawableBase {
    final List<DrawableBase> children = new ArrayList<>();
    public DrawableGroup(){

    }

    public void addChild(DrawableBase child){
        if(child!=this)
            children.add(child);
    }
    public void removeChild(DrawableBase child){
        children.remove(child);
    }
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
