package com.carepet.ai.animal;


import java.net.URL;
import java.net.URLEncoder;

import javax.imageio.ImageIO;

import org.apache.http.auth.AuthScheme;
import org.apache.tomcat.jni.File;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.carepet.ai.animal.utils.AuthService;
import com.carepet.ai.animal.utils.Base64Util;
import com.carepet.ai.animal.utils.FileUtil;
import com.carepet.ai.animal.utils.HttpUtil;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
* 动物识别
*/
public class AnimalClassify {

    /**
    * 重要提示代码中所需工具类
    * FileUtil,Base64Util,HttpUtil,GsonUtils请从
    * https://ai.baidu.com/file/658A35ABAB2D404FBF903F64D47C1F72
    * https://ai.baidu.com/file/C8D81F3301E24D2892968F09AE1AD6E2
    * https://ai.baidu.com/file/544D677F5D4E4F17B4122FBD60DB82B3
    * https://ai.baidu.com/file/470B3ACCA3FE43788B5A963BF0B625F3
    * 下载
    */
    public static String animal(String urlpath) {
        // 请求url
        String url = "https://aip.baidubce.com/rest/2.0/image-classify/v1/animal";
        try {
            // 本地文件路径
            String filePath = "E:\\";

            
            // 注意这里仅为了简化编码每一次请求都去获取access_token，线上环境access_token有过期时间， 客户端可自行缓存，过期后重新获取。
            String accessToken = AuthService.getAuth();
            String imageName=urlpath.split("/")[urlpath.split("/").length-1];
            System.out.println(imageName);
            String imageFormat=imageName.split("\\.")[1];
            System.out.println(imageFormat);
            java.io.File imageFile=new java.io.File(filePath+imageName);
            ImageIO.write(ImageIO.read(new URL(urlpath)),imageFormat, imageFile);
            byte[] imgData = FileUtil.readFileByBytes(imageFile.getAbsolutePath());
            String imgStr = Base64Util.encode(imgData);
            String imgParam = URLEncoder.encode(imgStr, "UTF-8");

            String param = "image=" + imgParam;
            String result = HttpUtil.post(url, accessToken, param);
            Gson gson=new Gson();
            JsonObject jsonObject= (JsonObject) new JsonParser().parse(result).getAsJsonObject();
            JsonArray result1=jsonObject.get("result").getAsJsonArray();
            System.out.println(result1.get(0).getAsJsonObject().get("name").getAsString());
            return result1.get(0).getAsJsonObject().get("name").getAsString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        AnimalClassify.animal("https://picturer.oss-cn-beijing.aliyuncs.com/OIP.jpg");
    }
}