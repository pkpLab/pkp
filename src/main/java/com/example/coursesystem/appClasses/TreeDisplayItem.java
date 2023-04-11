package com.example.coursesystem.appClasses;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;

public class TreeDisplayItem {
    private SimpleStringProperty name;
    private SimpleIntegerProperty subfolderCount;
    private SimpleLongProperty filesize;

    public TreeDisplayItem() {
        this.name = new SimpleStringProperty();
        this.subfolderCount = new SimpleIntegerProperty();
        this.filesize = new SimpleLongProperty();
    }

    public TreeDisplayItem(String name, int subfolders, long filesize) {
        this.name = new SimpleStringProperty(name);
        this.subfolderCount = new SimpleIntegerProperty(subfolders);
        this.filesize = new SimpleLongProperty(filesize);
    }

}
