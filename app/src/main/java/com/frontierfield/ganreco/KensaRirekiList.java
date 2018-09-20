package com.frontierfield.ganreco;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class KensaRirekiList {
    private static List<KensaRireki> KensaRirekiList= new ArrayList<KensaRireki>();//通院履歴のリスト

    public static List<KensaRireki> getInstance(){
        return KensaRirekiList;  //singleton
    }
    public static void addKensaRireki(KensaRireki kensaRireki){
        KensaRirekiList.add(kensaRireki);
        SetTag(kensaRireki);
        //ソートの処理
        Collections.sort(KensaRirekiList,new KensaRirekiComparator());
        //databaseとの連携処理
        KensaRirekiRDB.saveKensaRirekiRDB();
    }
    public static void deleteKensaRireki(int position){
        KensaRirekiList.remove(position);
        DeleteTag();
        //databaseとの連携処理
        KensaRirekiRDB.saveKensaRirekiRDB();
    }
    public static KensaRireki getSavedKensaRireki(int position){
        KensaRireki kensaRireki;
        kensaRireki=KensaRirekiList.get(position);
        return kensaRireki;
    }
    private static void SetTag(KensaRireki kensaRireki){//list内に年月日が一致するtrueがないときタグ付与
        KensaRireki kensaRirekiTag=new KensaRireki(null,true,kensaRireki.getHospital(),kensaRireki.getDetail(),
                kensaRireki.getYearIndex(), kensaRireki.getMonthIndex(), kensaRireki.getDayIndex());
        int i;
        for(i=0;i<KensaRirekiList.size();i++) {
            if (KensaRirekiList.get(i).getYearIndex()==kensaRireki.getYearIndex()&&
                    KensaRirekiList.get(i).getMonthIndex()==kensaRireki.getMonthIndex()&&
                    KensaRirekiList.get(i).getDayIndex()==kensaRireki.getDayIndex()&&
                    KensaRirekiList.get(i).getHead()){
                break;
            }
        }
        if(i==KensaRirekiList.size()){
            KensaRirekiList.add(kensaRirekiTag);
        }
    }
    private static void DeleteTag(){//tag消してほしいとき->①listの最後がtag ②tagが連続してる
        if(KensaRirekiList.size()>0){
            if(KensaRirekiList.get(KensaRirekiList.size()-1).t) //①
                KensaRirekiList.remove(KensaRirekiList.size()-1);

            for(int i=0;i<KensaRirekiList.size()-1;i++) {//②
                if (KensaRirekiList.get(i).t && KensaRirekiList.get(i + 1).t) {
                    KensaRirekiList.remove(i);
                }
            }
        }
    }
}
