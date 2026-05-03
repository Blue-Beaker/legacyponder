package io.bluebeaker.legacyponder.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.util.math.Vec3d;

public class PlayerPosTracker {
    private static Vec3d lastPos;
    private static float lastYaw;
    private static float lastPitch;

    public static void storePlayerPos(){
        EntityPlayerSP player = Minecraft.getMinecraft().player;
        lastPos = player.getPositionVector();
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
    }
    public static void restorePlayerPos(){
        setPlayerPos(lastPos,lastYaw,lastPitch);
    }
}
