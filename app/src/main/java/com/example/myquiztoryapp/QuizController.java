package com.example.myquiztoryapp;

import java.util.Arrays;
import java.util.List;

public class QuizController {
    // List of all questions in the quiz
    private final List<Question> questionList;

    // Tracks which question the user is currently on
    private int currentQuestionIndex = 0;

    // Total score based on correct answers
    private int score = 0;

    // Tracks if the user has selected an answer for each question
    private boolean[] answersSelected;

    // Stores the index of the selected answer for each question (e.g., 0, 1, 2, 3)
    private int[] selectedAnswerIndices;

    // Tracks whether the selected answer was correct for each question
    private boolean[] wasCorrect;

    // List of correct answers (hardcoded for now, one for each question)
    private final String[] correctAnswers;

    /**
     * Constructor: sets up the controller using questions from the repository.
     * Also prepares arrays to track user selections and correctness.
     */
    public QuizController(QuizRepository repository) {
        this.questionList = repository.getQuestions(); // Load all quiz questions
        int questionCount = questionList.size(); // How many questions in total

        // Create arrays to track selections and correctness
        this.answersSelected = new boolean[questionCount];
        this.selectedAnswerIndices = new int[questionCount];
        this.wasCorrect = new boolean[questionCount];

        // Correct answers for each question (in the same order)
        this.correctAnswers = new String[]{
                "Khufu", "Thebes", "Akhenaten", "Hieroglyphs", "Limestone",
                "First Intermediate Period", "Jean-François Champollion",
                "Tomb for officials", "Nile", "Royal tombs for pharaohs"
        };

        // Initialize selected answers to -1 (which means no answer picked yet)
        Arrays.fill(selectedAnswerIndices, -1);
    }

    // Get the current question the user is answering
    public Question getCurrentQuestion() {
        return questionList.get(currentQuestionIndex);
    }

    // Get the index (position) of the current question
    public int getCurrentQuestionIndex() {
        return currentQuestionIndex;
    }

    // Get total number of questions in the quiz
    public int getTotalQuestions() {
        return questionList.size();
    }

    // Returns true if the user's answer for a specific question was correct
    public boolean wasAnswerCorrect(int index) {
        if (index < 0 || index >= questionList.size()) {
            return false; // Prevent errors if index is out of bounds
        }
        return wasCorrect[index];
    }

    // Same as above – checks if the question was answered correctly
    public boolean wasQuestionCorrect(int index) {
        return wasCorrect[index];
    }

    // Check if the current question is the last one
    public boolean isLastQuestion() {
        return currentQuestionIndex == questionList.size() - 1;
    }

    // Move to the next question (and check the current answer before moving)
    public void nextQuestion() {
        if (currentQuestionIndex < questionList.size() - 1) {
            checkAnswer(); // Check and update score if needed
            currentQuestionIndex++; // Move to next question
        }
    }

    // Go back to the previous question (and check answer again)
    public void previousQuestion() {
        if (currentQuestionIndex > 0) {
            checkAnswer(); // Update score if something changed
            currentQuestionIndex--; // Move back
        }
    }

    // Save the user’s selected answer for the current question
    public void selectAnswer(int buttonIndex) {
        selectedAnswerIndices[currentQuestionIndex] = buttonIndex;
        answersSelected[currentQuestionIndex] = true; // Mark that an answer was chosen
    }

    // Check if the user has selected an answer for the current question
    public boolean isAnswerSelected() {
        return answersSelected[currentQuestionIndex];
    }

    // Get the total score (how many correct answers so far)
    public int getScore() {
        return score;
    }

    // Checks if the selected answer is correct, and updates the score
    public void checkAnswer() {
        // Don't check if the user hasn't picked an answer yet
        if (!answersSelected[currentQuestionIndex]) return;

        // Get the current question and the answer the user selected
        Question currentQuestion = getCurrentQuestion();
        String selectedAnswer = currentQuestion.getOptions()[selectedAnswerIndices[currentQuestionIndex]];

        // Compare the selected answer to the correct one
        boolean isCorrect = selectedAnswer.equals(correctAnswers[currentQuestionIndex]);

        // If the answer is correct and hasn't already been counted, add a point
        if (isCorrect && !wasCorrect[currentQuestionIndex]) {
            score++;
            wasCorrect[currentQuestionIndex] = true;
        }
        // If the answer is wrong but was previously counted as correct, subtract the point
        else if (!isCorrect && wasCorrect[currentQuestionIndex]) {
            score--;
            wasCorrect[currentQuestionIndex] = false;
        }
    }
}
