package com.group78.gamelogic;

/**
 * The direction enum defines the possible directions of movement in the game.
 * It is used to specify the direction in which a snake is moving or facing.
 */
public enum Direction {
    /** Represents movement or facing towards the right. */
    RIGHT,

    /** Represents movement or facing towards the left. */
    LEFT,

    /** Represents movement or facing upwards. */
    UP,

    /** Represents movement or facing downwards. */
    DOWN,

    /**
     * Represents a non-applicable direction, used specifically for enemy snakes that have died.
     * This direction indicates that the snake is no longer in motion.
     */
    NA
}
