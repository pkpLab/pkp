package com.example.coursesystem.appClasses;

import com.example.coursesystem.dataStructures.Course;
import com.example.coursesystem.dataStructures.File;
import com.example.coursesystem.dataStructures.Folder;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableView;

import java.sql.SQLException;
import java.util.Hashtable;
import java.util.List;

public class CourseTree {

    private TreeTableView treeTableView;
    private Hashtable<TreeItem<TreeDisplayItem>, Object> treeItemHashtable = new Hashtable<>();

    public CourseTree(TreeTableView treeTableView) {
        this.treeTableView = treeTableView;
    }

    public Hashtable<TreeItem<TreeDisplayItem>, Object> getTreeItemHashtable() {
        return treeItemHashtable;
    }

    public void loadTree(List<Course> courses) throws SQLException {
        TreeItem<TreeDisplayItem> rootItem = new TreeItem<>();
        rootItem.setValue(new TreeDisplayItem("root", 0, 0));
        treeTableView.setRoot(rootItem);
        treeTableView.setShowRoot(false);

        for (Course course:
                courses) {
            TreeItem<TreeDisplayItem> treeItem = new TreeItem<>();
            TreeDisplayItem displayItem = new TreeDisplayItem();
            displayItem.setName(course.getCourseName());
            treeItem.setValue(displayItem);
            treeItemHashtable.put(treeItem, course);
            rootItem.getChildren().add(treeItem);
        }
    }

    public void clearTree() {
        treeItemHashtable.clear();
        treeTableView.setRoot(null);
    }
}

