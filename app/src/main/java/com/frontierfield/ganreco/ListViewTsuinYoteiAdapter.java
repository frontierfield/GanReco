package com.frontierfield.ganreco;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

//通院予定リスト表示用アダプター
class ListViewTsuinYoteiAdapter extends BaseAdapter {
    private static ListViewTsuinYoteiAdapter instance=new ListViewTsuinYoteiAdapter();
    public static ListViewTsuinYoteiAdapter getInstance(){
        return instance;
    }

    List<tsuin_yotei> ty=TsuinYoteiList.getInstance();
    LayoutInflater layoutInflater = null;
    public ListViewTsuinYoteiAdapter(){
    }
    public ListViewTsuinYoteiAdapter(Context context) {
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