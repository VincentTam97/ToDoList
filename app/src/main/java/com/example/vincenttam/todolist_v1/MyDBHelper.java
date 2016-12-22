package com.example.vincenttam.todolist_v1;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public class MyDBHelper extends SQLiteOpenHelper {
    private static final String TAG = "TestSQLite";
    public static final int VERSION = 1;
    //必须要有构造函数  
    public MyDBHelper(Context context, String name, CursorFactory factory,
                      int version) {
        super(context, name, factory, version);
    }

    @Override
    // 当第一次创建数据库的时候，调用该方法   
    public void onCreate(SQLiteDatabase db) {
        String sql = "create table stu_table(itemID INTEGER PRIMARY KEY AUTOINCREMENT,Title varchar(100),Detail varchar(500)," +
                "isCompleted int,isDeleted int,addTime varchar(20),completedTime varchar(20),itemType varchar(10))";
        //输出创建数据库的日志信息  
        Log.i(TAG, "create Database------------->");
        //execSQL函数用于执行SQL语句  
        db.execSQL(sql);

        String[] startTips = {"","","","","","","",""};
        startTips[7] = "insert into stu_table(Title,Detail,isDeleted,itemType) values('欢迎使用','我们的ToDoList','0','Work')";
        startTips[6] = "insert into stu_table(Title,Detail,isDeleted,itemType) values('这是一个[工作]事项','看左边的黄色图标！','0','Work')";
        startTips[5] = "insert into stu_table(Title,Detail,isDeleted,itemType) values('这是一个[生活]事项','看左边的绿色图标！','0','Life')";
        startTips[4] = "insert into stu_table(Title,Detail,isDeleted,itemType) values('这是一个[学习]事项','看左边的紫色图标！','0','Study')";
        startTips[3] = "insert into stu_table(Title,Detail,isDeleted,itemType) values('右下角有个按钮','添加你的第一个事项！','0','Study')";
        startTips[2] = "insert into stu_table(Title,Detail,isDeleted,itemType) values('向左滑动删除一条','删掉我试试看！','0','Work')";
        startTips[1] = "insert into stu_table(Title,Detail,isDeleted,itemType) values('按左上角的按钮试试！','或者从屏幕左侧右滑','0','Life')";
        startTips[0] = "insert into stu_table(Title,Detail,isDeleted,itemType) values('从垃圾箱也能删除','那么就被永久删除了','1','Work')";

        for (int i = 0; i <= 7; i++)
            db.execSQL(startTips[i]);
    }

    //当更新数据库的时候执行该方法  
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //输出更新数据库的日志信息  
        Log.i(TAG, "update Database------------->");
    }
}
