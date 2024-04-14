package com.group78.gamelogic;

import java.util.ArrayList;
import java.util.LinkedList;


public class EnemySnake {
    /** The size of the enemy snake, represented by the number of tiles it occupies. */
    private int snakeSize;

    /** The head tile of the enemy snake. */
    private Tile head;

    /** The tail tile of the enemy snake. */
    private Tile tail;

    /** A list of tiles currently occupied by the enemy snake. */
    private LinkedList<Tile> currentTiles;

    /** The game board on which the enemy snake moves. */
    private Board board;

    /** The current direction of the enemy snake's movement. */
    private Direction dir;

    /** The width of the game board. */
    private int boardWidth;

    /** The height of the game board. */
    private int boardHeight;

    /**
     * The state the enemy snake is in. States include:
     * 0 - Normal moving, looking for food, trying not to get killed.
     * 1-6 - Kill mode, each mode represents a different case.
     */
    private int state;
    /** A difficulty factor influencing the enemy snake's behavior. */
    private double diffFactor;

    /**
     * Constructs an enemy snake with specified starting tiles, direction, and difficulty.
     *
     * @param board The game board on which the snake moves.
     * @param startingTiles The initial tiles occupied by the snake.
     * @param startingDir The initial direction of the snake's movement.
     * @param difficulty The difficulty level affecting the snake's behavior.
     */
    public EnemySnake(Board board, LinkedList<Tile> startingTiles, Direction startingDir, int difficulty) {
        this.board = board;
        this.snakeSize = startingTiles.size();
        this.currentTiles = startingTiles;
        this.head = this.currentTiles.getFirst();
        this.tail = this.currentTiles.getLast();
        this.boardWidth = board.getMap().length;
        this.boardHeight = board.getMap()[0].length;
        this.dir = startingDir;
        this.state = 0;
        this.diffFactor = difficulty;
    }

    /**
     * Function that will move the snake Every time step.
     * <br><br>
     *
     * <h1>Method use find next move:</h1>
     * <p>
     * 1. Array MoveScores is created, which holds the probability of all possible moves. 50 is the default value.
     * <p>
     * MoveScores Array Set up
     * index   | Direction
     * --------|----------
     * 0       | up
     * 1       | right
     * 2       | down
     * 3       | left
     * <p>
     * Note: think of how to remember directions on compass
     * array is representation on how good the next move is 50 is nothing special, 0 is move not possible
     * </p><br>
     * 2. Remove moves that the snake can not do. ie. moves that will crash into the boundary or make a U turn
     * (go Down when heading up).
     * <br>
     * 3. See if the snake is in a position to kill another snake. If yes, and choose to kill, does a set of
     * movements that could potentially kill the other snake. See canKill() and killSnakes() for more information.
     * Otherwise, looks at 3 blocks in the remaining directions and picks the best one. See simpleAI() for more
     * information.
     * Note: Methods listed above scale the values in moveScores to shows better or worse moves.
     * <br>
     * 4. Use randIndex() to pick a random direction to move the snakes based on the probabilities given on moveScores.
     * <br>
     * 5. Move the snake to the tile. Update: currentTiles, head, Tails and the new Tile's Type. If Tile had food
     * increase Size of snake, otherwise, if is was anything but empty, the snake hase died. (snake remains on the
     * board for one frame. Gets removed by the next call of the move() function.
     * </p>
     *
     * @throws Exception
     */
    public void move() throws Exception {
        // To make sure it's not dead
        if (dir == Direction.NA) {
            if (snakeSize != 0) {
                while (!currentTiles.isEmpty()) {
                    currentTiles.getFirst().clear();
                    currentTiles.removeFirst();
                }
                snakeSize = 0;
            }
            return;
        }

        double moveScores[] = {50, 50, 50, 50};
        int X, Y;
        X = head.getTileX();
        Y = head.getTileY();
        // removing not possible moves
        if (dir == Direction.UP || Y - 1 < 0) {
            moveScores[2] = 0;
        }
        if (dir == Direction.RIGHT || X - 1 < 0) {
            moveScores[3] = 0;
        }
        if (dir == Direction.DOWN || Y + 1 >= boardHeight ) {
            moveScores[0] = 0;
        }
        if (dir == Direction.LEFT || X + 1 >= boardWidth ) {
            moveScores[1] = 0;
        }

        // The move array here should have equal chances in all possible moves. From here on No + or -
        // only change values by * factor or / factor. factor is picked by difficulty;

        if (state == 0) {
            if (canKill()) {

                // System.out.println("going to kill");
                moveScores = killSnakes(moveScores);
            }
            if (state == 0) { // State zero; sight = 1 block, not killing anyone
                for (int i =0; i< moveScores.length;i++ ){
                }
                moveScores = simpleAI(moveScores);
            }
        } else {// implement 2nd move for the kills
            killSnakes(moveScores, true);
        }


        // pick random move
        int dirIndex = randIndex(moveScores);
        // implementing NextMove
        if (dirIndex == 0) {
            Y++;
            dir = Direction.UP;
        } else if (dirIndex == 1) {
            X++;
            dir = Direction.RIGHT;
        } else if (dirIndex == 2) {
            Y--;
            dir = Direction.DOWN;
        } else if (dirIndex == 3) {
            X--;
            dir = Direction.LEFT;
        } else {
            throw new RuntimeException("invalid move attempted, value=" + dirIndex);
        }


        Tile nextTile = board.getTile(X, Y);

        if (nextTile == null)
            System.out.println("next tile is null");

        currentTiles.addFirst(nextTile);
        this.head = nextTile;
        if (nextTile.getType() == TileType.FOOD) {
            eatFood();
        } else if (nextTile.getType() != TileType.EMPTY) {
            death();
            return;
        }

        nextTile.setEnemySnake();


        // updating size
        if (currentTiles.size() > snakeSize) {
            currentTiles.removeLast();
            tail.clear();
            tail = currentTiles.getLast();
        }
    }


