package com.frontierfield.ganreco;

import java.util.Comparator;

public class OkusuriRirekiComparator implements Comparator<OkusuriRireki> {
    @Override
    public int compare(OkusuriRireki okusuriRireki1, OkusuriRireki okusuriRireki2){
        int temp=okusuriRireki2.getYearIndex()-okusuriRireki1.getYearIndex();
        if(temp==0){
            temp=okusuriRireki2.getMonthIndex()-okusuriRireki1.getMonthIndex();
            if(temp==0){
                temp=okusuriRireki2.getDayIndex()-okusuriRireki1.getDayIndex();
                if(temp==0){
                    if (!okusuriRireki2.getHead() && okusuriRireki1.getHead())
                        temp = -1;
                    if (okusuriRireki2.getHead() && !okusuriRireki1.getHead())
                        temp = 1;
                }
            }
        }
        return temp;
    }
}
