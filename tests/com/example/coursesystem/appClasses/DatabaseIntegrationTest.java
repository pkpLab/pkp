package com.example.coursesystem.appClasses;

import com.example.coursesystem.dataStructures.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DatabaseIntegrationTest {
    private Database database;

    @BeforeEach
    public void setUp() {
        database = new Database();
    }

    @Test
    public void testCheckLoginInfo() throws SQLException {
        User user = new User();
        user.setLogin("test_user");
        user.setUserName("Test");
        user.setLastName("User");
        user.setEmail("test_user@example.com");
        user.setCompanyName("Example Company");
        database.createUser(user, "password");

        assertTrue(database.checkLoginInfo("test_user", "password"));

        assertFalse(database.checkLoginInfo("test_user", "wrong_password"));

        assertFalse(database.checkLoginInfo("wrong_username", "password"));
    }

    @Test
    public void testCreateUser() throws SQLException {
        User user = new User();
        user.setLogin("test_user");
        user.setUserName("Test");
        user.setLastName("User");
        user.setEmail("test_user@example.com");
        user.setCompanyName("Example Company");
        database.createUser(user, "password");

        assertTrue(database.userExists(user.getLogin()));

        int userId = database.getUserId(user.getLogin(), "password");
        assertEquals(user.getLogin(), "test_user");
    }

}
