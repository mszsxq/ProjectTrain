package com.example.carepet.Community;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import java.util.ArrayList;
import java.util.List;

public class ViewPagerAdapter extends PagerAdapter {
    private Context context;
    private int[] images;
    private List<ImageView> imagelist=new ArrayList<>();
    private ViewPager viewPager;

    /**
     * 构造方法，传入图片列表和ViewPager实例
     *
     * @param images
     * @param viewPager
     */

    public ViewPagerAdapter(int[] images, ViewPager viewPager,Context context) {
        this.images = images;
        this.viewPager = viewPager;
        for(int i=0;i<images.length;i++){
            ImageView imageView = new ImageView(viewPager.getContext());
            imageView.setImageResource(images[i]);
            imagelist.add(imageView);
        }
        this.context=context;

    }

    @Override
    public int getCount() {
        return imagelist.size();//返回一个无限大的值，可以 无限循环
    }

    /**
     * 判断是否使用缓存, 如果返回的是true, 使用缓存. 不去调用instantiateItem方法创建一个新的对象
     */
    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    /**
     * 初始化一个条目
     *
     * @param container
     * @param position  当前需要加载条目的索引
     * @return
     */
    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        // 把position对应位置的ImageView添加到ViewPager中
        ImageView iv =imagelist.get(position);
        imagelist.get(position).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.putExtra("position",position);
                intent.putExtra("size",imagelist.size());
                intent.setClass(context,TaskBigImgActivity.class);
                context.startActivity(intent);
            }
        });
        viewPager.addView(iv);

// 把当前添加ImageView返回回去.
        return iv;
    }

    /**
     * 销毁一个条目
     * position 就是当前需要被销毁的条目的索引
     */
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        // 把ImageView从ViewPager中移除掉
        viewPager.removeView(imagelist.get(position));

    }
}
