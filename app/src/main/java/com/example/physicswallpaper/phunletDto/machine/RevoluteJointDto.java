package com.example.physicswallpaper.phunletDto.machine;


import android.graphics.Matrix;

import com.example.physicswallpaper.helper.PhunletMath;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.World;
import org.jbox2d.dynamics.joints.Joint;
import org.jbox2d.dynamics.joints.RevoluteJointDef;

public class RevoluteJointDto extends JointDto {
    private final boolean enableMotor;
    private final float motorSpeed;
    private final float maxMotorTorque;
    private final float upperAngle;
    private final float lowerAngle;
    private final boolean enableLimit;
    private final boolean collideConnected;
    private final Vec2 anchor;

    public RevoluteJointDto(int bodyIndexA, int bodyIndexB, Vec2 anchor, boolean enableMotor, float motorSpeed, float maxMotorTorque, float upperAngle, float lowerAngle, boolean enableLimit, boolean collideConnected) {
        super(bodyIndexA, bodyIndexB);
        this.anchor = anchor;
        this.enableMotor = enableMotor;
        this.motorSpeed = motorSpeed;
        this.maxMotorTorque = maxMotorTorque;
        this.upperAngle = upperAngle;
        this.lowerAngle = lowerAngle;
        this.enableLimit = enableLimit;
        this.collideConnected = collideConnected;
    }

    public RevoluteJointDto(int bodyIndexA, int bodyIndexB, Vec2 anchor, boolean enableMotor, float motorSpeed, float maxMotorTorque) {
        this(bodyIndexA, bodyIndexB, anchor, enableMotor, motorSpeed, maxMotorTorque, 0, 0, false, false);
    }

    @Override
    public Joint create(World world, Body A, Body B, Matrix matrix) {
        RevoluteJointDef def = new RevoluteJointDef();
        Vec2 vec2 = PhunletMath.transformVec(matrix, anchor);
        def.initialize(A, B, vec2);
        def.enableMotor = enableMotor;
        def.motorSpeed = motorSpeed;
        def.maxMotorTorque = maxMotorTorque;
        def.upperAngle = upperAngle;
        def.lowerAngle = lowerAngle;
        def.enableLimit = enableLimit;
        def.collideConnected = collideConnected;
        return world.createJoint(def);
    }
}
