package com.group78.frontend;

import com.group78.backend.*;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * The MathQuestionnaire class extends JFrame and provides a user interface for answering math questions.
 * It processes user inputs, displays math questions, and handles game logic.
 */
public class MathQuestionnaire extends JFrame implements ActionListener {
	String userAnswer; // Stores the user's response to the math question
	private MathTest math; // Represents the math test associated with the current level
	private GameToken user; // Holds the user's game token with their current stats and level
	private DatabaseReader db; // Responsible for reading and writing to the game's database
	private int qnum; // Keeps track of the current question number
	private int won; // Counts the number of correct answers

	/**
	 * Constructs a MathQuestionnaire with a specified user token.
	 * Initializes the database reader, user token, math test, question number, and win count.
	 *
	 * @param user The GameToken associated with the current user.
	 */
	public MathQuestionnaire(GameToken user) {
		this.db = new DatabaseReader();
		this.user = user;
		this.math = db.getMathTest(user.getStats().getLevel());
		this.qnum = 0;
		this.won = 0;
		questionScreen(math, qnum, won);
	}

	/**
	 * Displays the math question screen where users can input their answers.
	 * Calls subsequent screens based on user responses and the number of correct answers.
	 *
	 * @param math The MathTest object containing the questions for the current level.
	 * @param qnum The current question number.
	 * @param won  The number of questions correctly answered so far.
	 */
	public void questionScreen(MathTest math, int qnum, int won) {

		if (qnum == 5) {
			if (won >= 3) {
				if (user.getStats().getLevel() == 10) {
					gameWonScreen();
					return;
				}
				levelResultScreen(true);
				return;
			} else {
				levelResultScreen(false);
				return;
			}
		}


		new CommonMethods().configureFrameSettings(this, "Math Mamba", "#EDFFFA"); // frame settings
		JFrame currentFrame = this;


		// top banner panel
		JPanel pageHeaderPanel = new CommonMethods().createRectangleHeaderPanel("MATH QUESTIONNAIRE");


		// create Question number label
		JLabel questionNumberLabel = new JLabel("Question "+ (qnum + 1));
		new CommonMethods().formatComponentText(questionNumberLabel, "Arial Rounded MT Bold", false, 25, "#8AD0C1");


		// create question label
		JLabel questionLabel = new JLabel(math.getQuestions().get(qnum).getQuestion());
		new CommonMethods().formatComponentText(questionLabel, "Arial Rounded MT Bold", false, 18, "#30695C");


		// create question panel
		JPanel questionBoxPanel = new CommonMethods().createTransparentPanel();
		questionBoxPanel.setLayout(new BoxLayout(questionBoxPanel, BoxLayout.Y_AXIS));
		questionBoxPanel.setBorder(BorderFactory.createLineBorder(Color.decode("#A8D1D1"), 2));

		questionBoxPanel.add(Box.createRigidArea(new Dimension(0, 20)));
		new CommonMethods().addComponentWithSpaceToPanel(questionBoxPanel, new JComponent[] {questionNumberLabel}, 20, true); // add element followed by rigid area
		questionBoxPanel.add(questionLabel);

		questionBoxPanel.setPreferredSize(new Dimension(900, 300));
		questionBoxPanel.setMaximumSize(new Dimension(900, 300));



		// create user input panel
		JPanel userInputPanel = new CommonMethods().createTransparentPanel();
		userInputPanel.setLayout(new BoxLayout(userInputPanel, BoxLayout.Y_AXIS));

		JLabel inputPromptLabel = new JLabel("Please Enter Your Response Below:");
		new CommonMethods().formatComponentText(inputPromptLabel, "Arial Rounded MT Bold", false, 16, "#FF82B2");

		// input field for user's answer
		JTextField answerInputField = new JTextField(20);
		answerInputField.setAlignmentX(Component.CENTER_ALIGNMENT);
		answerInputField.setMaximumSize(new Dimension(200, 30));
		answerInputField.setBorder(BorderFactory.createLineBorder(Color.decode("#FCB8D2")));


		// click button to submit answer
		JButton submitAnswerButton = new JButton("SUBMIT");
		new CommonMethods().colourButton(submitAnswerButton, "#FFFFFF", "#FF82B2", "#FFFFFF", "#E82771");
		new CommonMethods().formatComponentText(submitAnswerButton, null, true, 12, "#FF82B2");

		submitAnswerButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if(answerInputField.getText().isEmpty()) {
					new com.group78.frontend.CommonMethods().createDialogBox(currentFrame, null, "Oops!", "Input field cannot be empty!", new Object[] {"TRY AGAIN"});
					questionScreen(math, qnum, won);
				}

				else {
					userAnswer = answerInputField.getText();
//					for(int i = 0, n = userAnswer.length() ; i < n ; i++) {
//						char c = userAnswer.charAt(i);
//						if !(c.isDigit())
//					}
					boolean allDigits = userAnswer.chars() // IntStream
							.allMatch(Character::isDigit); // Check if all characters are digits

					// TODO add if index out of bounds
					if (allDigits) {
						if (Integer.parseInt(userAnswer) == Integer.parseInt(math.getQuestions().get(qnum).getAnswer())) {
							answerResultScreen(true, Integer.parseInt(math.getQuestions().get(qnum).getAnswer()));
							user.getStats().setTotalPoints(user.getStats().getTotalPoints()+(user.getStats().getLevel()*50));

							questionScreen(math, qnum + 1, won + 1);

						} else {
							answerResultScreen(false, Integer.parseInt(math.getQuestions().get(qnum).getAnswer()));
							questionScreen(math, qnum + 1, won);
						}
					} else {
						new com.group78.frontend.CommonMethods().createDialogBox(currentFrame, null, null, "Invalid Entry!", new Object[] {"TRY AGAIN"});
						questionScreen(math, qnum, won);
					}

				}
			}
		});


		new CommonMethods().addComponentWithSpaceToPanel(userInputPanel, new JComponent[] {inputPromptLabel, answerInputField}, 15, true);
		userInputPanel.add(submitAnswerButton);


		// create scoreboard panel -- displays scores + current level
		JPanel scoreBoardPanel = new CommonMethods(user).createScoreBoardPanel(true);


		// create home button panel -- contains button taking user back to main menu
		JPanel homeButtonPanel = new CommonMethods().addHomeButton(this);


		// final stacked questionnaire panel
		JPanel stackedMathQuestionnairePanel = new CommonMethods().createTransparentPanel();
		stackedMathQuestionnairePanel.setLayout(new BoxLayout(stackedMathQuestionnairePanel, BoxLayout.Y_AXIS));

		// add components to final stacked questionnaire panel
		stackedMathQuestionnairePanel.add(Box.createRigidArea(new Dimension(0, 20)));
		new CommonMethods().addComponentWithSpaceToPanel(stackedMathQuestionnairePanel, new JComponent[] {pageHeaderPanel}, 20, true);
		new CommonMethods().addComponentWithSpaceToPanel(stackedMathQuestionnairePanel, new JComponent[] {questionBoxPanel, userInputPanel}, 50, true);
		new CommonMethods().addComponentWithSpaceToPanel(stackedMathQuestionnairePanel, new JComponent[] {scoreBoardPanel}, 10, true);
		stackedMathQuestionnairePanel.add(homeButtonPanel);

		setContentPane(stackedMathQuestionnairePanel);

	}




	/**
	 * Displays a dialog indicating whether the user's answer is correct or incorrect.
	 * Also handles the display logic for moving to the next question.
	 *
	 * @param answerIsCorrect Indicates whether the user's answer is correct.
	 * @param answer          The correct answer to the current question.
	 */
	public void answerResultScreen(boolean answerIsCorrect, int answer) {

		if(answerIsCorrect) {
			new CommonMethods().createDialogBox(this, null, "Math Questionnaire", "Correct!", new Object[] {"NEXT QUESTION"});

			//questionScreen();

		}
		else {

			new CommonMethods().createDialogBox(this, null, "Math Questionnaire", "Incorrect!<br><br>The correct answer is:<br> " + answer, new Object[] {"NEXT QUESTION"});

			//levelResultScreen(true);
//			gameWonScreen();
		}

	}



	/**
	 * Displays the result of the current level, indicating whether the user has won or lost.
	 *
	 * @param win Indicates whether the user has won the level.
	 */
	public void levelResultScreen(boolean win) {

		// TODO next level loop
		System.out.println("levelresult");
		UserStats stats = user.getStats();
		new CommonMethods().configureFrameSettings(this, "Math Mamba", "#EDFFFA"); // frame settings


		JPanel levelCompletedPanel = new JPanel();
		levelCompletedPanel.setLayout(new BoxLayout(levelCompletedPanel, BoxLayout.Y_AXIS));

		levelCompletedPanel.setOpaque(true);
		levelCompletedPanel.setBackground(Color.decode("#E3FFBD"));
		levelCompletedPanel.setBorder(BorderFactory.createLineBorder(Color.decode("#FF82B2"), 3));

		levelCompletedPanel.setMaximumSize(new Dimension(800, 500));
		levelCompletedPanel.setPreferredSize(new Dimension(800, 500));


		JLabel resultImageLabel;

		if (win){
			resultImageLabel = new JLabel(new ImageIcon("src/main/java/com/group78/frontend/Images/three_stars.png"));
			stats.setLevel(stats.getLevel()+1);
			db.saveGame(user);
//			if (db.saveGame(user)) {
//				System.out.println("hello");
//			} else {
//				System.out.println("bye");
//			}


		} else {
			resultImageLabel = new JLabel(new ImageIcon("src/main/java/com/group78/frontend/Images/sad_face.png"));
		}


		JPanel resultImagePanel = new CommonMethods().createTransparentPanel();
		resultImagePanel.add(resultImageLabel);


		JLabel levelCompletedLabel;

		// add text to panel indicating win or loss
		if (win)
			levelCompletedLabel = new JLabel("<html><div style='text-align: center;'>LEVEL "+ (user.getStats().getLevel()-1) + "<br><br>COMPLETED!</html>");
		else
			levelCompletedLabel = new JLabel("<html><div style='text-align: center;'>GAME OVER<br><br>LEVEL "+ (user.getStats().getLevel()-1) + " FAILED!</html>");

		new CommonMethods().formatComponentText(levelCompletedLabel, "Arial Rounded MT Bold", false, 25, "#2CDB78");

		JPanel textPanel = new CommonMethods().createTransparentPanel();
		textPanel.add(levelCompletedLabel);


		// display final score
		JPanel scoreBoardPanel = new CommonMethods(user).createScoreBoardPanel(false);
		scoreBoardPanel.setMaximumSize(new Dimension(400, 100));


		// add sub-panels to levelCompletedPanel
		levelCompletedPanel.add(Box.createRigidArea(new Dimension(0, 30)));
		levelCompletedPanel.add(resultImagePanel);
		levelCompletedPanel.add(textPanel);
		new CommonMethods().addComponentWithSpaceToPanel(levelCompletedPanel, new JComponent[] {scoreBoardPanel}, 30, true);


		JPanel stackedResultPanel = new CommonMethods().createTransparentPanel();
		stackedResultPanel.setLayout(new BoxLayout(stackedResultPanel, BoxLayout.Y_AXIS));

		stackedResultPanel.add(Box.createRigidArea(new Dimension(0, 75)));
		new CommonMethods().addComponentWithSpaceToPanel(stackedResultPanel, new JComponent[] {levelCompletedPanel}, 75, true);


		// if win, add button option to move to next level
		if (win) {

			JButton button = new JButton("NEXT LEVEL");

			button.setMaximumSize(new Dimension(300, 50));

			button.setOpaque(true);
			new CommonMethods().colourButton(button, "#FF82B2", "#FFFFFF", "#E82771", "#FFFFFF");
			button.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 20));

			button.setAlignmentX(Component.CENTER_ALIGNMENT);

			button.setBorder(BorderFactory.createRaisedBevelBorder());

			button.addActionListener(this);
			button.setActionCommand("Next Level");

			stackedResultPanel.add(button);
		}

		// if loss, add options to return to main menu or exit application
		else
			stackedResultPanel.add(gameCompleteButtonPanel());


		setContentPane(stackedResultPanel);

	}



	/**
	 * Displays the final win screen when the user completes all levels and wins the game.
	 */
	public void gameWonScreen() {

		new CommonMethods(user).configureFrameSettings(this, "Math Mamba", "#EDFFFA"); // frame settings

		JPanel stackedGameWonPanel = new CommonMethods(user).createTransparentPanel();
		stackedGameWonPanel .setLayout(new BoxLayout(stackedGameWonPanel , BoxLayout.Y_AXIS));

		JPanel youWonBannerPanel = new CommonMethods(user).createRectangleHeaderPanel("YOU WIN!");

		JLabel trophyLabel = new JLabel(new ImageIcon((new ImageIcon("src/main/java/com/group78/frontend/Images/trophy.gif")).getImage().getScaledInstance(350,  350, Image.SCALE_DEFAULT)));
		trophyLabel.setAlignmentX(CENTER_ALIGNMENT);

		JPanel scoreBoardPanel = new CommonMethods(user).createScoreBoardPanel(false);

		new CommonMethods().addComponentWithSpaceToPanel(stackedGameWonPanel, new JComponent[] {youWonBannerPanel, trophyLabel}, 20, true);
		new CommonMethods().addComponentWithSpaceToPanel(stackedGameWonPanel , new JComponent[] {scoreBoardPanel}, 40, true);
		new CommonMethods().addComponentWithSpaceToPanel(stackedGameWonPanel , new JComponent[] {gameCompleteButtonPanel()}, 100, true);

		setContentPane(stackedGameWonPanel);

	}



	/**
	 * Handles action events generated by the interface's buttons.
	 *
	 * @param evt The action event generated by the button press.
	 */
	@Override
	public void actionPerformed(ActionEvent evt) {

		if (evt.getActionCommand().equals("Return To Main Menu")) {


			int resumeOrExit = new CommonMethods().createDialogBox(this, null, "Return To Main Menu", "Are you sure you want to return to the main menu?", new Object[] {"RETURN TO MAIN MENU", "NO, I'LL STAY"});

			if (resumeOrExit == 0) {
				dispose();
				new MainMenu();
			}

		}

		else if (evt.getActionCommand().equals("Next Level")) {

			dispose();
			new SnakeGamePlay(user).startGamePlayScreen();

		}


		else if(evt.getActionCommand().equals("Exit Application")) {

			int stayOrExit = new CommonMethods().createDialogBox(this, null, "Exit", "Are you sure you want to exit the application?", new Object[] {"NO, STAY", "YES, EXIT"});

			if (stayOrExit == 1)
				System.exit(0);
		}

	}


	/**
	 * Creates a panel with buttons for ending the game and returning to the main menu.
	 *
	 * @return A JPanel containing the game completion buttons.
	 */
	public JPanel gameCompleteButtonPanel() {


		JPanel stackedButtonPanel = new CommonMethods().createTransparentPanel();
		stackedButtonPanel.setLayout(new BoxLayout(stackedButtonPanel, BoxLayout.X_AXIS));
		stackedButtonPanel.setMaximumSize(new Dimension(500, 60));
		stackedButtonPanel.setAlignmentX(CENTER_ALIGNMENT);

		// format "return to main menu" button
		JButton returnToMainMenuButton = new JButton("RETURN TO MAIN MENU");
		returnToMainMenuButton.setOpaque(true);
		new CommonMethods().colourButton(returnToMainMenuButton, "#FF82B2", "#FFFFFF", "#E82771", "#FFFFFF");
		returnToMainMenuButton.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 14));
		returnToMainMenuButton.setBorder(BorderFactory.createLineBorder(Color.decode("#FFE3EE"), 2));

		returnToMainMenuButton.setMaximumSize(new Dimension(200, 30));

		returnToMainMenuButton.addActionListener(this);
		returnToMainMenuButton.setActionCommand("Return To Main Menu");



		JButton resumeGameButton = new JButton("EXIT");
		resumeGameButton.setOpaque(true);

		new CommonMethods().colourButton(resumeGameButton, "#FFBDD6", "#FFFFFF", "#E82771", "#FFFFFF");
		resumeGameButton.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 14));
		resumeGameButton.setBorder(BorderFactory.createLineBorder(Color.decode("#ffe3ee"), 2));

		resumeGameButton.setMaximumSize(new Dimension(200, 30));

		resumeGameButton.addActionListener(this);
		resumeGameButton.setActionCommand("Exit Application");



		stackedButtonPanel.add(Box.createRigidArea(new Dimension(50, 0)));
		stackedButtonPanel.add(returnToMainMenuButton);
		stackedButtonPanel.add(resumeGameButton);
		stackedButtonPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

		return stackedButtonPanel;
	}

}

