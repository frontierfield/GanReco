package com.frontierfield.ganreco;

import java.util.Comparator;

//listソートする人
public class TsuinYoteiComparator implements Comparator<TsuinYotei> {
    @Override
    public int compare(TsuinYotei tsuinYotei1, TsuinYotei tsuinYotei2){
        int temp=tsuinYotei2.getYearIndex()-tsuinYotei1.getYearIndex();
        if(temp==0){
            temp=tsuinYotei2.getMonthIndex()-tsuinYotei1.getMonthIndex();
            if(temp==0){
                temp=tsuinYotei2.getDayIndex()-tsuinYotei1.getDayIndex();
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
