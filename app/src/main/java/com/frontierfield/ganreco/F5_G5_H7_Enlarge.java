package com.frontierfield.ganreco;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

public class F5_G5_H7_Enlarge extends AppCompatActivity implements  F5_G5_H7_EnlargeFragment.backListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.f5);

        Intent intent = getIntent();
        String uriString=intent.getStringExtra("cameraUri");

        Bundle bundle=new Bundle();
        bundle.putString("cameraUri",uriString);

        F5_G5_H7_EnlargeFragment f5_g5_h7_enlargeFragment=new F5_G5_H7_EnlargeFragment();
        f5_g5_h7_enlargeFragment.setArguments(bundle);
        FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.container,f5_g5_h7_enlargeFragment);
        fragmentTransaction.commit();
    }
    @Override
    public void onClickButton(){
        FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
        F5_G5_H7_EnlargeFragment f5EnlargeFragment=new F5_G5_H7_EnlargeFragment();
        fragmentTransaction.remove(f5EnlargeFragment);
        fragmentTransaction.commit();
        finish();
    }
}
