package com.group78.frontend;//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//
import com.group78.gamelogic.Board;
import com.group78.gamelogic.Direction;
import com.group78.gamelogic.GameCompletionListener;
import com.group78.gamelogic.GameLoop;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * Represents the game screen for developers, allowing for direct interaction and testing
 * of game functionality such as switching levels and adjusting game speed.
 */
public class DeveloperGameScreen extends JFrame implements ActionListener {

    /** The width of the game board. */
    public int WIDTH;

    /** The height of the game board. */
    public int HEIGHT;

    /** The game board object that contains the logic and state of the game. */
    public Board board;

    /** The main game loop that drives game updates and rendering. */
    public GameLoop gameLoop;

    /** A two-dimensional array of JLabels representing the game board's grid. */
    public JLabel[][] gameBoardLabels;

    /** A panel that stacks different components vertically for the gameplay screen. */
    private JPanel gamePlayStackedPanel;

    /** The current level the game is set to. */
    private int level;

    /** A utility class instance for common operations like creating panels and configuring UI elements. */
    private CommonMethods commonMethods;

    /**
     * Constructs a DeveloperGameScreen instance for a specified level.
     *
     * @param level The level to initialize the game with.
     */
    public DeveloperGameScreen(int level) {
        this.level = level;
        try {
            this.board = new Board(this.level);
        } catch (Exception var2) {
            System.out.println(var2.toString());
        }

        this.WIDTH = this.board.getWidth();
        this.HEIGHT = this.board.getHeight();

        this.gameBoardLabels = new JLabel[HEIGHT][WIDTH];
        this.commonMethods = new CommonMethods();
        this.gamePlayScreen();
        this.gameLoop = new GameLoop(this.gameBoardLabels, this.board, this.commonMethods);
    }

    /**
     * Initializes the gameplay screen, including the game board, score board,
     * and other UI components.
     */
    public void gamePlayScreen() {
        (new CommonMethods()).configureFrameSettings(this, "Math Mamba", "#EDFFFA");
        JPanel gameBoardPanel = (new CommonMethods()).createTransparentPanel();
        gameBoardPanel.setLayout(new GridLayout(HEIGHT, WIDTH));
        gameBoardPanel.setFocusable(true);
        gameBoardPanel.requestFocusInWindow();
        gameBoardPanel.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case 37:
                        DeveloperGameScreen.this.gameLoop.setDirection(Direction.LEFT);
                        break;
                    case 38:
                        DeveloperGameScreen.this.gameLoop.setDirection(Direction.UP);
                        break;
                    case 39:
                        DeveloperGameScreen.this.gameLoop.setDirection(Direction.RIGHT);
                        break;
                    case 40:
                        DeveloperGameScreen.this.gameLoop.setDirection(Direction.DOWN);
                }

            }
        });

        for (int col = WIDTH - 1; col >= 0; --col) {
            for (int row = 0; row < HEIGHT; ++row) {
                this.gameBoardLabels[row][col] = new JLabel();
                this.gameBoardLabels[row][col].setOpaque(true);
                this.gameBoardLabels[row][col].setBackground(Color.PINK);
                gameBoardPanel.add(this.gameBoardLabels[row][col]);
            }
        }

        gameBoardPanel.setPreferredSize(new Dimension(700, 700));
        gameBoardPanel.setMaximumSize(new Dimension(700, 700));

        JPanel scoreBoardPanel = this.commonMethods.createScoreBoardPanel(true);
        JPanel homeButtonPanel = (new CommonMethods()).addHomeButton(this);
        JButton backToLevels = new JButton("Switch Levels");
        backToLevels.addActionListener(this);
        JPanel backToLevelPanel = new JPanel();
        JLabel speedLabel = new JLabel("Speed :");
        backToLevelPanel.add(speedLabel);
        JSlider speed = new JSlider(200, 2000);
        speed.setMinorTickSpacing(500);
        speed.setMinorTickSpacing(200);
        speed.setSnapToTicks(true);
        speed.setPaintLabels(true);
        speed.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                gameLoop.setSpeed(speed.getValue());
                gameBoardPanel.requestFocusInWindow();
            }
        });
        backToLevelPanel.add(speed);
        backToLevelPanel.add(backToLevels);
        this.gamePlayStackedPanel = (new CommonMethods()).createTransparentPanel();
        this.gamePlayStackedPanel.setLayout(new BoxLayout(this.gamePlayStackedPanel, 1));
        this.gamePlayStackedPanel.add(Box.createRigidArea(new Dimension(0, 30)));
        (new CommonMethods()).addComponentWithSpaceToPanel(this.gamePlayStackedPanel, new JComponent[]{gameBoardPanel}, 30, true);
        (new CommonMethods()).addComponentWithSpaceToPanel(this.gamePlayStackedPanel, new JComponent[]{scoreBoardPanel}, 70, true);
        (new CommonMethods()).addComponentWithSpaceToPanel(this.gamePlayStackedPanel, new JComponent[]{homeButtonPanel}, 30, true);
        (new CommonMethods()).addComponentWithSpaceToPanel(this.gamePlayStackedPanel, new JComponent[]{backToLevelPanel}, 20, true);
        this.setContentPane(this.gamePlayStackedPanel);
    }

    /**
     * Starts the gameplay, displaying the gameplay screen and initiating the game loop.
     */
    public void startGamePlayScreen() {
        this.gamePlayScreen();
        (new CommonMethods()).createDialogBox(this, (String) null, "Level" + (this.level+1), "Are You Ready?", new Object[]{"START"});
        SwingUtilities.invokeLater(() -> {
            (new Thread(this.gameLoop)).start();
        });
    }

    /**
     * Handles actions performed in the UI, such as button clicks for returning to the main menu
     * or switching levels.
     *
     * @param evt The ActionEvent that occurred.
     */
    public void actionPerformed(ActionEvent evt) {
        if (evt.getActionCommand().equals("Return To Main Menu")) {
            int resumeOrExit = (new CommonMethods()).createDialogBox((JFrame) null, (String) null, "Return To Main Menu", "<b>ATTENTION!</b><br><br>If you exit, your progress during this round will not be saved!", new Object[]{"RETURN TO MAIN MENU", "RESUME GAME"});
            if (resumeOrExit == 0) {
                this.dispose();
            }

            new MainMenu();
        } else if (evt.getActionCommand().equals("Switch Levels")) {
            (new DeveloperMode()).chooseLevelScreen();
        }
    }

}