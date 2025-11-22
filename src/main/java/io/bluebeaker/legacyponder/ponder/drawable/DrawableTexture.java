package io.bluebeaker.legacyponder.ponder.drawable;

import io.bluebeaker.legacyponder.utils.BoundingBox2D;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;

public class DrawableTexture extends DrawableBase {
    private final ResourceLocation texture;
    private final BoundingBox2D textureUV;

    public DrawableTexture(ResourceLocation texture, BoundingBox2D textureUV){
        this.texture = texture;
        this.textureUV = textureUV;
    }
    @Override
    public void draw(GuiScreen screen, int mouseX, int mouseY) {
        screen.mc.getTextureManager().bindTexture(texture);
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        screen.drawTexturedModalRect(x, y, textureUV.x, textureUV.y, textureUV.w, textureUV.h);
    }
}
