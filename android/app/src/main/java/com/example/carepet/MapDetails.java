package com.example.carepet;

import android.os.Bundle;
import android.util.Log;

import com.example.carepet.entity.FindTable;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class MapDetails extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle =getIntent().getExtras();
        FindTable findTable = (FindTable) bundle.getSerializable("findtable");
        Log.e("detaile",findTable.toString());
        setContentView(R.layout.map_details);
    }
}
