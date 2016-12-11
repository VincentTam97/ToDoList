package com.example.vincenttam.todolist_v1;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.example.vincenttam.todolist_v1.R.id.rv;

public class MainActivity extends AppCompatActivity {

    public static RVAdapter adapter;
    public static MainActivity instance = null;
    /***控制再按一次返回键退出程序的部分代码***/
    //控制返回键退出程序的相关参数
    private long mExitTime = 0;
    public MainActivity(){}
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if ((System.currentTimeMillis() - mExitTime) > 3000) {
                //如果两次按键时间间隔大于3000毫秒，则不退出
                Toast.makeText(this, "再次按下Back键退出程序", Toast.LENGTH_SHORT).show();
                mExitTime = System.currentTimeMillis();// 更新mExitTime
            } else {
                System.exit(0);// 否则退出程序
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
    /***控制再按一次返回键退出程序的部分代码***/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        instance=this;
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("待办事项");
        //toolbar.setSubtitle("这里是子标题");
        Toast.makeText(this,"onCreate！",Toast.LENGTH_SHORT).show();
        setSupportActionBar(toolbar);
        StuDBHelper dbHelper = new StuDBHelper(this,"stu_db",null,1);
//得到一个可读的SQLiteDatabase对象  
        SQLiteDatabase db =dbHelper.getWritableDatabase();
      //  db.close();
    }
    public Context getContext(){
        return this;
    }


    @Override
    protected void onStart(){
        super.onStart();
        adapter = new RVAdapter(MainActivity.this, initializeItems());
        RecyclerView rv = (RecyclerView)findViewById(R.id.rv);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        rv.setLayoutManager(llm);
        rv.setAdapter(adapter);

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new MyCallback());
        itemTouchHelper.attachToRecyclerView(rv);

    }

    //启动新的Activity：添加事项
    public void newItem(View view) {
        Intent intent = new Intent(this,EditTextActivity.class);
        startActivity(intent);
    }

    public void delete(String itemID){
        //Toast.makeText(this,"删除",Toast.LENGTH_SHORT).show();
        //System.out.println("删除");
        StuDBHelper dbHelper = new StuDBHelper(this,"stu_db",null,1);
        SQLiteDatabase db =dbHelper.getWritableDatabase();
        String whereClauses = "itemID=?";
        String [] where = {itemID};
        db.delete("stu_table",whereClauses,where);
        db.close();
    }


    public static List<ToDoItem> items;
    private List<ToDoItem> initializeItems() {
        items = new ArrayList<>();
//得到一个可读的数据库  
        StuDBHelper dbHelper = new StuDBHelper(this,"stu_db",null,1);
        SQLiteDatabase db =dbHelper.getReadableDatabase();
        Cursor cursor = db.query("stu_table", new String[]{"itemID","Title","Detail","isCompleted","addTime","completedTime","itemType"}, "isCompleted=?", new String[]{"0"}, null, null, null);
        DatabaseControl.getData(cursor,items);
        db.close();
        return items;
    }


}
