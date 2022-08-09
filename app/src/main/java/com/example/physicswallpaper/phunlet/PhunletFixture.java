package com.example.physicswallpaper.phunlet;

import com.example.physicswallpaper.phunlet.draw.FixtureDraw;

import org.jbox2d.dynamics.Fixture;

public class PhunletFixture {

    private final FixtureDraw phunletFixtureDraw;
    private final Fixture phunletFixture;

    public PhunletFixture(FixtureDraw phunletFixtureDraw, Fixture phunletFixture) {
        this.phunletFixtureDraw = phunletFixtureDraw;
        this.phunletFixture = phunletFixture;
        phunletFixture.m_userData = this;
    }

    public FixtureDraw getPhunletFixtureDraw() {
        return phunletFixtureDraw;
    }

    public Fixture getPhunletFixture() {
        return phunletFixture;
    }
}
