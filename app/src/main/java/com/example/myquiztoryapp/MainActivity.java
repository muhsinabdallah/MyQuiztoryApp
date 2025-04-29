package com.example.myquiztoryapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements SharedPreferences.OnSharedPreferenceChangeListener {

    private TextView highestScoreText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize views
        highestScoreText = findViewById(R.id.highestScoreText);
        Button startQuizButton = findViewById(R.id.startQuizButton);

        // Initialize shared preferences listener
        SharedPreferences sharedPreferences = getSharedPreferences("QUIZ_PREFERENCES", MODE_PRIVATE);
        sharedPreferences.registerOnSharedPreferenceChangeListener(this);

        // Initial score display
        updateHighestScore();

        // Set up button click listener
        startQuizButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, QuizActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Unregister the preference change listener
        getSharedPreferences("QUIZ_PREFERENCES", MODE_PRIVATE)
                .unregisterOnSharedPreferenceChangeListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Ensure the score is up-to-date when returning to MainActivity
        updateHighestScore();
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        // Update score whenever HIGHEST_SCORE preference changes
        if (key.equals("HIGHEST_SCORE")) {
            updateHighestScore();
        }
    }

    private void updateHighestScore() {
        // Access the same shared preferences file
        SharedPreferences sharedPreferences = getSharedPreferences("QUIZ_PREFERENCES", MODE_PRIVATE);
        // Get the latest highest score stored from ResultActivity
        int highestScore = sharedPreferences.getInt("HIGHEST_SCORE", 0);
        // Display it on the homepage
        highestScoreText.setText(String.format("Highest Score %d", highestScore));
    }
}