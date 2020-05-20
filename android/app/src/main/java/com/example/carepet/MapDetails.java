package com.example.carepet;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.carepet.entity.FindTable;
import com.example.carepet.entity.MapContent;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class MapDetails extends AppCompatActivity {
    private TextView title;
    private TextView content;
    private TextView name;
    private TextView city;
    private TextView type;
    private ImageView bimg;
    private MapContent mapContent;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle =getIntent().getExtras();
        mapContent = (MapContent) bundle.getSerializable("findtable");
        setContentView(R.layout.map_details);
        getView();
        setView();
    }
    private void setView(){
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
    }
}
