package com.group78.frontend;

import com.group78.backend.DatabaseReader;
import com.group78.backend.GameToken;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;

/**
 * Displays a leaderboard for the top players of the Snake game, showcasing their usernames and scores.
 * The leaderboard displays information for up to three top players, sorted by their total points in descending order.
 */
public class Leaderboard extends JFrame implements ActionListener {

    /** Database reader instance for fetching player data. */
    private DatabaseReader databaseReader = new DatabaseReader();

    /** List of players to be displayed on the leaderboard, initialized by fetching and sorting player data. */
    List<GameToken> players = listPlayers();

    /**
     * Constructs the Leaderboard frame, initializes the GUI components, and displays the top players.
     */
    public Leaderboard() {

        //databaseReader = new DatabaseReader();

        setTitle("Snake game: View Leaderboard");
        setSize(1000,800);
        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        JPanel banner = new CommonMethods().createMainBanner(this, "LEADERBOARD");

        JPanel player1 = new JPanel() {
            protected void paintComponent(Graphics graphics) {
                super.paintComponent(graphics);
                Graphics graphics2d = (Graphics2D) graphics;
                int rectWidth = 500;
                int rectHeight = 50;
                int rectX = (getWidth() - rectWidth) / 2;
                int rectY = (getHeight() - rectHeight) / 2;

                graphics2d.setColor(Color.decode("#E3FFBD"));

                graphics2d.fillRoundRect(rectX, rectY, 500, 50, 20, 20);

                graphics2d.setColor(Color.decode("#FD8A8A"));
                graphics2d.drawRoundRect(rectX, rectY, 500, 50, 20, 20);

                // set font and colour of text
                graphics2d.setColor(Color.decode("#198f07"));
                graphics2d.setFont(new Font("Arial", Font.BOLD, 20));

                //added now

                if(!players.isEmpty()){
                    GameToken player1Token = players.get(0);
                    String player1User = player1Token.getUsername();
                    int player1Score = player1Token.getStats().getTotalPoints();
                    String username = (" Player: " + player1User);
                    String points = ("Points: " + player1Score);

                    int textX = rectX;
                    int textY = rectY + ((rectHeight - graphics2d.getFontMetrics().getHeight()) / 2) + graphics2d.getFontMetrics().getAscent();
                    graphics2d.drawString(username, textX, textY);

                    int textX2 = rectX + rectWidth - graphics2d.getFontMetrics().stringWidth(" player 1")-80;
                    int textY2 = rectY + ((rectHeight - graphics2d.getFontMetrics().getHeight()) / 2) + graphics2d.getFontMetrics().getAscent();
                    graphics2d.drawString(points, textX2,textY2);
                }

            }

        };

        JPanel player2 = new JPanel() {
            protected void paintComponent(Graphics graphics) {
                super.paintComponent(graphics);
                Graphics graphics2d = (Graphics2D) graphics;
                int rectWidth = 500;
                int rectHeight = 50;
                int rectX = (getWidth() - rectWidth) / 2;
                int rectY = (getHeight() - rectHeight) / 2;

                graphics2d.setColor(Color.decode("#E3FFBD"));

                graphics2d.fillRoundRect(rectX, rectY, 500, 50, 20, 20);

                graphics2d.setColor(Color.decode("#FD8A8A"));
                graphics2d.drawRoundRect(rectX, rectY, 500, 50, 20, 20);

                // set font and colour of text
                graphics2d.setColor(Color.decode("#198f07"));
                graphics2d.setFont(new Font("Arial", Font.BOLD, 20));

                if(!players.isEmpty()){
                    GameToken player2Token = players.get(1);
                    String player2User = player2Token.getUsername();
                    int player2Score = player2Token.getStats().getTotalPoints();
                    String username = (" Player: " + player2User);
                    String points = ("Points: " + player2Score);

                    int textX = rectX;
                    int textY = rectY + ((rectHeight - graphics2d.getFontMetrics().getHeight()) / 2) + graphics2d.getFontMetrics().getAscent();
                    graphics2d.drawString(username, textX, textY);

                    int textX2 = rectX + rectWidth - graphics2d.getFontMetrics().stringWidth(" player 2")-80;
                    int textY2 = rectY + ((rectHeight - graphics2d.getFontMetrics().getHeight()) / 2) + graphics2d.getFontMetrics().getAscent();
                    graphics2d.drawString(points, textX2,textY2);
                }

            }

        };
        JPanel player3 = new JPanel() {
            protected void paintComponent(Graphics graphics) {
                super.paintComponent(graphics);
                Graphics graphics2d = (Graphics2D) graphics;
                int rectWidth = 500;
                int rectHeight = 50;
                int rectX = (getWidth() - rectWidth) / 2;
                int rectY = (getHeight() - rectHeight) / 2;

                graphics2d.setColor(Color.decode("#E3FFBD"));

                graphics2d.fillRoundRect(rectX, rectY, 500, 50, 20, 20);

                graphics2d.setColor(Color.decode("#FD8A8A"));
                graphics2d.drawRoundRect(rectX, rectY, 500, 50, 20, 20);

                // set font and colour of text
                graphics2d.setColor(Color.decode("#198f07"));
                graphics2d.setFont(new Font("Arial", Font.BOLD, 20));

                if(!players.isEmpty()){
                    GameToken player3Token = players.get(2);
                    String player3User = player3Token.getUsername();
                    int player3Score = player3Token.getStats().getTotalPoints();
                    String username = (" Player: " + player3User);
                    String points = ("Points: " + player3Score);

                    int textX = rectX;
                    int textY = rectY + ((rectHeight - graphics2d.getFontMetrics().getHeight()) / 2) + graphics2d.getFontMetrics().getAscent();
                    graphics2d.drawString(username, textX, textY);

                    int textX2 = rectX + rectWidth - graphics2d.getFontMetrics().stringWidth(" player 3")-80;
                    int textY2 = rectY + ((rectHeight - graphics2d.getFontMetrics().getHeight()) / 2) + graphics2d.getFontMetrics().getAscent();
                    graphics2d.drawString(points, textX2,textY2);
                }

            }

        };



        //to return to main menu

        JPanel homeButtonPanel = new JPanel();
        JButton homeButton = new JButton("Return To Main Menu");
        homeButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        homeButton.addActionListener(this);
        homeButton.setActionCommand("Main Menu");
        homeButtonPanel.add(homeButton);

        homeButtonPanel.setMaximumSize(new Dimension(450,75));
        //homeButtonPanel.setBorder(BorderFactory.createTitledBorder("Buttons"));

        JPanel stackedPanel = new JPanel();
        stackedPanel.setLayout(new BoxLayout(stackedPanel, BoxLayout.Y_AXIS));

        stackedPanel.add(Box.createRigidArea(new Dimension(0,30)));
        stackedPanel.add(banner);

        stackedPanel.add(player1);
        stackedPanel.add(player2);
        stackedPanel.add(player3);
        stackedPanel.add(homeButtonPanel);

        setContentPane(stackedPanel);
    }

