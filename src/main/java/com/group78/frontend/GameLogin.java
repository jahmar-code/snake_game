package com.group78.frontend;

import com.group78.backend.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Handles the game login process, allowing users to either start a new game or resume a previous one
 * by entering their username. It interacts with the database to check if the user exists and their game
 * data.
 */
public class GameLogin extends JFrame implements ActionListener {

    /** Stores the user's username entered during the login process. */
    private String username;

    /** Database reader for checking user existence and fetching or creating user data. */
    private DatabaseReader db = new DatabaseReader();

    /**
     * Constructs a GameLogin window and displays the appropriate login screen based on whether the user
     * is resuming a previous game or starting a new one.
     *
     * @param resumingPrevGame Indicates whether the user is attempting to resume a previous game (true)
     *                         or start a new game (false).
     */
    public GameLogin(boolean resumingPrevGame) {
        usernameLoginScreen(resumingPrevGame);
    }

    /**
     * Displays the login panel for entering a username. Configures the frame settings based on whether
     * the user is resuming a game or starting a new one.
     *
     * @param resumingPrevGame Indicates whether the user is attempting to resume a previous game (true)
     *                         or start a new game (false).
     */
    public void usernameLoginScreen(boolean resumingPrevGame) {

        JFrame currentFrame = this;

        if(resumingPrevGame)
            new CommonMethods().configureFrameSettings(this, "Math Mamba: Resume Game", "#EDFFFA");

        else
            new CommonMethods().configureFrameSettings(this, "Math Mamba: Start New Game", "#EDFFFA");



        // Create banner panel
        JPanel bannerPanel;
        if(resumingPrevGame)
            bannerPanel = new CommonMethods().createMainBanner(this, "RESUME GAME");
        else
            bannerPanel = new CommonMethods().createMainBanner(this,"START NEW GAME");




        // Username Prompt JLabel
        JLabel usernamePromptText;
        if(resumingPrevGame)
            usernamePromptText = new JLabel("Please enter your username:");

        else
            usernamePromptText = new JLabel("Please enter a username:");

        new CommonMethods().formatComponentText(usernamePromptText, "Arial Rounded MT Bold", false, 18, "#FF82B2");



        // user input field
        JTextField usernameInputField = new JTextField(20);
        usernameInputField.setMaximumSize(new Dimension(200, 30));
        usernameInputField.setAlignmentX(Component.CENTER_ALIGNMENT);
        usernameInputField.setBorder(BorderFactory.createLineBorder(Color.decode("#fcb8d2")));


        // enter username button
        JButton enterUsernameButton = new JButton("START");
        new CommonMethods().colourButton(enterUsernameButton, "#FFFFFF", "#FF82B2", "#FFFFFF", "#E82771");
        enterUsernameButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        enterUsernameButton.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 12));

        enterUsernameButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(usernameInputField.getText().isEmpty()) {
                    new CommonMethods().createDialogBox(currentFrame, null, "Oops!", "Input field cannot be empty!", new Object[]{"Try Again"});

                } else {
                    username = usernameInputField.getText();
                    if(resumingPrevGame) {
                        if (db.isUser(username)) {
                            userDataFoundScreen();
                        } else {
                            db.createGame(username);
                            newUserScreen();
                        }
                    } else {
                        dispose();
                        if (db.isUser(username)) {
                            userDataFoundScreen();
                        } else {
                            db.createGame(username);
                            newUserScreen();
                            //new SnakeGamePlay(db.fetchToken(username)).startGamePlayScreen();
                        }
                    }
                }
            }
        });


        // create login Panel
        JPanel usernameInputPanel = new CommonMethods().createTransparentPanel();
        usernameInputPanel.setLayout(new BoxLayout(usernameInputPanel, BoxLayout.Y_AXIS));
        usernameInputPanel.setPreferredSize(new Dimension(450, 200));

        new CommonMethods().addComponentWithSpaceToPanel(usernameInputPanel, new JComponent[] {usernamePromptText, usernameInputField, enterUsernameButton}, 19, true);


        JPanel snakePicPanel = new CommonMethods().createTransparentPanel();
        snakePicPanel.setLayout(new BoxLayout(snakePicPanel, BoxLayout.Y_AXIS));

        JLabel snakeLabel = new JLabel();
        snakeLabel.setIcon(new ImageIcon("src/main/java/com/group78/frontend/Images/pink_snake.png"));
        snakeLabel.setMaximumSize(new Dimension(175, 175));

        snakePicPanel.add(snakeLabel);
        snakePicPanel.setAlignmentX(Component.CENTER_ALIGNMENT);



        JPanel loginPanel = new CommonMethods().createTransparentPanel();
        loginPanel.setLayout(new BoxLayout(loginPanel, BoxLayout.X_AXIS));
        loginPanel.setMaximumSize(new Dimension(500, 250));
        loginPanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.decode("#fcb8d2"), 2), BorderFactory.createEmptyBorder(20, 20, 20, 20) ));

        loginPanel.add(Box.createRigidArea(new Dimension(30, 0)));
        loginPanel.add(snakePicPanel);
        loginPanel.add(usernameInputPanel);


        JPanel returnToMainMenuPanel = new CommonMethods().createTransparentPanel();
        new CommonMethods().createReturnToMainMenuButton(this, returnToMainMenuPanel, "#edb3fc", "#B745D1");
        returnToMainMenuPanel.setMaximumSize(new Dimension(450, 75));


        JPanel gameLoginStackedPanel = new CommonMethods().createTransparentPanel();
        gameLoginStackedPanel.setLayout(new BoxLayout(gameLoginStackedPanel, BoxLayout.Y_AXIS));

        new CommonMethods().addComponentWithSpaceToPanel(gameLoginStackedPanel, new JComponent[] {bannerPanel, loginPanel}, 75, true);
        gameLoginStackedPanel.add(returnToMainMenuPanel);
        setContentPane(gameLoginStackedPanel);


    }

    /**
     * Displays a screen with user data if previous game data is found. Provides options to resume the game
     * or return to the main menu.
     */
    public void userDataFoundScreen() {

        GameToken user = db.fetchToken(username);

        new CommonMethods().configureFrameSettings(this, "Math Mamba: Resume Game", "#EDFFFA");

        JPanel bannerPanel = new CommonMethods().createMainBanner(this,"USER DATA FOUND");


        // displays user's username
        JPanel usernamePanel = new JPanel();
        usernamePanel.setLayout(new GridBagLayout());
        usernamePanel.setMaximumSize(new Dimension(400, 100));
        usernamePanel.setOpaque(true);
        usernamePanel.setBackground(Color.decode("#ffe0ec"));
        usernamePanel.setBorder(BorderFactory.createLineBorder(Color.decode("#FF82B2"))); // dark pink

        JLabel usernameLabel = new JLabel("Username: " + user.getUsername());
        new CommonMethods().formatComponentText(usernameLabel, "Arial Rounded MT Bold", false, 24, "#FF82B2");
        usernamePanel.add(usernameLabel);



        // displays user's current level
        JPanel currentLevelPanel = new JPanel();
        currentLevelPanel.setLayout(new GridBagLayout());
        currentLevelPanel.setMaximumSize(new Dimension(400, 100));
        currentLevelPanel.setOpaque(true);
        currentLevelPanel.setBackground(Color.decode("#f7d6ff"));
        currentLevelPanel.setBorder(BorderFactory.createLineBorder(Color.decode("#d577ed")));

        JLabel currentLevelLabel = new JLabel("Current Level: " + user.getStats().getLevel());
        new CommonMethods().formatComponentText(currentLevelLabel, "Arial Rounded MT Bold", false, 24, "#D577ED");
        currentLevelPanel.add(currentLevelLabel);



        // displays user's scoreboard
        JPanel scoreBoardPanel = new CommonMethods(user).createScoreBoardPanel(false);
        scoreBoardPanel.setMaximumSize(new Dimension(400, 100));





        // displays button panel with option to resume game or return to main menu
        JPanel buttonPanel = new CommonMethods().createTransparentPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
        buttonPanel.setMaximumSize(new Dimension(500, 60));
        buttonPanel.setAlignmentX(CENTER_ALIGNMENT);


        // button lets user return to main menu
        JButton returnToMainMenuButton = new JButton("RETURN TO MAIN MENU");
        returnToMainMenuButton.setOpaque(true);
        returnToMainMenuButton.setMaximumSize(new Dimension(200, 30));

        new CommonMethods().colourButton(returnToMainMenuButton, "#FFD085", "#FFFFFF", "#EDB3FC", "#FFFFFF");
        returnToMainMenuButton.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 14));
        returnToMainMenuButton.setBorder(BorderFactory.createLineBorder(Color.decode("#ffe6bf"), 2));

        returnToMainMenuButton.addActionListener(this);
        returnToMainMenuButton.setActionCommand("Return To Main Menu");


        // button lets user resume saved game
        JButton resumeGameButton = new JButton("RESUME GAME");
        resumeGameButton.setOpaque(true);
        resumeGameButton.setMaximumSize(new Dimension(200, 30));

        new CommonMethods().colourButton(resumeGameButton, "#FEBA4F", "#FFFFFF", "#12DBFF", "#FFFFFF");
        resumeGameButton.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 14));
        resumeGameButton.setBorder(BorderFactory.createLineBorder(Color.decode("#ffe6bf"), 2));

        resumeGameButton.addActionListener(this);
        resumeGameButton.setActionCommand("Resume Game");


        // add elements to button panel
        buttonPanel.add(Box.createRigidArea(new Dimension(50, 0)));
        buttonPanel.add(returnToMainMenuButton);
        buttonPanel.add(resumeGameButton);




        JPanel userDataStackedPanel = new CommonMethods().createTransparentPanel();
        userDataStackedPanel.setLayout(new BoxLayout(userDataStackedPanel, BoxLayout.Y_AXIS));

        new CommonMethods().addComponentWithSpaceToPanel(userDataStackedPanel, new JComponent[] {bannerPanel, usernamePanel, currentLevelPanel}, 30, true);
        userDataStackedPanel.add(scoreBoardPanel);
        userDataStackedPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        userDataStackedPanel.add(buttonPanel);

        setContentPane(userDataStackedPanel);

    }

    /**
     * Displays a screen for new users with no associated game data. Allows users to start a new game.
     */
    public void newUserScreen() {

        GameToken user = db.fetchToken(username);

        new CommonMethods().configureFrameSettings(this, "Math Mamba: Start Game", "#EDFFFA");

        JPanel bannerPanel = new CommonMethods().createMainBanner(this,"NEW USER CREATED");


        // displays user's username
        JPanel usernamePanel = new JPanel();
        usernamePanel.setLayout(new GridBagLayout());
        usernamePanel.setMaximumSize(new Dimension(400, 100));
        usernamePanel.setOpaque(true);
        usernamePanel.setBackground(Color.decode("#ffe0ec"));
        usernamePanel.setBorder(BorderFactory.createLineBorder(Color.decode("#FF82B2"))); // dark pink

        JLabel usernameLabel = new JLabel("Username: " + user.getUsername());
        new CommonMethods().formatComponentText(usernameLabel, "Arial Rounded MT Bold", false, 24, "#FF82B2");
        usernamePanel.add(usernameLabel);



        // displays user's current level
        JPanel currentLevelPanel = new JPanel();
        currentLevelPanel.setLayout(new GridBagLayout());
        currentLevelPanel.setMaximumSize(new Dimension(400, 100));
        currentLevelPanel.setOpaque(true);
        currentLevelPanel.setBackground(Color.decode("#f7d6ff"));
        currentLevelPanel.setBorder(BorderFactory.createLineBorder(Color.decode("#d577ed")));

        JLabel currentLevelLabel = new JLabel("Current Level: " + user.getStats().getLevel());
        new CommonMethods().formatComponentText(currentLevelLabel, "Arial Rounded MT Bold", false, 24, "#D577ED");
        currentLevelPanel.add(currentLevelLabel);



        // displays user's scoreboard
        JPanel scoreBoardPanel = new CommonMethods(user).createScoreBoardPanel(false);
        scoreBoardPanel.setMaximumSize(new Dimension(400, 100));





        // displays button panel with option to resume game or return to main menu
        JPanel buttonPanel = new CommonMethods().createTransparentPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
        buttonPanel.setMaximumSize(new Dimension(500, 60));
        buttonPanel.setAlignmentX(CENTER_ALIGNMENT);


        // button lets user return to main menu
        JButton returnToMainMenuButton = new JButton("RETURN TO MAIN MENU");
        returnToMainMenuButton.setOpaque(true);
        returnToMainMenuButton.setMaximumSize(new Dimension(200, 30));

        new CommonMethods().colourButton(returnToMainMenuButton, "#FFD085", "#FFFFFF", "#EDB3FC", "#FFFFFF");
        returnToMainMenuButton.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 14));
        returnToMainMenuButton.setBorder(BorderFactory.createLineBorder(Color.decode("#ffe6bf"), 2));

        returnToMainMenuButton.addActionListener(this);
        returnToMainMenuButton.setActionCommand("Return To Main Menu");


        // button lets user resume saved game
        JButton resumeGameButton = new JButton("START GAME");
        resumeGameButton.setOpaque(true);
        resumeGameButton.setMaximumSize(new Dimension(200, 30));

        new CommonMethods().colourButton(resumeGameButton, "#FEBA4F", "#FFFFFF", "#12DBFF", "#FFFFFF");
        resumeGameButton.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 14));
        resumeGameButton.setBorder(BorderFactory.createLineBorder(Color.decode("#ffe6bf"), 2));

        resumeGameButton.addActionListener(this);
        resumeGameButton.setActionCommand("Resume Game");


        // add elements to button panel
        buttonPanel.add(Box.createRigidArea(new Dimension(50, 0)));
        buttonPanel.add(returnToMainMenuButton);
        buttonPanel.add(resumeGameButton);




        JPanel userDataStackedPanel = new CommonMethods().createTransparentPanel();
        userDataStackedPanel.setLayout(new BoxLayout(userDataStackedPanel, BoxLayout.Y_AXIS));

        new CommonMethods().addComponentWithSpaceToPanel(userDataStackedPanel, new JComponent[] {bannerPanel, usernamePanel, currentLevelPanel}, 30, true);
        userDataStackedPanel.add(scoreBoardPanel);
        userDataStackedPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        userDataStackedPanel.add(buttonPanel);

        setContentPane(userDataStackedPanel);

    }

    /**
     * Handles button actions within the GameLogin window. Includes navigating back to the main menu and
     * resuming or starting a game based on user data.
     *
     * @param evt The action event triggered by UI interactions.
     */
    @Override
    public void actionPerformed(ActionEvent evt) {


        // return to main menu
        if (evt.getActionCommand().equals("Return To Main Menu")) {

            int returnOrStay = new CommonMethods().createDialogBox(this, null, "Return To Main Menu", "Are you sure you want to return to the main menu?", new Object[] {"NO, STAY", "YES, RETURN TO MAIN MENU"});

            if(returnOrStay == 1) {
                dispose();
                new MainMenu();
            }

        }

        if(evt.getActionCommand().equals("Resume Game")) {

            dispose();
            new SnakeGamePlay(db.fetchToken(username)).startGamePlayScreen();
        }

    }



}