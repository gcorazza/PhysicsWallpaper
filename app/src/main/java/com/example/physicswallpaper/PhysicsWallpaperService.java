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
        private final Handler handler = new Handler();
        private final Runnable drawRunner = new Runnable() {
            @Override
            public void run() {
                draw();
            }

        };
        int height,width;
        private boolean visible = true;
        private boolean touchEnabled;
        private PhysicsSimulation physicsSimulation = new PhysicsSimulation();
        private int updateIntervallms = 20;
        private Vec2 gravity= new Vec2();

        public PhysicsWallpaperEngine() {
            SharedPreferences prefs = PreferenceManager
                    .getDefaultSharedPreferences(PhysicsWallpaperService.this);
            touchEnabled = prefs.getBoolean("touch", true);
            handler.post(drawRunner);

            SensorManager sensorManager;
            Sensor sensor;
            sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
//            if (sensorManager != null) {
                sensor = sensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY);

                sensorManager.registerListener(new SensorEventListener() {
                    @Override
                    public void onSensorChanged(SensorEvent sensorEvent) {
                        gravity = new Vec2(-sensorEvent.values[0],-sensorEvent.values[1]).mul(10);
                    }

                    @Override
                    public void onAccuracyChanged(Sensor sensor, int i) {

                    }
                }, sensor, 10000);
//            }
        }


        @Override
        public void onVisibilityChanged(boolean visible) {
            this.visible = visible;
            if (visible) {
                handler.post(drawRunner);
            } else {
                handler.removeCallbacks(drawRunner);
            }
        }

        @Override
        public void onSurfaceDestroyed(SurfaceHolder holder) {
            super.onSurfaceDestroyed(holder);
            this.visible = false;
            handler.removeCallbacks(drawRunner);
        }

        @Override
        public void onSurfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            this.width = width;
            this.height = height;
            super.onSurfaceChanged(holder, format, width, height);
        }

        @Override
        public void onTouchEvent(MotionEvent event) {
            if (touchEnabled) {
                float x = event.getX();
                float y = event.getY();
                physicsSimulation.touch(x,y);
                super.onTouchEvent(event);
            }
        }

        private void draw() {
            SurfaceHolder holder = getSurfaceHolder();
            Canvas canvas = null;
            try {
                canvas = holder.lockCanvas();
                if (canvas != null) {
                    setCanvasToCmScaleAndSetLetCornerAs00(canvas);
                    physicsSimulation.setGravity(gravity);
                    physicsSimulation.drawAndUpdate(canvas, updateIntervallms);
                }
            } finally {
                holder.isCreating();
                if (canvas != null)
                    holder.unlockCanvasAndPost(canvas);
            }
            handler.removeCallbacks(drawRunner);
            if (visible) {
                handler.postDelayed(drawRunner, updateIntervallms);
            }
        }

        private void setCanvasToCmScaleAndSetLetCornerAs00(Canvas canvas) {
            DisplayMetrics displayMetrics = Resources.getSystem().getDisplayMetrics();
            canvas.translate(0,displayMetrics.heightPixels);
            canvas.scale(displayMetrics.xdpi/2.54f, -displayMetrics.ydpi/2.54f);
        }

    }
}
