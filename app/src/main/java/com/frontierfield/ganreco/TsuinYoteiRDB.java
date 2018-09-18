package com.frontierfield.ganreco;

import android.support.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/*
通院予定をRDBと共有処理するためのやつ
 */
public class TsuinYoteiRDB {

    public static void saveTsuinYoteiRDB(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();
        FirebaseUser mAuthUser;
        mAuthUser = FirebaseAuth.getInstance().getCurrentUser();
        List<tsuin_yotei> tsuinYoteiListRDB=new ArrayList<tsuin_yotei>();
        tsuinYoteiListRDB=TsuinYoteiList.getInstance();
        myRef.child("users").child(mAuthUser.getUid()).child("TsuinYotei").setValue(TsuinYoteiList.getInstance());
        //↑これ可能ってどういうこと？インスタンスstaticのはず
    }
    public static void deleteTsuinYoteiRDB(int position){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();
        FirebaseUser mAuthUser;
        mAuthUser = FirebaseAuth.getInstance().getCurrentUser();
        myRef.child("users").child(mAuthUser.getUid()).child("TsuinYotei").child(String.valueOf(position)).removeValue();
        //このやり方だと、listを使ってる意味がない
    }
    /*
    public void tsuinData_delete(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();
        FirebaseUser mAuthUser;
        mAuthUser = FirebaseAuth.getInstance().getCurrentUser();

        myRef.child("tsuin_yotei").child(mAuthUser.getUid()).child(ID).removeValue();
        user_profile up = new user_profile();
        for(int i = 0;i < up.List_tsuin_yotei.size();i++){
            if(up.List_tsuin_yotei.get(i).ID.equals(this.ID)) {
                up.List_tsuin_yotei.remove(i);
            }
        }
    }

    public void tusinData_add(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();
        FirebaseUser mAuthUser;
        mAuthUser = FirebaseAuth.getInstance().getCurrentUser();

        if(ID != null) {
            myRef.child("tsuin_yotei").child(mAuthUser.getUid()).child(ID).setValue(this);
            user_profile up = new user_profile();
            for(int i = 0;i < up.List_tsuin_yotei.size();i++){
                if(up.List_tsuin_yotei.get(i).ID.equals(this.ID)){
                    up.List_tsuin_yotei.get(i).hospital = this.hospital;
                    up.List_tsuin_yotei.get(i).s_detail = this.s_detail;
                    up.List_tsuin_yotei.get(i).detail = this.detail;
                    up.List_tsuin_yotei.get(i).y_index = this.y_index;
                    up.List_tsuin_yotei.get(i).m_index = this.m_index;
                    up.List_tsuin_yotei.get(i).d_index = this.d_index;
                    up.List_tsuin_yotei.get(i).time = this.time;
                    up.List_tsuin_yotei.get(i).unixtime = this.unixtime;
                }
            }
        }else{
            DatabaseReference newty = myRef.child("tsuin_yotei").child(mAuthUser.getUid()).push();
            this.ID = newty.getKey();
            Task task = newty.setValue(this);

            //taskが完了次第upに入れたいが、どうやるのかわからない
            //listener実装してもいいが、このクラスに実装するといろんなところでリスナーが呼ばれておかしくなる可能性大
            //関数を引数にする方法もあるが、それだと、thisが呼べない
            //task awaitをやりたいが、メインスレッドでawaitを使うのは禁止されている
            UserProfile up = new UserProfile();
            int addIndex = 0;
            for(int i = 0;i < up.tsuinYoteiList.size();i++){
                if(this.unixtime > up.tsuinYoteiList.get(i).unixtime){
                    addIndex = i + 1;
                }else if(i == up.tsuinYoteiList.size()-1 && this.unixtime > up.tsuinYoteiList.get(i).unixtime){
                    addIndex = up.tsuinYoteiList.size();
                }
            }

            up.tsuinYoteiList.add(addIndex, this);
        }
    }
    public void get_tsuinData_and_input_static(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();
        FirebaseUser mAuthUser;
        mAuthUser = FirebaseAuth.getInstance().getCurrentUser();

        myRef.child("tsuin_yotei").child(mAuthUser.getUid()).orderByChild("unixtime").addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        user_profile up = new user_profile();
                        up.List_tsuin_yotei = new ArrayList<tsuin_yotei>() {
                        };
                        Iterable<DataSnapshot> snapshot_children = dataSnapshot.getChildren();
                        for (DataSnapshot t : snapshot_children) {
                            String t_y, t_m, t_d, t_t;

                            int i_y = -1, i_m = -1, i_d = -1, i_t = -1;

                            t_y = t.child("y_index").getValue(true).toString();
                            t_m = t.child("m_index").getValue(true).toString();
                            t_d = t.child("d_index").getValue(true).toString();
                            t_t = t.child("time").getValue(true).toString();

                            if (t_y != null) {
                                i_y = Integer.parseInt(t_y);
                            }
                            if (t_m != null) {
                                i_m = Integer.parseInt(t_m);
                            }
                            if (t_d != null) {
                                i_d = Integer.parseInt(t_d);
                            }
                            if (t_t != null) {
                                i_t = Integer.parseInt(t_t);
                            }

                            tsuin_yotei temp = new tsuin_yotei(
                                    t.getKey(),
                                    false,
                                    t.child("hospital").getValue(true).toString(),
                                    t.child("s_detail").getValue().toString(),
                                    t.child("detail").getValue(true).toString(),
                                    i_y,
                                    i_m,
                                    i_d,
                                    i_t
                            );
                            // up.tsuinYoteiList.add(temp);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                }
        );
    }*/
}
