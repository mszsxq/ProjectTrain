package com.example.carepet;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
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
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.example.carepet.entity.FindTable;
import com.example.carepet.entity.User;
import com.example.carepet.oss.OssService;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.snail.slidenested.SlideNestedPanelLayout;
import com.snail.slidenested.StateCallback;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

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
    private List<FindTable> postions = new ArrayList<>();
    private Marker marker;
    private List<Marker> markerList = new ArrayList<>();
    protected static final int CHANGE_UI = 1;
    protected static final int ERROR = 2;

    private Bitmap basebitmap;
    private Bitmap bitmap;
    private List<Bitmap> bitmapp = new ArrayList<>();
    private Context context;

    private String path = null;
    private String purl = null;

    private ImageView avatar;
    private ImageView imageView1;
    private TextView textView;
    private TextView textView1;
    private TextView textView2;
    private TextView textView3;
    private TextView textView5;
    private TextView textView7;

    private List<Bitmap> bitmaps = new ArrayList<>();
    // 主线程创建消息处理器
    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            Log.e("panduan",msg.what+"");
            if(msg.what == 26){
                super.handleMessage(msg);
                String info = (String)msg.obj;
                Log.e("zz",info);
                Type type=new TypeToken<List<FindTable>>(){}.getType();
                Gson gson=new Gson();
                List<FindTable> list = gson.fromJson(info,type);
                Log.e("error",list.toString());
                for(int i=0;i<list.size();i++){
                    postions.add(list.get(i));//my
                    path = postions.get(i).getImgjson().split("--")[2];
//                    path = postions.get(i).getImgjson();
                    Log.e("pikaqiu",path);
//                    getPicBitmap();
                }
            }
