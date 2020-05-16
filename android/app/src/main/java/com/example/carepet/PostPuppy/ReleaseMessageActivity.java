package com.example.carepet.PostPuppy;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.carepet.MainActivity;
import com.example.carepet.R;
import com.example.carepet.entity.Community;
import com.example.carepet.oss.OssService;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

import static com.foamtrace.photopicker.PhotoPickerActivity.EXTRA_RESULT;

public class ReleaseMessageActivity extends AppCompatActivity {
    private int flag;
    TextView textCancel;
    TextView textRelease;
    RecyclerView mRec;
    EditText etMesssage;
    EditText title;
    private ArrayList<String> listImagePath=new ArrayList<>();
    private ArrayList<String> mList = new ArrayList<>();
    private ReleaseMsgAdapter adapter;
    private FirstReleaseMsgAdapter firstAdapter;

    private ArrayList<String> list = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.post_puppy_layout);
        Intent get = getIntent();
        if(get.getIntExtra("flag",1)==0){
            flag=0;
        }else{
            flag=1;
        }
        textCancel=findViewById(R.id.text_cancel);
        textCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                Intent intent = new Intent();
                intent.setClass(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
        textRelease=findViewById(R.id.text_release);
        textRelease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StringBuffer stringBuffer = new StringBuffer();
                for(int i=0;i<listImagePath.size();i++){
                    OssService ossService = new OssService(getApplicationContext());
                    int filenameLocal=listImagePath.get(i).split("/").length;
                    String fileName=listImagePath.get(i).split("/")[filenameLocal-1];
                    ossService.uploadImage("",listImagePath.get(i),"");
                    Log.e("检测file",fileName+"-"+listImagePath.get(i));
                    if(i==0){
                        stringBuffer.append(fileName);
                    }else{
                        stringBuffer.append("--"+fileName);
                    }

                }
                String imgs = stringBuffer.toString();
                Log.e("Buffer",imgs);
                Log.e("图片",imgs);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd HH:mm:ss");// HH:mm:ss
                //获取当前时间
                Date date = new Date(System.currentTimeMillis());
                String time=simpleDateFormat.format(date);
                Community community = PackCommunity(etMesssage.getText().toString(),1,imgs,time,title.getText().toString());
                ToServer(community);
                finish();
                Intent intent = new Intent();
                intent.setClass(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
        mRec=findViewById(R.id.mRec);
        etMesssage=findViewById(R.id.et_message);
        title = findViewById(R.id.title);
        setRecyclerview();
    }

    private void setRecyclerview() {
        if (mList != null) {
            GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);
            mRec.setLayoutManager(gridLayoutManager);
            if(flag==0){
                firstAdapter = new FirstReleaseMsgAdapter(ReleaseMessageActivity.this, mList);
                mRec.setAdapter(firstAdapter);
            }else{
                Log.i("adapter","second");
                adapter = new ReleaseMsgAdapter(ReleaseMessageActivity.this, mList);
                mRec.setAdapter(adapter);
            }

        }

    }


    //用户选中图片后，拿到回掉结果
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0 && resultCode == RESULT_OK) {
            for(int i=0;i<data.getStringArrayListExtra(EXTRA_RESULT).size();i++){
                Log.i("图片",data.getStringArrayListExtra(EXTRA_RESULT).get(i));
                listImagePath.add(data.getStringArrayListExtra(EXTRA_RESULT).get(i));
            }
            //listImagePath=data.getStringArrayListExtra(EXTRA_RESULT);
            compress(data.getStringArrayListExtra(EXTRA_RESULT));
        }
    }
    //压缩 拿到返回选中图片的集合url，然后转换成file文件
    public void compress(ArrayList<String> list) {
        for (String imageUrl : list) {
            Log.e(">>>>>>", imageUrl);
            File file = new File(imageUrl);
            compressImage(file);
        }
        if(flag==0){
            firstAdapter.addMoreItem(list);
        }else{
            adapter.addMoreItem(list);
        }

    }
    //压缩
    private void compressImage(File file) {
        Luban.get(this)//用的第三方的压缩，开源库  Luban 大家可以自行百度
                .load(file)                     //传人要压缩的图片
                .putGear(Luban.THIRD_GEAR)      //设定压缩档次，默认三挡
                .setCompressListener(new OnCompressListener() { //设置回调

                    public void onStart() {
                        //TODO 压缩开始前调用，可以在方法内启动 loading UI
                    }

                    @Override
                    public void onSuccess(final File file) {
                        URI uri = file.toURI();
                        String[] split = uri.toString().split(":");
                        list.add(split[1]);//压缩后返回的文件，带file字样，所以需要截取
                       // Log.e(BaseApplication.TAG, uri + "????????????" + split[1]);
                    }


                    public void onError(Throwable e) {
                        //TODO 当压缩过去出现问题时调用
                    }
                }).launch();//启动压缩
    }
    public void ToServer(final Community community){
        new Thread() {
            @Override
            public void run() {
                try {
                    Gson gson = new Gson();
                    String jsonObject = gson.toJson(community);
                    URL url = new URL("http://192.168.137.1:8080/CarePet/community/insertcommunity?community="+jsonObject+"");
                    URLConnection conn = url.openConnection();
                    InputStream in = conn.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in, "utf-8"));
                    String info = reader.readLine();
                    Log.i("检测","得到"+info);
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
    public Community PackCommunity(String content,int id,String images,String time,String title){
        Community community = new Community();
        community.setUserId(id);
        community.setContent(content);
        community.setImgjson(images);
        community.setTime(time);
        community.setFlag(1);
        community.setTag("puppy");
        community.setTitle(title);
        return community;
    }
}

