package com.group78.frontend;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * The MainMenu class extends JFrame and serves as the main menu interface for the Math Mamba game.
 * It allows users to start a new game, resume an existing game, view game instructions, view the leaderboard, or exit the game.
 */
public class MainMenu extends JFrame implements ActionListener {

	/**
	 * Constructor for MainMenu. Sets up the main menu user interface with all necessary components.
	 */
	public MainMenu() {


		// Frame settings
		new CommonMethods().configureFrameSettings(this, "Math Mamba: Main Menu", "#EDFFFA");

		JPanel bannerPanel = new CommonMethods().createMainBanner(this,"MAIN MENU"); // displays banner at top (game graphic + MAIN MENU sign)


		// Button panel for main menu
		JPanel buttonsPanel = new CommonMethods().createTransparentPanel();
		buttonsPanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.Y_AXIS));

		// create buttons
		formatMainMenuButton("Start New Game", buttonsPanel, "Start New Game");
		formatMainMenuButton("Resume Game", buttonsPanel, "Resume Game");
		formatMainMenuButton("View Game Instructions", buttonsPanel, "View Game Instructions");
		formatMainMenuButton("View Leaderboard", buttonsPanel, "View Leaderboard");
		formatMainMenuButton("Exit", buttonsPanel, "Exit Application");

		buttonsPanel.setAlignmentX(Component.CENTER_ALIGNMENT);


		// Member info panel for main menu

		JPanel gameCreditsPanel = new CommonMethods().createTransparentPanel();
		gameCreditsPanel.add(new JLabel("<html><div style='text-align: center; font-family: Arial Rounded MT Bold; color: #d78de3;'>Mili Pabbi  |  Otilia Pasculescu  |  Jawaad Ahmar  |  Christopher Betancur  |  Tirth Patel"
				+ "<br>Team 78<br>Western University  |  CS2212  |  Term #2  |  Jan - April 2024</html>"));
		gameCreditsPanel.setMaximumSize(new Dimension(900, 100));


		// Final stacked panel for main menu
		JPanel stackedMainMenuPanel = new CommonMethods().createTransparentPanel();
		stackedMainMenuPanel.setLayout(new BoxLayout(stackedMainMenuPanel, BoxLayout.Y_AXIS));

		new CommonMethods().addComponentWithSpaceToPanel(stackedMainMenuPanel, new JComponent[] {bannerPanel, buttonsPanel, gameCreditsPanel}, 30, true);

		setContentPane(stackedMainMenuPanel);


	} // closes constructor

	/**
	 * Responds to action events triggered by user interactions with the main menu's buttons.
	 * Handles the functionality to start, resume the game, view instructions, view leaderboard, or exit the game.
	 *
	 * @param evt The ActionEvent containing information about the event and its source.
	 */
	public void actionPerformed(ActionEvent evt) {

		// Exit Application
		if (evt.getActionCommand().equals("Exit Application")) {

			int stayOrExit = new CommonMethods().createDialogBox(this, null, "Exit", "Are you sure you want to exit the application?", new Object[] { "NO, STAY", "YES, EXIT" });

			if (stayOrExit == 1)
				System.exit(0);
		}


		// Start or Resume Game
		else if (evt.getActionCommand().equals("Start New Game") || evt.getActionCommand().equals("Resume Game")) {

			dispose();

			if (evt.getActionCommand().equals("Start New Game")) {
				new GameLogin(false);
			}

			else {
				new GameLogin(true);
			}
		}



		// View Game Instructions
		else if (evt.getActionCommand().equals("View Game Instructions")) {

			dispose();
			new GameInstructions();
		}

		else if (evt.getActionCommand().equals("View Leaderboard")) {
			dispose();
			new Leaderboard();
		}


	} // close actionPerformed method

	/**
	 * Formats buttons for the main menu screen with appropriate styles and actions.
	 *
	 * @param buttonText The text to be displayed on the button.
	 * @param panel The JPanel where the button will be added.
	 * @param buttonAction The action command that will be set for the button.
	 */
	private void formatMainMenuButton(String buttonText, JPanel panel, String buttonAction) {

		JButton mainMenuButton = new JButton(buttonText);

		// button settings
		mainMenuButton.setMaximumSize(new Dimension(400, 75));
		mainMenuButton.setOpaque(true);


		// Set background colour, font colour, font type & border
		mainMenuButton.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 20));
		mainMenuButton.setBorder(BorderFactory.createLineBorder(Color.decode("#fc90b9"), 2));
		new CommonMethods().colourButton(mainMenuButton, "#FCB8D2",  "#FFFFFF",  "#E3FFBD",  "#2CDB78");

		mainMenuButton.setAlignmentX(Component.CENTER_ALIGNMENT);


		// Add ActionListener for button
		mainMenuButton.addActionListener(this);
		mainMenuButton.setActionCommand(buttonAction);

		new CommonMethods().addComponentWithSpaceToPanel(panel, new JComponent[] {mainMenuButton}, 3, true);


	} // close mainMenuButton method



} // closes MainMenu class
