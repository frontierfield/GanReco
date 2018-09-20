package com.frontierfield.ganreco;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
    List<TsuinYotei> listty=TsuinYoteiList.getInstance();
    List<TsuinRireki> viewTsuinRireki;
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
                //databaseに保存されてるデータを取ってくる
                ListViewTsuinYoteiAdapter listViewTsuinYoteiAdapter=ListViewTsuinYoteiAdapter.getInstance();//アダプターに通院予定送る処理
                listViewTsuinYoteiAdapter.layoutInflater=getLayoutInflater();//.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                listViewTsuinYoteiAdapter.tsuinYotei=TsuinYoteiList.getInstance();
                listView.setAdapter(listViewTsuinYoteiAdapter);
                break;
            case 1://通院履歴
                //***保存されてる通院履歴取得
                ListViewTsuinRirekiAdapter listViewTsuinRirekiAdapter=ListViewTsuinRirekiAdapter.getInstance();
                listViewTsuinRirekiAdapter.layoutInflater=getLayoutInflater();//.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                listViewTsuinRirekiAdapter.tsuinRireki=TsuinRirekiList.getInstance();//アダプターに通院予定送る
                listView.setAdapter(listViewTsuinRirekiAdapter);
                break;
            case 2://お薬履歴
                //***保存されてるお薬履歴取得
                ListViewSyohouRirekiAdapter listViewSyohouRirekiAdapter=ListViewSyohouRirekiAdapter.getInstance();//アダプターに通院予定送る
                listView.setAdapter(listViewSyohouRirekiAdapter);
                break;
            case 3:
                //***保存されてる検査履歴取得
                ListViewKensaRirekiAdapter listViewKensaRirekiAdapter=ListViewKensaRirekiAdapter.getInstance();//アダプターに通院予定送る
                listView.setAdapter(listViewKensaRirekiAdapter);
                break;
        }

        //アイテムタップ時の操作
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                switch(tab) {
                    case 0:
                        if (TsuinYoteiList.getInstance().get(position).t == false) {
                            Intent intent = new Intent(view.getContext(), E3_Input.class);
                            intent.putExtra("TsuinYoteiID", position);
                            startActivity(intent);
                        }
                        break;
                    case 1:
                        if (TsuinRirekiList.getInstance().get(position).t == false) {
                            Intent intent = new Intent(view.getContext(), F4_Input.class);
                            intent.putExtra("TsuinRirekiID", position);
                            startActivity(intent);
                        }
                        break;
                    case 2:
                        if (TsuinYoteiList.getInstance().get(position).t == false) {
                            Intent intent = new Intent(view.getContext(), g4_input.class);
                            intent.putExtra("OkusuriRirekiID", position);
                            startActivity(intent);
                        }
                        break;
                    case 3:
                        if (TsuinYoteiList.getInstance().get(position).t == false) {
                            Intent intent = new Intent(view.getContext(), h6_input.class);
                            intent.putExtra("KensaRirekiID", position);
                            startActivity(intent);
                        }
                        break;

                }
            }
        });


    }

}

