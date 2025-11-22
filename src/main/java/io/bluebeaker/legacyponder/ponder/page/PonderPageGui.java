package io.bluebeaker.legacyponder.ponder.page;

import io.bluebeaker.legacyponder.ponder.drawable.DrawableGroup;
import io.bluebeaker.legacyponder.ponder.drawable.DrawableTexture;
import io.bluebeaker.legacyponder.ponder.gui.GuiScreenPonder;
import io.bluebeaker.legacyponder.utils.BoundingBox2D;
import io.bluebeaker.legacyponder.utils.RenderUtils;
import net.minecraft.util.ResourceLocation;

public class PonderPageGui extends PonderPageBase{
    protected DrawableGroup drawable;

    public PonderPageGui(ResourceLocation texture, BoundingBox2D textureUV){
        DrawableTexture drawableTexture = new DrawableTexture(texture, textureUV);
        drawable = new DrawableGroup();
        drawable.addChild(drawableTexture);
//        drawable.addChild(new DrawableItem(new ItemStack(Items.DIAMOND_PICKAXE,1,10)).setPosition(30,17));
//        drawable.addChild(new DrawableItem(new ItemStack(Items.DIAMOND_PICKAXE,1,500)).setPosition(48,17));
    }
    public PonderPageGui(String id, int x, int y, int w, int h){
        this(new ResourceLocation(id),new BoundingBox2D(x,y,w,h));
    }
    @Override
    public void draw(GuiScreenPonder screen, int mouseX, int mouseY, float partialTicks){
        BoundingBox2D pageBounds = screen.getPageBounds();

        RenderUtils.setViewPort(pageBounds);

        drawable.draw(screen,mouseX,mouseY);

        RenderUtils.endViewPort();

    }
}
