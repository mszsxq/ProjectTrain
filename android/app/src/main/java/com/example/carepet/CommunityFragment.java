
package com.example.carepet;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.carepet.Community.ExperienceFragment;
import com.example.carepet.Community.FragmentAdapter;
import com.example.carepet.Community.LookPuppyFragment;

import java.util.ArrayList;
import java.util.List;

public class CommunityFragment extends Fragment implements ViewPager.OnPageChangeListener,View.OnClickListener{
    private List<Fragment> list;
    private View view;
    private ViewPager viewPager;
    private Button button01,button02;
    // private TabLayout tableLayout;

    @Nullable
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.community_layout, container, false);
        initView();
        return view;
    }


    private void initView() {
        viewPager=(ViewPager)view.findViewById(R.id.tab_viewpager);

        list=new ArrayList<>();
        button01=(Button)view.findViewById(R.id.button_lookpuppy);
        button02=(Button)view.findViewById(R.id.button_experience);


        button01.setOnClickListener(this);
        button02.setOnClickListener(this);


        //这些界面要也要一个一个先去实现
        list.add(LookPuppyFragment.newInstance());
        list.add(ExperienceFragment.newInstance());


        viewPager.setAdapter(new FragmentAdapter(getFragmentManager(),list));
        //viewPager.setOnPageChangeListener(this);
        viewPager.setCurrentItem(0);

    }

    @Override
    public void onPageScrolled(int i, float v, int i1) {

    }

    @Override
    public void onPageSelected(int i) {
        initBtnListener();
        switch (i){
            case 0:
                // button01.setBackgroundColor(Color.parseColor("#FCD9DD"));
                break;
            case 1:
                //button02.setBackgroundColor(Color.parseColor("#B4D1E1"));
                break;
        }

    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }

    @Override
    public void onClick(View v) {
        initBtnListener();
        switch (v.getId()){
            case R.id.button_lookpuppy:
                // button01.setBackgroundColor(Color.parseColor("#FCD9DD"));
                viewPager.setCurrentItem(0);
                break;
            case R.id.button_experience:
                // button02.setBackgroundColor(Color.parseColor("#B4D1E1"));
                viewPager.setCurrentItem(1);
                break;
        }
    }

    private void initBtnListener(){

        button01.setBackgroundResource(R.color.design_default_color_background);
        button02.setBackgroundResource(R.color.design_default_color_background);
    }
}