package io.bluebeaker.legacyponder.manual.gui;

import io.bluebeaker.legacyponder.manual.GuiUnconfusion;
import io.bluebeaker.legacyponder.manual.drawable.DrawableBase;
import io.bluebeaker.legacyponder.manual.hover.GuiHoverComponent;
import io.bluebeaker.legacyponder.manual.page.PageDrawable;
import io.bluebeaker.legacyponder.utils.RenderUtils;
import io.bluebeaker.legacyponder.utils.TextUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import org.lwjgl.util.vector.Vector3f;

import javax.annotation.Nullable;

public class GuiPageDrawable extends GuiPageWithPopups<PageDrawable> {
    /**
     * Cached drawable instance to be drawn. null if not initialized
     */
    @Nullable
    private DrawableBase drawableBase = null;
    public GuiPageDrawable(GuiUnconfusion parent, PageDrawable page) {
        super(parent, page);
    }

    @Override
    public void onResize() {
        super.onResize();
        drawableBase=page.getDrawable(pageBounds.w,pageBounds.h);
        updateHoverPositions();
    }

    @Override
    public void onKeyTyped(char typedChar, int keyCode) {
        if(drawableBase == null) return;

        if(keyTypedHover(typedChar, keyCode)) return;

        drawableBase.onKeyTyped(parent,typedChar,keyCode);
        super.onKeyTyped(typedChar, keyCode);
    }

    @Override
    public void draw(int mouseX, int mouseY, float partialTicks) {
        super.draw(mouseX, mouseY, partialTicks);
        if(drawableBase == null) {
            RenderUtils.drawSplitString(fontRenderer, TextUtils.formatKey("error.legacyponder.drawable_not_provided"),width/2,height/2,width,0xFFFFFFFF,true,0.5F,0.5F);
        };

        RenderUtils.setViewPort(pageBounds);

        drawableBase.draw(parent,mouseX,mouseY);

        if(!components.isEmpty()) {
            GlStateManager.translate(0,0,200);
            ScaledResolution scaled = new ScaledResolution(Minecraft.getMinecraft());
//            int scale = scaled.getScaleFactor();
//            drawHoverLines2(scale);
            drawHoverComponents(mouseX, mouseY);
            checkComponentHover(mouseX, mouseY);
            GlStateManager.translate(0,0,-200);
        }

        if(hoverComp == null && drawableBase.isFocused(parent,mouseX,mouseY)){
            drawableBase.onMouseHover(parent,mouseX,mouseY);
        }
        RenderUtils.endViewPort();
    }

    @Override
    public boolean onMouseClick(int x, int y, int button) {
        if(drawableBase == null) return false;

        if(mouseDownHover(x, y, button)) return true;

        return drawableBase.onMouseClick(parent,x,y,button);
    }
    @Override
    public boolean onMouseRelease(int x, int y, int state) {
        if(drawableBase == null) return false;

        if(mouseReleaseHover()) return true;

        return drawableBase.onMouseRelease(parent,x,y,state);
    }

    @Override
    public boolean mouseScroll(int mouseX, int mouseY, int wheelDelta) {
        super.mouseScroll(mouseX, mouseY, wheelDelta);
        if(drawableBase == null) return false;

        if(mouseScrollHover(mouseX,mouseY,wheelDelta)) return true;

        return drawableBase.onMouseScroll(parent, mouseX, mouseY, wheelDelta);
    }

    protected void updateHoverPositions(){
        for (GuiHoverComponent hoverComponent : this.components) {
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
