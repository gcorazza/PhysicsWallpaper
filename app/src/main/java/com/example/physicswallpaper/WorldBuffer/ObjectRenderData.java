package com.example.physicswallpaper.WorldBuffer;

import com.example.physicswallpaper.Phunlet.draw.FixtureDraw;

import org.jbox2d.common.Transform;

public class ObjectRenderData {
    private Transform transform;
    private FixtureDraw drawBody;

    public ObjectRenderData(Transform transform, FixtureDraw drawBody) {
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
