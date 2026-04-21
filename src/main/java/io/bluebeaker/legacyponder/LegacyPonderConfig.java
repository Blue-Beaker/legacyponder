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
        @RequiresMcRestart
        public boolean forgemultipart = true;
        @RequiresMcRestart
        public boolean enderio = true;
    }

    @Comment("Manage UI and control settings.")
    @LangKey("config.legacyponder.ui.name")
    public static UI ui = new UI();
    public static class UI {
        @Comment("Mouse wheel sensivity for zooming in structure page.")
        @LangKey("config.legacyponder.ui.wheel_sensivity.name")
        public double wheel_sensivity = 0.2;
        @Comment("Mouse movement sensivity for dragging in structure page.")
        @LangKey("config.legacyponder.ui.cursor_sensivity.name")
        public double cursor_sensivity = 1.0;
    }

    @Comment("Enable demo entries. Demo entries explain what can be made with this mod.")
    @LangKey("config.legacyponder.demo.name")
    public static boolean demo = true;
}