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
import com.example.carepet.entity.Communitys;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

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

public class LookPuppyFragment extends Fragment {
    List<Communitys> newList = new ArrayList<Communitys>();
    List<Communitys> list=new ArrayList<>();
    private ListAdapter myAdapter;
    RecyclerView recyclerView;
    RefreshLayout refreshLayout;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            String object = (String) msg.obj;
            Gson gson = new Gson();
            List<Communitys> alist = gson.fromJson(object, new TypeToken<List<Communitys>>() {}.getType());
            newList=alist;
            list.addAll(list.size(),alist);
//            List<Community> list = new ArrayList<>();
//            Community community = new Community();
//            community.setId(1);
//            community.setTime("2020");
//            community.setPic("OIP");
//            community.setImgjson("OIP");
//            list.add(community);
            myAdapter = new ListAdapter(alist,getContext()); //创建适配器，并且导入数据list
            recyclerView.setAdapter(myAdapter);//布局导入适配器
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));

        }
    };
    private Handler handler2 = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            String object = (String) msg.obj;
            Gson gson = new Gson();
            List<Communitys> alist = gson.fromJson(object, new TypeToken<List<Communitys>>() {}.getType());
            list.addAll(list.size(),alist);
            newList=alist;
            //list.addAll(list.size(),alist);
//            List<Community> list = new ArrayList<>();
//            Community community = new Community();
//            community.setId(1);
//            community.setTime("2020");
//            community.setPic("OIP");
//            community.setImgjson("OIP");
//            list.add(community);
            myAdapter.add(alist);


        }
    };

    public static LookPuppyFragment newInstance() {
        LookPuppyFragment fragment = new LookPuppyFragment();
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.recyclerview_layout ,container, false);
        refreshLayout = (RefreshLayout)rootView.findViewById(R.id.refreshLayout);
        //刷新数据加载
        setPullRefresher();
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
                    //192.168.5.7
                    URL url = new URL("http://192.168.43.65:8080/CarePet/community/listall");
//                    URL url = new URL("http://175.24.16.26:8080/CarePet/community/listall");
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

    public void ToServer2(final int flag){
        new Thread() {
            @Override
            public void run() {
                try {
                    Gson gson = new Gson();
                    URL url = new URL("http://192.168.43.65:8080/CarePet/community/listsome?flag="+flag+"");
/*                    URL url = new URL("http://175.24.16.26:8080/CarePet/community/listsome?flag="+flag+"");*/
                    URLConnection conn = url.openConnection();
                    InputStream in = conn.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in, "utf-8"));
                    String info = reader.readLine();
                    Log.i("检测","得到"+info);
                    wrapperMessage2(info);
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
    private void wrapperMessage2(String num){
        Message msg = Message.obtain();
        msg.obj =num;
        handler2.sendMessage(msg);
    }

    private void setPullRefresher(){
        //设置 Header 为 MaterialHeader
        //设置 Header 为 ClassicsFooter 比较经典的样式
        refreshLayout.setRefreshHeader(new ClassicsHeader(getContext()));
        //设置 Footer 为 经典样式
        refreshLayout.setRefreshFooter(new ClassicsFooter(getContext()));

        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                //在这里执行上拉刷新时的具体操作(网络请求、更新UI等)

                //模拟网络请求到的数据
                //ArrayList<Community> newList = new ArrayList<Community>();

                myAdapter.refresh(list);
                refreshlayout.finishRefresh(2000/*,false*/);
                //不传时间则立即停止刷新    传入false表示刷新失败
            }
        });
        refreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {

                //模拟网络请求到的数据
                int num=myAdapter.getItemCount();
                ToServer2(num);

                //在这里执行下拉加载时的具体操作(网络请求、更新UI等)
                refreshlayout.finishLoadmore(2000/*,false*/);//不传时间则立即停止刷新    传入false表示加载失败
            }
        });
    }
}
