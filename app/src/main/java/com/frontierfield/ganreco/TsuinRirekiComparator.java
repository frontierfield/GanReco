package com.frontierfield.ganreco;

import java.util.Comparator;

public class TsuinRirekiComparator implements Comparator<TsuinRireki> {
    @Override
    public int compare(TsuinRireki tsuinRireki1, TsuinRireki tsuinRireki2){
        int temp=tsuinRireki2.getYearIndex()-tsuinRireki1.getYearIndex();
        if(temp==0){
            temp=tsuinRireki2.getMonthIndex()-tsuinRireki1.getMonthIndex();
            if(temp==0){
                temp=tsuinRireki2.getDayIndex()-tsuinRireki1.getDayIndex();
                if(temp==0){
                    if (!tsuinRireki2.getHead() && tsuinRireki1.getHead())
                        temp = -1;
                    if (tsuinRireki2.getHead() && !tsuinRireki1.getHead())
                        temp = 1;
                }
            }
        }
        return temp;
    }
}
