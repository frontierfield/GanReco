package com.frontierfield.ganreco;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

//お薬履歴リスト表示用アダプター
class ListViewSyohouRirekiAdapter extends BaseAdapter {
    private static ListViewSyohouRirekiAdapter instance=new ListViewSyohouRirekiAdapter();
    public static ListViewSyohouRirekiAdapter getInstance(){
        return instance;
    }

    List<syohou_rireki> ty=syohou_rireki.getInstance();
    LayoutInflater layoutInflater = null;
    public ListViewSyohouRirekiAdapter(){
    }
    public ListViewSyohouRirekiAdapter(List<syohou_rireki> ty, Context context) {
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