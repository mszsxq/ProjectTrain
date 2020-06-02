package com.example.carepet;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import androidx.appcompat.app.AppCompatActivity;

public class Daren extends AppCompatActivity {
    private ImageView pet_a;
    private TextView pet_b;
    private TextView pet_c;
    private  ImageView btn_back;
    private SharedPreferences p;
    private int drtime;
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.daren);
        initview();
        SharedPreferences sharedPreferences=getSharedPreferences("user",MODE_PRIVATE);
        String name=sharedPreferences.getString("name","");
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Daren.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");// HH:mm:ss
        //获取当前时间
        Date date = new Date(System.currentTimeMillis());
        String time1=simpleDateFormat.format(date);
        p=getSharedPreferences("time",MODE_PRIVATE);
        String date_regist= p.getString("regist"+name,"");
        Log.e("time2",date_regist);
        try {
            Date d1=simpleDateFormat.parse(time1);
            Date d2=simpleDateFormat.parse(date_regist);
            drtime= (int) ((d1.getTime()-d2.getTime())/(60*60*1000*24));
            Log.e("tt",(d1.getTime()-d2.getTime())/(60*60*1000*24)+"");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if(0<=drtime&&drtime<=7){
            pet_a.setImageResource(R.drawable.pet1);
            pet_b.setText("爱宠萌新");
            pet_c.setText("连续登陆了"+drtime+"天");
        }else if(drtime>7&&drtime<=30){
            pet_a.setImageResource(R.drawable.pet2);
            pet_b.setText("爱宠达人");
            pet_c.setText("连续登陆了"+drtime+"天");
        }else  if(drtime>30&&drtime<100){
            pet_a.setImageResource(R.drawable.pet5);
            pet_b.setText("爱宠狂热");
            pet_c.setText("连续登陆了"+drtime+"天");
        }else{
            pet_a.setImageResource(R.drawable.pet4);
            pet_b.setText("爱宠殿堂");
            pet_c.setText("连续登陆了"+drtime+"天");
        }

        //计算时间差值
    }
    private void initview() {
        pet_a=findViewById(R.id.pet_a);
        pet_b=findViewById(R.id.pet_b);
        pet_c=findViewById(R.id.pet_c);
        btn_back=findViewById(R.id.btn_back);
    }
}
