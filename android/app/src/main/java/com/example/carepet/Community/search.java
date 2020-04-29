package com.example.carepet.Community;

import android.os.Bundle;
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
import java.util.ArrayList;
import java.util.List;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置软键盘不要把页面顶上去
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setContentView(R.layout.search_layout);
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
                    /*mListView.setVisibility(View.GONE);*/
                }
            }
            @Override
            public void afterTextChanged(Editable editable) {
                ymtextview.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        //如果输入框内容为空，提示请输入搜索内容
                        if(TextUtils.isEmpty(ymeditext.getText().toString().trim())){
                            Toast.makeText(getApplicationContext(),"请输入你要搜索的内容",Toast.LENGTH_SHORT);
                            Log.e("dsd","请输入你要搜索的内容");
                        }else {
                            viewPager.setVisibility(View.VISIBLE);
                            Log.e("ss","对不起，没有你要搜索的内容");
                            String str=ymeditext.getText().toString().trim();
                            Log.e("str",str);
                            if(str.equals("搜索")){
                                show2();
                            }else{
                                show1();

                            }

                            //显示ListView
                            /*showListView();
                            //判断cursor是否为空
                            if (cursor != null) {
                                int columnCount = cursor.getCount();
                                if (columnCount == 0) {
                                    Toast.makeText(context,"对不起，没有你要搜索的内容",Toast.LENGTH_SHORT);
                                }
                            }*/
                        }
                    }


                });
            }
            private void show2() {
                if(i==0){
                    viewPager.setCurrentItem(3);
                    ym2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            viewPager.setCurrentItem(3);
                            ym2.setTextColor(getResources().getColor(R.color.rrr));
                            ym3.setTextColor(getResources().getColor(R.color.bbb));
                            ym4.setTextColor(getResources().getColor(R.color.bbb));
                        }
                    });
                }
                if(i==0){
                    ym3.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            viewPager.setCurrentItem(3);
                            ym2.setTextColor(getResources().getColor(R.color.bbb));
                            ym3.setTextColor(getResources().getColor(R.color.rrr));
                            ym4.setTextColor(getResources().getColor(R.color.bbb));
                        }
                    });
                }
                if(i==0){
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
            private void show1() {
                if(i==0){
                    viewPager.setCurrentItem(2);
                    ym2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            viewPager.setCurrentItem(2);
                            ym2.setTextColor(getResources().getColor(R.color.rrr));
                            ym3.setTextColor(getResources().getColor(R.color.bbb));
                            ym4.setTextColor(getResources().getColor(R.color.bbb));
                        }
                    });
                }
                if(i==0){
                    ym3.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            viewPager.setCurrentItem(2);
                            ym2.setTextColor(getResources().getColor(R.color.bbb));
                            ym3.setTextColor(getResources().getColor(R.color.rrr));
                            ym4.setTextColor(getResources().getColor(R.color.bbb));
                        }
                    });
                }
                if(i==0){
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
/*        list.add();*/
        list.add(LookPuppyFragment.newInstance());
        list.add(ExperienceFragment.newInstance());
        list.add(NotFoundActivity.newInstance());
        list.add(search_look.newInstance());
        /*
        这两个页面是搜索后展示的页面：只有数据不同，形式相同
        list.add(search_look.newInstance);
        list.add(search_experience.newInstance);*/

        /*list.add();*/
        viewPager.setAdapter(new FragmentAdapter(getSupportFragmentManager(),list));
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
        /*initBtnListener();*/
        switch (i){
            case 0:
                // button01.setBackgroundColor(Color.parseColor("#FCD9DD"));
                break;
            case 1:
                //button02.setBackgroundColor(Color.parseColor("#B4D1E1"));
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
