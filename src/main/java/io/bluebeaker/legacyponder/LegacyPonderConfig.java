package io.bluebeaker.legacyponder;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.Config.Comment;
import net.minecraftforge.common.config.Config.LangKey;
import net.minecraftforge.common.config.Config.RequiresMcRestart;
import net.minecraftforge.common.config.Config.Type;

@Config(name = "legacyponder/legacyponder",modid = LegacyPonder.MODID,type = Type.INSTANCE,category = "general")
public class LegacyPonderConfig {
    @Comment("Manage built-in mod compatibility for mods.")
    @LangKey("config.legacyponder.compat.name")
    public static Compat compat = new Compat();

    public static class Compat{
        @RequiresMcRestart
        public boolean buildcraft = true;
        @RequiresMcRestart
        public boolean ic2 = true;
    }
}