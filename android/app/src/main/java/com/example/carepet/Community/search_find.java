package com.example.carepet.Community;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.etsy.android.grid.StaggeredGridView;
import com.example.carepet.MapFragment;
import com.example.carepet.R;
import com.example.carepet.adapter.SearchAdapter;
import com.example.carepet.entity.Community;
import com.example.carepet.entity.FindTable;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import static android.content.Context.MODE_PRIVATE;

public class search_find extends Fragment {
    private SharedPreferences p;
    private String sousuo;
    private Handler handler;
    private StaggeredGridView gridView;
    public static search_find newInstance() {
        search_find fragment=new search_find();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.map_layout, container, false);
        gridView = rootView.findViewById(R.id.gview);
        p=getContext().getSharedPreferences("SS",MODE_PRIVATE);
        getData();
        handler = new Handler() {
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                String object = (String) msg.obj;
                Gson gson = new Gson();
                List<FindTable> list = gson.fromJson(object, new TypeToken<List<FindTable>>() {}.getType());
                Log.e("findfind",list.toString());
                SearchAdapter adapter1= new SearchAdapter(getContext(), R.layout.map_search_item, list);
                adapter1.notifyDataSetChanged();
                //得到了数据，怎么进行展示？？？
/*                gridView.setAdapter(adapter1);*/
               /* ListAdapter listAdapter = new ListAdapter(list,getContext()); //创建适配器，并且导入数据list
                listAdapter.notifyDataSetChanged();
                recyclerView.setAdapter(listAdapter);//布局导入适配器
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));*/

            }

        };
        return rootView;
    }

    private void getData() {

        sousuo = p.getString("sousuo", null);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL("http://192.168.43.65:8080/CarePet/findtable/liststrf?sousuo=" + sousuo);
                    URLConnection conn = url.openConnection();
                    InputStream in = conn.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in, "utf-8"));
                    String info = reader.readLine();
                    Log.e("findtable", "df" + info);
                    wrapperMessage(info);
                } catch (MalformedURLException e) {
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
