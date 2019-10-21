package com.example.physicswallpaper.Phunlets;

import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.World;

public class PhunletBuilder {
    public static PhunletRectangle addRect(World world, int color, float posX, float posY, float width, float height, float angle, float density) {
        PolygonShape polygonShape = new PolygonShape();
        polygonShape.setAsBox(width, height);

        Body body = createBody(world, posX, posY, angle);
        body.createFixture(polygonShape, density);
        PhunletRectangle phunletRectangle = new PhunletRectangle(body, color, width, height);
        body.m_userData = phunletRectangle;
        return phunletRectangle;
    }

    public static PhunletCircle addCircle(World world, int color, float posX, float posY, float radius, float angle, float density) {
        CircleShape circleShape = new CircleShape();
        circleShape.m_radius = radius;

        Body body = createBody(world, posX, posY, angle);
        body.createFixture(circleShape, density);
        PhunletCircle phunletCircle = new PhunletCircle(body, color, radius);
        body.m_userData = phunletCircle;
        return phunletCircle;
    }

    private static Body createBody(World world, float posX, float posY, float angle) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(posX, posY);
        bodyDef.angle = angle;
        return world.createBody(bodyDef);
    }
}
