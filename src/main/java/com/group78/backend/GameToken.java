package com.group78.backend;

/**
 * Represents a token for a game session, containing user-specific data such as the username and game statistics.
 * This class is used to track and manage user progress and achievements within the game.
 */
public class GameToken {

    /** The username associated with this game token. */
    private String username;

    /** The statistics for the user, including game level, snake size, and total points. */
    private UserStats stats;

    /**
     * Constructs a GameToken with the specified username and game statistics.
     *
     * @param username The username associated with this game token.
     * @param stats The game statistics associated with this user.
     */
    public GameToken(String username, UserStats stats) {
        this.username = username;
        this.stats = stats;
    }

    /**
     * Returns the username associated with this game token.
     *
     * @return The username.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Returns the game statistics associated with this game token.
     *
     * @return The user's game statistics.
     */
    public UserStats getStats() {
        return stats;
    }

}

