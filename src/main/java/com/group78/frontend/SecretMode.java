package com.group78.frontend;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * The SecretMode class extends JFrame and provides a GUI for the secret mode features of the game.
 * This includes access to the Instructor Dashboard and Developer Mode.
 */
public class SecretMode extends JFrame implements ActionListener {

    /**
     * Constructor for SecretMode class that initializes the secret mode interface.
     */
    public SecretMode() {

        new CommonMethods().configureFrameSettings(this, "Math Mamba: Secret Mode", "#EDFFFA");

        // page header banner (game image + rectangle header)
        JPanel pageHeaderPanel = new CommonMethods().createMainBanner(this,"SECRET MODE");
        pageHeaderPanel.setMaximumSize(new Dimension(600, 250));
        pageHeaderPanel.setPreferredSize(new Dimension(600, 250));


        // create button panel containing 'instructor dashboard' & 'developer mode' buttons
        JPanel secretModeButtonPanel = new CommonMethods().createTransparentPanel();
        secretModeButtonPanel.setLayout(new BoxLayout(secretModeButtonPanel, BoxLayout.X_AXIS));

        // instructor dashboard button
        JButton instructorDashboardButton = new JButton("Instructor Dashboard");
        formatSecretModeButton(instructorDashboardButton, "Instructor Dashboard");

        // developer mode button
        JButton developerModeButton = new JButton("Developer Mode");
        formatSecretModeButton(developerModeButton, "Developer Mode");

        // add buttons to button panel
        new CommonMethods().addComponentWithSpaceToPanel(secretModeButtonPanel, new JComponent[] {instructorDashboardButton}, 10, false);
        secretModeButtonPanel.add(developerModeButton);



        // creating panel containing secret mode buttons with prompt label
        JPanel secretModeOptionsPanel = new CommonMethods().createTransparentPanel();
        secretModeOptionsPanel.setLayout(new BoxLayout(secretModeOptionsPanel, BoxLayout.Y_AXIS));
        secretModeOptionsPanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.decode("#fcb8d2"), 2), BorderFactory.createEmptyBorder(20, 20, 20, 20) ));

        // create label saying 'please choose an option'
        JLabel chooseOptionPromptLabel = new JLabel("Please Choose An Option:");
        new CommonMethods().formatComponentText(chooseOptionPromptLabel, "Arial Rounded MT Bold", false, 24, "#FF82B2");

        // adding label and buttons panel to panel
        new CommonMethods().addComponentWithSpaceToPanel(secretModeOptionsPanel, new JComponent[] {chooseOptionPromptLabel}, 30, true);
        secretModeOptionsPanel.add(secretModeButtonPanel);



        // create panel containing 'return to main menu' button
        JPanel returnToMainMenuPanel = new CommonMethods().createTransparentPanel();
        new CommonMethods().createReturnToMainMenuButton(this, returnToMainMenuPanel, "#EDB3FC", "#B745D1");


        // final stacked panel for secret mode screen
        JPanel stackedSecretModePanel = new CommonMethods().createTransparentPanel();
        stackedSecretModePanel.setLayout(new BoxLayout(stackedSecretModePanel, BoxLayout.Y_AXIS));

        // add components to final stacked panel
        stackedSecretModePanel.add(Box.createRigidArea(new Dimension(0,20)));
        new CommonMethods().addComponentWithSpaceToPanel(stackedSecretModePanel, new JComponent[] {pageHeaderPanel}, 50, true);
        new CommonMethods().addComponentWithSpaceToPanel(stackedSecretModePanel, new JComponent[] {secretModeOptionsPanel}, 100, true);
        stackedSecretModePanel.add(returnToMainMenuPanel);

        setContentPane(stackedSecretModePanel);

    }

    /**
     * Responds to action events triggered by buttons in the secret mode interface.
     *
     * @param evt The ActionEvent object generated when a button is pressed.
     */
    @Override
    public void actionPerformed(ActionEvent evt) {


        if(evt.getActionCommand().equals("Instructor Dashboard")) {
            dispose();
            new InstructorDash().usernameLoginScreen();
        }

        else if(evt.getActionCommand().equals("Developer Mode")){
            dispose();
            new DeveloperMode().usernameLoginScreen();
        }
    }

    /**
     * Formats the buttons used in the secret mode to maintain a consistent look and feel.
     *
     * @param button        The JButton object that will be formatted.
     * @param buttonAction  The string command that represents the action for this button.
     */
    private void formatSecretModeButton(JButton button, String buttonAction) {

        button.setMaximumSize(new Dimension(300, 75));
        button.setPreferredSize(new Dimension(300, 75));
        button.setOpaque(true);
        new CommonMethods().colourButton(button,"#FF82B2", "#FFFFFF", "#E82771", "#FFFFFF");
        button.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 24));
        button.setBorder(BorderFactory.createLineBorder(Color.decode("#FFE3EE"), 2));
        button.setAlignmentX(Component.CENTER_ALIGNMENT);

        button.addActionListener(this);
        button.setActionCommand(buttonAction);
    }




}