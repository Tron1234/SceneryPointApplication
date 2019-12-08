package com.example.a5069.scenerypointapplication;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import java.util.List;


public class Main2Activity extends Activity {
private ListView scenery_point;
    private ImageView button_backward;
    private Dao dao;
    private List<scenery> list;
    private scenery scenery;
    private MyAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.activity_main2);
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,R.layout.title1);
        scenery_point= (ListView) findViewById(R.id.scenery_point);
        button_backward= (ImageView) findViewById(R.id.button_backward);
        dao=new Dao(Main2Activity.this);
        Intent intent=getIntent();
        list=dao.queryAll1(intent.getStringExtra("province"));
        scenery=new scenery();
        adapter=new MyAdapter();
        scenery_point.setAdapter(adapter);
        button_backward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        scenery_point.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent1=new Intent(Main2Activity.this,Main3Activity.class);
                intent1.putExtra("name",list.get(position).getName());
                startActivity(intent1);
            }
        });
    }

    public class MyAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if(convertView==null){
                convertView= LayoutInflater.from(Main2Activity.this).inflate(R.layout.item,null);
            }
            ImageView littleimg= (ImageView) convertView.findViewById(R.id.littleimg);
            TextView scenery_smallname= (TextView) convertView.findViewById(R.id.scenery_smallname);
            scenery =list.get(position);
            GetImageByUrl getImageByUrl = new GetImageByUrl();
            getImageByUrl.setImage(littleimg, scenery.getGraph());
            scenery_smallname.setText(scenery.getName());
            return convertView;
        }
    }
}
