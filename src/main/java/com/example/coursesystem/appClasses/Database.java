package com.example.coursesystem.appClasses;

import com.example.coursesystem.dataStructures.User;
import org.apache.commons.dbcp.BasicDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Database {

    private static Database instance;

    private Database() {
        ds.setDriverClassName("com.mysql.cj.jdbc.Driver");
        ds.setUrl("jdbc:mysql://localhost:3306/coursesystem");
        ds.setUsername("root");
        ds.setPassword(""); //toor
        ds.setMinIdle(5);
        ds.setMaxIdle(10);
        ds.setMaxOpenPreparedStatements(100);
    }

    public static Database getInstance() {
        if (instance == null) {
            instance = new Database();
        }
        return instance;
    }
    private BasicDataSource ds = new BasicDataSource();

    public void closeConnection(Connection con) throws SQLException {
        if (con != null) {
            con.close();
        }
    }

    public boolean checkLoginInfo(String username, String password) throws SQLException {
        Connection con = ds.getConnection();
        try {
            PreparedStatement stmt = con.prepareStatement("SELECT 1 FROM users WHERE username = ? AND password = ?");
            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return true;
            } else {
                return false;
            }
        } finally {
            closeConnection(con);
        }
    }

    public void createUser(User user, String password) throws SQLException {
        Connection con = ds.getConnection();
        try {
            PreparedStatement stmt = con.prepareStatement("INSERT INTO users(username, password, name, last_name, email, company_name, user_type) " +
                    "VALUES(?, ?, ?, ?, ?, ?, ?)");
            stmt.setString(1, user.getLogin());
            stmt.setString(2, password);
            stmt.setString(3, user.getUserName());
            stmt.setString(4, user.getLastName());
            stmt.setString(5, user.getEmail());
            stmt.setString(6, user.getCompanyName());
            stmt.setInt(7,user.getUserType());
            stmt.execute();
        } finally {
            closeConnection(con);
        }
    }
    public int getUserId(String login, String password) throws SQLException {
        Connection con = ds.getConnection();
        try {
            int userId = -1;

            PreparedStatement stmt = con.prepareStatement("SELECT user_id FROM users WHERE username = ? AND password = ?");
            stmt.setString(1, login);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                userId = rs.getInt(1);
            }

            return userId;
        } finally {
            closeConnection(con);
        }
    }
}

