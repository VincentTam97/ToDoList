package com.example.vincenttam.todolist_v1;

import android.content.ClipData;
import android.content.ContentProvider;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.List;

/**
 * Created by szb on 2016/11/21.
 */

public class DatabaseControl{
    public static void getData(Cursor cursor,List<ToDoItem> items){

        while(cursor.moveToNext()){
            String itemID = cursor.getString(cursor.getColumnIndex("itemID"));
            String Title = cursor.getString(cursor.getColumnIndex("Title"));
            String Detail = cursor.getString(cursor.getColumnIndex("Detail"));
            String isCompleted = cursor.getString(cursor.getColumnIndex("isCompleted"));
            String addTime = cursor.getString(cursor.getColumnIndex("addTime"));
            String completedTime = cursor.getString(cursor.getColumnIndex("completedTime"));
            String itemType = cursor.getString(cursor.getColumnIndex("itemType"));

            items.add(new ToDoItem(itemID,Title, Detail, itemType));
        }

    }
}
