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

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.carepet.Community.AddExperience;
import com.example.carepet.Community.search_look;
import com.example.carepet.PostPuppy.CommonUtil;
import com.example.carepet.PostPuppy.ReleaseMessageActivity;
import com.example.carepet.entity.User;
import com.example.carepet.oss.OssService;
import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import static com.baidu.mapapi.BMapManager.getContext;

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
    private SharedPreferences ps;
    private String headPath;
    private SharedPreferences sharedPreferences;
    private String currentheadName;
    private Handler handler;
    private int userId;
    public static MainActivity newInstance() {
        MainActivity activity=new MainActivity();
        return activity;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initFragment(0);
        initTabBar();
        initPop();
        SharedPreferences user = getSharedPreferences("user", Context.MODE_PRIVATE);
        userId = user.getInt("user_id", 0);
        Log.e("id",userId+"");
        getData();
        handler = new Handler() {
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                String object = (String) msg.obj;
                Gson gson = new Gson();
                User user=gson.fromJson(object,User.class);
                String txstr=user.getTouxiang().toString();
                Log.e("头像",txstr);
                nick_phone.setText("carepet，爱宠晒宠。");
                nick_name.setText(user.getUsername());
                nick_image.setAdjustViewBounds(true);
                nick_image.setMaxHeight(180);
                nick_image.setMaxWidth(180);
                /*getHeadFromSD(nick_image);*/
                File file=new File(getApplicationContext().getFilesDir(),"oss/"+txstr);
                if(!file.exists()){
                    OssService ossService = new OssService(getApplicationContext());
                    ossService.downLoad("",txstr);//listData.getPic()
                    Log.e("检测","dd"+txstr);
                }
                Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
                if (bitmap==null&&txstr==null){
                    nick_image.setImageResource(R.drawable.tx);
                    touxiang.setImageResource(R.drawable.tx);
                }else if(bitmap==null&&txstr!=null){
                    FileInputStream fs = null;
                    try {
                        Log.e("111", txstr);
                        fs = new FileInputStream("/sdcard/myHead/" + txstr);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    Bitmap bitmap1 = BitmapFactory.decodeStream(fs);
                    nick_image.setImageBitmap(bitmap1);
                    touxiang.setImageBitmap(bitmap1);
                }
                else {
                    nick_image.setImageBitmap(bitmap);
                    touxiang.setImageBitmap(bitmap);
                }
                }

        };
        Drawer.closeDrawer(GravityCompat.END);
        navigationView.setItemIconTintList(null);
        MainActivity.newInstance();
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                //为item设置逐个点击事件
               switch (menuItem.getItemId()){
                  case  R.id.daren:
                      Intent intent = new Intent(MainActivity.this, Daren.class);
                      startActivity(intent);
                      finish();
                      break;
                   case R.id.yinsi:
                       Intent intent1 = new Intent(MainActivity.this, Yinsi.class);
                        startActivity(intent1);
/*                       Toast.makeText(MainActivity.this,"暂时还没有被开发出来~",Toast.LENGTH_SHORT).show();*/
                       break;
                   case R.id.unlogin:
                       SharedPreferences preferences = getSharedPreferences("login", MODE_PRIVATE);
                       SharedPreferences.Editor editor = sharedPreferences.edit();
                       editor.clear();
                       preferences.edit().putBoolean("isFirstIn",true).commit();
                       editor.commit();
                       Intent intent3 = new Intent(MainActivity.this, Login.class);
                       startActivity(intent3);
                       finish();
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

        //设置图像大小时，必须先有第一句才可以进行设置
//        String value = ps.getString("headName","");
//        headPath=value+"head.jpg";
        /*getHeadFromSD(nick_image);
        nick_image.setAdjustViewBounds(true);
        nick_image.setMaxHeight(180);
        nick_image.setMaxWidth(180);*/

        /*点击头像跳转到上传头像界面*/
        nick_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(MainActivity.this,LookPicture.class);
                int frgTag = navigationController.getSelected();
//                int frgTag = fragmentTabHost.getCurrentTab();
                Log.i("tag",frgTag+"");
                intent.putExtra("userId",userId+"");
                startActivity(intent);
            }
        });
    }

    private void getData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    //192.168.137.1
                    URL url = new URL("http://175.24.16.26:8080/CarePet/user/getuser?id=" + userId);
                    URLConnection conn = url.openConnection();
                    InputStream in = conn.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in, "utf-8"));
                    String info = reader.readLine();
                    Log.e("user", "user" + info);
                    wrapperMessage(info);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void wrapperMessage(String info) {
        Message msg = Message.obtain();
        msg.obj = info;
        handler.sendMessage(msg);

    }

    private void getHeadFromSD(ImageView nick_image) {
        sharedPreferences = getSharedPreferences("headName", Context.MODE_PRIVATE);
        currentheadName = sharedPreferences.getString("name", "");
        if (currentheadName.isEmpty()) {
            nick_image.setImageResource(R.drawable.tx);
        } else {
            FileInputStream fs = null;
            try {
                Log.e("111", currentheadName);
                fs = new FileInputStream("/sdcard/myHead/" + currentheadName);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            Bitmap bitmap = BitmapFactory.decodeStream(fs);
            nick_image.setImageBitmap(bitmap);
            touxiang.setImageBitmap(bitmap);//新加上的

        }
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

//                                Toast.makeText(MainActivity.this, "你点击了第0个位置", Toast.LENGTH_SHORT).show();
                                break;
                            case 1:
                                Intent intent1=new Intent(MainActivity.this, AddExperience.class);
                                startActivity(intent1);
//                                Toast.makeText(MainActivity.this, "你点击了第1个位置", Toast.LENGTH_SHORT).show();
                                break;
                            case 2:
                                startActivity(new Intent(getApplicationContext(),NewFindTable.class));

//                                Toast.makeText(MainActivity.this, "你点击了第2个位置", Toast.LENGTH_SHORT).show();
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


    private void initView() {

        Drawer = findViewById(R.id.da);
        navigationView =findViewById(R.id.cebian);
        layout=findViewById(R.id.header_layout);
        touxiang=findViewById(R.id.touxiang);
        getHeadFromSD(touxiang);

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
