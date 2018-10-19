package com.frontierfield.ganreco;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

public class F5_G5_H7_Enlarge extends AppCompatActivity implements  F5_G5_H7_EnlargeFragment.backListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.f5);

        F5_G5_H7_EnlargeFragment f5EnlargeFragment=new F5_G5_H7_EnlargeFragment();
        FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.container,f5EnlargeFragment);
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
