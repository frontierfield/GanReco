package com.frontierfield.ganreco;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class OkusuriRirekiList {
    private static List<OkusuriRireki> OkusuriRirekiList= new ArrayList<OkusuriRireki>();//通院履歴のリスト

    public static List<OkusuriRireki> getInstance(){
        return OkusuriRirekiList;  //singleton
    }
    public static void addOkusuriRireki(OkusuriRireki okusuriRireki){
        OkusuriRirekiList.add(okusuriRireki);
        SetTag(okusuriRireki);
        //ソートの処理
        Collections.sort(OkusuriRirekiList,new OkusuriRirekiComparator());
        //databaseとの連携処理
        OkusuriRirekiRDB.saveOkusuriRirekiRDB();
    }
    public static void deleteOkusuriRireki(int position){
        OkusuriRirekiList.remove(position);
        DeleteTag();
        //databaseとの連携処理
        OkusuriRirekiRDB.saveOkusuriRirekiRDB();
    }
    public static OkusuriRireki getSavedOkusuriRireki(int position){
        OkusuriRireki okusuriRireki;
        okusuriRireki=OkusuriRirekiList.get(position);
        return okusuriRireki;
    }
    private static void SetTag(OkusuriRireki okusuriRireki){//list内に年月日が一致するtrueがないときタグ付与
        OkusuriRireki okusuriRirekiTag=new OkusuriRireki(null,true,okusuriRireki.getDrugstore(),okusuriRireki.getDetail(),
                okusuriRireki.getYearIndex(), okusuriRireki.getMonthIndex(), okusuriRireki.getDayIndex());
        int i;
        for(i=0;i<OkusuriRirekiList.size();i++) {
            if (OkusuriRirekiList.get(i).getYearIndex()==okusuriRireki.getYearIndex()&&
                    OkusuriRirekiList.get(i).getMonthIndex()==okusuriRireki.getMonthIndex()&&
                    OkusuriRirekiList.get(i).getDayIndex()==okusuriRireki.getDayIndex()&&
                    OkusuriRirekiList.get(i).getHead()){
                break;
            }
        }
        if(i==OkusuriRirekiList.size()){
            OkusuriRirekiList.add(okusuriRirekiTag);
        }
    }
    private static void DeleteTag(){//tag消してほしいとき->①listの最後がtag ②tagが連続してる
        if(OkusuriRirekiList.size()>0){
            if(OkusuriRirekiList.get(OkusuriRirekiList.size()-1).t) //①
                OkusuriRirekiList.remove(OkusuriRirekiList.size()-1);

            for(int i=0;i<OkusuriRirekiList.size()-1;i++) {//②
                if (OkusuriRirekiList.get(i).t && OkusuriRirekiList.get(i + 1).t) {
                    OkusuriRirekiList.remove(i);
                }
            }
        }
    }
}

