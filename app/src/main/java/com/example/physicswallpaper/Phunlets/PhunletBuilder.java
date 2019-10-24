package com.example.physicswallpaper.Phunlets;

import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.Fixture;
import org.jbox2d.dynamics.World;

public class PhunletBuilder {

    public static Body addRect(Body body, int color, float width, float height, float density) {
        return addRect(body, color, width, height, density, new Vec2(), 0);
    }

    public static Body addRect(Body body, int color, float width, float height, float density, Vec2 offset, float offAngle) {
        PolygonShape polygonShape = new PolygonShape();
        polygonShape.setAsBox(width, height, offset, offAngle);
        Fixture fixture = body.createFixture(polygonShape, density);
        FixtureDrawRectangle phunletRectangle = new FixtureDrawRectangle(fixture, color, width, height);
        phunletRectangle.offAngle = offAngle;
        phunletRectangle.offset = offset;
        fixture.m_userData = phunletRectangle;
        return body;
    }

    public static Body addCircle(Body body, int color, float radius, float density, Vec2 offset) {
        CircleShape circleShape = new CircleShape();
        circleShape.m_radius = radius;
        circleShape.m_p.set(offset);

        Fixture fixture = body.createFixture(circleShape, density);
        FixtureDrawCircle phunletCircle = new FixtureDrawCircle(fixture, color, radius);
        fixture.m_userData = phunletCircle;
        return body;
    }

    public static Body createBody(World world, float posX, float posY) {
        return createBody(world, new Vec2(posX, posY));
    }

    public static Body createBody(World world, Vec2 pos) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(pos);
        bodyDef.type = BodyType.DYNAMIC;
        return world.createBody(bodyDef);
    }
}
