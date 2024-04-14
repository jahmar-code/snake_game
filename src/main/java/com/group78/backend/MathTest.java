package com.group78.backend;

import java.util.List;

/**
 * Represents a math test, including its difficulty level and a list of questions.
 * This class is designed to manage the information related to individual math tests within the game,
 * facilitating the retrieval and manipulation of test details.
 */
public class MathTest {
    /** The difficulty level of the math test. */
    private int level;

    /** The list of questions included in the math test. */
    private List<Question> questions;

    /**
     * Constructs a new MathTest with the specified level and list of questions.
     *
     * @param level The difficulty level of the math test.
     * @param questions The list of questions that make up the math test.
     */
    public MathTest(int level, List<Question> questions) {
        this.level = level;
        this.questions = questions;
    }

    /**
     * Returns the difficulty level of the math test.
     *
     * @return The difficulty level of the test.
     */
    public int getLevel() {
        return level;
    }

    /**
     * Sets the difficulty level of the math test.
     *
     * @param level The new difficulty level for the math test.
     */
    public void setLevel(int level) {
        this.level = level;
    }

    /**
     * Returns the list of questions for the math test.
     *
     * @return A list of questions included in the math test.
     */
    public List<Question> getQuestions() {
        return questions;
    }
}


