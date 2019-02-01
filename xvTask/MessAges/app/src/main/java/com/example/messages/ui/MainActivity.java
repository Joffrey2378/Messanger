package com.example.messages.ui;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.messages.BuildConfig;
import com.example.messages.MessageModel;
import com.example.messages.R;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.messaging.FirebaseMessaging;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    public static final String USER_NAME = "anonymous";
    private static final String TAG = MainActivity.class.getSimpleName();
    private static final int RC_SIGN_IN = 1;
    private static final int REQUEST_PERMISSIONS_REQUEST_CODE = 34;
    public static final String PATH_HTTPS = "https";
    public static final String PATH_AUTHORITY = "www.google.com";
    public static final String PATH_SEGMENT = "maps";
    public static final String QUERY_PARAMETER_API = "api";
    public static final String QUERY_PARAMETER_VALUE = "1";
    public static final String PARAMETER_QUERY = "query";
    public static final String PATH_SEARCH = "search";
    public static final String EMPTY_STRING = "";

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference messageReference = db.collection("chat");
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private FirebaseAuth.AuthStateListener authStateListener;

    protected Location lastLocation;
    private FusedLocationProviderClient fusedLocationClient;
    private double latitude;
    private double longitude;
    private String locationCoordinates;
    private String userLocation;

    private MessageAdapter adapter;

    private EditText textMessage;
    private Button btnSend;
    private Button btnLogOut;

    private String userName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupRecyclerView();
        initializeViews();
        editTextSetups();
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        buttonClickers();
        initializeAuthStateListener();
        checkIfBundleExists(savedInstanceState);
        notificationChannelSettings();
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

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView,
                                  @NonNull RecyclerView.ViewHolder viewHolder,
                                  @NonNull RecyclerView.ViewHolder viewHolder1) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
                adapter.deleteItem(viewHolder.getAdapterPosition());
            }
        }).attachToRecyclerView(recyclerView);

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView,
                                  @NonNull RecyclerView.ViewHolder viewHolder,
                                  @NonNull RecyclerView.ViewHolder viewHolder1) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
                userLocation = adapter.getLocationFieldFromFirebaseObject(
                        viewHolder.getAdapterPosition());
                Log.d(TAG, "onSwiped: userLocation " + userLocation);
                Uri.Builder positionsBuilder = new Uri.Builder()
                        .scheme(PATH_HTTPS)
                        .authority(PATH_AUTHORITY)
                        .appendPath(PATH_SEGMENT)
                        .appendPath(PATH_SEARCH)
                        .appendPath(EMPTY_STRING)
                        .appendQueryParameter(QUERY_PARAMETER_API, QUERY_PARAMETER_VALUE)
                        .appendQueryParameter(PARAMETER_QUERY, userLocation);

                startActivity(new Intent(Intent.ACTION_VIEW, positionsBuilder.build()));
            }
        }).attachToRecyclerView(recyclerView);
    }

    private void initializeViews() {
        textMessage = findViewById(R.id.message_editText);
        btnSend = findViewById(R.id.send_button);
        btnLogOut = findViewById(R.id.log_out);
        userName = USER_NAME;
    }

    private void editTextSetups() {
        textMessage.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                btnSend.setEnabled(charSequence.toString().trim().length() > 0);
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
    }

    private void buttonClickers() {
        btnSend.setOnClickListener(this::pushMessage);
        btnLogOut.setOnClickListener(this::logOut);
    }

    private void pushMessage(View view) {
        String exactAddress = getAddress(latitude, longitude);
        MessageModel messageModel = new MessageModel(
                null,
                userName,
                textMessage.getText().toString(),
                null,
                exactAddress);
        messageReference.add(messageModel);
        textMessage.setText("");
    }

    private String getAddress(double latitude, double longitude) {
        StringBuilder result = new StringBuilder();
        try {
            Geocoder geocoder = new Geocoder(this, Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
            if (addresses.size() > 0) {
                Address address = addresses.get(0);
                result.append(address.getSubThoroughfare()).append(", ");
                result.append(address.getLocality()).append(", ");
                result.append(address.getCountryName());
            }
        } catch (IOException e) {
            Log.e("tag", e.getMessage());
        }
        return result.toString();
    }

    private void logOut(View view) {
        Toast.makeText(this, "You are signing out", Toast.LENGTH_SHORT).show();
        AuthUI.getInstance().signOut(this);
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

    private void checkIfBundleExists(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            Log.d(TAG, "Subscribing to messages topic");
            subscribeTopic();
        }
    }

    private void subscribeTopic() {
        FirebaseMessaging.getInstance().subscribeToTopic("chat")
                .addOnCompleteListener(task -> {
                    String msg = getString(R.string.msg_subscribed);
                    if (!task.isSuccessful()) {
                        msg = getString(R.string.msg_subscribe_failed);
                    }
                    Log.d(TAG, msg);
                    Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
                });
    }

    private void notificationChannelSettings() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String channelId = getString(R.string.default_notification_channel_id);
            String channelName = getString(R.string.default_notification_channel_name);
            NotificationManager notificationManager =
                    getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(new NotificationChannel(channelId,
                    channelName, NotificationManager.IMPORTANCE_LOW));
        }

        if (getIntent().getExtras() != null) {
            for (String key : getIntent().getExtras().keySet()) {
                Object value = getIntent().getExtras().get(key);
                Log.d(TAG, "Key: " + key + " Value: " + value);
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();

        if (!checkPermissions()) {
            requestPermissions();
        } else {
            getLastLocation();
        }
    }

    private boolean checkPermissions() {
        int permissionState = ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION);
        return permissionState == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermissions() {
        boolean shouldProvideRationale =
                ActivityCompat.shouldShowRequestPermissionRationale(this,
                        Manifest.permission.ACCESS_COARSE_LOCATION);

        if (shouldProvideRationale) {
            Log.i(TAG, "Displaying permission rationale to provide additional context.");

            showSnackbar(R.string.permission_rationale, android.R.string.ok,
                    view -> startLocationPermissionRequest());

        } else {
            Log.i(TAG, "Requesting permission");
            startLocationPermissionRequest();
        }
    }

    private void showSnackbar(final int mainTextStringId, final int actionStringId,
                              View.OnClickListener listener) {
        Snackbar.make(findViewById(android.R.id.content),
                getString(mainTextStringId),
                Snackbar.LENGTH_INDEFINITE)
                .setAction(getString(actionStringId), listener).show();
    }

    private void startLocationPermissionRequest() {
        ActivityCompat.requestPermissions(MainActivity.this,
                new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                REQUEST_PERMISSIONS_REQUEST_CODE);
    }

    @SuppressLint("MissingPermission")
    private void getLastLocation() {
        fusedLocationClient.getLastLocation()
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful() && task.getResult() != null) {
                        lastLocation = task.getResult();
                        latitude = Double.parseDouble(String.format(Locale.ENGLISH,
                                "%f",
                                lastLocation.getLatitude()));
                        longitude = Double.parseDouble(String.format(Locale.ENGLISH,
                                "%f",
                                lastLocation.getLongitude()));
                        locationCoordinates = latitude + ", " + longitude;
                    } else {
                        Log.w(TAG, "getLastLocation:exception", task.getException());
                        showSnackbar(getString(R.string.no_location_detected));
                    }
                });
    }

    private void showSnackbar(final String text) {
        View container = findViewById(R.id.main_activity_container);
        if (container != null) {
            Snackbar.make(container, text, Snackbar.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        firebaseAuth.addAuthStateListener(authStateListener);
    }

    @Override
    protected void onPause() {
        super.onPause();
        firebaseAuth.removeAuthStateListener(authStateListener);
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

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        Log.i(TAG, "onRequestPermissionResult");
        if (requestCode == REQUEST_PERMISSIONS_REQUEST_CODE) {
            if (grantResults.length <= 0) {
                Log.i(TAG, "User interaction was cancelled.");
            } else if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLastLocation();
            } else {
                showSnackbar(R.string.permission_denied_explanation, R.string.settings,
                        view -> {
                            Intent intent = new Intent();
                            intent.setAction(
                                    Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                            Uri uri = Uri.fromParts("package",
                                    BuildConfig.APPLICATION_ID, null);
                            intent.setData(uri);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        });
            }
        }
    }
}
