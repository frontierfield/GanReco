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
        myRef.child("users").child(mAuthUser.getUid()).child("TsuinYotei").setValue(tsuinYoteiListRDB);
        //↑これ可能ってどういうこと？インスタンスstaticのはず
    }
    public static void deleteTsuinYoteiRDB(int position){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();
        FirebaseUser mAuthUser;
        mAuthUser = FirebaseAuth.getInstance().getCurrentUser();
        myRef.child("users").child(mAuthUser.getUid()).child("TsuinYotei").child(String.valueOf(position)).removeValue();
        //このやり方だと、listを使ってる意味がない
        //データベース上で抜けが出ても、番号が詰まるわけではない
    }
    public static void getSavedTsuinYoteiRDB(){
        DatabaseReference myref= FirebaseDatabase.getInstance().getReference();
        FirebaseUser mAuthUser=FirebaseAuth.getInstance().getCurrentUser();
        myref.child("users").child(mAuthUser.getUid()).child("TsuinYotei").addListenerForSingleValueEvent(
                new ValueEventListener() {//最初に一回だけ呼ばれるメソッド
                    // @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                            tsuin_yotei ty=dataSnapshot.getValue(tsuin_yotei.class);
                            TsuinYoteiList.getInstance().add(ty);
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
    }
}
