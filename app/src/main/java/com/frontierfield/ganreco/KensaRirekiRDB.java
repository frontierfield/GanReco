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

public class KensaRirekiRDB {
    public static void saveKensaRirekiRDB(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();
        FirebaseUser mAuthUser;
        mAuthUser = FirebaseAuth.getInstance().getCurrentUser();
        List<KensaRireki> kensaRirekiListRDB=new ArrayList<KensaRireki>();
        kensaRirekiListRDB=KensaRirekiList.getInstance();
        myRef.child("users").child(mAuthUser.getUid()).child("KensaRireki").setValue(kensaRirekiListRDB);
        //↑これ可能ってどういうこと？インスタンスstaticのはず
    }
    public static void deleteKensaRirekiRDB(int position){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();
        FirebaseUser mAuthUser;
        mAuthUser = FirebaseAuth.getInstance().getCurrentUser();
        myRef.child("users").child(mAuthUser.getUid()).child("KensaRireki").child(String.valueOf(position)).removeValue();
        //このやり方だと、listを使ってる意味がない
        //データベース上で抜けが出ても、番号が詰まるわけではない
    }
    public static void getSavedKensaRirekiRDB(){
        DatabaseReference myref= FirebaseDatabase.getInstance().getReference();
        FirebaseUser mAuthUser=FirebaseAuth.getInstance().getCurrentUser();
        myref.child("users").child(mAuthUser.getUid()).child("KensaRireki").addListenerForSingleValueEvent(
                new ValueEventListener() {//最初に一回だけ呼ばれるメソッド
                    // @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                            KensaRireki ty=dataSnapshot.getValue(KensaRireki.class);
                            KensaRirekiList.getInstance().add(ty);
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
    }
}
