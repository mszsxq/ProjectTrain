package com.example.carepet.Community;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.carepet.R;
import com.example.carepet.entity.Community;
import com.example.carepet.entity.Communitys;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import static android.content.Context.MODE_PRIVATE;

public class search_look extends Fragment {
    private SharedPreferences p;
    private SharedPreferences p1;
    private String sousuo;
    private Handler handler;

    public static search_look newInstance() {
        search_look fragment=new search_look();
        return fragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.recyclerview_layout ,container, false);
        final RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerview);
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(1,
                StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(staggeredGridLayoutManager);
        p=getContext().getSharedPreferences("SS",MODE_PRIVATE);
        getData();
        handler = new Handler() {
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                String object = (String) msg.obj;
                Gson gson = new Gson();
                List<Communitys> list = gson.fromJson(object, new TypeToken<List<Communitys>>() {}.getType());
                ListAdapter listAdapter = new ListAdapter(list,getContext()); //创建适配器，并且导入数据list

                listAdapter.notifyDataSetChanged();
                recyclerView.setAdapter(listAdapter);//布局导入适配器
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
            }

        };

        return rootView;
    }

    private void getData() {
        sousuo=p.getString("sousuo",null);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL("http://192.168.43.65:8080/CarePet/community/liststrp?sousuo="+sousuo);
                    URLConnection conn = url.openConnection();
                    InputStream in = conn.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in, "utf-8"));
                    String info = reader.readLine();
                    Log.e("wer", "df" + info);
                    wrapperMessage(info);
                }catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
    private void wrapperMessage(String info) {
        Message msg = Message.obtain();
        msg.obj = info;
        handler.sendMessage(msg);
    }
}
