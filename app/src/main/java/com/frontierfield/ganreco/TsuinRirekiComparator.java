package com.frontierfield.ganreco;

import java.util.Comparator;

public class TsuinRirekiComparator implements Comparator<TsuinRireki> {
    @Override
    public int compare(TsuinRireki tsuinRireki1, TsuinRireki tsuinRireki2){
        int temp=tsuinRireki2.getYear()-tsuinRireki1.getYear();
        if(temp==0){
            temp=tsuinRireki2.getMonth()-tsuinRireki1.getMonth();
            if(temp==0){
                temp=tsuinRireki2.getDay()-tsuinRireki1.getDay();
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
