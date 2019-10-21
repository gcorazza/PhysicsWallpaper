package com.example.physicswallpaper;

import com.example.physicswallpaper.Phunlets.Phunlet;

import org.jbox2d.common.Transform;

class ShowObjectData {
    private Transform transform;
    private Phunlet drawBody;

    public ShowObjectData(Transform transform, Phunlet drawBody) {
        this.transform = transform;
        this.drawBody = drawBody;
    }

    public Transform getTransform() {
        return transform;
    }

    public Phunlet getDrawBody() {
        return drawBody;
    }
}
