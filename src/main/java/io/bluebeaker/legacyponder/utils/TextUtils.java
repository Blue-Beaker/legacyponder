package io.bluebeaker.legacyponder.utils;

import net.minecraft.client.resources.I18n;

public class TextUtils {
    public static String formatKey(String key){
        return I18n.format(key);
    }
    public static String[] formatLines(String text){
        return I18n.format(text).replace("\\n","\n").split("\n");
    }
}
