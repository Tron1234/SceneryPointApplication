package com.example.a5069.scenerypointapplication;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by a5069 on 2017/6/19.
 */
public class Dao {
    private MyHelper helper;
    public Dao(Context context) {
        helper=new MyHelper(context);
    }
    public List<String> queryAll() {
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor c = db.query("province", null, null, null, null, null, null);
        List<String> list = new ArrayList<String>();
        while (c.moveToNext()) {
            String name=c.getString(1);
            list.add(name);
        }
        c.close();
        db.close();
        return list;
    }
    public List<scenery> queryAll1(String province) {
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor c = db.query("scenery", null, "belongprovince=?", new String[]{province}, null, null, null);
        List<scenery> list = new ArrayList<scenery>();
        while (c.moveToNext()) {
            String name=c.getString(1);
            String graph=c.getString(4);
            list.add(new scenery(name,graph));
        }
        c.close();
        db.close();
        return list;
    }
    public String[] queryAll2(String name) {
        String[] picPath=new String[3];
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor c = db.query("scenerypicture", null, "name=?", new String[]{name}, null, null, null);
        List<scenerypicture> list = new ArrayList<scenerypicture>();
        while (c.moveToNext()) {
            String url1=c.getString(1);
            String url2=c.getString(2);
            String url3=c.getString(3);
            list.add(new scenerypicture(url1,url2,url3));
        }
        for(int i=0;i<list.size();i++){
            scenerypicture scenerypicture=list.get(i);
            picPath[0]=scenerypicture.getUrl1();
            picPath[1]=scenerypicture.getUrl2();
            picPath[2]=scenerypicture.getUrl3();
        }
        c.close();
        db.close();
        return picPath;
    }
    public List<scenery> queryAll3(String name) {
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor c = db.query("scenery", null, "name=?", new String[]{name}, null, null, null);
        List<scenery> list = new ArrayList<scenery>();
        while (c.moveToNext()) {
            String name1=c.getString(1);
            String contact=c.getString(2);
            String address=c.getString(3);
            String summary=c.getString(5);
            String lat=c.getString(6);
            String lng=c.getString(7);
            list.add(new scenery(name1,contact,address,lat,lng,summary));
        }
        c.close();
        db.close();
        return list;
    }
}
