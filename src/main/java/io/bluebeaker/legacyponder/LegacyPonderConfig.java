package io.bluebeaker.legacyponder;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.Config.Comment;
import net.minecraftforge.common.config.Config.LangKey;
import net.minecraftforge.common.config.Config.Type;

@Config(modid = LegacyPonder.MODID,type = Type.INSTANCE,category = "general")
public class LegacyPonderConfig {
    @Comment("Example")
    @LangKey("config.legacyponder.example.name")
    public static boolean example = true;
}