package io.bluebeaker.legacyponder.render;

import io.bluebeaker.legacyponder.LegacyPonder;
import net.minecraft.util.math.Vec3d;

public class StructureViewPos {
    public float yaw = 60f;
    public float pitch = 30f;
    public float scale = 1f;
    public Vec3d camera_center = new Vec3d(0,0,0);

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
        Vec3d vecX = new Vec3d(x,y,0).rotatePitch(pitch*0.017453292F).rotateYaw(-yaw* 0.017453292F);
        camera_center=camera_center.add(vecX);

//        LegacyPonder.getLogger().info("View:{},{},{},{},{}",camera_center.x,camera_center.y,camera_center.z,yaw,pitch);
    }
}
