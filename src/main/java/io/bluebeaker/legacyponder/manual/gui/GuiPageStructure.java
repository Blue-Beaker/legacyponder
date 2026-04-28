package io.bluebeaker.legacyponder.manual.gui;

import io.bluebeaker.legacyponder.LegacyPonder;
import io.bluebeaker.legacyponder.UIConfig;
import io.bluebeaker.legacyponder.jeiplugin.JEIUtils;
import io.bluebeaker.legacyponder.manual.GuiUnconfusion;
import io.bluebeaker.legacyponder.manual.drawable.DrawableBase;
import io.bluebeaker.legacyponder.manual.hover.GuiHoverComponent;
import io.bluebeaker.legacyponder.manual.hover.HighlightArea;
import io.bluebeaker.legacyponder.manual.page.PageStructure;
import io.bluebeaker.legacyponder.render.RenderPosUtils;
import io.bluebeaker.legacyponder.render.StructureRenderManager;
import io.bluebeaker.legacyponder.structure.PonderStructure;
import io.bluebeaker.legacyponder.structure.StructureLoader;
import io.bluebeaker.legacyponder.utils.RenderUtils;
import io.bluebeaker.legacyponder.utils.TextUtils;
import io.bluebeaker.legacyponder.utils.Vec2i;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.fml.client.config.GuiButtonExt;
import net.minecraftforge.fml.client.config.GuiSlider;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector3f;

import javax.annotation.Nullable;
import java.awt.*;
import java.io.IOException;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.List;

import static io.bluebeaker.legacyponder.render.StructureRenderManager.getWorld;
import static io.bluebeaker.legacyponder.render.StructureRenderManager.viewPos;

public class GuiPageStructure extends GuiPageWithPopups<PageStructure> {
    /** Is dragging the camera? */
    protected boolean dragCam = false;

    /** Optional overlay drawable */
    protected DrawableBase overlay = null;
    protected boolean overlayFocused = false;

    private FloatBuffer modelView = BufferUtils.createFloatBuffer(16);
    private FloatBuffer projection = BufferUtils.createFloatBuffer(16);
    private IntBuffer viewport = BufferUtils.createIntBuffer(16);
    private ItemStack hoverItem = ItemStack.EMPTY;
    protected GuiSlider slider = null;
    public boolean isStructureLoaded = false;

    public GuiPageStructure(GuiUnconfusion parent, PageStructure page) {
        super(parent, page);
    }

    @Override
    public void onPageRefresh() {
        super.onPageRefresh();
        StructureRenderManager.getWorld().setWorldTime(0);

        PonderStructure structure = StructureLoader.getStructure(page.structureID);
        if(structure!=null){
            StructureRenderManager.getWorld().loadStructure(structure);
            isStructureLoaded=true;
        }else {
            StructureRenderManager.getWorld().clearWorld();
            isStructureLoaded=false;
        }
        StructureRenderManager.resetCameraOffset();
        StructureRenderManager.updateBuffer();
    }

    @Override
    public void onResize() {
        super.onResize();
        int w = pageBounds.w;
        int h = pageBounds.h;
        this.buttonList.clear();

        this.buttonList.add(new GuiButtonExt(ButtonID.RESET_VIEW,0,h- 16,16, 16,"!"));
        this.buttonList.add(new GuiButtonExt(ButtonID.RESET_ZOOM,16,h- 16,16, 16,"="));
        if(UIConfig.zoom_buttons){
            this.buttonList.add(new GuiButtonExt(ButtonID.ZOOM_IN,UIConfig.zoom_slider?112:48,h- 16,16, 16,"+"));
            this.buttonList.add(new GuiButtonExt(ButtonID.ZOOM_OUT,32,h- 16,16, 16,"-"));
        }
        if(UIConfig.zoom_slider){
            GuiSlider slider = new GuiSlider(ButtonID.ZOOM_SLIDER, UIConfig.zoom_buttons?48:32, h - 16, 64, 16, "", "", -5, 5, viewPos.zoom_power, true, true, slider1 -> viewPos.zoom_power= slider1.getValue());
            slider.precision=2;
            this.slider=slider;
            this.buttonList.add(slider);
        }
        updateSlider();

        overlay = page.getOverlay(w,h);
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        super.actionPerformed(button);
        if(button.id==ButtonID.RESET_VIEW){
            StructureRenderManager.viewPos.resetAll();
        } else if (button.id == ButtonID.ZOOM_IN) {
            viewPos.zoom(0.2);
        } else if (button.id == ButtonID.ZOOM_OUT) {
            viewPos.zoom(-0.2);
        } else if (button.id == ButtonID.RESET_ZOOM) {
            viewPos.zoom_power=0;
        }
        updateSlider();
    }

    protected void updateSlider() {
        if(this.slider==null) return;
        this.slider.maxValue=Math.max(5,viewPos.zoom_power);
        this.slider.minValue=Math.min(-5,viewPos.zoom_power);
        this.slider.setValue(viewPos.zoom_power);
        this.slider.updateSlider();
        this.slider.displayString=String.format("%.2f",this.slider.getValue());
    }

