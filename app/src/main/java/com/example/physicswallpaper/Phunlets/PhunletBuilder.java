package com.example.physicswallpaper.Phunlets;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.World;

public class PhunletBuilder {
    public static RectPhunlet addRect( World world, int color, float posX, float posY, float width, float height, float angle, float density) {
        PolygonShape polygonShape = new PolygonShape();
        polygonShape.setAsBox(width, height);

        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(posX, posY);
        bodyDef.angle = angle;
        Body body = world.createBody(bodyDef);
        body.createFixture(polygonShape, density);
        RectPhunlet rectWallpaperBody = new RectPhunlet(body, color, width, height);
        body.m_userData = rectWallpaperBody;
        return rectWallpaperBody;
    }
}
