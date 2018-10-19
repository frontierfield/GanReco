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

//お薬履歴リスト表示用アダプター
class OkusuriRirekiListViewAdapter extends BaseAdapter {
    private static OkusuriRirekiListViewAdapter instance=new OkusuriRirekiListViewAdapter();
    public static OkusuriRirekiListViewAdapter getInstance(){
        return instance;
    }

    List<OkusuriRireki> okusuriRireki=OkusuriRirekiList.getInstance();
    LayoutInflater layoutInflater = null;
    Context context=null;
    public OkusuriRirekiListViewAdapter(){
    }
    public OkusuriRirekiListViewAdapter(List<OkusuriRireki> okusuriRireki, Context context) {
        this.okusuriRireki = okusuriRireki;
        this.layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.context=context;
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
        Global_Util globalUtil = new Global_Util();
        Integer year = globalUtil.aYotei.get(okusuriRireki.get(position).y_index);
        Integer month = globalUtil.aMonth[okusuriRireki.get(position).m_index];
        Integer day = globalUtil.aDay[okusuriRireki.get(position).d_index];
    try {
        if (okusuriRireki.get(position).t == true) {
            convertView = layoutInflater.inflate(R.layout.list_header_efgh, parent, false);
            ((TextView) convertView.findViewById(R.id.textView15Listheader)).setText(
                    year.toString() + "年" +
                            month.toString() + "月" + day + "日" + okusuriRireki.get(position).getWeek());
        } else {
            convertView = layoutInflater.inflate(R.layout.listelement_g, parent, false);
            ((TextView) convertView.findViewById(R.id.shisetsuNameG)).setText(okusuriRireki.get(position).getDrugstore());
            ((TextView) convertView.findViewById(R.id.textViewTouyakuG)).setText(okusuriRireki.get(position).getSDetail());
            ImageView imageView = convertView.findViewById(R.id.photoImageG);
            ProgressBar progressBar = convertView.findViewById(R.id.progressBarG);
            if (okusuriRireki.get(position).getStoragePath() != null) {
                PictureLoadTask pictureLoadTask = new PictureLoadTask(imageView, progressBar);
                pictureLoadTask.execute(okusuriRireki.get(position).getStoragePath());
            } else {
                bitmap = globalUtil.getPreResizedBitmap(Uri.parse(okusuriRireki.get(position).getFilePath()), context);
                imageView.setImageBitmap(bitmap);
            }
        }
    }catch(Exception e){
    e.printStackTrace();
    }
        return convertView;
    }
}