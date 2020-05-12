package com.example.carepet.Community;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;


import com.example.carepet.R;
import com.example.carepet.entity.Community;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

public class ExperienceFragment extends Fragment {
    RecyclerView recyclerView;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            String object = (String) msg.obj;
            Gson gson = new Gson();
            List<Community> list = gson.fromJson(object, new TypeToken<List<Community>>() {}.getType());
//            List<Community> list = new ArrayList<>();
//            Community community = new Community();
//            community.setId(1);
//            community.setTime("2020");
//            community.setPic("OIP");
//            community.setImgjson("OIP");
//            list.add(community);
            ExperienceListAdapter listAdapter = new  ExperienceListAdapter(list,getContext()); //创建适配器，并且导入数据list
            recyclerView.setAdapter(listAdapter);//布局导入适配器
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));

        }
    };

    public static  ExperienceFragment newInstance() {
        ExperienceFragment fragment = new  ExperienceFragment();
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.recyclerview_experience_layout ,container, false);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerview);
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(1,
                StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(staggeredGridLayoutManager);
//        List<Community> list = new ArrayList<>();
//        Community community = new Community();
//        community.setId(1);
//        community.setTime("2020");
//        community.setPic("OIP.jpg");
//        community.setImgjson("OIP.jpg");
//        community.setContent("今天很开心呀");
//        community.setTitle("晒猫日常");
//        list.add(community);
//        ListAdapter listAdapter = new ListAdapter(list,getContext()); //创建适配器，并且导入数据list
//        recyclerView.setAdapter(listAdapter);//布局导入适配器
//        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        ToServer();
        return rootView;
    }
    public void ToServer(){
        new Thread() {
            @Override
            public void run() {
                try {
                    Gson gson = new Gson();
                    URL url = new URL("http://192.168.43.65:8080/CarePet/community/listall");
                    URLConnection conn = url.openConnection();
                    InputStream in = conn.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in, "utf-8"));
                    String info = reader.readLine();
                    Log.i("检测","得到"+info);
                    wrapperMessage(info);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    private void wrapperMessage(String num){
        Message msg = Message.obtain();
        msg.obj =num;
        handler.sendMessage(msg);
    }
}
