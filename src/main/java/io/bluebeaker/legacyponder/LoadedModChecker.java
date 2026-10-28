package io.bluebeaker.legacyponder;

import net.minecraftforge.fml.common.Loader;

public enum LoadedModChecker {
    buildcraftcore("buildcraftcore"),
    crafttweaker("crafttweaker"),
    enderioconduits("enderioconduits"),
    forgemultipartcbe("forgemultipartcbe"),
    ftbquests("ftbquests"),
    ic2("ic2"),
    zenutils("zenutils");

    public final String modid;
    private boolean isLoaded = false;

    LoadedModChecker(String modid) {
        this.modid = modid;
    }

    public boolean isLoaded() {
        if (this.isLoaded)
            return true;
        this.isLoaded = Loader.isModLoaded(this.modid);
        return this.isLoaded;
    }

    public String getVersion() {
        return Loader.instance().getIndexedModList().get(this.modid).getVersion();
    }
}
