package com.frontierfield.ganreco;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

//ViewPager用のアダプター
//これで、タブレイアウトにcontent_e_f_g_hを追加
class HistoryFragmentAdapter extends FragmentPagerAdapter {
    public HistoryFragmentAdapter(FragmentManager fm) {
        super(fm);
    }

    private String[] tabTitle = {"通院予定","通院履歴","お薬履歴","検査履歴"};

    @Override
    public CharSequence getPageTitle(int position){
        return tabTitle[position];
    }

/*
    // 自分のコードから Fragment の初期化に使うメソッド
    public static HistoryFragmentAdapter newInstance(FragmentManager fm) {
        HistoryFragmentAdapter fragment = new HistoryFragmentAdapter(fm);
        return fragment;
    }
*/
    @Override
    public Fragment getItem(int position) {
        //case 0://通院予定//case 1://通院履歴//case 2://お薬履歴//case 3://検査履歴
        //bundleで表示してる場所をフラグメントに送る
        Bundle bundle=new Bundle();
        bundle.putInt("tab",position);
        ContentFragment fragment=new ContentFragment();
        fragment.setArguments(bundle);
        return fragment;
    }
    @Override
    public int getCount() {
        return tabTitle.length;
    }
}




