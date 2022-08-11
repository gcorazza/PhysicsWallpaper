package com.example.physicswallpaper.helper;

import android.content.res.Resources;
import android.graphics.Matrix;
import android.util.DisplayMetrics;

public class Display {


    public static float getScreenXcm() {
        DisplayMetrics displayMetrics = Resources.getSystem().getDisplayMetrics();
        return ((float) displayMetrics.widthPixels) / displayMetrics.xdpi * 2.54f;
    }

    public static float getScreenYcm() {
        DisplayMetrics displayMetrics = Resources.getSystem().getDisplayMetrics();
        return ((float) displayMetrics.heightPixels) / displayMetrics.ydpi * 2.54f;
    }

    public static Matrix getMatrixCanvasToCmScaleAndSetLeftDownCornerAs00() {
        DisplayMetrics displayMetrics = Resources.getSystem().getDisplayMetrics();
        Matrix matrix = new Matrix();
        matrix.setTranslate(0, displayMetrics.heightPixels);
        matrix.preScale(displayMetrics.xdpi / 2.54f, -displayMetrics.ydpi / 2.54f);
        return matrix;
    }
}
