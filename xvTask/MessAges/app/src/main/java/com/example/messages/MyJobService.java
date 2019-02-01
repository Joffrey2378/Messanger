package com.example.messages;

import android.annotation.SuppressLint;
import android.util.Log;

import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.JobService;

@SuppressLint("Registered")
public class MyJobService extends JobService {
    private static final String TAG = "MyJobService";


    @Override
    public boolean onStartJob(JobParameters job) {
        Log.d(TAG, "Performing long running task in scheduled job");
        return false;
    }

    @Override
    public boolean onStopJob(JobParameters job) {
        return false;
    }
}