    @Override
    public void draw(int mouseX, int mouseY, float partialTicks) {
        RenderUtils.setViewPort(pageBounds);

        if(!isStructureLoaded){
            RenderUtils.drawSplitString(fontRenderer, TextUtils.formatKey("error.legacyponder.structure_not_found",this.page.structureID),width/2,height/2,width,0xFFFFFFFF,true,0.5F,0.5F);
        }else {
            StructureRenderManager.prepareTransformations(pageBounds.x, pageBounds.y, pageBounds.w,pageBounds.h);


            StructureRenderManager.renderStructure(partialTicks, pageBounds.x, pageBounds.y, pageBounds.w,pageBounds.h);

            ScaledResolution scaled = new ScaledResolution(Minecraft.getMinecraft());
            int scale = scaled.getScaleFactor();

            if(!this.page.getHighlightAreas().isEmpty()){
                drawHighlightBoxes(scale);
            }

            // Get Matrix for hover component rendering and raycasting
            GL11.glGetFloat(GL11.GL_MODELVIEW_MATRIX, modelView);
            GL11.glGetFloat(GL11.GL_PROJECTION_MATRIX, projection);
            GL11.glGetInteger(GL11.GL_VIEWPORT, viewport);

            StructureRenderManager.cleanTransformations();

            // Maybe the state is wrong, so clean it to fix fonts not drawn properly
            GlStateManager.enableDepth();
            GlStateManager.disableDepth();

            if(!this.components.isEmpty()){


                GlStateManager.enableTexture2D();
                GlStateManager.disableTexture2D();

                updateHoverPositions(scale,modelView,projection,viewport);

//                drawHoverLines2(scale);

                drawHoverComponents(mouseX, mouseY);

                checkComponentHover(mouseX, mouseY);
            }

            List<String> tooltip = null;

            if(hoverComp == null && !parent.isMouseDownInPage() && this.buttonList.stream().noneMatch(GuiButton::isMouseOver)){
                RayTraceResult rayTraceResult = raycastFromCursor(MouseTracker.INSTANCE.x, MouseTracker.INSTANCE.y, modelView, projection, viewport);

                if(rayTraceResult!=null && rayTraceResult.typeOfHit== RayTraceResult.Type.BLOCK){
                    BlockPos pos = rayTraceResult.getBlockPos();
                    try {
                        this.hoverItem = getWorld().getBlockState(pos).getBlock().getPickBlock(getWorld().getBlockState(pos), rayTraceResult, mc.world, pos, StructureRenderManager.getPlayer());
                    }catch (Exception e){
                        LegacyPonder.getLogger().warn("Error getting pick block for pos {}:",pos,e);
                        this.hoverItem = ItemStack.EMPTY;
                    }

                    if(!hoverItem.isEmpty()){
                        tooltip=getItemToolTip(hoverItem);
//                    String text = hoverItem.getDisplayName();
//                    int textX = mouseX + 5;
//                    int textY = mouseY - 15;
//
//                    drawHoverBackground(new Color(0x5028007f), textX-4, textY-4, textX+mc.fontRenderer.getStringWidth(text)+4, textY+mc.fontRenderer.FONT_HEIGHT+4);
//
//                    mc.fontRenderer.drawStringWithShadow(text, textX, textY, Color.white.getRGB());
                    }
                }
            }else {
                this.hoverItem=ItemStack.EMPTY;
            }

            if (tooltip != null && !tooltip.isEmpty()) {
                GlStateManager.translate(0,0,100);
                this.drawHoveringText(tooltip, mouseX, mouseY);
                RenderHelper.disableStandardItemLighting();
                GlStateManager.translate(0,0,-100);
            }
        }

        super.draw(mouseX, mouseY, partialTicks);

        overlayFocused = false;
        if(overlay!=null){
            overlay.draw(parent,mouseX,mouseY);
            if(overlay.isFocused(parent,mouseX,mouseY)) {
                overlayFocused = true;
                overlay.onMouseHover(parent, mouseX, mouseY);
            }
        }

        drawButtonTooltip(mouseX, mouseY);

        RenderUtils.endViewPort();

    }

    private void drawButtonTooltip(int mouseX, int mouseY) {
        for (GuiButton button : this.buttonList) {
            if(button.isMouseOver()){
                GlStateManager.disableDepth();
                switch (button.id){
                    case ButtonID.RESET_VIEW:
                        this.drawHoveringText(I18n.format("button.legacyponder.reset_view"), mouseX, mouseY);
                        break;
                    case ButtonID.RESET_ZOOM:
                        this.drawHoveringText(I18n.format("button.legacyponder.reset_zoom"), mouseX, mouseY);
                        break;
                }
                GlStateManager.disableLighting();
                break;
            }
        }
    }

    private void drawHighlightBoxes(int scale) {
        GlStateManager.glLineWidth(scale);
        GlStateManager.disableTexture2D();
        GlStateManager.disableLighting();
        GlStateManager.enableBlend();

        for (HighlightArea highlightArea : page.getHighlightAreas()) {
            Vec3d pos1 = highlightArea.getPos1();
            Vec3d pos2 = highlightArea.getPos2();
            Color color = highlightArea.getColor();
            RenderUtils.drawHighlightBox(pos1, pos2, color);
        }
        GlStateManager.enableTexture2D();
    }

