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
import java.util.List;

public class TsuinRirekiRDB {
    public static void saveTsuinRirekiRDB(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();
        FirebaseUser mAuthUser;
        mAuthUser = FirebaseAuth.getInstance().getCurrentUser();
        List<TsuinRireki> tsuinRirekiListRDB=new ArrayList<TsuinRireki>();
        tsuinRirekiListRDB=TsuinRirekiList.getInstance();
        myRef.child("users").child(mAuthUser.getUid()).child("TsuinRireki").setValue(tsuinRirekiListRDB);
        //↑これ可能ってどういうこと？インスタンスstaticのはず
    }
    public static void deleteTsuinRirekiRDB(int position){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();
        FirebaseUser mAuthUser;
        mAuthUser = FirebaseAuth.getInstance().getCurrentUser();
        myRef.child("users").child(mAuthUser.getUid()).child("TsuinRireki").child(String.valueOf(position)).removeValue();
        //このやり方だと、listを使ってる意味がない
        //データベース上で抜けが出ても、番号が詰まるわけではない
    }
    public static void getSavedTsuinRirekiRDB(){
        DatabaseReference myref= FirebaseDatabase.getInstance().getReference();
        FirebaseUser mAuthUser=FirebaseAuth.getInstance().getCurrentUser();
        myref.child("users").child(mAuthUser.getUid()).child("TsuinRireki").addListenerForSingleValueEvent(
                new ValueEventListener() {//最初に一回だけ呼ばれるメソッド
                    // @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                            TsuinRireki tsuinRireki=dataSnapshot.getValue(TsuinRireki.class);
                            TsuinRirekiList.getInstance().add(tsuinRireki);
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
    }
}
