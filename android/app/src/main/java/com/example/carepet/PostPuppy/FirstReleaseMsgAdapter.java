package com.example.carepet.PostPuppy;


import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.carepet.R;

import java.util.ArrayList;

public class FirstReleaseMsgAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Activity context;
    private ArrayList<String> mList;
    private final LayoutInflater inflater;
    private static final int ITEM_TYPE_ONE = 0x00001;
    private static final int ITEM_TYPE_TWO = 0x00002;
    /**
     *这里之所以用多行视图，因为我们默认的有一张图片的（那个带+的图片，用户点击它才会才会让你去选择图片）
     *集合url为空的时候，默认显示它，当它达到集合9时，这个图片会自动隐藏。
     */
    public FirstReleaseMsgAdapter(Activity context, ArrayList<String> mList) {
        this.context = context;
        this.mList = mList;
        inflater = LayoutInflater.from(context);
        CommonUtil.uploadPictures(context, 9, 0);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        parent.setPadding(20, 0, 20, 0);
        switch (viewType) {
            case ITEM_TYPE_ONE:
                return new MyHolder(inflater.inflate(R.layout.release_message_item, parent, false));
            case ITEM_TYPE_TWO:
                return new MyTWOHolder(inflater.inflate(R.layout.release_message_two_item, parent, false));
            default:
                return null;
        }

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof MyHolder) {
            bindItemMyHolder((MyHolder) holder, position);
        } else if (holder instanceof MyTWOHolder) {
            bindItemTWOMyHolder((MyTWOHolder) holder, position);
        }
    }

    private void bindItemTWOMyHolder(final MyTWOHolder holder, int position) {
        Log.e("Adapter", listSize() + "");
        if (listSize() >= 9) {//集合长度大于等于9张时，隐藏 图片
            holder.imageview2.setVisibility(View.GONE);
        }
        holder.imageview2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("按钮","first");
                //选择图片
                CommonUtil.uploadPictures(context, 9 - listSize(), 0);
            }
        });
    }

    private void bindItemMyHolder(MyHolder holder, int position) {
        Glide.with(context)
                .load(mList.get(position))
                .centerCrop()
                .into(holder.imageview);
    }

    @Override
    public int getItemViewType(int position) {
        if (position + 1 == getItemCount()) {
            return ITEM_TYPE_TWO;
        } else {
            return ITEM_TYPE_ONE;
        }
    }

    @Override
    public int getItemCount() {
        Log.e("getItemCount", mList.size() + 1 + "");
        return mList.size() + 1;
    }

    class MyHolder extends RecyclerView.ViewHolder {
        private final ImageView imageview;
        public MyHolder(View itemView) {
            super(itemView);
            imageview = (ImageView) itemView.findViewById(R.id.imageview);
        }
    }

    class MyTWOHolder extends RecyclerView.ViewHolder {
        private final ImageView imageview2;
        public MyTWOHolder(View itemView) {
            super(itemView);
            imageview2 = (ImageView) itemView.findViewById(R.id.imageview2);
        }
    }
    //对外暴露方法  。点击添加图片（类似于上啦加载数据）
    public void addMoreItem(ArrayList<String> loarMoreDatas) {
        mList.addAll(loarMoreDatas);
        notifyDataSetChanged();
    }
    //得到集合长度
    public int listSize() {
        int size = mList.size();
        return size;
    }
}
