package com.example.carepet.PostPuppy;

import android.app.Activity;
import android.content.Intent;

import com.foamtrace.photopicker.SelectModel;
import com.foamtrace.photopicker.intent.PhotoPickerIntent;

public class CommonUtil {
    /*
     * 选择图片
     * */
    public static Intent uploadPictures(Activity activity, int number, int requestCode){
        //加载图片
        PhotoPickerIntent intent = new PhotoPickerIntent(activity);
        intent.setSelectModel(SelectModel.MULTI);//多选
        intent.setShowCarema(true); // 是否显示拍照
        intent.setMaxTotal(number); // 最多选择照片数量，默认为9
//      intent.setSelectedPaths(imagePaths); // 已选中的照片地址， 用于回显选中状态
        intent.putExtra("type","photo");//选择方式；
        activity.startActivityForResult(intent,requestCode);
        return intent;
    }

}
