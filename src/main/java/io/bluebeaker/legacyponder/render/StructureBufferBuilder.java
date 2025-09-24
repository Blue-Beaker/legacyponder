package io.bluebeaker.legacyponder.render;

import net.minecraft.client.renderer.BufferBuilder;

public class StructureBufferBuilder extends BufferBuilder {
    public StructureBufferBuilder(int bufferSizeIn) {
        super(bufferSizeIn);
    }

    @Override
    public void reset() {
    }

    public void clear(){
        super.reset();
    }
}
