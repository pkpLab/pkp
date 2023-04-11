package com.example.coursesystem.fxControllers;

import com.example.coursesystem.appClasses.Database;
import com.example.coursesystem.dataStructures.Course;
import com.example.coursesystem.dataStructures.User;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class UserCountTest {

    @Test
    void getEditRightCountFunctionTest() throws SQLException {


        //testavimo aplinkos paruosimas------------------------------------------

        Database db = new Database();
        ManagedCoursesController x = new ManagedCoursesController();
        Course course = new Course();

        User manager1 = new User();
        manager1.setLogin("loginMng1");
        manager1.setUserName("User");
        manager1.setLastName("lastNameUser1");
        manager1.setEmail("user1@test.com");
        manager1.setCompanyName("");
        manager1.setUserType(1);
        db.createUser(manager1, "PasswordUser1!!!");

        User manager2 = new User();
        manager2.setLogin("loginMng2");
        manager2.setUserName("User");
        manager2.setLastName("lastNameUser1");
        manager2.setEmail("user1@test.com");
        manager2.setCompanyName("");
        manager2.setUserType(1);
        db.createUser(manager2, "PasswordUser1!!!");

        User manager3 = new User();
        manager3.setLogin("loginMng3");
        manager3.setUserName("User");
        manager3.setLastName("lastNameUser1");
        manager3.setEmail("user1@test.com");
        manager3.setCompanyName("");
        manager3.setUserType(1);
        db.createUser(manager3, "PasswordUser1!!!");


        //course.setCreator_id(db.getUserId("loginMng1", "PasswordUser1!!!"));
        course.setCreator_id(0);
        course.setCourseName("testCourse");
        course.setDescription("testDesc");
        course.setCourseLength("2");
        //db.insertCourse(course); NEAPRAŠYTA FUNKCIJA

        //---------------------------TESTAS-----------------------------------

        int[] results = new int[4];

//        NEAPRAŠYTOS FUNKCIJOS

//        db.setUserPrivileges(1, 0);
//        results[0] = db.getEditRightCount(0); //results[0] turi būti 1
//
//        db.setUserPrivileges(2, 0);
//        results[1] = db.getEditRightCount(0); //results[1] turi būti 2
//
//        db.deleteUserPrivileges(1, 0);
//        results[2] = db.getEditRightCount(0); //results[2] turi būti 1
//
//        db.deleteUserPrivileges(2, 0);
//        results[3] = db.getEditRightCount(0); //results[3] turi būti 0

        assertArrayEquals(new int[]{1,2,1,0}, results );



        //IŠVADA - test "fail", nes neaprašytos reikalingos funkcijos



    }

}