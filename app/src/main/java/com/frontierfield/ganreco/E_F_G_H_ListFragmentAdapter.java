package com.frontierfield.ganreco;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

//ViewPager用のアダプター
//これで、タブレイアウトにcontent_e_f_g_hを追加
class E_F_G_H_ListFragmentAdapter extends FragmentPagerAdapter {
    private final Bundle fragmentBundle;
    private int tsuinRirekiID;
    private int detail;
    public E_F_G_H_ListFragmentAdapter(FragmentManager fm,Bundle bundle) {
        super(fm);
        fragmentBundle=bundle;
    }

    private String[] tabTitle = {"通院予定","通院履歴","お薬履歴","検査履歴"};

    @Override
    public CharSequence getPageTitle(int position){
        return tabTitle[position];
    }

    @Override
    public Fragment getItem(int position) {
        //case 0://通院予定//case 1://通院履歴//case 2://お薬履歴//case 3://検査履歴
        detail=fragmentBundle.getInt("detailKey",0);
        tsuinRirekiID=fragmentBundle.getInt("TsuinRirekiID",-1);

        if(detail==position&&detail!=0){
            F6_TsuinRirekiDetail f6TsuinRirekiDetail=new F6_TsuinRirekiDetail();
            f6TsuinRirekiDetail.setArguments(this.fragmentBundle);
            return f6TsuinRirekiDetail;
        }else {
            //bundleで表示してる場所をフラグメントに送る
            Bundle bundle = new Bundle();
            bundle.putInt("tab", position);
            E_F_G_H_ContentFragment fragment = new E_F_G_H_ContentFragment();
            fragment.setArguments(bundle);
            return fragment;
        }
    }
    @Override
    public int getCount() {
        return tabTitle.length;
    }
}




