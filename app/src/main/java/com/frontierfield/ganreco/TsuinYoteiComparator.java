package com.frontierfield.ganreco;

import java.util.Comparator;

//listソートする人
public class TsuinYoteiComparator implements Comparator<TsuinYotei> {
    @Override
    public int compare(TsuinYotei tsuinYotei1, TsuinYotei tsuinYotei2){
        int temp=tsuinYotei2.getYear()-tsuinYotei1.getYear();
        if(temp==0){
            temp=tsuinYotei2.getMonth()-tsuinYotei1.getMonth();
            if(temp==0){
                temp=tsuinYotei2.getDay()-tsuinYotei1.getDay();
                if(temp==0){
                    if (!tsuinYotei2.getHead() && tsuinYotei1.getHead())
                        temp = -1;
                    if (tsuinYotei2.getHead() && !tsuinYotei1.getHead())
                        temp = 1;
                    if(temp==0) {
                        temp=tsuinYotei1.getTime()-tsuinYotei2.getTime();
                    }
                }
            }
        }
        return temp;
    }
}
