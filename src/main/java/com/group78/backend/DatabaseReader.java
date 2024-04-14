package com.group78.backend;

// import libraries
import java.util.ArrayList;
import java.util.List;
import java.io.FileWriter;
import java.io.IOException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import java.io.FileReader;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * class reads and writes to json database
 * @author jawaad ahmar
 * */
public class DatabaseReader {

    // filepath for users json file
    private static final String USER_FILE = "src/main/users.json";

    // filepath for math tests json file
    private static final String MATH_TESTS_FILE = "src/main/math.json";

    // initialize json parser tool
    private JSONParser parser = new JSONParser();

    /***
     * method fetches a user token
     * @param username - user's username
     * @return
     */
    public GameToken fetchToken(String username) {
        try (FileReader reader = new FileReader(USER_FILE)) {
            JSONArray tokenArray = (JSONArray) parser.parse(reader);
            for (Object o : tokenArray) {
                JSONObject token = (JSONObject) o;
                String userName = (String) token.get("username");
                if (userName.equals(username)) {
                    int level = ((Long) token.get("level")).intValue();
                    int snakeSize = ((Long) token.get("snakeSize")).intValue();
                    int totalPoints = ((Long) token.get("totalPoints")).intValue();
                    UserStats stats = new UserStats(level, snakeSize, totalPoints);
                    return new GameToken(username, stats);
                }
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * method saves game to JSON database
     * @param token - user game token
     * @return true if user is saved successfully, false if user exists or save failed
     * */

    public void saveGame(GameToken token) {
        JSONArray tokenArray;
        try (FileReader reader = new FileReader(USER_FILE)) {
            tokenArray = (JSONArray) parser.parse(reader);
        } catch (IOException | ParseException e) {
            tokenArray = new JSONArray();
        }
        JSONObject gameObj = new JSONObject();
        gameObj.put("username", token.getUsername());
        gameObj.put("level", token.getStats().getLevel());
        gameObj.put("snakeSize", token.getStats().getSnakeSize());
        gameObj.put("totalPoints", token.getStats().getTotalPoints());

        // Check if the user already exists
        JSONObject existingUser = null;
        for (Object obj : tokenArray) {
            JSONObject userObj = (JSONObject) obj;
            if (token.getUsername().equals(userObj.get("username"))) {
                existingUser = userObj;
                break;
            }
        }

        if (existingUser != null) {
            // User exists, update their data
            existingUser.put("level", token.getStats().getLevel());
            existingUser.put("snakeSize", token.getStats().getSnakeSize());
            existingUser.put("totalPoints", token.getStats().getTotalPoints());
        } else {
            // New user, add to array
            tokenArray.add(gameObj);
        }

        // Write the updated array back to the file
        try (FileWriter file = new FileWriter(USER_FILE)) {
            file.write(tokenArray.toJSONString());
        } catch (IOException e) {
            e.printStackTrace();
            //return false;
        }
        //return true;
    }

    /**
     * Attempts to create a new game entry for a user in the JSON database.
     * If the user already exists, no new entry is created.
     *
     * @param username The username for which to create a new game entry.
     * @return true if a new game was successfully created, false if the user already exists or if an error occurred.
     */
    public boolean createGame(String username) {
        if (isUser(username)) {
            return false;
        }
        JSONArray tokenArray;
        try (FileReader reader = new FileReader(USER_FILE)) {
            tokenArray = (JSONArray) parser.parse(reader);
        } catch (IOException | ParseException e) {
            tokenArray = new JSONArray();
        }
        JSONObject gameObj = new JSONObject();
        gameObj.put("username", username);
        gameObj.put("level", 1);
        gameObj.put("snakeSize", 1);
        gameObj.put("totalPoints", 0);
        tokenArray.add(gameObj);
        try (FileWriter file = new FileWriter(USER_FILE)) {
            file.write(tokenArray.toJSONString());
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * method fetches a math test
     * @param level - of the math test
     * @return MathTest object
     * */
    public MathTest getMathTest(int level) {
        JSONParser parser = new JSONParser();
        try {
            JSONArray testsArray = (JSONArray) parser.parse(new FileReader(MATH_TESTS_FILE));
            for (Object o : testsArray) {
                JSONObject test = (JSONObject) o;
                long lvl = (long) test.get("level");
                if (lvl == level) {
                    JSONArray questionsArray = (JSONArray) test.get("questions");
                    List<Question> questions = new ArrayList<>();
                    for (Object q : questionsArray) {
                        JSONObject questionObj = (JSONObject) q;
                        String question = (String) questionObj.get("question");
                        String answer = (String) questionObj.get("answer");
                        questions.add(new Question(question, answer));
                    }
                    return new MathTest((int) lvl, questions);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * method fetches all users (dashboard)
     * @return List of GameToken objects
     */
    public List<GameToken> fetchAll() {
        List<GameToken> gameTokens = new ArrayList<>();
        try (FileReader reader = new FileReader(USER_FILE)) {
                JSONArray tokenArray = (JSONArray) parser.parse(reader);
                for (Object o : tokenArray) {
                    JSONObject token = (JSONObject) o;
                    String username = (String) token.get("username");
                    int level = ((Long) token.get("level")).intValue();
                    int snakeSize = ((Long) token.get("snakeSize")).intValue();
                    int totalPoints = ((Long) token.get("totalPoints")).intValue();
                    UserStats stats = new UserStats(level, snakeSize, totalPoints);
                    gameTokens.add(new GameToken(username, stats));
                }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        return gameTokens;
    }

    /**
     * method checks if user exists or not
     * @param username - of user to be checked
     * @return true if user exists, false otherwise
     */
    public boolean isUser(String username) {
            try (FileReader reader = new FileReader(USER_FILE)) {
            JSONArray tokenArray = (JSONArray) parser.parse(reader);
            for (Object o : tokenArray) {
                JSONObject token = (JSONObject) o;
                String userName = (String) token.get("username");
                if (userName.equals(username)) {
                    return true;
                }
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        return false;
    }

}

