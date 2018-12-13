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

    public static final int GRAVITY = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        drawingUI();
    }

    private void drawingUI() {

        final ScrollView scrollView = new ScrollView(this);

        final LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setOrientation(LinearLayout.VERTICAL);

        scrollView.addView(linearLayout);
        setContentView(scrollView);

        TextView helloMoonTextView = new TextView(this);
        helloMoonTextView.setText(R.string.hello_moon);
        helloMoonTextView.setGravity(GRAVITY);
        helloMoonTextView.setPadding(20, 20, 20, 0);
        linearLayout.addView(helloMoonTextView);

        final Button showCalcBtn = new Button(this);
        showCalcBtn.setText(R.string.calculators);
        linearLayout.addView(showCalcBtn);
//
        showCalcBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), SecondActivity.class));
            }
        });

        Button addElementsBtn = new Button(this);
        addElementsBtn.setText(R.string.add_elements);
        linearLayout.addView(addElementsBtn);

        addElementsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AsyncLayoutInflater asyncLayoutInflater = new AsyncLayoutInflater(getApplicationContext());   //Asynchronic adding
                asyncLayoutInflater.inflate(R.layout.custom_activity, linearLayout, new AsyncLayoutInflater.OnInflateFinishedListener() {
                    @Override
                    public void onInflateFinished(View view, int resid, ViewGroup parent) {     //adding custom_activity in linearLayout
                        parent.addView(view);
                    }
                });

            }
        });
    }
}