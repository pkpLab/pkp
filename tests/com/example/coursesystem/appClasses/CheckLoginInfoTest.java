package com.example.coursesystem.appClasses;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class CheckLoginInfoTest {
    private Database database;

    @BeforeEach
    public void setUp() {
        database = new Database();
    }

    @Test
    void checkLoginInfoValid() throws SQLException {
        assertTrue(database.checkLoginInfo("testuser", "test"));
    }

    @Test
    void checkLoginInfoInvalid() throws SQLException {
        assertFalse(database.checkLoginInfo("wronguser", "wrongpassword"));
    }

}