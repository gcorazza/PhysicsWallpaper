package com.example.physicswallpaper.DAO;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.World;

public abstract class PhunletFixtureDAO {
    protected float density;
    protected Vec2 offset;
    protected float offAngle;
    protected int color;

    public abstract void build(World world, Body body);
}
