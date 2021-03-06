package com.example.vincenttam.todolist_v1;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static RVAdapter adapter;
    public static MainActivity instance = null;

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;



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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        instance=this;
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("待办事项");
        //toolbar.setSubtitle("这里是子标题");
        //Toast.makeText(this,"onCreate！",Toast.LENGTH_SHORT).show();
        setSupportActionBar(toolbar);
        MyDBHelper dbHelper = new MyDBHelper(this,"stu_db",null,1);
        //得到一个可读的SQLiteDatabase对象  
        SQLiteDatabase db =dbHelper.getWritableDatabase();

        setUpDrawer();

        // 打開 up button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        // 實作 drawer toggle 並放入 toolbar
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close);
        mDrawerToggle.syncState();
        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }
    public Context getContext(){
        return this;
    }


    @Override
    protected void onStart(){
        super.onStart();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        adapter = new RVAdapter(MainActivity.this, initializeItems("ALL"));
        RecyclerView rv = (RecyclerView)findViewById(R.id.rv);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        rv.setLayoutManager(llm);
        rv.setAdapter(adapter);

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new MyCallback());
        itemTouchHelper.attachToRecyclerView(rv);
        toolbar.setTitle("待办事项");

    }





    /***启动新的Activity：添加事项***/
    public void newItem(View view) {
        Intent intent = new Intent(this,EditTextActivity.class);
        startActivity(intent);
    }


    /***从列表中删除一个事项***/
    public void delete(int itemID){
        //System.out.println("删除");
        MyDBHelper dbHelper = new MyDBHelper(this,"stu_db",null,1);
        SQLiteDatabase dbWriter =dbHelper.getWritableDatabase();
        SQLiteDatabase dbReader =dbHelper.getReadableDatabase();
        ContentValues cvW = new ContentValues();


        String whereClauses = "itemID=?";
        String [] where = {Integer.toString(itemID)};

        Cursor cursor = dbReader.query("stu_table", new String[]{"itemID","isDeleted"}, "itemID=?", new String[]{Integer.toString(itemID)}, null, null, null);
        cursor.moveToNext();
        if (cursor.getString(cursor.getColumnIndex("isDeleted")).equals("1"))
            cvW.put("isDeleted",2);
        else
            cvW.put("isDeleted",1);

        dbWriter.update("stu_table", cvW, whereClauses, where);
        //db.delete("stu_table",whereClauses,where);
        dbWriter.close();
        //Toast.makeText(this,"删除成功",Toast.LENGTH_SHORT).show();

        CoordinatorLayout coordinatorLayout = (CoordinatorLayout)findViewById(R.id.activity_main);
        Snackbar.make(coordinatorLayout,"删除成功", Snackbar.LENGTH_LONG).show();
    }


    /***刷新列表***/
    protected void refreshList(String type){
        adapter = new RVAdapter(MainActivity.this, initializeItems(type));
        RecyclerView rv = (RecyclerView)findViewById(R.id.rv);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        rv.setLayoutManager(llm);
        rv.setAdapter(adapter);

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new MyCallback());
        itemTouchHelper.attachToRecyclerView(rv);
    }

    /***设置主Activity的NavigationDrawer菜单***/
    public void setUpDrawer() {
        NavigationView navigationView = (NavigationView)findViewById(R.id.main_navigation_view);
        final DrawerLayout drawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        final FloatingActionButton FAB = (FloatingActionButton)findViewById(R.id.fab);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.all_items_list:
                        toolbar.setTitle("待办事项");
                        FAB.show();
                        refreshList("ALL");
                        break;
                    case R.id.work_items_list:
                        toolbar.setTitle("工作");
                        FAB.hide();
                        refreshList("WORK");
                        break;
                    case R.id.life_items_list:
                        toolbar.setTitle("生活");
                        FAB.hide();
                        refreshList("LIFE");
                        break;
                    case R.id.study_items_list:
                        toolbar.setTitle("学习");
                        FAB.hide();
                        refreshList("STUDY");
                        break;
                    case R.id.trash_list:
                        toolbar.setTitle("垃圾箱");
                        FAB.hide();
                        refreshList("TRASH");
                        break;
                    default:
                        break;
                }
                item.setChecked(true);//设置菜单选中
                drawerLayout.closeDrawers();
                return false;
            }
        });
    }

    /***生成RecyclerView的数据集***/
    public static List<ToDoItem> items;
    private List<ToDoItem> initializeItems(String type) {
        items = new ArrayList<>();
        //得到一个可读的数据库  
        MyDBHelper dbHelper = new MyDBHelper(this,"stu_db",null,1);
        SQLiteDatabase db =dbHelper.getReadableDatabase();
        //Cursor cursor = db.query("stu_table", new String[]{"itemID","Title","Detail","isCompleted","isDeleted","addTime","completedTime","itemType"}, "isDeleted=?", new String[]{"0"}, null, null, "itemID desc");
        Cursor cursor = null;
        if (type.equals("ALL"))
            cursor = db.query("stu_table", new String[]{"itemID","Title","Detail","isCompleted","isDeleted","addTime","completedTime","itemType"}, "isDeleted=?", new String[]{"0"}, null, null, "itemID desc");
        else if (type.equals("WORK"))
            cursor = db.query("stu_table", new String[]{"itemID","Title","Detail","isCompleted","isDeleted","addTime","completedTime","itemType"}, "isDeleted=? AND itemType=?", new String[]{"0","Work"}, null, null, "itemID desc");
        else if (type.equals("STUDY"))
            cursor = db.query("stu_table", new String[]{"itemID","Title","Detail","isCompleted","isDeleted","addTime","completedTime","itemType"}, "isDeleted=? AND itemType=?", new String[]{"0","Study"}, null, null, "itemID desc");
        else if (type.equals("LIFE"))
            cursor = db.query("stu_table", new String[]{"itemID","Title","Detail","isCompleted","isDeleted","addTime","completedTime","itemType"}, "isDeleted=? AND itemType=?", new String[]{"0","Life"}, null, null, "itemID desc");
        else if (type.equals("TRASH"))
            cursor = db.query("stu_table", new String[]{"itemID","Title","Detail","isCompleted","isDeleted","addTime","completedTime","itemType"}, "isDeleted=?", new String[]{"1"}, null, null, "itemID desc");

        DatabaseControl.getData(cursor,items);
        db.close();
        return items;
    }


}
