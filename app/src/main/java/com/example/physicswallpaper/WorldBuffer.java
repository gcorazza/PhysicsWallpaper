package com.example.physicswallpaper;

import com.example.physicswallpaper.Phunlets.FixtureDraw;

import org.jbox2d.common.Transform;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.Fixture;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WorldBuffer {
    private Map<Integer, WorldShowState> worldBuffer = Collections.synchronizedMap(new HashMap<>());
    private int lastElement;
    private int step;

    public void saveState(Body bodyList) {
        List<ShowObjectData> data = new ArrayList<>();

        while (bodyList != null) {
            Fixture fixtureList = bodyList.m_fixtureList;
            while (fixtureList != null){
                FixtureDraw drawBody = (FixtureDraw) fixtureList.m_userData;
                data.add(new ShowObjectData(new Transform(drawBody.getBody().getTransform()), drawBody));
                fixtureList=fixtureList.m_next;
            }
            bodyList = bodyList.m_next;
        }
        worldBuffer.put(step++, new WorldShowState(data));
    }

    public int size() {
        return worldBuffer.size();
    }

    public boolean isEmpty() {
        return worldBuffer.isEmpty();
    }

    public WorldShowState getAndRemoveBefores(int step) {
        if (step > this.step)
            return null;

        WorldShowState worldShowState = worldBuffer.get(step);

        if (step >= lastElement) {
            for (int i = lastElement; i < step; i++) {
                worldBuffer.remove(i);
            }
            lastElement = step;
        }
        return worldShowState;
    }

    public int getStep() {
        return step;
    }
}
