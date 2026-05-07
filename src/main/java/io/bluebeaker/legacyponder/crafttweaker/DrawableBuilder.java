package io.bluebeaker.legacyponder.crafttweaker;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.liquid.ILiquidStack;
import crafttweaker.api.text.ITextComponent;
import io.bluebeaker.legacyponder.manual.drawable.*;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

/**
 * Conventional builder class for creating drawable elements
 */
@ZenClass("mods.legacyponder.DrawableBuilder")
@ZenRegister
public class DrawableBuilder {

    /** Builds a box drawable with the specified coordinates and color.
     * @param x1 left X coordinate
     * @param y1 top Y coordinate
     * @param x2 right X coordinate
     * @param y2 bottom Y coordinate
     * @param color color of the box in ARGB format (e.g. 0xFFFF0000 for opaque red)
     * @return The new DrawableBox instance
     */
    @ZenMethod
    public static DrawableBox box(int x1, int y1, int x2, int y2, int color){
        return DrawableBox.build(x1, y1, x2, y2, color);
    }

    /** Create a line without any point included initially.
     * @param color Color of the line, in ARGB format (e.g. 0xFFFF0000 for red)
     * @return The created DrawableLine instance.
     */
    @ZenMethod
    public static DrawableLine line(int color){
        return DrawableLine.build(color);
    }

    /** Create a line with two points included initially.
     * @param x1 X coordinate of the first point
     * @param y1 Y coordinate of the first point
     * @param x2 X coordinate of the second point
     * @param y2 Y coordinate of the second point
     * @param color Color of the line, in ARGB format (e.g. 0xFFFF0000 for red)
     * @return The created DrawableLine instance.
     */
    @ZenMethod
    public static DrawableLine line(int x1, int y1, int x2, int y2, int color){
        return DrawableLine.build(x1, y1, x2, y2, color);
    }

    /** Builds a drawable item from the given IItemStack, array of IItemStack, or IIngredient. If the item parameter contains multiple items, all of them will be included in the drawable and displayed in a cycle.
     * @param item the IItemStack, array of IItemStack or IIngredient to be displayed as a drawable item
     * @return A DrawableItem instance representing the given item(s)
     */
    @ZenMethod
    public static DrawableItem item(IItemStack item){
        return DrawableItem.build(item);
    }
    @ZenMethod
    public static DrawableItem item(IIngredient item){
        return DrawableItem.build(item);
    }
    @ZenMethod
    public static DrawableItem item(IItemStack[] item){
        return DrawableItem.build(item);
    }
    @ZenMethod
    public static DrawableFluid fluid(ILiquidStack fluid){
        return DrawableFluid.build(fluid);
    }

    /** Build a DrawableText with the specified text and color. The text will be formatted as a translation key, so it can be used for localization. If the text is not a valid translation key, it will be displayed as is.
     * @param text Translation key of the text to be displayed. If the text is not a valid translation key, it will be displayed as is.
     * @param color Color of the text, in ARGB format (e.g. 0xFFFF0000 for red)
     * @return The new DrawableText instance
     */
    @ZenMethod
    public static DrawableText formattedText(String text, int color, String... parameters){
        return DrawableText.buildFormatted(text, color, parameters);
    }
    @ZenMethod
    public static DrawableText formattedText(String text, int color){
        return DrawableText.buildFormatted(text, color);
    }
    /** Build a DrawableText with the specified text/ITextComponent.
     * @param text Text to be displayed.
     * @param color Color of the text, in ARGB format (e.g. 0xFFFF0000 for red)
     * @return The new DrawableText instance
     */
    @ZenMethod
    public static DrawableText text(String text, int color){
        return DrawableText.build(text, color);
    }
    @ZenMethod
    public static DrawableText text(ITextComponent text, int color){
        return DrawableText.build(text, color);
    }

    /** Build a DrawableTexture with the specified texture and UV coordinates. The texture will be drawn at the specified coordinates, and the UV coordinates will determine which part of the texture is drawn. The size of the drawable will be determined by the width and height of the UV coordinates, and cannot be changed.
     * @param texture ResourceLocation for the texture.
     * @param x section left X
     * @param y section top Y
     * @param w section width
     * @param h section height
     * @return The new DrawableTexture instance
     */
    @ZenMethod
    public static DrawableTexture texture(String texture, int x, int y, int w, int h){
        return DrawableTexture.build(texture,x,y,w,h);
    }

    /** Build an empty DrawableGroup, which can be used to group multiple drawables together. The drawables in the group will be drawn in the order they were added.
     * @return The new DrawableGroup instance
     */
    @ZenMethod
    public static DrawableScroll scroll(DrawableBase internal, int width, int height){
        return DrawableScroll.build(internal, width, height);
    }

    /** Build an empty DrawableGroup, which can be used to group multiple drawables together. The drawables in the group will be drawn in the order they were added.
     * @return The new DrawableGroup instance
     */
    @ZenMethod
    public static DrawableGroup group(){
        return new DrawableGroup();
    }

    /** Build an empty DrawableGrid, which is similar to DrawableGroup, but with its children arranged automatically.
     * @return The new DrawableGrid instance
     */
    @ZenMethod
    public static DrawableGrid grid(int columns, int colWidth, int lineHeight){
        return new DrawableGrid(columns, colWidth, lineHeight);
    }
    @ZenMethod
    public static DrawableGrid grid(int colWidth, int lineHeight){
        return new DrawableGrid(0, colWidth, lineHeight);
    }

    /** A helper to help positioning HoverComponent for PageDrawable.
     * Doesn't do anything on its own, but you can attach a HoverComponent to its position, by setting the same ID on both this and the HoverComponent. Negative ID doesn't work.
     * @param id The ID for HoverComponent to bind to
     * @return The hover position component
     */
    @ZenMethod
    public static DrawableHoverPos hoverPos(int id){
        return new DrawableHoverPos(id);
    }
}