    @Nullable
    private RayTraceResult raycastFromCursor(int x, int y, FloatBuffer modelView, FloatBuffer projection, IntBuffer viewport){
        float[] pos0 = RenderPosUtils.unprojectFromScreen(x, y, 0.0F, modelView, projection, viewport);
        float[] pos1 = RenderPosUtils.unprojectFromScreen(x, y, 1.0F, modelView, projection, viewport);
        Vec3d from = new Vec3d(pos0[0], pos0[1], pos0[2]).add(new Vec3d(StructureRenderManager.STRUCTURE_OFFSET));
        Vec3d to = new Vec3d(pos1[0], pos1[1], pos1[2]).add(new Vec3d(StructureRenderManager.STRUCTURE_OFFSET));

        return getWorld().rayTraceBlocks(from, to);
    }

    protected void updateHoverPositions(int scale, FloatBuffer modelView, FloatBuffer projection, IntBuffer viewport){
        for (GuiHoverComponent hoverComponent : this.components) {
            Vector3f pos = hoverComponent.internal.pos;
            float[] floats = RenderPosUtils.projectToScreen(pos.x, pos.y, pos.z, modelView, projection, viewport);

            float x = floats[0] / scale - pageBounds.x;
            float y = parent.height - floats[1] / scale - pageBounds.y;

            hoverComponent.updatePos(x,y);
        }
    }

    private boolean isOverlayFocused() {
        return overlay != null && overlayFocused;
    }

    @Override
    public boolean mouseScroll(int mouseX, int mouseY, int wheelDelta) {
        if(isOverlayFocused() && overlay.onMouseScroll(parent, mouseX, mouseY, wheelDelta)) return true;
        if(mouseScrollHover(mouseX,mouseY,wheelDelta)) return true;

        super.mouseScroll(mouseX, mouseY, wheelDelta);
        viewPos.zoom((double) wheelDelta /120* UIConfig.wheel_sensivity);
        updateSlider();
        return true;
    }

    @Override
    public boolean onMouseClick(int x, int y, int button) throws IOException {
        if(isOverlayFocused()  && overlay.onMouseClick(parent,x,y,button)) return true;

        if(this.buttonList.stream().anyMatch(GuiButton::isMouseOver))
            return super.onMouseClick(x, y, button);
        if(mouseDownHover(x, y, button)) return true;

        dragCam = true;
        return super.onMouseClick(x, y, button);
    }

    @Override
    public boolean onMouseRelease(int x, int y, int state) {
        if(isOverlayFocused()  && overlay.onMouseRelease(parent,x,y,state)) return true;

        if (mouseReleaseHover()) return true;
        dragCam = false;
        return super.onMouseRelease(x, y, state);
    }

    @Override
    public void onKeyTyped(char typedChar, int keyCode) {
        if(parent.isMouseDownInPage()) return;

        if(isOverlayFocused()  && overlay.onKeyTyped(parent,typedChar,keyCode)) return;


        if (keyTypedHover(typedChar, keyCode)) return;
        try {
            JEIUtils.JEIAction action = JEIUtils.getJEIAction(keyCode);
            if(action!= JEIUtils.JEIAction.NONE && !this.hoverItem.isEmpty()){
                JEIUtils.handleJEIAction(hoverItem, action);
                return;
            }
        }catch (Throwable e){
            LegacyPonder.getLogger().warn("Error handling key input {}:",keyCode,e);
        }
        super.onKeyTyped(typedChar, keyCode);
    }

    @Override
    public boolean onMouseDrag(int x, int y, int clickedMouseButton, long timeSinceLastClick) {

        if (dragHover(x, y)) return true;
        if(!dragCam) return false;

        super.onMouseDrag(x, y, clickedMouseButton, timeSinceLastClick);

        ScaledResolution scaledResolution = new ScaledResolution(Minecraft.getMinecraft());
        int factor = scaledResolution.getScaleFactor();
        Vec2i mouseDelta = MouseTracker.INSTANCE.getMouseDelta();
        double deltaX = (float) mouseDelta.x /factor * UIConfig.cursor_sensivity;
        double deltaY = (float) mouseDelta.y /factor * UIConfig.cursor_sensivity;

        if(clickedMouseButton==0){
            viewPos.addYaw((float) deltaX);
            viewPos.addPitch((float) -deltaY);
        }else if (clickedMouseButton<=2){
            viewPos.translate(deltaX/100f,deltaY/100f,0);
        }
        return true;
    }

    @Override
    public void updateScreen() {
        super.updateScreen();
//        StructureRenderManager.getWorld().tick();
    }
    public static class ButtonID{
        public static final int ZOOM_OUT = 0;
        public static final int ZOOM_IN = 1;
        public static final int ZOOM_SLIDER = 2;
        public static final int RESET_ZOOM = 3;
        public static final int RESET_VIEW = 4;
    }
}
