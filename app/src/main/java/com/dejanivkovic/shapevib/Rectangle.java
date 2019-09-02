package com.dejanivkovic.shapevib;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import android.os.Bundle;
import android.os.Vibrator;
import android.util.DisplayMetrics;

import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;


public class Rectangle extends Activity {
    private ImageView iv;
    public int seekBarValue;
    int width;
    int height;
    int x;
    int y;
    int xv, yv, pixel;
    Bitmap bm;
    private Paint paint;
    float factor;
    private float finalX;
    private float finalY;
    private float initialX;
    private float initialY;
    private int fingers;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rect);

        iv = findViewById(R.id.imgRview);
        seekBarValue = getIntent().getIntExtra(MainActivity.KEY, 0);
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        width = dm.widthPixels;
        height = dm.heightPixels;
        bm = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bm);
        iv.draw(canvas);
        x = canvas.getWidth();
        y = canvas.getHeight();
        iv.setImageBitmap(bm);

        paint = new Paint();
        paint.setStrokeWidth(seekBarValue / 2f);

        factor = 3.2f;
        int halfW = width / 2;
        int halfH = height / 2;
        float hFactor = seekBarValue * factor;
        float wFactor = seekBarValue * factor;

        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.BLACK);
        canvas.drawRect(halfW - wFactor, halfH - hFactor, halfW + wFactor, halfH + hFactor, paint);

        iv.setDrawingCacheEnabled(true);
        iv.buildDrawingCache(true);


        iv.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN ||
                        motionEvent.getAction() == MotionEvent.ACTION_MOVE) {

                    bm = iv.getDrawingCache();
                    xv = (int) motionEvent.getX();
                    yv = (int) motionEvent.getY();


                    pixel = bm.getPixel(xv, yv);

                    int r = Color.red(pixel);
                    int g = Color.green(pixel);
                    int bl = Color.blue(pixel);
                    int alfa = Color.alpha(pixel);


                    if (motionEvent.getPointerCount() == 1 && alfa == 255 && r == 0 && g == 0 && bl == 0) {
                        vibrator.vibrate(100);
                    } else {

                        vibrator.cancel();
                        return true;
                    }
                }


                if (motionEvent.getActionMasked() == MotionEvent.ACTION_POINTER_DOWN) {
                    initialX = motionEvent.getX(1);
                    initialY = motionEvent.getY(1);
                    fingers = motionEvent.getPointerCount();
                }


                if (fingers == 2 && motionEvent.getActionMasked() == MotionEvent.ACTION_POINTER_UP) {
                    finalX = motionEvent.getX(1);
                    finalY = motionEvent.getY(0);

                    if (finalX - initialX > 300 || initialY == finalY) {
                        Intent intent = new Intent(Rectangle.this, MainActivity.class);
                        finish();
                        startActivity(intent);


                    }
                }

                if (fingers == 3 && motionEvent.getActionMasked() == MotionEvent.ACTION_POINTER_UP) {
                    finalX = motionEvent.getX(0);
                    finalY = motionEvent.getY(1);


                    if (finalY - initialY > 300 || initialX == finalX) {
                        moveTaskToBack(true);
                        android.os.Process.killProcess(android.os.Process.myPid());
                        System.exit(1);
                        return true;
                    }
                }

                return true;

            }

        });
    }

    @Override
    public void onBackPressed() {

    }
}