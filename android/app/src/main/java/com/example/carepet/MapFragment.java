package com.example.carepet;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.SimpleAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class MapFragment extends Fragment {
    private GridView gridView;
    private SimpleAdapter simpleAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.map_layout,container,false);
        gridView =view.findViewById(R.id.gview);
//        simpleAdapter=new SimpleAdapter(this, getData(), R.layout.map_search_item,
//                new String[]{"img", "txt"}, new int[]{R.id.img_ite
//                m, R.id.txt_item});
        gridView.setAdapter(simpleAdapter);
        return  view;
    }
}