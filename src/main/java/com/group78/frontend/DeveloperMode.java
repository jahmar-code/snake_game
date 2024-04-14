package com.group78.frontend;

import javax.swing.*;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Provides a developer mode interface for the game, allowing developers to login, select levels,
 * and test game functionalities.
 */
public class DeveloperMode extends JFrame implements ActionListener {
    /** Username input by the developer for login. */
    public String developerUsername;

    /** Password input by the developer for login. */
    public String developerPassword;

    /** The correct username for developer access. */
    public final String CORRECT_DEVELOPER_USERNAME = "team78developer";

    /** The correct password for developer access. */
    public final String CORRECT_DEVELOPER_PASSWORD = "CS2212";

    /**
     * Constructs an instance of the DeveloperMode class. Initializes the developer mode
     * login screen.
     */
    public DeveloperMode() {
    }

    /**
     * Displays the login screen for username input in developer mode.
     */
    public void usernameLoginScreen() {
        JTextField usernameInputField = new JTextField(20);
        this.developerModeLoginScreen(this, "Please Enter Your Username:", "NEXT", usernameInputField, true);
    }

    /**
     * Displays the login screen for password input in developer mode.
     */
    public void passwordLoginScreen() {
        JPasswordField passwordInputField = new JPasswordField(20);
        passwordInputField.setEchoChar('*');
        this.developerModeLoginScreen(this, "Please Enter Your Password:", "LOGIN", passwordInputField, false);
    }

