package com.example.physicswallpaper.sceneCreator;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;

import com.example.physicswallpaper.PhysicsWallpaperService;

public class SceneCreatorView extends View implements View.OnTouchListener {
    Matrix matrix = PhysicsWallpaperService.getMatrixCanvasToCmScaleAndSetLeftDownCornerAs00();
    private Drawer drawer;

    static final int NONE = 0;
    static final int ZOOMDRAG = 2;
    static final int CLICK = 3;
    int mode = NONE;

    PointF last = new PointF();
    PointF start = new PointF();
    float[] m;

    float width, height;

    ScaleGestureDetector mScaleDetector;
    Context context;

    public SceneCreatorView(Context context, AttributeSet attr) {
        super(context, attr);
        super.setClickable(true);
        this.context = context;
        mScaleDetector = new ScaleGestureDetector(context, new ScaleListener());
        m = new float[9];
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.concat(matrix);
        if (drawer != null)
            drawer.onDraw(canvas);
    }

    public void setDrawer(Drawer drawer) {
        this.drawer = drawer;
    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {
        mScaleDetector.onTouchEvent(event);

        PointF curr = new PointF(event.getX(), event.getY());

        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            //when two fingers are touching
            //set the mode to ZOOMDRAG
            case MotionEvent.ACTION_POINTER_DOWN:
                last.set(event.getX(), event.getY());
                start.set(last);
                mode = ZOOMDRAG;
                break;
            case MotionEvent.ACTION_DOWN:
                break;
            //when a finger moves
            //If mode is applicable move image
            case MotionEvent.ACTION_MOVE:
                if (mode == ZOOMDRAG) {
                    float deltaX = curr.x - last.x;// x difference
                    float deltaY = curr.y - last.y;// y difference
                    //move the image with the matrix
                    matrix.postTranslate(deltaX, deltaY);
                    //set the last touch location to the current
                    last.set(curr.x, curr.y);
                }
                break;
            //first finger is lifted
            case MotionEvent.ACTION_POINTER_UP:
                mode = NONE;
                int xDiff = (int) Math.abs(curr.x - start.x);
                int yDiff = (int) Math.abs(curr.y - start.y);
                if (xDiff < CLICK && yDiff < CLICK)
                    performClick();
                break;
        }
        invalidate();
        return true;
    }

    private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {

        @Override
        public boolean onScaleBegin(ScaleGestureDetector detector) {
            mode = ZOOMDRAG;
            return true;
        }

        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            float mScaleFactor = detector.getScaleFactor();
            float focusX = detector.getFocusX();
            float focusY = detector.getFocusY();
            matrix.postScale(mScaleFactor, mScaleFactor, focusX, focusY);
            return true;
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        width = MeasureSpec.getSize(widthMeasureSpec);
        height = MeasureSpec.getSize(heightMeasureSpec);
    }

    @Override
    public Matrix getMatrix() {
        return matrix;
    }
}