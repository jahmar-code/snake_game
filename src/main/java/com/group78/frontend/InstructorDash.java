package com.group78.frontend;

import com.group78.backend.DatabaseReader;
import com.group78.backend.GameToken;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Provides functionality for the instructor dashboard including login and user data search.
 * This class allows instructors to log in with a username and password and search for game data
 * associated with specific usernames.
 */
public class InstructorDash extends JFrame implements ActionListener {

    /** The instructor's entered username. */
    public String instructorUsername;

    /** The instructor's entered password. */
    public String instructorPassword;

    /** The correct username for instructor access. */
    public final String CORRECT_INSTRUCTOR_USERNAME = "servos";

    /** The correct password for instructor access. */
    public final String CORRECT_INSTRUCTOR_PASSWORD = "2212";

    /** A temporary holder for the username input by the instructor. */
    private String username;

    /** Database reader for checking if a user exists and for data retrieval. */
    private DatabaseReader db;

    /**
     * Constructor that initializes the DatabaseReader.
     */
    public InstructorDash() {
        this.db = new DatabaseReader();
    }


    /**
     * Prompts a login screen for the instructor to enter their username.
     */
    public void usernameLoginScreen() {

        JTextField usernameInputField = new JTextField(20);
        instructorDashboardLoginScreen(this, "Please Enter Your Username:", "NEXT", usernameInputField, true);

    }


    /**
     * Prompts a login screen for the instructor to enter their password.
     */
    public void passwordLoginScreen() {

        JPasswordField passwordInputField = new JPasswordField(20);
        passwordInputField.setEchoChar('*');
        instructorDashboardLoginScreen(this, "Please Enter Your Password:", "LOGIN", passwordInputField, false);

    }

    /**
     * Method for creating a login screen for the instructor dashboard.
     *
     * @param currentFrame The current JFrame to be used for the login screen.
     * @param loginPrompt The text prompt for login (username/password).
     * @param buttonText The text to display on the button (e.g., "NEXT" or "LOGIN").
     * @param inputField The text input component for entering username or password.
     * @param takingUsername A boolean indicating if the current input is for the username.
     */

