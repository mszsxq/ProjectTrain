package com.example.carepet;

import android.os.Bundle;
import android.view.LayoutInflater;
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
    private SearchAdapter adapter;
    private int [] img ={R.drawable.shu,R.drawable.cat,R.drawable.cat,R.drawable.cat,R.drawable.shu,R.drawable.dancing,R.drawable.cat,R.drawable.cat2,R.drawable.fight};
    private String [] imgname ={"cat2","cat","figth"};
    private List<Map<String, Integer>> dataList = new ArrayList<Map<String, Integer>>();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.map_layout,container,false);
        gridView =view.findViewById(R.id.gview);
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
}