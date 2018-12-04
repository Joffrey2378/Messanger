package com.example.heorhii_dubinin.blindviews;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.view.View;

import static com.example.heorhii_dubinin.blindviews.SecondActivity.LINK_COLOR;

public class ClickMaker extends ClickableSpan {

    private Context context;
    private Intent intent;

    public ClickMaker(Context context, Intent intent) {
        this.context = context;
        this.intent = intent;
    }

    public ClickMaker(Context context, Class<? extends Activity> activityToGo) {
        this.context = context;
        this.intent = new Intent(context, activityToGo);
    }

    @Override
    public void onClick(View textView) {
        this.context.startActivity(intent);
    }

    @Override
    public void updateDrawState(TextPaint ds) {
        ds.setColor(LINK_COLOR);
        ds.setUnderlineText(true);
    }
}
