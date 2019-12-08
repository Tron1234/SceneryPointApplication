package com.example.a5069.scenerypointapplication;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Main3Activity extends Activity {
private List<View> views;
    private ViewPagerAdapter vpAdapter;
    private ViewPager vp;
    private RadioGroup group;
    private RadioButton radioButton;
    private String[] picPath;
    private Dao dao,dao1;
    private ImageView button_backward;
    private TextView scenery_name,scenery_contact,scenery_address,scenery_summary,title1;
    private Button check_point,navigation_point;
    private List<scenery> list;
    private scenery scenery;
    private String name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.activity_main3);
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,R.layout.title1);
        //引用id
        vp= (ViewPager) findViewById(R.id.viewpager);
        group= (RadioGroup) findViewById(R.id.RadioGroup);
        button_backward= (ImageView) findViewById(R.id.button_backward);
        scenery_name= (TextView) findViewById(R.id.scenery_name);
        scenery_contact= (TextView) findViewById(R.id.scenery_contact);
        scenery_address= (TextView) findViewById(R.id.scenery_address);
        scenery_summary= (TextView) findViewById(R.id.scenery_summary);
        check_point= (Button) findViewById(R.id.check_point);
        navigation_point= (Button) findViewById(R.id.navigation_point);
        title1= (TextView) findViewById(R.id.title1);
        title1.setText("景点介绍");

        Intent intent=getIntent();
        name=intent.getStringExtra("name");
        //获取String数组，views中使用该数组
        dao=new Dao(Main3Activity.this);
        picPath=dao.queryAll2(name);
        //获取scenery对象
        dao1=new Dao(Main3Activity.this);
        list=dao1.queryAll3(name);
        scenery=list.get(0);
        //设置UI文本的值
        scenery_name.setText("景点名称:"+scenery.getName());
        scenery_contact.setText("景点联系方式:"+scenery.getContact());
        scenery_address.setText("景点地址:"+scenery.getAddress());
        scenery_summary.setText("简介:"+scenery.getSummary());

        check_point.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1=new Intent(Main3Activity.this,Main4Activity.class);
                intent1.putExtra("lat",scenery.getLat());
                intent1.putExtra("lng",scenery.getLng());
                startActivity(intent1);
            }
        });
        navigation_point.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2=new Intent(Main3Activity.this,Main5Activity.class);
                intent2.putExtra("lat",scenery.getLat());
                intent2.putExtra("lng",scenery.getLng());
                startActivity(intent2);
            }
        });

        //创建views传递给适配器
        views = new ArrayList<>();
        LinearLayout.LayoutParams mParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        for (int i = 0; i < picPath.length; i++) {
            ImageView iv = new ImageView(this);
            iv.setLayoutParams(mParams);
            GetImageByUrl getImageByUrl = new GetImageByUrl();
            getImageByUrl.setImage(iv, picPath[i]);
            iv.setScaleType(ImageView.ScaleType.FIT_XY);
            views.add(iv);
        }
        vpAdapter = new ViewPagerAdapter(views);
        //ViewPager设置适配器添加事件
        vp.setAdapter(vpAdapter);vp.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                position=position%views.size();//循环时，一定要有这句，不然会越界异常
                radioButton = (RadioButton) group.getChildAt(position);
                radioButton.setChecked(true);
            }

            @Override
            public void onPageScrolled(int positon, float arg1, int arg2) {
            }
            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        //返回按钮事件
        button_backward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    public class ViewPagerAdapter extends PagerAdapter {

        //界面列表
        private List<View> views;

        public ViewPagerAdapter (List<View> views){
            this.views = views;
        }

        @Override
        public int getCount() {
           // return Integer.MAX_VALUE; //设置成最大，使用户看不到边界
       return views==null?0:views.size();
        }

        @Override
        public void destroyItem(View container, int position, Object arg2) {
           // ((ViewPager)container).removeView(views.get(position % views.size()));
       ((ViewPager)container).removeView(views.get(position));
        }

        @Override
        public Object instantiateItem(View container, int position) {
            //((ViewPager)container).addView(views.get(position % views.size()), 0);
           // return views.get(position % views.size());
     ((ViewPager)container).addView(views.get(position), 0);
        return views.get(position);
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return (arg0 == arg1);
        }
    }
}
