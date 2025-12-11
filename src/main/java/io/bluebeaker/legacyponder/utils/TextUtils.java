package io.bluebeaker.legacyponder.utils;

import net.minecraft.client.resources.I18n;

public class TextUtils {
    public static String formatKey(String key){
        return formatText(I18n.format(key));
    }
    public static String formatText(String text){
        return text;
    }
}
