package io.bluebeaker.legacyponder.manual;

import io.bluebeaker.legacyponder.crafttweaker.ManualRegistry;
import net.minecraft.client.resources.I18n;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class HistoryTracker {

    protected final List<HistoryEntry> history = new ArrayList<>();
    public int currentIndex = 0;

    public @Nullable HistoryEntry getPrevHistory(){
        if(currentIndex<=0) return null;
        return this.history.get(currentIndex-1);
    }

    public void record(String entryID, int page){
        if(this.currentIndex <this.history.size()-1){
            this.history.subList(this.currentIndex +1,this.history.size()).clear();
        }

        HistoryEntry entry = new HistoryEntry(entryID,page);
        this.history.add(entry);

        currentIndex =this.history.size()-1;
    }

    public void clear(){
        this.history.clear();
        this.currentIndex=0;
    }

    public @Nullable HistoryEntry gotoIndex(int index){
        if(index<0 || index>this.history.size()-1) return null;
        currentIndex=index;
        return this.history.get(currentIndex);
    }

    public List<String> getTooltipStrings(){
        List<String> tooltip = new ArrayList<>();
        for (int i = 0; i < history.size(); i++) {
            String titleAndPage = this.history.get(i).getTitleAndPage();
            if(i==currentIndex) titleAndPage = "§l"+titleAndPage+"§r";
            tooltip.add(titleAndPage);
        }
        return tooltip;
    }

    private static HistoryTracker INSTANCE;
    public static HistoryTracker get(){
        if(INSTANCE==null) INSTANCE = new HistoryTracker();
        return INSTANCE;
    }

    public void updateCurrentHistory(String entryID, int currentPageID) {
        if(currentIndex<0 || currentIndex>history.size()-1) return;
        HistoryEntry currentHistory = history.get(currentIndex);
        if(currentHistory==null) return;
        if(currentHistory.id.equals(entryID) && currentHistory.page==currentPageID) return;
        history.set(currentIndex,new HistoryEntry(entryID,currentPageID));
    }

    public static class HistoryEntry{
        final String id;
        final int page;
        public HistoryEntry(String id, int page) {
            this.id = id;
            this.page = page;
        }
        public String getTitle(){
            Entry entry = ManualRegistry.get(id);
            return entry!=null ? I18n.format(entry.title) : "INVALID ENTRY:"+id;
        }
        public String getTitleAndPage(){
            return getTitle()+" : "+(page);
        }

        @Override
        public boolean equals(Object obj) {
            if(obj instanceof HistoryEntry){
                HistoryEntry other = (HistoryEntry) obj;
                return this.id.equals(other.id) && this.page == other.page;
            }
            return super.equals(obj);
        }
    }
}
