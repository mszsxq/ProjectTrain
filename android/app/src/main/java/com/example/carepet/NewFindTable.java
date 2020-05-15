package com.example.carepet;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.bumptech.glide.Glide;
import com.example.carepet.entity.FindTable;
import com.google.gson.Gson;
import com.tangxiaolv.telegramgallery.GalleryActivity;
import com.tangxiaolv.telegramgallery.GalleryConfig;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import androidx.annotation.Nullable;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class NewFindTable extends Activity {
    private LinearLayout mRelativeLayout;
    private RelativeLayout mRelativeLayout1;
    private EditText mEditText;
    private EditText mEditText1;
    private ImageView tuichu;
    private ImageView mImageView1;
    private ImageView mImageView2;
    private ImageView mImageView3;
    private Button fabu;
    private TextView dizhi;
    private Button dizhi1;
    private Button dizhi2;
    private Button dizhi3;
    private int reqCode=12;
    private List<String> imgs=new ArrayList<String>();
    private boolean isFirstLocation = true;
    //初始化LocationClient定位类
    private LocationClient mLocationClient = null;
    //BDAbstractLocationListener为7.2版本新增的Abstract类型的监听接口，原有BDLocationListener接口
    private BDLocationListener myListener = new MyLocationListener();
    //经纬度
    private double lat;
    private double lon;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.newfindtable);
        initView();
        //声明LocationClient类
        mLocationClient = new LocationClient(this);
        //注册监听函数

        mLocationClient.registerLocationListener(myListener);
        initLocation();
        //开始定位
        mLocationClient.start();
    }
    private void initView(){
        mRelativeLayout=findViewById(R.id.imgcontent);
        mRelativeLayout1=findViewById(R.id.noimg);
        mEditText=findViewById(R.id.edit_query);
        mEditText1=findViewById(R.id.edit_query1);
        tuichu=findViewById(R.id.tuichu);
        mImageView1=findViewById(R.id.image1);
        mImageView2=findViewById(R.id.image2);
        mImageView3=findViewById(R.id.image3);
        fabu=findViewById(R.id.fabu);
        dizhi=findViewById(R.id.dizhi);
        dizhi1=findViewById(R.id.button1);
        dizhi2=findViewById(R.id.button2);
        dizhi3=findViewById(R.id.button3);
        tuichu.setOnClickListener(new mbuttonlistener());
        fabu.setOnClickListener(new mbuttonlistener());
        dizhi1.setOnClickListener(new mbuttonlistener());
        dizhi2.setOnClickListener(new mbuttonlistener());
        dizhi3.setOnClickListener(new mbuttonlistener());
        mRelativeLayout1.setOnClickListener(new mbuttonlistener());

    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (12 == requestCode && resultCode == Activity.RESULT_OK) {
            imgs = (List<String>) data.getSerializableExtra(GalleryActivity.PHOTOS);
            for (int a=0;a<imgs.size();a++){
//                new OssService(getApplicationContext()).uploadImage("",im,"https://www.baidu.com/");
                ImageView imageView=new ImageView(getApplicationContext());
                if (a==0){
                    Glide.with(getApplicationContext()).load(new File(imgs.get(a))).into(mImageView1);
                    LayoutParams layoutParams=mImageView1.getLayoutParams();
                    layoutParams.width=500;
                    layoutParams.height=500;
                    mImageView1.setLayoutParams(layoutParams);
                }
                if (a==1){
                    Glide.with(getApplicationContext()).load(new File(imgs.get(a))).into(mImageView2); mImageView2.setMaxWidth(140);
                    LayoutParams layoutParams=mImageView2.getLayoutParams();
                    layoutParams.width=500;
                    layoutParams.height=500;
                    mImageView2.setLayoutParams(layoutParams);}
                if (a==2){
                    Glide.with(getApplicationContext()).load(new File(imgs.get(a))).into(mImageView3); mImageView3.setMaxWidth(140);
                    LayoutParams layoutParams=mImageView3.getLayoutParams();
                    layoutParams.width=500;
                    layoutParams.height=500;
                    mImageView3.setLayoutParams(layoutParams);}
            }
        }
    }
    class mbuttonlistener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.tuichu:
