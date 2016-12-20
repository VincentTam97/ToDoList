package com.example.vincenttam.todolist_v1;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public class StuDBHelper extends SQLiteOpenHelper {
    private static final String TAG = "TestSQLite";
    public static final int VERSION = 1;
    //必须要有构造函数  
    public StuDBHelper(Context context, String name, CursorFactory factory,
                       int version) {
        super(context, name, factory, version);
    }

// 当第一次创建数据库的时候，调用该方法   
    public void onCreate(SQLiteDatabase db) {
        String sql = "create table stu_table(itemID INTEGER PRIMARY KEY AUTOINCREMENT,Title varchar(100),Detail varchar(500)," +
                "isCompleted int,isDeleted int,addTime varchar(20),completedTime varchar(20),itemType varchar(10))";
//输出创建数据库的日志信息  
        Log.i(TAG, "create Database------------->");
//execSQL函数用于执行SQL语句  
        db.execSQL(sql);
    }

//当更新数据库的时候执行该方法  
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//输出更新数据库的日志信息  
        Log.i(TAG, "update Database------------->");
    }
}
