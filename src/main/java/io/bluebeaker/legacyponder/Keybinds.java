package io.bluebeaker.legacyponder;

import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.client.settings.KeyConflictContext;
import net.minecraftforge.client.settings.KeyModifier;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import org.lwjgl.input.Keyboard;

public class Keybinds {
    public static final String categoryName = LegacyPonder.NAME;
    public static final KeyBinding prevPage;
    public static final KeyBinding nextPage;
    public static final KeyBinding histBack;
    public static final KeyBinding histForward;
    public static final KeyBinding openManual;
    static {
        prevPage = new KeyBinding("key.legacyponder.prev_page", KeyConflictContext.GUI, KeyModifier.NONE, Keyboard.KEY_A, categoryName);
        nextPage = new KeyBinding("key.legacyponder.next_page", KeyConflictContext.GUI, KeyModifier.NONE, Keyboard.KEY_D, categoryName);
        histBack = new KeyBinding("key.legacyponder.history_back", KeyConflictContext.GUI, KeyModifier.NONE, -97, categoryName);
        histForward = new KeyBinding("key.legacyponder.history_forward", KeyConflictContext.GUI, KeyModifier.NONE, -96, categoryName);
        openManual = new KeyBinding("key.legacyponder.open_manual", KeyConflictContext.IN_GAME, KeyModifier.NONE, Keyboard.KEY_NONE, categoryName);
    }

    public static void init() {
        ClientRegistry.registerKeyBinding(prevPage);
        ClientRegistry.registerKeyBinding(nextPage);
        ClientRegistry.registerKeyBinding(histBack);
        ClientRegistry.registerKeyBinding(histForward);
        ClientRegistry.registerKeyBinding(openManual);
    }
}
