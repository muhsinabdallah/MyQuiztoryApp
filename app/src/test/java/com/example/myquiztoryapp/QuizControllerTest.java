package com.example.myquiztoryapp;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;


public class QuizControllerTest {
    private QuizController controller;

    @Before
    public void setUp() {
        // Create repository and controller before each test
        QuizRepository repository = new QuizRepository();
        controller = new QuizController(repository);
    }

    @Test
    public void testCorrectAnswerIncreasesScore() {
        // Select the correct answer for question 1 (Khufu is option 1, index 1)
        controller.selectAnswer(1);  // "Khufu" is at index 1 in options
        controller.checkAnswer();

        assertEquals(1, controller.getScore());  // Should now have 1 point
    }

    @Test
    public void testWrongAnswerDoesNotIncreaseScore() {
        controller.selectAnswer(0);  // "Djoser" is index 0, which is wrong for Q1
        controller.checkAnswer();

        assertEquals(0, controller.getScore());  // Should still be 0
    }

    @Test
    public void testScoreDoesNotDoubleCountCorrectAnswer() {
        controller.selectAnswer(1);  // Correct answer for Q1
        controller.checkAnswer();
        controller.selectAnswer(1);  // Select again
        controller.checkAnswer();

        assertEquals(1, controller.getScore());  // Still only 1 point
    }

}







