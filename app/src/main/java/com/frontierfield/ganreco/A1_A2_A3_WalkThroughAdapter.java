package com.frontierfield.ganreco;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import static com.frontierfield.ganreco.A1_A2_A3_WalkThrough.Page_Num;

public class A1_A2_A3_WalkThroughAdapter extends FragmentPagerAdapter {
    public A1_A2_A3_WalkThroughAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new A1_WalkThroughFragment();
            case 1:
                return new A2_WalkThroughFragment();
            case 2:
                return new A3_WalkThroughFragment();
        }
        return null;
    }

    @Override
    public int getCount() {
        return Page_Num;
    }
}
