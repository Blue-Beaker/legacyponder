package io.bluebeaker.legacyponder.render;

import net.minecraft.util.math.Vec3d;

public class StructureViewPos {
    public float yaw = 60f;
    public float pitch = 30f;
    public float scale = 1f;
    public Vec3d camera_center = new Vec3d(0,0,0);
    public Vec3d camera_offset = new Vec3d(0,0,0);
    public double zoom_power = 0;

    public void resetAngle() {
        this.yaw=60f;
        this.pitch=30f;
    }
    public void resetScaling() {
        this.scale=1f;
    }
    public void resetPos() {
        this.camera_center=new Vec3d(0,0,0);
    }
    public void resetAll() {
        resetPos();
        resetAngle();
        resetScaling();
        zoom_power=0;
    }


    public void addYaw(float delta){
        yaw+=delta;
        yaw=yaw%360;
    }
    public void addPitch(float delta){
        pitch+=delta;
        pitch=Math.max(Math.min(pitch,90),-90);
    }
    public void translate(double x, double y, double z){
        Vec3d vecX = new Vec3d(x,y,z).rotatePitch(pitch*0.017453292F).rotateYaw(-yaw* 0.017453292F);
        camera_center=camera_center.add(vecX);
    }

    public Vec3d getZoomedOffset(){
        return camera_offset.scale(Math.pow(2,zoom_power));
    }
    public void translateOffset(double x,double y,double z){
        camera_offset=camera_offset.add(x,y,z);
    }
    public void zoom (double zoom){
        zoom_power+=zoom;
    }
}
