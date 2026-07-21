package io.bluebeaker.legacyponder.compat.buildcraft;

import buildcraft.core.client.BuildCraftLaserManager;
import buildcraft.core.marker.PathConnection;
import buildcraft.core.marker.VolumeConnection;
import buildcraft.lib.client.render.laser.LaserBoxRenderer;
import buildcraft.lib.client.render.laser.LaserData_BC8;
import buildcraft.lib.client.render.laser.LaserRenderer_BC8;
import buildcraft.lib.marker.MarkerConnection;
import buildcraft.lib.misc.VecUtil;
import buildcraft.lib.misc.data.Box;
import io.bluebeaker.legacyponder.utils.NBTTypes;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagLong;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class LaserConnectionsHandler {
    public static NBTTagCompound dumpConnections(Set<MarkerConnection<?>> connections){
        NBTTagCompound connectionsTag = new NBTTagCompound();
        NBTTagList volumeTag = new NBTTagList();
        NBTTagList pathTag = new NBTTagList();

        /* The resulting tag looks like:
        * {
        *   volume: [{"min":pos1,"max":pos2},...],
        *   path: [{"loop":true,"positions":[pos1,pos2,...]}]...
        * }
        * */
        for (MarkerConnection<?> connection : connections) {
            NBTTagCompound connectionTag = new NBTTagCompound();

            if(connection instanceof VolumeConnection){
                Box box = ((VolumeConnection) connection).getBox();
                connectionTag.setLong("min",box.min().toLong());
                connectionTag.setLong("max",box.max().toLong());
                volumeTag.appendTag(connectionTag);
            } else if (connection instanceof PathConnection) {
                NBTTagList positions = new NBTTagList();
                for (BlockPos pos : connection.getMarkerPositions()) {
                    positions.appendTag(new NBTTagLong(pos.toLong()));
                }
                connectionTag.setTag("positions", positions);
                pathTag.appendTag(connectionTag);
            }
        }

        connectionsTag.setTag("volume", volumeTag);
        connectionsTag.setTag("path", pathTag);
        return connectionsTag;
    }

    public static void renderConnections(NBTTagCompound tag){
        NBTTagList volumeTag = tag.getTagList("volume",NBTTypes.Compound);
        for (NBTBase entry : volumeTag) {
            if (!(entry instanceof NBTTagCompound)) continue;
            NBTTagCompound connection = (NBTTagCompound) entry;
            BlockPos min = BlockPos.fromLong(connection.getLong("min"));
            BlockPos max = BlockPos.fromLong(connection.getLong("max"));

            LaserBoxRenderer.renderLaserBoxStatic(new Box(min,max), BuildCraftLaserManager.MARKER_VOLUME_CONNECTED, true);
        }

        NBTTagList pathTag = tag.getTagList("path",NBTTypes.Compound);
        for (NBTBase entry : pathTag) {
            if (!(entry instanceof NBTTagCompound)) continue;
            List<BlockPos> positions = new ArrayList<>();
            NBTTagCompound connection = (NBTTagCompound) entry;
            NBTTagList posList = connection.getTagList("positions", NBTTypes.Long);
            for (NBTBase posTag : posList) {
                if (!(posTag instanceof NBTTagLong)) continue;
                long pos = ((NBTTagLong) posTag).getLong();
                positions.add(BlockPos.fromLong(pos));
            }

            for(int i = 1; i < positions.size(); i++){
                BlockPos lastPos = positions.get(i-1);
                BlockPos pos = positions.get(i);
                renderLaser(VecUtil.add(VEC_HALF, lastPos), VecUtil.add(VEC_HALF, pos));
            }
        }
    }

    public static final Vec3d VEC_HALF = new Vec3d(0.5, 0.5, 0.5);
    private static final double RENDER_SCALE = 1 / 16.05;
    private static void renderLaser(Vec3d from, Vec3d to) {
        Vec3d one = offset(from, to);
        Vec3d two = offset(to, from);
        LaserData_BC8 data = new LaserData_BC8(BuildCraftLaserManager.MARKER_PATH_CONNECTED, one, two, RENDER_SCALE);
        LaserRenderer_BC8.renderLaserStatic(data);
    }
    private static Vec3d offset(Vec3d from, Vec3d to) {
        Vec3d dir = to.subtract(from).normalize();
        return from.add(VecUtil.scale(dir, 0.125));
    }
}