//                    new OssService(getApplicationContext()).downLoad("","OIP.jpg");
                    finish();

                    break;
                case R.id.fabu:
                    String te1=mEditText.getText().toString().trim();
                    String te2=mEditText1.getText().toString().trim();
                    String didian =dizhi.getText().toString().trim();
                    FindTable findTable=new FindTable();
                    findTable.setTitle(te1);
                    findTable.setContent(te2);
                    findTable.setLongitude(lon);
                    findTable.setLatitude(lat);
                    SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                    String imgjson="";
                    for (String img:imgs){
                        String im=img.split("/")[img.split("/").length-1];
                        imgjson="https://picturer.oss-cn-beijing.aliyuncs.com/"+im+"--"+imgjson;
                        Log.e("img",img);
                    }
                    findTable.setImgjson(imgjson);
//                    https://picturer.oss-cn-beijing.aliyuncs.com/
                    findTable.setTime(ft.format(new Date()));
                    findTable.setUserid(1);
                    findTable.setCity(dizhi1.getText().toString());
//                    findTable.setCity(CityInfo);
                    Gson gson=new Gson();
                    String findtableString=gson.toJson(findTable);
                    OkHttpClient okHttpClient=new OkHttpClient();
                    Request request = new Request.Builder()
                            .url("http://175.24.16.26:8080/CarePet/findtable/insertfindtable?findtable="+findtableString)
                            .build();
                    Call call = okHttpClient.newCall(request);
                    call.enqueue(new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {

                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            response.body().string();
                        }
                    });
//                    new OssService(getApplicationContext()).downLoad("","OIP.jpg");
                    finish();
                    break;
                case R.id.button1:
                    dizhi.setText(dizhi1.getText());
                    break;
                case R.id.button2:
                    dizhi.setText(dizhi2.getText());
                    break;
                case R.id.button3:
                    dizhi.setText(dizhi3.getText());
                    break;
                case R.id.noimg:
                    GalleryConfig config = new GalleryConfig.Build()
                            .limitPickPhoto(3)
                            .singlePhoto(false)
                            .hintOfPick("this is pick hint")
                            .filterMimeTypes(new String[]{"image/jpeg"})
                            .build();
                    GalleryActivity.openActivity(NewFindTable.this, reqCode, config);
                    break;
            }
        }
    }
    private void initLocation() {
        LocationClientOption option = new LocationClientOption();
        //可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        //可选，默认gcj02，设置返回的定位结果坐标系
        option.setCoorType("bd09ll");
        //可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
        int span = 0;
        option.setScanSpan(0);
        //可选，设置是否需要地址信息，默认不需要
        option.setIsNeedAddress(true);
        //可选，默认false,设置是否使用gps
        option.setOpenGps(true);
        //可选，默认false，设置是否当GPS有效时按照1S/1次频率输出GPS结果
        option.setLocationNotify(false);
        //可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
        option.setIsNeedLocationDescribe(true);
        //可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
        option.setIsNeedLocationPoiList(true);
        //可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
        option.setIgnoreKillProcess(false);
        //可选，默认false，设置是否收集CRASH信息，默认收集
        option.SetIgnoreCacheException(false);
        //可选，默认false，设置是否需要过滤GPS仿真结果，默认需要
        option.setEnableSimulateGps(false);
        mLocationClient.setLocOption(option);
    }

    /**
     * 实现定位监听 位置一旦有所改变就会调用这个方法
     * 可以在这个方法里面获取到定位之后获取到的一系列数据
     */
    public class MyLocationListener implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            //获取定位结果
            location.getTime();    //获取定位时间
            location.getLocationID();    //获取定位唯一ID，v7.2版本新增，用于排查定位问题
            location.getLocType();    //获取定位类型
            location.getLatitude();    //获取纬度信息
            location.getLongitude();    //获取经度信息
            location.getRadius();    //获取定位精准度
            location.getAddrStr();    //获取地址信息
            location.getCountry();    //获取国家信息
            location.getCountryCode();    //获取国家码
            location.getCity();    //获取城市信息
            dizhi1.setText(location.getCity());
            location.getCityCode();    //获取城市码
            location.getDistrict();    //获取区县信息
            dizhi2.setText(location.getDistrict());
            dizhi3.setText(location.getStreet());
            location.getStreet();    //获取街道信息
            location.getStreetNumber();    //获取街道码
            location.getLocationDescribe();    //获取当前位置描述信息
            location.getPoiList();    //获取当前位置周边POI信息

            location.getBuildingID();    //室内精准定位下，获取楼宇ID
            location.getBuildingName();    //室内精准定位下，获取楼宇名称
            location.getFloor();    //室内精准定位下，获取当前位置所处的楼层信息
            //经纬度
            lat = location.getLatitude();
            lon = location.getLongitude();

            //这个判断是为了防止每次定位都重新设置中心点和marker
            if (isFirstLocation) {
                isFirstLocation = false;
                //设置并显示中心点\\
            }
        }
    }

}
