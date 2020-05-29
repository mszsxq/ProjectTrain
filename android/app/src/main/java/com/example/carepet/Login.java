package com.example.carepet;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.text.method.TransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.carepet.entity.User;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import androidx.appcompat.app.AppCompatActivity;

public class Login extends AppCompatActivity {

    private TextView btn_register;

    private TextView btn_fpwd;

    private EditText user_number;

    private TextView full;

    private EditText user_pwd;

    private ImageView eyes;

    private Button btlog;

    private Handler handler;

    //默认密码输入框为隐藏的

    private boolean isHideFirst = true;

    private CustomOnclickListener listener;

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.login);

        final SharedPreferences p =getSharedPreferences("user",MODE_PRIVATE);

        getViews();

        registers();


        btlog = findViewById(R.id.btn_log);

        btlog.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View view) {

                sendMessage();

            }

        });

        //显示服务器返回的数据

        handler= new Handler() {

            @Override

            public void handleMessage(Message msg) {

                super.handleMessage(msg);

                String info = (String)msg.obj;

                Log.e("info",info);

                if("密码错误".equals(info)){

                    Toast.makeText(getApplicationContext(),info,Toast.LENGTH_SHORT).show();

                }else{

                    Gson gson=new Gson();

                    User usering = new User();

                    usering = gson.fromJson(info,User.class);

                    int id=usering.getId();

                    SharedPreferences.Editor editor=p.edit();

                    editor.putInt("user_id",id);

                    editor.commit();

                    int a = p.getInt("user_id",0);

                    Log.e("yz",a+"");

                    Intent intent = new Intent(Login.this, MainActivity.class);

                    intent.putExtra("name",usering.getUsername());

                    intent.putExtra("touxiang",usering.getTouxiang());

                    intent.putExtra("id",usering.getId());

                    startActivity(intent);

                }

            }

        };
    }



    private void registers() {

        listener=new CustomOnclickListener();

        btn_register.setOnClickListener(listener);

        btn_fpwd.setOnClickListener(listener);

        user_number.setOnClickListener(listener);

        eyes.setOnClickListener(listener);



    }



    private void getViews() {
        btn_register=findViewById(R.id.btn_register);

        btn_fpwd=findViewById(R.id.btn_fpwd);

        user_number=findViewById(R.id.user_number);

        full=findViewById(R.id.full);

        user_pwd=findViewById(R.id.user_pwd);

        eyes=findViewById(R.id.eyes);

    }

    class CustomOnclickListener implements View.OnClickListener{



        @Override

        public void onClick(View v) {

            switch (v.getId()){

                case R.id.btn_register:

                    Intent intent=new Intent();

                    intent.setClass(Login.this, Register.class);

                    startActivity(intent);
                    finish();

                    break;

                case R.id.btn_fpwd:

                    Intent intent1=new Intent();

                    intent1.setClass(Login.this, Forgetpwd.class);

                    startActivity(intent1);

                    break;

                case R.id.eyes://改变密码输入框是否可见

                    if(isHideFirst==true){

                        eyes.setImageResource(R.drawable.openeye);

                        HideReturnsTransformationMethod method=HideReturnsTransformationMethod.getInstance();

                        user_pwd.setTransformationMethod(method);

                        isHideFirst=false;

                    }else{

                        eyes.setImageResource(R.drawable.yincangmima);

                        TransformationMethod method1= PasswordTransformationMethod.getInstance();

                        user_pwd.setTransformationMethod(method1);

                        isHideFirst=true;

                    }

                    //光标的位置

                    int index=user_pwd.getText().toString().length();

                    user_pwd.setSelection(index);

                    break;

            }



        }

    }

    //向服务器发送数据

    private void sendMessage() {

        String num = user_number.getText().toString();

        String pwd = user_pwd.getText().toString();

        User user = new User();
        user.setUsername(num);
        user.setPassword(pwd);
        Gson gson = new Gson();

        final String client = gson.toJson(user);

        new Thread(){

            @Override

            public void run() {

                try {

                    URL url = new URL("http://175.24.16.26:8080/CarePet/user/ifuser?client="+client);

                    URLConnection conn = url.openConnection();

                    InputStream in = conn.getInputStream();

                    BufferedReader reader = new BufferedReader(new InputStreamReader(in, "utf-8"));

                    String info = reader.readLine();

                    if(null!=info) {

                        Log.e("ww", info);

                        wrapperMessage(info);

                    }

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

    private void wrapperMessage(String info){

        Message msg = Message.obtain();

        msg.obj = info;

        handler.sendMessage(msg);

    }
}
