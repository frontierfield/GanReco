package com.frontierfield.ganreco;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.BundleCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.List;

public class ContentFragment extends Fragment {
    int tab=0;
    ListView listView;
    List<tsuin_yotei> listty=tsuin_yotei.getInstance();
    List<tsuin_rireki> viewTsuinRireki;
    List<syohou_rireki> viewSyohouRireki;
    List<kensa_rireki> viewKensaRireki;

    public ContentFragment(){
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        Bundle bundle=getArguments();
        tab=bundle.getInt("tab");
        return inflater.inflate(R.layout.content_e_f_g_h_mainmenu,container,false);
    }

    @Override
    public void onViewCreated(View view,@Nullable Bundle savedInstanceState){
        super.onViewCreated(view,savedInstanceState);
        //ここに、content_e_f_g_hで実行してほしい処理を入力
        listView=(ListView) view.findViewById(R.id.ListDetailEFGH_1);
        switch (tab){
            case 0://通院予定
                ListViewTsuinYotei listViewTsuinYotei=ListViewTsuinYotei.getInstance();//アダプターに通院予定送る処理
                listViewTsuinYotei.layoutInflater=getLayoutInflater();//.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                listViewTsuinYotei.ty=tsuin_yotei.getInstance();
                listView.setAdapter(listViewTsuinYotei);
                break;
            case 1://通院履歴
                //***保存されてる通院履歴取得
                ListViewTsuinRireki listViewTsuinRireki=ListViewTsuinRireki.getInstance();//アダプターに通院予定送る
                listView.setAdapter(listViewTsuinRireki);
                break;
            case 2://お薬履歴
                //***保存されてるお薬履歴取得
                ListViewOkusuriRireki listViewOkusuriRireki=ListViewOkusuriRireki.getInstance();//アダプターに通院予定送る
                listView.setAdapter(listViewOkusuriRireki);
                break;
            case 3:
                //***保存されてる検査履歴取得
                ListViewKensaRireki listViewKensaRireki=ListViewKensaRireki.getInstance();//アダプターに通院予定送る
                listView.setAdapter(listViewKensaRireki);
                break;
        }

        //アイテムタップ時の操作
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                if(listty.get(position).t == false){
                    Intent intent = new Intent(view.getContext(), e3_yotei.class);
                    intent.putExtra("yotei_id",position);
                    startActivity(intent);
                }
            }
        });


    }

}

