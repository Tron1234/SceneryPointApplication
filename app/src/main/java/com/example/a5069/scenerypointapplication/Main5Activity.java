package com.example.a5069.scenerypointapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.Poi;
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
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.route.BikingRouteResult;
import com.baidu.mapapi.search.route.DrivingRoutePlanOption;
import com.baidu.mapapi.search.route.DrivingRouteResult;
import com.baidu.mapapi.search.route.IndoorRouteResult;
import com.baidu.mapapi.search.route.MassTransitRouteResult;
import com.baidu.mapapi.search.route.OnGetRoutePlanResultListener;
import com.baidu.mapapi.search.route.PlanNode;
import com.baidu.mapapi.search.route.RoutePlanSearch;
import com.baidu.mapapi.search.route.TransitRouteResult;
import com.baidu.mapapi.search.route.WalkingRouteResult;

import java.util.List;

public class Main5Activity extends Activity {
    private TextView title1;
    private MapView mMapView;
    private BaiduMap mbaidumap;
    private ImageView button_backward;
    private ImageView location_point,poi_btn;
    private RoutePlanSearch mSearch;
    public LocationClient mLocationClient=null;
    public LatLng latlng=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SDKInitializer.initialize(getApplicationContext());
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.activity_main5);
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,R.layout.title2);
        title1= (TextView) findViewById(R.id.title1);
        title1.setText("自驾路线规划");
        mMapView = (MapView) findViewById(R.id.bmapView1);
        button_backward= (ImageView) findViewById(R.id.button_backward);
        mbaidumap=mMapView.getMap();
        mbaidumap.setMapType(BaiduMap.MAP_TYPE_NORMAL);//获取地图
        mbaidumap.setTrafficEnabled(true);//时实交通信息
        button_backward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        location_point= (ImageView) findViewById(R.id.location_point);
        poi_btn= (ImageView) findViewById(R.id.poi_btn);
        poi_btn.setEnabled(false);
        final Intent intent=getIntent();
        //定位
        BDLocationListener listener=new MyLocationListener();
        mLocationClient=new LocationClient(getApplicationContext());//声明LocationClient类
        mLocationClient.registerLocationListener(listener );//注册位置监听器


        //请求我位置信息
        location_point= (ImageView) findViewById(R.id.location_point);
        location_point.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initLocation();
                mLocationClient.start();
                mLocationClient.requestLocation();//发送请求
                poi_btn.setEnabled(true);
            }
        });
        mSearch = RoutePlanSearch.newInstance();
        OnGetRoutePlanResultListener routelistener = new OnGetRoutePlanResultListener() {
            public void onGetWalkingRouteResult(WalkingRouteResult result) {
                //获取步行线路规划结果
            }
            public void onGetTransitRouteResult(TransitRouteResult result) {
                //获取公交换乘路径规划结果
            }

            @Override
            public void onGetMassTransitRouteResult(MassTransitRouteResult massTransitRouteResult) {

            }

            public void onGetDrivingRouteResult(DrivingRouteResult result) {
                //获取驾车线路规划结果
                if (result == null
                        || SearchResult.ERRORNO.RESULT_NOT_FOUND == result.error) {
                    Toast.makeText(getApplicationContext(), "未搜索到结果", Toast.LENGTH_SHORT).show();
                    return;
                }

                DrivingRouteOverlay drivingOverlay = new MyDrivingOverlay(mbaidumap);
                mbaidumap.setOnMarkerClickListener(drivingOverlay);
                drivingOverlay.setData(result.getRouteLines().get(0));//设置线路为搜索结果的第一条
                drivingOverlay.addToMap();
                drivingOverlay.zoomToSpan();
            }

            @Override
            public void onGetIndoorRouteResult(IndoorRouteResult indoorRouteResult) {

            }

            @Override
            public void onGetBikingRouteResult(BikingRouteResult bikingRouteResult) {

            }
        };


        mSearch.setOnGetRoutePlanResultListener(routelistener);
        poi_btn= (ImageView) findViewById(R.id.poi_btn);
        poi_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //发起一个检索的请求，异步过程
                mbaidumap.clear();
                mLocationClient.stop();
                DrivingRoutePlanOption drivingOption = new DrivingRoutePlanOption();
                PlanNode from = PlanNode.withLocation(latlng);//窗前起点
                PlanNode to = PlanNode.withLocation(new LatLng(Double.parseDouble(intent.getStringExtra("lat")),Double.parseDouble(intent.getStringExtra("lng"))));//创建终点
                drivingOption.from(from);//设置起点
                drivingOption.to(to);//设置终点
                drivingOption.policy(DrivingRoutePlanOption.DrivingPolicy.ECAR_DIS_FIRST);//设置策略，驾乘检索策略常量：最短距离
                mSearch.drivingSearch(drivingOption);
            }
        });
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        mMapView.onDestroy();
        mSearch.destroy();
        mLocationClient.stop();
    }
    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
        mMapView.onResume();
    }
    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
        mMapView.onPause();
    }

    //位置监听器基于BDLocationListener的MyLocationListener类
    public class MyLocationListener implements BDLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location) {//接受位置信息的回调信息
            mbaidumap.clear();
            if(location==null)
                return;
            //获取定位结果
            StringBuffer sb = new StringBuffer(256);

            sb.append("time : ");
            sb.append(location.getTime());    //获取定位时间

            sb.append("\nerror code : ");
            sb.append(location.getLocType());    //获取类型类型

            sb.append("\nlatitude : ");
            sb.append(location.getLatitude());    //获取纬度信息

            sb.append("\nlontitude : ");
            sb.append(location.getLongitude());    //获取经度信息

            sb.append("\nradius : ");
            sb.append(location.getRadius());    //获取定位精准度

            if (location.getLocType() == BDLocation.TypeGpsLocation){

                // GPS定位结果
                sb.append("\nspeed : ");
                sb.append(location.getSpeed());    // 单位：公里每小时

                sb.append("\nsatellite : ");
                sb.append(location.getSatelliteNumber());    //获取卫星数

                sb.append("\nheight : ");
                sb.append(location.getAltitude());    //获取海拔高度信息，单位米

                sb.append("\ndirection : ");
                sb.append(location.getDirection());    //获取方向信息，单位度

                sb.append("\naddr : ");
                sb.append(location.getAddrStr());    //获取地址信息

                sb.append("\ndescribe : ");
                sb.append("gps定位成功");

            } else if (location.getLocType() == BDLocation.TypeNetWorkLocation){

                // 网络定位结果
                sb.append("\naddr : ");
                sb.append(location.getAddrStr());    //获取地址信息

                sb.append("\noperationers : ");
                sb.append(location.getOperators());    //获取运营商信息

                sb.append("\ndescribe : ");
                sb.append("网络定位成功");

            } else if (location.getLocType() == BDLocation.TypeOffLineLocation) {

                // 离线定位结果
                sb.append("\ndescribe : ");
                sb.append("离线定位成功，离线定位结果也是有效的");

            } else if (location.getLocType() == BDLocation.TypeServerError) {

                sb.append("\ndescribe : ");
                sb.append("服务端网络定位失败，可以反馈IMEI号和大体定位时间到loc-bugs@baidu.com，会有人追查原因");

            } else if (location.getLocType() == BDLocation.TypeNetWorkException) {

                sb.append("\ndescribe : ");
                sb.append("网络不同导致定位失败，请检查网络是否通畅");

            } else if (location.getLocType() == BDLocation.TypeCriteriaException) {

                sb.append("\ndescribe : ");
                sb.append("无法获取有效定位依据导致定位失败，一般是由于手机的原因，处于飞行模式下一般会造成这种结果，可以试着重启手机");

            }

            sb.append("\nlocationdescribe : ");
            sb.append(location.getLocationDescribe());    //位置语义化信息

            List<Poi> list = location.getPoiList();    // POI数据
            if (list != null) {
                sb.append("\npoilist size = : ");
                sb.append(list.size());
                for (Poi p : list) {
                    sb.append("\npoi= : ");
                    sb.append(p.getId() + " " + p.getName() + " " + p.getRank());
                }
            }
            Log.i("BaiduLocationApiDem", sb.toString());
            BitmapDescriptor bitmap= BitmapDescriptorFactory.fromResource(R.drawable.location_marker);
            latlng=new LatLng(location.getLatitude(),location.getLongitude());
            OverlayOptions options=new MarkerOptions().position(latlng).icon(bitmap);
            mbaidumap.addOverlay(options);
            MapStatus mMapStatus = new MapStatus.Builder()
                    .target(latlng)
                    .zoom(18)
                    .build();
            MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mMapStatus);
            mbaidumap.setMapStatus(mMapStatusUpdate);
        }
    }
    private void initLocation(){
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        //可选，默认高精度，设置定位模式，高精度，低功耗，仅设备

        option.setCoorType("bd09ll");
        //可选，默认gcj02，设置返回的定位结果坐标系

        int span=1000;
        option.setScanSpan(span);
        //可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的

        option.setIsNeedAddress(true);
        //可选，设置是否需要地址信息，默认不需要

        option.setOpenGps(true);
        //可选，默认false,设置是否使用gps

        option.setLocationNotify(true);
        //可选，默认false，设置是否当GPS有效时按照1S/1次频率输出GPS结果

        option.setIsNeedLocationDescribe(true);
        //可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”

        option.setIsNeedLocationPoiList(true);
        //可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到

        option.setIgnoreKillProcess(false);
        //可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死

        option.SetIgnoreCacheException(false);
        //可选，默认false，设置是否收集CRASH信息，默认收集

        option.setEnableSimulateGps(false);
        //可选，默认false，设置是否需要过滤GPS仿真结果，默认需要

        mLocationClient.setLocOption(option);
    }
    class MyDrivingOverlay extends DrivingRouteOverlay{
        public MyDrivingOverlay(BaiduMap arg0) {
            super(arg0);
        }

        @Override
        public BitmapDescriptor getStartMarker() {
            // 覆写此方法以改变默认起点图标
            return BitmapDescriptorFactory.fromResource(R.drawable.icon_start);
        }

        @Override
        public BitmapDescriptor getTerminalMarker() {
            // 覆写此方法以改变默认终点图标
            return BitmapDescriptorFactory.fromResource(R.drawable.icon_end);
        }
    }
}

