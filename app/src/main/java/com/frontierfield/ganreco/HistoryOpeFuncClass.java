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
    int position;
    int current_view_ty_size = 0;// UserProfile内の通院予定数が変わっていないかどうか確認するための変数
    List<TsuinYotei> view_tsuin_yoteis;
    TsuinYoteiListViewAdapter adapter;
    ListView listdetail;
    UserProfile userProfile = UserProfile.getInstance();

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
        if(current_view_ty_size != userProfile.getTsuinYoteiList().size()) {
            init_view_tsuin_yotei(this.getContext());
            adapter.notifyDataSetChanged();
        }
    }

    public void init_view_tsuin_yotei(Context context) {
        view_tsuin_yoteis = new ArrayList<TsuinYotei>(){};
        current_view_ty_size = 0;
        TsuinYotei tsuinYotei = null;
        final int INDEX = 0;

        //if(up.tsuinYoteiList != null && up.tsuinYoteiList.size() > 0) {
        if(userProfile.getTsuinYoteiList().size() > 0) {
            tsuinYotei = new TsuinYotei(
                    userProfile.getTsuinYoteiList().get(INDEX).ID,
                    false,
                    userProfile.getTsuinYoteiList().get(INDEX).hospital,
                    userProfile.getTsuinYoteiList().get(INDEX).s_detail,
                    "",
                    userProfile.getTsuinYoteiList().get(INDEX).y_index,
                    userProfile.getTsuinYoteiList().get(INDEX).m_index,
                    userProfile.getTsuinYoteiList().get(INDEX).d_index,
                    userProfile.getTsuinYoteiList().get(INDEX).time
            );
            TsuinYotei firstHeader = new TsuinYotei(
                    "",
                    true,
                    "",
                    "",
                    "",
                    tsuinYotei.y_index,
                    tsuinYotei.m_index,
                    tsuinYotei.d_index,
                    tsuinYotei.time
            );
            view_tsuin_yoteis.add(firstHeader);
            view_tsuin_yoteis.add(new TsuinYotei(
                    userProfile.getTsuinYoteiList().get(INDEX).ID,
                    false,
                    userProfile.getTsuinYoteiList().get(INDEX).hospital,
                    userProfile.getTsuinYoteiList().get(INDEX).s_detail,
                    "",
                    userProfile.getTsuinYoteiList().get(INDEX).y_index,
                    userProfile.getTsuinYoteiList().get(INDEX).m_index,
                    userProfile.getTsuinYoteiList().get(INDEX).d_index,
                    userProfile.getTsuinYoteiList().get(INDEX).time
            ));
            current_view_ty_size++;

            for (int i = 1; i < userProfile.getTsuinYoteiList().size(); i++) {
                if (tsuinYotei.calc_unixtime_day() != userProfile.getTsuinYoteiList().get(i).calc_unixtime_day()) {
                    TsuinYotei header = new TsuinYotei(
                            "",
                            true,
                            "",
                            "",
                            "",
                            userProfile.getTsuinYoteiList().get(i).y_index,
                            userProfile.getTsuinYoteiList().get(i).m_index,
                            userProfile.getTsuinYoteiList().get(i).d_index,
                            userProfile.getTsuinYoteiList().get(i).time
                    );
                    view_tsuin_yoteis.add(header);
                }
                tsuinYotei = userProfile.getTsuinYoteiList().get(i);
                view_tsuin_yoteis.add(new TsuinYotei(
                        userProfile.getTsuinYoteiList().get(i).ID,
                        false,
                        userProfile.getTsuinYoteiList().get(i).hospital,
                        userProfile.getTsuinYoteiList().get(i).s_detail,
                        "",
                        userProfile.getTsuinYoteiList().get(i).y_index,
                        userProfile.getTsuinYoteiList().get(i).m_index,
                        userProfile.getTsuinYoteiList().get(i).d_index,
                        userProfile.getTsuinYoteiList().get(i).time
                ));

                current_view_ty_size++;
            }
        }
        //adapter = new ListViewTsuinYotei(view_tsuin_yoteis,c);
        //listdetail.setAdapter(adapter);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if(view_tsuin_yoteis.get(position).t == false){
            Intent i = new Intent(view.getContext(), E3_Input.class);
            i.putExtra("yotei_id",view_tsuin_yoteis.get(position).ID);
            startActivity(i);
        }
    }
}
