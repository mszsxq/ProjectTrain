package com.example.carepet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import lrq.com.addpopmenu.PopMenu;
import lrq.com.addpopmenu.PopMenuItem;
import lrq.com.addpopmenu.PopMenuItemListener;
import me.majiajie.pagerbottomtabstrip.NavigationController;
import me.majiajie.pagerbottomtabstrip.PageNavigationView;
import me.majiajie.pagerbottomtabstrip.listener.OnTabItemSelectedListener;
import me.majiajie.pagerbottomtabstrip.listener.SimpleTabItemSelectedListener;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.carepet.PostPuppy.CommonUtil;
import com.example.carepet.PostPuppy.ReleaseMessageActivity;
import com.google.android.material.navigation.NavigationView;


import java.io.ByteArrayInputStream;

public class MainActivity extends AppCompatActivity {
    private DrawerLayout Drawer;
    private NavigationView navigationView;
    private ImageView touxiang;
    private RelativeLayout layout;
    private TextView nick_phone;
    private ImageView nick_image;
    private TextView nick_name;
    private AddFragment addFragment;
    private MapFragment mapFragment;
    private CommunityFragment communityFragment;
    private NavigationController navigationController;
    private PageNavigationView tab;
    private PopMenu mPopMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initFragment(0);
        initTabBar();
        initPop();


        Drawer.closeDrawer(GravityCompat.END);
        navigationView.setItemIconTintList(null);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                //为item设置逐个点击事件
               switch (menuItem.getItemId()){
                  case  R.id.daren:
                      Toast.makeText(MainActivity.this,"暂时还没有被开发出来~",Toast.LENGTH_SHORT).show();
                      break;
                   case R.id.xiugai:
                       Toast.makeText(MainActivity.this,"暂时还没有被开发出来~",Toast.LENGTH_SHORT).show();
                       break;
                   case R.id.yinsi:
                       Toast.makeText(MainActivity.this,"暂时还没有被开发出来~",Toast.LENGTH_SHORT).show();
                       break;
                }
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
//        nick_image.setImageResource(R.drawable.tx);
        //设置图像大小时，必须先有第一句才可以进行设置
        getBitmapFromSharedPreferences(nick_image);
        nick_image.setAdjustViewBounds(true);
        nick_image.setMaxHeight(180);
        nick_image.setMaxWidth(180);

