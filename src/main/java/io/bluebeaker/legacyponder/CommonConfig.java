package io.bluebeaker.legacyponder;

import io.bluebeaker.legacyponder.demo.DemoEntries;
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

    public static int debug_verbosity = 1;

    @Comment("Set a custom homepage for your modpack. Defaults to the demo entry.")
    @LangKey("config.legacyponder.homepage_id")
    public static String homepage_id = DemoEntries.INTERNAL_DEMO_ID;

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

    @Comment("Enable FTB Quests Integration. Adds a task to open an entry from this mod.")
    @LangKey("config.legacyponder.ftbquests_integration")
    @RequiresMcRestart
    public static boolean ftbquests_integration = true;

    @Comment("Moves client-side player position temporarily when rendering structure. Workaround for renderers with view-based culling")
    @LangKey("config.legacyponder.renderWorkaround")
    public static PlayerPosWorkaround renderWorkaround = PlayerPosWorkaround.DISABLED;

    public static enum PlayerPosWorkaround{
        DISABLED,
        ENABLED,
        SINGLEPLAYER_ONLY
    }

    @Comment({"Special TileEntity classes to capture updateTag when saving structure, and load them when loading. Workaround for some tiles not rendering correctly.",
        "Comments are allowed after a '#'"})
    @LangKey("config.legacyponder.specialTileClasses")
    public static String[] specialTileClasses = {
            "appeng.tile.networking.TileCableBus#AE2",
            "buildcraft.transport.tile.TilePipeHolder#BCPipe"};

    @Comment({"Dimension ID for the dummy world. Shouldn't conflict with existing dimension IDs to prevent side effects on some mods."})
    @LangKey("config.legacyponder.dummyWorldDimension")
    public static int dummyWorldDimension = -25555;
}