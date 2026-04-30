package io.bluebeaker.legacyponder.crafttweaker;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.IAction;
import crafttweaker.annotations.ZenRegister;
import io.bluebeaker.legacyponder.manual.Entry;
import net.minecraft.client.resources.I18n;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;
import youyihj.zenutils.api.reload.Reloadable;

import java.util.HashMap;
import java.util.Map;

@ZenClass("mods.legacyponder.ManualRegistry")
@ZenRegister
public class ManualRegistry {

    private static final Map<String, Entry> PONDER_REGISTRY = new HashMap<>();

    /** Adds a manual entry to the registry.
     * @param id ID of the manual entry
     * @param ponderEntry The manual entry to be added
     */
    @ZenMethod
    public static void add(String id, IEntry ponderEntry){
        CraftTweakerAPI.apply(new AddPonderAction(id,ponderEntry));
    }

    @ZenMethod
    public static String getEntryTitle(String id){
        Entry entry = getEntries().get(id);
        return entry==null ? "" : I18n.format(entry.title);
    }
    @ZenMethod
    public static String getEntrySummary(String id){
        Entry entry = getEntries().get(id);
        return entry==null ? "" : I18n.format(entry.summary);
    }

    public static Map<String, Entry> getEntries(){
        return PONDER_REGISTRY;
    }

    @Reloadable
    public static class AddPonderAction implements IAction {

        private final IEntry ponderEntry;
        private final String id;

        public AddPonderAction(String id, IEntry ponderEntry) {
            this.ponderEntry = ponderEntry;
            this.id=id;
        }

        @Override
        public void apply() {
            PONDER_REGISTRY.put(id,ponderEntry.internal);
        }

        public void undo(){
            PONDER_REGISTRY.remove(id);
        }

        @Override
        public String describe() {
            return "Adding manual entry "+id;
        }
    }
}
