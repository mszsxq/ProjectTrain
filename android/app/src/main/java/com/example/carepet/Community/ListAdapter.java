package com.example.carepet.Community;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.example.carepet.R;
import com.example.carepet.entity.Community;
import com.example.carepet.oss.OssService;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {
    private final int MAX_LINE_COUNT = 3;
    private final int STATE_UNKNOW = -1;
    private final int STATE_NOT_OVERFLOW = 1;//文本行数不能超过限定行数
    private final int STATE_COLLAPSED = 2;//文本行数超过限定行数，进行折叠
    private final int STATE_EXPANDED = 3;//文本超过限定行数，被点击全文展开
    private SparseArray<Integer> mTextStateList;
    private Context context;
    private List<Community> mDataList;
    private ArrayList<String> imgList=new ArrayList<>();
    //private  int[] imageViewList={R.drawable.k1,R.drawable.k2};
    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView name;
        TextView content;
        TextView expandOrCollapse;
        ImageView imageAvatar;
        TextView nameText;
        TextView timeText;
        ViewPager viewPager;
        LinearLayout linearLayout;
        public ViewHolder(View itemView) {
            super(itemView);
            //注意这里可能需要import com.example.lenovo.myrecyclerview.R; 才能使用R.id
            imageAvatar = (ImageView)itemView.findViewById(R.id.headpic);
            nameText =(TextView) itemView.findViewById(R.id.user_name);
            timeText = (TextView)itemView.findViewById(R.id.release_time);
            viewPager = itemView.findViewById(R.id.vp_loop_advertisement);
            linearLayout = (LinearLayout) itemView.findViewById(R.id.dot_horizontal);
            name = (TextView) itemView.findViewById(R.id.title);
            content = (TextView) itemView.findViewById(R.id.content);
            expandOrCollapse = (TextView) itemView.findViewById(R.id.tv_expand_or_collapse);

        }

    }




    public  ListAdapter(List<Community> listDatas,Context context){
        this.context=context;
        mDataList = listDatas;
        mTextStateList = new SparseArray<>();
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_item,
                parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        imgList = new ArrayList<>();

        Community listData = mDataList.get(position);
        Log.e("数据",listData.toString());
        File file=new File(context.getFilesDir(),"oss"+listData.getPic());
        if(!file.exists()){
            OssService ossService = new OssService(context);
            ossService.downLoad("",listData.getPic());//listData.getPic()
            Log.e("检测","dd");
        }
        Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
        holder.imageAvatar.setImageBitmap(bitmap);
//        Glide.with(context)
//                .load("https://picturer.oss-cn-beijing.aliyuncs.com/OIP.jpg")//https://picturer.oss-cn-beijing.aliyuncs.com/1588149087234.jpg
//                .into(holder.imageAvatar);
        holder.nameText.setText(listData.getId()+"");
        holder.timeText.setText(listData.getTime());
        String imgjson=listData.getImgjson();
        //List<String> imgList = new ArrayList<>();
        List<String> list = new ArrayList<>();
        if (imgjson.contains("--")) {
            String[] s = imgjson.split("--");
            for(String ss:s){
                list.add(ss);
            }
        }else {
            list.add(imgjson);
        }
        for(int i=0;i<list.size();i++){
            File pics=new File(context.getFilesDir(),"oss"+list.get(i));
            Log.e("file",list.get(i)+"");
            if(!pics.exists()){
                OssService ossService = new OssService(context);
                ossService.downLoad("",list.get(i));
            }
            imgList.add(context.getFilesDir()+"/oss"+File.separator+list.get(i));//pics.getAbsolutePath()
        }

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(imgList, holder.viewPager,context);
        holder.viewPager.setAdapter(viewPagerAdapter);
        holder.viewPager.setOnPageChangeListener(new ViewPagerIndicator(context, holder.viewPager, holder.linearLayout, imgList.size()));
        holder.name.setText(listData.getTitle());//设置名称
        int state=mTextStateList.get(position,STATE_UNKNOW);
//        如果该itme是第一次初始化，则取获取文本的行数
        if (state==STATE_UNKNOW){
            holder.content.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                @Override
                public boolean onPreDraw() {
//                    这个回掉会调用多次，获取玩行数后记得注销监听
                    holder.content.getViewTreeObserver().removeOnPreDrawListener(this);
//                    holder.content.getViewTreeObserver().addOnPreDrawListener(null);
//                    如果内容显示的行数大于限定显示行数
                    if (holder.content.getLineCount()>MAX_LINE_COUNT) {
                        holder.content.setMaxLines(MAX_LINE_COUNT);//设置最大显示行数
                        holder.expandOrCollapse.setVisibility(View.VISIBLE);//让其显示全文的文本框状态为显示
                        holder.expandOrCollapse.setText("全文");//设置其文字为全文
                        mTextStateList.put(position, STATE_COLLAPSED);
                    }else{
                        holder.expandOrCollapse.setVisibility(View.GONE);//显示全文隐藏
                        mTextStateList.put(position,STATE_NOT_OVERFLOW);//让其不能超过限定的行数
                    }
                    return true;
                }
            });

            holder.content.setMaxLines(Integer.MAX_VALUE);//设置文本的最大行数，为整数的最大数值
            holder.content.setText(listData.getContent());//用Util中的getContent方法获取内容
        }else{
//            如果之前已经初始化过了，则使用保存的状态，无需在获取一次
            switch (state){
                case STATE_NOT_OVERFLOW:
                    holder.expandOrCollapse.setVisibility(View.GONE);
                    break;
                case STATE_COLLAPSED:
                    holder.content.setMaxLines(MAX_LINE_COUNT);
                    holder.expandOrCollapse.setVisibility(View.VISIBLE);
                    holder.expandOrCollapse.setText("全文");
                    break;
                case STATE_EXPANDED:
                    holder.content.setMaxLines(Integer.MAX_VALUE);
                    holder.expandOrCollapse.setVisibility(View.VISIBLE);
                    holder.expandOrCollapse.setText("收起");
                    break;
            }
            holder.content.setText(listData.getContent());
        }


//        设置显示和收起的点击事件
        holder.expandOrCollapse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int state=mTextStateList.get(position,STATE_UNKNOW);
                if (state==STATE_COLLAPSED){
                    holder.content.setMaxLines(Integer.MAX_VALUE);
                    holder.expandOrCollapse.setText("收起");
                    mTextStateList.put(position,STATE_EXPANDED);
                }else if (state==STATE_EXPANDED){
                    holder.content.setMaxLines(MAX_LINE_COUNT);
                    holder.expandOrCollapse.setText("全文");
                    mTextStateList.put(position,STATE_COLLAPSED);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }


    /**
     * 资源图片转Drawable
     * @param context
     * @param resId
     * @return
     */
    public Drawable fromResToDrawable(Context context, int resId) {
        return context.getResources().getDrawable(resId);
    }



}

