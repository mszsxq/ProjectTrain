package com.example.carepet.Community;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.carepet.R;
import com.example.carepet.entity.Community;

import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

public class search_look extends Fragment {

    public static search_look newInstance() {
        search_look fragment=new search_look();
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.recyclerview_layout ,container, false);
        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerview);
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(1,
                StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(staggeredGridLayoutManager);
        List<Community> list = new ArrayList<>();
        Community c1 = new Community();
        Community c2 = new Community();
        /******
         * 新增代码
         * ******/
        c1.setContent("ymyyyyyyyyyyyyyyyyyyymmmmmmmmmmmyn");
        c1.setUserId(1);
        c1.setFlag(0);
        c1.setTime("2018/10/20");
        c1.setPic(R.id.headpic);
        c1.setTitle("windows");
        list.add(c1);
        Search_listAdapter search_listAdapter=new Search_listAdapter(list,getContext()); //创建适配器，并且导入数据list
        recyclerView.setAdapter(search_listAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL,false));

        return rootView;
    }
}
