package io.bluebeaker.legacyponder;

import io.bluebeaker.legacyponder.demo.DemoEntries;
import io.bluebeaker.legacyponder.structure.StructureLoader;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import youyihj.zenutils.api.reload.ScriptReloadEvent;

public class ZenUtilsCompat {
    @SubscribeEvent
    public void onReload(ScriptReloadEvent event) {
        StructureLoader.loadTemplates();
        DemoEntries.addDemoIfNeeded();
    }
}
