package com.group78.backend;

/**
 * Represents a single question within a math test, including the question text and its correct answer.
 */
public class Question {
    /** The text of the question. */
    private String question;

    /** The correct answer to the question. */
    private String answer;

    /**
     * Constructs a new Question with the specified question text and answer.
     *
     * @param question The text of the question.
     * @param answer The correct answer to the question.
     */
    public Question(String question, String answer) {
        this.question = question;
        this.answer = answer;
    }

    /**
     * Returns the text of the question.
     *
     * @return The question text.
     */
    public String getQuestion() {
        return question;
    }

    /**
     * Returns the correct answer to the question.
     *
     * @return The correct answer.
     */
    public String getAnswer() {
        return answer;
    }

}


