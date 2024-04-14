package com.group78;

import com.group78.backend.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class DatabaseReader_test {
    private DatabaseReader db;

    @BeforeEach
    void setUp() {
        db = new DatabaseReader();
    }

    @Test
    void fetchToken_test() {
        GameToken expectedToken = new GameToken("testUser", new UserStats(1, 1, 0));
        db.saveGame(expectedToken);
        GameToken actualToken = db.fetchToken("testUser");
        assertEquals(expectedToken.getUsername(), actualToken.getUsername(), "Fetched token username should match.");
    }

    @Test
    void getMathTest_test() {
        MathTest level1Test = db.getMathTest(1);
        //level1Test.getQuestions();
        String expectedQuestion = level1Test.getQuestions().get(0).getQuestion();
        String actualQuestion = "2 + 3 = ?";
        assertEquals(expectedQuestion, actualQuestion, "Math questions should match");
    }

    @Test
    void isUser_test() {
        GameToken expectedToken = new GameToken("testUser", new UserStats(1, 1, 0));
        db.saveGame(expectedToken);
        boolean testUser = db.isUser("testUser");
        assertTrue(testUser);
    }


}

