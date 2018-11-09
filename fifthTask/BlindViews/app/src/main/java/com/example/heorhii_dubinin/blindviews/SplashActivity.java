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
    public static final int REVERSE_ROTATION = 200;
    public static final int FORWARD_ROTATION = 300;
    public static final int SPEED_ROTATION = 1000;

    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_progressbar);

        progressBar = findViewById(R.id.progressBar);
        animate3();
    }

    void animate3() {
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(progressBar, "rotation", REVERSE_ROTATION, SPEED_ROTATION, FORWARD_ROTATION);
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