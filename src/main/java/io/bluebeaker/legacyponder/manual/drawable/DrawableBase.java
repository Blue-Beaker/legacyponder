package io.bluebeaker.legacyponder.manual.drawable;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import io.bluebeaker.legacyponder.manual.GuiUnconfusion;
import io.bluebeaker.legacyponder.manual.link.*;
import io.bluebeaker.legacyponder.utils.BoundingBox2D;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

import javax.annotation.Nullable;
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

    protected LinkBase link = null;
    private boolean interactable = true;
    protected boolean isHovered = false;
    protected DrawableBase parent = null;

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

    public abstract void draw(GuiUnconfusion screen, int mouseX, int mouseY);

    /** Get drawing offset for its child
     * @return Offset X
     */
    public int getOffsetX() {return x;}
    /** Get drawing offset for its child
     * @return Offset Y
     */
    public int getOffsetY() {return y;}

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

    public boolean isFocused(GuiScreen screen, int mouseX, int mouseY){
        return this.getBoundingBox().contains(mouseX,mouseY);
    }

    public boolean onMouseHover(GuiUnconfusion screen, int mouseX, int mouseY){
        if (this.link!=null){
            this.isHovered=true;
            List<String> tooltip = link.getTooltip(screen);
            if (tooltip==null || tooltip.isEmpty()) return false;

            GlStateManager.translate(0,0,100);
            screen.drawHoveringText(tooltip,mouseX+parentX,mouseY+parentY);
            RenderHelper.disableStandardItemLighting();
            GlStateManager.translate(0,0,-100);
            return true;
        }
        return false;}

    public boolean isLastHovered() {
        boolean lastHovered = this.isHovered;
        this.isHovered=false;
        return lastHovered;
    }

    public void setParent(DrawableBase newParent){
        if(this==newParent) return;
        this.parent=newParent;
    }
    public void updateParentPos(){
        if(parent==null){
            parentX=0;
            parentY=0;
            return;
        }
        this.parentX=parent.getOffsetX()+parent.parentX;
        this.parentY=parent.getOffsetY()+parent.parentY;
    }

    @ZenMethod
    public int getAbsX() {return x+parentX;}
    @ZenMethod
    public int getAbsY() {return y+parentY;}

    /** Override this to add a default link to this drawable.
     * Make sure to call {@link DrawableBase#resetLink} in your init method after setting fields.
     * @return Default Link for this drawable
     */
    @Nullable
    public LinkBase getDefaultLink(){
        return null;
    }

    @ZenMethod
    public DrawableBase setLinkHover(String tooltip){
        this.link=new LinkHover(tooltip);
        return this;
    }

    @ZenMethod
    public DrawableBase setLinkManual(String id, int page){
        this.link=new LinkPonder(id, page);
        return this;
    }
    @ZenMethod
    public DrawableBase setLinkManual(String id){
        this.link=new LinkPonder(id);
        return this;
    }
    @ZenMethod
    public DrawableBase setLinkUrl(String url, String tooltip){
        this.link=new LinkUrl(url,tooltip);
        return this;
    }
    @ZenMethod
    public DrawableBase setLinkItem(IItemStack item){
        this.link=new LinkItem(CraftTweakerMC.getItemStack(item));
        return this;
    }
    @ZenMethod
    public DrawableBase resetLink(){
        this.link=getDefaultLink();
        return this;
    }

    public boolean onKeyTyped(GuiUnconfusion parent, char typedChar, int keyCode) {
        if (this.link!=null){
            return link.onKeyDown(parent,keyCode);
        }
        return false;
    }

    @ZenMethod
    public boolean hasLink(){
        return this.link!=null;
    }

    @ZenMethod
    public void setInteractable(boolean interactable) {
        this.interactable = interactable;
    }

    @ZenMethod
    public boolean isInteractable() {
        return interactable;
    }

    @ZenMethod
    public boolean isLinkClickable() {
        return this.hasLink() && this.link.isClickable();
    }

    public boolean onMouseClick(GuiUnconfusion parent, int x, int y, int button) {
        if (this.link!=null && this.isFocused(parent,x,y)){
            link.onClick(parent,button);
            return true;
        }
        return false;
    }

    public boolean onMouseRelease(GuiUnconfusion parent, int x, int y, int state) {
        return false;
    }

    public boolean onMouseScroll(int mouseX, int mouseY, int wheelDelta) {
        return false;
    }
}
