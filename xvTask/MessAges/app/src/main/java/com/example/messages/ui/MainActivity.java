package com.example.messages.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.messages.MessageModel;
import com.example.messages.R;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
    public static final String USER_NAME = "anonymous";
    private static final int RC_SIGN_IN = 1;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference messageReference = db.collection("chat");
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private FirebaseAuth.AuthStateListener authStateListener;

    private MessageAdapter adapter;

    private EditText textMessage;
    private Button btnSend;
    private Button btnLogOut;
    private Button btnLocation;

    private String userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupRecyclerView();

        initializeViews();

        editTextSetups();

        btnLogOut.setOnClickListener(this::logOut);

        btnSend.setOnClickListener(this::pushMessage);

        btnLocation.setOnClickListener(this::getLocationInfo);

        initializeAuthStateListener();
    }

    private void getLocationInfo(View view) {
        Toast.makeText(this, "You are locating", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(this, BasicLocationActivity.class));
    }

    private void logOut(View view) {
        Toast.makeText(this, "You are signing out", Toast.LENGTH_SHORT).show();
        AuthUI.getInstance().signOut(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            if (resultCode == RESULT_OK) {
                Toast.makeText(this, "Signed in!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Sign in canceled", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }

    private void initializeAuthStateListener() {
        authStateListener = firebaseAuth -> {
            FirebaseUser user = firebaseAuth.getCurrentUser();
            if (user != null) {
                onSignedInInitialize(user.getDisplayName());
            } else {
                onSignedOutCleanup();
                startActivityForResult(
                        AuthUI.getInstance()
                                .createSignInIntentBuilder()
                                .setAvailableProviders(Arrays.asList(
                                        new AuthUI.IdpConfig.GoogleBuilder().build(),
                                        new AuthUI.IdpConfig.EmailBuilder().build(),
                                        new AuthUI.IdpConfig.PhoneBuilder().build()))
                                .build(),
                        RC_SIGN_IN);
            }
        };
    }

    private void onSignedOutCleanup() {
        userName = USER_NAME;

    }

    private void onSignedInInitialize(String username) {
        userName = username;
    }

    @Override
    protected void onPause() {
        super.onPause();
        firebaseAuth.removeAuthStateListener(authStateListener);
    }

    @Override
    protected void onResume() {
        super.onResume();
        firebaseAuth.addAuthStateListener(authStateListener);
    }

    private void initializeViews() {
        textMessage = findViewById(R.id.message_editText);
        btnSend = findViewById(R.id.send_button);
        btnLogOut = findViewById(R.id.log_out);
        btnLocation = findViewById(R.id.test_button);
        userName = USER_NAME;
    }

    private void pushMessage(View view) {
        MessageModel messageModel = new MessageModel(
                null,
                userName,
                textMessage.getText().toString(),
                null,
                null);
        messageReference.add(messageModel);

        textMessage.setText("");
    }

    private void editTextSetups() {
        textMessage.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.toString().trim().length() > 0) {
                    btnSend.setEnabled(true);
                } else {
                    btnSend.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void setupRecyclerView() {
        Query query = messageReference
                .orderBy("timestamp", Query.Direction.ASCENDING)
                .limit(50);

        FirestoreRecyclerOptions<MessageModel> options = new FirestoreRecyclerOptions
                .Builder<MessageModel>()
                .setQuery(query, MessageModel.class)
                .build();

        adapter = new MessageAdapter(options);

        RecyclerView recyclerView = findViewById(R.id.recycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

}
