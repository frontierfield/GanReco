package com.frontierfield.ganreco;

import android.content.Context;
import android.net.Uri;

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
    public static void deleteOkusuriRireki(int position, Context context){
        if(OkusuriRirekiList.get(position).getLocalPath()==OkusuriRirekiList.get(position).getFilePath()) {
            context.getContentResolver().delete(Uri.parse(OkusuriRirekiList.get(position).getLocalPath()), null, null);
        }
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
        OkusuriRireki okusuriRirekiTag=new OkusuriRireki(null,true,okusuriRireki.getPharmacy(),okusuriRireki.getDetail(),
                okusuriRireki.getYear(), okusuriRireki.getMonth(), okusuriRireki.getDay());
        int i;
        for(i=0;i<OkusuriRirekiList.size();i++) {
            if (OkusuriRirekiList.get(i).getYear()==okusuriRireki.getYear()&&
                    OkusuriRirekiList.get(i).getMonth()==okusuriRireki.getMonth()&&
                    OkusuriRirekiList.get(i).getDay()==okusuriRireki.getDay()&&
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

