package io.bluebeaker.legacyponder.ponder.page;

import crafttweaker.annotations.ZenRegister;
import io.bluebeaker.legacyponder.crafttweaker.IDrawableSupplier;
import io.bluebeaker.legacyponder.ponder.drawable.DrawableTexture;
import io.bluebeaker.legacyponder.ponder.gui.GuiInfoPage;
import io.bluebeaker.legacyponder.ponder.gui.GuiPageDefault;
import io.bluebeaker.legacyponder.ponder.gui.GuiScreenPonder;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.legacyponder.PonderPage")
@ZenRegister
public abstract class PonderPageBase {

    @ZenMethod
    public static PonderPageDummy dummyPage(){
        return new PonderPageDummy();
    }
    @ZenMethod
    public static PonderPageDummy dummyPage(String text){
        return new PonderPageDummy(text);
    }

    @ZenMethod
    public static PonderPageStructure fromStructure(String id){
        return new PonderPageStructure(id);
    }

    @ZenMethod
    public static PonderPageDrawable fromTexture(String texture, int x, int y, int w, int h){
        return fromDrawable((width, height) -> DrawableTexture.build(texture, x, y, w, h));
    }

    @ZenMethod
    public static PonderPageDrawable fromDrawable(IDrawableSupplier drawableSupplier){
        return new PonderPageDrawable(drawableSupplier);
    }

    private String description = "";

    public PonderPageBase() {
    }

    public GuiInfoPage<? extends PonderPageBase> getGuiPage(GuiScreenPonder parent){
        return new GuiPageDefault<>(parent,this);
    }

    /** Draws page
     * @param screen The ponder screen
     * @param mouseX Mouse X relative to page area
     * @param mouseY Mouse Y relative to page area
     * @param partialTicks Parent partialTicks
     */
    public void draw(GuiScreenPonder screen, int mouseX, int mouseY, float partialTicks){
    }

    @ZenMethod
    public PonderPageBase setDescription(String langKey){
        this.description=langKey;
        return this;
    }

    @ZenMethod
    public String getDescription() {
        return description;
    }
}