    /**
     * Fetches and sorts all players by their total points in descending order, then returns the top three players.
     * If there are fewer than three players, returns all available players.
     *
     * @return A list of GameToken objects for the top three (or fewer) players.
     */
    private List<GameToken> listPlayers(){
        List<GameToken> allPlayers = databaseReader.fetchAll();
        Comparator<GameToken> comparing = Comparator.comparingInt(i -> i.getStats().getTotalPoints());
        // sorts the scores
        allPlayers.sort(comparing);
        // list was in ascending, so need it to reverse to do descending
        Collections.reverse(allPlayers);
        // taking the top three
        List<GameToken> allThree = allPlayers.subList(0,Math.min(3,allPlayers.size()));

        return allThree;
    }

    /**
     * Handles action events, specifically the event triggered by pressing the "Return To Main Menu" button.
     * Confirms the action with the user before disposing of the leaderboard frame and opening the main menu.
     *
     * @param actionEvent The ActionEvent object representing the user's interaction.
     */
    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if(actionEvent.getActionCommand().equals("Main Menu")) {
            if((JOptionPane.showConfirmDialog(null, "Are you sure you want to return to the main menu?", "Return To Main Menu",
                    JOptionPane.YES_NO_OPTION)) == 0) {
                dispose();
                MainMenu ret = new MainMenu();
                ret.setVisible(true);
            }
        }
    }

}