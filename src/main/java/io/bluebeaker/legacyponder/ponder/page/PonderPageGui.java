package io.bluebeaker.legacyponder.ponder.page;

import io.bluebeaker.legacyponder.ponder.gui.GuiScreenPonder;
import io.bluebeaker.legacyponder.utils.BoundingBox2D;
import io.bluebeaker.legacyponder.utils.RenderUtils;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;

public class PonderPageGui extends PonderPageBase{
    protected ResourceLocation texture;
    protected BoundingBox2D textureUV;

    public PonderPageGui(ResourceLocation texture, BoundingBox2D textureUV){
        this.texture=texture;
        this.textureUV=textureUV;
    }
    public PonderPageGui(String id, int x, int y, int w, int h){
        this(new ResourceLocation(id),new BoundingBox2D(x,y,w,h));
    }
    @Override
    public void draw(GuiScreenPonder screen, int mouseX, int mouseY, float partialTicks){
        BoundingBox2D pageBounds = screen.getPageBounds();

        RenderUtils.setViewPort(pageBounds);

        screen.mc.getTextureManager().bindTexture(texture);
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);

        int drawX=0;
        int drawY=0;
        screen.drawTexturedModalRect(drawX, drawY, textureUV.x, textureUV.y, textureUV.w, textureUV.h);

        RenderUtils.endViewPort();

    }
}
