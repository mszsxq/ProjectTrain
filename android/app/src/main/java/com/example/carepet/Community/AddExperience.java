package com.example.carepet.Community;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
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
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.carepet.R;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddExperience extends AppCompatActivity {
    private EditText title;
    private EditText content;
    private GridView gridView;
    private Button finish;
    private Button cancle;
    private List<Map<String, Object>> datas;
    private AddImageAdapter addImageAdapter;
    private final int PHOTO_REQUEST_CAREMA = 1;// 拍照
    private final int PHOTO_REQUEST_GALLERY = 2;// 从相册中选择private static final String PHOTO_FILE_NAME = "temp_photo.jpg";
    private File tempFile;
    private Bitmap bitmap;
    private final String IMAGE_DIR = Environment.getExternalStorageDirectory() + "/gridview/";
    //    private final String PHOTO_FILE_NAME = "temp_photo.jpg";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_experience);
        inits();
        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(AddExperience.this,ExperienceFragment.class);
                intent.putExtra("title",title.getText());
                intent.putExtra("content",content.getText());
                startActivity(intent);
            }
        });

    }

    private void inits() {
        title = findViewById(R.id.title);
        content=findViewById(R.id.content);
        gridView=findViewById(R.id.gridView);
        cancle=findViewById(R.id.btn_cancel);
        finish=findViewById(R.id.btn_finish);
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
        Intent intent = new Intent(
                Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
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
                    Uri uri = data.getData();
                    String[] proj = {MediaStore.Images.Media.DATA};
                    //好像是android多媒体数据库的封装接口，具体的看Android文档
                    Cursor cursor = managedQuery(uri, proj, null, null, null);
                    //按我个人理解 这个是获得用户选择的图片的索引值
                    int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                    //将光标移至开头 ，这个很重要，不小心很容易引起越界
                    cursor.moveToFirst();
                    //最后根据索引值获取图片路径
                    String path = cursor.getString(column_index);


                    uploadImage(path);
                }

            } else if (requestCode == PHOTO_REQUEST_CAREMA) {
                if (resultCode != RESULT_CANCELED) {
                    // 从相机返回的数据
                    if (hasSdcard()) {
                        if (tempFile != null) {
                            uploadImage(tempFile.getPath());
                        } else {
                            Toast.makeText(this, "相机异常请稍后再试！", Toast.LENGTH_SHORT).show();
                        }

                        Log.i("images", "拿到照片path=" + tempFile.getPath());
                    } else {
                        Toast.makeText(this, "未找到存储卡，无法存储照片！", Toast.LENGTH_SHORT).show();
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

    public void photoPath(String path) {
        Map<String,Object> map=new HashMap<>();
        map.put("path",path);
        datas.add(map);
        for (int i=0;i<datas.size();i++){
            Log.e("path",(String) datas.get(i).get("path"));
        }
        Log.e("title",title.getText().toString());
        Log.e("content",content.getText().toString());
        addImageAdapter.notifyDataSetChanged();
    }
}
