package io.bluebeaker.legacyponder.compat;

import crazypants.enderio.base.conduit.IClientConduit;
import crazypants.enderio.base.conduit.IServerConduit;
import crazypants.enderio.conduits.conduit.TileConduitBundle;
import io.bluebeaker.legacyponder.LegacyPonder;
import io.bluebeaker.legacyponder.structure.events.StructureTileEvent;
import io.bluebeaker.legacyponder.world.DummyWorld;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.lang.reflect.Field;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class EventHandlerEIOConduits {
    private Field clientConduits = null;
    public EventHandlerEIOConduits(){
        // Setup accessor
        try {
            clientConduits = TileConduitBundle.class.getDeclaredField("clientConduits");
            clientConduits.setAccessible(true);
        } catch (Throwable e) {
            LegacyPonder.getLogger().warn("Error loading enderIO compat: ",e);
        }
    }

    @SubscribeEvent
    public void onLoadConduit(StructureTileEvent.AfterLoad event){
        TileEntity tile = event.tileEntity;
        if((tile instanceof TileConduitBundle) && (event.world instanceof DummyWorld)){
            if(clientConduits==null) return;
            TileConduitBundle bundle = (TileConduitBundle) tile;
            try {
                // Copy conduits to clientConduits
                List<IClientConduit> cc = (List<IClientConduit>)clientConduits.get(bundle);
                if(cc==null){
                    cc=new CopyOnWriteArrayList<>();
                    clientConduits.set(bundle,cc);
                }
                for (IServerConduit conduit : bundle.getServerConduits()) {
                    if(conduit instanceof IClientConduit){
                        cc.add((IClientConduit) conduit);
                    }
                }

            } catch (Throwable e) {
                LegacyPonder.getLogger().warn("Error loading enderIO conduit: ",e);
            }
        }
    }
}
