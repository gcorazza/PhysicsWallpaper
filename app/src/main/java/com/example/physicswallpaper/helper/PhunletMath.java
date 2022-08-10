package com.example.physicswallpaper.helper;


import static java.lang.Math.PI;

import android.graphics.Matrix;

import androidx.annotation.NonNull;

import org.jbox2d.common.Vec2;

public class PhunletMath {
    public static Vec2 transformVec(Matrix m, Vec2 v){
        float[] dst = new float[2];
        m.mapPoints(dst, new float[]{v.x,v.y});
        return new Vec2(dst[0], dst[1]);
    }

    public static float getMatrixRotationDegree(Matrix m){
        float[] dst1 = new float[2];
        float[] dst0 = new float[2];
        m.mapPoints(dst0, new float[]{0,0});
        m.mapPoints(dst1, new float[]{1,0});
        double rad = Math.atan2(dst1[1] - dst0[1], dst1[0] - dst0[0]);
        return (float) toDegree(rad);
    }

    private static double toDegree(double rad) {
        return (rad/PI * 180);
    }

    @NonNull
    public static Matrix getMatrix(Vec2 pos, float degree) {
        Matrix matrix = new Matrix();
        matrix.postTranslate(pos.x, pos.y);
        matrix.postRotate(degree, pos.x, pos.y);
        return matrix;
    }
}
