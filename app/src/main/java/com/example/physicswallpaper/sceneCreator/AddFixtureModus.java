package com.example.physicswallpaper.sceneCreator;

import static android.graphics.Color.BLACK;
import static android.graphics.Color.BLUE;
import static android.graphics.Color.GREEN;

import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;

import com.example.physicswallpaper.R;
import com.example.physicswallpaper.helper.PhunletMath;

import org.jbox2d.common.Vec2;

public class AddFixtureModus extends Modus {
    final int CIRCLE = 0, RECT = 1;
    int shape = -1;
    private Vec2 startPos, endPos;
    private static Paint positionPaint = positionPaint_();
    private static Paint fixturePaint = fixturePaint_();

    private static Paint fixturePaint_() {
        Paint paint = new Paint();
        paint.setColor(BLACK);
        paint.setStrokeWidth(0.1f);
        return paint;
    }

    private static Paint positionPaint_() {
        Paint paint = new Paint();
        paint.setColor(BLUE);
        paint.setStrokeWidth(0.1f);
        paint.setStyle(Paint.Style.STROKE);
        return paint;
    }

    public AddFixtureModus(SceneCreatorActivity sceneCreatorActivity, View header) {
        super(sceneCreatorActivity, header);
        View readyBtn = header.findViewById(R.id.readyFixtureBtn);
        View addCircleBtn = header.findViewById(R.id.addCircleBtn);
        View addRectBtn = header.findViewById(R.id.addRectBtn);

        readyBtn.setOnClickListener((view) -> {
            sceneCreatorActivity.popModus();
        });
        addCircleBtn.setOnClickListener((view) -> {
            shape = CIRCLE;
            sceneCreatorActivity.redraw();
        });
        addRectBtn.setOnClickListener((view) -> {
            shape = RECT;
            sceneCreatorActivity.redraw();
        });

    }

    @Override
    public void init() {

    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        Matrix inverse = new Matrix();
        v.getMatrix().invert(inverse);
        Vec2 pos = PhunletMath.transformVec(inverse, new Vec2(event.getX(), event.getY()));

        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                startPos = pos;
                endPos = pos;
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                startPos = null;
                endPos = null;
                break;
            case MotionEvent.ACTION_MOVE:
                if (startPos != null)
                    endPos = pos;
                break;
            case MotionEvent.ACTION_UP:
                break;
        }

        return true;
    }

    @Override
    public void onDraw(Canvas canvas) {
        if (startPos != null)
            canvas.drawRect(startPos.x - 0.2f, startPos.y - 0.2f, startPos.x + 0.2f, startPos.y + 0.2f, positionPaint);
        if (endPos != null)
            canvas.drawRect(endPos.x - 0.2f, endPos.y - 0.2f, endPos.x + 0.2f, endPos.y + 0.2f, positionPaint);

        if (startPos != null && endPos != null) {
            canvas.drawLine(startPos.x, startPos.y, endPos.x, endPos.y, positionPaint);

            switch (shape) {
                case CIRCLE:
                    Vec2 s_e_mid = new Vec2(startPos).add(endPos.negate()).mul(0.5f);
                    canvas.drawCircle(endPos.x+s_e_mid.x, endPos.y + s_e_mid.y, s_e_mid.length(), fixturePaint);
                    break;

                case RECT:
                    canvas.drawRect(startPos.x, startPos.y, endPos.x, endPos.y, fixturePaint);
                    break;

                case -1:
                    break;

                default:
                    throw new IllegalStateException("Unexpected value: " + shape);
            }
        }

    }
}
