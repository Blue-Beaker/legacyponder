package io.bluebeaker.legacyponder.compat;

import buildcraft.lib.marker.MarkerConnection;
import buildcraft.lib.tile.TileBC_Neptune;
import buildcraft.lib.tile.TileMarker;
import io.bluebeaker.legacyponder.LegacyPonder;
import io.bluebeaker.legacyponder.compat.buildcraft.LaserConnectionsHandler;
import io.bluebeaker.legacyponder.render.StructureRenderManager;
import io.bluebeaker.legacyponder.structure.events.StructureCaptureEvent;
import io.bluebeaker.legacyponder.structure.events.StructureRenderEvent;
import io.bluebeaker.legacyponder.structure.events.StructureTileEvent;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.HashSet;
import java.util.Set;

public class EventHandlerBC {
    @SubscribeEvent
    public void onSaveBCTile(StructureTileEvent.Save event){
        if (!(event.tileEntity instanceof TileBC_Neptune)) return;
        // Crop tag
        event.tileData.getCompoundTag("owner").removeTag("Properties");
    }

    @SubscribeEvent
    public void onCapture(StructureCaptureEvent event){
        World world = event.world;

        try {

            Set<MarkerConnection<?>> connections = new HashSet<>();

            for (BlockPos pos : BlockPos.getAllInBox(event.minPoint, event.maxPoint)) {
                TileEntity tile = world.getTileEntity(pos);
                if (tile instanceof TileMarker) {
                    MarkerConnection<?> currentConnection = ((TileMarker<?>) tile).getCurrentConnection();
                    if (currentConnection != null) {
                        connections.add(currentConnection);
                    }
                }
            }

            NBTTagCompound bcData = new NBTTagCompound();

            NBTTagCompound markerData = LaserConnectionsHandler.dumpConnections(connections);

            bcData.setTag("markerConnections", markerData);

            event.extraData.setTag("buildcraft", bcData);
        }catch (Exception e){
            LegacyPonder.getLogger().error("Error capturing BC extra data: {}",e.getMessage());
        }
    }

    @SubscribeEvent
    public void postRender(StructureRenderEvent.Post event){
        if(event.extraStructureData==null) return;

        NBTTagCompound bcData = event.extraStructureData.getCompoundTag("buildcraft");
        NBTTagCompound markerData = bcData.getCompoundTag("markerConnections");
        if(markerData.isEmpty()) return;

        Vec3d delta = StructureRenderManager.calcRenderOffset();

        GlStateManager.translate(-delta.x, -delta.y, -delta.z);
        RenderHelper.disableStandardItemLighting();

        try {
            LaserConnectionsHandler.renderConnections(markerData);
        }catch (Exception e){
            LegacyPonder.getLogger().error("Error rendering BC extras: {}",e.getMessage());
        }

        RenderHelper.enableStandardItemLighting();
        GlStateManager.translate(delta.x, delta.y, delta.z);
    }
}
