package com.example.physicswallpaper.phunletDto.machine;


import android.graphics.Matrix;

import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.World;
import org.jbox2d.dynamics.joints.Joint;

public abstract class JointDto {
    private final int bodyIndexA, bodyIndexB;

    public JointDto(int bodyIndexA, int bodyIndexB) {
        this.bodyIndexA = bodyIndexA;
        this.bodyIndexB = bodyIndexB;
    }

    public abstract Joint create(World world, Body A, Body B, Matrix matrix);

    public int getBodyIndexA() {
        return bodyIndexA;
    }

    public int getBodyIndexB() {
        return bodyIndexB;
    }
}
