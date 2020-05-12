package com.example.carepet.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import com.example.carepet.R;
import com.example.carepet.entity.FindTable;
import com.example.carepet.entity.MapContent;

import java.util.List;
import java.util.Map;

public class SearchAdapter extends BaseAdapter {
    private Context context;
    private int layoutid;
    private List<MapContent> data;
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
            holder.name=convertView.findViewById(R.id.ms_item_name);
            holder.touxiang=convertView.findViewById(R.id.ms_touxiang);
            convertView.setTag(holder);   //将Holder存储到convertView中
        }else{
            holder = (ViewHolder) convertView.getTag();
        }

        String name =data.get(i).getName();
        String touxiang =data.get(i).getTouxiang();
        String bimg =data.get(i).getBimg();
        holder.name.setText(name);
        holder.title.setText(data.get(i).getFindTable().getTitle());
        holder.city.setText(data.get(i).getFindTable().getCity());
        //设置图片圆角角度
        Glide.with(context).load(touxiang).fitCenter().into(holder.touxiang);
        Glide.with(context).load(bimg).fitCenter().into(holder.bimg);
        return convertView;
    }

    class ViewHolder{
        ImageView bimg;
        TextView city;
        TextView title;
        ImageView touxiang;
        TextView name;
    }
}
