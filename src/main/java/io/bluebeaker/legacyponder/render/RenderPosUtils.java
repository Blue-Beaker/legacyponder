package io.bluebeaker.legacyponder.render;

import org.lwjgl.BufferUtils;
import org.lwjgl.util.glu.GLU;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

public class RenderPosUtils {

    public static float[] projectToScreen(
            float objX, float objY, float objZ,
            FloatBuffer modelViewMatrix,
            FloatBuffer projectionMatrix,
            IntBuffer viewport) {

        FloatBuffer winPos = BufferUtils.createFloatBuffer(3);

        // 使用GLU的投影函数
        GLU.gluProject(objX, objY, objZ,
                modelViewMatrix, projectionMatrix, viewport,
                winPos);
        return new float[]{
                winPos.get(0),
                winPos.get(1),
                winPos.get(2)
        };
    }

    public static float[] unprojectFromScreen(
            float winX, float winY, float winZ,
            FloatBuffer modelViewMatrix,
            FloatBuffer projectionMatrix,
            IntBuffer viewport) {

        FloatBuffer objPos = BufferUtils.createFloatBuffer(3);

        GLU.gluUnProject(winX, winY, winZ,
                modelViewMatrix, projectionMatrix, viewport,
                objPos);

        return new float[]{
                objPos.get(0),
                objPos.get(1),
                objPos.get(2)
        };
    }

}
