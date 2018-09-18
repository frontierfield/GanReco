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
//通院予定リスト表示用アダプター
class ListViewTsuinYotei extends BaseAdapter {
    private static ListViewTsuinYotei instance=new ListViewTsuinYotei();
    public static ListViewTsuinYotei getInstance(){
        return instance;
    }

    List<tsuin_yotei> ty=TsuinYoteiList.getInstance();
    LayoutInflater layoutInflater = null;
    public ListViewTsuinYotei(){
    }
    public ListViewTsuinYotei(Context context) {
        this.layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return ty.size();
    }

    @Override
    public Object getItem(int position) {
        return ty.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Global_Util gu = new Global_Util();
        Integer year = gu.aYotei.get(ty.get(position).y_index);
        Integer month = gu.aMonth[ty.get(position).m_index];
        Integer day = gu.aDay[ty.get(position).d_index];

        if(ty.get(position).t == true) {
            convertView = layoutInflater.inflate(R.layout.list_header_efgh, parent, false);
            ((TextView) convertView.findViewById(R.id.textView15Listheader)).setText(
                    year.toString() + "年" +
                            month.toString()+"月"+day.toString()+"日"+ty.get(position).getWeek());
        }else {
            String start_t = gu.aStartTime[ty.get(position).time];

            convertView = layoutInflater.inflate(R.layout.listelement_e, parent, false);
            ((TextView) convertView.findViewById(R.id.watchE)).setText(ty.get(position).emoji_watch);
            ((TextView) convertView.findViewById(R.id.timeE)).setText(start_t);
            ((TextView) convertView.findViewById(R.id.hospnameE)).setText(ty.get(position).hospital);
            ((TextView) convertView.findViewById(R.id.hospDetail)).setText(ty.get(position).s_detail);
        }
        return convertView;
    }
}

//通院履歴リスト表示用アダプター
class ListViewTsuinRireki extends BaseAdapter {
    private static ListViewTsuinRireki instance=new ListViewTsuinRireki();
    public static ListViewTsuinRireki getInstance(){
        return instance;
    }

    List<tsuin_rireki> ty=tsuin_rireki.getInstance();
    LayoutInflater layoutInflater = null;
    public ListViewTsuinRireki(){
    }
    public ListViewTsuinRireki(List<tsuin_rireki> ty, Context context) {
        this.ty = ty;
        this.layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        return ty.size();
    }

    @Override
    public Object getItem(int position) {
        return ty.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Global_Util gu = new Global_Util();
        Integer year = gu.aYotei.get(ty.get(position).y_index);
        Integer month = gu.aMonth[ty.get(position).m_index];
        Integer day = gu.aDay[ty.get(position).d_index];

        if(ty.get(position).t == true) {
            convertView = layoutInflater.inflate(R.layout.list_header_efgh, parent, false);
            ((TextView) convertView.findViewById(R.id.textView15Listheader)).setText(
                    year.toString() + "年" +
                            month.toString()+"月"+day+"日"+ty.get(position).getWeek());
        }else {
            convertView = layoutInflater.inflate(R.layout.listelement_f_h, parent, false);
            ((TextView) convertView.findViewById(R.id.shisetsuNameF_H)).setText(ty.get(position).hospital);
            //((ImageView) convertView.findViewById(R.id.photoImageF_H)).setImage();
        }

        return convertView;
    }
}

//お薬履歴リスト表示用アダプター
class ListViewOkusuriRireki extends BaseAdapter {
    private static ListViewOkusuriRireki instance=new ListViewOkusuriRireki();
    public static ListViewOkusuriRireki getInstance(){
        return instance;
    }

    List<syohou_rireki> ty=syohou_rireki.getInstance();
    LayoutInflater layoutInflater = null;
    public ListViewOkusuriRireki(){
    }
    public ListViewOkusuriRireki(List<syohou_rireki> ty, Context context) {
        this.ty = ty;
        this.layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return ty.size();
    }

    @Override
    public Object getItem(int position) {
        return ty.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Global_Util gu = new Global_Util();
        Integer year = gu.aYotei.get(ty.get(position).y_index);
        Integer month = gu.aMonth[ty.get(position).m_index];
        Integer day = gu.aDay[ty.get(position).d_index];

        if(ty.get(position).t == true) {
            convertView = layoutInflater.inflate(R.layout.list_header_efgh, parent, false);
            ((TextView) convertView.findViewById(R.id.textView15Listheader)).setText(
                    year.toString() + "年" +
                            month.toString()+"月"+day+"日"+ty.get(position).getWeek());
        }else {
            convertView = layoutInflater.inflate(R.layout.listelement_g, parent, false);
            ((TextView) convertView.findViewById(R.id.shisetsuNameG)).setText(ty.get(position).drugstore);
            ((TextView) convertView.findViewById(R.id.hospnameE)).setText(ty.get(position).s_detail);
            //((ImageView) convertView.findViewById(R.id.photoImageG)).setImage();
        }
        return convertView;
    }
}

//検査履歴リスト表示用アダプター
class ListViewKensaRireki extends BaseAdapter {
    private static ListViewKensaRireki instance=new ListViewKensaRireki();
    public static ListViewKensaRireki getInstance(){
        return instance;
    }

    List<kensa_rireki> ty=kensa_rireki.getInstance();
    LayoutInflater layoutInflater = null;
    public ListViewKensaRireki(){
    }
    public ListViewKensaRireki(List<kensa_rireki> ty, Context context) {
        this.ty = ty;
        this.layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return ty.size();
    }

    @Override
    public Object getItem(int position) {
        return ty.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Global_Util gu = new Global_Util();
        Integer year = gu.aYotei.get(ty.get(position).y_index);
        Integer month = gu.aMonth[ty.get(position).m_index];
        Integer day = gu.aDay[ty.get(position).d_index];

        if(ty.get(position).t == true) {
            convertView = layoutInflater.inflate(R.layout.list_header_efgh, parent, false);
            ((TextView) convertView.findViewById(R.id.textView15Listheader)).setText(
                    year.toString() + "年" +
                            month.toString()+"月"+day+"日"+ty.get(position).getWeek());
        }else {
            convertView = layoutInflater.inflate(R.layout.listelement_f_h, parent, false);
            ((TextView) convertView.findViewById(R.id.shisetsuNameF_H)).setText(ty.get(position).hospital);
            //((ImageView) convertView.findViewById(R.id.photoImageF_H)).setImage();
        }

        return convertView;
    }
}