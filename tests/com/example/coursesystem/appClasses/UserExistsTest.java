package com.example.coursesystem.appClasses;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class UserExistsTest {
    private Database database;

    @BeforeEach
    public void setUp() {
        database = new Database();
    }

    @Test
    void checkLoginInfoValid() throws SQLException {
        assertTrue(database.userExists("testuser"));
    }

    @Test
    void checkLoginInfoInvalid() throws SQLException {
        assertFalse(database.userExists("wronguser"));
    }
}