    /**
     * Constructs the login screen for either username or password based on the parameters provided.
     *
     * @param currentFrame The current JFrame context.
     * @param loginPrompt A string prompt either for username or password input.
     * @param buttonText The text to display on the button (e.g., "NEXT" or "LOGIN").
     * @param inputField The text component for input (either JTextField for username or JPasswordField for password).
     * @param takingUsername A boolean indicating whether the input is for username (true) or password (false).
     */
    public void developerModeLoginScreen(final JFrame currentFrame, String loginPrompt, String buttonText, final JTextComponent inputField, final boolean takingUsername) {
        (new CommonMethods()).configureFrameSettings(this, "Math Mamba: Developer Mode", "#EDFFFA");
        JPanel pageHeaderPanel = (new CommonMethods()).createMainBanner(this, "DEVELOPER MODE");
        JLabel enterUsernameLabel = new JLabel(loginPrompt);
        (new CommonMethods()).formatComponentText(enterUsernameLabel, "Arial Rounded MT Bold", false, 18, "#FF82B2");
        enterUsernameLabel.setAlignmentX(0.5F);
        JButton enterUsernameButton = new JButton(buttonText);
        enterUsernameButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (inputField.getText().isEmpty()) {
                    (new CommonMethods()).createDialogBox(currentFrame, (String) null, "Oops!", "Input field cannot be empty!", new Object[]{"Try Again"});
                } else if (takingUsername) {
                    DeveloperMode.this.developerUsername = inputField.getText();
                    DeveloperMode.this.passwordLoginScreen();
                } else {
                    DeveloperMode.this.developerPassword = inputField.getText();
                    if (DeveloperMode.this.developerUsername.equals(CORRECT_DEVELOPER_USERNAME) && DeveloperMode.this.developerPassword.equals(CORRECT_DEVELOPER_PASSWORD)) {
                        DeveloperMode.this.dispose();
                        DeveloperMode.this.chooseLevelScreen();
                    } else {
                        int userChoice = (new CommonMethods()).createDialogBox(currentFrame, "src/main/java/com/group78/frontend/Images/error.gif", "Oops!", "Incorrect username and/or password!", new Object[]{"TRY AGAIN", "RETURN TO MAIN MENU"});
                        if (userChoice == 1) {
                            DeveloperMode.this.dispose();
                            new MainMenu();
                        } else {
                            DeveloperMode.this.usernameLoginScreen();
                        }
                    }
                }

            }
        });
        JPanel usernameInputPanel = (new InstructorDash()).createVerticalUserInputPanel(enterUsernameLabel, inputField, "#fcb8d2", enterUsernameButton, "#FF82B2", "#E82771");
        usernameInputPanel.setMaximumSize(new Dimension(350, 150));
        usernameInputPanel.setPreferredSize(new Dimension(350, 150));
        JPanel loginPanel = (new InstructorDash()).createHorizontalPanel(usernameInputPanel, new JLabel(new ImageIcon("src/main/java/com/group78/frontend/Images/user_icon.png")), "#FCB8D2");
        JPanel stackedInstructorLoginPanel = (new InstructorDash()).assembleStackedPagePanel(pageHeaderPanel, loginPanel);
        this.setContentPane(stackedInstructorLoginPanel);
    }

    /**
     * Displays the level selection screen allowing the developer to select a game level to test.
     */
    public void chooseLevelScreen() {
        (new CommonMethods()).configureFrameSettings(this, "Math Mamba: Developer Mode", "#EDFFFA");
        JPanel pageHeaderPanel = (new CommonMethods()).createMainBanner(this, "DEVELOPER MODE");
        JPanel buttonTablePanel = new JPanel(new GridLayout(5, 2, 5, 5));
        buttonTablePanel.setOpaque(false);
        buttonTablePanel.setPreferredSize(new Dimension(500, 300));
        buttonTablePanel.setMaximumSize(new Dimension(500, 300));

        JButton level1 = new JButton("LEVEL #1");
        level1.addActionListener(this);
        level1.setActionCommand("Level #1");
        level1.setBorder(BorderFactory.createLineBorder(Color.decode("#fc90b9"), 2));
        level1.setOpaque(true);
        (new CommonMethods()).colourButton(level1, "#FCB8D2", "#FFFFFF", "#E3FFBD", "#2CDB78");
        level1.setFont(new Font("Arial Rounded MT Bold", 1, 18));
        level1.setHorizontalAlignment(0);
        buttonTablePanel.add(level1);

        JButton level2 = new JButton("LEVEL #2");
        level2.addActionListener(this);
        level2.setActionCommand("Level #2");
        level2.setBorder(BorderFactory.createLineBorder(Color.decode("#fc90b9"), 2));
        level2.setOpaque(true);
        (new CommonMethods()).colourButton(level2, "#FCB8D2", "#FFFFFF", "#E3FFBD", "#2CDB78");
        level2.setFont(new Font("Arial Rounded MT Bold", 1, 18));
        level2.setHorizontalAlignment(0);
        buttonTablePanel.add(level2);

        JButton level3 = new JButton("LEVEL #3");
        level3.addActionListener(this);
        level3.setActionCommand("Level #3");
        level3.setBorder(BorderFactory.createLineBorder(Color.decode("#fc90b9"), 2));
        level3.setOpaque(true);
        (new CommonMethods()).colourButton(level3, "#FCB8D2", "#FFFFFF", "#E3FFBD", "#2CDB78");
        level3.setFont(new Font("Arial Rounded MT Bold", 1, 18));
        level3.setHorizontalAlignment(0);
        buttonTablePanel.add(level3);

        JButton level4 = new JButton("LEVEL #4");
        level4.addActionListener(this);
        level4.setActionCommand("Level #4");
        level4.setBorder(BorderFactory.createLineBorder(Color.decode("#fc90b9"), 2));
        level4.setOpaque(true);
        (new CommonMethods()).colourButton(level4, "#FCB8D2", "#FFFFFF", "#E3FFBD", "#2CDB78");
        level4.setFont(new Font("Arial Rounded MT Bold", 1, 18));
        level4.setHorizontalAlignment(0);
        buttonTablePanel.add(level4);

        JButton level5 = new JButton("LEVEL #5");
        level5.addActionListener(this);
        level5.setActionCommand("Level #5");
        level5.setBorder(BorderFactory.createLineBorder(Color.decode("#fc90b9"), 2));
        level5.setOpaque(true);
        (new CommonMethods()).colourButton(level5, "#FCB8D2", "#FFFFFF", "#E3FFBD", "#2CDB78");
        level5.setFont(new Font("Arial Rounded MT Bold", 1, 18));
        level5.setHorizontalAlignment(0);
        buttonTablePanel.add(level5);

        JButton level6 = new JButton("LEVEL #6");
        level6.addActionListener(this);
        level6.setActionCommand("Level #6");
        level6.setBorder(BorderFactory.createLineBorder(Color.decode("#fc90b9"), 2));
        level6.setOpaque(true);
        (new CommonMethods()).colourButton(level6, "#FCB8D2", "#FFFFFF", "#E3FFBD", "#2CDB78");
        level6.setFont(new Font("Arial Rounded MT Bold", 1, 18));
        level6.setHorizontalAlignment(0);
        buttonTablePanel.add(level6);

        JButton level7 = new JButton("LEVEL #7");
        level7.addActionListener(this);
        level7.setActionCommand("Level #7");
        level7.setBorder(BorderFactory.createLineBorder(Color.decode("#fc90b9"), 2));
        level7.setOpaque(true);
        (new CommonMethods()).colourButton(level7, "#FCB8D2", "#FFFFFF", "#E3FFBD", "#2CDB78");
        level7.setFont(new Font("Arial Rounded MT Bold", 1, 18));
        level7.setHorizontalAlignment(0);
        buttonTablePanel.add(level7);

        JButton level8 = new JButton("LEVEL #8");
        level8.addActionListener(this);
        level8.setActionCommand("Level #8");
        level8.setBorder(BorderFactory.createLineBorder(Color.decode("#fc90b9"), 2));
        level8.setOpaque(true);
        (new CommonMethods()).colourButton(level8, "#FCB8D2", "#FFFFFF", "#E3FFBD", "#2CDB78");
        level8.setFont(new Font("Arial Rounded MT Bold", 1, 18));
        level8.setHorizontalAlignment(0);
        buttonTablePanel.add(level8);

        JButton level9 = new JButton("LEVEL #9");
        level9.addActionListener(this);
        level9.setActionCommand("Level #9");
        level9.setBorder(BorderFactory.createLineBorder(Color.decode("#fc90b9"), 2));
        level9.setOpaque(true);
        (new CommonMethods()).colourButton(level9, "#FCB8D2", "#FFFFFF", "#E3FFBD", "#2CDB78");
        level9.setFont(new Font("Arial Rounded MT Bold", 1, 18));
        level9.setHorizontalAlignment(0);
        buttonTablePanel.add(level9);

        JButton level10 = new JButton("LEVEL #10");
        level10.addActionListener(this);
        level10.setActionCommand("Level #10");
        level10.setBorder(BorderFactory.createLineBorder(Color.decode("#fc90b9"), 2));
        level10.setOpaque(true);
        (new CommonMethods()).colourButton(level10, "#FCB8D2", "#FFFFFF", "#E3FFBD", "#2CDB78");
        level10.setFont(new Font("Arial Rounded MT Bold", 1, 18));
        level10.setHorizontalAlignment(0);
        buttonTablePanel.add(level10);

        JLabel chooseLevelPrompt = new JLabel("Please Choose A Level:");
        (new CommonMethods()).formatComponentText(chooseLevelPrompt, "Arial Rounded MT Bold", false, 24, "#2CDB78");
        JPanel returnToMainMenuPanel = (new CommonMethods()).createTransparentPanel();
        (new CommonMethods()).createReturnToMainMenuButton(this, returnToMainMenuPanel, "#EDB3FC", "#B745D1");
        JPanel stackedScreenPanel = (new CommonMethods()).createTransparentPanel();
        stackedScreenPanel.setLayout(new BoxLayout(stackedScreenPanel, 1));
        (new CommonMethods()).addComponentWithSpaceToPanel(stackedScreenPanel, new JComponent[]{pageHeaderPanel, chooseLevelPrompt, buttonTablePanel}, 30, true);
        stackedScreenPanel.add(returnToMainMenuPanel);
        this.setContentPane(stackedScreenPanel);
    }

    /**
     * Overrides the actionPerformed method from the ActionListener interface to handle button
     * clicks in the developer mode screens making a new game for each level button.
     *
     * @param actionEvent The action event triggered by button clicks.
     */
    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if(actionEvent.getActionCommand().equals("Level #1")){
            dispose();
            new DeveloperGameScreen(0).startGamePlayScreen();
        }
        else if(actionEvent.getActionCommand().equals("Level #2")){
            dispose();
            new DeveloperGameScreen(1).startGamePlayScreen();
        }
        else if(actionEvent.getActionCommand().equals("Level #3")){
            dispose();
            new DeveloperGameScreen(2).startGamePlayScreen();
        }
        else if(actionEvent.getActionCommand().equals("Level #4")){
            dispose();
            new DeveloperGameScreen(3).startGamePlayScreen();
        }
        else if(actionEvent.getActionCommand().equals("Level #5")){
            dispose();
            new DeveloperGameScreen(4).startGamePlayScreen();
        }
        else if(actionEvent.getActionCommand().equals("Level #6")){
            dispose();
            new DeveloperGameScreen(5).startGamePlayScreen();
        }
        else if(actionEvent.getActionCommand().equals("Level #7")){
            dispose();
            new DeveloperGameScreen(6).startGamePlayScreen();
        }
        else if(actionEvent.getActionCommand().equals("Level #8")){
            dispose();
            new DeveloperGameScreen(7).startGamePlayScreen();
        }
        else if(actionEvent.getActionCommand().equals("Level #9")){
            dispose();
            new DeveloperGameScreen(8).startGamePlayScreen();
        }
        else if(actionEvent.getActionCommand().equals("Level #10")){
            dispose();
            new DeveloperGameScreen(9).startGamePlayScreen();
        }
    }
}