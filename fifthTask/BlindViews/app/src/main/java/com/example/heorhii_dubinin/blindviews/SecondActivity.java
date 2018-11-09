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

    public static final int SPANNABLE_ORANGE = 0xffff9800;
    public static final int SPANNABLE_INDIGO = 0xff3949ab;
    public static final int SPANNABLE_VIOLET = 0xff9c27b0;
    public static final int LINK_COLOR = 0xff2196f3;

    TextView rainbowTextView;
    TextView splashTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        final LinearLayout linLayout = new LinearLayout(this);
        linLayout.setOrientation(LinearLayout.VERTICAL);

        LinearLayout.LayoutParams linLayoutParam = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        setContentView(linLayout, linLayoutParam);

        LinearLayout.LayoutParams lpView = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        rainbowTextView = new TextView(this);
        rainbowTextView.setLayoutParams(lpView);
        linLayout.addView(rainbowTextView);
        rainbowTextView.setPadding(20, 20, 20, 20);

        splashTextView = new TextView(this);
        splashTextView.setLayoutParams(lpView);
        linLayout.addView(splashTextView);
        splashTextView.setPadding(20, 50, 0, 0);

        magicString();
    }

    private void magicString() {

        rainbowTextView.setText(R.string.rainbow);

        final SpannableString spannableColor = new SpannableString(rainbowTextView.getText());
        spannableColor.setSpan(new ForegroundColorSpan(Color.RED), 25, 28, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableColor.setSpan(new ForegroundColorSpan(SPANNABLE_ORANGE), 29, 36, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableColor.setSpan(new ForegroundColorSpan(Color.YELLOW), 38, 44, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableColor.setSpan(new ForegroundColorSpan(Color.GREEN), 46, 51, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableColor.setSpan(new ForegroundColorSpan(Color.BLUE), 53, 57, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableColor.setSpan(new ForegroundColorSpan(SPANNABLE_INDIGO), 59, 65, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableColor.setSpan(new ForegroundColorSpan(SPANNABLE_VIOLET), 67, 73, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        rainbowTextView.setText(spannableColor);

        SpannableString spannableString = new SpannableString(rainbowTextView.getText());
        ClickableSpan clickableSpan1 = new ClickableSpan() {
            @Override
            public void onClick(View textView) {
                startActivity(CalculatorLayoutActivity.newInstanceIntent(SecondActivity.this, R.layout.activity_frame_layout));
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                ds.setColor(LINK_COLOR);
                ds.setUnderlineText(true);
            }
        };
        spannableString.setSpan(clickableSpan1, 105, 117, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        ClickableSpan clickableSpan2 = new ClickableSpan() {
            @Override
            public void onClick(View textView) {
                startActivity(CalculatorLayoutActivity.newInstanceIntent(SecondActivity.this, R.layout.activity_linear_layout));
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                ds.setColor(LINK_COLOR);
                ds.setUnderlineText(true);
            }
        };
        spannableString.setSpan(clickableSpan2, 119, 131, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        ClickableSpan clickableSpan3 = new ClickableSpan() {
            @Override
            public void onClick(View textView) {
                startActivity(CalculatorLayoutActivity.newInstanceIntent(SecondActivity.this, R.layout.activity_relative_layout));
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                ds.setColor(LINK_COLOR);
                ds.setUnderlineText(true);
            }
        };
        spannableString.setSpan(clickableSpan3, 133, 147, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View textView) {
                startActivity(CalculatorLayoutActivity.newInstanceIntent(SecondActivity.this, R.layout.activity_constraint_layout));
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                ds.setColor(LINK_COLOR);
                ds.setUnderlineText(true);
            }
        };
        spannableString.setSpan(clickableSpan, 149, 165, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        rainbowTextView.setText(spannableString);
        rainbowTextView.setMovementMethod(LinkMovementMethod.getInstance());

        SpannableString ss = new SpannableString("Show me Splash please.");
        ClickableSpan clickableSpan2Test = new ClickableSpan() {
            @Override
            public void onClick(View splashTextView) {
                startActivity(new Intent(SecondActivity.this, SplashActivity.class));
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                ds.setColor(LINK_COLOR);
                ds.setUnderlineText(true);
            }
        };
        ss.setSpan(clickableSpan2Test, 8, 14, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        splashTextView.setText(ss);
        splashTextView.setMovementMethod(LinkMovementMethod.getInstance());
    }
}
