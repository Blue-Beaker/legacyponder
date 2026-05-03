package io.bluebeaker.legacyponder.manual.page;

import crafttweaker.annotations.ZenRegister;
import io.bluebeaker.legacyponder.crafttweaker.IDrawableSupplier;
import io.bluebeaker.legacyponder.manual.GuiUnconfusion;
import io.bluebeaker.legacyponder.manual.gui.GuiInfoPage;
import io.bluebeaker.legacyponder.manual.gui.GuiPageDefault;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenGetter;
import stanhebben.zenscript.annotations.ZenMethod;
import stanhebben.zenscript.annotations.ZenSetter;

@ZenClass("mods.legacyponder.Page")
@ZenRegister
public abstract class PageBase {

    /** Build a new dummy page for testing.
     * @return The new dummy page */
    @ZenMethod
    public static PageDummy dummyPage(){
        return new PageDummy();
    }
    /** Build a new dummy page for testing, with text set.
     * @param text The text to be displayed
     * @return The new dummy page */
    @ZenMethod
    public static PageDummy dummyPage(String text){
        return new PageDummy(text);
    }

    /** Build a new page, showing all registered entries as links
     * @return The new catalog page */
    @ZenMethod
    public static PageCatalog catalogPage(){
        return new PageCatalog();
    }

    /** Build a new page to display the structure from the specified path.
     * The structure should be in .minecraft/config/legacyponder/structures, subdirectories are allowed.
     * Use '/legacyponder' command to capture a structure into the specified path. Vanilla structure block format is also supported.
     * @param path The path. Split subdirectories with '/'.
     * @return The new page with structure */
    @ZenMethod
    public static PageStructure fromStructure(String path){
        return new PageStructure(path);
    }

    /** Build a new page to display the drawable. The drawable system is flexible and can draw textures, texts, or items.
     * @param drawableSupplier A function accepting width and height, returning the drawable to render. The function is called after selecting the page, and after resizing.
     * @return The new page with drawable */
    @ZenMethod
    public static PageDrawable fromDrawable(IDrawableSupplier drawableSupplier){
        return new PageDrawable(drawableSupplier);
    }

    private String description = "";

    public PageBase() {
    }

    public GuiInfoPage<? extends PageBase> getGuiPage(GuiUnconfusion parent){
        return new GuiPageDefault<>(parent,this);
    }

    /** Draws page
     * @param screen The manual screen
     * @param mouseX Mouse X relative to page area
     * @param mouseY Mouse Y relative to page area
     * @param partialTicks Parent partialTicks
     */
    public void draw(GuiUnconfusion screen, int mouseX, int mouseY, float partialTicks){
    }

    /** @param langKey Localization key for the description, or the description itself.
     * @return The page itself, for chaining */
    @ZenMethod
    public PageBase setDescription(String langKey){
        this.description=langKey;
        return this;
    }

    @ZenSetter("description")
    public void setDescriptionAlt(String langKey){
        setDescription(langKey);
    }

    /** Get the defined description of this page.
     * @return The defined description of this page */
    @ZenMethod
    @ZenGetter("description")
    public String getDescription() {
        return description;
    }
}
