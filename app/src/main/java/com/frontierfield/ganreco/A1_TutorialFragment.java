package com.frontierfield.ganreco;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class A1_TutorialFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup container, Bundle savedInstanceState){
        super.onCreateView(layoutInflater,container,savedInstanceState);
        return layoutInflater.inflate(R.layout.a1,container,false);
    }
}
