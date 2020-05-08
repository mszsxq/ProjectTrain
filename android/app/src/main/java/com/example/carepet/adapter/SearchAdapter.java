package com.example.carepet.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import com.example.carepet.R;
import com.example.carepet.entity.FindTable;

import java.util.List;
import java.util.Map;

public class SearchAdapter extends BaseAdapter {
    private Context context;
    private int layoutid;
    private List<FindTable> data;
    public SearchAdapter(Context context ,int id,List list){
        this.context=context;
        this.layoutid=id;
        this.data=list;
    }
    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int i) {
        return data.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        ViewHolder holder;
        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(layoutid,null);
            holder = new ViewHolder();
            holder.bimg = (ImageView) convertView.findViewById(R.id.ms_item_iv);
            holder.city=(TextView) convertView.findViewById(R.id.ms_city);
            holder.title=(TextView) convertView.findViewById(R.id.ms_item_content);
            convertView.setTag(holder);   //将Holder存储到convertView中
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
         holder.title.setText(data.get(i).getTitle());
         holder.city.setText(data.get(i).getCity());
        //设置图片圆角角度
         Glide.with(context).load(data.get(i).getImgjson()).into(holder.bimg);
        return convertView;
    }

    class ViewHolder{
        ImageView bimg;
        TextView city;
        TextView title;
    }
}
