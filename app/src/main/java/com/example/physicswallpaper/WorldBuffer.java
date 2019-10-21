package com.example.physicswallpaper;

import com.example.physicswallpaper.Phunlets.Phunlet;

import org.jbox2d.common.Transform;
import org.jbox2d.dynamics.Body;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WorldBuffer {
    private Map<Integer, WorldShowState> worldBuffer = Collections.synchronizedMap(new HashMap<>());
    private int lastElement;
    private int step;

    public void saveState(Body drawBodys) {
        List<ShowObjectData> data = new ArrayList<>();

        while (drawBodys!=null){
            Phunlet drawBody = (Phunlet) drawBodys.m_userData;
            data.add(new ShowObjectData(new Transform(drawBody.getBody().getTransform()), drawBody));
            drawBodys=drawBodys.m_next;
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
