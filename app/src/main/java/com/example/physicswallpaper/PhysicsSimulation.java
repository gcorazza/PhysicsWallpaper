package com.example.physicswallpaper;

import static android.graphics.Color.BLACK;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.util.DisplayMetrics;

import com.example.physicswallpaper.helper.PhunletMath;
import com.example.physicswallpaper.phunletDto.machine.JointDto;
import com.example.physicswallpaper.phunletDto.machine.MachineDto;
import com.example.physicswallpaper.phunlet.PhunletBody;
import com.example.physicswallpaper.phunletDto.PhunletDtoBuilder;
import com.example.physicswallpaper.phunletDto.PhunletBodyDto;
import com.example.physicswallpaper.WorldBuffer.ObjectRenderData;
import com.example.physicswallpaper.WorldBuffer.WorldRenderData;
import com.example.physicswallpaper.phunletDto.machine.MachinePartDto;

import org.jbox2d.callbacks.ContactImpulse;
import org.jbox2d.callbacks.ContactListener;
import org.jbox2d.collision.Manifold;
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

import static com.example.physicswallpaper.helper.PhunletMath.getMatrix;
import static com.example.physicswallpaper.myMachines.MyMachines.cutie;
import static com.example.physicswallpaper.phunlet.BodyBuilder.addRect;
import static com.example.physicswallpaper.phunlet.BodyBuilder.createBody;


public class PhysicsSimulation extends Thread implements ContactListener {

    private final World world;

    private final float sleep;
    private WorldRenderData lastToShow;
    private boolean paused = false;
    private final int wallThickness = 3;
    private final List<Vec2> moveAcceleration = Collections.synchronizedList(new ArrayList<>());
    private Vec2 lastMoveAcceleration = new Vec2();
    private final int WHITE = Color.rgb(255, 255, 255);
    private final int GRAY = Color.rgb(127, 127, 127);
    private final List<PostSolve> postSolves = new ArrayList<>();
    private final int velocityIterations = 100;
    private final int positionIterations = 100;
    private long lastPhysicsUpdate;
    private Vec2 gravity = new Vec2();

    private final List<PhunletBody> phunlets = new ArrayList<>();

    public PhysicsSimulation(float sleep) {
        super("Physics Simulation Thread");
        this.sleep = sleep;
        world = new World(new Vec2());
        world.setContactListener(this);

        MachineDto cutie = cutie();
        addMachine(cutie, new Vec2(0,8), 45);
        addMachine(cutie, new Vec2(4,4), 90);
        addMachine(cutie, new Vec2(0,0), 0);
//        addMachine(cutie, new Vec2(5,5), 0);

        setWalls();
    }

    public PhunletBody addPhunlet(PhunletBodyDto phunletBodyDto, Vec2 pos, float degree){
        PhunletBody phunletBody = phunletBodyDto.create(world, pos, degree);
        phunlets.add(phunletBody);
        return phunletBody;
    }

    public void addMachine(MachineDto machineDto, Vec2 pos, float degree){
        List<MachinePartDto> machinePartDtos = machineDto.getMachinePartDtos();
        List<PhunletBody> forJoints = new ArrayList<>();
        for (int i = 0; i < machinePartDtos.size(); i++) {
            Matrix machineCoord = getMatrix(pos, degree);
            MachinePartDto machinePartDto = machinePartDtos.get(i);
            Vec2 offset = machinePartDto.getOffset();
            float offDegree = machinePartDto.getOffDegree();
            Matrix partCoord = getMatrix(offset, offDegree);
            partCoord.postConcat(machineCoord);
            Vec2 endPos = PhunletMath.transformVec(partCoord, new Vec2(0, 0));
            float degreeDst = PhunletMath.getMatrixRotationDegree(machineCoord);
            PhunletBody phunletBody = addPhunlet(machinePartDto.getPhunletBodyDto(), endPos, degreeDst);
            forJoints.add(phunletBody);
        }

        Matrix matrix = getMatrix(pos, degree);
        for (JointDto jointDto : machineDto.getJointDtos()) {
            Body bodyA = forJoints.get(jointDto.getBodyIndexA()).getBody();
            Body bodyB = forJoints.get(jointDto.getBodyIndexB()).getBody();
            jointDto.create(world, bodyA, bodyB, matrix);
        }

    }

