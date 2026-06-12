package io.bluebeaker.legacyponder;

import crafttweaker.CraftTweakerAPI;
import net.minecraftforge.fml.common.discovery.ASMDataTable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashSet;
import java.util.Set;

/**
 * Scans for {@link ModZenRegister} annotated classes and registers them with CraftTweaker.
 * This replaces CraftTweaker's own @ZenRegister scanning to avoid classloading issues
 * on dedicated servers where client-only classes may not be available.
 *
 * To register a new ZenScript class, simply annotate it with {@link ModZenRegister}
 * and {@link stanhebben.zenscript.annotations.ZenClass}.
 * No other registration steps are needed.
 */
public class ZenClassScanner {
    private static final Logger LOGGER = LogManager.getLogger();

    private static Set<String> modZenClasses = null;

    /**
     * Initializes the scanner with ASM data from the mod's pre-initialization event.
     * Call this from {@link LegacyPonder#preInit(net.minecraftforge.fml.common.event.FMLPreInitializationEvent)}.
     */
    public static void init(ASMDataTable asmDataTable) {
        Set<ASMDataTable.ASMData> all = asmDataTable.getAll(ModZenRegister.class.getName());
        modZenClasses = new HashSet<>();
        for (ASMDataTable.ASMData data : all) {
            modZenClasses.add(data.getClassName());
        }
        LOGGER.info("Found {} @ModZenRegister annotated classes", modZenClasses.size());
    }

    /**
     * Registers all {@link ModZenRegister}-annotated classes with CraftTweaker.
     * Called during {@code preInit} on the client side only.
     */
    public static void registerAll() {
        if (modZenClasses == null) {
            LOGGER.warn("@ModZenRegister scanner not initialized, skipping registration");
            return;
        }
        for (String className : modZenClasses) {
            try {
                Class<?> clazz = Class.forName(className);
                CraftTweakerAPI.registerClass(clazz);
            } catch (ClassNotFoundException e) {
                LOGGER.error("Failed to load @ModZenRegister class: {}", className, e);
            }
        }
    }
}
