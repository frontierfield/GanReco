package com.frontierfield.ganreco;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

//お薬履歴リスト表示用アダプター
class OkusuriRirekiListViewAdapter extends BaseAdapter {
    private static OkusuriRirekiListViewAdapter instance=new OkusuriRirekiListViewAdapter();
    public static OkusuriRirekiListViewAdapter getInstance(){
        return instance;
    }

    List<OkusuriRireki> okusuriRireki=OkusuriRirekiList.getInstance();
    LayoutInflater layoutInflater = null;
    public OkusuriRirekiListViewAdapter(){
    }
    public OkusuriRirekiListViewAdapter(List<OkusuriRireki> ty, Context context) {
        this.okusuriRireki = ty;
        this.layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return okusuriRireki.size();
    }

    @Override
    public Object getItem(int position) {
        return okusuriRireki.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Global_Util gu = new Global_Util();
        Integer year = gu.aYotei.get(okusuriRireki.get(position).y_index);
        Integer month = gu.aMonth[okusuriRireki.get(position).m_index];
        Integer day = gu.aDay[okusuriRireki.get(position).d_index];

        if(okusuriRireki.get(position).t == true) {
            convertView = layoutInflater.inflate(R.layout.list_header_efgh, parent, false);
            ((TextView) convertView.findViewById(R.id.textView15Listheader)).setText(
                    year.toString() + "年" +
                            month.toString()+"月"+day+"日"+okusuriRireki.get(position).getWeek());
        }else {
            convertView = layoutInflater.inflate(R.layout.listelement_g, parent, false);
            ((TextView) convertView.findViewById(R.id.shisetsuNameG)).setText(okusuriRireki.get(position).drugstore);
            ((TextView) convertView.findViewById(R.id.textViewTouyakuG)).setText(okusuriRireki.get(position).s_detail);
            //((ImageView) convertView.findViewById(R.id.photoImageG)).setImage();
        }
        return convertView;
    }
}