    @Override
    public void run() {
        try {
            lastPhysicsUpdate = System.currentTimeMillis();
            while (true) {
                while (!isPaused()) {
                    updatePhysics();
                    sleep((long) sleep);
                }
                sleep(100);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void pausePhysics() {
        paused = true;
    }

    public void resumePhysics() {
        paused = false;
        lastPhysicsUpdate = System.currentTimeMillis();
    }

    public void updatePhysics() {
        float dt = ((float) (System.currentTimeMillis() - lastPhysicsUpdate)) / 1000;
        world.step(dt, velocityIterations, positionIterations);
        Vec2 accel = calcAccelerationVec();
        world.setGravity(gravity.add(accel));
        postSolves.forEach(postSolve -> processPostSolves(postSolve.contact, postSolve.contactImpulse));
        lastPhysicsUpdate = System.currentTimeMillis();
        lastToShow = saveRenderDataFromWorld(phunlets);
    }

    public static WorldRenderData saveRenderDataFromWorld(List<PhunletBody> bodyList) {
        List<ObjectRenderData> data = new ArrayList<>();

        bodyList.forEach(phunletBody -> {
            phunletBody.getPhunletFixtures().forEach(phunletFixture -> {
                data.add(new ObjectRenderData(new Transform(phunletFixture.getPhunletFixture().getBody().getTransform()), phunletFixture.getPhunletFixtureDraw()));
            });
        });

        return new WorldRenderData(data);
    }

    public Vec2 calcAccelerationVec() {
        if (moveAcceleration.isEmpty()) {
            return lastMoveAcceleration;
        }
        final Vec2[] vr = {new Vec2()};
        moveAcceleration.forEach((v) -> vr[0] = vr[0].add(v));
        lastMoveAcceleration = vr[0].mul(1f / moveAcceleration.size());
        moveAcceleration.clear();
        return vr[0];
    }

    public void draw(Canvas canvas, Matrix myMatrix) {
        if (lastToShow == null) {
            return;
        }
        canvas.drawColor(BLACK);

        for (ObjectRenderData objectShowDatum : lastToShow.objectShowData) {
            canvas.setMatrix(myMatrix);
            Transform transform = objectShowDatum.getTransform();
            objectShowDatum.getDrawBody().draw(canvas, transform);
        }

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

        PhunletBodyDto body = PhunletDtoBuilder.createBody();
        PhunletDtoBuilder.addRect(body, color, width, height, 5);
        addPhunlet(body, new Vec2(posX, posY), 0);
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
        float wallTight = wallThickness;

        setWall(-wallTight, screenYcm / 2, wallThickness, wallThickness + screenYcm); //left Wall
        setWall(screenXcm + wallTight, screenYcm / 2, wallThickness, wallThickness + screenYcm); //right Wall
        setWall(screenXcm / 2, -wallTight, screenXcm + wallThickness, wallThickness); //down Wall
        setWall(screenXcm / 2, screenYcm + wallTight, screenXcm + wallThickness, wallThickness); //top Wall
    }

    private void setWall(float posX, float posY, float width, float height) {
        PolygonShape polygonShape = new PolygonShape();
        polygonShape.setAsBox(width, height);
        Body body = createBody(world, posX, posY, 0);
        addRect(body, width, height, 5);
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
        return paused;
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

    private void processPostSolves(Contact contact, ContactImpulse impulse) {
//        WorldManifold worldManifold = new WorldManifold();
//        contact.getWorldManifold(worldManifold);
//        Vec2[] points = worldManifold.points;
//        float normalImpuls = impulse.normalImpulses[0];
//        int color = ((FixtureDraw) contact.getFixtureA().m_userData).getColor();
    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {
        //postSolves.add(new PostSolve(contact, impulse));
    }

    private class PostSolve {
        Contact contact;
        ContactImpulse contactImpulse;

        public PostSolve(Contact contact, ContactImpulse contactImpulse) {
            this.contact = contact;
            this.contactImpulse = contactImpulse;
        }
    }
}
