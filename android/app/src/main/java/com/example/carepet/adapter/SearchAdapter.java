package com.example.carepet.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.example.carepet.R;

import java.util.List;
import java.util.Map;

public class SearchAdapter extends BaseAdapter {
    private Context context;
    private int layoutid;
    private List<Map<String,Integer>> data;
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
        ViewHolder holder = null;
        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(layoutid,null);
            holder = new ViewHolder();
            holder.bimg = (ImageView) convertView.findViewById(R.id.ms_item_iv);
            convertView.setTag(holder);   //将Holder存储到convertView中
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        holder.bimg.setImageResource(data.get(i).get("img"));
        return convertView;
    }

    class ViewHolder{
        ImageView bimg;
    }
}