    public void instructorDashboardLoginScreen(JFrame currentFrame, String loginPrompt, String buttonText, JTextComponent inputField, boolean takingUsername) {


        new CommonMethods().configureFrameSettings(this, "Math Mamba: Instructor Dashboard", "#EDFFFA");

        // header panel containing game visual & rectangle banner
        JPanel pageHeaderPanel = new CommonMethods().createMainBanner(this, "INSTRUCTOR DASHBOARD");


        // enter username label
        JLabel enterUsernameLabel = new JLabel(loginPrompt);
        new CommonMethods().formatComponentText(enterUsernameLabel, "Arial Rounded MT Bold", false, 18, "#FF82B2");
        enterUsernameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        //enter username button
        JButton enterUsernameButton = new JButton(buttonText);
        enterUsernameButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                // if input is empty, prompt user to try again
                if (inputField.getText().isEmpty())
                    new CommonMethods().createDialogBox(currentFrame, null, "Oops!", "Input field cannot be empty!", new Object[]{"Try Again"});

                    // input is not empty
                else {

                    // if current screen is accepting username input, store username & call passwordLoginScreem
                    if (takingUsername) {
                        instructorUsername = inputField.getText();
                        passwordLoginScreen();
                    }

                    // if current screen is accepting password input, evaluate instructor credentials
                    else {
                        instructorPassword = inputField.getText(); // store password

                        // if credentials are correct, enter instructor dashboard
                        if ((instructorUsername.equals(CORRECT_INSTRUCTOR_USERNAME)) && (instructorPassword.equals(CORRECT_INSTRUCTOR_PASSWORD))) {
                            dispose();
                            searchUserDatabaseScreen();

                        }

                        // if credentials are incorrect, display dialog to re-prompt user
                        else {
                            int userChoice = new CommonMethods().createDialogBox(currentFrame, "src/main/java/com/group78/frontend/Images/error.gif", "Oops!", "Incorrect username and/or password!", new Object[]{"TRY AGAIN", "RETURN TO MAIN MENU"});

                            // if user chooses 'return to main menu'
                            if (userChoice == 1) {
                                dispose();
                                new MainMenu();
                            }
                            // if user chooses 'try again' or x option on window
                            else
                                usernameLoginScreen();
                        }
                    }
                }
            }
        });

        // vertical panel containing user input field
        JPanel usernameInputPanel = createVerticalUserInputPanel(enterUsernameLabel, inputField, "#fcb8d2", enterUsernameButton, "#FF82B2", "#E82771");
        usernameInputPanel.setMaximumSize(new Dimension(350, 150));
        usernameInputPanel.setPreferredSize(new Dimension(350, 150));

        // horizontal panel containing user input field + icon
        JPanel loginPanel = createHorizontalPanel(usernameInputPanel, new JLabel(new ImageIcon("src/main/java/com/group78/frontend/Images/user_icon.png")), "#FCB8D2");

        // final stacked panel for full screen
        JPanel stackedInstructorLoginPanel = assembleStackedPagePanel(pageHeaderPanel, loginPanel);

        setContentPane(stackedInstructorLoginPanel);

    }

    /**
     * Displays a screen where the instructor can search the user database by entering a username.
     */
    public void searchUserDatabaseScreen() {

        JFrame currentFrame = this;
        new CommonMethods().configureFrameSettings(this, "Math Mamba: Instructor Dashboard", "#EDFFFA");

        // header panel (pic + rectangle banner)
        JPanel pageHeaderPanel = new CommonMethods().createMainBanner(this, "INSTRUCTOR DASHBOARD");

        // create input field that will accept a username from the instructor
        JTextField usernameInputField = new JTextField(20);

        // create button which can be clicked to search database for user
        JButton searchButton = new JButton("SEARCH");

        searchButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                // input field left empty
                if (usernameInputField.getText().isEmpty())
                    new CommonMethods().createDialogBox(currentFrame, null, "Oops!", "Input field cannot be empty!", new Object[]{"Try Again"});

                    // input field not empty
                else {

                    username = usernameInputField.getText();
                    if (db.isUser(username)) {
                        userProfileScreen(username);
                    } else {
                        Object[] dialogOption = {"TRY AGAIN", "RETURN TO MAIN MENU"};
                    int tryAgainOrExit = new CommonMethods().createDialogBox(currentFrame, "src/Images/search_failed_icon.png", "Player Database", "Sorry,<br><br>no game data found<br>for the entered username!", dialogOption);
                        if(tryAgainOrExit == 1) { // user chooses 'return to main menu'
                            dispose();
                            new MainMenu();
                        }
                    }

                    // IMPLEMENT CODE TO SEARCH ALL SAVED GAME DATA FOR USERNAME ENTERED

                    // if username matches profile in storage


                    // if username does not match any profile in storage





                }

            }
        });



        // vertical panel containing user input panel
        JPanel userInputPanel = createVerticalUserInputPanel((new JLabel("<html><div style='text-align: center; font-family: Arial Rounded MT Bold; font-size: 18px; color: #12DBFF;'>PLAYER DATABASE<br><br><span style='font-size: 14px;'>Please Enter A Username:</span></html>")), usernameInputField, "#12DBFF", searchButton, "#12DBFF", "#0997b0");

        // horizontal panel with input panel + icon
        JPanel searchPanel = createHorizontalPanel(userInputPanel, new JLabel(new ImageIcon("src/main/java/com/group78/frontend/Images/search_icon.png")), "#12DBFF");

        // final stacked panel for full screen
        JPanel stackedInstructorDatabasePanel = assembleStackedPagePanel(pageHeaderPanel, searchPanel);

        setContentPane(stackedInstructorDatabasePanel);


    }

    /**
     * Creates a panel containing user input components arranged vertically.
     *
     * @param enterInputPrompt The JLabel prompt for user input.
     * @param inputField The JTextComponent for user input.
     * @param inputFieldColour The color code for the input field border.
     * @param enterInputButton The JButton to trigger the input process.
     * @param buttonColour The color code for the button text.
     * @param hoverButtonColour The color code for the button text on hover.
     * @return A JPanel containing the arranged components.
     */
    public JPanel createVerticalUserInputPanel(JLabel enterInputPrompt, JTextComponent inputField, String inputFieldColour, JButton enterInputButton, String buttonColour, String hoverButtonColour) {

        // create panel containing label that prompts user's entry
        JPanel enterInputPromptPanel = new CommonMethods().createTransparentPanel();
        enterInputPromptPanel.add(enterInputPrompt);

        // configure settings of input field
        inputField.setMaximumSize(new Dimension(200, 30));
        inputField.setAlignmentX(Component.CENTER_ALIGNMENT);
        inputField.setBorder(BorderFactory.createLineBorder(Color.decode(inputFieldColour)));

        // configure settings of enter input button
        new CommonMethods().colourButton(enterInputButton, "#FFFFFF", buttonColour, "#FFFFFF", hoverButtonColour);
        enterInputButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        enterInputButton.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 12));


        // create vertical panel that contains prompt label + input field + enter button
        JPanel usernameInputPanel = new CommonMethods().createTransparentPanel();
        usernameInputPanel.setLayout(new BoxLayout(usernameInputPanel, BoxLayout.Y_AXIS));
        usernameInputPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // add components to panel
        usernameInputPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        new CommonMethods().addComponentWithSpaceToPanel(usernameInputPanel, new JComponent[]{enterInputPromptPanel, inputField, enterInputButton}, 20, true);

        return usernameInputPanel;

    }

    /**
     * Creates a panel containing a user input panel and an icon, arranged horizontally.
     *
     * @param panel The JPanel containing user input components.
     * @param iconLabel The JLabel containing an icon image.
     * @param panelBorderColour The color code for the panel border.
     * @return A JPanel containing the arranged components.
     */
    public JPanel createHorizontalPanel(JPanel panel, JLabel iconLabel, String panelBorderColour) {

        // create horizontal panel + configure settings
        JPanel horizontalPanel = new CommonMethods().createTransparentPanel();
        horizontalPanel.setLayout(new BoxLayout(horizontalPanel, BoxLayout.X_AXIS));
        horizontalPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        horizontalPanel.setMaximumSize(new Dimension(550, 250));
        horizontalPanel.setPreferredSize(new Dimension(550, 250));

        // set border for horizontal panel
        horizontalPanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.decode(panelBorderColour), 2), BorderFactory.createEmptyBorder(20, 20, 20, 20)));

        // add components to horizontal panel
        horizontalPanel.add(iconLabel);
        horizontalPanel.add(panel);

        return horizontalPanel;

    }

    /**
     * Assembles a stacked page panel that includes the page header and user input panel.
     *
     * @param pageHeaderPanel The JPanel for the page header.
     * @param userInputPanel The JPanel for user input.
     * @return A JPanel containing the stacked components.
     */
    public JPanel assembleStackedPagePanel(JPanel pageHeaderPanel, JPanel userInputPanel) {

        JPanel returnToMainMenuPanel = new CommonMethods().createTransparentPanel();
        new CommonMethods().createReturnToMainMenuButton(this, returnToMainMenuPanel, "#EDB3FC", "#B745D1");


        JPanel stackedScreenPanel = new CommonMethods().createTransparentPanel();
        stackedScreenPanel.setLayout(new BoxLayout(stackedScreenPanel, BoxLayout.Y_AXIS));

        new CommonMethods().addComponentWithSpaceToPanel(stackedScreenPanel, new JComponent[]{pageHeaderPanel}, 50, true);
        new CommonMethods().addComponentWithSpaceToPanel(stackedScreenPanel, new JComponent[]{userInputPanel}, 100, true);
        stackedScreenPanel.add(returnToMainMenuPanel);

        return stackedScreenPanel;

    }

    /**
     * Displays the profile screen for a specified username after searching the database.
     *
     * @param username The username to display profile information for.
     */
    private void userProfileScreen(String username) {

        GameToken user = db.fetchToken(username);

        new CommonMethods().configureFrameSettings(this, "Math Mamba: Instructor Dashboard", "#EDFFFA");

        JPanel pageHeaderPanel = new CommonMethods().createRectangleHeaderPanel("INSTRUCTOR DASHBOARD");

        // vertical panel containing user's info
        JPanel stackedUserInfoPanel = new CommonMethods().createTransparentPanel();
        stackedUserInfoPanel.setLayout(new BoxLayout(stackedUserInfoPanel, BoxLayout.Y_AXIS));

        createUserInfoLabel(stackedUserInfoPanel, "Username:", user.getUsername());
        createUserInfoLabel(stackedUserInfoPanel, "Current Level:", user.getStats().getLevel() + "");
        createUserInfoLabel(stackedUserInfoPanel, "Score:", user.getStats().getTotalPoints() + "");
        createUserInfoLabel(stackedUserInfoPanel, "Snake Size:", user.getStats().getSnakeSize() + "");

        stackedUserInfoPanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.decode("#22d4ce"), 2), new EmptyBorder(30, 30, 30, 30)));


        JPanel buttonPanel = makeDualButtonPanel("RETURN TO MAIN MENU", "SEARCH NEW USERNAME", "Main Menu", "Search");


        JPanel stackedScreenPanel = new CommonMethods().createTransparentPanel();
        stackedScreenPanel.setLayout(new BoxLayout(stackedScreenPanel, BoxLayout.Y_AXIS));

        new CommonMethods().addComponentWithSpaceToPanel(stackedScreenPanel, new JComponent[] {pageHeaderPanel, stackedUserInfoPanel}, 40, true);
        stackedScreenPanel.add(buttonPanel);
        stackedScreenPanel.add(Box.createRigidArea(new Dimension(0, 50)));

        setContentPane(stackedScreenPanel);

    }

    /**
     * Creates and formats a label with user information.
     *
     * @param panel The JPanel to add the label to.
     * @param headerLabelText The text for the header label.
     * @param userInfoText The user information text to display.
     */
    private void createUserInfoLabel(JPanel panel, String headerLabelText, String userInfoText) {

        Border compoundBorder = BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.decode("#ff4d91")), new EmptyBorder(10, 10, 10, 10));

        JLabel headerLabel = new JLabel(headerLabelText);
        new CommonMethods().formatComponentText(headerLabel, "Arial Rounded MT Bold", false, 20, "#ff4d91");

        JLabel label = new JLabel(userInfoText);
        label.setBorder(compoundBorder);
        new CommonMethods().formatComponentText(label, "Arial Rounded MT Bold", false, 20, "#FF82B2");

        //  label.setPreferredSize(new Dimension(300, 50));
        label.setMaximumSize(new Dimension(300, 100));

        //headerLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        headerLabel.setHorizontalAlignment(SwingConstants.CENTER);

        new CommonMethods().addComponentWithSpaceToPanel(panel, new JComponent[] {headerLabel, label}, 20, true);

    }

    /**
     * Creates a panel with two buttons, each configured with specific actions and text.
     *
     * @param button1Text The text for the first button.
     * @param button2Text The text for the second button.
     * @param button1Action The action command for the first button.
     * @param button2Action The action command for the second button.
     * @return A JPanel containing the two buttons aligned horizontally.
     */
    private JPanel makeDualButtonPanel(String button1Text, String button2Text, String button1Action, String button2Action) {

        JPanel buttonPanel = new CommonMethods().createTransparentPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
        buttonPanel.setMaximumSize(new Dimension(500, 60));
        buttonPanel.setAlignmentX(CENTER_ALIGNMENT);


        // button lets user return to main menu
        JButton button1 = new JButton(button1Text);
        button1.setOpaque(true);
        button1.setMaximumSize(new Dimension(200, 30));
        button1.setPreferredSize(new Dimension(200, 30));

        new CommonMethods().colourButton(button1, "#3DD18C", "#FFFFFF", "#E3FFBD", "#53DB6A");
        button1.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 14));
        button1.setBorder(BorderFactory.createLineBorder(Color.decode("#E3FFBD"), 2));

        button1.addActionListener(this);
        button1.setActionCommand(button1Action);



        // button lets user resume saved game
        JButton button2 = new JButton(button2Text);
        button2.setOpaque(true);
        button2.setMaximumSize(new Dimension(200, 30));
        button2.setPreferredSize(new Dimension(200, 30));

        new CommonMethods().colourButton(button2, "#3DD18C", "#FFFFFF", "#E3FFBD", "#53DB6A");
        button2.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 14));
        button2.setBorder(BorderFactory.createLineBorder(Color.decode("#E3FFBD"), 2));

        button2.addActionListener(this);
        button2.setActionCommand(button2Action);


        // add elements to button panel
        buttonPanel.add(Box.createRigidArea(new Dimension(50, 0)));
        buttonPanel.add(button1);
        buttonPanel.add(button2);

        return buttonPanel;


    }

    /**
     * Handles action events triggered by user interactions within the InstructorDash interface.
     *
     * @param evt The ActionEvent object representing the user's interaction.
     */
    public void actionPerformed(ActionEvent evt) {

        // Exit Application
        if (evt.getActionCommand().equals("Main Menu")) {
            int returnOrStay = new CommonMethods().createDialogBox(this, null, "Return To Main Menu", "Are you sure you want to return to the main menu?", new Object[]{"NO, STAY", "YES, RETURN TO MAIN MENU"});

            if(returnOrStay == 1) {
                dispose();
                new MainMenu();
            }
        }

        else if(evt.getActionCommand().equals("Search")) {
            searchUserDatabaseScreen();
        }
    }
}