package com.example.physicswallpaper;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.service.wallpaper.WallpaperService;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.SurfaceHolder;

import org.jbox2d.common.Vec2;


public class PhysicsWallpaperService extends WallpaperService {
    @Override
    public Engine onCreateEngine() {
        return new PhysicsWallpaperEngine();
    }

    private class PhysicsWallpaperEngine extends Engine {
        private final Runnable draw = new Runnable() {
            @Override
            public void run() {
                draw();
            }
        };

        Handler handler = new Handler();
        private boolean visible = true;
        private boolean touchEnabled;
        private int FPS = 40;
        private PhysicsSimulation physicsSimulation = new PhysicsSimulation(FPS);
        private int timeBehindms = 30;

        public PhysicsWallpaperEngine() {
            SharedPreferences prefs = PreferenceManager
                    .getDefaultSharedPreferences(PhysicsWallpaperService.this);
            touchEnabled = prefs.getBoolean("touch", true);
            handler.post(draw);
            physicsSimulation.start();

            registerSensor(new SensorEventListener() {
                @Override
                public void onSensorChanged(SensorEvent sensorEvent) {
                    physicsSimulation.setGravity(new Vec2(-sensorEvent.values[0], -sensorEvent.values[1]).mul(10));
                }

                @Override
                public void onAccuracyChanged(Sensor sensor, int i) {
                }
            }, Sensor.TYPE_GRAVITY);

            registerSensor(new SensorEventListener() {
                @Override
                public void onSensorChanged(SensorEvent sensorEvent) {
                    physicsSimulation.setGravity(new Vec2(-sensorEvent.values[0], -sensorEvent.values[1]).mul(50));
                }

                @Override
                public void onAccuracyChanged(Sensor sensor, int accuracy) {

                }
            }, Sensor.TYPE_LINEAR_ACCELERATION);

        }

        private void registerSensor(SensorEventListener listener, int typeGravity) {
            SensorManager sensorManager;
            Sensor sensor;
            sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
            sensor = sensorManager.getDefaultSensor(typeGravity);

            sensorManager.registerListener(listener, sensor, 10000);
        }


        @Override
        public void onVisibilityChanged(boolean visible) {
            this.visible = visible;
            if (!visible){
                physicsSimulation.pausePhysics();
            }else
                physicsSimulation.resumePhysics();
        }

        @Override
        public void onSurfaceDestroyed(SurfaceHolder holder) {
            super.onSurfaceDestroyed(holder);
            this.visible = false;
        }


        @Override
        public void onTouchEvent(MotionEvent event) {
            if (touchEnabled) {
                float x = event.getX();
                float y = event.getY();
                physicsSimulation.touch(x, y);
                super.onTouchEvent(event);
            }
        }

        private void draw() {
            handler.postDelayed(draw, (long) (1000f/FPS));
            if (!visible)
                return;

            SurfaceHolder holder = getSurfaceHolder();
            Canvas canvas = null;
            try {
                canvas = holder.lockCanvas();
                if (canvas != null) {
                    setCanvasToCmScaleAndSetLetCornerAs00(canvas);
                    physicsSimulation.draw(canvas, timeBehindms);
                }
            } finally {
                if (canvas != null) {
                    holder.unlockCanvasAndPost(canvas);
                }
            }
        }

        private void setCanvasToCmScaleAndSetLetCornerAs00(Canvas canvas) {
            DisplayMetrics displayMetrics = Resources.getSystem().getDisplayMetrics();
            canvas.translate(0, displayMetrics.heightPixels);
            canvas.scale(displayMetrics.xdpi / 2.54f, -displayMetrics.ydpi / 2.54f);
        }

    }
}
