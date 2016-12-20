package com.example.vincenttam.todolist_v1;

/**
 * Created by VincentTam on 16/11/20.
 */

public class ToDoItem {
    int itemID;
    String Title;
    String Detail;
    int isCompleted;
    int isDeleted;
    String itemType; //Work, Study, Life, Others
    String addTime;
    String completedTime;

    ToDoItem (int itemID,String Title, String Detail, String itemType) {
        this.itemID=itemID; 
        this.Title = Title;
        if (Detail.length() > 0)
            this.Detail = Detail;

        this.addTime = addTime;
        this.isCompleted = 0;
        this.isDeleted = 0;
        this.itemType = itemType;
    }
}
