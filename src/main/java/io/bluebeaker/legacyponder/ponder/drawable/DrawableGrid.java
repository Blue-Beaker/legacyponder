package io.bluebeaker.legacyponder.ponder.drawable;

import crafttweaker.annotations.ZenRegister;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenGetter;
import stanhebben.zenscript.annotations.ZenSetter;

@ZenClass("mods.legacyponder.DrawableGrid")
@ZenRegister
public class DrawableGrid extends DrawableGroup{
    protected int columns;
    protected int colWidth;
    protected int lineHeight;

    public DrawableGrid(int columns, int colWidth, int lineHeight){
        this.columns = columns;
        this.colWidth = colWidth;
        this.lineHeight = lineHeight;
    }

    @Override
    public void updateSizes() {
        int column = 0;
        int line = 0;
        for (DrawableBase child : children) {
            child.setPosition(column*colWidth,line*lineHeight);
            column+=1;
            if(column>=columns){
                column=0;
                line+=1;
            }
        }
        x1=0;
        y1=0;
        x2=columns*colWidth;
        y2=lineHeight*(column==0?line:line+1);
        w=x2-x1;
        h=y2-y1;
        if(this.parent instanceof DrawableContainer){
            ((DrawableContainer) this.parent).updateSizes();
        }
    }

    @ZenGetter("columns")
    public int getColumns() {
        return columns;
    }
    @ZenSetter("columns")
    public void setColumns(int columns) {
        this.columns = columns;
        updateSizes();
    }
    @ZenGetter("colWidth")
    public int getColWidth() {
        return colWidth;
    }
    @ZenSetter("colWidth")
    public void setColWidth(int colWidth) {
        this.colWidth = colWidth;
        updateSizes();
    }
    @ZenGetter("lineHeight")
    public int getLineHeight() {
        return lineHeight;
    }
    @ZenSetter("lineHeight")
    public void setLineHeight(int lineHeight) {
        this.lineHeight = lineHeight;
        updateSizes();
    }
}
