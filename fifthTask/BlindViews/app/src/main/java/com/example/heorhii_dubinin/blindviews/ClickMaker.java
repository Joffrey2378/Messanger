package com.example.heorhii_dubinin.blindviews;

import android.app.Activity;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.view.View;

import androidx.annotation.NonNull;

import static com.example.heorhii_dubinin.blindviews.SecondActivity.LINK_COLOR;

public class ClickMaker extends ClickableSpan {

    public final Activity activity;

    public ClickMaker(Activity activity) {
        this.activity = activity;
    }

    @Override
    public void onClick(@androidx.annotation.NonNull View textView) {
    }

    @Override
    public void updateDrawState(@NonNull TextPaint ds) {
        ds.setColor(LINK_COLOR);
        ds.setUnderlineText(true);
    }
}
