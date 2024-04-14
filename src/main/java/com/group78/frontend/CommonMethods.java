package com.group78.frontend;

import com.group78.backend.GameToken;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Provides common utility methods used across different parts of the code base.
 * This class includes functions for creating UI components, formatting text, and handling user interactions.
 */
public class CommonMethods extends JPanel {

    /** Label to display the user's points. */
    public JLabel userPointsLabel;

    /** Label to display the size of the user's snake. */
    public JLabel userSnakeLabel;

    /** The current user's game token. */
    private GameToken user;

    /** Flag indicating whether the application is in developer mode. */
    private boolean isDeveloperMode;

    /**
     * Constructs a CommonMethods instance with developer mode enabled by default.
     * This constructor is only used by the developer.
     */
    public CommonMethods() {
        this.isDeveloperMode = true;
    }

    /**
     * Constructs a CommonMethods instance for a specific user.
     *
     * @param user The user's game token.
     */
    public CommonMethods(GameToken user) {
        this.user = user;
        this.isDeveloperMode = false;
    }

    /**
     * Creates a JPanel with a rectangle header and specified header text.
     *
     * @param headerText The text to display in the header.
     * @return A JPanel with customized graphics for the header.
     */
    public JPanel createRectangleHeaderPanel(String headerText) {

        JPanel rectHeaderPanel = new JPanel() {

            protected void paintComponent(Graphics g) {

                super.paintComponent(g);

                // Draw the rectangle
                Graphics2D g2d = (Graphics2D) g;

                int rectangleXCoordinate = (getWidth() - 350) / 2; // 300 = rectangle width
                int rectangleYCoordinate = (getHeight() - 50) / 2; // 50 = rectangle height

                // fill rectangle
                g2d.setColor(Color.decode("#22d4ce"));
                g2d.fillRoundRect(rectangleXCoordinate, rectangleYCoordinate, 350, 50, 20, 20);

                // set font and colour of text
                g2d.setColor(Color.WHITE);
                g2d.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 25));

                int textXCoordinate = rectangleXCoordinate + (350 - g2d.getFontMetrics().stringWidth(headerText)) / 2;
                int textYCoordinate = rectangleYCoordinate + ((50 - g2d.getFontMetrics().getHeight()) / 2) + g2d.getFontMetrics().getAscent();

                g2d.drawString(headerText, textXCoordinate, textYCoordinate);
            }
        };

        rectHeaderPanel.setOpaque(false);

        return rectHeaderPanel;

    }

    /**
     * Creates the main banner panel for the application, including any necessary image and rectangle header panels.
     *
     * @param frame The JFrame on which the banner is displayed.
     * @param headerText The text to display on the banner.
     * @return A JPanel that serves as the main banner of the application.
     */
    public JPanel createMainBanner(JFrame frame, String headerText) {

        JPanel bannerStackedPanel = createTransparentPanel();

        JPanel imagePanel = createTransparentPanel();
        JButton button = new JButton(new ImageIcon("src/main/java/com/group78/frontend/Images/header.png"));

        // ChANGE CHATGPT
        button.setOpaque(false); // Make the button transparent
        button.setContentAreaFilled(false); // Make the content area transparent
        button.setBorderPainted(false); // Remove the border

        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                new SecretMode();

            }
        });

        imagePanel.add(button);


        JPanel rectanglePanel = createRectangleHeaderPanel(headerText);


        imagePanel.setMaximumSize(new Dimension(450, 150));
        rectanglePanel.setMaximumSize(new Dimension(400, 75));

        bannerStackedPanel.setLayout(new BoxLayout(bannerStackedPanel, BoxLayout.Y_AXIS));

        bannerStackedPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        addComponentWithSpaceToPanel(bannerStackedPanel, new JComponent[] {imagePanel}, 20, true);
        bannerStackedPanel.add(rectanglePanel);

        bannerStackedPanel.setMaximumSize(new Dimension(600, 250));
        bannerStackedPanel.setPreferredSize(new Dimension(600, 250));

        return bannerStackedPanel;
    }


    /**
     * Creates a score board panel that displays the user's points and snake size.
     * If the game is run in developer mode then no size nor score will be shown.
     *
     * @param levelIncluded Indicates whether to include the level information in the score board.
     * @return A JPanel representing the score board.
     */
    public JPanel createScoreBoardPanel(boolean levelIncluded) {

        // panel containing user's points
        JPanel userPointsPanel = createTransparentPanel();
        userPointsPanel.setLayout(new GridBagLayout());
        userPointsPanel.setMaximumSize(new Dimension(300, 125));
        userPointsPanel.setOpaque(true);
        userPointsPanel.setBackground(Color.decode("#E3FFBD"));

        // pic of star next to point number
        ImageIcon scaledStarImage = new ImageIcon(new ImageIcon("src/main/java/com/group78/frontend/Images/star.png").getImage().getScaledInstance(75,  75,  Image.SCALE_SMOOTH));
        JLabel starImageLabel = new JLabel();
        starImageLabel.setIcon(scaledStarImage);

        // label displaying points
        if (!this.isDeveloperMode)
            userPointsLabel = new JLabel("<html><div style='text-align: center;'>" +user.getStats().getTotalPoints()+"<br>Points</html>"); // sub in user's points
        else
            userPointsLabel = new JLabel("<html><div style='text-align: center;'>DEVELOPER<br>Points</html>"); // sub in user's points

        formatGamePlayScreenText(userPointsLabel, 20, levelIncluded);

        // add components to user points panel
        userPointsPanel.add(starImageLabel);
        userPointsPanel.add(userPointsLabel);

        // panel containing user's snake size
        JPanel userSizePanel = createTransparentPanel();
        userSizePanel.setLayout(new GridBagLayout());
        userSizePanel.setMaximumSize(new Dimension(300, 125));
        userSizePanel.setOpaque(true);
        userSizePanel.setBackground(Color.decode("#E3FFBD"));

        // pic of snake next to snake size number
        ImageIcon scaledSnakeImage = new ImageIcon(new ImageIcon("src/main/java/com/group78/frontend/Images/green_snake.png").getImage().getScaledInstance(75,  75,  Image.SCALE_SMOOTH));
        JLabel snakeImageLabel = new JLabel();
        snakeImageLabel.setIcon(scaledSnakeImage);

        // label displaying snake size
        if (!this.isDeveloperMode)
            userSnakeLabel = new JLabel("<html><div style='text-align: center;'>5<br>Size</html>"); // sub in user's snake size
        else
            userSnakeLabel = new JLabel("<html><div style='text-align: center;'>DEVELOPER<br>Size</html>"); // sub in user's snake size

        formatGamePlayScreenText(userSnakeLabel, 20, levelIncluded);


        // add components to user snake size panel
        userSizePanel.add(snakeImageLabel);
        userSizePanel.add(userSnakeLabel);



        // colour borders based on whether level is included
        if(levelIncluded) {
            userPointsPanel.setBorder(BorderFactory.createLineBorder(Color.decode("#edb3fc")));
            userSizePanel.setBorder(BorderFactory.createLineBorder(Color.decode("#edb3fc")));
        }

        else {
            userPointsPanel.setBorder(BorderFactory.createLineBorder(Color.decode("#2cdb78")));
            userSizePanel.setBorder(BorderFactory.createLineBorder(Color.decode("#2cdb78")));
        }



        // panel displaying score board (user points + user snake size)
        JPanel scoreBoardPanel = createTransparentPanel();
        scoreBoardPanel.setLayout(new BoxLayout(scoreBoardPanel, BoxLayout.X_AXIS));

        scoreBoardPanel.add(userPointsPanel);
        scoreBoardPanel.add(userSizePanel);

        if(!levelIncluded) {
            scoreBoardPanel.setMaximumSize(new Dimension(600, 100));
            scoreBoardPanel.setPreferredSize(new Dimension(600, 100));
            return scoreBoardPanel;
        }



        // panel containing current level number
        JPanel levelPanel = createTransparentPanel();
        levelPanel.setLayout(new GridBagLayout());
        levelPanel.setMaximumSize(new Dimension(300, 125));
        levelPanel.setOpaque(true);
        levelPanel.setBackground(Color.decode("#E3FFBD"));
        levelPanel.setBorder(BorderFactory.createLineBorder(Color.decode("#edb3fc")));

        JLabel currentLevelLabel = null;
        if (!this.isDeveloperMode)
            currentLevelLabel = new JLabel("<html>Level: " + user.getStats().getLevel() + "</html>");
        else
            currentLevelLabel = new JLabel("<html>Level: DEVELOPER</html>");

        new CommonMethods(user).formatGamePlayScreenText(currentLevelLabel, 30, levelIncluded);

        levelPanel.add(currentLevelLabel);

        scoreBoardPanel.setMaximumSize(new Dimension(900, 100));
        scoreBoardPanel.setPreferredSize(new Dimension(900, 100));

        scoreBoardPanel.add(Box.createRigidArea(new Dimension(100, 0)));
        scoreBoardPanel.add(levelPanel);



        return scoreBoardPanel;

    }





    /**
     * Formats text for gameplay screen elements like points, size, and level labels.
     *
     * @param label The JLabel to format.
     * @param fontSize The font size to use.
     * @param levelIncluded Indicates whether level information is included.
     */
    public void formatGamePlayScreenText(JLabel label, int fontSize, boolean levelIncluded) {

        label.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, fontSize));

        if(levelIncluded)
            label.setForeground(Color.decode("#e099f2"));
        else
            label.setForeground(Color.decode("#2cdb78"));

    }

    /**
     * Creates a transparent JPanel.
     *
     * @return A JPanel with a transparent background.
     */
    public JPanel createTransparentPanel() {

        JPanel newPanel = new JPanel();
        newPanel.setOpaque(false);
        return newPanel;

    }

    /**
     * Creates a button that allows the user to return to the main menu.
     *
     * @param currentFrame The current JFrame from which the user can return to the main menu.
     * @param panel The JPanel to add the return button to.
     * @param defaultButtonBackgroundColour The default background color of the button.
     * @param hoverButtonBackgroundColour The background color of the button when hovered.
     */
    public void createReturnToMainMenuButton(JFrame currentFrame, JPanel panel, String defaultButtonBackgroundColour, String hoverButtonBackgroundColour) {


        JButton button = new JButton("RETURN TO MAIN MENU");
        button.setPreferredSize(new Dimension(180, 30));

        button.setOpaque(true);

        colourButton(button, defaultButtonBackgroundColour, "#FFFFFF", hoverButtonBackgroundColour, "#FFFFFF");
        button.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 12));
        button.setBorder(BorderFactory.createRaisedBevelBorder());

        button.setAlignmentX(Component.CENTER_ALIGNMENT);


        button.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                int returnOrStay =createDialogBox(currentFrame, null, "Return To Main Menu", "Are you sure you want to return to the main menu?", new Object[]{"NO, STAY", "YES, RETURN TO MAIN MENU"});

                if(returnOrStay == 1) {
                    currentFrame.dispose();
                    new MainMenu();
                }
            }
        });

        panel.add(button);
        panel.add((Box.createRigidArea(new Dimension(0, 3))));

    }

    /**
     * Creates a dialog box with a specified message and button options.
     *
     * @param frame The parent JFrame for the dialog box.
     * @param imageFileName The file name of an image to display in the dialog box (optional).
     * @param dialogTitle The title of the dialog box.
     * @param dialogMessage The message to display in the dialog box.
     * @param buttonOptions The options to provide as buttons in the dialog box.
     * @return An integer indicating the option chosen by the user.
     */
    public int createDialogBox(JFrame frame, String imageFileName, String dialogTitle, String dialogMessage, Object[] buttonOptions) {

        JPanel panel = new JPanel();

        // Load custom image

        if(imageFileName != null) {

            ImageIcon scaledImage = new ImageIcon(new ImageIcon(imageFileName).getImage().getScaledInstance(100,  100, Image.SCALE_DEFAULT));

            JLabel imageLabel = new JLabel(scaledImage);
            panel.add(imageLabel);

        }

        JLabel dialogMessageLabel = new JLabel(("<html><div style='text-align: center; font-family: Arial Rounded MT Bold; font-size: 12px; color: #f56c77;'>" + dialogMessage + "</html>"));
        panel.add(dialogMessageLabel);



        Object[] formattedButtonOptions = new Object[buttonOptions.length];

        for(int i = 0; i < buttonOptions.length; i++) {
            formattedButtonOptions[i] = ("<html><div style='text-align: center; font-family: Arial Rounded MT Bold; font-size: 9px; color: #2cdb78;'>" + buttonOptions[i] + "</html>");
        }


        int result = JOptionPane.showOptionDialog(frame, panel, dialogTitle,
                JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE, null, formattedButtonOptions, null);


        return result;

    }

    /**
     * Adds components to a panel with specified spacing between them.
     *
     * @param destinationPanel The panel to add the components to.
     * @param componentsToAdd The components to add to the panel.
     * @param space The amount of space to add between components.
     * @param addVerticalSpace Indicates whether the space should be added vertically.
     */
    public void addComponentWithSpaceToPanel(JPanel destinationPanel, JComponent[] componentsToAdd, int space, boolean addVerticalSpace) {

        for(int componentIndex = 0; componentIndex < componentsToAdd.length; componentIndex++) {

            destinationPanel.add(componentsToAdd[componentIndex]);

            if(addVerticalSpace)
                destinationPanel.add(Box.createRigidArea(new Dimension(0, space)));
            else
                destinationPanel.add(Box.createRigidArea(new Dimension(space, 0)));

        }
    }

    /**
     * Configures basic settings for a JFrame, including title, size, location, and background color.
     *
     * @param frame The JFrame to configure.
     * @param frameTitle The title of the frame.
     * @param backgroundColour The background color of the frame, specified in hex format (e.g., "#FFFFFF").
     */
    public void configureFrameSettings(JFrame frame, String frameTitle, String backgroundColour) {

        frame.setTitle(frameTitle);
        frame.setSize(1200, 900);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setBackground(Color.decode(backgroundColour));

        frame.setVisible(true);

    }

    /**
     * Formats the text of a Swing component with the specified font attributes and color.
     *
     * @param textComponent The component whose text is to be formatted.
     * @param fontType The name of the font to use.
     * @param boldFont A boolean indicating whether the font should be bold.
     * @param fontSize The size of the font.
     * @param fontColour The color of the font, specified in hex format (e.g., "#FFFFFF").
     */
    public void formatComponentText(JComponent textComponent, String fontType, boolean boldFont, int fontSize, String fontColour) {

        if(boldFont) {
            textComponent.setFont(new Font(fontType, Font.BOLD, fontSize));
        }
        else {
            textComponent.setFont(new Font(fontType, Font.PLAIN, fontSize));
        }

        textComponent.setForeground(Color.decode(fontColour));
        textComponent.setAlignmentX(CENTER_ALIGNMENT);

    }


    /**
     * Adds a home button to the specified JFrame. The home button allows users to return to the main menu.
     *
     * @param currentFrame The current JFrame to which the home button is added.
     * @return A JPanel containing the home button.
     */
    public JPanel addHomeButton(JFrame currentFrame) {

        JPanel homeButtonPanel = new CommonMethods(user).createTransparentPanel();
        homeButtonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));

        Image scaledHomeButton = new ImageIcon("src/main/java/com/group78/frontend/Images/homebutton.png").getImage().getScaledInstance(50,  50,  Image.SCALE_SMOOTH);

        // Create a JButton with the scaled home button image
        JButton homeButton = new JButton(new ImageIcon(scaledHomeButton));
        homeButton.setPreferredSize(new Dimension(55, 55));
        homeButton.setOpaque(false);
        homeButton.setBorder(BorderFactory.createEmptyBorder());


        homeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                int resumeOrExit = new CommonMethods(user).createDialogBox(null, null, "Return To Main Menu", "<b>ATTENTION!</b><br><br>If you exit, your progress during this round will not be saved!", new Object[] {"RESUME GAME", "RETURN TO MAIN MENU"});

                if(resumeOrExit == 1) {
                    currentFrame.dispose();
                    new MainMenu();
                }
            }
        });

        homeButtonPanel.setPreferredSize(new Dimension(55, 55));

        homeButtonPanel.add(homeButton);

        return homeButtonPanel;

    }

    /**
     * Colors a JButton with specified colors for its default state and hover state.
     *
     * @param button The JButton to color.
     * @param defaultBackgroundColour The background color of the button in its default state.
     * @param defaultForegroundColour The text color of the button in its default state.
     * @param hoverBackgroundColour The background color of the button when hovered over.
     * @param hoverForegroundColour The text color of the button when hovered over.
     */
    public void colourButton(JButton button, String defaultBackgroundColour, String defaultForegroundColour, String hoverBackgroundColour, String hoverForegroundColour) {

        button.setBackground(Color.decode(defaultBackgroundColour)); // light pink
        button.setForeground(Color.decode(defaultForegroundColour)); // white

        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(Color.decode(hoverBackgroundColour)); // light green
                button.setForeground(Color.decode(hoverForegroundColour)); // dark green
            }

            // colours of button when mouse is NOT hovering over
            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(Color.decode(defaultBackgroundColour)); // light pink
                button.setForeground(Color.decode(defaultForegroundColour)); // white
            }

        });


    }

    /**
     * Retrieves the JLabel that displays the user's points.
     *
     * @return The JLabel displaying the user's points.
     */
    public JLabel getPointsLabel() {
        return userPointsLabel;
    }

    /**
     * Retrieves the JLabel that displays the user's snake size.
     *
     * @return The JLabel displaying the user's snake size.
     */
    public JLabel getSizeLabel() {
        return userSnakeLabel;
    }

    /**
     * Sets the text of the points label to the specified string.
     *
     * @param text The text to display in the points label.
     */
    public void setPointsLabel(String text) {
        this.userPointsLabel.setText(text);
    }

    /**
     * Sets the text of the size label to the specified string.
     *
     * @param text The text to display in the size label.
     */
    public void setSizeLabel(String text) {
        this.userSnakeLabel.setText(text);
    }

}
