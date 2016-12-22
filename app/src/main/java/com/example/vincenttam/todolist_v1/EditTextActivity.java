package com.example.vincenttam.todolist_v1;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Random;

public class EditTextActivity extends AppCompatActivity {

    public static String itemType = "Work";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_text);

        /***设置默认事项***/
        {
            itemType = "Work";
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss ");
            SimpleDateFormat formatterTitle = new SimpleDateFormat("yyMMddHHmmss");
            Date curDate = new Date(System.currentTimeMillis());//获取当前时间
            String str = formatter.format(curDate);
            String strTitle = formatterTitle.format(curDate);
            EditText default_Title = (EditText) findViewById(R.id.edit_title);
            //default_Title.setText("事件 " + strTitle);
            EditText default_Detail = (EditText) findViewById(R.id.edit_detail);
            //default_Detail.setText(str);
        }


        TextView titleTextView = (TextView)findViewById(R.id.fixed_title);
        titleTextView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                String[] titleTips = {"别忘了：","记得去：","还没做：","重要：","完成："};
                Random randomSeed = new Random();
                int i = randomSeed.nextInt(5)%(5+1);
                EditText default_Title = (EditText) findViewById(R.id.edit_title);
                default_Title.setText(titleTips[i]);
                return false;
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_edit_text, menu);
        return super.onCreateOptionsMenu(menu);
    }

    /***点击完成键的响应代码***/
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.edit_done:

                SimpleDateFormat formatter = new SimpleDateFormat("MM月dd日 HH:mm");
                Date curDate = new Date(System.currentTimeMillis());//获取当前时间
                String str = formatter.format(curDate);

                EditText editText1 =(EditText)findViewById(R.id.edit_title);
                String Title=editText1.getText().toString();

                EditText editText2 =(EditText)findViewById(R.id.edit_detail);
                String Detail=editText2.getText().toString();

                if (Detail.equals(""))
                    Detail += str;
                else
                    Detail = Detail + "  |  " + str;

                MyDBHelper dbHelper = new MyDBHelper(EditTextActivity.this,"stu_db",null,1);
                //得到一个可写的数据库  
                SQLiteDatabase db =dbHelper.getWritableDatabase();


                //生成ContentValues对象 //key:列名，value:想插入的值   
                ContentValues cv = new ContentValues();
                //往ContentValues对象存放数据，键-值对模式  
                cv.put("Title", Title);
                cv.put("Detail",Detail);
                cv.put("isCompleted", 0);
                cv.put("isDeleted", 0);
                cv.put("addTime", "now");
                cv.put("completedTime", "after");
                cv.put("itemType", itemType);
                //调用insert方法，将数据插入数据库  
                db.insert("stu_table", null, cv);
                //关闭数据库  
                db.close();

                //Toast.makeText(this, "保存成功", Toast.LENGTH_SHORT).show();
                EditTextActivity.this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    public void detailClicked(View view) {
        Toast.makeText(this, "说明部分会自动添加当前时间", Toast.LENGTH_SHORT).show();
    }


    /***选择分类***/
    public void typeLifeSelected(View view) {
        ImageView imageView_life = (ImageView)this.findViewById(R.id.image_life);
        imageView_life.setImageResource(R.drawable.life_selected);

        ImageView imageView_work = (ImageView)this.findViewById(R.id.image_work);
        imageView_work.setImageResource(R.drawable.work_unselected);

        ImageView imageView_study = (ImageView)this.findViewById(R.id.image_study);
        imageView_study.setImageResource(R.drawable.study_unselected);

        itemType = "Life";
    }

    public void typeWorkSelected(View view) {
        ImageView imageView_life = (ImageView)this.findViewById(R.id.image_life);
        imageView_life.setImageResource(R.drawable.life_unselected);

        ImageView imageView_work = (ImageView)this.findViewById(R.id.image_work);
        imageView_work.setImageResource(R.drawable.work_selected);

        ImageView imageView_study = (ImageView)this.findViewById(R.id.image_study);
        imageView_study.setImageResource(R.drawable.study_unselected);

        itemType = "Work";
    }

    public void typeStudySelected(View view) {
        ImageView imageView_life = (ImageView)this.findViewById(R.id.image_life);
        imageView_life.setImageResource(R.drawable.life_unselected);

        ImageView imageView_work = (ImageView)this.findViewById(R.id.image_work);
        imageView_work.setImageResource(R.drawable.work_unselected);

        ImageView imageView_study = (ImageView)this.findViewById(R.id.image_study);
        imageView_study.setImageResource(R.drawable.study_selected);

        itemType = "Study";
    }

}
