package io.bluebeaker.legacyponder.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.util.math.Vec3d;

public class PlayerPosTracker {
    private static Vec3d lastPos;
    private static Vec3d lastPrevPos;
    private static Vec3d lastMotion;
    private static float lastYaw;
    private static float lastPitch;

    public static void storePlayerPos(){
        EntityPlayerSP player = Minecraft.getMinecraft().player;
        lastPos = player.getPositionVector();
        lastPrevPos = new Vec3d(player.prevPosX, player.prevPosY, player.prevPosZ);
        lastMotion = new Vec3d(player.motionX, player.motionY, player.motionZ);
        lastYaw = player.cameraYaw;
        lastPitch = player.cameraPitch;
    }
    public static void setPlayerPos(Vec3d pos, float yaw, float pitch){
        EntityPlayerSP player = Minecraft.getMinecraft().player;
        player.posX=pos.x;
        player.posY=pos.y;
        player.posZ=pos.z;
        player.cameraYaw=yaw;
        player.cameraPitch=pitch;

        player.prevPosX=pos.x;
        player.prevPosY=pos.y;
        player.prevPosZ=pos.z;
    }

    public static void restorePlayerPos(){
        setPlayerPos(lastPos,lastYaw,lastPitch);
        EntityPlayerSP player = Minecraft.getMinecraft().player;
        player.motionX=lastMotion.x;
        player.motionY=lastMotion.y;
        player.motionZ=lastMotion.z;
        player.prevPosX=lastPrevPos.x;
        player.prevPosY=lastPrevPos.y;
        player.prevPosZ=lastPrevPos.z;
    }

    public static Vec3d getLastPos() {
        return lastPos;
    }

    public static Vec3d getLastMotion() {
        return lastMotion;
    }

    public static float getLastPitch() {
        return lastPitch;
    }

    public static float getLastYaw() {
        return lastYaw;
    }
}
