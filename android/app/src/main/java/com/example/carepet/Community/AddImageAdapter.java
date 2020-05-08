package com.example.carepet.Community;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.example.carepet.R;

import java.io.File;
import java.util.List;
import java.util.Map;

class AddImageAdapter extends BaseAdapter {
    private List<Map<String, Object>> datas;
    private Context context;
    private LayoutInflater inflater;
    private int maxImages = 9;

    public AddImageAdapter(List<Map<String, Object>> datas, Context context) {
        this.datas = datas;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }
    public int getMaxImages() {
        return maxImages;
    }
    public void setMaxImages(int maxImages) {
        this.maxImages = maxImages;
    }

    public int getCount() {
        int count = datas == null ? 1 : datas.size() + 1;
        if (count >= maxImages) {
            return maxImages;
        } else {
            return count;
        }
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.experience_item_layout, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        /**代表+号之前的需要正常显示图片**/
        if (datas != null && position < datas.size()) {
            final File file = new File(datas.get(position).get("path").toString());
            Glide.with(context)
                    .load(file)
                    .priority(Priority.HIGH)
                    .into(viewHolder.ivimage);
            viewHolder.btdel.setVisibility(View.VISIBLE);
            viewHolder.btdel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (file.exists()) {
                        file.delete();
                    }
                    datas.remove(position);
                    notifyDataSetChanged();
                }
            });
        } else {
            /**代表+号的需要+号图片显示图片**/
            Glide.with(context)
                    .load(R.drawable.plus1)
                    .priority(Priority.HIGH)
                    .centerCrop()
                    .into(viewHolder.ivimage);
            viewHolder.ivimage.setScaleType(ImageView.ScaleType.FIT_XY);
            viewHolder.btdel.setVisibility(View.GONE);
        }

        return convertView;
    }
    public class ViewHolder {
        public final ImageView ivimage;
        public final Button btdel;
        public final View root;

        public ViewHolder(View root) {
            ivimage = (ImageView) root.findViewById(R.id.iv_image);
            btdel = (Button) root.findViewById(R.id.bt_del);
            this.root = root;
        }
    }
}
