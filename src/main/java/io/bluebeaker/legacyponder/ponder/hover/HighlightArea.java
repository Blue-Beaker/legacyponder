package io.bluebeaker.legacyponder.ponder.hover;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.api.world.IBlockPos;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

import java.awt.*;

@ZenClass("mods.legacyponder.HighlightArea")
@ZenRegister
public class HighlightArea {

    protected Vec3d pos1;
    protected Vec3d pos2;
    protected Color color = new Color(80,128,255);

    public HighlightArea(BlockPos pos1, BlockPos pos2){
        this(new Vec3d(pos1),new Vec3d(pos2));
    }

    public HighlightArea(Vec3d pos1, Vec3d pos2){
        this.pos1=pos1;
        this.pos2=pos2;
    }

    @ZenMethod
    public static HighlightArea build(IBlockPos pos1){
        BlockPos pos = CraftTweakerMC.getBlockPos(pos1);
        return new HighlightArea(pos, pos.add(1,1,1));
    }

    @ZenMethod
    public static HighlightArea build(IBlockPos pos1, IBlockPos pos2){
        return new HighlightArea(CraftTweakerMC.getBlockPos(pos1),CraftTweakerMC.getBlockPos(pos2));
    }
    @ZenMethod
    public static HighlightArea build(double x1, double y1, double z1, double x2, double y2, double z2){
        return new HighlightArea(new Vec3d(x1,y1,z1),new Vec3d(x2,y2,z2));
    }

    @ZenMethod
    public HighlightArea setColor(int r, int g, int b){
        this.color = new Color(r,g,b);
        return this;
    }
    public Color getColor() {
        return color;
    }
    public Vec3d getPos1() {
        return pos1;
    }

    public Vec3d getPos2() {
        return pos2;
    }
}
