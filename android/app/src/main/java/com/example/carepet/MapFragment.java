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
import android.widget.GridView;
import android.widget.SimpleAdapter;

import com.etsy.android.grid.StaggeredGridView;
import com.example.carepet.adapter.SearchAdapter;
import com.example.carepet.entity.FindTable;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedInputStream;
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
import java.util.Map;

import javax.security.auth.login.LoginException;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class MapFragment extends Fragment {
    private GestureDetector gue;
    private int[] img = {R.drawable.shu, R.drawable.cat, R.drawable.cat, R.drawable.cat, R.drawable.shu, R.drawable.dancing, R.drawable.cat, R.drawable.cat2, R.drawable.fight};
    private List<FindTable> dataList = new ArrayList<>();
    private List<FindTable> findlist = new ArrayList<>();
    private  SearchAdapter adapter;
    private  StaggeredGridView gridView;
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
        MyAsnycTask myAsnycTask = new MyAsnycTask();
        myAsnycTask.execute();

        return view;
    }

    private List<FindTable> getData() {
        FindTable findTable =new FindTable();
        findTable.setCity("1");
        findTable.setContent("1");
        dataList.add(findTable);

        return dataList;
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

        
        @Override
        protected List<FindTable> doInBackground(Integer... integers) {
//            final String ip = "http://192.168.101.16:8080/CarePet/findtable/listall?a=1";
            final String ip = "http://192.168.43.109:8080/CarePet/findtable/listall?a=1";
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
                }
            }
            SearchAdapter adapter1= new SearchAdapter(getContext(), R.layout.map_search_item, findlist);
            gridView.setAdapter(adapter1);

        }
    }

}