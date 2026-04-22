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
    static {
        prevPage = new KeyBinding("key.legacyponder.prev_page", KeyConflictContext.GUI, KeyModifier.NONE, Keyboard.KEY_A, categoryName);
        nextPage = new KeyBinding("key.legacyponder.next_page", KeyConflictContext.GUI, KeyModifier.NONE, Keyboard.KEY_D, categoryName);
    }

    public static void init() {
        ClientRegistry.registerKeyBinding(prevPage);
        ClientRegistry.registerKeyBinding(nextPage);
    }
}
