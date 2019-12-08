package com.example.a5069.scenerypointapplication;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by a5069 on 2017/6/19.
 */
public class MyHelper extends SQLiteOpenHelper {
    private String Create_Table="create table province(id integer primary key autoincrement,name)";
    private String Create_Table1="create table scenery(id integer primary key autoincrement,name,contact,address,graph,summary,lat,lng,belongprovince)";
    private String Create_Table2="create table scenerypicture(id integer primary key autoincrement,url1,url2,url3,name)";
    public MyHelper(Context context) {
        super(context,"scenery.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Create_Table);
        db.execSQL(Create_Table1);
        db.execSQL(Create_Table2);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
