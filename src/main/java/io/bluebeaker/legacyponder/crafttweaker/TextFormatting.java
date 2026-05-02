package io.bluebeaker.legacyponder.crafttweaker;

import net.minecraft.util.text.TextComponentKeybind;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("io.bluebeaker.legacyponder.TextFormatting")
public class TextFormatting {
    @ZenMethod
    public static String formatKeybind(String key){
        return new TextComponentKeybind(key).getFormattedText();
    }

    @ZenMethod
    public static String getEntryTitle(String id){
        return ManualRegistry.getEntryTitle(id);
    }

    @ZenMethod
    public static String getEntrySummary(String id){
        return ManualRegistry.getEntrySummary(id);
    }
}