    /**
     * Random function that returns an index based on the probability of that index
     *
     * @param probabilities double list with the probability of that index getting picked
     * @return random index based on the probability array given
     */
    public int randIndex(double[] probabilities) {
        double sum = 0;

        // reset array to be near 100
        for (double probability : probabilities) {
            sum += probability;
        }
        if (sum == 0) {// No valid moves, it's an edge case that should not happen unless you underflow a double by division
            return -1;
        }

        for (int i = 0; i < probabilities.length; i++) {
            probabilities[i] = (probabilities[i] / sum) * 100;
        }

        int value = (int) (Math.random() * 100);
        // find what index was picked. By >
        for (int i = 0; i < probabilities.length; i++) {
            if (value <= probabilities[i]) {
                return i;
            }
            value = (int) (value - probabilities[i]);
        }
        throw new RuntimeException(" Random move function failed Value=" + value);
    }

    /**
     * Looks 5 tiles in potential direction and scale the move scores values based on what's there.
     * <br>
     * Scales up is multiplied diffFactor. Scale up is for food
     * scale down is divided by diffFactor. scale down for anything but food or empty.
     * <br>
     * If the tile in this direction is part of the snake body, divide by 100 *1,000. Snake suicide prevention code.
     * <br>
     *
     * @param moveScores current probability of all possible moves
     * @return moveScores - new probability of all possible moves
     */
    private double[] simpleAI(double[] moveScores) {
        // simple go for food nearby and avoidance
        int newX, newY, X, Y;
        X = head.getTileX();
        Y = head.getTileY();
        Tile next;
        for (int i = 0; i < moveScores.length; i++) {
            if (moveScores[i] == 0) {
                continue;
            }
            newX = X;
            newY = Y;
            for (int sight = 1; sight < 3; sight++) {

                if (i == 0) {
                    newY++;
                } else if (i == 1) {
                    newX++;
                } else if (i == 2) {
                    newY--;
                } else if (i == 3) {
                    newX--;
                }
                if (!board.isValidPos(newX, newY)) {
                    break;
                }
                next = board.getTile(newX, newY);

                if (next.getType() == TileType.FOOD) {//Food this way
                    System.out.println("moveS "+ moveScores[i]+" diff "+diffFactor+"result "+moveScores[i]*diffFactor);
                    moveScores[i] *= diffFactor;
                    // no break here because food is not worth more than a snake hiding behind.
                } else if (next.getType() != TileType.EMPTY) {//Something is in the path do not come
                    moveScores[i] /= diffFactor;
                    break;
                }
            }
        }
        return moveScores;
    }

