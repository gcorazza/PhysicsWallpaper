package com.example.physicswallpaper;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Build;
import android.util.DisplayMetrics;
import android.util.Log;

import androidx.annotation.RequiresApi;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Transform;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.Fixture;
import org.jbox2d.dynamics.World;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import static java.lang.Thread.sleep;


public class PhysicsSimulation extends Thread {

    private final WorldBuffer worldBuffer;
    private World world;
    private List<WallpaperBody> drawBodys = new ArrayList<>();
    private long startTime = System.currentTimeMillis();

    private final float FPS;
    private int step;
    private WorldShowState lastToShow;
    private int pauseStep = -1;
    private long startPause;
    private final int wallWidth = 3;
    private List<Vec2> moveAcceleration = Collections.synchronizedList(new ArrayList<>());
    private Vec2 gravity = new Vec2();
    private Vec2 lastMoveAcceleration = new Vec2();

    public PhysicsSimulation(float FPS) {
        this.FPS = FPS;
        worldBuffer = new WorldBuffer();
        world = new World(new Vec2(0, -100));
        Random random = new Random();
        addRandomBody(random);
        addRandomBody(random);
        addRandomBody(random);
        addRandomBody(random);
        setWalls();
    }

    @Override
    public void run() {
        try {
            while (true) {
                while (!isPaused()) {
                    updateToActualStep();
                    sleep(10);
                }
                sleep(100);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void pausePhysics() {
        pauseStep = shouldBeInStep();
        startPause = System.currentTimeMillis();
    }

    public void resumePhysics() {
        pauseStep = -1;
        if (startPause != 0)
            startTime += System.currentTimeMillis() - startPause;
    }

    public void updateToActualStep() {
        int shouldBeInStep = shouldBeInStep();
        for (int i = step; i < shouldBeInStep; i++) {
            worldBuffer.saveState(drawBodys);
            world.setGravity(calcAccelerationVec().add(gravity));
            world.step(1f / FPS, 10, 10);
            step++;
            Log.d("timing", "step");
        }
    }

    public Vec2 calcAccelerationVec() {
        if (moveAcceleration.isEmpty()) {
            return lastMoveAcceleration;
        }
        final Vec2[] vr = {new Vec2()};
        moveAcceleration.forEach((v) -> {
            vr[0] = vr[0].add(v);
        });
        lastMoveAcceleration = vr[0].mul(1f / moveAcceleration.size());
        moveAcceleration.clear();
        return vr[0];
    }

    public void draw(Canvas canvas, float timeBehindms) {
        canvas.drawColor(Color.BLACK);
        int stepToShow = (int) (shouldBeInStep() - timeBehindms / 1000 * FPS);
        WorldShowState toShow = worldBuffer.getAndRemoveBefores(stepToShow);

        if (toShow != null) {
            lastToShow = toShow;
        } else {
            toShow = lastToShow;
        }

        if (toShow == null)
            return;

        for (ShowObjectData objectShowDatum : toShow.objectShowData) {
            Transform transform = objectShowDatum.getTransform();
            objectShowDatum.getDrawBody().draw(canvas, transform);
        }

    }

    private int shouldBeInStep() {
        if (pauseStep == -1)
            return (int) ((float) (System.currentTimeMillis() - startTime) * (FPS / 1000));
        else
            return pauseStep;
    }

    private void addRandomBody(Random random) {

        float xr = random.nextFloat() + 0.2f;
        float yr = random.nextFloat() + 0.2f;
        float[] hsv = new float[3];
        hsv[0] = random.nextFloat() * 360;
        hsv[1] = 0.9f;
        hsv[2] = 0.9f;

        PolygonShape polygonShape = new PolygonShape();
        polygonShape.setAsBox(xr, yr);

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyType.DYNAMIC;
        bodyDef.angularVelocity = random.nextFloat() * 20;
        bodyDef.linearVelocity = new Vec2(0, 10);
        bodyDef.position.set((2 + random.nextFloat() * 5), (2 + random.nextFloat() * 5));
        Body body = world.createBody(bodyDef);
        body.setBullet(true);
        body.setSleepingAllowed(false);
        Fixture fixture = body.createFixture(polygonShape, 5.0f);
        fixture.m_restitution = 0.2f + random.nextFloat() * 0.3f;

        WallpaperBody drawBody = new RectWallpaperBody(body, Color.HSVToColor(hsv), xr, yr);
        drawBodys.add(drawBody);
    }

    public void setWalls() {
        float screenXcm = getScreenXcm();
        float screenYcm = getScreenYcm();
        float wallTight = wallWidth;

        setWall(-wallTight, screenYcm / 2, wallWidth, wallWidth + screenYcm); //left Wall
        setWall(screenXcm + wallTight, screenYcm / 2, wallWidth, wallWidth + screenYcm); //right Wall
        setWall(screenXcm / 2, -wallTight, screenXcm + wallWidth, wallWidth); //down Wall
        setWall(screenXcm / 2, screenYcm + wallTight, screenXcm + wallWidth, wallWidth); //top Wall
    }

    private void setWall(float posX, float posY, float width, float height) {
        PolygonShape polygonShape = new PolygonShape();
        polygonShape.setAsBox(width, height);

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyType.STATIC;
        bodyDef.position.set(posX, posY);
        Body body = world.createBody(bodyDef);
        body.createFixture(polygonShape, 5.0f);
        RectWallpaperBody rectWallpaperBody = new RectWallpaperBody(body, Color.rgb(255, 255, 255), width, height);
        drawBodys.add(rectWallpaperBody);
    }

    private float getScreenXcm() {
        DisplayMetrics displayMetrics = Resources.getSystem().getDisplayMetrics();
        return ((float) displayMetrics.widthPixels) / displayMetrics.xdpi * 2.54f;
    }

    private float getScreenYcm() {
        DisplayMetrics displayMetrics = Resources.getSystem().getDisplayMetrics();
        return ((float) displayMetrics.heightPixels) / displayMetrics.ydpi * 2.54f;
    }

    public void touch(float x, float y) {

    }

    public void setMovement(Vec2 moveAcc) {
        Log.d("timing", "setMove");
        this.moveAcceleration.add(moveAcc);
    }

    private boolean isPaused() {
        return pauseStep != -1;
    }

    public void setGravity(Vec2 gravity) {
        this.gravity = gravity;
    }
}
