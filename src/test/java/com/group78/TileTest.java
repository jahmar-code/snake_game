package com.group78;


import com.group78.gamelogic.Tile;
import com.group78.gamelogic.TileType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TileTest {
    private Tile tile;
    private int x =1;
    private int y = 3;

    /**
     * Sets Up Tile with X and Y locations
     */
    @BeforeEach
    void setUp() {
        tile = new Tile(TileType.EMPTY,x,y);
    }

    /**
     * Cycles through all the different types a tile can have.
     * Tests SetFood(), setUserSnake(), setEnemySnake(), setObstacle() and setClear()
     *
     * @throws Exception
     */
    @Test
    void cycleTileTypes() throws Exception {

        System.out.println("testing initial empty tile");
        assertEquals(tile.getType(),TileType.EMPTY);

        System.out.println("Changing tile type to food then testing");
        tile.setFood();
        assertEquals(tile.getType(),TileType.FOOD);

        System.out.println("Changing tile type to Enemy Snake then testing");
        tile.setEnemySnake();
        assertEquals(tile.getType(),TileType.E_SNAKE);

        System.out.println("Clearing tile type  then testing");
        tile.clear();
        assertEquals(tile.getType(),TileType.EMPTY);

        System.out.println("Changing tile type to User Snake then testing");
        tile.setUserSnake();
        assertEquals(tile.getType(),TileType.U_SNAKE);

        System.out.println("Changing tile type to obstacle then testing");
        tile.clear();
        tile.setObstacle();
        assertEquals(tile.getType(),TileType.OBSTACLE);
    }

    /**
     * tests to see if x is returned
     */
    @Test
    void getTileX() {
        assertEquals(x,tile.getTileX());
    }

    /**
     * tests to see if y is returned
     */
    @Test
    void getTileY() {
        assertEquals(y,tile.getTileY());
    }

    /**
     * Tests char rep and toString. Cycles through all possible char types
     */
    @Test
    void charRep() throws Exception {
        System.out.println("Testing initial empty tile");
        assertEquals(tile.charRep(),"-");
        System.out.println(tile.toString());


        System.out.println("\nChanging tile type to food then testing");
        tile.setFood();
        assertEquals(tile.charRep(),"f");
        System.out.println(tile.toString());

        System.out.println("\nChanging tile type to Enemy Snake then testing");
        tile.setEnemySnake();
        assertEquals(tile.charRep(),"e");
        System.out.println(tile.toString());

        tile.clear();


        System.out.println("\nChanging tile type to User Snake then testing");
        tile.setUserSnake();
        assertEquals(tile.charRep(),"u");
        System.out.println(tile.toString());

        tile.clear();

        System.out.println("\nChanging tile type to obstacle then testing");
        tile.setObstacle();
        assertEquals(tile.charRep(),"o");
        System.out.println(tile.toString());
    }
}