package com.group78.backend;

/**
 * Represents the statistics of a user playing the game.
 */
public class UserStats {
    /** The current level of the user in the game. */
    private int level;

    /** The current size of the user's snake. This may reflect the number of segments or length. */
    private int snakeSize;

    /** The total points accumulated by the user up to the current moment. */
    private int totalPoints;

    /**
     * Constructs an instance of UserStats with specified level, snake size, and total points.
     * This constructor allows for the creation of a UserStats object with custom initial values.
     *
     * @param level The initial level of the user.
     * @param snakeSize The initial size of the user's snake.
     * @param totalPoints The initial total points accumulated by the user.
     */
    public UserStats(int level, int snakeSize, int totalPoints) {
        this.level = level;
        this.snakeSize = snakeSize;
        this.totalPoints = totalPoints;
    }

    /**
     * Returns the current level of the user.
     *
     * @return The user's current level.
     */
    public int getLevel() {
        return level;
    }

    /**
     * Sets the user's current level to the specified value.
     *
     * @param level The new level of the user.
     */
    public void setLevel(int level) {
        this.level = level;
    }

    /**
     * Returns the current size of the user's snake.
     *
     * @return The current snake size.
     */
    public int getSnakeSize() {
        return snakeSize;
    }

    /**
     * Returns the total points accumulated by the user.
     *
     * @return The user's total points.
     */
    public int getTotalPoints() {
        return totalPoints;
    }

    /**
     * Sets the user's total points to the specified value.
     *
     * @param totalPoints The new total points of the user.
     */
    public void setTotalPoints(int totalPoints) {
        this.totalPoints = totalPoints;
    }
}

