package com.example.carepet;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.carepet.Community.ContentUtil;

import androidx.appcompat.app.AppCompatActivity;

public class Yinsi extends AppCompatActivity {

    private ImageView btn_backy;
    private TextView ys;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.yinsi);

        btn_backy=findViewById(R.id.btn_backy);
        ys=findViewById(R.id.ys);
        btn_backy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Yinsi.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        ys.setText(ContentUtil.getContent(3));
        Log.e("content",ContentUtil.getContent(1));

    }
}
