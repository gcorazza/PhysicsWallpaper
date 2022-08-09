package com.example.physicswallpaper.phunlet;

import org.jbox2d.dynamics.Body;

import java.util.List;

public class PhunletBody {
    private final List<PhunletFixture> phunletFixtures;
    private final Body body;

    public PhunletBody(List<PhunletFixture> phunletFixtures, Body body) {
        this.phunletFixtures = phunletFixtures;
        this.body = body;
    }

    public List<PhunletFixture> getPhunletFixtures() {
        return phunletFixtures;
    }

    public Body getBody() {
        return body;
    }
}
