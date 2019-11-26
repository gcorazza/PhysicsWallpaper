package com.example.physicswallpaper.WorldBuffer;

import com.example.physicswallpaper.Phunlets.FixtureDraw;

import org.jbox2d.common.Transform;

public class ShowObjectData {
    private Transform transform;
    private FixtureDraw drawBody;

    public ShowObjectData(Transform transform, FixtureDraw drawBody) {
        this.transform = transform;
        this.drawBody = drawBody;
    }

    public Transform getTransform() {
        return transform;
    }

    public FixtureDraw getDrawBody() {
        return drawBody;
    }
}
