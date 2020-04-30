package com.example.carepet.Community;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.carepet.R;
import com.github.chrisbanes.photoview.PhotoView;

import java.util.ArrayList;


public class TaskBigImgActivity extends AppCompatActivity {
    ViewPager bigImgVp;
    private TextView tv_pos;
    private int position;
    private ArrayList<String> paths;
    private int size;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.clickpic_viewpager_layout);
        //ButterKnife.bind(this);
        bigImgVp=findViewById(R.id.viewpager);
        tv_pos=findViewById(R.id.tv_indicator);

        Intent intent = getIntent();
        position = intent.getIntExtra("position", 0);
        //paths = intent.getStringArrayListExtra("paths");
        size=intent.getIntExtra("size",0);
        String title = intent.getStringExtra("title");
        paths = (ArrayList<String>) intent.getSerializableExtra("paths");
        tv_pos.setText(position + 1 + "/" +size);
        initView();
    }

    private void initView() {
        bigImgVp.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return size;//paths == null ? 0 : paths.size()
            }

            @Override
            public boolean isViewFromObject(View view, Object o) {
                return view == o;
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                View adView = LayoutInflater.from(TaskBigImgActivity.this).inflate(R.layout.clickpic_layout, null);
                PhotoView icon = (PhotoView) adView.findViewById(R.id.flaw_img);
                icon.setBackgroundColor(getResources().getColor(R.color.design_default_color_background));
                Bitmap bitmap = BitmapFactory.decodeFile(paths.get(position));
                icon.setImageBitmap(bitmap);
//                Glide.with(TaskBigImgActivity.this)
//                        .load(paths.get(position))
//                        .into(icon);
                container.addView(adView);
                icon.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        finish();
                    }
                });
                return adView;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView((View) object);
            }
        });

        bigImgVp.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                tv_pos.setText(position + 1 + "/" + size);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }

        });

        bigImgVp.setCurrentItem(position, true);

    }

}
