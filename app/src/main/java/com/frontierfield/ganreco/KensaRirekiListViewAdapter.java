package com.frontierfield.ganreco;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

//検査履歴リスト表示用アダプター
class KensaRirekiListViewAdapter extends BaseAdapter {
    private static KensaRirekiListViewAdapter instance=new KensaRirekiListViewAdapter();
    public static KensaRirekiListViewAdapter getInstance(){
        return instance;
    }

    List<KensaRireki> kensaRireki=KensaRirekiList.getInstance();
    LayoutInflater layoutInflater = null;
    public KensaRirekiListViewAdapter(){
    }
    public KensaRirekiListViewAdapter(List<KensaRireki> kensaRireki, Context context) {
        this.kensaRireki = kensaRireki;
        this.layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return kensaRireki.size();
    }

    @Override
    public Object getItem(int position) {
        return kensaRireki.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Global_Util gu = new Global_Util();
        Integer year = gu.aYotei.get(kensaRireki.get(position).y_index);
        Integer month = gu.aMonth[kensaRireki.get(position).m_index];
        Integer day = gu.aDay[kensaRireki.get(position).d_index];

        if(kensaRireki.get(position).t == true) {
            convertView = layoutInflater.inflate(R.layout.list_header_efgh, parent, false);
            ((TextView) convertView.findViewById(R.id.textView15Listheader)).setText(
                    year.toString() + "年" +
                            month.toString()+"月"+day+"日"+kensaRireki.get(position).getWeek());
        }else {
            convertView = layoutInflater.inflate(R.layout.listelement_f_h, parent, false);
            ((TextView) convertView.findViewById(R.id.shisetsuNameF_H)).setText(kensaRireki.get(position).hospital);
            //((ImageView) convertView.findViewById(R.id.photoImageF_H)).setImage();
        }

        return convertView;
    }
}