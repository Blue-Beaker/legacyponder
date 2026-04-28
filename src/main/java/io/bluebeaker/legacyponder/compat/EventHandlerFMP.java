package io.bluebeaker.legacyponder.compat;

import codechicken.lib.packet.PacketCustom;
import codechicken.multipart.TMultiPart;
import codechicken.multipart.TileMultipart;
import io.bluebeaker.legacyponder.CommonConfig;
import io.bluebeaker.legacyponder.LegacyPonder;
import io.bluebeaker.legacyponder.structure.events.StructureTileEvent;
import io.bluebeaker.legacyponder.world.DummyWorld;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import scala.collection.Seq;

public class EventHandlerFMP {
    @SubscribeEvent
    public void onTileEntityLoad(StructureTileEvent.AfterLoad event){
        TileEntity tile = event.tileEntity;
        World world = event.world;
        if(!(world instanceof DummyWorld)) return;
        if(!(tile instanceof TileMultipart)) return;

        BlockPos pos = event.pos;

        try {
            TileMultipart tileMP = (TileMultipart) tile;

            Seq<TMultiPart> parts = tileMP.partList();
            PacketCustom pktWrite = new PacketCustom("legacyponder_fmp",1);
            tileMP.writeDesc(pktWrite);
            world.setTileEntity(pos,null);
            PacketCustom pktRead = new PacketCustom(pktWrite.copy());
            TileMultipart.handleDescPacket(world,pos,pktRead);
            if(CommonConfig.log_debug)
                LegacyPonder.getLogger().info("tile {} being loaded at {}, parts: {}", tileMP, tileMP.getPos(), parts);
        } catch (Throwable e) {
            LegacyPonder.getLogger().warn("Exception loading part for FMP", e);
        }
    }
}