    /**
     * Tells if snake is dead or not.
     *
     * @return true is snake is dead, else false
     */
    public boolean isDead() {
        return dir == Direction.NA;
    }

    /**
     * Uses status and direction to massively scale up move that will lead to a kill. Uses grid below.
     * <p>
     *
     * <h1>State</h1>
     * <b> ref is up. transform if not up.</b>
     * <table, border="1">
     * <tr>
     * <th>State value</th>
     * <th>Location relative to snake head</th>
     * <th>Next moves to kill snake</th>
     * </tr>
     * <tr>
     * <td>1</td>
     * <td>x-2, Y</td>
     * <td>up, left</td>
     * </tr>
     * <tr>
     * <td>2</td>
     * <td>X-2, Y-2</td>
     * <td>left</td>
     * </tr>
     * <tr>
     * <td>3</td>
     * <td>x-2, Y-3</td>
     * <td>left, left</td>
     * </tr>
     * <tr>
     * <td>4</td>
     * <td>X+2</td>
     * <td>up, right</td>
     * </tr>
     * <tr>
     * <td>5</td>
     * <td>X+2, Y-2</td>
     * <td>right</td>
     * </tr>
     * <tr>
     * <td>6</td>
     * <td>X+2, Y-3</td>
     * <td>right right</td>
     * </tr>
     * </table>
     * </p>
     *
     * @param moveScores current probability of all possible moves
     * @return moveScores - new probability of all possible moves
     */
    private double[] killSnakes(double[] moveScores) {
        if (!canKill()) {
            return moveScores;
        }
        /*
        State
        Ref is up. Transform if not up.
         1 = snake X-2      = up, left
         2 = snake X-2,Y-2  = left
         3 = snake X-2, Y-3 = left left
         4 = snake X+2      = up, right
         5 = snake X+ 2,Y-2 = right
         6 = snake X+2, Y-3 = right right
         */

        // to kill or not to kill
        double killchance[] = {50 / diffFactor, 50 * diffFactor}; // 0= not to kill, 1 = kill
        if (randIndex(killchance) == 0) {
            return moveScores;// will not kill
        }
        // will kill
        int index;
        if (state == 1 || state == 4) {
            index = transform(0);
            moveScores[index] = moveScores[index] * 100000;//a huge number to always go there
        } else if (state <= 3) {
            index = transform(3);
            moveScores[index] = moveScores[index] * 100000;
        } else {
            index = transform(1);
            moveScores[index] = moveScores[index] * 100000;
        }
        return moveScores;
    }

    /** Applies second move to kill snakes where required, based on status and direction.
     *
     * @param moveScores current probability of all possible moves
     * @param val overwrite Value.
     * @return Updated moveScores with killing directions scaled up
     */
    private double[] killSnakes(double[] moveScores, boolean val) {// val is not required just used for overwrite
        /*
        State
        Ref is up. Transform if not up.
         1 = snake X-2      = up, left
         2 = snake X-2,Y-2  = left
         3 = snake X-2, Y-3 = left left
         4 = snake X+2      = up, right
         5 = snake X+ 2,Y-2 = right
         6 = snake X+2, Y-3 = right right
         */
        int index;
        if (state == 1 || state == 3) {
            index = transform(3);
            moveScores[index] = moveScores[index] * 100000;
        } else if (state == 4 || state == 6) {
            index = transform(1);
            moveScores[index] = moveScores[index] * 100000;
        } else {
            moveScores = simpleAI(moveScores);
        }
        state = 0;
        return moveScores;
    }

