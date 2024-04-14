package com.group78.gamelogic;


import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Represents the game board for the math snake game.
 * This class handles the logic for the playing area including snake movement,
 * food spawning, obstacle creation, and board size based on difficulty level.
 */
public class Board {
    /** The width of the board in tiles. */
    private int width;

    /** The height of the board in tiles. */
    private int height;

    /** A two-dimensional array representing the map of the board where the game is played. */
    private Tile[][] map;

    /** An array holding all enemy snakes present on the board. */
    private EnemySnake[] enemySnakes;

    /** The player's snake controlled by the user. */
    private UserSnake userSnake;

    /** Flag to determine if the player's snake is dead. */
    private boolean isUserDead;

    /** The number of enemy snakes that have been killed. */
    private int numEnemiesKilled;

    /** The default size for the player's snake when starting the game. */
    private final int DEFAULT_SNAKE_SIZE = 5;

    /** The default direction for the player's snake when the game starts. */
    private final Direction DEFAULT_DIR = Direction.UP;

    /** The starting X-coordinate for the player's snake on the board. */
    private int snakeStartingX;

    /** The number of enemy snakes to spawn on the board. */
    private int numbEnemySnakes;

    /** The number of obstacles to spawn on the board. */
    private int numbObstacles;

    /** The number of food items to spawn on the board. */
    private int numFoods;

    /** The current level of the game, which affects the difficulty and board parameters. */
    private int level;

