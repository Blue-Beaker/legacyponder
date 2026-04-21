package io.bluebeaker.legacyponder;

import net.minecraftforge.common.config.Config;

@Config.LangKey("config.legacyponder.ui")
@Config(name = "legacyponder/ui",modid = LegacyPonder.MODID,type = Config.Type.INSTANCE,category = "ui")
public class UIConfig {
    @Config.RangeDouble(min = -10, max = 10)
    @Config.Comment("Mouse wheel sensivity for zooming in structure page.")
    @Config.LangKey("config.legacyponder.ui.wheel_sensivity")
    public static double wheel_sensivity = 0.2;
    @Config.RangeDouble(min = -10, max = 10)
    @Config.Comment("Mouse movement sensivity for dragging in structure page.")
    @Config.LangKey("config.legacyponder.ui.cursor_sensivity")
    public static double cursor_sensivity = 1.0;
}