    /**
     * Transforms the index based on the snake's current direction.
     *
     * @param index The original direction index.
     * @return The transformed index considering the current direction.
     */
    private int transform(int index) {
        int transformfactor = 0;
        if (dir == Direction.RIGHT) {
            transformfactor = 1;
        } else if (dir == Direction.DOWN) {
            transformfactor = 2;
        } else if (dir == Direction.LEFT) {
            transformfactor = 3;
        }
        return index = (index + transformfactor) % 4;
    }

    /**
     * Looks for heads snakes in killable spots. Updates status based on where th snake is located.
     * <p>
     *
     *         <h1>State</h1>
     *         <b> ref is up. transform if not up.</b>
     *         <table, border="1">
     *             <tr>
     *                 <th>State value</th>
     *                 <th>Location relative to snake head</th>
     *                 <th>Next moves to kill snake</th>
     *             </tr>
     *             <tr>
     *                 <td>1</td>
     *                 <td>x-2, Y</td>
     *                 <td>up, left</td>
     *             </tr>
     *             <tr>
     *                 <td>2</td>
     *                 <td>X-2, Y-2</td>
     *                 <td>left</td>
     *             </tr>
     *             <tr>
     *                 <td>3</td>
     *                 <td>x-2, Y-3</td>
     *                 <td>left, left</td>
     *             </tr>
     *             <tr>
     *                 <td>4</td>
     *                 <td>X+2</td>
     *                 <td>up, right</td>
     *             </tr>
     *             <tr>
     *                 <td>5</td>
     *                 <td>X+2, Y-2</td>
     *                 <td>right</td>
     *             </tr>
     *             <tr>
     *                 <td>6</td>
     *                 <td>X+2, Y-3</td>
     *                 <td>right right</td>
     *             </tr>
     *             </table>
     * </p>
     *
     * @return if there is a killable snake
     */
    private boolean canKill() {
        ArrayList<Tile> heads = board.locateHeads();
        heads.remove(head);

        int X = head.getTileX();
        int Y = head.getTileY();

        // relative vectors I and J
        int I;
        int J;
        if (dir == Direction.UP || dir == Direction.DOWN) {
            I = X;
            J = Y;
        } else {
            I = Y;
            J = X;
        }
        if (I - 2 < 0) {
            if (board.isValidPos(I - 2, I) && heads.contains(board.getTile(I - 2, J))) {
                state = 1;
                return true;
            } else if (J - 2 < 0) {
                if (board.isValidPos(I - 2, J - 2) && heads.contains(board.getTile(I - 2, J - 2))) {
                    state = 2;
                    return true;
                }
            }
            if (I - 3 < 0) {
                if (board.isValidPos(I - 2, J - 3) && heads.contains((board.getTile(I - 2, J - 3)))) {
                    state = 3;
                }
            }
        }
        if (J + 2 < 0) {
            if (board.isValidPos(I + 2, J) && heads.contains(board.getTile(I + 2, J))) {
                state = 4;
                return true;
            } else if (I - 2 < 0) {
                if (board.isValidPos(I + 2, J - 2) && heads.contains(board.getTile(I + 2, J - 2))) {
                    state = 5;
                    return true;
                }
            }
            if (I - 3 < 0) {
                if (board.isValidPos(I + 2, J - 3) && heads.contains((board.getTile(I + 2, J - 3)))) {
                    state = 6;
                    return true;
                }
            }
        }
        /*
        State
        ** ref is up. transform if not up.
         1 = going to kill snake on block x-2 = up, left
         2 = snake X-2,Y-2 = left
         3 = snake X-2, Y-3= left left
         4 = snake X+2 = up, right
         5 = snake X+ 2,Y-2 = right
         6 = snake X+2, Y-3= right right
         */
        return false;
    }

    /**
     * Lets the snake eat food
     * <p>
     * adds size
     */
    public void eatFood() {
        snakeSize++;
    }

    /**
     * kills the snake
     * <p>
     * set direction of snake to NA, then in the next move the snake is cleared off the board.
     */
    public void death() {// snake must stay for one frame before getting cleared off the board
        dir = Direction.NA;

    }

    /**
     * Gives location of the head of the snake
     *
     * @return tile where the head is located.
     */
    public Tile getHead() {
        if (this.dir == Direction.NA) {
            return null;
        }
        return head;
    }
}