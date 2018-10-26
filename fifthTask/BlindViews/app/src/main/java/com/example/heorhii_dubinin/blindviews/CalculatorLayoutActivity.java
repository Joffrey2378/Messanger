package com.example.heorhii_dubinin.blindviews;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class CalculatorLayoutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frame_layout);
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
