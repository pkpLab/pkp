package com.example.coursesystem.appClasses;

import com.example.coursesystem.dataStructures.Course;
import com.example.coursesystem.dataStructures.User;
import org.apache.commons.dbcp.BasicDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Database {

    private static Database instance;

    protected Database() {
        ds.setDriverClassName("com.mysql.cj.jdbc.Driver");
        ds.setUrl("jdbc:mysql://localhost:3306/coursesystem");
        ds.setUsername("root");
        ds.setPassword(""); //toor
        ds.setMinIdle(5);
        ds.setMaxIdle(10);
        ds.setMaxOpenPreparedStatements(100);
    }
    Database(String url, String username, String password) {
        ds.setDriverClassName("com.mysql.cj.jdbc.Driver");
        ds.setUrl(url);
        ds.setUsername(username);
        ds.setPassword(password);
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
    static void setInstance(Database newInstance) {
        instance = newInstance;
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
    public boolean userExists(String username) throws SQLException {
        Connection con = ds.getConnection();
        try {
            PreparedStatement stmt = con.prepareStatement("select 1 from users where username = ?");
            stmt.setString(1, username);
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

    public List<UserDisplayItem> getUserPrivileges(int course_id, int user_id) throws SQLException {
        Connection con = ds.getConnection();
        try {
            List<UserDisplayItem> userItems = new ArrayList<>();

            String sql = "SELECT u.user_id, name, last_name, company_name, IFNULL(umc.course_id, 0) " +
                    "FROM users u " +
                    "LEFT JOIN user_managed_courses umc ON u.user_id = umc.user_id AND umc.course_id = ? " +
                    "WHERE u.user_id != ?";

            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, course_id);
            stmt.setInt(2, user_id);

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                UserDisplayItem userItem = new UserDisplayItem();
                userItem.setUserId(rs.getInt(1));
                userItem.setName(rs.getString(2));
                userItem.setSurname(rs.getString(3));
                userItem.setCompanyName(rs.getString(4));
                if (rs.getInt(5) > 0) {
                    userItem.setHasRights(1);
                } else {
                    userItem.setHasRights(0);
                }
                userItems.add(userItem);
            }

            return userItems;
        } finally {
            closeConnection(con);
        }
    }

    public void deleteUserPrivileges(int user_id, int course_id) throws SQLException {
        Connection con = ds.getConnection();
        try {
            PreparedStatement stmt = con.prepareStatement("DELETE FROM user_managed_courses WHERE user_id = ? AND course_id = ?");
            stmt.setInt(1, user_id);
            stmt.setInt(2, course_id);
            stmt.execute();
        } finally {
            closeConnection(con);
        }
    }

    public void setUserPrivileges(int user_id, int course_id) throws SQLException {
        Connection con = ds.getConnection();
        try {
            PreparedStatement stmt = con.prepareStatement("INSERT INTO user_managed_courses(user_id, course_id) VALUES(?, ?)");
            stmt.setInt(1, user_id);
            stmt.setInt(2, course_id);
            stmt.execute();
        } finally {
            closeConnection(con);
        }
    }

    public List<Course> getEnrolledCourses(int user_id) throws SQLException {
        Connection con = ds.getConnection();
        try {
            List<Course> courses = new ArrayList<Course>();

            PreparedStatement stmt = con.prepareStatement("SELECT distinct c.course_id, c.creator_id, c.name, c.description" +
                    ", (select u.username from users u where u.user_id=c.creator_id ) as username, IFNULL(course_length,'Not mentioned')" +
                    "FROM courses c, user_enrolled_courses uc " +
                    "WHERE uc.course_id = c.course_id " +
                    "AND uc.user_id = ?");
            stmt.setInt(1, user_id);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Course course = new Course();
                course.setCourse_id(rs.getInt(1));
                course.setCreator_id(rs.getInt(2));
                course.setCourseName(rs.getString(3));
                course.setDescription(rs.getString(4));
                course.setUsername(rs.getString(5));
                course.setCourseLength(rs.getString(6));
                courses.add(course);
            }

            return courses;
        } finally {
            closeConnection(con);
        }
    }

    public void removeUserFromCourse(int user_id, int course_id) throws SQLException {
        Connection con = ds.getConnection();
        try {
            PreparedStatement stmt = con.prepareStatement("DELETE FROM user_enrolled_courses WHERE user_id = ? AND course_id = ?");
            stmt.setInt(1, user_id);
            stmt.setInt(2, course_id);
            stmt.execute();
        } finally {
            closeConnection(con);
        }
    }
    public List<User> getAllUsers() throws SQLException {
        Connection con = ds.getConnection();
        try {
            List<User> users = new ArrayList<>();

            String sql = "SELECT user_id, name, last_name, email FROM users";

            PreparedStatement stmt = con.prepareStatement(sql);

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                User user = new User();
                user.setUser_id(rs.getInt(1));
                user.setUserName(rs.getString(2));
                user.setLastName(rs.getString(3));
                user.setEmail(rs.getString(4));
                users.add(user);
            }

            return users;
        } finally {
            closeConnection(con);
        }
    }


    public void linkUserAndCourse(int user_id, int course_id) throws SQLException {
        Connection con = ds.getConnection();
        try {
            PreparedStatement stmt = con.prepareStatement("INSERT INTO user_enrolled_courses(user_id, course_id) VALUES(?, ?)");
            stmt.setInt(1, user_id);
            stmt.setInt(2, course_id);
            stmt.execute();
        } finally {
            closeConnection(con);
        }
    }
}

