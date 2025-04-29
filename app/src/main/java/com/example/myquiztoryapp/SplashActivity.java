package com.example.myquiztoryapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.WindowManager;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
public class SplashActivity extends AppCompatActivity {
    private ProgressBar progressBar;
    private boolean isDestroyed = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("QuiztoryApp", "SplashActivity started");

        // Initialize views before setting content view
        progressBar = new ProgressBar(this, null, android.R.attr.progressBarStyleHorizontal);

        setContentView(R.layout.activity_splash);
        Log.d("QuiztoryApp", "Splash layout inflated");

        // Make activity full screen
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
        );
        Log.d("QuiztoryApp", "Fullscreen flags set");

        // Initialize progress bar
        progressBar = findViewById(R.id.progressBar);
        progressBar.setMax(100);
        progressBar.setProgress(0);
        Log.d("QuiztoryApp", "Progress bar initialized");

        // Start loading simulation
        simulateLoading();
    }

    private void simulateLoading() {
        final int[] progress = {0};
        final Handler handler = new Handler(Looper.getMainLooper());
        Log.d("QuiztoryApp", "Starting loading simulation");

        handler.post(new Runnable() {
            @Override
            public void run() {
                if (!isDestroyed && progress[0] <= 100) {
                    progressBar.setProgress(progress[0]);
                    progress[0] += 2;
                    Log.d("QuiztoryApp", "Progress updated: " + progress[0]);

                    handler.postDelayed(this, 30);
                } else if (!isDestroyed) {
                    Log.d("QuiztoryApp", "Loading complete, starting MainActivity");
                    Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(intent);
                    Log.d("QuiztoryApp", "MainActivity started");
                    finish();
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        isDestroyed = true;
        Log.d("QuiztoryApp", "SplashActivity destroyed");
    }
}

