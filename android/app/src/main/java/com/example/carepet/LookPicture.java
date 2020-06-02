package com.example.carepet;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Message;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.carepet.oss.OssService;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Timer;
import java.util.TimerTask;

public class LookPicture extends Activity {
    private PopupWindow mPopWindow;
    private ImageView back;
    private ImageView ivHead;//头像显示
    private Button btnTakephoto;//拍照
    private Bitmap head;//头像Bitmap
    private static String path="/sdcard/myHead/";//sd路径
    private Boolean aBoolean=false;
    private String laterheadName;
    private String currentheadName;
    private int userId;
    private ImageView touxiang;
    private SharedPreferences sharedPreferences;
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_look_picture);
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        builder.detectFileUriExposure();
        Intent intent=getIntent();
        String id=intent.getStringExtra("userId");
        laterheadName=id+System.currentTimeMillis();
        userId=(Integer.parseInt(intent.getStringExtra("userId")));
        init();
        getListener();
//        if (aBoolean){
//            Intent intent=new Intent(this,Main2Activity.class);
//            startActivity(intent);
//        }
    }
    private void getListener() {
        Listener listeners=new Listener();
        back.setOnClickListener(listeners);
        btnTakephoto.setOnClickListener(listeners);
//        btnPhotos.setOnClickListener(listeners);
    }

    private void init() {
        back = findViewById(R.id.btn1);
        ivHead=findViewById(R.id.img2);
        touxiang=findViewById(R.id.touxiang);
//        Bitmap bt = BitmapFactory.decodeFile(path + "head.jpg");//从Sd中找头像，转换成Bitmap
//        if(bt!=null){
//            @SuppressWarnings("deprecation")
//            Drawable drawable = new BitmapDrawable(bt);//转换成drawable
//            ivHead.setImageDrawable(drawable);
//        }else{
//            /**
//             * 如果SD里面没有则需要从服务器取头像，取回来的头像再保存在SD中
//             *
//             */
//        }
//        getBitmapFromSharedPreferences();

        sharedPreferences=getSharedPreferences("user",MODE_PRIVATE);
        String txname= sharedPreferences.getString("user_tx","");

        sharedPreferences=getSharedPreferences("headName", Context.MODE_PRIVATE);
        currentheadName=sharedPreferences.getString("name","");
        Log.e("qqqqq0000",currentheadName);

        if (txname.isEmpty()){
            ivHead.setImageResource(R.drawable.tx);
        }else{
            FileInputStream fs = null;
            try {
                Log.e("111",currentheadName);
                fs = new FileInputStream("/sdcard/myHead/"+txname);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            Bitmap bitmap  = BitmapFactory.decodeStream(fs);
            ivHead.setImageBitmap(bitmap);
        }


       /* if (currentheadName.isEmpty()){
            ivHead.setImageResource(R.drawable.tx);
        }else{
            FileInputStream fs = null;
            try {
                Log.e("111",currentheadName);
                fs = new FileInputStream("/sdcard/myHead/"+currentheadName);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            Bitmap bitmap  = BitmapFactory.decodeStream(fs);
            ivHead.setImageBitmap(bitmap);
        }*/



        btnTakephoto=findViewById(R.id.btnCamera);
    }
    public class Listener implements View.OnClickListener{

        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.btn1:
                    finish();
                    break;
                case R.id.btnCamera:
                    showPopupWindow();
                    break;
                case R.id.btnOne:
                    modiftHeadFromPhotograph();
                    mPopWindow.dismiss();
//                    if (aBoolean=true){
//                        Intent intent=new Intent(Main2Activity.this,MainActivity.class);
//                        startActivity(intent);
//                    }
                    break;
                case R.id.btnTwo:
                    modifyHeadFromAlbum();
                    mPopWindow.dismiss();
                    break;
                case R.id.btnThree:
                    mPopWindow.dismiss();
                    break;


            }
        }
    }

    private void showPopupWindow() {
        View contentView = LayoutInflater.from(LookPicture.this).inflate(R.layout.change_photo_window, null);
        mPopWindow = new PopupWindow(contentView,
                ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT, true);
        mPopWindow.setContentView(contentView);
        mPopWindow.setOutsideTouchable(true);
        mPopWindow.setFocusable(true);
        mPopWindow.setWidth(WindowManager.LayoutParams.MATCH_PARENT);
        int height = getWindowManager().getDefaultDisplay().getHeight();
        mPopWindow.setHeight(height * 1/4 );
        //设置各个控件的点击响应
        Button camera= (contentView).findViewById(R.id.btnOne);
        Button photo=(contentView).findViewById(R.id.btnTwo);
        Button finishes=(contentView).findViewById(R.id.btnThree);
        Listener listeners=new Listener();
        finishes.setOnClickListener(listeners);
        camera.setOnClickListener(listeners);
        photo.setOnClickListener(listeners);
        //显示PopupWindow
        View rootview = LayoutInflater.from(LookPicture.this).inflate(R.layout.change_photo_window, null);
        mPopWindow.showAtLocation(rootview, Gravity.BOTTOM, 0, 0);

    }

    private void modifyHeadFromAlbum() {
        Intent intent1 = new Intent(Intent.ACTION_PICK, null);
        intent1.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        startActivityForResult(intent1, 1);
    }

    private void modiftHeadFromPhotograph() {
        Intent intent2 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //头像文件名称 head.jpg
        intent2.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(Environment.getExternalStorageDirectory(),laterheadName+"head.jpg")));
        startActivityForResult(intent2, 2);//采用ForResult打开

    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 1:
                if (resultCode == RESULT_OK) {
                    cropPhoto(data.getData());//裁剪图片
                }
                break;
            case 2:
                if (resultCode == RESULT_OK) {
                    File temp = new File(Environment.getExternalStorageDirectory()
                            +"/"+laterheadName+"head.jpg");
                    cropPhoto(Uri.fromFile(temp));//裁剪图片
                }
                break;
            case 3:
                if (data != null) {
                    Bundle extras = data.getExtras();
                    head = extras.getParcelable("data");
                    if (head != null) {
                        /**
                         * 上传服务器代码
                         */
                        setPicToView(head);//保存在SD卡中
                        new Thread(new Runnable(){
                            @Override
                            public void run() {
                                new OssService(getApplicationContext()).uploadImage("","/sdcard/myHead/"+laterheadName+"head.jpg","");
                            }
                        }).start();
                        sendToServer();
                        saveHeadNameToSharedPreferences(laterheadName+"head.jpg");
//                        ivHead.setImageBitmap(head);//用ImageView显示出来
                        touxiang.setImageBitmap(head);
                    }
                }
                break;
            default:
                break;

        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void sendToServer() {
        new Thread() {
            @Override
            public void run() {
                try {
                    Gson gson = new Gson();
<<<<<<< HEAD

                    URL url = new URL("http://192.168.43.65:8080/CarePet/changephoto/change?headname="+laterheadName+"head.jpg"+"&userId="+userId);
=======
                    URL url = new URL("http://175.24.16.26:8080/CarePet/changephoto/change?headname="+laterheadName+"head.jpg"+"&userId="+userId);
>>>>>>> 64072d3a983f60b5a0328d1e53ba11c1f318b66c
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

    private void saveHeadNameToSharedPreferences(String headName) {
        SharedPreferences sharedPreferences = getSharedPreferences("headName", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Log.e("3333",headName);
        editor.putString("name", headName);
        editor.commit();
        currentheadName=sharedPreferences.getString("name","");
        Log.e("qqqqq0000",currentheadName);
        if (currentheadName.isEmpty()){
            ivHead.setImageResource(R.drawable.tx);
        }else{
            FileInputStream fs = null;
            try {
                Log.e("111",currentheadName);
                fs = new FileInputStream("/sdcard/myHead/"+currentheadName);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            Bitmap bitmap  = BitmapFactory.decodeStream(fs);
            ivHead.setImageBitmap(bitmap);
        }

        //上传头像
//        setImgByStr(imageString,"");
    }

        private void setPicToView(Bitmap head) {
        String sdStatus = Environment.getExternalStorageState();
        if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) { // 检测sd是否可用
            return;
        }
        FileOutputStream b = null;
        File file = new File(path);
        file.mkdirs();// 创建文件夹
            Log.e("222",laterheadName);
        String fileName = path +laterheadName+"head.jpg";//图片名字
        try {
            b = new FileOutputStream(fileName);
            head.compress(Bitmap.CompressFormat.JPEG, 100, b);// 把数据写入文件

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                //关闭流
                b.flush();
                b.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public void cropPhoto(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 150);
        intent.putExtra("outputY", 150);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, 3);
    }
}