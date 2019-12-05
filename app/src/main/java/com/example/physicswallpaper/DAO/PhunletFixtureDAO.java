package com.example.physicswallpaper.DAO;

import com.example.physicswallpaper.Phunlets.PhunletBuilder;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.World;

public class PhunletFixtureDAO {
    protected Vec2 offset;
    protected float offAngle;
    protected float density;
    protected int color;
    protected float[] data;
    private Shape shape;

    public PhunletFixtureDAO(Vec2 offset, float offAngle, float density, int color, float[] data, Shape shape) {
        this.offset = offset;
        this.offAngle = offAngle;
        this.density = density;
        this.color = color;
        this.data = data;
        this.shape = shape;
    }

    public void build(World world, Body body) {
        switch (shape) {
            case Rect:
                PhunletBuilder.addRect(body, color, data[0], data[1], density, offset, offAngle);
                return;
            case Circle:
                PhunletBuilder.addCircle(body, color, data[0], density, offset);
        }
    }

    public Vec2 getOffset() {
        return offset;
    }

    public float getOffAngle() {
        return offAngle;
    }

    public float getDensity() {
        return density;
    }

    public int getColor() {
        return color;
    }

    public float[] getData() {
        return data;
    }

    public Shape getShape() {
        return shape;
    }
}
