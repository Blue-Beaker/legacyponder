package io.bluebeaker.legacyponder.crafttweaker;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.IAction;
import crafttweaker.annotations.ZenRegister;
import io.bluebeaker.legacyponder.ponder.PonderEntry;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;
import youyihj.zenutils.api.reload.Reloadable;

import java.util.HashMap;
import java.util.Map;

@ZenClass("mods.legacyponder.PonderRegistry")
@ZenRegister
public class PonderRegistry {

    private static final Map<String,PonderEntry> PONDER_REGISTRY = new HashMap<>();

    @ZenMethod
    public static void add(IPonderEntry ponderEntry){
        CraftTweakerAPI.apply(new AddPonderAction(ponderEntry));
    }

    @Reloadable
    public static class AddPonderAction implements IAction {

        private final IPonderEntry ponderEntry;
        private final String id;

        public AddPonderAction(IPonderEntry ponderEntry) {
            this.ponderEntry = ponderEntry;
            this.id=ponderEntry.getID();
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
            return "Adding ponder entry "+ponderEntry.getID();
        }
    }
}
