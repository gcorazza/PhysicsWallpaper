package com.example.physicswallpaper.Phunlet.dto;

import com.example.physicswallpaper.Phunlet.draw.FixtureDraw;
import com.example.physicswallpaper.Phunlet.draw.FixtureDrawCircle;
import com.example.physicswallpaper.Phunlet.draw.FixtureDrawRectangle;
import com.google.gson.Gson;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.Fixture;
import org.jbox2d.dynamics.World;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static com.example.physicswallpaper.ContextGetter.getAppContext;
import static com.example.physicswallpaper.Phunlet.PhunletBuilder.addCircle;
import static com.example.physicswallpaper.Phunlet.PhunletBuilder.addRect;
import static com.example.physicswallpaper.Phunlet.PhunletBuilder.createBody;

import android.util.Log;

public class PhunletDAO implements Serializable {
    private final List<PhunletFixtureDAO> fixtures = new ArrayList<>();

    public PhunletDAO(Body body) {
        Fixture fix = body.m_fixtureList;
        while (fix != null) {
            PhunletFixtureDAO fixtureDAO = createFixtureDAO(fix);
            fixtures.add(fixtureDAO);
            fix = fix.m_next;
        }
    }

    private PhunletFixtureDAO createFixtureDAO(Fixture fix) {
        FixtureDraw fixtureDraw = ((FixtureDraw) fix.m_userData);
        Shape shape;
        float[] data;

        if (fixtureDraw instanceof FixtureDrawRectangle) {
            shape = Shape.Rect;
            Vec2 dim = ((FixtureDrawRectangle) fixtureDraw).getDim();
            data = new float[]{dim.x, dim.y};
        } else if (fixtureDraw instanceof FixtureDrawCircle) {
            shape = Shape.Circle;
            data = new float[]{((FixtureDrawCircle) fixtureDraw).getRadius()};
        } else {
            throw new RuntimeException("No Shape found for:" + fixtureDraw.toString());
        }

        return new PhunletFixtureDAO(
                fixtureDraw.getOffset(),
                fixtureDraw.getOffAngle(),
                fix.m_density,
                fixtureDraw.getColor(),
                data,
                shape
        );
    }

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

    public static PhunletDAO load(String name) {
        String filesDirPath = getAppContext().getFilesDir().getAbsolutePath();
        String filePath = filesDirPath + "/" + name + ".pl";
        File file = new File(filePath);
        String json = null;
        try {
            json = new Scanner(file).useDelimiter("\\Z").next();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return new Gson().fromJson(json, PhunletDAO.class);

    }

    public void addInWorld(World world, Vec2 pos, float angle) {
        Body body = createBody(world, pos, angle);
        for (PhunletFixtureDAO dao : fixtures) {
            switch (dao.getShape()) {
                case Circle:
                    addCircle(body, dao.color, dao.data[0], dao.density, dao.offset);
                    break;
                case Rect:
                    addRect(body, dao.color, dao.data[0], dao.data[1], dao.density, dao.offset, dao.offAngle);
                    break;
            }
        }
    }
}
