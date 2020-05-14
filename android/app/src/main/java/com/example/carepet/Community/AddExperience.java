package com.example.carepet.Community;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;

import com.example.carepet.MainActivity;
import com.example.carepet.R;
import com.example.carepet.entity.Community;
import com.example.carepet.oss.OssService;
import com.foamtrace.photopicker.SelectModel;
import com.foamtrace.photopicker.intent.PhotoPickerIntent;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static com.foamtrace.photopicker.PhotoPickerActivity.EXTRA_RESULT;

public class AddExperience extends AppCompatActivity {
    private EditText title;
    private EditText content;
    private GridView gridView;
    private Button finish;
    private Button cancle;
    private ArrayList<String> listImagePath = new ArrayList<>();
    private ArrayList<String> mList = new ArrayList<>();
    private List<Map<String, Object>> datas;
    private AddImageAdapter addImageAdapter;
    private final int PHOTO_REQUEST_CAREMA = 1;// 拍照
    private final int PHOTO_REQUEST_GALLERY = 2;// 从相册中选择private static final String PHOTO_FILE_NAME = "temp_photo.jpg";
    private File tempFile;
    private Bitmap bitmap;
    private final String IMAGE_DIR = Environment.getExternalStorageDirectory() + "/gridview";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_experience);
        inits();
        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (datas.size()>0){
                    for (int i=0;i<datas.size();i++){
                        final File file = new File(datas.get(i).get("path").toString());
                        file.delete();
                    }
                    finish();
                }else
                    finish();
            }
        });
        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StringBuffer stringBuffer = new StringBuffer();
                for(int i=0;i<listImagePath.size();i++){
                    int filenameLocal=listImagePath.get(i).split("/").length;
                    OssService ossService = new OssService(getApplicationContext());
                    String fileName=listImagePath.get(i).split("/")[filenameLocal-1];
                    ossService.uploadImage("",listImagePath.get(i),"");
                    Log.e("检测file",listImagePath.get(i));
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
//                Community community=new Community();
//                community.setId(2);
//                community.setContent(content.toString());
//                community.setImgjson(imgs);
//                community.setTime(time);
//                community.setTag("experience");
//                community.setTitle(title.toString());
//                community.setFlag(1);
                Community community = PackCommunity(content.getText().toString(),1,imgs,time,title.getText().toString());
                sendToServer(community);
                finish();
            }
        });

    }

    private Community PackCommunity(String toString, int i, String imgs, String time, String toString1) {
        Community community = new Community();
        community.setUserId(i);
        community.setContent(toString);
        community.setImgjson(imgs);
        community.setTime(time);
        community.setFlag(2);
        community.setTag("exeperience");
        community.setTitle(toString1);
        return community;
    }

    private void sendToServer(final Community community) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Gson gson = new Gson();
                    String jsonObject = gson.toJson(community);
                    URL url = new URL("http://192.168.43.65:8080/CarePet/experience/insertcommunity?community="+jsonObject+"");
                    URLConnection conn = url.openConnection();
                    InputStream in = conn.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(in, "utf-8"));
                String info = reader.readLine();
                Log.i("检测","得到"+info);
                } catch (
                        MalformedURLException e) {
                    e.printStackTrace();
                } catch (
                        UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (
                        IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
    private void inits() {
        title = findViewById(R.id.title);
        content = findViewById(R.id.content);
        gridView = findViewById(R.id.gridView);
        cancle = findViewById(R.id.btn_cancel);
        finish = findViewById(R.id.btn_finish);
        datas = new ArrayList<>();
        addImageAdapter = new AddImageAdapter(datas, this);
        gridView.setAdapter(addImageAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                gallery();
            }
        });
    }

    /**
     * 判断sdcard是否被挂载
     */
    public boolean hasSdcard() {
        return Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED);
    }


    /**
     * 从相册获取2
     */
    public void gallery() {
//        Intent intent = new Intent(
//                Intent.ACTION_PICK,
//                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        PhotoPickerIntent intent = new PhotoPickerIntent(AddExperience.this);
        intent.setSelectModel(SelectModel.MULTI);//多选
        intent.setShowCarema(true); // 是否显示拍照
        intent.setMaxTotal(9); // 最多选择照片数量，默认为9
        startActivityForResult(intent, PHOTO_REQUEST_GALLERY);
//        Intent intent1 = new Intent(Intent.ACTION_PICK, null);
//        intent1.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
//        startActivityForResult(intent1, PHOTO_REQUEST_GALLERY);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == PHOTO_REQUEST_GALLERY) {
                // 从相册返回的数据
                if (data != null) {
                    // 得到图片的全路径
                    for (int i = 0; i < data.getStringArrayListExtra(EXTRA_RESULT).size(); i++) {
                        String path = data.getStringArrayListExtra(EXTRA_RESULT).get(i);
                        uploadImage(path);
                    }
                }

            }
        }
    }
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0xAAAAAAAA) {
                photoPath(msg.obj.toString());
            }
        }
    };

    /**
     * 上传图片
     *
     * @param path
     */

    private void photoPath(String path) {
        Map<String, Object> map = new HashMap<>();
        map.put("path", path);
        datas.add(map);
        listImagePath.add(path);
        Log.e("title", title.getText().toString());
        Log.e("content", content.getText().toString());
        addImageAdapter.notifyDataSetChanged();
    }
    private void uploadImage(final String path) {
        new Thread() {
            @Override
            public void run() {
                if (new File(path).exists()) {
                    Log.d("images", "源文件存在" + path);
                } else {
                    Log.d("images", "源文件不存在" + path);
                }

                File dir = new File(IMAGE_DIR);
                if (!dir.exists()) {
                    dir.mkdir();
                }
                final File file = new File(dir + "/exeper_photo" + System.currentTimeMillis() + ".jpg");
                NativeUtil.compressBitmap(path, file);
                if (file.exists()) {
                    Log.d("images", "压缩后的文件存在" + file.getAbsolutePath());
                } else {
                    Log.d("images", "压缩后的不存在" + file.getAbsolutePath());
                }
                Message message = new Message();
                message.what = 0xAAAAAAAA;
                message.obj = file.getAbsolutePath();
                handler.sendMessage(message);

            }
        }.start();

    }
}
