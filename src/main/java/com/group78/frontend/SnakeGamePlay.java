package com.group78.frontend;

import com.group78.backend.*;
import com.group78.gamelogic.Board;
import com.group78.gamelogic.Direction;
import com.group78.gamelogic.GameCompletionListener;
import com.group78.gamelogic.GameLoop;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * This class represents the main game window for the snake game.
 * It manages the rendering of the game board and responds to keyboard input for game control.
 * It also handles game start and completion events.
 */
public class SnakeGamePlay extends JFrame implements ActionListener, GameCompletionListener {

    public static int WIDTH; // Width of the game board
    public static int HEIGHT; // Height of the game board
    public Board board; // The game board
    public GameLoop gameLoop; // The game loop responsible for updating the game state
    public JLabel[][] gameBoardLabels; // 2D array of labels representing the game board on the UI
    public boolean userWon; // Flag indicating if the user won the game
    public boolean userLost; // Flag indicating if the user lost the game
    private int level; // Current level of the game
    private DatabaseReader db; // Database reader for saving and retrieving game state
    private CommonMethods commonMethods; // Utility class for common UI methods
    private GameToken user; // User's game token
    private MathTest math; // Math test associated with the game

    /**
     * Default constructor for creating an instance of SnakeGamePlay.
     */
    public SnakeGamePlay() {}

    /**
     * Constructs an instance of SnakeGamePlay with a specified GameToken.
     * @param user the GameToken containing user's data
     */
    public SnakeGamePlay(GameToken user) {
        this.user= user;
        this.level = user.getStats().getLevel();
        this.db = new DatabaseReader();

        try {

            board = new Board(level);
            WIDTH = board.getWidth();
            HEIGHT = board.getHeight();

        } catch (Exception e) {
            System.out.println(e.toString());
        }
        gameBoardLabels = new JLabel[HEIGHT][WIDTH];

        commonMethods = new CommonMethods(user);

        this.gamePlayScreen();

        System.out.println(commonMethods.getSizeLabel() == null);
        System.out.println(commonMethods.getPointsLabel() == null);

        gameLoop = new GameLoop(gameBoardLabels, board, commonMethods, this.user);
    }

