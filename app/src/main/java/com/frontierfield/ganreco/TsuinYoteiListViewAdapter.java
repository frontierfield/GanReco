package com.frontierfield.ganreco;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

//通院予定リスト表示用アダプター
class TsuinYoteiListViewAdapter extends BaseAdapter {
    private static TsuinYoteiListViewAdapter instance=new TsuinYoteiListViewAdapter();
    public static TsuinYoteiListViewAdapter getInstance(){
        return instance;
    }

    List<TsuinYotei> tsuinYotei=TsuinYoteiList.getInstance();
    LayoutInflater layoutInflater = null;
    public TsuinYoteiListViewAdapter(){
    }
    public TsuinYoteiListViewAdapter(Context context) {
        this.layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return tsuinYotei.size();
    }

    @Override
    public Object getItem(int position) {
        return tsuinYotei.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Global_Util gu = new Global_Util();

        if(tsuinYotei.get(position).t == true) {
            convertView = layoutInflater.inflate(R.layout.list_header_efgh, parent, false);
            ((TextView) convertView.findViewById(R.id.textView15Listheader)).setText(
                    tsuinYotei.get(position).getYear() + "年" +
                            (tsuinYotei.get(position).getMonth()+1) + "月" + tsuinYotei.get(position).getDay() + "日" + tsuinYotei.get(position).getWeek());
        }else {
            String start_t = gu.aStartTime[tsuinYotei.get(position).time];

            convertView = layoutInflater.inflate(R.layout.listelement_e, parent, false);
            ((TextView) convertView.findViewById(R.id.watchE)).setText(tsuinYotei.get(position).emoji_watch);
            ((TextView) convertView.findViewById(R.id.timeE)).setText(start_t);
            ((TextView) convertView.findViewById(R.id.hospnameE)).setText(tsuinYotei.get(position).hospital);
            ((TextView) convertView.findViewById(R.id.hospDetail)).setText(tsuinYotei.get(position).s_detail);
        }
        return convertView;
    }
}