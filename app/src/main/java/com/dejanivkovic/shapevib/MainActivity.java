package com.dejanivkovic.shapevib;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    TextView textView;
    int progress = 0;
    public int seekBarValue;
    private ImageButton ibCircle;
    private ImageButton ibTriangle;
    private ImageButton ibRect;
    int x;
    public static final String KEY = "ivkovic";


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

         ibCircle = findViewById(R.id.CIrcleButton);
         ibTriangle = findViewById(R.id.TriangleButton);
         ibRect = findViewById(R.id.RectButton);
        ibRect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               x = 1;
            }
        });
        ibCircle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             x = 2;
            }
        });
        ibTriangle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                x = 3;
            }
        });



        SeekBar seekBar = findViewById(R.id.seekBar);
        seekBar.setMax(100);
        seekBar.setProgress(progress);
        textView = findViewById(R.id.textView);
        textView.setText("Size " + progress + "%");



        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
               progress = i;
                textView.setText("Size" + " " +progress + "%");
                seekBarValue = progress/2;

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        Button drawBut = findViewById(R.id.draw);
        drawBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (x == 2) {
                    Intent Cintent = new Intent(MainActivity.this, Circle.class);
                    Cintent.putExtra("ivkovic", seekBarValue);
                    startActivity(Cintent);

                }else if(x == 3){
                    Intent Tintent = new Intent(MainActivity.this, Triangle.class);
                    Tintent.putExtra("ivkovic", (seekBarValue*2));
                    startActivity(Tintent);

                }else if(x == 1){
                    Intent Rintent = new Intent(MainActivity.this, Rectangle.class);
                    Rintent.putExtra("ivkovic", seekBarValue*2);
                    startActivity(Rintent);

                }

            }




        });
    }

}
