package io.bluebeaker.legacyponder.api;

import io.bluebeaker.legacyponder.manual.Entry;
import net.minecraft.client.resources.I18n;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ManualRegistry {
    public static final Map<String, Entry> PONDER_REGISTRY = new HashMap<>();

    public static String getEntryTitle(String id){
        Entry entry = getEntries().get(id);
        return entry==null ? "" : I18n.format(entry.title);
    }

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
}
