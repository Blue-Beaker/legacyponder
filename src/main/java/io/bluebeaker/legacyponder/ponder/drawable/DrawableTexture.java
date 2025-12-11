package io.bluebeaker.legacyponder.ponder.drawable;

import crafttweaker.annotations.ZenRegister;
import io.bluebeaker.legacyponder.utils.BoundingBox2D;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.legacyponder.DrawableTexture")
@ZenRegister
public class DrawableTexture extends DrawableBase {
    private final ResourceLocation texture;
    private final BoundingBox2D textureUV;

    public DrawableTexture(ResourceLocation texture, BoundingBox2D textureUV){
        this.texture = texture;
        this.textureUV = textureUV;
    }

    @ZenMethod
    public static DrawableTexture build(String texture, int x, int y, int w, int h) {
        return new DrawableTexture(new ResourceLocation(texture), new BoundingBox2D(x,y,w,h));
    }

    @Override
    public void draw(GuiScreen screen, int mouseX, int mouseY) {
        screen.mc.getTextureManager().bindTexture(texture);
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        screen.drawTexturedModalRect(x, y, textureUV.x, textureUV.y, textureUV.w, textureUV.h);
    }

    @ZenMethod
    public int getWidth(){return textureUV.w;}
    @ZenMethod
    public int getHeight(){return textureUV.h;}
}
