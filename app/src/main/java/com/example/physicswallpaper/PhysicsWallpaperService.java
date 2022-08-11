package com.example.physicswallpaper;

import static com.example.physicswallpaper.helper.Display.getMatrixCanvasToCmScaleAndSetLeftDownCornerAs00;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.service.wallpaper.WallpaperService;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;

import org.jbox2d.common.Vec2;


public class PhysicsWallpaperService extends WallpaperService {
    @Override
    public Engine onCreateEngine() {
        return new PhysicsWallpaperEngine();
    }

    private class PhysicsWallpaperEngine extends Engine {
        private final Runnable draw = this::draw;

        Handler handler = new Handler();
        private boolean visible = true;
        private boolean touchEnabled;
        private float FPS = 40;
        private PhysicsSimulation physicsSimulation;
        private int timeBehindms = 30;
        private SensorEventListener gravityListener;
        private SensorEventListener accelerationListener;

        public PhysicsWallpaperEngine() {
            SharedPreferences prefs = PreferenceManager
                    .getDefaultSharedPreferences(PhysicsWallpaperService.this);
            touchEnabled = prefs.getBoolean("touch", true);

            int accelerationStrength = 30;
            int gravityStrength = 10;

            physicsSimulation = new PhysicsSimulation(20);
            gravityListener = new SensorEventListener() {
                @Override
                public void onSensorChanged(SensorEvent sensorEvent) {
                    physicsSimulation.setGravity(new Vec2(-sensorEvent.values[0], -sensorEvent.values[1]).mul(gravityStrength));
                }

                @Override
                public void onAccuracyChanged(Sensor sensor, int i) {
                }
            };
            accelerationListener = new SensorEventListener() {
                @Override
                public void onSensorChanged(SensorEvent sensorEvent) {
                    physicsSimulation.setMovement(new Vec2(-sensorEvent.values[0], -sensorEvent.values[1]).mul(accelerationStrength));
                }

                @Override
                public void onAccuracyChanged(Sensor sensor, int accuracy) {

                }
            };

            handler.post(draw);
            physicsSimulation.start();
        }

        private void registerSensors() {
            registerSensor(gravityListener, Sensor.TYPE_GRAVITY);
            registerSensor(accelerationListener, Sensor.TYPE_LINEAR_ACCELERATION);
        }

        private void unregisterSensors() {
            SensorManager sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
            sensorManager.unregisterListener(gravityListener);
            sensorManager.unregisterListener(accelerationListener);
        }


        private void registerSensor(SensorEventListener listener, int typeGravity) {
            SensorManager sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
            Sensor sensor = sensorManager.getDefaultSensor(typeGravity);
            sensorManager.registerListener(listener, sensor, (int) (1f / FPS * 1000000), timeBehindms * 1000 / 2); //method saves energy consumtion
        } //jeremy

        @Override
        public void onVisibilityChanged(boolean visibility) {
            Log.d("visibility", visibility + "");
            this.visible = visibility;
            if (visibility) {
                setVisible();
            } else {
                setUnvisible();
            }
        }

        private void setVisible() {
            registerSensors();
            handler.post(draw);
            physicsSimulation.resumePhysics();
        }

        private void setUnvisible() {
            unregisterSensors();
            physicsSimulation.pausePhysics();
        }

        @Override
        public void onSurfaceCreated(SurfaceHolder holder) {
            super.onSurfaceCreated(holder);
            Log.d("created", "created");
        }

        @Override
        public void onSurfaceDestroyed(SurfaceHolder holder) {
            Log.d("destroyed", "destroyed");
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

        @Override
        public void onSurfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            super.onSurfaceChanged(holder, format, width, height);
            Log.d("changed", "changed surface size");
        }

        private void draw() {
            handler.removeCallbacks(draw);
            if (!visible)
                return;
            handler.postDelayed(draw, (long) (1000f / FPS));

            SurfaceHolder holder = getSurfaceHolder();
            Canvas canvas = null;
            Paint paint = new Paint();
            paint.setColor(Color.rgb(255, 255, 0));
            paint.setStrokeWidth(0.5f);
            try {
                canvas = holder.lockCanvas();
                if (canvas != null) {
                    Matrix baseMatrix = canvas.getMatrix();
                    Matrix matrixCanvasToCmScaleAndSetLeftDownCornerAs00 = getMatrixCanvasToCmScaleAndSetLeftDownCornerAs00();
                    Matrix myMatrix = new Matrix(baseMatrix);
                    myMatrix.preConcat(matrixCanvasToCmScaleAndSetLeftDownCornerAs00);

                    canvas.drawLine(1, 0, 1, 1, paint);
                    canvas.drawLine(5, 0, 1, 1, paint);
                    physicsSimulation.draw(canvas, myMatrix);
                }
            } finally {
                if (canvas != null) {
                    holder.unlockCanvasAndPost(canvas);
                }
            }
        }


    }



}
