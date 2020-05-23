package com.example.carepet;

import android.content.Intent;
import android.os.Bundle;
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

public class Register extends AppCompatActivity {




    private final String TAG = "--Register--";

    public String country = "86";

    private static final int CODE_REPEAT = 1;
    private TextView btn_login;

    private TextView btn_register;

    private EditText full_re;

    private TextView full;

    private ImageView eyes1;

    private EditText user_pwd1;

    private Button countdown;

    private Button register;

    private EditText et;

    private String phone;

    private String password;

    //默认密码输入框为隐藏的

    private boolean isHideFirst = true;

    private CustomOnclickListner listner;
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.resgister);

        getviews();

        registers();


    }


    private void getviews() {

        btn_login = findViewById(R.id.btn_login);

        btn_register = findViewById(R.id.btn_register);

        full_re = findViewById(R.id.full_re);

        full = findViewById(R.id.full);

        user_pwd1 = findViewById(R.id.user_pwd1);

        eyes1 = findViewById(R.id.eyes1);

        register = findViewById(R.id.re_btn_register);
        et = findViewById(R.id.re_et);

    }



    private void registers() {

        listner = new CustomOnclickListner();

        btn_login.setOnClickListener(listner);

        eyes1.setOnClickListener(listner);

        register.setOnClickListener(listner);

    }



    class CustomOnclickListner implements View.OnClickListener {

        @Override

        public void onClick(View v) {

            switch (v.getId()) {

                case R.id.btn_login:

                    Intent intent = new Intent();

                    intent.setClass(Register.this, Login.class);

                    startActivity(intent);

                    overridePendingTransition(R.anim.in, R.anim.out);

                    finish();
                    break;

                case R.id.eyes1:

                    if (isHideFirst == true) {

                        eyes1.setImageResource(R.drawable.openeye);

                        HideReturnsTransformationMethod method = HideReturnsTransformationMethod.getInstance();

                        user_pwd1.setTransformationMethod(method);

                        isHideFirst = false;

                    } else {

                        eyes1.setImageResource(R.drawable.yincangmima);

                        TransformationMethod method1 = PasswordTransformationMethod.getInstance();

                        user_pwd1.setTransformationMethod(method1);

                        isHideFirst = true;

                    }

                    //光标的位置

                    int index = user_pwd1.getText().toString().length();

                    user_pwd1.setSelection(index);

                    break;

                case R.id.re_btn_register:

                    phone = full_re.getText().toString();

                    password = user_pwd1.getText().toString();

                    String number = et.getText().toString();

                    if (number.equals("")){
                        Toast.makeText(Register.this,"验证码不能为空",Toast.LENGTH_SHORT).show();
                    }else {
                        RegisterUser(phone, password);
                        break;
                    }

            }

        }

    }



    private void RegisterUser(String name, String password) {

        User user = new User();

        user.setPassword(password);
        user.setUsername(name);

        Gson gson = new Gson();

        final String client = gson.toJson(user);

        Log.e("zcn", client);

        new Thread() {

            @Override

            public void run() {

                try {

                    URL url = new URL("http://192.168.43.81:8080/CarePet/user/insertuser?client=" + client);

                    URLConnection conn = url.openConnection();

                    InputStream in = conn.getInputStream();

                    BufferedReader reader = new BufferedReader(new InputStreamReader(in, "utf-8"));

                    String info = reader.readLine();

                    Log.e("ww", info);

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
}
