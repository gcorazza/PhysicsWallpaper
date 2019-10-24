package com.example.physicswallpaper.DAO;

import com.example.physicswallpaper.Phunlets.FixtureDraw;
import com.example.physicswallpaper.Phunlets.FixtureDrawRectangle;

import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.Fixture;

import java.util.ArrayList;
import java.util.List;

public class PhunletDAO {
    List<PhunletFixtureDAO> fixtures = new ArrayList<>();

    public void addFixtureDAO(PhunletFixtureDAO fixtureDAO) {
        fixtures.add(fixtureDAO);
    }

    public static PhunletDAO createDAO(Body body) {
        PhunletDAO phunletDAO = new PhunletDAO();
        Fixture fix = body.m_fixtureList;
        while (fix != null) {
            PhunletFixtureDAO fixtureDAO = createFixtureDAO(fix);
            phunletDAO.addFixtureDAO(fixtureDAO);
            fix = fix.m_next;
        }

        return phunletDAO;
    }

    private static PhunletFixtureDAO createFixtureDAO(Fixture fix) {
        FixtureDraw fixtureDraw = ((FixtureDraw) fix.m_userData);
        if (fixtureDraw instanceof FixtureDrawRectangle) {
            return createRectDAO(fix);
        }
        return null;
    }

    private static PhunletRectFixtureDAO createRectDAO(Fixture fixture) {
        FixtureDrawRectangle fixtureDraw = ((FixtureDrawRectangle) fixture.m_userData);
        PhunletRectFixtureDAO dao = new PhunletRectFixtureDAO(
                fixture.m_density,
                fixtureDraw.getOffset(),
                fixtureDraw.getOffAngle(),
                fixtureDraw.getColor(),
                fixtureDraw.getDim());
        return dao;
    }}
