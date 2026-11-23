package io.bluebeaker.legacyponder.crafttweaker.drawable;

import crafttweaker.api.text.ITextComponent;
import io.bluebeaker.legacyponder.manual.drawable.DrawableText;
import io.bluebeaker.legacyponder.utils.ModZenRegister;
import stanhebben.zenscript.annotations.ZenExpansion;
import stanhebben.zenscript.annotations.ZenMethodStatic;

@ModZenRegister
@ZenExpansion("mods.legacyponder.DrawableText")
public class DrawableTextExt {
    @ZenMethodStatic
    public static DrawableText build(ITextComponent text, int color) {
        return new DrawableText(text.getFormattedText(),color);
    }
}
