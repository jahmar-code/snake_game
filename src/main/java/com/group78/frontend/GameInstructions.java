package com.group78.frontend;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Displays the game instructions in a separate window. This class provides an overview of how to play the game,
 * including the controls, objectives, and additional rules. It also includes information about the math questionnaire
 * that follows the snake game.
 */
public class GameInstructions extends JFrame implements ActionListener {

	/**
	 * Constructs the GameInstructions window and initializes its UI components.
	 */
	public GameInstructions() {

		new CommonMethods().configureFrameSettings(this, "Math Mamba: View Game Instructions", "#EDFFFA"); // Frame Settings

		JPanel bannerPanel = new CommonMethods().createMainBanner(this,"GAME INSTRUCTIONS");


		// rectangle panel containing written instructions
		JPanel instructionsPanel = new JPanel(new BorderLayout());
		instructionsPanel.setBackground(Color.decode("#E3FFBD"));

		JLabel instructionsLabel = new JLabel("<html><div style='text-align: center;'><font color=#f01d86>SNAKE GAMEPLAY:</font><br><br>- You are the <font color=#F5CF51>YELLOW</font> snake, rivalling against the opposing <font color=#FF1212>RED</font> serpents, with the goal of eliminating all opponents.<br><br>" +
				"- Use your arrow keys to intuitively navigate your snake across the game board.<br><br>" +
				"- Collect foods (<font color=#3dabff>BLUE</font> tiles) across the grid to increase your snake's size while avoiding obstacles (<font color=#FF9EBA>PINK</font> tiles) at all costs!<br><br>" +
				"- For a rival (<font color=#FF1212>RED</font>) snake to be eliminated, their head must collide with your snake's body.<br><br>" +
				"- If your (<font color=#F5CF51>YELLOW</font>) snake collides with an enemy's body, its own body, an obstacle, or the board boundary, you will be eliminated! <br><br>" +
				"- A collision between the heads of two snakes will result in the elimination of both snakes.<br><br><br>" +
				"<font color=#f01d86>MATH QUESTIONNAIRE:</font><br><br>" +
				"- Once you have completed snake gameplay, you will engage in a thrilling and stimulating math questionnaire.<br><br>" +
				"- To complete the level, you must correctly answer at least three out of the five questions.<br><br><br><br>" +
				"<font color=#f556a5>This action-packed game takes you on a thrilling adventure with strategic gameplay, challenging<br>" +
				"math questionnaires, and the pursuit of ultimate triumph!<br>" +
				"Good luck, player!</font></html>");
		//instructionsLabel.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 12));
		new CommonMethods().formatComponentText(instructionsLabel, "Arial Rounded MT Bold", false, 12, "#07ab4e");
		instructionsLabel.setAlignmentX(JComponent.CENTER_ALIGNMENT);
		instructionsLabel.setHorizontalAlignment(SwingConstants.CENTER);
		instructionsPanel.add(instructionsLabel, BorderLayout.CENTER);


		instructionsPanel.setMaximumSize(new Dimension(800, 450));
		instructionsPanel.setPreferredSize(new Dimension(800, 450));
		instructionsPanel.setOpaque(true);
		instructionsPanel.setBackground(Color.decode("#E3FFBD"));
		instructionsPanel.setBorder(BorderFactory.createLineBorder(Color.decode("#FF82B2"), 2));


		// panel containing 'return to main menu' panel
		JPanel returnToMainMenuPanel = new CommonMethods().createTransparentPanel();
		new CommonMethods().createReturnToMainMenuButton(this, returnToMainMenuPanel, "#EDB3FC", "#B745D1");
		returnToMainMenuPanel.setMaximumSize(new Dimension(450,75));


		// final stacked panel for game instructions
		JPanel instructionsStackedPanel = new CommonMethods().createTransparentPanel();
		instructionsStackedPanel.setLayout(new BoxLayout(instructionsStackedPanel, BoxLayout.Y_AXIS));

		instructionsStackedPanel.add(bannerPanel);
		instructionsStackedPanel.add(instructionsPanel);
		instructionsStackedPanel.add(returnToMainMenuPanel);

		setContentPane(instructionsStackedPanel);

	}




	/**
	 * Stacks various UI components (banner, instructions, return button) in a single panel.
	 *
	 * @return A JPanel containing all the stacked components.
	 */
	public void actionPerformed(ActionEvent evt) {

		// return to main menu
		if (evt.getActionCommand().equals("Main Menu")) {

			int returnOrStay = new CommonMethods().createDialogBox(this, null, "Return To Main Menu", "Are you sure you want to return to the main menu?", new Object[] {"NO, STAY", "YES, RETURN TO MAIN MENU"});

			if(returnOrStay == 1) {
				dispose();
				new MainMenu();
			}
		}
	}


}
