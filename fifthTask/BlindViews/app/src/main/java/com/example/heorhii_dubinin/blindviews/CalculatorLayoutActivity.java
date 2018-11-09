package com.example.heorhii_dubinin.blindviews;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;

public class CalculatorLayoutActivity extends AppCompatActivity {

    public static final String KEY_ROOT_LAYOUT = "KEY_ROOT_LAYOUT";

    public static Intent newInstanceIntent(Context context, int activityResource) {
        Intent intent = new Intent(context, CalculatorLayoutActivity.class);
        intent.putExtra(KEY_ROOT_LAYOUT, activityResource);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        long startTime = System.currentTimeMillis();
        super.onCreate(savedInstanceState);
        setContentView(getLayoutIdFromIntent());
        long endTime = System.currentTimeMillis();
        EditText editText = findViewById(R.id.editText3);
        editText.setText("t= " + (endTime - startTime));
    }

    private int getLayoutIdFromIntent() {
        Intent intent = getIntent();
        if (intent != null) {
            int layoutId = intent.getIntExtra(KEY_ROOT_LAYOUT, -1);
            if (layoutId == -1) {
                throw new RuntimeException("No layout id was provided.");
            } else {
                return layoutId;
            }
        }
        throw new RuntimeException("No extras were provided.");
    }
}