        /*点击头像跳转到上传头像界面*/
        nick_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(MainActivity.this,LookPicture.class);
                int frgTag = navigationController.getSelected();
//                int frgTag = fragmentTabHost.getCurrentTab();
                Log.i("tag",frgTag+"");
                intent.putExtra("tagId",frgTag);
                startActivity(intent);
            }
        });
    }
    public void initPop(){
        mPopMenu = new PopMenu.Builder().attachToActivity(MainActivity.this)
                .addMenuItem(new PopMenuItem("晒宠贴", getResources().getDrawable(R.drawable.shaichong)))
                .addMenuItem(new PopMenuItem("经验贴", getResources().getDrawable(R.drawable.jingyan)))
                .addMenuItem(new PopMenuItem("寻找贴", getResources().getDrawable(R.drawable.xunzhao)))
                .setOnItemClickListener(new PopMenuItemListener() {
                    @Override
                    public void onItemClick(PopMenu popMenu, int position) {
                        switch (position) {
                            case 0:
                                CommonUtil.uploadPictures(MainActivity.this, 9 , 0);
                                Intent intent = new Intent();
                                intent.setClass(getApplicationContext(), ReleaseMessageActivity.class);
                                intent.putExtra("flag",0);
                                startActivity(intent);

                                Toast.makeText(MainActivity.this, "你点击了第0个位置", Toast.LENGTH_SHORT).show();
                                break;
                            case 1:
                                Toast.makeText(MainActivity.this, "你点击了第1个位置", Toast.LENGTH_SHORT).show();
                                break;
                            case 2:
                                Toast.makeText(MainActivity.this, "你点击了第2个位置", Toast.LENGTH_SHORT).show();
                                break;
                        }
                    }
                })
                .build();
        mPopMenu.setCloseMenuMarginbottom(-30);
    }
    private void initTabBar(){
        tab = (PageNavigationView) findViewById(R.id.tab);
        navigationController = tab.material()
                .addItem(R.drawable.map, "地图")
                .addItem(R.drawable.jiahao, "添加")
                .addItem(R.drawable.shequ, "社区")
                .build();
        //    底部栏监听事件
        navigationController.addTabItemSelectedListener(new OnTabItemSelectedListener() {
            @Override
            public void onSelected(int index, int old) {
                switch (index){
                    case 0:
                        initFragment(0);
                        mPopMenu.hide();
                        break;
                    case 1:
                        mPopMenu.show();
                        break;
                    case 2:
                        initFragment(2);
                        mPopMenu.hide();
                        break;
                }
            }

            @Override
            public void onRepeat(int index) {
                if (index ==1){
                   if( mPopMenu.isShowing()){} else{
                       mPopMenu.show();
                   }
                }
            }
        });
    }

    private void initFragment(@Nullable int i) {

        FragmentManager fragmentManager=getSupportFragmentManager();
        FragmentTransaction transaction=fragmentManager.beginTransaction();
        hideFragments(transaction);

        switch (i){
            case 0:
                hideFragments(transaction);
                if (mapFragment==null){
                    mapFragment=new MapFragment();
                    transaction.add(R.id.content,mapFragment,"map");
                }else {
                    transaction.show(mapFragment);
                }
                break;
            case 1:
                hideFragments(transaction);
                if (addFragment==null){
                   addFragment=new AddFragment();
                    transaction.add(R.id.content,addFragment,"add");
                }else {
                    transaction.show(addFragment);
                }
                break;
            case 2:
                hideFragments(transaction);
                if (communityFragment==null){
                    communityFragment=new CommunityFragment();
                    transaction.add(R.id.content,communityFragment,"community");
                }else {
                    transaction.show(communityFragment);
                }
                break;
            default:
                break;
        }
        //
        transaction.commit();
    }
    private void hideFragments(FragmentTransaction transaction) {
        if (mapFragment != null) {
            transaction.hide(mapFragment);
        }
        if (communityFragment != null) {
            transaction.hide(communityFragment);
        }
        if (addFragment != null) {
            transaction.hide(addFragment);
        }
    }

    private void getBitmapFromSharedPreferences(ImageView imageView) {
        SharedPreferences sharedPreferences=getSharedPreferences("testSP", Context.MODE_PRIVATE);
        //第一步:取出字符串形式的Bitmap
        String imageString=sharedPreferences.getString("image", "");
        //第二步:利用Base64将字符串转换为ByteArrayInputStream
        byte[] byteArray= Base64.decode(imageString, Base64.DEFAULT);
        if(byteArray.length==0){
            imageView.setImageResource(R.drawable.tx);
        }else{
            ByteArrayInputStream byteArrayInputStream=new ByteArrayInputStream(byteArray);

            //第三步:利用ByteArrayInputStream生成Bitmap
            Bitmap bitmap= BitmapFactory.decodeStream(byteArrayInputStream);
            imageView.setImageBitmap(bitmap);
        }

    }

    private void initView() {

        Drawer = findViewById(R.id.da);
        navigationView =findViewById(R.id.cebian);
        layout=findViewById(R.id.header_layout);
        touxiang=findViewById(R.id.touxiang);
        getBitmapFromSharedPreferences(touxiang);

    }

    public void onBackPressed() {
        if (Drawer.isDrawerOpen(GravityCompat.START)) {
            Drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

}
class listener implements  SimpleTabItemSelectedListener{

    @Override
    public void onSelected(int index, int old) {

    }
}
