package io.bluebeaker.legacyponder.manual.gui;

import io.bluebeaker.legacyponder.LegacyPonder;
import io.bluebeaker.legacyponder.manual.GuiUnconfusion;
import io.bluebeaker.legacyponder.manual.hover.GuiHoverComponent;
import io.bluebeaker.legacyponder.manual.hover.HoverComponent;
import io.bluebeaker.legacyponder.manual.page.PagePopups;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;

import java.util.ArrayList;
import java.util.List;

public abstract class GuiPageWithPopups<T extends PagePopups> extends GuiInfoPage<T> {
    /**
     * All hover components in this structure page
     */
    protected final List<GuiHoverComponent> components = new ArrayList<>();
    /**
     * Currently hovered component
     */
    protected GuiHoverComponent hoverComp = null;
    /**
     * Is dragging a component?
     */
    protected boolean dragComp = false;
    /**
     * Used when dragging a component
     */
    protected int dragX = 0;
    protected int dragY = 0;

    public GuiPageWithPopups(GuiUnconfusion parent, T page) {
        super(parent, page);
        initHoverComponents(page);
    }

//    protected void drawHoverLines2(int scale) {
//        if(true) return;
//        GlStateManager.glLineWidth(scale);
//        GlStateManager.disableTexture2D();
//        GlStateManager.disableLighting();
//        GlStateManager.enableBlend();
//        Tessellator tessellator = Tessellator.getInstance();
//        BufferBuilder bufferbuilder = tessellator.getBuffer();
//        for (GuiHoverComponent hoverComponent : this.components) {
//
//            bufferbuilder.begin(3, DefaultVertexFormats.POSITION_COLOR);
//
//            Color color = hoverComponent.internal.getColor();
//            int r = color.getRed();
//            int g = color.getGreen();
//            int b = color.getBlue();
//            Vec2i lineEnd = hoverComponent.getLineEnd();
//            // Line
//            bufferbuilder.pos(hoverComponent.lineX, hoverComponent.lineY, 0).color(r, g, b, 0).endVertex();
//            bufferbuilder.pos(lineEnd.x, lineEnd.y, 0).color(r, g, b, 255).endVertex();
//
//            tessellator.draw();
//        }
//        GlStateManager.glLineWidth(1.0F);
//    }

    protected void drawHoverComponents(int mouseX, int mouseY) {
        GlStateManager.disableTexture2D();
        GlStateManager.disableLighting();
        for (GuiHoverComponent comp : this.components){
            comp.drawLine();
        }
        for (GuiHoverComponent comp : this.components) {
            // Try to draw the component
            try {
                comp.drawBackground();
                comp.draw(this.parent, mouseX, mouseY);
            } catch (Exception e) {
                LegacyPonder.getLogger().warn("Error drawing hoverComponent {}:", comp, e);
            }
        }

        GlStateManager.enableTexture2D();
    }

    protected void checkComponentHover(int mouseX, int mouseY) {
        // When dragging a component, skip hover detection
        if (dragComp) return;

        // Check if hovering any component, start from the last one to ensure hovering the topmost one
        this.hoverComp = null;
        // If hovered on a button, skip hovering check
        if (this.buttonList.stream().anyMatch(GuiButton::isMouseOver)) return;
        for (int i = components.size() - 1; i >= 0; i--) {
            GuiHoverComponent hoveredComponent1 = components.get(i);

            if (hoveredComponent1.getDrawable().getBoundingBox().expand(5).contains(mouseX, mouseY)) {
                this.hoverComp = hoveredComponent1;
                break;
            }
        }
        // If hovering a component, call its hover action
        if (this.hoverComp != null && this.hoverComp.getDrawable().isFocused(parent, mouseX, mouseY)) {
            this.hoverComp.getDrawable().onMouseHover(this.parent, mouseX, mouseY);
        }
    }

    protected void initHoverComponents(T page) {
        for (HoverComponent hoverComponent : page.getHoverComponents()) {
            this.components.add(hoverComponent.getGui());
        }
    }

    protected boolean mouseDownHover(int x, int y, int button) {
        if(hoverComp==null) return false;
        boolean clicked = hoverComp.getDrawable().onMouseClick(this.parent, x, y, button);
        if(clicked) return true;

        this.components.remove(hoverComp);
        this.components.add(hoverComp);
        dragComp =true;
        dragX = x - hoverComp.offX;
        dragY = y - hoverComp.offY;
        return true;
    }

    protected boolean mouseReleaseHover() {
        if(dragComp){
            dragComp =false;
            dragX =0;
            dragY =0;
            return true;
        }
        return false;
    }

    protected boolean keyTypedHover(char typedChar, int keyCode) {
        if(hoverComp !=null){
            hoverComp.getDrawable().onKeyTyped(this.parent, typedChar, keyCode);
            return true;
        }
        return false;
    }

    protected boolean mouseScrollHover(int mouseX, int mouseY, int delta) {
        if(hoverComp !=null){
            hoverComp.getDrawable().onMouseScroll(this.parent, mouseX, mouseY, delta);
            return true;
        }
        return false;
    }

    protected boolean dragHover(int x, int y) {
        if(dragComp && hoverComp !=null){
            hoverComp.offX = x -dragX;
            hoverComp.offY = y -dragY;
            return true;
        }
        return false;
    }
}
