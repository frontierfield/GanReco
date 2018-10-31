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

public class E_F_G_H_ContentFragment extends Fragment {
    int tab=0;
    ListView listView;

    public E_F_G_H_ContentFragment(){
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
                TsuinYoteiListViewAdapter tsuinYoteiListViewAdapter = TsuinYoteiListViewAdapter.getInstance();
                tsuinYoteiListViewAdapter.layoutInflater=getLayoutInflater();//.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                listView.setAdapter(tsuinYoteiListViewAdapter);
                break;
            case 1://通院履歴
                TsuinRirekiListViewAdapter tsuinRirekiListViewAdapter = TsuinRirekiListViewAdapter.getInstance();
                tsuinRirekiListViewAdapter.layoutInflater=getLayoutInflater();
                tsuinRirekiListViewAdapter.context=getContext();
                listView.setAdapter(tsuinRirekiListViewAdapter);
                break;
            case 2://お薬履歴
                //***保存されてるお薬履歴取得
                OkusuriRirekiListViewAdapter okusuriRirekiListViewAdapter = OkusuriRirekiListViewAdapter.getInstance();
                okusuriRirekiListViewAdapter.layoutInflater=getLayoutInflater();
                okusuriRirekiListViewAdapter.context=getContext();
                listView.setAdapter(okusuriRirekiListViewAdapter);
                break;
            case 3:
                //***保存されてる検査履歴取得
                KensaRirekiListViewAdapter kensaRirekiListViewAdapter = KensaRirekiListViewAdapter.getInstance();
                kensaRirekiListViewAdapter.layoutInflater=getLayoutInflater();
                kensaRirekiListViewAdapter.context=getContext();
                listView.setAdapter(kensaRirekiListViewAdapter);
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
                            intent.putExtra("position", position);
                            startActivity(intent);
                        }
                        break;
                    case 1:
                        if (TsuinRirekiList.getInstance().get(position).t == false) {
                            Intent intent = new Intent(view.getContext(), e_f_g_h_mainmenu.class);
                            intent.putExtra("tab",tab);
                            intent.putExtra("detailKey",tab);
                            intent.putExtra("position", position);
                            startActivity(intent);
                        }
                        break;
                    case 2:
                        if (OkusuriRirekiList.getInstance().get(position).t == false) {
                            Intent intent = new Intent(view.getContext(), e_f_g_h_mainmenu.class);
                            intent.putExtra("tab",tab);
                            intent.putExtra("detailKey",tab);
                            intent.putExtra("position", position);
                            startActivity(intent);
                        }
                        break;
                    case 3:
                        if (KensaRirekiList.getInstance().get(position).t == false) {
                            Intent intent = new Intent(view.getContext(), e_f_g_h_mainmenu.class);
                            intent.putExtra("tab",tab);
                            intent.putExtra("detailKey",tab);
                            intent.putExtra("position", position);
                            startActivity(intent);
                        }
                        break;

                }
            }
        });


    }

}

