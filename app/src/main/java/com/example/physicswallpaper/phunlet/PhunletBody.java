package com.example.physicswallpaper.phunlet;

import java.util.List;

public class PhunletBody {
    private final List<PhunletFixture> phunletFixtures;

    public PhunletBody(List<PhunletFixture> phunletFixtures) {
        this.phunletFixtures = phunletFixtures;
    }

    public List<PhunletFixture> getPhunletFixtures() {
        return phunletFixtures;
    }
}
