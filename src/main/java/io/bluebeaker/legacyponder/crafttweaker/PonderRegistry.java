package io.bluebeaker.legacyponder.crafttweaker;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.IAction;
import crafttweaker.annotations.ZenRegister;
import io.bluebeaker.legacyponder.ponder.Entry;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;
import youyihj.zenutils.api.reload.Reloadable;

import java.util.HashMap;
import java.util.Map;

@ZenClass("mods.legacyponder.PonderRegistry")
@ZenRegister
public class PonderRegistry {

    private static final Map<String, Entry> PONDER_REGISTRY = new HashMap<>();

    @ZenMethod
    public static void add(String id, IPonderEntry ponderEntry){
        CraftTweakerAPI.apply(new AddPonderAction(id,ponderEntry));
    }

    public static Map<String, Entry> getEntries(){
        return PONDER_REGISTRY;
    }

    @Reloadable
    public static class AddPonderAction implements IAction {

        private final IPonderEntry ponderEntry;
        private final String id;

        public AddPonderAction(String id, IPonderEntry ponderEntry) {
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
            return "Adding ponder entry "+id;
        }
    }
}
