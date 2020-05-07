package com.example.carepet.Community;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;

class NativeUtil {
    public static void compressBitmap(String filePath, File file){
        // 数值越高，图片像素越低
        int inSampleSize = 1;
        BitmapFactory.Options options = new BitmapFactory.Options();
        //采样率
        options.inSampleSize = inSampleSize;
        Bitmap bitmap = BitmapFactory.decodeFile(filePath, options);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        // 把压缩后的数据存放到baos中
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100 ,baos);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(baos.toByteArray());
            fos.flush();
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
