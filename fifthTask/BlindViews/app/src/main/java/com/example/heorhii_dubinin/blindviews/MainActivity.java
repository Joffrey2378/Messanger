package com.example.heorhii_dubinin.blindviews;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.AsyncLayoutInflater;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        drawingUI();
    }

    private void drawingUI() {

        final ScrollView scrollView = new ScrollView(this);

        final LinearLayout linLayout = new LinearLayout(this);
        linLayout.setOrientation(LinearLayout.VERTICAL);

        LinearLayout.LayoutParams linLayoutParam = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        scrollView.addView(linLayout, linLayoutParam);
        setContentView(scrollView, linLayoutParam);

        LinearLayout.LayoutParams lpView = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        TextView tv = new TextView(this);
        tv.setText(R.string.hello_moon);
        tv.setLayoutParams(lpView);
        linLayout.addView(tv);

        final Button btn1 = new Button(this);
        btn1.setText(R.string.calculators);
        linLayout.addView(btn1, lpView);

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), SecondActivity.class));
            }
        });

        Button btn2 = new Button(this);
        btn2.setText(R.string.add_elements);
        linLayout.addView(btn2, lpView);

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AsyncLayoutInflater asyncLayoutInflater = new AsyncLayoutInflater(getApplicationContext());
                asyncLayoutInflater.inflate(R.layout.custom_activity, linLayout, new AsyncLayoutInflater.OnInflateFinishedListener() {
                    @Override
                    public void onInflateFinished(View view, int resid, ViewGroup parent) {
                        parent.addView(view);
                    }
                });

            }
        });
    }
//    linearLayout.setOnTouchListener(new View.OnTouchListener() {
//        @Override
//        public boolean onTouch(View v, MotionEvent event) {
//            Intent intent = new Intent(SecondActivity.this,SevenColorRainbowActivity.class);
//            startActivity(intent);
//            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
//            return true;
//        }
//    }
}