package com.example.physicswallpaper.phunlet;


import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.Fixture;
import org.jbox2d.dynamics.World;

public class BodyBuilder {

    public static Fixture addRect(Body body, int color, float width, float height, float density) {
        return addRect(body, width, height, density, new Vec2(), 0);
    }

    public static Fixture addRect(Body body, float width, float height, float density, Vec2 offset, float offAngle) {
        PolygonShape polygonShape = new PolygonShape();
        polygonShape.setAsBox(width, height, offset, offAngle);
        return body.createFixture(polygonShape, density);
    }

    public static Fixture addCircle(Body body, float radius, float density, Vec2 offset) {
        CircleShape circleShape = new CircleShape();
        circleShape.m_radius = radius;
        circleShape.m_p.set(offset);

        //        fixture.m_userData = new FixtureDrawCircle(fixture, color, radius);
        return body.createFixture(circleShape, density);
    }

    public static Body createBody(World world, float posX, float posY, float angle) {
        return createBody(world, new Vec2(posX, posY), angle);
    }

    public static Body createBody(World world, Vec2 pos, float angle) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(pos);
        bodyDef.angle = angle;
        bodyDef.type = BodyType.DYNAMIC;
        return world.createBody(bodyDef);
    }
}
