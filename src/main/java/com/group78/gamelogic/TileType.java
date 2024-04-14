package com.group78.gamelogic;// CHRIS

/**
 * Represents the types of entities that can occupy a tile on the game board.
 * Each tile type indicates a different state, such as being empty,
 * containing food, or being part of a snake.
 */
public enum TileType {
    /** Represents a tile that is currently unoccupied by any entity. */
    EMPTY,

    /** Represents a tile that contains food for the snake to consume. */
    FOOD,

    /** Represents a tile that is part of the user-controlled snake. */
    U_SNAKE,

    /** Represents a tile that is part of an enemy snake. */
    E_SNAKE,

    /** Represents a tile that contains an obstacle. Obstacles are static and cannot be moved through by snakes. */
    OBSTACLE
}
