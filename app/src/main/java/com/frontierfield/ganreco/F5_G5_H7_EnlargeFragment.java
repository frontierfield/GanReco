package com.frontierfield.ganreco;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class F5_G5_H7_EnlargeFragment extends Fragment {
    LinearLayout backF5;
    //***フラグメントからアクティビティへのイベント
    public interface backListener {
        public void onClickButton();
    }

    private backListener mListener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        //contextclassがbackListenerを実装しているかチェック
        if (context instanceof backListener) {
            mListener = (backListener) context;
        }
    }
    //Fragmentがactivityから離れたら呼ばれる
    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
    //***

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.f5_fragment, container, false);
        Bitmap bitmap=null;
        try {
            bitmap=F6_G6_H8_Detail.bitmap;
            ((ImageView) view.findViewById(R.id.imageViewSinryoF5)).setImageBitmap(bitmap);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return view;
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        backF5 = view.findViewById(R.id.backF5);
        //ボタン押したときの処理
        backF5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mListener!=null){
                    mListener.onClickButton();
                }
            }
        });
    }
}
