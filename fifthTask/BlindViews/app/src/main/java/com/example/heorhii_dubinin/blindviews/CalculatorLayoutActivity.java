package com.example.heorhii_dubinin.blindviews;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;

public class CalculatorLayoutActivity extends AppCompatActivity {

    public static final String KEY_ROOT_LAYOUT = "KEY_ROOT_LAYOUT";

    public static Intent newInstance(Context context, int activityResource) {
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

//    public static final String KEY_TEXT = "KEY_TEXT";
//    public static final String KEY_COLOR = "KEY_COLOR";
//
//    public static CalculatorLayoutActivity newInstance(String text, int color) {
//        CalculatorLayoutActivity calculatorLayoutActivity = new CalculatorLayoutActivity();
//        Bundle attributes = new Bundle();
//        attributes.putString(KEY_TEXT, text);
//        attributes.putInt(KEY_COLOR, color);
//        calculatorLayoutActivity.setContentTransitionManager(R.layout.);
//        return calculatorLayoutActivity;
//    }

//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        return inflater.inflate(R.layout.background_fragment_apearence, container, false);
//    }

//    @Override
//    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//        Bundle arguments = getArguments();
//        TextView textView = view.findViewById(R.id.textV_in_fragment);
//        textView.setText(arguments.getString(KEY_TEXT));
//        View backgroundView = view.findViewById(R.id.container_view);
//        int backgroundColor = arguments.getInt(KEY_COLOR);
//        backgroundView.setBackgroundColor(backgroundColor);
//    }

//    @Override
//    public void onPause() {
//        super.onPause();
//        Bundle arguments = this.getArguments();
//        arguments.putInt(KEY_COLOR, ((ColorDrawable) this.getView().getBackground()).getColor());
//    }

}
