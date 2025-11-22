package io.bluebeaker.legacyponder.ponder.drawable;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;

import java.util.HashSet;
import java.util.Set;

public class DrawableGroup extends DrawableBase {
    final Set<DrawableBase> children = new HashSet<>();
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
    public void draw(GuiScreen screen, int mouseX, int mouseY) {
        GlStateManager.pushMatrix();
        GlStateManager.translate(x,y,0);
        for (DrawableBase child : children) {
            child.draw(screen,mouseX,mouseY);
        }
        GlStateManager.popMatrix();
    }
}
