package io.bluebeaker.legacyponder.ponder.drawable;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import io.bluebeaker.legacyponder.ponder.GuiScreenPonder;
import io.bluebeaker.legacyponder.ponder.link.LinkBase;
import io.bluebeaker.legacyponder.ponder.link.LinkItem;
import io.bluebeaker.legacyponder.ponder.link.LinkPonder;
import io.bluebeaker.legacyponder.utils.BoundingBox2D;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

import java.util.List;

@ZenClass("mods.legacyponder.DrawableBase")
@ZenRegister
public abstract class DrawableBase {
    protected int x;
    protected int y;
    protected int w;
    protected int h;

    public int parentX = 0;
    public int parentY = 0;

    private LinkBase link = null;

    public DrawableBase(){
    }
    @ZenMethod
    public DrawableBase setPosition(int x, int y){
        this.x=x;
        this.y=y;
        return this;
    }
    @ZenMethod
    public DrawableBase setSize(int w, int h){
        this.w=w;
        this.h=h;
        return this;
    }

    public abstract void draw(GuiScreen screen, int mouseX, int mouseY);

    @ZenMethod
    public int getX() {return x;}
    @ZenMethod
    public int getY() {return y;}

    @ZenMethod
    public int getXMin() {return x;}
    @ZenMethod
    public int getYMin() {return y;}
    @ZenMethod
    public int getXMax(){return x+getWidth();}
    @ZenMethod
    public int getYMax(){return y+getHeight();}

    @ZenMethod
    public int getWidth(){return w;}
    @ZenMethod
    public int getHeight(){return h;}

    public BoundingBox2D getBoundingBox(){
        return BoundingBox2D.fromMinMax(getXMin(), getYMin(),getXMax(),getYMax());
    }

    public boolean isFocused(GuiScreenPonder screen, int mouseX, int mouseY){
        return this.getBoundingBox().contains(mouseX,mouseY);
    }

    public boolean onMouseHover(GuiScreenPonder screen, int mouseX, int mouseY){
        if (this.link!=null && this.isFocused(screen,x,y)){

            List<String> tooltip = link.getTooltip(screen);
            if (tooltip==null || tooltip.isEmpty()) return false;

            GlStateManager.translate(0,0,100);
            screen.drawHoveringText(tooltip,mouseX+parentX,mouseY+parentY);
            RenderHelper.disableStandardItemLighting();
            GlStateManager.translate(0,0,-100);
            return true;
        }
        return false;}

    @ZenMethod
    public int getAbsX() {return x+parentX;}
    @ZenMethod
    public int getAbsY() {return y+parentY;}


    @ZenMethod
    public DrawableBase setLinkPonder(String id){
        this.link=new LinkPonder(id);
        return this;
    }
    @ZenMethod
    public DrawableBase setLinkItem(IItemStack item){
        this.link=new LinkItem(CraftTweakerMC.getItemStack(item));
        return this;
    }

    public void onKeyTyped(GuiScreenPonder parent, char typedChar, int keyCode) {
        if (this.link!=null){
            link.onKeyDown(parent,keyCode);
        }
    }

    @ZenMethod
    public boolean hasLink(){
        return this.link!=null;
    }

    @ZenMethod
    public boolean isInteractable() {
        return this.hasLink();
    }

    public boolean onMouseClick(GuiScreenPonder parent, int x, int y, int button) {
        if (this.link!=null && this.isFocused(parent,x,y)){
            link.onClick(parent,button);
            return true;
        }
        return false;
    }

    public boolean onMouseRelease(GuiScreenPonder parent, int x, int y, int state) {
        return false;
    }
}
