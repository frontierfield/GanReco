package com.frontierfield.ganreco;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

@SuppressLint("ValidFragment")
class HistoryOpeFuncClass extends android.support.v4.app.Fragment implements AdapterView.OnItemClickListener {
    //通院予定
    //private String[] tabTitle = {"通院予定","通院履歴","お薬履歴","検査履歴"};
    private int position;
    List<tsuin_yotei> view_tsuin_yoteis;
    ListViewTsuinYotei adapter;
    ListView listdetail;
    int current_view_ty_size = 0;//up内の通院予定数が変わっていないかどうか確認するための変数

    // メモリ不足による破棄後からの復帰時にフレームワークがリフレクションで呼びだすコンストラクタ
    public HistoryOpeFuncClass(int i)
    {
        position = i;
    }

    // 自分のコードから Fragment の初期化に使うメソッド
    public static HistoryOpeFuncClass newInstance(int position) {
        HistoryOpeFuncClass fragment = new HistoryOpeFuncClass(position);
        Bundle arguments = new Bundle();
        arguments.putInt("Position", position);
        fragment.setArguments(arguments);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.content_e_f_g_h_mainmenu,container,false);
        TextView listbtn = view.findViewById(R.id.listviewButtonH1_2);
        TextView calenderbtn = view.findViewById(R.id.listviewButtonH1_2);

        listdetail = view.findViewById(R.id.ListDetailEFGH_1);
        init_view_tsuin_yotei(container.getContext());

        listdetail.setOnItemClickListener(this);

        return view;
    }
    @Override
    public void onResume() {
        super.onResume();
        user_profile up = new user_profile();
        up.DebugOutput();
        if(current_view_ty_size != up.List_tsuin_yotei.size()) {
        init_view_tsuin_yotei(this.getContext());
        adapter.notifyDataSetChanged();
        }
    }

    public void init_view_tsuin_yotei(Context c) {
        view_tsuin_yoteis = new ArrayList<tsuin_yotei>(){};
        current_view_ty_size = 0;
        user_profile up = new user_profile();
        tsuin_yotei prev_ty;

        if(up.List_tsuin_yotei != null && up.List_tsuin_yotei.size() > 0) {
            prev_ty = new tsuin_yotei(
                    up.List_tsuin_yotei.get(0).ID,
                    false,
                    up.List_tsuin_yotei.get(0).hospital,
                    up.List_tsuin_yotei.get(0).s_detail,
                    "",
                    up.List_tsuin_yotei.get(0).y_index,
                    up.List_tsuin_yotei.get(0).m_index,
                    up.List_tsuin_yotei.get(0).d_index,
                    up.List_tsuin_yotei.get(0).time);
            tsuin_yotei firstHeader = new tsuin_yotei(
                    "",
                    true,
                    "",
                    "",
                    "",
                    prev_ty.y_index,
                    prev_ty.m_index,
                    prev_ty.d_index,
                    prev_ty.time
            );
            view_tsuin_yoteis.add(firstHeader);
            view_tsuin_yoteis.add(new tsuin_yotei(
                    up.List_tsuin_yotei.get(0).ID,
                    false,
                    up.List_tsuin_yotei.get(0).hospital,
                    up.List_tsuin_yotei.get(0).s_detail,
                    "",
                    up.List_tsuin_yotei.get(0).y_index,
                    up.List_tsuin_yotei.get(0).m_index,
                    up.List_tsuin_yotei.get(0).d_index,
                    up.List_tsuin_yotei.get(0).time));
            current_view_ty_size++;

            for (int i = 1; i < up.List_tsuin_yotei.size(); i++) {
                if (prev_ty.calc_unixtime_day() != up.List_tsuin_yotei.get(i).calc_unixtime_day()) {
                    tsuin_yotei header = new tsuin_yotei(
                            "",
                            true,
                            "",
                            "",
                            "",
                            up.List_tsuin_yotei.get(i).y_index,
                            up.List_tsuin_yotei.get(i).m_index,
                            up.List_tsuin_yotei.get(i).d_index,
                            up.List_tsuin_yotei.get(i).time
                    );
                    view_tsuin_yoteis.add(header);
                }
                prev_ty = up.List_tsuin_yotei.get(i);
                view_tsuin_yoteis.add(new tsuin_yotei(
                        up.List_tsuin_yotei.get(i).ID,
                        false,
                        up.List_tsuin_yotei.get(i).hospital,
                        up.List_tsuin_yotei.get(i).s_detail,
                        "",
                        up.List_tsuin_yotei.get(i).y_index,
                        up.List_tsuin_yotei.get(i).m_index,
                        up.List_tsuin_yotei.get(i).d_index,
                        up.List_tsuin_yotei.get(i).time));

                current_view_ty_size++;
            }
        }
        //adapter = new ListViewTsuinYotei(view_tsuin_yoteis,c);
        //listdetail.setAdapter(adapter);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if(view_tsuin_yoteis.get(position).t == false){
            Intent i = new Intent(view.getContext(), e3_yotei.class);
            i.putExtra("yotei_id",view_tsuin_yoteis.get(position).ID);
            startActivity(i);
        }
    }
}
