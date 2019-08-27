package com.example.physicswallpaper;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.DisplayMetrics;
import android.util.Log;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Transform;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.World;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class PhysicsSimulation {

    private World world;
    private List<WallpaperBody> drawBodys= new ArrayList<>();

    List<WorldShowState> worldBuffer =new ArrayList<>();
    private int bufferedStatesCount = 20;

    public PhysicsSimulation() {
        world = new World(new Vec2(0,-100));
        Random random = new Random();
        addRandomBody(random);
        addRandomBody(random);
        addRandomBody(random);
        addRandomBody(random);
        setWalls();
    }

    private void addRandomBody(Random random) {

        PolygonShape polygonShape = new PolygonShape();
        float xr= random.nextFloat();
        float yr= random.nextFloat() +0.1f;
        float[] hsv= new float[3];
        hsv[0]= random.nextFloat()*360;
        hsv[1]= 0.9f;
        hsv[2]= 0.9f;

        polygonShape.setAsBox(xr, yr);

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyType.DYNAMIC;
        bodyDef.angularVelocity= random.nextFloat()*20;
        bodyDef.linearVelocity= new Vec2(0,10);
        bodyDef.position.set((2+random.nextFloat()*5), (2+random.nextFloat()*5));
        Body body = world.createBody(bodyDef);
        body.setBullet(true);
        body.setSleepingAllowed(false);
        world.getBodyList();
        body.createFixture(polygonShape, 5.0f);
        WallpaperBody drawBody = new RectWallpaperBody(body,Color.HSVToColor(hsv),xr,yr);
        drawBodys.add(drawBody);
    }

    public void setGravity(Vec2 gravity) {
        world.setGravity(gravity);
    }

    public void drawAndUpdate(Canvas canvas, float updateIntervallms) {
        worldBuffer.add(new WorldShowState(getObjectShowData()));
        world.step(updateIntervallms/1000,5,5);


        canvas.drawColor(Color.BLACK);

        if (worldBuffer.size()> bufferedStatesCount){
            WorldShowState toShow = worldBuffer.remove(0);
            for (ShowObjectData objectShowDatum : toShow.objectShowData) {
                Transform transform = objectShowDatum.getTransform();
                objectShowDatum.getDrawBody().draw(canvas,transform);
            }
        }

    }

    private List<ShowObjectData> getObjectShowData() {
        List<ShowObjectData> data= new ArrayList<>();

        for (WallpaperBody drawBody : drawBodys) {
            data.add(new ShowObjectData(new Transform(drawBody.body.getTransform()), drawBody));
        }

        return data;
    }

    public void setWalls(){
        DisplayMetrics displayMetrics = Resources.getSystem().getDisplayMetrics();
        float screenXcm=((float) displayMetrics.widthPixels)/displayMetrics.xdpi*2.54f;
        float screenYcm=((float) displayMetrics.heightPixels)/displayMetrics.ydpi*2.54f;

        WallpaperBody floor = getWall(0,-101+0.3f);
        WallpaperBody right = getWall(-100+0.1f,0);
        WallpaperBody left = getWall(100+screenXcm-0.1f,0);
    }

    private WallpaperBody getWall(float x, float y) {
        PolygonShape polygonShape = new PolygonShape();
        polygonShape.setAsBox(100, 100);

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyType.STATIC;
        bodyDef.position.set(x,y);
        Body body = world.createBody(bodyDef);
        body.createFixture(polygonShape, 5.0f);
        RectWallpaperBody rectWallpaperBody = new RectWallpaperBody(body, Color.rgb(0, 255, 0), 100,100);
        drawBodys.add(rectWallpaperBody);
        return rectWallpaperBody;
    }

    public void touch(float x, float y) {

    }
}
