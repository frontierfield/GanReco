package com.frontierfield.ganreco;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TsuinRirekiList {
    private static List<TsuinRireki> TsuinRirekiList= new ArrayList<TsuinRireki>();//通院履歴のリスト

    public static List<TsuinRireki> getInstance(){
        return TsuinRirekiList;  //singleton
    }
    public static void addTsuinRireki(TsuinRireki tsuinRireki){
        TsuinRirekiList.add(tsuinRireki);
        SetTag(tsuinRireki);
        //ソートの処理
        Collections.sort(TsuinRirekiList,new TsuinRirekiComparator());
        //databaseとの連携処理
        TsuinRirekiRDB.saveTsuinRirekiRDB();
    }
    public static void deleteTsuinRireki(int position){
        TsuinRirekiList.remove(position);
        DeleteTag();
        //databaseとの連携処理
        TsuinRirekiRDB.saveTsuinRirekiRDB();
    }
    public static TsuinRireki getSavedTsuinRireki(int position){
        TsuinRireki tsuinRireki;
        tsuinRireki=TsuinRirekiList.get(position);
        return tsuinRireki;
    }
    private static void SetTag(TsuinRireki tsuinRireki){//list内に年月日が一致するtrueがないときタグ付与
        TsuinRireki tsuinRirekiTag=new TsuinRireki(null,true,tsuinRireki.getHospital(),tsuinRireki.getDetail(),
                tsuinRireki.getYearIndex(), tsuinRireki.getMonthIndex(), tsuinRireki.getDayIndex());
        int i;
        for(i=0;i<TsuinRirekiList.size();i++) {
            if (TsuinRirekiList.get(i).getYearIndex()==tsuinRireki.getYearIndex()&&
                    TsuinRirekiList.get(i).getMonthIndex()==tsuinRireki.getMonthIndex()&&
                    TsuinRirekiList.get(i).getDayIndex()==tsuinRireki.getDayIndex()&&
                    TsuinRirekiList.get(i).getHead()){
                break;
            }
        }
        if(i==TsuinRirekiList.size()){
            TsuinRirekiList.add(tsuinRirekiTag);
        }
    }
    private static void DeleteTag(){//tag消してほしいとき->①listの最後がtag ②tagが連続してる
        if(TsuinRirekiList.size()>0){
            if(TsuinRirekiList.get(TsuinRirekiList.size()-1).t) //①
                TsuinRirekiList.remove(TsuinRirekiList.size()-1);

            for(int i=0;i<TsuinRirekiList.size()-1;i++) {//②
                if (TsuinRirekiList.get(i).t && TsuinRirekiList.get(i + 1).t) {
                    TsuinRirekiList.remove(i);
                }
            }
        }
    }
}
