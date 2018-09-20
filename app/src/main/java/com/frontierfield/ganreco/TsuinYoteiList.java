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
        SetTag(ty);
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
        DeleteTag();
        //databaseとの連携処理
        TsuinYoteiRDB.saveTsuinYoteiRDB();
    }
    public static tsuin_yotei getSavedTsuinYotei(int position){
        tsuin_yotei ty;
        ty=TsuinYoteiList.get(position);
        return ty;
    }
    private static void SetTag(tsuin_yotei ty){//list内に年月日が一致するtrueがないとき
        tsuin_yotei tyTag=new tsuin_yotei(null,true,ty.getHospital(),ty.getSDetail(),
                ty.getDetail(), ty.getYearIndex(), ty.getManthIndex(), ty.getDayIndex(), ty.getTime());
        int i;
        for(i=0;i<TsuinYoteiList.size();i++) {
            if (TsuinYoteiList.get(i).getYearIndex()==ty.getYearIndex()&&
                    TsuinYoteiList.get(i).getManthIndex()==ty.getManthIndex()&&
                    TsuinYoteiList.get(i).getDayIndex()==ty.getDayIndex()&&
                    TsuinYoteiList.get(i).getHead()){
                break;
            }
        }
        if(i==TsuinYoteiList.size()){
            TsuinYoteiList.add(tyTag);
        }
    }
    private static void DeleteTag(){//tag消してほしいとき->①listの最後がtag ②tagが連続してる
        if(TsuinYoteiList.size()>0){
            if(TsuinYoteiList.get(TsuinYoteiList.size()-1).t) //①
                TsuinYoteiList.remove(TsuinYoteiList.size()-1);

            for(int i=0;i<TsuinYoteiList.size()-1;i++) {//②
                if (TsuinYoteiList.get(i).t && TsuinYoteiList.get(i + 1).t) {
                    TsuinYoteiList.remove(i);
                }
            }
        }
    }
}
