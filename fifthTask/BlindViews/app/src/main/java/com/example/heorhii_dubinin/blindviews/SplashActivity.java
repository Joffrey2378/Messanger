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
    public static final int ANIMATION_ANGLE_PARAM1 = 0;
    public static final int ANIMATION_ANGLE_PARAM2 = 350;
    public static final int ANIMATION_ANGLE_PARAM3 = 700;

    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_progressbar);

        progressBar = findViewById(R.id.progressBar);
        animate();
    }

    void animate() {
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(progressBar, "rotation",
                ANIMATION_ANGLE_PARAM1, ANIMATION_ANGLE_PARAM2, ANIMATION_ANGLE_PARAM3);
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