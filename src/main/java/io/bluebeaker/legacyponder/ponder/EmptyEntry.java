package io.bluebeaker.legacyponder.ponder;

public class EmptyEntry extends Entry {
    protected EmptyEntry(String title, String summary) {
        super(title, summary);
    }
    protected EmptyEntry(){
        super("INVALID ID","");
    }
    public static final EmptyEntry INSTANCE = new EmptyEntry();
}
