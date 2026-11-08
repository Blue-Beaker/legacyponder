package io.bluebeaker.legacyponder.crafttweaker;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.IAction;
import io.bluebeaker.legacyponder.api.ManualRegistry;
import io.bluebeaker.legacyponder.utils.ModZenRegister;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;
import youyihj.zenutils.api.reload.Reloadable;

@ModZenRegister
@ZenClass("mods.legacyponder.ManualRegistry")
public class IManualRegistry {

    @ZenMethod
    public static String getEntryTitle(String id){
        return ManualRegistry.getEntryTitle(id);
    }
    @ZenMethod
    public static String getEntrySummary(String id){
        return ManualRegistry.getEntrySummary(id);
    }

    /** Adds a manual entry to the registry.
     * @param id ID of the manual entry
     * @param ponderEntry The manual entry to be added
     */
    @ZenMethod
    public static void add(String id, IEntry ponderEntry){
        CraftTweakerAPI.apply(new AddPonderAction(id,ponderEntry));
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
            ManualRegistry.PONDER_REGISTRY.put(id,ponderEntry.internal);
        }

        public void undo(){
            ManualRegistry.PONDER_REGISTRY.remove(id);
        }

        @Override
        public String describe() {
            return "Adding manual entry "+id;
        }
    }
}
