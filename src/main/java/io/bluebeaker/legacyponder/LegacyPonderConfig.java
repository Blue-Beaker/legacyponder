package io.bluebeaker.legacyponder;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.Config.Comment;
import net.minecraftforge.common.config.Config.LangKey;
import net.minecraftforge.common.config.Config.RequiresMcRestart;
import net.minecraftforge.common.config.Config.Type;

@Config.LangKey("config.legacyponder.general")
@Config(name = "legacyponder/legacyponder",modid = LegacyPonder.MODID,type = Type.INSTANCE,category = "general")
public class LegacyPonderConfig {
    @Comment("Manage built-in mod compatibility for mods.")
    @LangKey("config.legacyponder.compat")
    public static Compat compat = new Compat();

    public static class Compat{
        @RequiresMcRestart
        public boolean buildcraft = true;
        @RequiresMcRestart
        public boolean ic2 = true;
        @RequiresMcRestart
        public boolean forgemultipart = true;
        @RequiresMcRestart
        public boolean enderio = true;
    }

    @Comment("Enable demo entries. Demo entries explain what can be made with this mod.")
    @LangKey("config.legacyponder.demo")
    public static boolean demo = true;
}