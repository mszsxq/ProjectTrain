package com.example.carepet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentTabHost;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {
    private DrawerLayout Drawer;
    private NavigationView navigationView;
    private ImageView touxiang;
    private RelativeLayout layout;
    private TextView nick_phone;
    private ImageView nick_image;
    private TextView nick_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        FragmentTabHost fragmentTabHost = findViewById(android.R.id.tabhost);
        fragmentTabHost.setup(this,
                getSupportFragmentManager(),
                android.R.id.tabcontent);
        TabHost.TabSpec tabSpec1 = fragmentTabHost.newTabSpec("地图").setIndicator("地图");
        fragmentTabHost.addTab(tabSpec1,
                MapFragment.class,
                null);

        TabHost.TabSpec tabSpec2 = fragmentTabHost.newTabSpec("add").setIndicator("+");
        fragmentTabHost.addTab(tabSpec2,
                AddFragment.class,
                null);
        TabHost.TabSpec tabSpec3 = fragmentTabHost.newTabSpec("社区").setIndicator("社区");
        fragmentTabHost.addTab(tabSpec3,
                CommunityFragment.class,
                null);

        fragmentTabHost.setCurrentTab(1);

        Drawer.closeDrawer(GravityCompat.END);
        navigationView.setItemIconTintList(null);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                //为item设置逐个点击事件
//               switch (menuItem.getItemId()){
//                  case  R.id.nav_camera:
//                      Toast.makeText(MainActivity.this,"nav_camera",Toast.LENGTH_SHORT).show();
//                      break;
//                }
                /* Toast.makeText(mysellf.this,menuItem.getTitle().toString(),Toast.LENGTH_SHORT).show();*/
                return false;
            }
        });
        layout.setBackgroundColor(Color.parseColor("#B4D1E1"));
        /*点击头像出现右侧滑抽屉*/
        touxiang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Drawer.openDrawer(GravityCompat.END);
            }
        });
        /*设置侧滑抽屉的header系列*/
        View headerLayout = navigationView.inflateHeaderView(R.layout.header_layout);
        nick_phone = (TextView)headerLayout.findViewById(R.id.nick_phone);
        nick_name=(TextView) headerLayout.findViewById(R.id.nick_name);
        nick_image=(ImageView)headerLayout.findViewById(R.id.nick_image);
        nick_phone.setText("12345555");
        nick_name.setText("可乐加冰");
        nick_image.setImageResource(R.drawable.tx);
        //设置图像大小时，必须先有第一句才可以进行设置
        nick_image.setAdjustViewBounds(true);
        nick_image.setMaxHeight(160);
        nick_image.setMaxWidth(160);

        /*点击头像跳转到上传头像界面*/
        nick_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(MainActivity.this,LookPicture.class);
                startActivity(intent);
            }
        });
    }
    private void initView() {
        Drawer = findViewById(R.id.da);
        navigationView =findViewById(R.id.cebian);
        layout=findViewById(R.id.header_layout);
        touxiang=findViewById(R.id.touxiang);
    }

    public void onBackPressed() {
        if (Drawer.isDrawerOpen(GravityCompat.START)) {
            Drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

}
