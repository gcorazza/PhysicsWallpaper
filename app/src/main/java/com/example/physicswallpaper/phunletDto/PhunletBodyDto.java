package com.example.physicswallpaper.phunletDto;

import com.example.physicswallpaper.phunlet.PhunletBody;
import com.example.physicswallpaper.phunlet.PhunletFixture;
import com.google.gson.Gson;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.World;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import static com.example.physicswallpaper.ContextGetter.getAppContext;
import static com.example.physicswallpaper.phunlet.BodyBuilder.addRect;
import static com.example.physicswallpaper.phunlet.BodyBuilder.createBody;

import android.util.Log;

public class PhunletBodyDto implements Serializable {
    final List<PhunletFixtureDto> fixturesDto = new ArrayList<>();
    private final boolean bullet, allowSleeping;
    private final BodyType bodyType;


    public PhunletBodyDto(boolean bullet, boolean allowSleeping, BodyType bodyType) {
        this.bullet = bullet;
        this.allowSleeping = allowSleeping;
        this.bodyType = bodyType;
    }

//    public PhunletBodyDto(Body body) {
//        Fixture fixture = body.m_fixtureList;
//        while (fixture != null){
//
//            ShapeType shape = fixture.getShape().getType();
//
//            switch (shape){
//                case CIRCLE:
//                    fixturesDto.add(new PhunletFixture());
//                    break;
//                case EDGE:
//                    break;
//                case POLYGON:
//                    break;
//                case CHAIN:
//                    break;
//                default:
//                    throw new IllegalStateException("Unexpected value: " + shape);
//            }
//
//            fixture = fixture.m_next;
//        }
//    }

    public void save(String name) {
        String json = new Gson().toJson(this);
        Log.d("gson", json);
        String filesDirPath = getAppContext().getFilesDir().getAbsolutePath();
        String filePath = filesDirPath + "/" + name + ".pl";
        System.out.println("filePath = " + filePath);
        File file = new File(filePath);
        try {
            if (file.createNewFile()) {
                FileWriter fileWriter = new FileWriter(file);
                fileWriter.write(json);
                fileWriter.close();
            } else {
                System.err.println("Couldn't create file: " + filePath);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static PhunletBodyDto load(String name) {
        String filesDirPath = getAppContext().getFilesDir().getAbsolutePath();
        String filePath = filesDirPath + "/" + name + ".pl";
        File file = new File(filePath);
        String json = null;
        try {
            json = new Scanner(file).useDelimiter("\\Z").next();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return new Gson().fromJson(json, PhunletBodyDto.class);
    }

    public PhunletBody create(World world, Vec2 pos, float angle) {
        Body body = createBody(world, pos, angle);
        body.setBullet(bullet);
        body.setType(bodyType);
        body.setSleepingAllowed(allowSleeping);

        List<PhunletFixture> phunletFixtures
                = fixturesDto.stream()
                .map(phunletFixtureDto -> phunletFixtureDto.create(body))
                .collect(Collectors.toList());
        return new PhunletBody(phunletFixtures, body);
    }
}
