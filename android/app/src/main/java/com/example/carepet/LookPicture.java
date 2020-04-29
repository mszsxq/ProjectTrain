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

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
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
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_look_picture);
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        builder.detectFileUriExposure();
        init();
        getListener();
        Log.e("ss",aBoolean+"");
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
        FileInputStream fs = null;
        try {
            fs = new FileInputStream("/sdcard/myHead/head.jpg");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        Bitmap bitmap  = BitmapFactory.decodeStream(fs);
        ivHead.setImageBitmap(bitmap);
        btnTakephoto=findViewById(R.id.btnCamera);
    }

    private void getBitmapFromSharedPreferences() {
        SharedPreferences sharedPreferences=getSharedPreferences("testSP", Context.MODE_PRIVATE);
        //第一步:取出字符串形式的Bitmap
        String imageString=sharedPreferences.getString("image", "");
        //第二步:利用Base64将字符串转换为ByteArrayInputStream
        byte[] byteArray= Base64.decode(imageString, Base64.DEFAULT);
        if(byteArray.length==0){
            ivHead.setImageResource(R.drawable.tx);
        }else{
            ByteArrayInputStream byteArrayInputStream=new ByteArrayInputStream(byteArray);

            //第三步:利用ByteArrayInputStream生成Bitmap
            Bitmap bitmap= BitmapFactory.decodeStream(byteArrayInputStream);
            ivHead.setImageBitmap(bitmap);
        }

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
        intent2.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(Environment.getExternalStorageDirectory(),"head.jpg")));
        startActivityForResult(intent2, 2);//采用ForResult打开

    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 1:
                if (resultCode == RESULT_OK) {
                    cropPhoto(data.getData());//裁剪图片
                } aBoolean=true;
                break;
            case 2:
                if (resultCode == RESULT_OK) {
                    File temp = new File(Environment.getExternalStorageDirectory()
                            + "/head.jpg");
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
                                new OssService(getApplicationContext()).uploadImage("myHead/head.jpg","/sdcard/myHead/head.jpg","202.221.010");
                            }
                        }).start();
                        Log.e("sss","成功");
//                        saveBitmapToSharedPreferences(head);
                        ivHead.setImageBitmap(head);//用ImageView显示出来
                        aBoolean = true;
                    }
                }
                break;
            default:
                break;

        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void saveBitmapToSharedPreferences(Bitmap head) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        head.compress(Bitmap.CompressFormat.PNG, 80, byteArrayOutputStream);
        //第二步:利用Base64将字节数组输出流中的数据转换成字符串String
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        String imageString = new String(Base64.encodeToString(byteArray, Base64.DEFAULT));
        //第三步:将String保持至SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("testSP", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("image", imageString);
        editor.commit();

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
        String fileName = path + "head.jpg";//图片名字
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