package com.example.carepet;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.bumptech.glide.Glide;
import com.etsy.android.grid.StaggeredGridView;
import com.example.carepet.adapter.SearchAdapter;
import com.example.carepet.entity.FindTable;
import com.example.carepet.entity.MapContent;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class MapFragment extends Fragment {
    private GestureDetector gue;
    private List<FindTable> findlist = new ArrayList<>();
    private  StaggeredGridView gridView;
    private SearchAdapter adapter;
    private List<MapContent> dataList = new ArrayList<>();
    private List<MapContent> list = new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.map_layout, container, false);
        gridView = view.findViewById(R.id.gview);
        gue = new GestureDetector(getContext(), new MyGestureListener());
        adapter = new SearchAdapter(getContext(), R.layout.map_search_item, getData());
        gridView.setAdapter(adapter);
        gridView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return gue.onTouchEvent(motionEvent);
            }
        });
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent  intent  =new Intent(getActivity(),MapDetails.class);
                Bundle bundle =new Bundle();
                bundle.putSerializable("findtable",list.get(i));
                Log.e("bundle",list.get(i).toString());
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        MyAsnycTask myAsnycTask = new MyAsnycTask();
        myAsnycTask.execute();

        return view;
    }
    private List<MapContent> getData(){
        FindTable findTable =new FindTable();
        findTable.setCity("石家庄");
        findTable.setTitle("title");
        MapContent mapContent = new MapContent();
        mapContent.setFindTable(findTable);
        dataList.add(mapContent);
        return  dataList;
    }
    //滑动显示显示
    class MyGestureListener extends GestureDetector.SimpleOnGestureListener {
        //onFling方法的第一个参数是 手指按下的位置， 第二个参数是 手指松开的位置，第三个参数是手指的速度
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            float startX = e1.getX();//通过e1.getX（）获得手指按下位置的横坐标
            float endX = e2.getX();//通过e2.getX（）获得手指松开位置的横坐标
            float startY = e1.getY();//通过e1.getY（）获得手指按下位置的纵坐标
            float endY = e2.getY();//通过e2.getY（）获得手指松开的纵坐标
            if ((endX - startX) > 50 && Math.abs(startY - endY) < 200) {
                Intent i = new Intent(getActivity(), MapFindAcitvity.class);
                startActivity(i);
            }
            return true;
        }
    }

    class MyAsnycTask extends AsyncTask<Integer, Integer, List<FindTable>> {
        //用来写等待时的UI（加载中转圈）
        @Override
        protected void onPreExecute() {

        }

        @Override
        protected List<FindTable> doInBackground(Integer... integers) {

//            final String ip = "http://192.168.101.16:8080/CarePet/findtable/listall";
            final String ip = "http://192.168.43.109:8080/CarePet/findtable/listall";
//            final String ip ="http://175.24.16.26:8080/CarePet/findtable/listall";
            URL url;
            String json = "";
            List<FindTable> findList = new ArrayList<>();
            try {
                url = new URL(ip);
                URLConnection conn = url.openConnection();
                InputStream in = conn.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
                json = reader.readLine();

            } catch (IOException e) {
                e.printStackTrace();
            }
            if (!json.equals("")) {
                Gson gson = new Gson();
                findList = gson.fromJson(json, new TypeToken<List<FindTable>>() {
                }.getType());
            }
            return findList;
        }

        @Override
        protected void onPostExecute(List<FindTable> findTables) {
            findlist=findTables;
            if (findlist != null) {
                for(FindTable f:findlist){
                    Log.e("aaaaa", f.toString());
                    String jason=f.getImgjson();
                    String name =jason.split("--")[0];
                    String touxiang =jason.split("--")[1];
                    String bimg =jason.split("--")[2];
                    MapContent mapContent= new MapContent();
                    mapContent.setFindTable(f);
                    mapContent.setName(name);
                    mapContent.setTouxiang(touxiang);
                    mapContent.setBimg(bimg);
                    list.add(mapContent);
                }
            }
            //findtable 数据 不更新问题
            SearchAdapter adapter1= new SearchAdapter(getContext(), R.layout.map_search_item, list);
            gridView.setAdapter(adapter1);

        }
    }

}