package com.example.heorhii_dubinin.blindviews;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        drawingUI();
    }

    private void drawingUI() {

        LinearLayout linLayout = new LinearLayout(this);
        linLayout.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams linLayoutParam = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        setContentView(linLayout, linLayoutParam);

        LinearLayout.LayoutParams lpView = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        TextView tv = new TextView(this);
        tv.setText(R.string.hello_moon);
        tv.setLayoutParams(lpView);
        linLayout.addView(tv);

        Button btn1 = new Button(this);
        btn1.setText(R.string.calculators);
        linLayout.addView(btn1, lpView);

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        Button btn2 = new Button(this);
        btn2.setText(R.string.add_elements);
        linLayout.addView(btn2, lpView);

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}