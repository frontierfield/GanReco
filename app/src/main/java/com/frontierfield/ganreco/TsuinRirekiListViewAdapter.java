package com.frontierfield.ganreco;


import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.util.List;

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
            StorageReference storageReference = FirebaseStorage.getInstance().getReference();
            FirebaseUser mAuthUser=FirebaseAuth.getInstance().getCurrentUser();
            StorageReference imagesRef= storageReference.child(mAuthUser.getUid()).child(String.format("TsuinRireki/rireki_%s.jpg",tsuinRireki.get(position).getFileName()));
            ImageView imageView=convertView.findViewById(R.id.photoImageF_H);
            Glide.with(context).using(new FirebaseImageLoader()).load(imagesRef).into(imageView);
            //Bitmap bitmap=gu.getBitmap(TsuinRirekiFirebaseStorage.getTsuinRirekiThum(tsuinRireki.get(position).getFileName()),context);
            //((ImageView) convertView.findViewById(R.id.photoImageF_H)).setImageBitmap(bitmap);
            //サムネをどこからどう持ってくるか、確認
        }

        return convertView;
    }
}