    /**
     * Sets up and displays the snake game play screen.
     */
    public void gamePlayScreen() {

        this.commonMethods.configureFrameSettings(this, "Math Mamba", "#EDFFFA"); // Frame settings

        JPanel gameBoardPanel = new CommonMethods(user).createTransparentPanel();
        gameBoardPanel.setLayout(new GridLayout(HEIGHT, WIDTH));

        gameBoardPanel.setFocusable(true);
        gameBoardPanel.requestFocusInWindow();

        gameBoardPanel.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_UP:
                        gameLoop.setDirection(Direction.UP);
                        break;
                    case KeyEvent.VK_DOWN:
                        //System.out.println("going down");
                        gameLoop.setDirection(Direction.DOWN);
                        break;
                    case KeyEvent.VK_LEFT:
                        // System.out.println("going left");
                        gameLoop.setDirection(Direction.LEFT);
                        break;
                    case KeyEvent.VK_RIGHT:
                        //   System.out.println("going right");
                        gameLoop.setDirection(Direction.RIGHT);
                        break;
                }
            }
        });

        for(int col = WIDTH-1; col >= 0; col--) {
            for(int row = 0; row < HEIGHT; row++) {
                // gameBoardLabels[row][col] = new JLabel("[" + (row) + "," + (col) + "]");
                gameBoardLabels[row][col] = new JLabel();
                //gameBoardLabels[row][col].setBorder(BorderFactory.createLineBorder(Color.BLACK));
                gameBoardLabels[row][col].setOpaque(true);
                if(row % 2 == 0) {
                    if (col % 2 == 0)
                        gameBoardLabels[row][col].setBackground(Color.decode("#9dedb2"));
                    else
                        gameBoardLabels[row][col].setBackground(Color.decode("#61e885"));
                }
                else {
                    if (col % 2 == 0)
                        gameBoardLabels[row][col].setBackground(Color.decode("#61e885"));
                    else
                        gameBoardLabels[row][col].setBackground(Color.decode("#9dedb2"));
                }
                gameBoardPanel.add(gameBoardLabels[row][col]);
            }
        }

        gameBoardPanel.setPreferredSize(new Dimension(700, 700));
        gameBoardPanel.setMaximumSize(new Dimension(700, 700));

        JPanel scoreBoardPanel = commonMethods.createScoreBoardPanel(true);

        JPanel homeButtonPanel = new CommonMethods(user).addHomeButton(this);

        JPanel gamePlayStackedPanel = new CommonMethods(user).createTransparentPanel();
        gamePlayStackedPanel.setLayout(new BoxLayout(gamePlayStackedPanel, BoxLayout.Y_AXIS));

        gamePlayStackedPanel.add(Box.createRigidArea(new Dimension(0, 30)));
        new CommonMethods(user).addComponentWithSpaceToPanel(gamePlayStackedPanel, new JComponent[] {gameBoardPanel}, 30, true);
        new CommonMethods(user).addComponentWithSpaceToPanel(gamePlayStackedPanel, new JComponent[] {scoreBoardPanel}, 70, true);
        new CommonMethods(user).addComponentWithSpaceToPanel(gamePlayStackedPanel, new JComponent[] {homeButtonPanel}, 30, true);

        this.setContentPane(gamePlayStackedPanel);
    }




    /**
     * Starts the snake game by displaying a prompt and initiating the game loop.
     */
    public void startGamePlayScreen() {

        gamePlayScreen();

        gameLoop.setGameCompletionListener(this);

        new CommonMethods(user).createDialogBox(this, null, "Level "+ user.getStats().getLevel(), "Are You Ready?", new Object[]{"START"});
        SwingUtilities.invokeLater(() -> {
            new Thread(gameLoop).start();
        });
    }



    /**
     * Displays a prompt when the user has won the snake game play.
     */
    public void snakeGamePlayWinScreen() {

        gamePlayScreen();

        new CommonMethods(user).createDialogBox(this, "src/main/java/com.group78/frontend/Images/confetti.gif", null, "Congratulations,<br>you won!", new Object[] {"START MATH QUESTIONNAIRE"});

       // dispose();
        new MathQuestionnaire(user);

    }

    /**
     * Implements the ActionListener interface to respond to button click events.
     * @param evt the action event that triggered the method call
     */
    @Override
    public void actionPerformed(ActionEvent evt) {

        if(evt.getActionCommand().equals("Return To Main Menu")) {

            int resumeOrExit = new CommonMethods(user).createDialogBox(null, null, "Return To Main Menu", "<b>ATTENTION!</b><br><br>If you exit, your progress during this round will not be saved!", new Object[] {"RETURN TO MAIN MENU", "RESUME GAME"});

            if(resumeOrExit == 0)
                dispose();
            new MainMenu();
        }

    }

    /**
     * Responds to the completion of the game, triggered by the GameLoop.
     * @param isUserDead indicates if the user's snake has died
     * @param isEnemiesDead indicates if all enemies have been defeated
     */
    @Override
    public void onGameComplete(boolean isUserDead, boolean isEnemiesDead) {
        SwingUtilities.invokeLater(() -> {
            if (isUserDead) {

                new CommonMethods().createDialogBox(this, null, "Oh no...", "You lost!<br>", new Object[] {"CONTINUE"});

                new MathQuestionnaire(user).levelResultScreen(false);

            } else {
                int score = (board.userFoodAte() * 10) + (board.getNumEnemiesKilled() * 50);
                UserStats stats = user.getStats();
                stats.setTotalPoints(stats.getTotalPoints()+score);
                db.saveGame(user);
                snakeGamePlayWinScreen();
            }
        });
    }
}