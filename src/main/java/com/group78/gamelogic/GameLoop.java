package com.group78.gamelogic;
// CHRIS

import com.group78.backend.GameToken;
import com.group78.frontend.CommonMethods;

import javax.swing.*;
import java.awt.*;

/**
 * The GameLoop class represents the main game loop for the math snake game.
 * It handles game updates, rendering, and checks for game completion conditions.
 */
public class GameLoop implements Runnable {
    /** The game board. */
    private Board board;

    /** The delay between game updates in milliseconds. */
    private int DELAY_MIL;

    /** The current direction of the user-controlled snake. */
    private Direction dir;

    /** Flag to control the running state of the game loop. */
    private boolean run;

    /** A 2D array of JLabels representing the game board in the UI. */
    private JLabel[][] gameBoardLabels;

    /** An instance of common methods used across the game for UI updates. */
    private CommonMethods commonMethods;

    /** Listener for game completion events. */
    private GameCompletionListener completionListener;

    /** Game token for tracking game-related statistics and achievements. */
    private GameToken token;

    /** Counter for the number of loops executed. */
    private int loopNum;

    /** Flag to indicate if the game is in developer mode. */
    private boolean isDeveloper;

    /**
     * Constructs a GameLoop with the specified game board, UI labels, and common methods.
     *
     * @param gameBoardLabels A 2D array of JLabels for displaying the game board.
     * @param board The game board.
     * @param commonMethods An instance of common methods for UI updates.
     */
    public GameLoop(JLabel[][] gameBoardLabels, Board board, CommonMethods commonMethods) {
        this.board = board;
        this.DELAY_MIL = 300;
        this.run = true;
        this.dir = Direction.UP; // Initial direction

        this.gameBoardLabels = gameBoardLabels;
        this.commonMethods = commonMethods;
        this.loopNum = 0;
        this.isDeveloper = true;

    }

    /**
     * Constructs a GameLoop with the specified game board, UI labels, common methods, and game token.
     *
     * @param gameBoardLabels A 2D array of JLabels for displaying the game board.
     * @param board The game board.
     * @param commonMethods An instance of common methods for UI updates.
     * @param token The game token for tracking game statistics.
     */
    public GameLoop(JLabel[][] gameBoardLabels, Board board, CommonMethods commonMethods, GameToken token) {
        this.board = board;
        this.DELAY_MIL = 300;
        this.run = true;
        this.dir = Direction.UP; // Initial direction
        this.token = token;

        this.gameBoardLabels = gameBoardLabels;
        this.commonMethods = commonMethods;
        this.loopNum = 0;
        this.isDeveloper = false;
    }

    /**
     * The main game loop that updates game state and renders the game board at regular intervals.
     */
    @Override
    public void run() {

        try {
            loop();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Sets the listener for game completion events.
     *
     * @param listener The listener to be notified when the game completes.
     */
    public void setGameCompletionListener(GameCompletionListener listener) {
        this.completionListener = listener;
    }

    /**
     * The core loop that updates the game state, checks for game completion, and renders the game board.
     *
     * @throws Exception If there is an error during game update or rendering.
     */
    private void loop() throws Exception {
        while (run) {
            try {
                Thread.sleep(DELAY_MIL);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            //board.updateFood();
            if (!board.moveSnakes(dir)) {
                board.setUserDead(true);
                break;
            }

            loopNum++;
            this.drawBoard();

            if (!this.isDeveloper)
                this.drawScoreBoard();

            if (board.isEnemiesDead()) {
                break;
            }



        }
        if (completionListener != null) {
            completionListener.onGameComplete(this.board.isUserDead(), this.board.isEnemiesDead()); // Assuming !run means the user has won or game over
        }
    }

    /**
     * Renders the game board to the UI using the current state of the game board.
     */
    public void drawBoard() {

        Tile[][] boardMap = board.getMap();

        for (int j = board.getHeight() - 1; j > -1; j--) {
            for(int i = 0; i < board.getWidth(); i++) {

                switch(boardMap[i][j].getType()) {
                    case FOOD:
                        gameBoardLabels[i][j].setBackground(Color.decode("#3dabff"));
                        gameBoardLabels[i][j].setBorder(BorderFactory.createEmptyBorder());
                        break;
                    case EMPTY:
                        // gameBoardLabels[i][j].setBackground(Color.BLUE);
                        gameBoardLabels[i][j].setBorder(BorderFactory.createEmptyBorder());
                        if(i % 2 == 0) {
                            if (j % 2 == 0)
                                gameBoardLabels[i][j].setBackground(Color.decode("#9dedb2"));
                            else
                                gameBoardLabels[i][j].setBackground(Color.decode("#61e885"));

                        }
                        else {
                            if (j % 2 == 0)
                                gameBoardLabels[i][j].setBackground(Color.decode("#61e885"));
                            else
                                gameBoardLabels[i][j].setBackground(Color.decode("#9dedb2"));
                        }
                        break;
                    case U_SNAKE:
                        gameBoardLabels[i][j].setBackground(Color.YELLOW);
                        gameBoardLabels[i][j].setBorder(BorderFactory.createLineBorder(Color.CYAN));
                        break;
                    case E_SNAKE:
                        gameBoardLabels[i][j].setBackground(Color.RED);
                        gameBoardLabels[i][j].setBorder(BorderFactory.createLineBorder(Color.CYAN));
                        break;
                    case OBSTACLE:
                        gameBoardLabels[i][j].setBackground(Color.PINK);
                        gameBoardLabels[i][j].setBorder(BorderFactory.createEmptyBorder());
                        break;
                    default:
                        gameBoardLabels[i][j].setBackground(Color.GRAY);
                        gameBoardLabels[i][j].setBorder(BorderFactory.createEmptyBorder());
                }


            }
        }

    }


    /**
     * Updates the score and size display on the game UI.
     */
    public void drawScoreBoard() {

        int score = getScore() + token.getStats().getTotalPoints();

        int size = this.board.getUserSnakeSize();


        this.commonMethods.setPointsLabel("<html><div style='text-align: center;'>" + score + "<br>Points</html>"); // sub in user's points
        this.commonMethods.setSizeLabel("<html><div style='text-align: center;'>" + size + "<br>Size</html>");

    }

    /**
     * Calculates the current score based on the food eaten and enemies killed.
     *
     * @return The current score.
     */
    public int getScore() {
        return (board.userFoodAte() * 50) + (board.getNumEnemiesKilled() * 50);
    }

    /**
     * Sets the speed of the game by adjusting the delay between game updates.
     *
     * @param speed The speed setting, which inversely affects the update delay.
     */
    public void setSpeed(int speed) {
        DELAY_MIL = 2100 - speed;
    }

    /**
     * Sets the direction of the user-controlled snake.
     *
     * @param newDir The new direction for the snake.
     */
    public synchronized void setDirection(Direction newDir) {
        this.dir = newDir;
    }
}