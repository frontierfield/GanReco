package com.frontierfield.ganreco;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TsuinYoteiList {
    private static List<TsuinYotei> TsuinYoteiList= new ArrayList<TsuinYotei>();//通院予定のリスト

    public static List<TsuinYotei> getInstance(){
        return TsuinYoteiList;  //singleton
    }
    public static void addTsuinYotei(TsuinYotei ty){
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
    public static TsuinYotei getSavedTsuinYotei(int position){
        TsuinYotei ty;
        ty=TsuinYoteiList.get(position);
        return ty;
    }
    private static void SetTag(TsuinYotei ty){//list内に年月日が一致するtrueがないとき
        TsuinYotei tyTag=new TsuinYotei(null,true,ty.getHospital(),ty.getSDetail(),
                ty.getDetail(), ty.getYear(), ty.getMonth(), ty.getDay(), ty.getTime());
        int i;
        for(i=0;i<TsuinYoteiList.size();i++) {
            if (TsuinYoteiList.get(i).getYear()==ty.getYear()&&
                    TsuinYoteiList.get(i).getMonth()==ty.getMonth()&&
                    TsuinYoteiList.get(i).getDay()==ty.getDay()&&
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
