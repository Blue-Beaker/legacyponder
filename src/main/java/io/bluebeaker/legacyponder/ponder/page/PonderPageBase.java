package io.bluebeaker.legacyponder.ponder.page;

import crafttweaker.annotations.ZenRegister;
import io.bluebeaker.legacyponder.crafttweaker.IDrawableSupplier;
import io.bluebeaker.legacyponder.ponder.drawable.DrawableTexture;
import io.bluebeaker.legacyponder.ponder.gui.GuiInfoPage;
import io.bluebeaker.legacyponder.ponder.gui.GuiPageDefault;
import io.bluebeaker.legacyponder.ponder.gui.GuiScreenPonder;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenGetter;
import stanhebben.zenscript.annotations.ZenMethod;
import stanhebben.zenscript.annotations.ZenSetter;

@ZenClass("mods.legacyponder.PonderPage")
@ZenRegister
public abstract class PonderPageBase {

    /** Build a new dummy page for testing.
     * @return The new dummy page */
    @ZenMethod
    public static PonderPageDummy dummyPage(){
        return new PonderPageDummy();
    }
    /** Build a new dummy page for testing, with text set.
     * @param text The text to be displayed
     * @return The new dummy page */
    @ZenMethod
    public static PonderPageDummy dummyPage(String text){
        return new PonderPageDummy(text);
    }

    /** Build a new page to display the structure from the specified path.
     * The structure should be in .minecraft/config/legacyponder/structures, subdirectories are allowed.
     * Use '/legacyponder' command to capture a structure into the specified path. Vanilla structure block format is also supported.
     * @param path The path. Split subdirectories with '/'.
     * @return The new page with structure */
    @ZenMethod
    public static PonderPageStructure fromStructure(String path){
        return new PonderPageStructure(path);
    }

    /** Build a new page to display the defined texture.
     * x,y,w,h is the section in the texture to be displayed
     * @param texture ResourceLocation for the texture.
     * @return The new page with texture */
    @ZenMethod
    public static PonderPageDrawable fromTexture(String texture, int x, int y, int w, int h){
        return fromDrawable((width, height) -> DrawableTexture.build(texture, x, y, w, h));
    }

    /** Build a new page to display the drawable. The drawable system is flexible and can draw textures, texts, or items.
     * @param drawableSupplier A function accepting width and height, returning the drawable to render. The function is called after selecting the page, and after resizing.
     * @return The new page with drawable */
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

    /** @param langKey Localization key for the description, or the description itself.
     * @return The page itself, for chaining */
    @ZenMethod
    @ZenSetter("description")
    public PonderPageBase setDescription(String langKey){
        this.description=langKey;
        return this;
    }

    /** Get the defined description of this page.
     * @return The defined description of this page */
    @ZenMethod
    @ZenGetter("description")
    public String getDescription() {
        return description;
    }
}
