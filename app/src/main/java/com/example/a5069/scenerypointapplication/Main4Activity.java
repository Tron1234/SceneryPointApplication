package com.example.a5069.scenerypointapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;

public class Main4Activity extends Activity {
private TextView title1;
    private MapView mMapView;
    private BaiduMap mbaidumap;
    private ImageView button_backward;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SDKInitializer.initialize(getApplicationContext());
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.activity_main4);
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,R.layout.title1);
        title1= (TextView) findViewById(R.id.title1);
        title1.setText("地理位置");
        mMapView = (MapView) findViewById(R.id.bmapView);
        button_backward= (ImageView) findViewById(R.id.button_backward);
        Intent intent=getIntent();
        mbaidumap=mMapView.getMap();
        mbaidumap.setMapType(BaiduMap.MAP_TYPE_NORMAL);//获取地图
        mbaidumap.setTrafficEnabled(true);//时实交通信息
        LatLng latLng=new LatLng(Double.parseDouble(intent.getStringExtra("lat")),Double.parseDouble(intent.getStringExtra("lng")));//经纬度
        MapStatus mMapStatus = new MapStatus.Builder()
                .target(latLng)
                .zoom(18)
                .build();
        MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mMapStatus);
        mbaidumap.setMapStatus(mMapStatusUpdate);
        BitmapDescriptor bitmapDescriptor= BitmapDescriptorFactory.fromResource(R.drawable.a3);
        //创建图层选项
        OverlayOptions overlayOptions=new MarkerOptions().position(latLng).icon(bitmapDescriptor);
        //把图层选项添加到地图上
        mbaidumap.addOverlay(overlayOptions);
        button_backward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        mMapView.onDestroy();
    }
    @Override
    protected void onResume() {
        mMapView.setVisibility(View.VISIBLE);
        mMapView.onResume();
        super.onResume();
    }
    @Override
    protected void onPause() {
        mMapView.setVisibility(View.INVISIBLE);
        mMapView.onPause();
        super.onPause();
    }
}
