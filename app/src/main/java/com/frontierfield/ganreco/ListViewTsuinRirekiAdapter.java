package com.frontierfield.ganreco;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

//通院履歴リスト表示用アダプター
class ListViewTsuinRirekiAdapter extends BaseAdapter {
    private static ListViewTsuinRirekiAdapter instance=new ListViewTsuinRirekiAdapter();
    public static ListViewTsuinRirekiAdapter getInstance(){
        return instance;
    }

    List<TsuinRireki> tsuinRireki= TsuinRirekiList.getInstance();
    LayoutInflater layoutInflater = null;
    public ListViewTsuinRirekiAdapter(){
    }
    public ListViewTsuinRirekiAdapter(List<TsuinRireki> tsuinRireki, Context context) {
        this.tsuinRireki = tsuinRireki;
        this.layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        return tsuinRireki.size();
    }

    @Override
    public Object getItem(int position) {
        return tsuinRireki.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Global_Util gu = new Global_Util();
        Integer year = gu.aYotei.get(tsuinRireki.get(position).y_index);
        Integer month = gu.aMonth[tsuinRireki.get(position).m_index];
        Integer day = gu.aDay[tsuinRireki.get(position).d_index];

        if(tsuinRireki.get(position).t == true) {
            convertView = layoutInflater.inflate(R.layout.list_header_efgh, parent, false);
            ((TextView) convertView.findViewById(R.id.textView15Listheader)).setText(
                    year.toString() + "年" +
                            month.toString()+"月"+day+"日"+tsuinRireki.get(position).getWeek());
        }else {
            convertView = layoutInflater.inflate(R.layout.listelement_f_h, parent, false);
            ((TextView) convertView.findViewById(R.id.shisetsuNameF_H)).setText(tsuinRireki.get(position).hospital);
            //((ImageView) convertView.findViewById(R.id.photoImageF_H)).setImage();
        }

        return convertView;
    }
}