package com.example.carepet;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeOption;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.bumptech.glide.Glide;
import com.example.carepet.entity.FindTable;
import com.example.carepet.entity.MapContent;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class MapDetails extends AppCompatActivity {
    private TextView title;
    private TextView content;
    private TextView name;
    private TextView city;
    private TextView addr;
    private TextView type;
    private ImageView bimg;
    private MapContent mapContent;
    
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle =getIntent().getExtras();
        setContentView(R.layout.map_details);
        mapContent = (MapContent) bundle.getSerializable("findtable");
        
       
        getView();
        setView();
    }
    private void setView(){
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        List<Address> addresses = null;
        String locality = "";
        try {
            addresses = geocoder.getFromLocation(mapContent.getFindTable().getLatitude(),
                    mapContent.getFindTable().getLongitude(), 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(addresses!=null||addresses.size()>0) {
           locality=addresses.get(0).getLocality()+
            addresses.get(0).getSubLocality()+
            addresses.get(0).getThoroughfare();
            Log.e("aaa",locality);
        }
        String name1=mapContent.getName();
        String bimg1= mapContent.getBimg();
        String city1=mapContent.getFindTable().getCity();
        String content1=mapContent.getFindTable().getContent();
        String type1=mapContent.getFindTable().getPettype();
        String title1=mapContent.getFindTable().getTitle();
        name.setText(name1);
        title.setText(title1);
        city.setText(city1);
        addr.setText(locality);
        content.setText(content1);
        type.setText(type1);
        Glide.with(this).load(bimg1).fitCenter().into(bimg);
    }
    public void getView(){
        title=findViewById(R.id.textView);
        city=findViewById(R.id.textView1);
        name=findViewById(R.id.textView2);
        type=findViewById(R.id.textView3);
        bimg=findViewById(R.id.imageView1);
        content=findViewById(R.id.textView7);
        addr=findViewById(R.id.textView5);
    }


}
