package io.bluebeaker.legacyponder.compat;

import codechicken.multipart.MultipartGenerator$;
import codechicken.multipart.TileMultipart;
import codechicken.multipart.handler.MultipartProxy$;
import io.bluebeaker.legacyponder.LegacyPonder;
import io.bluebeaker.legacyponder.structure.events.StructureTileEvent;
import io.bluebeaker.legacyponder.world.DummyWorld;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class EventHandlerFMP {
    @SubscribeEvent
    public void onTileEntityLoad(StructureTileEvent.AfterLoad event){
        TileEntity tile = event.tileEntity;
        BlockPos pos = event.pos;
        World world = event.world;
        if((tile instanceof TileMultipart) && (world instanceof DummyWorld)){
            try {
                TileMultipart tileMP = (TileMultipart) tile;
                LegacyPonder.getLogger().info("tile {} being loaded at {}, parts: {}",tileMP,tileMP.getPos(),tileMP.partList());

                TileMultipart tileMPC = MultipartGenerator$.MODULE$.generateCompositeTile(null, tileMP.partList(), true);

                if(tileMPC!=tileMP){
                    world.setBlockState(pos, MultipartProxy$.MODULE$.block().getDefaultState());
//                    MultipartGenerator$.MODULE$.silentAddTile(world, pos, tileMPC);
                    tileMPC.setPos(pos);
                    tileMPC.setWorld(world);
                    world.removeTileEntity(pos);
                    world.setTileEntity(pos,tileMPC);
                }
                tileMPC.loadParts(tileMP.partList());
                tileMPC.notifyTileChange();
                tileMPC.markRender();

                LegacyPonder.getLogger().info("tile {} being loaded at {}, parts: {}",tileMPC,tileMPC.getPos(),tileMPC.partList());
            } catch (Throwable e) {
                LegacyPonder.getLogger().warn("Exception loading part for FMP",e);
            }
        }
    }
    private long lastPrintTimestamp = 0L;
    @SubscribeEvent
    public void onTileEntityRender(StructureTileEvent.Render event){
        TileEntity tile = event.tileEntity;
        if(tile instanceof TileMultipart){
            if(System.currentTimeMillis()-lastPrintTimestamp>5000L){
                lastPrintTimestamp=System.currentTimeMillis();

                LegacyPonder.getLogger().info("tile {} being rendered at {}, parts: {}",tile,tile.getPos(),
                        ((TileMultipart) tile).partList());
            }
        }
    }
}
