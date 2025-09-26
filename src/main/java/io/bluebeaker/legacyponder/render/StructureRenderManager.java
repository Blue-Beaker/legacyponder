package io.bluebeaker.legacyponder.render;

import com.google.common.base.Predicates;
import io.bluebeaker.legacyponder.LegacyPonder;
import io.bluebeaker.legacyponder.world.DummyWorld;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.client.MinecraftForgeClient;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;

import java.util.HashMap;
import java.util.Map;

public class StructureRenderManager {

    public static final BlockPos STRUCTURE_OFFSET = new BlockPos(0,63,0);
    protected static DummyWorld world = new DummyWorld();
    private static final Map<String, BufferBuilder> buffers = new HashMap<>();

    public static StructureViewPos viewPos = new StructureViewPos();

    protected static BufferBuilder bufferBuilder = new BufferBuilder(1024) {
        @Override
        public void reset() {
        }
    };
    protected static StructureBufferBuilder localBuffer;


    public static DummyWorld getWorld(){
        if(world==null){
            world=new DummyWorld();
        }
        return world;
    }

    public static void renderStructure(float partialTicks, int offsetX, int offsetY, int sizeX, int sizeY){

        GlStateManager.pushAttrib();
        GlStateManager.enableDepth();
        GlStateManager.enableBlend();
        GlStateManager.pushMatrix();
        GlStateManager.matrixMode(GL11.GL_PROJECTION);
        GlStateManager.pushMatrix();
        GlStateManager.loadIdentity();
        ScaledResolution scaledResolution = new ScaledResolution(Minecraft.getMinecraft());
        int viewportX = offsetX * scaledResolution.getScaleFactor();
        int viewportY = Minecraft.getMinecraft().displayHeight - (sizeY + offsetY) * scaledResolution.getScaleFactor();
        int viewportWidth = sizeX * scaledResolution.getScaleFactor();
        int viewportHeight = sizeY * scaledResolution.getScaleFactor();
        GL11.glEnable(GL11.GL_SCISSOR_TEST);
        GL11.glScissor(
                viewportX,
                viewportY,
                viewportWidth,
                viewportHeight
        );
        GlStateManager.clear(GL11.GL_DEPTH_BUFFER_BIT);
        GL11.glDisable(GL11.GL_SCISSOR_TEST);
        GlStateManager.viewport(
                viewportX,
                viewportY,
                viewportWidth,
                viewportHeight
        );
        GLU.gluPerspective(70.0F, (float) sizeX / sizeY, 0.1F, 1000F);
        GlStateManager.matrixMode(GL11.GL_MODELVIEW);
        GlStateManager.loadIdentity();
        GlStateManager.enableRescaleNormal();
        GlStateManager.pushMatrix();
        int snapshotSize = Math.max(Math.max(world.templateSize.getX(), world.templateSize.getY()), world.templateSize.getY());
        GlStateManager.translate(0, 0, -snapshotSize * 0.5F - 3);

        GlStateManager.scale(viewPos.scale,viewPos.scale,viewPos.scale);
        GlStateManager.rotate(viewPos.pitch, 1, 0, 0);
        GlStateManager.rotate(viewPos.yaw, 0, 1, 0);

        GlStateManager.translate(viewPos.camera_center.x,viewPos.camera_center.y,viewPos.camera_center.z);

        GlStateManager.translate(-world.templateSize.getX() / 2F, -world.templateSize.getY() / 2F, -world.templateSize.getZ() / 2F);
        GlStateManager.translate(0, snapshotSize * 0.1F, 0);
        Minecraft.getMinecraft().getRenderManager().renderEngine.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
        new WorldVertexBufferUploader().draw(localBuffer);
        if (snapshotSize < 32) {
            TileEntityRendererDispatcher.instance.preDrawBatch();
            for (int z = 0; z < world.templateSize.getZ(); z++) {
                for (int y = 0; y < world.templateSize.getY(); y++) {
                    for (int x = 0; x < world.templateSize.getX(); x++) {
                        BlockPos pos = new BlockPos(x, y, z).add(STRUCTURE_OFFSET);
                        GlStateManager.pushAttrib();
                        TileEntity tileEntity = world.getTileEntity(pos);
                        if(tileEntity !=null){
                            TileEntityRendererDispatcher.instance.render(
                                    tileEntity,
                                    pos.getX() - STRUCTURE_OFFSET.getX(),
                                    pos.getY() - STRUCTURE_OFFSET.getY(),
                                    pos.getZ() - STRUCTURE_OFFSET.getZ(),
                                    partialTicks
                            );
                        }
                        GlStateManager.popAttrib();
                    }
                }
            }
            TileEntityRendererDispatcher.instance.drawBatch(1);
        }
        for (Entity entity : world.getEntities(Entity.class, Predicates.alwaysTrue())) {
            Vec3d pos = entity.getPositionVector();
            GlStateManager.pushAttrib();
            Minecraft.getMinecraft().getRenderManager().renderEntity(
                    entity,
                    pos.x - STRUCTURE_OFFSET.getX(),
                    pos.y - STRUCTURE_OFFSET.getY(),
                    pos.z - STRUCTURE_OFFSET.getZ(),
                    entity.rotationYaw,
                    partialTicks,
                    true
            );
            GlStateManager.popAttrib();
        }
        GlStateManager.popMatrix();
        GlStateManager.disableRescaleNormal();
        GlStateManager.matrixMode(GL11.GL_PROJECTION);
        GlStateManager.viewport(0, 0, Minecraft.getMinecraft().displayWidth, Minecraft.getMinecraft().displayHeight);
        GlStateManager.popMatrix();
        GlStateManager.matrixMode(GL11.GL_MODELVIEW);
        GlStateManager.popMatrix();
        GlStateManager.disableBlend();
        GlStateManager.disableDepth();
        GlStateManager.popAttrib();
    }

