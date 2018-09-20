package com.frontierfield.ganreco;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class TsuinYoteiList {
    private static List<tsuin_yotei> TsuinYoteiList= new ArrayList<tsuin_yotei>();//通院予定のリスト

    public static List<tsuin_yotei> getInstance(){
        return TsuinYoteiList;  //singleton
    }
    public static void addTsuinYotei(tsuin_yotei ty){
        TsuinYoteiList.add(ty);
        //ソートの処理
        Collections.sort(TsuinYoteiList,new TsuinYoteiComparator());
        /*
        ***とりあえず追加しちゃってからソートすべきか、要素を検索して適切な位置に追加するようにするか
        * ひとまず前者でやってみる
         */
        //databaseとの連携処理
        TsuinYoteiRDB.saveTsuinYoteiRDB();
    }
    public static void deleteTsuinYotei(int position){
        TsuinYoteiList.remove(position);
        //databaseとの連携処理
        TsuinYoteiRDB.saveTsuinYoteiRDB();
    }
    public List<tsuin_yotei> getSavedTsuinYoteiList(){
        List<tsuin_yotei> ty=null;
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();
        FirebaseUser mAuthUser;
        mAuthUser = FirebaseAuth.getInstance().getCurrentUser();
        //databaseから保存されてるリストとってくる処理？

        return TsuinYoteiList;
    }
    public static tsuin_yotei getSavedTsuinYotei(int position){
        tsuin_yotei ty;
        ty=TsuinYoteiList.get(position);
        return ty;
    }
}
