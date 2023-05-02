//package com.example.coursesystem.appClasses;
//
//import org.apache.commons.dbcp.BasicDataSource;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mock;
//
//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//class removeUserFromCourseTest {
//
//    @Mock
//    private BasicDataSource mockDataSource = new BasicDataSource();
//    @Mock
//    private Connection mockConnection;
//    @Mock
//    private PreparedStatement mockStatement;
//
//    @BeforeEach
//    void setUp() throws Exception {
//        mockDataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
//        mockDataSource.setUrl("jdbc:mysql://localhost:3306/temp");
//        mockDataSource.setUsername("root");
//        mockDataSource.setPassword(""); //toor
//        mockDataSource.setMinIdle(5);
//        mockDataSource.setMaxIdle(10);
//        mockDataSource.setMaxOpenPreparedStatements(100);
//        mockConnection = mockDataSource.getConnection();
//    }
//
//    @Test
//    public void testRemoveUserFromCourseInvalid() throws SQLException {
//        int userId = 545;
//        int courseId = 424;
//
//        mockStatement = mockConnection.prepareStatement("DELETE FROM user_enrolled_courses WHERE user_id = ? AND course_id = ?");
//        mockStatement.setInt(1, userId);
//        mockStatement.setInt(2, courseId);
//
//        assertFalse(mockStatement.execute());
//    }
//    @Test
//    public void testRemoveUserFromCourseValid() throws SQLException {
//        int userId = 1;
//        int courseId = 3;
//
//        mockStatement = mockConnection.prepareStatement("DELETE FROM user_enrolled_courses WHERE user_id = ? AND course_id = ?");
//        mockStatement.setInt(1, userId);
//        mockStatement.setInt(2, courseId);
//        mockStatement.executeUpdate();
//
//        PreparedStatement selectStmt = mockConnection.prepareStatement("SELECT COUNT(*) FROM user_enrolled_courses WHERE user_id = ? AND course_id = ?");
//        selectStmt.setInt(1, userId);
//        selectStmt.setInt(2, courseId);
//        ResultSet rs = selectStmt.executeQuery();
//        rs.next();
//        int count = rs.getInt(1);
//        assertEquals(0, count);
//    }
//}