    public static BufferBuilder updateBuffer(){
        localBuffer = new StructureBufferBuilder(1024);
        localBuffer.clear();

        BlockRenderLayer lastRenderLayer = MinecraftForgeClient.getRenderLayer();
        int lastRenderPass = MinecraftForgeClient.getRenderPass();

        localBuffer.begin(GL11.GL_QUADS, DefaultVertexFormats.BLOCK);

        ForgeHooksClient.setRenderPass(1);

        renderBuffer(localBuffer);


        localBuffer.setTranslation(0,0,0);
        localBuffer.finishDrawing();

        ForgeHooksClient.setRenderLayer(lastRenderLayer);
        ForgeHooksClient.setRenderPass(lastRenderPass);

        return localBuffer;
    }

    private static void renderBuffer(BufferBuilder buffer) {
        for (int z = 0; z < world.templateSize.getZ(); z++) {
            for (int y = 0; y < world.templateSize.getY(); y++) {
                for (int x = 0; x < world.templateSize.getX(); x++) {
                    BlockPos pos = new BlockPos(x, y, z).add(STRUCTURE_OFFSET);
                    buffer.setTranslation(
                            -STRUCTURE_OFFSET.getX(),
                            -STRUCTURE_OFFSET.getY(),
                            -STRUCTURE_OFFSET.getZ()
                    );
                    IBlockState blockState = world.getBlockState(pos);
                    if(blockState.getRenderType()== EnumBlockRenderType.INVISIBLE) continue;

                    for (BlockRenderLayer value : BlockRenderLayer.values()) {
                        if(blockState.getBlock().canRenderInLayer(blockState,value)){
                            ForgeHooksClient.setRenderLayer(value);
                            Minecraft.getMinecraft().getBlockRendererDispatcher().renderBlock(
                                    blockState,
                                    pos,
                                    world,
                                    buffer
                            );
                        }
                    }
                }
            }
        }
    }
}
