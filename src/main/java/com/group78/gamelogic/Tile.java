package com.group78.gamelogic;

/**
 * Represents a single tile on the game board in a snake game.
 * A tile can hold different types of entities, such as a part of the snake, food, or an obstacle.
 */
public class Tile {
    /** The type of entity currently occupying this tile. */
    private TileType type;

    /** The x-coordinate of this tile on the game board. */
    private int x;

    /** The y-coordinate of this tile on the game board. */
    private int y;

    /**
     * Constructs a Tile with the specified type and coordinates.
     *
     * @param type The type of entity this tile is initially set to.
     * @param x The x-coordinate of this tile on the game board.
     * @param y The y-coordinate of this tile on the game board.
     */
    public Tile(TileType type, int x, int y) {
        this.x = x;
        this.y = y;
        this.type = type;
    }

    /**
     * Returns the type of entity occupying this tile.
     *
     * @return The current type of this tile.
     */
    public TileType getType() {
        return type;
    }

    /**
     * Returns the x-coordinate of this tile on the game board.
     *
     * @return The x-coordinate of this tile.
     */
    public int getTileX(){
        return x;
    }

    public int getTileY() {
        return y;
    }

    /**
     *   Setters will set value if and only if the Tile is currently empty.
     **/
    public void setFood() throws Exception {
        if (this.type == TileType.EMPTY)
            this.type = TileType.FOOD;
        else
            throw new Exception("Tile: Invalid FOOD placement at (" + this.x + ", " + this.y + ")");
    }

    /**
     * Sets the tile's type to U_SNAKE (user-controlled snake) if the tile is currently empty or contains food.
     *
     * @throws Exception If attempting to place the user snake on an invalid tile.
     */
    public void setUserSnake() throws Exception{
        if (this.type == TileType.EMPTY || this.type == TileType.FOOD)
            this.type = TileType.U_SNAKE;
        else {
            System.out.println(this);
            throw new Exception("Tile: Invalid USER snake placement at (" + this.x + ", " + this.y + ")");
        }
    }

    /**
     * Sets the tile's type to E_SNAKE (enemy snake) if the tile is currently empty or contains food.
     *
     * @throws Exception If attempting to place the enemy snake on an invalid tile.
     */
    public void setEnemySnake() throws Exception {
        if (this.type == TileType.EMPTY || this.type == TileType.FOOD)
            this.type = TileType.E_SNAKE;
        else
            throw new Exception("Tile: Invalid ENEMY snake placement at (" + this.x + ", " + this.y + ")");
    }

    /**
     * Sets the tile's type to OBSTACLE if the tile is currently empty.
     *
     * @throws Exception If attempting to place an obstacle on a non-empty tile.
     */
    public void setObstacle() throws Exception{
        if (this.type == TileType.EMPTY)
            this.type = TileType.OBSTACLE;
        else
            throw new Exception("Tile: Invalid OBSTACLE  placement at (" + this.x + ", " + this.y + ")");
    }

    /**
     * Resets the tile's type to EMPTY, indicating it is no longer occupied.
     */
    public void clear(){
        this.type=TileType.EMPTY;
    }

    /**
     * Generates a string representation of the tile, including its type and coordinates for debugging purposes.
     *
     * @return A string representation of the tile.
     */
    @Override
    public String toString() {
        return "Tile{" +
                "type=" + type +
                ", x=" + x +
                ", y=" + y +
                '}';
    }

    /**
     * Generates a character representation of the tile's type for debugging purposes.
     *
     * @return A single character representing the tile's type.
     * @throws Exception If the tile type is unrecognized.
     */
    public String charRep() throws Exception{
        switch (this.type) {
            case FOOD -> {
                return "f";
            }
            case OBSTACLE -> {
                return "o";
            }
            case U_SNAKE -> {
                return "u";
            }
            case E_SNAKE -> {
                return "e";
            }
            case EMPTY -> {
                return "-";
            }
            default -> {
                throw new Exception("Tile: Invalid OBSTACLE  placement at (" + this.x + ", " + this.y + ")");
            }
        }
    }
}