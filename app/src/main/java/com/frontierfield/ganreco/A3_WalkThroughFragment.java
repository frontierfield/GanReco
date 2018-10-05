package com.frontierfield.ganreco;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class A3_WalkThroughFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup container, Bundle savedInstanceState){
        super.onCreateView(layoutInflater,container,savedInstanceState);
        return layoutInflater.inflate(R.layout.a3,container,false);
    }
    @Override
    public void onViewCreated(View view,@Nullable Bundle savedInstanceState) {
        view.findViewById(R.id.skipA3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), a4_registry_user.class));
            }
        });

    }
}
