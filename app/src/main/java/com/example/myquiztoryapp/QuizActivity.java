package com.example.myquiztoryapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

public class QuizActivity extends AppCompatActivity {

    // UI elements for displaying the question and interacting with the quiz
    private TextView questionText;
    private Button[] answerButtons;
    private Button previousButton, nextButton;
    private ProgressBar progressBar;

    // The controller that handles quiz logic
    private QuizController quizController;

    /**
     * This method is called when the activity starts.
     * It's responsible for setting up the UI and connecting it to the quiz logic.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz); // Load the XML layout for this screen

        // Find and connect each UI element in the layout to this Java class
        questionText = findViewById(R.id.questionText);
        answerButtons = new Button[]{
                findViewById(R.id.answer1),
                findViewById(R.id.answer2),
                findViewById(R.id.answer3),
                findViewById(R.id.answer4)
        };
        previousButton = findViewById(R.id.previousButton);
        nextButton = findViewById(R.id.nextButton);
        progressBar = findViewById(R.id.progressBar);

        // Create the repository (provides data) and controller (handles logic)
        QuizRepository repository = new QuizRepository();
        quizController = new QuizController(repository);

        // Display the first question immediately
        displayQuestion();

        // Set up what happens when a user taps an answer button
        for (int i = 0; i < answerButtons.length; i++) {
            final int buttonIndex = i;
            answerButtons[i].setOnClickListener(v -> {
                quizController.selectAnswer(buttonIndex); // Tell controller which answer was selected
                highlightSelectedAnswer(buttonIndex); // Give visual feedback to the user
                updateButtonVisibility(); // Update visibility of "Next" or "End Quiz" button
            });
        }

        // Go to the previous question when "Previous" button is clicked
        previousButton.setOnClickListener(v -> {
            quizController.previousQuestion();
            displayQuestion(); // Refresh the UI
        });

        // Go to next question or end the quiz if it's the last one
        nextButton.setOnClickListener(v -> {
            if (quizController.isLastQuestion()) {
                navigateToResultActivity(); // Move to result screen
            } else {
                quizController.nextQuestion();
                displayQuestion();
            }
        });

        // Initial check to set button states properly
        updateButtonVisibility();
    }

    /**
     * This method updates the screen to show the current question and its answer choices.
     */
    private void displayQuestion() {
        Question currentQuestion = quizController.getCurrentQuestion(); // Get question from controller
        questionText.setText(currentQuestion.getQuestion()); // Show question text

        // Show each answer option on the appropriate button
        String[] options = currentQuestion.getOptions();
        for (int i = 0; i < answerButtons.length; i++) {
            answerButtons[i].setText(options[i]);
            answerButtons[i].setBackgroundResource(R.drawable.button_background); // Reset button look
            answerButtons[i].setTextColor(ContextCompat.getColor(this, R.color.white));
        }

        // Update progress bar to reflect which question number the user is on
        progressBar.setProgress((quizController.getCurrentQuestionIndex() + 1) * 10);

        updateButtonVisibility(); // Ensure buttons match quiz state
    }

    /**
     * Highlights the button that the user selected to show visual confirmation.
     */
    private void highlightSelectedAnswer(int index) {
        // Reset all buttons to default style
        for (Button button : answerButtons) {
            button.setBackgroundResource(R.drawable.button_background);
        }
        // Highlight the selected one
        answerButtons[index].setBackgroundResource(R.drawable.button_correct);
    }

    /**
     * This method decides whether "Previous" and "Next"/"End Quiz" buttons should be visible,
     * and updates the text depending on quiz progress.
     */
    private void updateButtonVisibility() {
        // Only show "Previous" if not on the first question
        previousButton.setVisibility(quizController.getCurrentQuestionIndex() > 0 ? View.VISIBLE : View.GONE);

        // Change text to "End Quiz" on the last question
        nextButton.setText(quizController.isLastQuestion() ? "End Quiz" : "Next");

        // Only show "Next"/"End Quiz" if user has selected an answer
        nextButton.setVisibility(quizController.isAnswerSelected() ? View.VISIBLE : View.GONE);
    }

    /**
     * Starts the result screen (ResultActivity) and passes the final score and correctness of each question.
     */
    private void navigateToResultActivity() {
        quizController.checkAnswer();
        Intent intent = new Intent(QuizActivity.this, ResultActivity.class);
        intent.putExtra("SCORE", quizController.getScore());

        // Send whether each question was answered correctly
        for (int i = 0; i < quizController.getTotalQuestions(); i++) {
            intent.putExtra("wasCorrect_" + i, quizController.wasAnswerCorrect(i));
        }

        startActivity(intent);
        finish();
    }
}
