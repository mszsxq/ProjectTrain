package com.example.carepet.Community;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.example.carepet.R;
import androidx.fragment.app.Fragment;

public class NotFoundActivity extends Fragment {

    public static NotFoundActivity newInstance() {
        NotFoundActivity fragment = new NotFoundActivity();
        return fragment;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.notfound_layout ,container, false);
        return rootView;
    }
}
