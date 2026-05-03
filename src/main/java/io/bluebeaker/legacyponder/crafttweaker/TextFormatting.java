package io.bluebeaker.legacyponder.crafttweaker;

import io.bluebeaker.legacyponder.utils.TextUtils;
import net.minecraft.util.text.TextComponentKeybind;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.legacyponder.TextFormatting")
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

    @ZenMethod
    public static String formatKey(String text, String... params){
        return TextUtils.formatKey(text, (Object[]) params);
    }
    @ZenMethod
    public static String[] formatLines(String text, String... params){
        return TextUtils.formatLines(text, (Object[]) params);
    }
}
