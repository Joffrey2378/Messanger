package com.example.heorhii_dubinin.blindviews;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class SecondActivity extends AppCompatActivity {

    public static final int SPANABLE_ORANGE = 0xffff9800;
    public static final int SPANABLE_INDIGO = 0xff3949ab;
    public static final int SPANABLE_VIOLET = 0xff9c27b0;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        final LinearLayout linLayout = new LinearLayout(this);
        linLayout.setOrientation(LinearLayout.VERTICAL);

        LinearLayout.LayoutParams linLayoutParam = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        setContentView(linLayout, linLayoutParam);

        LinearLayout.LayoutParams lpView = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        textView = new TextView(this);
        textView.setText(R.string.rainbow);
        textView.setLayoutParams(lpView);
        linLayout.addView(textView);

        final SpannableString spannableColor = new SpannableString(textView.getText());
        spannableColor.setSpan(new ForegroundColorSpan(Color.RED), 25, 28, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableColor.setSpan(new ForegroundColorSpan(SPANABLE_ORANGE), 29, 36, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableColor.setSpan(new ForegroundColorSpan(Color.YELLOW), 38, 44, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableColor.setSpan(new ForegroundColorSpan(Color.GREEN), 46, 51, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableColor.setSpan(new ForegroundColorSpan(Color.BLUE), 53, 57, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableColor.setSpan(new ForegroundColorSpan(SPANABLE_INDIGO), 59, 65, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableColor.setSpan(new ForegroundColorSpan(SPANABLE_VIOLET), 67, 73, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView.setText(spannableColor);

        final SpannableString spannableString = new SpannableString(textView.getText());
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View textView) {
                startActivity(new Intent(SecondActivity.this, FrameLayoutActivity.class));
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setUnderlineText(true);
            }
        };
        spannableString.setSpan(clickableSpan, 83, 93, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        SpannableString ss = new SpannableString("Show me Splash please.");
        ClickableSpan clickableSpan2 = new ClickableSpan() {
            @Override
            public void onClick(View textView) {
                startActivity(new Intent(SecondActivity.this, SplashActivity.class));
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setUnderlineText(false);
            }
        };
        ss.setSpan(clickableSpan, 8, 14, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);


        TextView textView = new TextView(this);
        textView.setText(R.string.rainbow);
        textView.setLayoutParams(lpView);
        linLayout.addView(textView);
        textView.setText(ss);
        textView.setMovementMethod(LinkMovementMethod.getInstance());
//        TextView textView = (TextView) findViewById(R.id.hello);
//        textView.setHighlightColor(Color.TRANSPARENT);
    }
}
