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

//通院履歴リスト表示用アダプター
class TsuinRirekiListViewAdapter extends BaseAdapter {
    private static TsuinRirekiListViewAdapter instance=new TsuinRirekiListViewAdapter();
    public static TsuinRirekiListViewAdapter getInstance(){
        return instance;
    }

    List<TsuinRireki> tsuinRireki= TsuinRirekiList.getInstance();
    LayoutInflater layoutInflater = null;
    Context context=null;
    public TsuinRirekiListViewAdapter(){
    }
    public TsuinRirekiListViewAdapter(List<TsuinRireki> tsuinRireki, Context context) {
        this.tsuinRireki = tsuinRireki;
        this.layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.context=context;
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
        Global_Util globalUtil = new Global_Util();
        try {
            if (tsuinRireki.get(position).t == true) {
                convertView = layoutInflater.inflate(R.layout.list_header_efgh, parent, false);
                ((TextView) convertView.findViewById(R.id.textView15Listheader)).setText(
                        tsuinRireki.get(position).getYear() + "年" +
                                (tsuinRireki.get(position).getMonth()+1) + "月" + tsuinRireki.get(position).getDay() + "日" + tsuinRireki.get(position).getWeek());
            } else {
                convertView = layoutInflater.inflate(R.layout.listelement_f_h, parent, false);
                ((TextView) convertView.findViewById(R.id.shisetsuNameF_H)).setText(tsuinRireki.get(position).hospital);
                ImageView imageView = convertView.findViewById(R.id.photoImageF_H);
                ProgressBar progressBar=convertView.findViewById(R.id.progressBarF_H);
                if (tsuinRireki.get(position).getStoragePath() != null) {
                    PictureLoadTask pictureLoadTask = new PictureLoadTask(imageView,progressBar);
                    pictureLoadTask.execute(tsuinRireki.get(position).getStoragePath());
                } else {
                    bitmap = globalUtil.getPreResizedBitmap(Uri.parse(tsuinRireki.get(position).getFilePath()), context);
                    imageView.setImageBitmap(bitmap);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return convertView;
    }
}