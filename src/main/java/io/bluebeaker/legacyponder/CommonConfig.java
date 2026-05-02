package io.bluebeaker.legacyponder;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.Config.Comment;
import net.minecraftforge.common.config.Config.LangKey;
import net.minecraftforge.common.config.Config.RequiresMcRestart;
import net.minecraftforge.common.config.Config.Type;

@Config.LangKey("config.legacyponder.common")
@Config(name = LegacyPonder.MODID+"/common",modid = LegacyPonder.MODID,type = Type.INSTANCE,category = "general")
public class CommonConfig {
    @Comment("Manage built-in mod compatibility for mods.")
    @LangKey("config.legacyponder.compat")
    public static Compat compat = new Compat();

    @Comment("Interval when logging errors for drawing. 0 for no interval, negative for no logs.")
    @LangKey("config.legacyponder.drawing_logs_interval")
    public static int drawing_logs_interval = 1000;

    @Comment("Print verbose logs for debugging.")
    @LangKey("config.legacyponder.log_debug")
    public static boolean log_debug = false;

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

    @Comment({"Special TileEntity classes to capture updateTag when saving structure, and load them when loading. Workaround for some tiles not rendering correctly.",
        "Comments are allowed after a '#'"})
    @LangKey("config.legacyponder.specialTileClasses")
    public static String[] specialTileClasses = {
            "appeng.tile.networking.TileCableBus#AE2",
            "buildcraft.transport.tile.TilePipeHolder#BCPipe"};
}