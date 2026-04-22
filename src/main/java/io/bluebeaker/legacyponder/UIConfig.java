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

    @Config.RangeDouble(min = 0.01, max = 100)
    @Comment("Base scaling factor for structure view.")
    @LangKey("config.legacyponder.ui.structure_scaling")
    public static float structure_scaling = 1.0F;
    @Config.RangeDouble(min = -180, max = 180)
    @Comment("Default rotation yaw for structure view.")
    @LangKey("config.legacyponder.ui.defaultYaw")
    public static float default_yaw = 60F;
    @Config.RangeDouble(min = -90, max = 90)
    @Comment("Default rotation pitch for structure view.")
    @LangKey("config.legacyponder.ui.defaultPitch")
    public static float default_pitch = 15F;
}
