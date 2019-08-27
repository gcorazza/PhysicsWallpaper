package com.example.physicswallpaper;

import org.jbox2d.common.Transform;

class ShowObjectData {
    private Transform transform;
    private WallpaperBody drawBody;

    public ShowObjectData(Transform transform, WallpaperBody drawBody) {
        this.transform = transform;
        this.drawBody = drawBody;
    }

    public Transform getTransform() {
        return transform;
    }

    public WallpaperBody getDrawBody() {
        return drawBody;
    }
}
