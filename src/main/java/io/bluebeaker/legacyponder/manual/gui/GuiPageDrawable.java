package io.bluebeaker.legacyponder.manual.gui;

import io.bluebeaker.legacyponder.manual.GuiUnconfusion;
import io.bluebeaker.legacyponder.manual.drawable.DrawableBase;
import io.bluebeaker.legacyponder.manual.drawable.DrawableContainer;
import io.bluebeaker.legacyponder.manual.drawable.DrawableHoverPos;
import io.bluebeaker.legacyponder.manual.hover.GuiHoverComponent;
import io.bluebeaker.legacyponder.manual.page.PageDrawable;
import io.bluebeaker.legacyponder.utils.RenderUtils;
import io.bluebeaker.legacyponder.utils.TextUtils;
import io.bluebeaker.legacyponder.utils.Vec2i;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import org.lwjgl.util.vector.Vector3f;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

public class GuiPageDrawable extends GuiPageWithPopups<PageDrawable> {
    /**
     * Cached drawable instance to be drawn. null if not initialized
     */
    @Nullable
    private DrawableBase drawable = null;
    private int lastW = 0;
    private int lastH = 0;
    public GuiPageDrawable(GuiUnconfusion parent, PageDrawable page) {
        super(parent, page);
    }

    @Override
    public void onResize() {
        super.onResize();
        if(lastW!=pageBounds.w || lastH!=pageBounds.h){
            drawable =page.getDrawable(pageBounds.w,pageBounds.h);
            updateHoverPositions();
        }
        lastW=pageBounds.w;
        lastH=pageBounds.h;
    }

    @Override
    public void onKeyTyped(char typedChar, int keyCode) {
        if(drawable == null) return;

        if(keyTypedHover(typedChar, keyCode)) return;

        drawable.onKeyTyped(parent,typedChar,keyCode);
        super.onKeyTyped(typedChar, keyCode);
    }

    @Override
    public void draw(int mouseX, int mouseY, float partialTicks) {
        super.draw(mouseX, mouseY, partialTicks);
        if(drawable == null) {
            RenderUtils.drawSplitString(fontRenderer, TextUtils.formatKey("error.legacyponder.drawable_not_provided"),width/2,height/2,width,0xFFFFFFFF,true,0.5F,0.5F);
        };

        RenderUtils.setViewPort(pageBounds);

        drawable.draw(parent,mouseX,mouseY);

        if(!components.isEmpty()) {
            GlStateManager.translate(0,0,350);
            ScaledResolution scaled = new ScaledResolution(Minecraft.getMinecraft());
//            int scale = scaled.getScaleFactor();
//            drawHoverLines2(scale);
            drawHoverComponents(mouseX, mouseY);
            checkComponentHover(mouseX, mouseY);
            GlStateManager.translate(0,0,-350);
        }

        if(hoverComp == null && drawable.isFocused(parent,mouseX,mouseY)){
            drawable.onMouseHover(parent,mouseX,mouseY);
        }
        RenderUtils.endViewPort();
    }

    @Override
    public boolean onMouseClick(int x, int y, int button) {
        if(drawable == null) return false;

        if(mouseDownHover(x, y, button)) return true;

        return drawable.onMouseClick(parent,x,y,button);
    }
    @Override
    public boolean onMouseRelease(int x, int y, int state) {
        if(drawable == null) return false;

        if(mouseReleaseHover()) return true;

        return drawable.onMouseRelease(parent,x,y,state);
    }

    @Override
    public boolean mouseScroll(int mouseX, int mouseY, int wheelDelta) {
        super.mouseScroll(mouseX, mouseY, wheelDelta);
        if(drawable == null) return false;

        if(mouseScrollHover(mouseX,mouseY,wheelDelta)) return true;

        return drawable.onMouseScroll(parent, mouseX, mouseY, wheelDelta);
    }

    protected void updateHoverPositions(){
        Map<Integer, Vec2i> hoverPositions = new HashMap<>();
        DrawableBase drawable = this.drawable;

        if(drawable instanceof DrawableContainer){
            for (DrawableBase child : ((DrawableContainer) drawable).getAllChildren()) {
                if(child instanceof DrawableHoverPos){
                    DrawableHoverPos hoverPos = (DrawableHoverPos) child;
                    hoverPositions.put(hoverPos.id,new Vec2i(hoverPos.getAbsX(),hoverPos.getAbsY()));
                }
            }
        }

        for (GuiHoverComponent hoverComponent : this.components) {
            Vec2i hoverPos = hoverPositions.get(hoverComponent.internal.getID());
            if(hoverPos!=null){
                hoverComponent.updatePos(hoverPos.x,hoverPos.y);
                continue;
            }
            Vector3f pos = hoverComponent.internal.pos;
            hoverComponent.updatePos(pos.x,pos.y);
        }
    }

    @Override
    public boolean onMouseDrag(int x, int y, int clickedMouseButton, long timeSinceLastClick) {
        if (dragHover(x, y)){
            updateHoverPositions();
            return true;
        }
        return super.onMouseDrag(x, y, clickedMouseButton, timeSinceLastClick);
    }
}
