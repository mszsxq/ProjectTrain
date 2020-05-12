package com.example.carepet.Community;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.example.carepet.R;
import com.example.carepet.entity.Community;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

public class search extends AppCompatActivity implements ViewPager.OnPageChangeListener,View.OnClickListener {
    private List<Fragment> list;
    private Button ym2,ym3,ym4;
    private ViewPager viewPager;
    private EditText ymeditext;
    private ImageView ymimageview;
    private TextView ymtextview;
    int i=0;
    private int puttynum;
    private String info;
    private String info2;
    private int experiencenum;
    private SharedPreferences p;
    private SharedPreferences pl;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            String object = (String) msg.obj;
            Gson gson = new Gson();
            List<Community> list = gson.fromJson(object, new TypeToken<List<Community>>() {}.getType());
            Log.e("list",list.toString());
        }
    };
    private Handler handler2 = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            String object = (String) msg.obj;
            Gson gson = new Gson();
            List<Community> list = gson.fromJson(object, new TypeToken<List<Community>>() {}.getType());
            Log.e("list2",list.toString());
        }

    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置软键盘不要把页面顶上去
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setContentView(R.layout.search_layout);
        p=getSharedPreferences("SS",MODE_PRIVATE);
        pl=getSharedPreferences("SEARCH",MODE_PRIVATE);
        //初始化
        initView();
        //点击取消按钮的时候
        ymimageview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //把EditText内容设置为空
                ymeditext.setText("");
                //显示
                viewPager.setVisibility(View.VISIBLE);
            }
        });
        //监听输入框文字字数改变的时候
        ymeditext.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                viewPager.setVisibility(View.GONE);
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() == 0) {
                    //隐藏“删除”图片
                    ymimageview.setVisibility(View.GONE);
                    viewPager.setVisibility(View.VISIBLE);
                } else {//长度不为0
                    //显示“删除图片”
                    ymimageview.setVisibility(View.VISIBLE);
                }
            }
            @Override
            public void afterTextChanged(Editable editable) {
                ymtextview.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        //如果输入框内容为空，提示请输入搜索内容
                        if(TextUtils.isEmpty(ymeditext.getText().toString().trim())){
                            Toast.makeText(getApplicationContext(),"请输入你要搜索的内容",Toast.LENGTH_SHORT).show();
                        }else {
                            viewPager.setVisibility(View.VISIBLE);
                            String str=ymeditext.getText().toString().trim();
                            Log.e("str",str);
                            SharedPreferences.Editor editor=p.edit();
                            editor.putString("sousuo",str);
                            editor.commit();
                            toServerPuppy(str);
                            toServerExperience(str);
                            list.remove(3);
                            list.add(3,search_look.newInstance());
                            list.remove(4);
                            list.add(4,search_experience.newInstance());
                            viewPager.setAdapter( new FragmentAdapter(getSupportFragmentManager(),list));
                            viewPager.setCurrentItem(3);
                            ym2.setTextColor(getResources().getColor(R.color.rrr));
                            ym3.setTextColor(getResources().getColor(R.color.bbb));
                            ym4.setTextColor(getResources().getColor(R.color.bbb));
                           ym2.setOnClickListener(new View.OnClickListener() {
                               @Override
                               public void onClick(View view) {
                                   viewPager.setCurrentItem(3);
                                   ym2.setTextColor(getResources().getColor(R.color.rrr));
                                   ym3.setTextColor(getResources().getColor(R.color.bbb));
                                   ym4.setTextColor(getResources().getColor(R.color.bbb));
                                    }
                           });
                           ym3.setOnClickListener(new View.OnClickListener() {
                               @Override
                               public void onClick(View view) {
                                   viewPager.setCurrentItem(4);
                                   ym2.setTextColor(getResources().getColor(R.color.bbb));
                                   ym3.setTextColor(getResources().getColor(R.color.rrr));
                                   ym4.setTextColor(getResources().getColor(R.color.bbb));
                               }
                           });
                           ym4.setOnClickListener(new View.OnClickListener() {
                               @Override
                               public void onClick(View view) {
                                   viewPager.setCurrentItem(2);
                                   ym2.setTextColor(getResources().getColor(R.color.bbb));
                                   ym3.setTextColor(getResources().getColor(R.color.bbb));
                                   ym4.setTextColor(getResources().getColor(R.color.rrr));
                               }
                           });
                        }
                    }
                });
            }
        });
    }
    private void toServerExperience(final String str) {
        new Thread(){
            @Override
            public void run() {
                try {
                    URL url = new URL(" http://192.168.43.65:8080/CarePet/community/liststre?sousuo="+str);
                    URLConnection conn = url.openConnection();
                    InputStream in = conn.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in, "utf-8"));
                    info = reader.readLine();
                    Log.e("num1",experiencenum+"");
                    Log.i("2222","sasa"+info);
                    wrapperMessage2(info);
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
    private void toServerPuppy(final String str) {
        new Thread(){
            @Override
            public void run() {
                try {
                    URL url = new URL(" http://192.168.43.65:8080/CarePet/community/liststrp?sousuo="+str);
                    URLConnection conn = url.openConnection();
                    InputStream in = conn.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in, "utf-8"));
                    info2 = reader.readLine();
                    Log.e("num2",puttynum+"");
                    Log.i("llll","sasa"+info);
                    wrapperMessage(info2);
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
        msg.obj =info;
        handler.sendMessage(msg);
    }
    private void wrapperMessage2(String info) {
        Message msg=Message.obtain();
        msg.obj=info;
        handler2.sendMessage(msg);
    }

    private void initView() {
        ym2=findViewById(R.id.ym2);
        ym3=findViewById(R.id.ym3);
        ym4=findViewById(R.id.ym4);
        ymeditext=findViewById(R.id.ymedittext);
        ymimageview=findViewById(R.id.ymimageview);
        ymtextview=findViewById(R.id.ymtextview);
        viewPager=(ViewPager)findViewById(R.id.ymtab_viewpager);
        list=new ArrayList<>();
        ym2.setOnClickListener(this);
        ym3.setOnClickListener(this);
        ym4.setOnClickListener(this);

        //实现四个页面：头像页，动态页：晒宠，经验页，寻找页
        list.add(LookPuppyFragment.newInstance());
        list.add(ExperienceFragment.newInstance());
        list.add(NotFoundActivity.newInstance());
        list.add(search_look.newInstance());
        list.add(search_experience.newInstance());
        /*
        这两个页面是搜索后展示的页面：只有数据不同，形式相同
        list.add(search_look.newInstance);
        list.add(search_experience.newInstance);*/

        /*list.add();*/
        viewPager.setAdapter(new FragmentAdapter(getSupportFragmentManager(),list));
        viewPager.setOnPageChangeListener(this);
        viewPager.setCurrentItem(0);
    }

    @Override
    public void onClick(View view) {
        initBtnListener();
        switch (view.getId()){
            case R.id.ym2:
                // button02.setBackgroundColor(Color.parseColor("#B4D1E1"));
                viewPager.setCurrentItem(0);
                ym2.setTextColor(getResources().getColor(R.color.rrr));
                ym3.setTextColor(getResources().getColor(R.color.bbb));
                ym4.setTextColor(getResources().getColor(R.color.bbb));
                break;
            case R.id.ym3:
                // button02.setBackgroundColor(Color.parseColor("#B4D1E1"));
                viewPager.setCurrentItem(1);
                ym2.setTextColor(getResources().getColor(R.color.bbb));
                ym3.setTextColor(getResources().getColor(R.color.rrr));
                ym4.setTextColor(getResources().getColor(R.color.bbb));
                break;
            case R.id.ym4:
                // button02.setBackgroundColor(Color.parseColor("#B4D1E1"));
                viewPager.setCurrentItem(2);
                ym2.setTextColor(getResources().getColor(R.color.bbb));
                ym3.setTextColor(getResources().getColor(R.color.bbb));
                ym4.setTextColor(getResources().getColor(R.color.rrr));
                break;
        }

    }

    @Override
    public void onPageScrolled(int i, float v, int i1) {


    }

    @Override
    public void onPageSelected(int i) {
        initBtnListener();
        switch (i){
            case 0:
                viewPager.setCurrentItem(3);
                // button01.setBackgroundColor(Color.parseColor("#FCD9DD"));
                break;
            case 1:
                viewPager.setCurrentItem(4);
                //button02.setBackgroundColor(Color.parseColor("#B4D1E1"));
                break;
            case 2:
                viewPager.setCurrentItem(2);
                break;
        }

    }

    private void initBtnListener() {
        ym2.setBackgroundResource(R.color.design_default_color_background);
    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }
}