    /**
     * Constructs a new Board object for a specified level.
     * It sets up the game map with initial parameters, spawns the user snake, enemy snakes, obstacles, and food.
     *
     * @param level The level of difficulty for the board.
     * @throws Exception If there is an error setting up the board.
     */
    public Board(int level) throws Exception {
        this.level = level-1;
        setLevelParameters();
        this.map = new Tile[this.width][this.height];
        this.snakeStartingX = (width - 1) / 2;
        this.isUserDead = false;
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                this.map[i][j] = new Tile(TileType.EMPTY, i, j);
            }
        }

        this.spawnUserSnake();
        spawnEnemySnake(numbEnemySnakes);
        for (int i = 0; i < numbObstacles; i++) {
            this.spawnObstacle();
        }

        for (int i = 0; i < this.numFoods; i++) {
            this.spawnFood();
        }
    }
    public Board(int width, int height,int numFoods, int numbObstacles,int numbEnemySnakes,int snakeStartingX) throws Exception {
        this.level = 100;
        this.height=height;
        this.width=width;
        this.numFoods=numFoods;
        this.numbObstacles=numbObstacles;
        this.snakeStartingX=snakeStartingX;
        this.map = new Tile[this.width][this.height];
        this.isUserDead = false;
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                this.map[i][j] = new Tile(TileType.EMPTY, i, j);
            }
        }
        this.spawnUserSnake();
        spawnEnemySnake(numbEnemySnakes);
        for (int i = 0; i < numbObstacles; i++) {
            this.spawnObstacle();
        }

        for (int i = 0; i < this.numFoods; i++) {
            this.spawnFood();
        }
    }

    /**
     * <p>
     * Sets Setup Parameters Based on level
     * </p><br>
     * Stores level parameter database
     * and sets it based on level.
     * this is hard coded, so it can not be changed by user.
     * <br>
     * Sets: number of Foods, width & height of board, number of Enemy Snakes, number of Obstacles
     */
    private void setLevelParameters() {
        /*Level DataBase
         *  index +1 = level
         *  in [level]:
         *      [0] = food
         *      [1] = EnemySnakes
         *      [2] = Obstacles
         *      [3] = width
         *      [4] = height
         */

        int[][] levelDataBase = {
                {4, 1, 0, 10, 10},// level 1
                {3, 2, 0, 10, 10},// level 2
                {3, 2, 3, 10, 10},// level 3
                {2, 2, 3, 10, 10},// level 4
                {6, 3, 5, 15, 15},// level 5
                {4, 4, 5, 15, 15},// level 6
                {3, 5, 5, 15, 15},// level 7
                {4, 3, 7, 20, 20},// level 8
                {4, 4, 7, 20, 20},// level 9
                {5, 5, 9, 25, 25} // level 10
        };

        if (level > 10) {
            level = 10;
        }
        if (level<=0){
            level=1;
        }

        this.numFoods = levelDataBase[level-1][0];
        this.width = levelDataBase[level-1][3];
        this.height = levelDataBase[level-1][4];
        this.numbEnemySnakes = levelDataBase[level-1][1];
        this.numbObstacles = levelDataBase[level-1][2];

    }

    public String getBitboard() throws Exception {
        String board_str = "";
        for (int j = this.height - 1; j > -1; j--) {
            for (int i = 0; i < this.width; i++) {
                board_str += this.map[i][j].charRep() + "  ";
            }
            board_str += "\n";
        }

        return board_str;
    }

    /**
     * Spawns a single obstacle on the board at a random empty location,
     * avoiding the initial area where the user snake spawns.
     *
     * @throws Exception If there is an error spawning the obstacle.
     */
    private void spawnObstacle() throws Exception {
        ArrayList<Tile> empties = new ArrayList<Tile>();
        for (int i = 0; i < width - 1; i++) {
            for (int j = 0; j < height - 1; j++) {
                if (map[i][j].getType() == TileType.EMPTY) {
                    if(j >= this.DEFAULT_SNAKE_SIZE && j<= DEFAULT_SNAKE_SIZE+1){
                        continue;
                    }
                    empties.add(map[i][j]);
                }
            }
        }
        empties.get((int) (Math.random() * empties.size())).setObstacle();
    }


    /**
     * Spawns a specified number of enemy snakes on the board, each with a default size
     * and initial direction, distributed evenly along the width of the board.
     *
     * @param number The number of enemy snakes to spawn.
     * @throws Exception If there is an error spawning enemy snakes.
     */
    private void spawnEnemySnake(int number) throws Exception {
        this.enemySnakes = new EnemySnake[number];

        for (int n = 0; n < number; n++) {
            LinkedList<Tile> snakeTiles = new LinkedList<>();
            for (int i = this.DEFAULT_SNAKE_SIZE - 1; i > -1; i--) {
                Tile currTile = this.map[(width - 1) * (n % 2) + 2 * (n / 2) * ((int) Math.pow(-1, n))][i];
                currTile.setEnemySnake();
                snakeTiles.add(currTile);
            }
            enemySnakes[n] = new EnemySnake(this, snakeTiles, Direction.UP, 10*level);
        }
    }

    /**
     * Checks if all enemy snakes on the board are dead.
     *
     * @return true if all enemy snakes are dead, false otherwise.
     */
    public boolean isEnemiesDead() {
        for (int n = 0; n < enemySnakes.length; n++) {
            if (!enemySnakes[n].isDead()) {
                return false;
            }
        }
        return true;
    }

    /**
     * Updates the count of how many enemy snakes have been killed on the board.
     */
    public void updateEnemySnakeDeathCount() {
        int count = 0;
        for (int n = 0; n < enemySnakes.length; n++) {
            if (enemySnakes[n].isDead()) {
                count++;
            }
        }

        this.numEnemiesKilled = count;
    }


    /**
     * Spawns a food item in a random empty tile on the board.
     * Ensures that the food is not placed on a tile already occupied by the snake or an obstacle.
     *
     * @throws Exception If there is an error in finding an empty tile or spawning food.
     */
    private void spawnFood() throws Exception {
        ArrayList<Tile> empties = new ArrayList<Tile>();
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if (map[i][j].getType() == TileType.EMPTY) {
                    empties.add(map[i][j]);
                }
            }
        }
        if (!(empties.size()<=0)) {
            empties.get((int) (Math.random() * empties.size())).setFood();
        }
    }


    /**
     * Spawns the user-controlled snake on the board.
     * The snake is placed vertically in the center of the board starting at the default snake size.
     *
     * @throws Exception If there is an error in placing the snake on the board.
     */
    private void spawnUserSnake() throws Exception {
        LinkedList<Tile> snakeTiles = new LinkedList<>();
        for (int i = this.DEFAULT_SNAKE_SIZE - 1; i > -1; i--) {
            Tile currTile = this.map[this.snakeStartingX][i];
            currTile.setUserSnake();
            snakeTiles.add(currTile);
        }

        this.userSnake = new UserSnake(this, snakeTiles, this.DEFAULT_DIR);
    }

    /**
     * Calculates how many food items the user snake has eaten based on its size and the default size.
     *
     * @return The number of food items eaten by the user snake.
     */
    public int userFoodAte() {
        return userSnake.getSnakeSize() - DEFAULT_SNAKE_SIZE;
    }

    /**
     * Gets the size of the user-controlled snake.
     *
     * @return The number of tiles currently occupied by the user snake.
     */
    public int getUserSnakeSize() {
        return this.userSnake.getSnakeSize();
    }


    /**
     * Attempts to move all snakes on the board in their current directions.
     * Updates the state of the board based on the result of the moves.
     *
     * @param dir The direction in which the user snake should move.
     * @return true if all snakes move successfully, false if the user snake moves into an invalid tile.
     */
    public boolean moveSnakes(Direction dir) {
        try {// returns false if out of bounds
            for (int n = 0; n < enemySnakes.length; n++) {
                enemySnakes[n].move();
            }
            this.updateEnemySnakeDeathCount();
            return this.userSnake.move(dir);
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }

    /**
     * Updates the board by checking the number of food items.
     * If there are fewer food items than required, spawns additional food until the required number is met.
     *
     * @throws Exception If there is an error in spawning additional food.
     */
    public void updateFood() throws Exception {
        int foodCounter = 0;
        for (int i = 0; i < width - 1; i++) {
            for (int j = 0; j < height - 1; j++) {
                if (map[i][j].getType() == TileType.FOOD) {
                    foodCounter++;
                }
            }
        }

        if (foodCounter < this.numFoods) {
            spawnFood();
            updateFood();
        }
    }

    /**
     * Retrieves the tile at the specified coordinates on the board.
     *
     * @param x The x-coordinate of the tile to retrieve.
     * @param y The y-coordinate of the tile to retrieve.
     * @return The tile at the specified location, or null if the coordinates are out of bounds.
     */

    public Tile getTile(int x, int y) {
        if (x >= 0 && x < width && y >= 0 && y < height) {
            return map[x][y];
        }
        return null;
    }

    /**
     * Checks if the specified position is a valid tile within the bounds of the board.
     *
     * @param x The x-coordinate of the tile to check.
     * @param y The y-coordinate of the tile to check.
     * @return true if the position is valid, false otherwise.
     */
    public boolean isValidPos(int x, int y) {
        if (x < this.width && y < this.height && y >= 0 && x >= 0)
            return true;
        return false;
    }

    /**
     * Locates the head tiles of all snakes on the board, including the user snake and enemy snakes.
     *
     * @return An ArrayList of tiles where each tile is the head of a snake on the board.
     */
    public ArrayList<Tile> locateHeads() {
        ArrayList<Tile> heads = new ArrayList<>();
        heads.add(userSnake.getHead());
        Tile head;
        for (int i = 0; i < enemySnakes.length; i++) {
            head = enemySnakes[i].getHead();
            if (head != null) {
                heads.add(head);
            }
        }
        return heads;
    }

    /**
     * Retrieves the map of tiles representing the game board.
     *
     * @return A two-dimensional array of Tile objects.
     */
    public Tile[][] getMap() {
        return map;
    }

    /**
     * Retrieves the height of the game board.
     *
     * @return The height of the board in tiles.
     */

    public int getHeight() {
        return this.height;
    }

    /**
     * Retrieves the width of the game board.
     *
     * @return The width of the board in tiles.
     */
    public int getWidth() {
        return this.width;
    }

    /**
     * Sets the state indicating whether the user snake is dead.
     *
     * @param userDead A boolean indicating if the user snake is dead.
     */
    public void setUserDead(boolean userDead) {
        isUserDead = userDead;
    }

    /**
     * Checks if the user snake is dead.
     *
     * @return true if the user snake is dead, false otherwise.
     */

    public boolean isUserDead() {
        return isUserDead;
    }

    /**
     * Retrieves the number of enemy snakes killed.
     *
     * @return The count of enemy snakes killed on the board.
     */
    public int getNumEnemiesKilled() {
        return numEnemiesKilled;
    }
}