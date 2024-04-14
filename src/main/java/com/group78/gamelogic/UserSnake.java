package com.group78.gamelogic;// CHRIS

import java.util.LinkedList;

/**
 * This class represents a user-controlled snake.
 * It manages the snake's movement, growth, and checks for the validity of each snake move.
 */
public class UserSnake {
    /** The first Tile of the snake which represents the head of the linked list that represents the snake. */
    private Tile head;

    /** The last Tile of the snake which represents the tail of the linked list that represents the snake. */
    private Tile tail;

    /** A list of Tiles currently occupied by the snake represented in a linked list data structure. */
    private LinkedList<Tile> currentTiles;

    /** The game board on which the snake moves. */
    private Board board;

    /** The current direction of the snake's movement. */
    private Direction dir;

    /**
     * Constructs a new UserSnake object.
     *
     * @param board The game board on which the snake exists.
     * @param startingTiles The initial tiles occupied by the snake represented in a linked list data structure.
     * @param startingDir The initial direction of the snake's movement.
     */
    public UserSnake(Board board, LinkedList<Tile> startingTiles, Direction startingDir) {
        this.board = board;
        this.currentTiles = startingTiles;
        this.head = this.currentTiles.getFirst();
        this.tail = this.currentTiles.getLast();
        this.dir = startingDir;
    }

    /**
     * Checks if the proposed direction of movement is valid.
     * A direction is considered invalid if it directly opposes the current movement direction.
     *
     * @param dir The new direction to be validated.
     * @return true if the direction is valid, false otherwise.
     */
    private boolean isValidDir(Direction dir) {
        if (dir == Direction.UP && this.dir != Direction.DOWN)
            return true;
        if (dir == Direction.DOWN && this.dir != Direction.UP)
            return true;
        if (dir == Direction.LEFT && this.dir != Direction.RIGHT)
            return true;
        if (dir == Direction.RIGHT && this.dir != Direction.LEFT)
            return true;

        return false;
    }

    /**
     * Determines the new head position of the snake based on the proposed direction.
     *
     * @param dir The direction in which the snake intends to move.
     * @return The new head tile if the position is valid, otherwise null.
     * @throws Exception If an invalid direction is provided.
     */
    private Tile getNewHead(Direction dir) throws Exception{
        int newX = this.head.getTileX();
        int newY = this.head.getTileY();

        switch (dir) {
            case UP -> newY++;
            case DOWN -> newY--;
            case RIGHT -> newX++;
            case LEFT -> newX--;
            default -> throw new Exception("Snake: Invalid Direction ");
        }
        if (!this.board.isValidPos(newX, newY))
            return null;

        return this.board.getMap()[newX][newY];
    }

    /**
     * Moves the snake in the current direction.
     *
     * @return true if the move is successful, false if the move is invalid, such as moving into an invalid tile.
     * @throws Exception If there's an issue determining the new head position.
     */
    public boolean move() throws Exception {
        Tile newHead = getNewHead(this.dir);

        if (newHead == null) {
            System.out.println("new head is null");
            this.board.setUserDead(true);
            return false;
        }

        if (newHead.getType() != TileType.FOOD) {
            this.tail.clear();
            this.currentTiles.removeLast();
            this.tail = currentTiles.getLast();
        }

        newHead.setUserSnake();

        this.head = newHead;
        this.currentTiles.addFirst(newHead);


        return true;
    }

    /**
     * Moves the snake in the specified direction and if the direction is not valid then continue moving in the current direction.
     *
     * @param curr_dir The direction in which the snake should move.
     * @return true if the move is successful and valid, false otherwise.
     * @throws Exception If there's an issue determining the new head position.
     */
    public boolean move(Direction curr_dir) throws Exception {
        if (isValidDir(curr_dir)) {
            this.dir = curr_dir;
            return this.move();
        }
        return this.move();
    }

    /**
     * Gets the current size of the snake.
     *
     * @return The number of tiles occupied by the snake.
     */
    public int getSnakeSize() {
        return this.currentTiles.size();
    }

    /**
     * Retrieves the current head tile of the snake.
     *
     * @return The tile object that represents the head of the snake.
     */
    public Tile getHead() {
        return head;
    }
}
