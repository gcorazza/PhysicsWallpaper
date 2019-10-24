package com.example.physicswallpaper.DAO;

import com.example.physicswallpaper.Phunlets.PhunletBuilder;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.World;

public class PhunletRectFixtureDAO extends PhunletFixtureDAO {
    private Vec2 dim;

    public PhunletRectFixtureDAO(float density, Vec2 offset, float offAngle, int color, Vec2 dim) {
        this.dim = dim;
        this.density = density;
        this.offset = offset;
        this.offAngle = offAngle;
        this.color = color;
    }

    @Override
    public void build(World world, Body body) {
        PhunletBuilder.addRect(body, color, dim.x, dim.y, density, offset, offAngle);
    }
}
