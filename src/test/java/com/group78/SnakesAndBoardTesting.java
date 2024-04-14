package com.group78;
import com.group78.gamelogic.Board;
import com.group78.gamelogic.Direction;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SnakesAndBoardTesting {

    private Board board;

    /**
     * User Looping around the outside perimeter of map.
     * Made the User snake loop around the map, then go inwards and go over most of the inter Tiles
     *
     * @throws Exception
     */
    @Test
    void moveUserSnake() throws Exception {
        Board board = new Board(8, 8, 0, 0, 0, 0);
        System.out.println("Demoing Valid moves with one UserSnake");
        for (int j = 0; j < 3; j++) {
            assertEquals(true, board.moveSnakes(Direction.UP));
            System.out.println(board.getBitboard());
        }
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 7 - i; j++) {
                assertEquals(true, board.moveSnakes(Direction.RIGHT));
                System.out.println(board.getBitboard());
            }
            i++;
            for (int j = 0; j < 7 - i; j++) {
                assertEquals(true, board.moveSnakes(Direction.DOWN));
                System.out.println(board.getBitboard());
            }
            for (int j = 0; j < 7 - i; j++) {
                assertEquals(true, board.moveSnakes(Direction.LEFT));
                System.out.println(board.getBitboard());
            }
            for (int j = 0; j < 7 - i - 1; j++) {
                assertEquals(true, board.moveSnakes(Direction.UP));
                System.out.println(board.getBitboard());
            }
        }
    }

    /**
     * Makes sure Enemy snake can hit perimeter cells. If it can
     *
     * @throws Exception
     */
    @Test
    void moveEnemySnake() throws Exception {

        board = new Board(8, 8, 0, 0, 1, 1);
        System.out.println("Demoing Valid moves with UserSnake and 1 enemySnake ");
        for (int i = 0; i < 1; i++) {
            assertEquals(true, board.moveSnakes(Direction.UP));
            System.out.println(board.getBitboard());
            assertEquals(true, board.moveSnakes(Direction.UP));
            System.out.println(board.getBitboard());
            for (int j = 0; j < 5; j++) {
                assertEquals(true, board.moveSnakes(Direction.RIGHT));
                System.out.println(board.getBitboard());
            }
            for (int j = 0; j < 5; j++) {
                assertEquals(true, board.moveSnakes(Direction.DOWN));
                System.out.println(board.getBitboard());
            }
            for (int j = 0; j < 5; j++) {
                assertEquals(true, board.moveSnakes(Direction.LEFT));
                System.out.println(board.getBitboard());
            }
            for (int j = 0; j < 3; j++) {
                assertEquals(true, board.moveSnakes(Direction.UP));
                System.out.println(board.getBitboard());
            }
        }
    }

    @Test
    void getSnakeSize() throws Exception {
        System.out.println("Demo Snake eating food");
        board = new Board(1, 9, 4, 0, 0, 0);
        for (int i = 0; i < 4; i++) {
            System.out.println(board.getBitboard());
            assertEquals(board.userFoodAte(), i);
            board.moveSnakes(Direction.UP);

        }
    }

    @Test
    void getSnakeSizeWithEnemy() throws Exception {
        System.out.println("Demo Snake eating food");
        board = new Board(2, 9, 7, 0, 1, 1);
        for (int i = 0; i < 5; i++) {
            System.out.println(board.getBitboard());
            assertEquals(board.userFoodAte(), i);
            board.moveSnakes(Direction.UP);

        }

    }
}
