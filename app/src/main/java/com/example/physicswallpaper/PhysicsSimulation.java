package com.example.physicswallpaper;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.DisplayMetrics;

import com.example.physicswallpaper.DAO.PhunletBodyDAO;
import com.example.physicswallpaper.WorldBuffer.ShowObjectData;
import com.example.physicswallpaper.WorldBuffer.WorldBuffer;
import com.example.physicswallpaper.WorldBuffer.WorldShowState;

import org.jbox2d.callbacks.ContactImpulse;
import org.jbox2d.callbacks.ContactListener;
import org.jbox2d.collision.Manifold;
import org.jbox2d.collision.WorldManifold;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Transform;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.World;
import org.jbox2d.dynamics.contacts.Contact;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import static com.example.physicswallpaper.Phunlets.PhunletBuilder.addRect;
import static com.example.physicswallpaper.Phunlets.PhunletBuilder.createBody;
import static java.lang.Math.toDegrees;


public class PhysicsSimulation extends Thread implements ContactListener {

    private final WorldBuffer worldBuffer;
    private World world;
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
    private int WHITE = Color.rgb(255, 255, 255);

    public PhysicsSimulation(float FPS) {
        this.FPS = FPS;
        worldBuffer = new WorldBuffer();
        world = new World(new Vec2());
        world.setContactListener(this);
        Random random = new Random();
        /*
        Body body = createBody(world, 2, 2, 0);
        addRect(body, Color.BLUE, 1f, 0.3f, 5);
        addRect(body, Color.BLUE, 0.3f, 1f, 5);
        PhunletBodyDAO phunletBodyDAO = new PhunletBodyDAO(body);
        phunletBodyDAO.save("cross");*/
        PhunletBodyDAO cross = PhunletBodyDAO.load("cross");
        for (int i = 0; i < 5; i++) {
            cross.addInWorld(world, new Vec2(i * 2, i * 2), (float) toDegrees(i * 10));
        }
        cross.addInWorld(world, new Vec2(2f, 2f), (float) toDegrees(45));
        setWalls();
    }

    @Override
    public void run() {
        try {
            while (true) {
                while (!isPaused()) {
                    updateToActualStep();
                    sleep((long) (1000f/FPS));
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
            worldBuffer.saveState(world.getBodyList());
            //world.setGravity(calcAccelerationVec().add(gravity));
            world.step(1f / FPS, 10, 10);
            step++;
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
        float h = random.nextFloat() * 360;
        float s = 0.9f;
        float v = 0.9f;
        int color = HSVToColor(h, s, v);

        float posX = 2 + random.nextFloat() * 5;
        float posY = 2 + random.nextFloat() * 5;
        float width = random.nextFloat() + 0.2f;
        float height = random.nextFloat() + 0.2f;

        Body body = addRect(createBody(world, posX, posY, 0), color, width, height, 5);
        body.setBullet(true);
        body.setSleepingAllowed(false);
    }

    private int HSVToColor(float h, float s, float v) {
        float[] hsv = new float[3];
        hsv[0] = h;
        hsv[1] = s;
        hsv[2] = v;
        return Color.HSVToColor(hsv);
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
        Body body = addRect(createBody(world, posX, posY, 0), WHITE, width, height, 5);
        body.setType(BodyType.STATIC);
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
        this.moveAcceleration.add(moveAcc);
    }

    private boolean isPaused() {
        return pauseStep != -1;
    }

    public void setGravity(Vec2 gravity) {
        this.gravity = gravity;
    }

    @Override
    public void beginContact(Contact contact) {

    }

    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {
        WorldManifold worldManifold = new WorldManifold();
        contact.getWorldManifold(worldManifold);
        //worldManifold.points;
        //impulse.
    }
}
