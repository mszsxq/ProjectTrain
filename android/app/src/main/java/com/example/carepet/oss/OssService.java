package com.example.carepet.oss;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import com.alibaba.sdk.android.oss.ClientConfiguration;
import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.OSS;
import com.alibaba.sdk.android.oss.OSSClient;
import com.alibaba.sdk.android.oss.ServiceException;
import com.alibaba.sdk.android.oss.callback.OSSCompletedCallback;
import com.alibaba.sdk.android.oss.callback.OSSProgressCallback;
import com.alibaba.sdk.android.oss.common.OSSLog;
import com.alibaba.sdk.android.oss.common.auth.OSSAuthCredentialsProvider;
import com.alibaba.sdk.android.oss.common.auth.OSSCredentialProvider;
import com.alibaba.sdk.android.oss.internal.OSSAsyncTask;
import com.alibaba.sdk.android.oss.model.GetObjectRequest;
import com.alibaba.sdk.android.oss.model.GetObjectResult;
import com.alibaba.sdk.android.oss.model.PutObjectRequest;
import com.alibaba.sdk.android.oss.model.PutObjectResult;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import androidx.annotation.Nullable;
import me.shouheng.compress.Compress;
import me.shouheng.compress.strategy.Strategies;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class OssService {
    OSS oss;
    Context context;
//    static {
//        String endpoint = "http://oss-cn-beijing.aliyuncs.com";
//        String stsServer = "http://192.168.2.229:7080/sts/getsts";
////推荐使用OSSAuthCredentialsProvider。token过期可以及时更新
//        OSSCredentialProvider credentialProvider = new OSSAuthCredentialsProvider(stsServer);
//
//        ClientConfiguration conf = new ClientConfiguration();
//        conf.setConnectionTimeout(15 * 1000); // 连接超时，默认15秒
//        conf.setSocketTimeout(15 * 1000); // socket超时，默认15秒
//        conf.setMaxConcurrentRequest(5); // 最大并发请求数，默认5个
//        conf.setMaxErrorRetry(2); // 失败后最大重试次数，默认2次
//        oss = new OSSClient(context, endpoint, credentialProvider,conf);
//    }
    public OssService(Context context){
        this.context=context;
        String endpoint = "http://oss-cn-beijing.aliyuncs.com";
        String stsServer = "http://175.24.16.26:7080/sts/getsts";
//推荐使用OSSAuthCredentialsProvider。token过期可以及时更新
        OSSCredentialProvider credentialProvider = new OSSAuthCredentialsProvider(stsServer);

        ClientConfiguration conf = new ClientConfiguration();
        conf.setConnectionTimeout(15 * 1000); // 连接超时，默认15秒
        conf.setSocketTimeout(15 * 1000); // socket超时，默认15秒
        conf.setMaxConcurrentRequest(5); // 最大并发请求数，默认5个
        conf.setMaxErrorRetry(2); // 失败后最大重试次数，默认2次
        oss = new OSSClient(context, endpoint, credentialProvider,conf);
    }
    //参数说明 ossfilepath表示上传到阿里的位置 filepath表示图片在本地的位置 httpurl表示上传成功之后打开的网页
    public void uploadImage(String ossFilePath,String filePath,@Nullable final String httpUrl){
        // 构造上传请求。
        File fileDir = new File(context.getFilesDir(), "image");
        if (!fileDir.exists()){
            fileDir.mkdirs();
        }
        PutObjectRequest put;
        File file=new File(filePath);
        int filenameLocal=filePath.split("/").length;
        String fileName=filePath.split("/")[filenameLocal-1];
        Compress compress = Compress.with(context,file);
        compress.setQuality(60)
                .setTargetDir(fileDir.getAbsolutePath());
        File resultfile= compress.strategy(Strategies.compressor())
                .get();
        if (ossFilePath.equals("")){
             put = new PutObjectRequest("picturer", fileName, resultfile.getAbsolutePath());
        }else {
             put = new PutObjectRequest("picturer", ossFilePath+"/"+fileName, resultfile.getAbsolutePath());
        }
        Log.e("开始","执行异步任务"+resultfile.getAbsolutePath());
// 异步上传时可以设置进度回调。
        put.setProgressCallback(new OSSProgressCallback<PutObjectRequest>() {
            @Override
            public void onProgress(PutObjectRequest request, long currentSize, long totalSize) {
                Log.d("PutObject", "currentSize: " + currentSize + " totalSize: " + totalSize);
            }
        });

        OSSAsyncTask task = oss.asyncPutObject(put, new OSSCompletedCallback<PutObjectRequest, PutObjectResult>() {
            @Override
            public void onSuccess(PutObjectRequest request, PutObjectResult result) {
                Log.d("PutObject", "UploadSuccess");
                Log.d("ETag", result.getETag());
                Log.d("RequestId", result.getRequestId());

                OkHttpClient client=new OkHttpClient.Builder()
                        .connectTimeout(15,TimeUnit.SECONDS)
                        .readTimeout(15, TimeUnit.SECONDS)
                        .writeTimeout(15, TimeUnit.MILLISECONDS)
                        .build();
                if (httpUrl!=null){
                    Request req = new Request.Builder()
                            .url(httpUrl)
                            .build();
                    Log.e("网络地址","    ");

                    client.newCall(req).enqueue(new Callback() {

                        @Override
                        public void onFailure(Call call, IOException e) {
                            Log.e("网络地址","    错误"+e.toString());

                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            Log.e("网络地址",response.toString());
                        }
                    });
                }

            }

            @Override
            public void onFailure(PutObjectRequest request, ClientException clientExcepion, ServiceException serviceException) {
                // 请求异常。
                if (clientExcepion != null) {
                    // 本地异常，如网络异常等。
                    clientExcepion.printStackTrace();
                }
                if (serviceException != null) {
                    // 服务异常。
                    Log.e("ErrorCode", serviceException.getErrorCode());
                    Log.e("RequestId", serviceException.getRequestId());
                    Log.e("HostId", serviceException.getHostId());
                    Log.e("RawMessage", serviceException.getRawMessage());
                }
            }
        });
// task.cancel(); // 可以取消任务。
// task.waitUntilFinished(); // 等待任务完成。
    }

    //参数说明 ossfilepath表示下载的图片在oss中的目录  downloadFileName要下载的图片的名称
    public void downLoad(final String ossFileDir,final String downloadFileName){
        //下载文件。
        GetObjectRequest get = new GetObjectRequest("picturer",downloadFileName);

        oss.asyncGetObject(get, new OSSCompletedCallback<GetObjectRequest, GetObjectResult>() {
            @Override
            public void onSuccess(GetObjectRequest request, GetObjectResult result) {
//开始读取数据。
                long length = result.getContentLength();
                byte[] buffer = new byte[(int) length];
                int readCount = 0;
                while (readCount < length) {
                    try{
                        readCount += result.getObjectContent().read(buffer, readCount, (int) length - readCount);
                    }catch (Exception e){
                        OSSLog.logInfo(e.toString());
                    }
                }
//将下载后的文件存放在指定的本地路径。
                try {
                    File fileDir = new File(context.getFilesDir(), "oss"+File.separator+ossFileDir);
                    if (!fileDir.exists()){
                        fileDir.mkdirs();
                    }
                    File file=new File(context.getFilesDir(), "oss"+File.separator+downloadFileName);
                    FileOutputStream fout = new FileOutputStream(file);
                    fout.write(buffer);
                    fout.close();
                    Log.e("图片位置",fileDir.getAbsolutePath());
                } catch (Exception e) {
                    OSSLog.logInfo(e.toString());
                }
            }

            @Override
            public void onFailure(GetObjectRequest request, ClientException clientException,
                                  ServiceException serviceException)  {

            }
        });
    }
//检测单机目录是否存在
    public boolean isFolderExists(String strFolder) {
        File file = new File(strFolder);

        if (!file.exists()) {
            if (file.mkdir()) {
                Log.e( "isFolderExists: ", "创建文件夹"+strFolder);
                return true;
            } else
                return false;
        }
        return true;
    }
//检测多级目录是否存在
    boolean isFolderMoreExists(String strFolder) {
        File file = new File(strFolder);
        if (!file.exists()) {
            if (file.mkdirs()) {
                return true;
            } else {
                return false;

            }
        }
        return true;
    }
    /* Checks if external storage is available for read and write */
    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

    /* Checks if external storage is available to at least read */
    public boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            return true;
        }
        return false;
    }

    public File imageCompression(File file){

        return new File("");
    }
}
