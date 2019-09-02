package com.dejanivkovic.shapevib;


import android.app.Activity;
import android.content.Context;

import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;


import android.os.Bundle;

import android.os.Vibrator;
import android.util.DisplayMetrics;


import android.view.MotionEvent;

import android.view.View;
import android.view.ViewGroup;


public class Circle extends Activity implements View.OnTouchListener {

    public float x;
    public float y;
    public float mCircleX, mCircleY;
    public float mCircleX1, mCircleY1;
    float r;
    float p;
    public int seekBarValue;
    private float finalX;
    private float finalY;
    private float initialX;
    private float initialY;
    private int fingers;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vibration);


        seekBarValue = getIntent().getIntExtra(MainActivity.KEY, 0);

        MyCustomPanel view = new MyCustomPanel(this);


        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        addContentView(view, params);
        view.setOnTouchListener(this);
    }


    public class MyCustomPanel extends View {

        public MyCustomPanel(Context context) {
            super(context);


        }


        @Override
        public void onDraw(Canvas canvas) {

            Paint paint = new Paint();

            DisplayMetrics dm = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(dm);
            int width = dm.widthPixels;


            super.onDraw(canvas);

            paint.setStyle(Paint.Style.FILL);


//---CIRCLE
            if (mCircleX == 0f || mCircleY == 0f) {
                mCircleX = getWidth() / 2;
                mCircleY = getHeight() / 2;
            }


            paint.setColor(Color.BLACK);
            paint.setStyle(Paint.Style.FILL);
            r = (width / 100) * seekBarValue;
            canvas.drawCircle(mCircleX, mCircleY, r, paint);


            if (mCircleX1 == 0f || mCircleY1 == 0f) {
                mCircleX1 = getWidth() / 2;
                mCircleY1 = getHeight() / 2;
            }

            paint.setColor(Color.WHITE);
            paint.setStyle(Paint.Style.FILL);
            p = (width / 100) * (seekBarValue - 8);
            canvas.drawCircle(mCircleX1, mCircleY1, p, paint);
        }


    }


    @Override
    public boolean onTouch(View view, MotionEvent event) {


        if (event.getActionMasked() == MotionEvent.ACTION_POINTER_DOWN) {
            initialX = event.getX(1);
            initialY = event.getY(1);
            fingers = event.getPointerCount();
        }


        if (fingers == 2 && event.getActionMasked() == MotionEvent.ACTION_POINTER_UP) {
            finalX = event.getX(1);
            finalY = event.getY(0);

            if (finalX - initialX > 300 || initialY == finalY) {
                Intent intent = new Intent(Circle.this, MainActivity.class);
                finish();
                startActivity(intent);


            }
        }

        if (fingers == 3 && event.getActionMasked() == MotionEvent.ACTION_POINTER_UP) {
            finalX = event.getX(0);
            finalY = event.getY(1);


            if (finalY - initialY > 300 || initialX == finalX) {
                moveTaskToBack(true);
                android.os.Process.killProcess(android.os.Process.myPid());
                System.exit(1);
                return true;
            }
        }

        Vibrator vb = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);


        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                x = event.getX();
                y = event.getY();
                if (event.getPointerCount() > 1) {
                    vb.cancel();
                } else {
                    vb.vibrate(100);
                    return true;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                x = event.getX();
                y = event.getY();

                if (event.getPointerCount() == 1) {

                    double dx = Math.pow(x - mCircleX, 2);
                    double dy = Math.pow(y - mCircleY, 2);

                    double dx1 = Math.pow(x - mCircleX1, 2);
                    double dy1 = Math.pow(y - mCircleY1, 2);


                    if ((float) (dx + dy) < Math.pow(r, 2) && dx1 + dy1 > Math.pow(p, 2)) {
                        vb.vibrate(50000);

                    } else {
                        vb.cancel();

                    }

                    return true;
                }
                vb.cancel();

                break;

            case MotionEvent.ACTION_UP:
                x = event.getX();
                y = event.getY();
                vb.cancel();
                break;
            default:
                return true;
        }


        return true;

    }

    @Override
    public void onBackPressed() {
    }


}





