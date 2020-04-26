package com.example.carepet;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.ZoomControls;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.Poi;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.UiSettings;
import com.baidu.mapapi.model.LatLng;
import com.google.android.material.tabs.TabLayout;
import com.snail.slidenested.SlideNestedPanelLayout;
import com.snail.slidenested.StateCallback;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;

public class MapFindAcitvity extends AppCompatActivity {
    private MapView mMapView;
    private BaiduMap mBaiduMap;
    private UiSettings uiSettings;
    private LocationClient locationClient;
    private LocationClientOption locationClientOption;
    private List<String> poiss;
    private int span = 5000;
    private List<OverlayOptions> overlayOptionses=new ArrayList<OverlayOptions>();
    private MarkerOptions options;
    private List<Postion> postions = new ArrayList<>();
    private Marker marker;
    private List<Marker> markerList = new ArrayList<>();
    protected static final int CHANGE_UI = 1;
    protected static final int ERROR = 2;
    private Bitmap basebitmap;
    private String path = null;
    // 主线程创建消息处理器
    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            Log.e("panduan",msg.what+"");
            if (msg.what == CHANGE_UI) {
                Postion postion1 = new Postion();
                postion1.setId(1);
                postion1.setTitle("皮卡丘");
                postion1.setLat(38.02753);
                postion1.setLng(114.567347);
                Postion postion2 = new Postion();
                postion2.setLat(38.029784);
                postion2.setLng(114.565869);
                postion2.setTitle("蒜头王八");
                postion2.setId(2);
                postions.add(postion1);
                postions.add(postion2);
                basebitmap = (Bitmap) msg.obj;
                basebitmap = getZoomImage(basebitmap,64,64);
                addOverlay(postions);
            } else if (msg.what == ERROR) {
                Toast.makeText(MapFindAcitvity.this, "显示图片错误",
                        Toast.LENGTH_SHORT).show();
            }
        }
    };
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.activity_totally);
        // 获取地图控件引用
        mMapView = (MapView)findViewById(R.id.bmapView);
        //初始化Map
        initializeMap();
        //隐藏logo
        hideLogo();
        //比例尺
        zoomLevelOp();
        //定位
        LocationOption();
        //加载图片缩略图
        path = "https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=2155327358,3650797713&fm=15&gp=0.jpg";
        getPicBitmap();
        //多点定wei
        /*Postion postion1 = new Postion();
        postion1.setId(1);
        postion1.setTitle("皮卡丘");
        postion1.setLat(38.02753);
        postion1.setLng(114.567347);
        Postion postion2 = new Postion();
        postion2.setLat(38.029784);
        postion2.setLng(114.565869);
        postion2.setTitle("蒜头王八");
        postion2.setId(2);
        postions.add(postion1);
        postions.add(postion2);
        addOverlay(postions);*/

        //点击事件
        mBaiduMap.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                Bundle bundle = marker.getExtraInfo();
                Postion infoUtil = (Postion) bundle.getSerializable("info");
                Log.e("皮卡皮卡",infoUtil.getId()+infoUtil.getTitle()+"");
                return true;
            }
        });
        //总图构成
        final FrameLayout mFrameLayout = findViewById(R.id.frameLayout);
        TabLayout mTabLayout = (TabLayout) findViewById(R.id.tabLayout);
        mTabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        mTabLayout.addTab(mTabLayout.newTab().setText("费用说明"));
        mTabLayout.addTab(mTabLayout.newTab().setText("预定须知"));
        mTabLayout.addTab(mTabLayout.newTab().setText("退款政策"));
        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        mFrameLayout.setBackgroundColor(Color.parseColor("#ff0000"));
                        break;

                    case 1:
                        mFrameLayout.setBackgroundColor(Color.parseColor("#0000ff"));
                        break;

                    case 2:
                        mFrameLayout.setBackgroundColor(Color.parseColor("#00ff00"));
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        NestedScrollView mScrollView = findViewById(R.id.nestedScrollView);
        mScrollView.animate().translationY(-150).alpha(1.0f).setDuration(500);

        SlideNestedPanelLayout mPanelLayout = findViewById(R.id.slideNestedPanelLayout);
        mPanelLayout.setStateCallback(new StateCallback() {
            @Override
            public void onExpandedState() {
                Log.i("-->","onExpandedState");
            }

            @Override
            public void onCollapsedState() {
                Log.i("-->","onCollapsedState");
            }
        });
    }
    private void LocationOption() {
        locationClient = new LocationClient(getApplicationContext());
        mBaiduMap.setMyLocationEnabled(true);
        locationClientOption = new LocationClientOption();
        locationClientOption.setOpenGps(true);
        locationClientOption.setCoorType("bd09ll");
        locationClientOption.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        locationClientOption.setIsNeedAddress(true);
        locationClientOption.setIsNeedLocationDescribe(true);
        locationClientOption.setIsNeedLocationPoiList(true);
        //多自定义标记
        /*locationClientOption.setScanSpan(span);
        locationClientOption.setLocationNotify(true);// 可选，默认false，设置是否当GPS有效时按照1S/1次频率输出GPS结果
        locationClientOption.setIgnoreKillProcess(false);// 可选，默认为true不杀死，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程
        locationClientOption.SetIgnoreCacheException(false);// 可选，默认false，设置是否收集CRASH信息，默认收集
        locationClientOption.setEnableSimulateGps(false);// 可选，默认false，设置是否需要过滤GPS仿真结果，默认需要*/

        locationClient.setLocOption(locationClientOption);
        locationClient.start();
        locationClient.registerLocationListener(new BDAbstractLocationListener() {
            @Override
            public void onReceiveLocation(BDLocation bdLocation) {
                String addr = bdLocation.getAddrStr();
                String locastring=getIntent().getStringExtra("local1");
                double lat = bdLocation.getLatitude();
                double lng = bdLocation.getLongitude();
                Log.e("啦啦啦啦lat"+lat,"lng"+lng);
                List<Poi> pois = bdLocation.getPoiList();
                poiss=new ArrayList<>();
                for (Poi p:pois){
                    String name = p.getName();
                    String pAddr = p.getAddr();
                    Log.i("mmy"+poiss.size(),"POI:"+name+":"+pAddr);
                    poiss.add("name"+name);
                }
                String time = bdLocation.getTime();
                showLocOnMap(lat,lng);
            }
        });
    }
    private void showLocOnMap(double lat, double lng) {
        BitmapDescriptor icon = BitmapDescriptorFactory.fromResource(R.drawable.location);
        MyLocationConfiguration config = new MyLocationConfiguration(
                MyLocationConfiguration.LocationMode.COMPASS,
                false,
                icon);
        mBaiduMap.setMyLocationConfiguration(config);
        MyLocationData locData = new MyLocationData
                .Builder()
                .latitude(lat)
                .longitude(lng)
                .build();
        mBaiduMap.setMyLocationData(locData);
        MapStatusUpdate msu = MapStatusUpdateFactory.newLatLng(new LatLng(lat,lng));
        mBaiduMap.animateMapStatus(msu);
    }
    //初始化Map
    private void initializeMap() {
        mBaiduMap = mMapView.getMap();
        uiSettings = mBaiduMap.getUiSettings();
    }
    //隐藏logo
    public void hideLogo(){
        View child = mMapView.getChildAt(1);
        if (child != null && (child instanceof ImageView || child instanceof ZoomControls)) {
            child.setVisibility(View.INVISIBLE);
        }
    }
    //比例尺
    private void zoomLevelOp() {
        mBaiduMap.setMaxAndMinZoomLevel(21, 13);
        MapStatusUpdate msu = MapStatusUpdateFactory
                .zoomTo(19);
        mBaiduMap.setMapStatus(msu);
    }

    //显示marker
    private void addOverlay(List<Postion> postions) {
        //清空地图
        mBaiduMap.clear();
        //创建marker的显示图标
        BitmapDescriptor bitmap = BitmapDescriptorFactory.fromBitmap(basebitmap);
        LatLng latLng = null;
        Marker marker;
        OverlayOptions options;
        for(Postion info:postions){
            //获取经纬度
            latLng = new LatLng(info.getLat(),info.getLng());
            //设置marker
            options = new MarkerOptions()
                    .position(latLng)//设置位置
                    .icon(bitmap)//设置图标样式
                    .zIndex(9) // 设置marker所在层级
                    .draggable(true); // 设置手势拖拽;
            //添加marker
            marker = (Marker) mBaiduMap.addOverlay(options);
            //使用marker携带info信息，当点击事件的时候可以通过marker获得info信息
            Bundle bundle = new Bundle();
            //info必须实现序列化接口
            bundle.putSerializable("info", info);
            marker.setExtraInfo(bundle);
        }
    }
    //读取网络图片URL
    public void getPicBitmap(){
        new Thread() {
            private HttpURLConnection conn;
            private Bitmap bitmap;
            public void run() {
                // 连接服务器 get 请求 获取图片
                try {
                    //创建URL对象
                    Log.e("ppppp",path);
                    URL url = new URL(path);
                    // 根据url 发送 http的请求
                    conn = (HttpURLConnection) url.openConnection();
                    // 设置请求的方式
                    conn.setRequestMethod("GET");
                    //设置超时时间
                    conn.setConnectTimeout(5000);
                    // 得到服务器返回的响应码
                    int code = conn.getResponseCode();
                    //请求网络成功后返回码是200
                    if (code == 200) {
                        //获取输入流
                        InputStream is = conn.getInputStream();
                        //将流转换成Bitmap对象
                        bitmap = BitmapFactory.decodeStream(is);
                        //将更改主界面的消息发送给主线程
                        Message msg = new Message();
                        msg.what = CHANGE_UI;
                        msg.obj = bitmap;
                        handler.sendMessage(msg);
                    } else {
                        //返回码不等于200 请求服务器失败
                        Message msg = new Message();
                        msg.what = ERROR;
                        handler.sendMessage(msg);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Message msg = new Message();
                    msg.what = ERROR;
                    handler.sendMessage(msg);
                }
                //关闭连接
                conn.disconnect();
            }
        }.start();
    }
    /**
     * 图片的缩放方法
     *
     * @param orgBitmap ：源图片资源
     * @param newWidth  ：缩放后宽度
     * @param newHeight ：缩放后高度
     * @return
     */
    public static Bitmap getZoomImage(Bitmap orgBitmap, double newWidth, double newHeight) {
        if (null == orgBitmap) {
            return null;
        }
        if (orgBitmap.isRecycled()) {
            return null;
        }
        if (newWidth <= 0 || newHeight <= 0) {
            return null;
        }

        // 获取图片的宽和高
        float width = orgBitmap.getWidth();
        float height = orgBitmap.getHeight();
        // 创建操作图片的matrix对象
        Matrix matrix = new Matrix();
        // 计算宽高缩放率
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // 缩放图片动作
        matrix.postScale(scaleWidth, scaleHeight);
        Bitmap bitmap = Bitmap.createBitmap(orgBitmap, 0, 0, (int) width, (int) height, matrix, true);
        return bitmap;
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        // 在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        mMapView.onDestroy();
    }
    @Override
    public void onResume() {
        super.onResume();
        // 在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
        mMapView.onResume();
    }
    @Override
    public void onPause() {
        super.onPause();
        // 在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
        mMapView.onPause();
    }
}
