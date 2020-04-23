package com.example.carepet;

import android.content.Intent;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.SimpleAdapter;

import com.etsy.android.grid.StaggeredGridView;
import com.example.carepet.adapter.SearchAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class MapFragment extends Fragment {
    private StaggeredGridView gridView ;
    private GestureDetector gue;
    private SearchAdapter adapter;
    private int [] img ={R.drawable.shu,R.drawable.cat,R.drawable.cat,R.drawable.cat,R.drawable.shu,R.drawable.dancing,R.drawable.cat,R.drawable.cat2,R.drawable.fight};
    private String [] imgname ={"cat2","cat","figth"};
    private List<Map<String, Integer>> dataList = new ArrayList<Map<String, Integer>>();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.map_layout,container,false);
        gridView =view.findViewById(R.id.gview);
        gue = new GestureDetector(getContext(), new MyGestureListener());
        adapter=new SearchAdapter(getContext(), R.layout.map_search_item,getData());
        gridView.setAdapter(adapter);
        return  view;
    }
    private List<Map<String, Integer>> getData() {
        for (int i=0; i<img.length; i++) {
            Map<String, Integer> map = new HashMap<String, Integer>();
            map.put("img", img[i]);
            dataList.add(map);
        }
        return dataList;
    }
    class MyGestureListener extends GestureDetector.SimpleOnGestureListener {
//onFling方法的第一个参数是 手指按下的位置， 第二个参数是 手指松开的位置，第三个参数是手指的速度

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            float startX = e1.getX();//通过e1.getX（）获得手指按下位置的横坐标
            float endX = e2.getX();//通过e2.getX（）获得手指松开位置的横坐标
            float startY = e1.getY();//通过e1.getY（）获得手指按下位置的纵坐标
            float endY = e2.getY();//通过e2.getY（）获得手指松开的纵坐标
            if ((startX - endX) > 50 && Math.abs(startY - endY) < 200) {
                //(startX - endX) > 50 是手指从按下到松开的横坐标距离大于50
                // Math.abs(startY - endY) < 200 是手指从按下到松开的纵坐标的差的绝对值

                //在这里通过Intent实现界面转跳
                Intent i  = new Intent(getActivity(),MapFindAcitvity.class);
                startActivity(i);
            }

            if ((endX - startX) > 50 && Math.abs(startY - endY) <200) {
                Intent i  = new Intent(getActivity(),MapFindAcitvity.class);
                startActivity(i);
            }
//返回值是重点：如果返回值是true则动作可以执行，如果是flase动作将无法执行
            return true;
        }
    }

}