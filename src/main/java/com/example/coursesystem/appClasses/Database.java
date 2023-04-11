package com.example.coursesystem.appClasses;

import com.example.coursesystem.dataStructures.Course;
import com.example.coursesystem.dataStructures.File;
import com.example.coursesystem.dataStructures.Folder;
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

    public List<Course> getManagedCourses(int user_id) throws SQLException {
        Connection con = ds.getConnection();
        try {
            List<Course> courses = new ArrayList<Course>();

            PreparedStatement stmt = con.prepareStatement("SELECT DISTINCT c.course_id, c.creator_id, c.name, c.description " +
                    "FROM courses c, user_managed_courses uc " +
                    "WHERE ((uc.course_id = c.course_id AND uc.user_id = ?) OR c.creator_id = ?);");
            stmt.setInt(1, user_id);
            stmt.setInt(2, user_id);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Course course = new Course();
                course.setCourse_id(rs.getInt(1));
                course.setCreator_id(rs.getInt(2));
                course.setCourseName(rs.getString(3));
                course.setDescription(rs.getString(4));
                courses.add(course);
            }

            return courses;
        } finally {
            closeConnection(con);
        }
    }

    public List<Folder> getFoldersForParent(int course_id, int parent_id) throws SQLException {
        Connection con = ds.getConnection();
        try {
            List<Folder> folders = new ArrayList<Folder>();

            String sql = "SELECT folder_id, course_id, name, parent_id " +
                    "FROM folders " +
                    "WHERE course_id = ? ";
            if (parent_id == 0) {
                sql += "AND parent_id is null";
            } else {
                sql += "AND parent_id = ?";
            }
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, course_id);
            if (parent_id != 0) {
                stmt.setInt(2, parent_id);
            }
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Folder folder = new Folder();
                folder.setFolder_id(rs.getInt(1));
                folder.setCourse_id(rs.getInt(2));
                folder.setFolderName(rs.getString(3));
                folder.setParent_id(rs.getInt(4));
                folders.add(folder);
            }

            return folders;
        } finally {
            closeConnection(con);
        }
    }

    public List<File> getFilesForFolder(int folder_id) throws SQLException {
        Connection con = ds.getConnection();
        try {
            List<File> files = new ArrayList<File>();

            PreparedStatement stmt = con.prepareStatement("SELECT file_id, folder_id, name, size " +
                    "FROM files " +
                    "WHERE folder_id = ?");
            stmt.setInt(1, folder_id);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                File file = new File();
                file.setFile_id(rs.getInt(1));
                file.setFolder_id(rs.getInt(2));
                file.setFileName(rs.getString(3));
                file.setFileSize(rs.getInt(4));
                files.add(file);
            }

            return files;
        } finally {
            closeConnection(con);
        }
    }

    public boolean checkIfUserIsCreator(int user_id, int course_id) throws SQLException {
        Connection con = ds.getConnection();
        try {
            PreparedStatement stmt = con.prepareStatement("SELECT 1 FROM courses WHERE creator_id = ? AND course_id = ?");
            stmt.setInt(1, user_id);
            stmt.setInt(2, course_id);
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

    public void deleteCourse(int course_id) throws SQLException {
        Connection con = ds.getConnection();
        try {
            PreparedStatement stmt = con.prepareStatement("DELETE FROM courses WHERE course_id = ?");
            stmt.setInt(1, course_id);
            stmt.execute();
        } finally {
            closeConnection(con);
        }
    }




}

