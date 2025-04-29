package com.example.myquiztoryapp;

public class Question {
    // The actual text of the question, e.g., "Who built the Great Pyramid?"
    private final String question;

    // An array of four possible answer options for the question
    private final String[] options;

    // Index of the correct answer in the options array (starting from 0)
    private final int correctAnswerIndex;

    /**
     * Constructor that initializes a question with its text, four options,
     * and the index of the correct answer (given as 1-based index).
     */
    public Question(String question, String option1, String option2, String option3, String option4, int correctAnswerIndex) {
        this.question = question;
        // Store the four options in an array for easy access by index
        this.options = new String[] {option1, option2, option3, option4};
        // Adjust the correct answer index from 1-based to 0-based
        this.correctAnswerIndex = correctAnswerIndex - 1;
    }

    /**
     * Returns the question text so it can be shown in the UI.
     */
    public String getQuestion() {
        return question;
    }

    /**
     * Returns the array of answer options, e.g., to display as buttons.
     */
    public String[] getOptions() {
        return options;
    }

    /**
     * Returns the index of the correct answer in the options array (0-based).
     */
    public int getCorrectAnswerIndex() {
        return correctAnswerIndex;
    }

    /**
     * Returns the actual correct answer string, e.g., "Khufu".
     * This could be used for showing the correct answer after a user submits.
     */
    public String getCorrectAnswer() {
        return options[correctAnswerIndex];
    }
}

