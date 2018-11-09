package com.example.heorhii_dubinin.blindviews;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ProgressBar;

public class SplashActivity extends AppCompatActivity {

    private static final int SPLASH_DELAY = 5000;

    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_progressbar);

        progressBar = findViewById(R.id.progressBar);
        animate3();
    }

    void animate3() {
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(progressBar, "rotation", 200, 0, 300);
        objectAnimator.setDuration(SPLASH_DELAY);
        objectAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                startActivity(new Intent(SplashActivity.this, MainActivity.class));
            finish();
            }
        });
        objectAnimator.reverse();
    }
}