package com.dejanivkovic.shapevib;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

public class Triangle extends Activity {

    public int seekBarValue;
    int a;
    int b;
    int c;
    int x;
    int y;
    int width;
    int height;
    int xv;
    int yv;
    Bitmap bitmap;
    int pixel;
    private ImageView imageView;
    private Path path;
    private Paint paint;
    private float finalX;
    private float finalY;
    private float initialX;
    private float initialY;
    private int fingers;


    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_triangle);
        imageView = findViewById(R.id.imageView);
        seekBarValue = getIntent().getIntExtra(MainActivity.KEY, 0);
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        width = dm.widthPixels;
        height = dm.heightPixels;
        bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        imageView.draw(canvas);
        x = canvas.getWidth();
        y = canvas.getHeight();
        imageView.setImageBitmap(bitmap);
        path = new Path();
        paint = new Paint();
        paint.setStrokeWidth(seekBarValue / 2.5f);


        a = (x / 4 - x / 4) + seekBarValue * 3;
        b = a;
        if (seekBarValue < x / 2) {
            a = seekBarValue * 3;
        } else {
            seekBarValue = x / 2;
        }
        c = a;

        path.moveTo(x / 2, y / 2 - b);

        path.lineTo(x / 2 - b - (seekBarValue / 3), y / 2 + b); //

        path.lineTo(x / 2 + b + (seekBarValue / 3), y / 2 + b); //

        path.lineTo(x / 2, y / 2 - b);


        path.close();
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.BLACK);
        canvas.drawPath(path, paint);

        imageView.setDrawingCacheEnabled(true);
        imageView.buildDrawingCache(true);


        imageView.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN || motionEvent.getAction() == MotionEvent.ACTION_MOVE) {

                    bitmap = imageView.getDrawingCache();
                    xv = (int) motionEvent.getX();
                    yv = (int) motionEvent.getY();
                    pixel = bitmap.getPixel(xv, yv);

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
                        Intent intent = new Intent(Triangle.this, MainActivity.class);
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

