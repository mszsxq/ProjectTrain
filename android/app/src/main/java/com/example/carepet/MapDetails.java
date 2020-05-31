package com.example.carepet;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.carepet.entity.FindTable;
import com.example.carepet.entity.MapContent;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

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
    private  double jingdu;
    private  double weidu;
    private String address;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle =getIntent().getExtras();
        setContentView(R.layout.map_details);
        mapContent = (MapContent) bundle.getSerializable("findtable");
        MyAsnycTask3 myAsnycTask3 =new MyAsnycTask3();
        myAsnycTask3.execute();
        getView();
        setView();
    }
    private void setView(){
        weidu=mapContent.getFindTable().getLatitude();
        jingdu=mapContent.getFindTable().getLongitude();
        String name1=mapContent.getName();
        String bimg1= mapContent.getBimg();
        String city1=mapContent.getFindTable().getCity();
        String content1=mapContent.getFindTable().getContent();
        String type1=mapContent.getFindTable().getPettype();
        String title1=mapContent.getFindTable().getTitle();
        name.setText(name1);
        title.setText(title1);
        city.setText(city1);

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

    class MyAsnycTask3 extends AsyncTask<Integer, Integer,String> {
        //用来写等待时的UI（加载中转圈）
        @Override
        protected void onPreExecute() {

        }

        @Override
        protected String doInBackground(Integer... integers) {
            String ak="2xoGra94KHd666IrmIlUpNTzMs2qTVXk";
            String mcode="89:B0:28:32:A1:B6:10:62:8A:C7:A6:DA:DB:8C:3E:4D:0D:05:B7:CB;com.example.carepet";
            final String ip ="http://api.map.baidu.com/reverse_geocoding/v3/?ak=" +
                  ak  +"&output=json&coordtype=BD09&mcode=" +mcode+
                    "&location="+weidu+","+jingdu;
            URL url;
            String str = "";
            try {
                url = new URL(ip);
                URLConnection conn = url.openConnection();
                InputStream in = conn.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
                str = reader.readLine();

            } catch (IOException  e) {
                e.printStackTrace();
            }
            if (!str.equals("")) {
                int addss = str.indexOf("formatted_address\":");
                int added = str.indexOf("\",\"business");
                address = str.substring(addss+20, added);
            }
            return str ;
        }

        @Override
        protected void onPostExecute(String json) {
            addr.setText(address);
        }
    }

}
