package io.bluebeaker.legacyponder.utils;

import io.bluebeaker.legacyponder.manual.Entry;
import net.minecraft.client.resources.I18n;

public class TextUtils {
    public static String formatKey(String key, Object... parameters){
        return I18n.format(key, parameters);
    }
    public static String[] formatLines(String text, Object... parameters){
        return splitLines(I18n.format(text, parameters));
    }
    public static String[] splitLines(String text1) {
        return text1.replace("\\n", "\n").split("\n");
    }

    public static String formatEntry(Entry entry) {
        return I18n.format(entry.title);
    }
    public static String formatEntry(Entry entry,int page) {
        return formatEntry(entry)+":"+page;
    }
}
