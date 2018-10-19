package com.frontierfield.ganreco;


import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

import static com.frontierfield.ganreco.F6_G6_H8_Detail.bitmap;

//検査履歴リスト表示用アダプター
class KensaRirekiListViewAdapter extends BaseAdapter {
    private static KensaRirekiListViewAdapter instance=new KensaRirekiListViewAdapter();
    public static KensaRirekiListViewAdapter getInstance(){
        return instance;
    }

    List<KensaRireki> kensaRireki=KensaRirekiList.getInstance();
    LayoutInflater layoutInflater = null;
    Context context=null;
    public KensaRirekiListViewAdapter(){
    }
    public KensaRirekiListViewAdapter(List<KensaRireki> kensaRireki, Context context) {
        this.kensaRireki = kensaRireki;
        this.layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.context=context;
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
        Global_Util globalUtil = new Global_Util();
        Integer year = globalUtil.aYotei.get(kensaRireki.get(position).y_index);
        Integer month = globalUtil.aMonth[kensaRireki.get(position).m_index];
        Integer day = globalUtil.aDay[kensaRireki.get(position).d_index];

        try {
            if (kensaRireki.get(position).t == true) {
                convertView = layoutInflater.inflate(R.layout.list_header_efgh, parent, false);
                ((TextView) convertView.findViewById(R.id.textView15Listheader)).setText(
                        year.toString() + "年" +
                                month.toString() + "月" + day + "日" + kensaRireki.get(position).getWeek());
            } else {
                convertView = layoutInflater.inflate(R.layout.listelement_f_h, parent, false);
                ((TextView) convertView.findViewById(R.id.shisetsuNameF_H)).setText(kensaRireki.get(position).hospital);
                ImageView imageView = convertView.findViewById(R.id.photoImageF_H);
                ProgressBar progressBar = convertView.findViewById(R.id.progressBarF_H);
                if (kensaRireki.get(position).getStoragePath() != null) {
                    PictureLoadTask pictureLoadTask = new PictureLoadTask(imageView, progressBar);
                    pictureLoadTask.execute(kensaRireki.get(position).getStoragePath());
                } else {
                    bitmap = globalUtil.getPreResizedBitmap(Uri.parse(kensaRireki.get(position).getFilePath()), context);
                    imageView.setImageBitmap(bitmap);
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return convertView;
    }
}