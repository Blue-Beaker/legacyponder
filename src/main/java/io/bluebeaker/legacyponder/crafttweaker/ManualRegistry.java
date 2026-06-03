package io.bluebeaker.legacyponder.crafttweaker;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.IAction;
import crafttweaker.annotations.ZenRegister;
import io.bluebeaker.legacyponder.manual.Entry;
import net.minecraft.client.resources.I18n;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;
import youyihj.zenutils.api.reload.Reloadable;

import javax.annotation.Nullable;
import java.util.Collections;
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

    /** Get all entries in the registry, including hidden ones with IDs starting with "_" that are not shown in JEI or the manual index. This is modifiable and can be used to add or remove entries from the registry.
     * @return the all entries
     */
    public static Map<String, Entry> getEntries(){
        return PONDER_REGISTRY;
    }

    /** Get all entries that don't have an ID starting with "_", which are considered hidden and not shown in JEI or the manual index. This map is not modifiable.
     * @return the entries that should be visible
     */
    public static Map<String, Entry> getVisibleEntries(){
        Map<String, Entry> entries = new HashMap<>(PONDER_REGISTRY);
        for (String key : PONDER_REGISTRY.keySet()) {
            if(key.startsWith("_")){
                entries.remove(key);
            }
        }
        return Collections.unmodifiableMap(entries);
    }

    @Nullable
    public static Entry get(String id){
        return PONDER_REGISTRY.get(id);
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
