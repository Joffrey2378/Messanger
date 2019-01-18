package com.example.messages.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.messages.MessageModel;
import com.example.messages.R;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class MainActivity extends AppCompatActivity {
    public static final String USER_NAME = "admin";
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference messageReference = db.collection("chat");

    private MessageAdapter adapter;

    private EditText textMessage;
    private Button btnSend;

    private String userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        setupRecyclerView();

        initializeViews();

        editTextSetups();

        btnSend.setOnClickListener(this::pushMessage);
    }

    private void initializeViews() {
        textMessage = findViewById(R.id.messageEditText);
        btnSend = findViewById(R.id.sendButton);
        userName = USER_NAME;
    }

    private void pushMessage(View view) {
        MessageModel messageModel = new MessageModel(
                null,
                userName,
                textMessage.getText().toString(),
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
}
