package io.bluebeaker.legacyponder.ponder.page;

import io.bluebeaker.legacyponder.ponder.PonderEntry;

public class EmptyPonder extends PonderEntry {
    protected EmptyPonder(String title, String summary) {
        super(title, summary);
    }
    protected EmptyPonder(){
        super("INVALID ID","");
    }
    public static final EmptyPonder INSTANCE = new EmptyPonder();
}
