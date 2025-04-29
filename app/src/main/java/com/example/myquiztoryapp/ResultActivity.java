package com.example.myquiztoryapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;


public class ResultActivity extends AppCompatActivity {
    private TableLayout feedbackTable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        // Initialize views
        TextView messageTextView = findViewById(R.id.messageTextView);
        TextView highestScoreTextView = findViewById(R.id.highestScoreTextView);
        TextView currentScoreTextView = findViewById(R.id.currentScoreTextView);
        Button reattemptButton = findViewById(R.id.reattemptButton);
        Button endButton = findViewById(R.id.endButton);
        feedbackTable = findViewById(R.id.feedbackTable);

        // Get score from intent
        int currentScore = getIntent().getIntExtra("SCORE", 0);

        // Initialize shared preferences to store highest score
        SharedPreferences sharedPreferences = getSharedPreferences("QUIZ_PREFERENCES", MODE_PRIVATE);
        int highestScore = sharedPreferences.getInt("HIGHEST_SCORE", 0);

        // Update highest score if the current score is greater
        if (currentScore > highestScore) {
            highestScore = currentScore;
            sharedPreferences.edit().putInt("HIGHEST_SCORE", highestScore).apply();
            messageTextView.setText("Congratulations! You have set a new highest score.");
        } else {
            messageTextView.setText("Do better to beat the highest score.");
        }

        // Display the highest and current score
        highestScoreTextView.setText(String.valueOf(highestScore));
        currentScoreTextView.setText(String.valueOf(currentScore));

        // Display feedback table based on question correctness
        displayFeedbackTable();

        // Restart quiz on reattempt button click
        reattemptButton.setOnClickListener(v -> {
            Intent intent = new Intent(ResultActivity.this, QuizActivity.class);
            startActivity(intent);
            finish();
        });

        // Exit application on end button click
        endButton.setOnClickListener(v -> finishAffinity());
    }

    /**
     * Populates the feedback table with information on which questions were answered correctly or incorrectly.
     */
    private void displayFeedbackTable() {
        // Clear existing table rows to refresh the display
        feedbackTable.removeAllViews();

        // Add a header row for feedback table
        TableRow headerRow = new TableRow(this);
        TextView headerText = new TextView(this);
        headerText.setText("Feedback");
        headerText.setPadding(16, 16, 16, 16);
        headerText.setTextColor(Color.WHITE);
        headerRow.addView(headerText);
        feedbackTable.addView(headerRow);

        // Add separator row for better UI structure
        View separator = new View(this);
        separator.setLayoutParams(new TableRow.LayoutParams(
                TableRow.LayoutParams.MATCH_PARENT,
                2));
        separator.setBackgroundColor(ContextCompat.getColor(this, R.color.separator_color));
        feedbackTable.addView(separator);

        // Iterate through each question and display correctness
        for (int i = 0; i < 10; i++) {
            TableRow row = new TableRow(this);
            TextView feedbackText = new TextView(this);
            feedbackText.setPadding(16, 16, 16, 16);
            feedbackText.setTextColor(Color.WHITE);

            // Retrieve correctness of each question from intent extras
            boolean isCorrect = getIntent().getBooleanExtra("wasCorrect_" + i, false);
            String feedbackMessage = String.format("Question %d: %s", i + 1, isCorrect ? "Correct" : "Incorrect");

            feedbackText.setText(feedbackMessage);
            feedbackText.setBackgroundColor(ContextCompat.getColor(
                    this,
                    isCorrect ? R.color.correct_answer : R.color.incorrect_answer));

            row.addView(feedbackText);
            feedbackTable.addView(row);
        }
    }
}
