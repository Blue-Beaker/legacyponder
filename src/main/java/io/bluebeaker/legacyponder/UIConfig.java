package io.bluebeaker.legacyponder;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.Config.Comment;
import net.minecraftforge.common.config.Config.LangKey;

@LangKey("config.legacyponder.ui")
@Config(name = "legacyponder/ui",modid = LegacyPonder.MODID,type = Config.Type.INSTANCE,category = "ui")
public class UIConfig {
    @Config.RangeDouble(min = -10, max = 10)
    @Comment("Mouse wheel sensivity for zooming in structure page.")
    @LangKey("config.legacyponder.ui.wheel_sensivity")
    public static double wheel_sensivity = 0.2;
    @Config.RangeDouble(min = -10, max = 10)
    @Comment("Mouse movement sensivity for dragging in structure page.")
    @LangKey("config.legacyponder.ui.cursor_sensivity")
    public static double cursor_sensivity = 1.0;

    @Comment("Enable zoom buttons in structure page.")
    @LangKey("config.legacyponder.ui.zoom_buttons")
    public static boolean zoom_buttons = true;
    @Comment("Enable zoom slider in structure page.")
    @LangKey("config.legacyponder.ui.zoom_slider")
    public static boolean zoom_slider = true;
}
