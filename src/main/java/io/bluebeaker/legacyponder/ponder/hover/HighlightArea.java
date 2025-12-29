package io.bluebeaker.legacyponder.ponder.hover;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.api.world.IBlockPos;
import net.minecraft.util.math.BlockPos;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

import java.awt.*;

@ZenClass("mods.legacyponder.HighlightArea")
@ZenRegister
public class HighlightArea {
    protected BlockPos pos1;
    protected BlockPos pos2;
    protected Color color = new Color(80,128,255);

    public HighlightArea(BlockPos pos1, BlockPos pos2){
        this.pos1=pos1;
        this.pos2=pos2;
    }

    @ZenMethod
    public static HighlightArea build(IBlockPos pos1, IBlockPos pos2){
        return new HighlightArea(CraftTweakerMC.getBlockPos(pos1),CraftTweakerMC.getBlockPos(pos2));
    }
    @ZenMethod
    public static HighlightArea build(int x1, int y1, int z1, int x2, int y2, int z2){
        return new HighlightArea(new BlockPos(x1,y1,z1),new BlockPos(x2,y2,z2));
    }

    @ZenMethod
    public HighlightArea setColor(int r, int g, int b){
        this.color = new Color(r,g,b);
        return this;
    }
    public Color getColor() {
        return color;
    }
}
