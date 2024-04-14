package com.group78.gamelogic;

/**
 * The GameCompletionListener interface defines a listener for game completion events.
 * Implementations of this interface can be used to receive notifications when a game ends,
 * along with the outcome of the game.
 */
public interface GameCompletionListener {
    /**
     * Invoked when the game is complete. This method provides information on the outcome of the game,
     * indicating whether the user won or lost.
     *
     * @param userWon Indicates if the user won the game. True if the user won, false otherwise.
     * @param userLost Indicates if the user lost the game. True if the user lost, false otherwise.
     */
    void onGameComplete(boolean userWon, boolean userLost);
}