//            getPicBitmap(postions);
//            change(postions);
//            getPic(postions);
            bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.iconlalla);
            bitmaps = getZoomImage(postions,bitmap,128,128);
            addOverlay(postions);
            if (msg.what == CHANGE_UI) {
                bitmaps = (List<Bitmap>) msg.obj;
                Log.e("eeeeee",bitmaps.toString());
//                basebitmap = (Bitmap) msg.obj;
                /*bitmaps = getZoomImage(bitmaps,128,128);
                addOverlay(postions);*/
            }else if(msg.what == 62){
                basebitmap = (Bitmap)msg.obj;
                imageView1.setImageBitmap(basebitmap);
            }else if(msg.what == 66){
                Log.e("皮卡what",msg.what+"");
                String info = (String)msg.obj;
                Log.e("kb",info);
                Type type=new TypeToken<User>(){}.getType();
                Gson gson=new Gson();
                User user = gson.fromJson(info,type);
                purl = user.getTouxiang();
//                getPicBitmap2();
                File file=new File(getApplicationContext().getFilesDir(),"oss"+purl);
                if(!file.exists()){
                    OssService ossService = new OssService(getApplicationContext());
                    ossService.downLoad("",purl);//listData.getPic()
                    Log.e("检测","dd");
                }
                Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
                avatar.setImageBitmap(bitmap);
                textView2.setText(user.getUsername());
            }else if(msg.what == 22){
                basebitmap = (Bitmap)msg.obj;
                avatar.setImageBitmap(basebitmap);
            }else if (msg.what == ERROR) {
                /*Toast.makeText(MapFindAcitvity.this, "显示图片错误",
                        Toast.LENGTH_SHORT).show();*/
                Log.e("报错了！","llalalalala");
            }
            //点击事件
            mBaiduMap.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {
                @Override
                public boolean onMarkerClick(Marker marker) {
                    Bundle bundle = marker.getExtraInfo();
                    FindTable infoUtil = (FindTable) bundle.getSerializable("info");
                    Log.e("皮卡皮卡",infoUtil.getId()+infoUtil.getTitle()+"");
                    Log.e("皮卡",infoUtil.getUserid()+"");
                    getUser(infoUtil.getUserid());
                    path = infoUtil.getImgjson().split("--")[2];
                    Glide.with(getApplicationContext()).load(path).asBitmap().into(new SimpleTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                            bitmap = resource;
                            Log.e("kenan",bitmap.toString());
                            imageView1.setImageBitmap(bitmap);
                        }
                    });
                    textView.setText(infoUtil.getTitle());
                    textView1.setText(infoUtil.getCity());
                    textView3.setText(infoUtil.getPettype());
                    textView5.setText(infoUtil.getTime());
                    textView7.setText(infoUtil.getContent());
                    return true;
                }
            });
        }
    };
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.activity_totally);
        // 获取地图控件引用
        mMapView = (MapView)findViewById(R.id.bmapView);
        avatar = findViewById(R.id.avatar);
        imageView1 = findViewById(R.id.imageView1);
        textView = findViewById(R.id.textView);
        textView1 = findViewById(R.id.textView1);
        textView2 = findViewById(R.id.textView2);
        textView3 = findViewById(R.id.textView3);
        textView5 = findViewById(R.id.textView5);
        textView7 = findViewById(R.id.textView7);
        //初始化Map
        initializeMap();
        //隐藏logo
        hideLogo();
        //比例尺
        zoomLevelOp();
        //定位
        LocationOption();
        //获取数据
        checkedShopper();
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
    private void addOverlay(List<FindTable> postions) {
        //清空地图
        mBaiduMap.clear();
        //创建marker的显示图标
//        BitmapDescriptor bitmap = BitmapDescriptorFactory.fromBitmap(basebitmap);
        LatLng latLng = null;
        Marker marker;
        OverlayOptions options;
        Log.e("ccccccp",postions.size()+"");
        for(int i = 0;i<postions.size();i++){
            BitmapDescriptor bitmap = BitmapDescriptorFactory.fromBitmap(bitmaps.get(i));
            FindTable info = postions.get(i);
            //获取经纬度
            latLng = new LatLng(info.getLatitude(),info.getLongitude());
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
    public void getPic(final List<FindTable> poss){
        for(int i = 0;i<poss.size();i++){
            FindTable listData = poss.get(i);
//            Log.e("数据",listData.getImgjson().split("--")[2].split("/")[3]);
            File file=new File(getApplicationContext().getFilesDir(),"oss/"+listData.getImgjson().split("--")[2].split("/")[3]);
            if(!file.exists()){
                OssService ossService = new OssService(getApplicationContext());
                ossService.downLoad("",listData.getImgjson().split("--")[2].split("/")[3]);//listData.getPic()
                Log.e("cccccc","dd");
            }
            Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
            bitmaps.add(bitmap);
            Log.e("cccccce",bitmap+"");
            Log.e("ccccccf",bitmaps.get(i)+"");
            Log.e("cccccca",file.getAbsolutePath());
            Log.e("ccccccb",bitmaps.size()+"");
        }
        Log.e("cccccccccc",bitmaps.get(0)+"");
    }
    public final static Bitmap returnBitMap(String url) {
        URL myFileUrl = null;
        Bitmap bitmap = null;
        try {
            myFileUrl = new URL(url);
            HttpURLConnection conn;
            conn = (HttpURLConnection) myFileUrl.openConnection();
            conn.setDoInput(true);
            int length = conn.getContentLength();
            conn.connect();
            InputStream is = conn.getInputStream();
            BufferedInputStream bis = new BufferedInputStream(is, length);
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize =2;    // 设置缩放比例
            Rect rect = new Rect(0, 0,0,0);
            bitmap = BitmapFactory.decodeStream(bis,rect,options);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }
    public void change(final List<FindTable> poss){
        /*new Thread() {
            public void run() {
                for (int i = 0; i < poss.size(); i++) {
                    try {
                        Bitmap myBitmap = Glide.with(getApplicationContext())
                                .asBitmap()
                                .load(poss.get(i).getImgjson().split("--")[1])
                                .submit(100, 100).get();
                        Bitmap bitmap = Bitmap.createBitmap(myBitmap, 0, 0, myBitmap.getWidth(), myBitmap.getHeight());
                        Log.e("wwww", i + bitmap.toString());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

        }.start();*/
        for(int i = 0;i<poss.size();i++){
            Glide.with(getApplicationContext()).load(postions.get(i).getImgjson().split("--")[1]).asBitmap().into(new SimpleTarget<Bitmap>() {
                @Override
                public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                    bitmap = resource;
                    Log.e("kenan",bitmap.toString());
//                  avatar.setImageBitmap(bitmap);
                    bitmapp.add(bitmap);
                    Log.e("kenan",bitmapp.size()+"::::"+poss.size());
                    if(bitmapp.size() == poss.size()-1){
                        /*bitmaps = getZoomImage(bitmapp,128,128);
                        addOverlay(postions);*/
                    }
                }
            });

//            Log.e("kenan1",bitmapp.get(3)+"");
        }
//        Log.e("liebiao",bitmapp.get(2).toString());
//        bitmapp = getZoomImage(bitmapp,64,64);
//        addOverlay(postions);
    }
    //读取网络图片URL
    public void getPicBitmap(final List<FindTable> poss){
        new Thread() {
            private List<Bitmap> bitmaps = new ArrayList<>();
            private HttpURLConnection conn;
            private Bitmap bitmap;
            public void run() {
                // 连接服务器 get 请求 获取图片
                try {
                    //创建URL对象
                    Log.e("ppppp",path);
                    for(int i = 0;i<poss.size();i++){
                        URL url = new URL(poss.get(i).getImgjson().split("--")[2]);
                        // 根据url 发送 http的请求
                        conn = (HttpURLConnection) url.openConnection();
                        // 设置请求的方式
                        conn.setRequestMethod("GET");
                        //设置超时时间
                        conn.setConnectTimeout(5000);
                        // 得到服务器返回的响应码
                        int code = conn.getResponseCode();
                        if (code != 200) {
                            //返回码不等于200 请求服务器失败
                            Message msg = new Message();
                            msg.what = ERROR;
                            handler.sendMessage(msg);
//                          byte[] data = getBytes(is);
//                          bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
                        }
                        //请求网络成功后返回码是200
                        InputStream is = conn.getInputStream();
                        bitmap = BitmapFactory.decodeStream(is);
                        bitmaps.add(bitmap);
                    }
                    //将更改主界面的消息发送给主线程
                    Message msg = new Message();
                    msg.what = CHANGE_UI;
                    msg.obj = bitmaps;
                    handler.sendMessage(msg);
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
    //定义一个根据图片url获取InputStream的方法
    public static byte[] getBytes(InputStream is) throws IOException {
        ByteArrayOutputStream outstream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024]; // 用数据装
        int len = -1;
        while ((len = is.read(buffer)) != -1) {
            outstream.write(buffer, 0, len);
        }
        outstream.close();
        // 关闭流一定要记得。
        return outstream.toByteArray();
    }
    //读取网络图片URL
    public void getPicBitmap1(){
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
                        msg.what = 62;
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
    //读取网络图片URL
    public void getPicBitmap2(){
        new Thread() {
            private HttpURLConnection conn;
            private Bitmap bitmap;
            public void run() {
                // 连接服务器 get 请求 获取图片
                try {
                    //创建URL对象
                    Log.e("ppppp",purl);
                    URL url = new URL(purl);
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
                        msg.what = 22;
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
    public static List<Bitmap> getZoomImage(List<FindTable> poss,Bitmap orgBitmap, double newWidth, double newHeight) {
        if (null == orgBitmap) {
            return null;
        }
        /*if (orgBitmap.isRecycled()) {
            return null;
        }*/
        if (newWidth <= 0 || newHeight <= 0) {
            return null;
        }
        List<Bitmap> bs = new ArrayList<>();
        for(int i = 0;i<poss.size();i++){
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
            bs.add(bitmap);
        }

        return bs;
    }
    //传输数据gson
    private void checkedShopper() {
        new Thread(){
            @Override
            public void run() {
                try {
                    Log.e("pikaqiu","你说一二三，打碎了过往！");
                    URL url = new URL("http://175.24.16.26:8080/CarePet/findtable/listall?a=1");
                    URLConnection conn = url.openConnection();
                    InputStream in = conn.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in, "utf-8"));
                    String info = reader.readLine();
                    Log.e("pikaqiu","我想，左肩有你，右肩微笑！");
                    Log.e("ww",info);
                    wrapperMessage(info);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
    private void wrapperMessage(String info) {
        Message msg = Message.obtain();
        msg.obj = info;
        msg.what = 26;
        handler.sendMessage(msg);
    }
    //传输数据gsonuser
    private void getUser(final int id) {
        new Thread(){
            @Override
            public void run() {
                try {
                    String userId = id+"";
                    Log.e("xx1",userId);
                    URL url = new URL("http://175.24.16.26:8080/CarePet/user/getuser?id="+userId);
                    URLConnection conn = url.openConnection();
                    InputStream in = conn.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in, "utf-8"));
                    String info = reader.readLine();
                    Log.e("pikaqiu","传过来了呢！");
                    Log.e("xx2",info);
                    wrapperMessage1(info);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
    private void wrapperMessage1(String info) {
        Message msg = Message.obtain();
        msg.obj = info;
        msg.what = 66;
        handler.sendMessage(msg);
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