package com.frontierfield.ganreco;

import java.util.Comparator;

public class KensaRirekiComparator implements Comparator<KensaRireki> {
    @Override
    public int compare(KensaRireki kensaRireki1, KensaRireki kensaRireki2){
        int temp=kensaRireki2.getYearIndex()-kensaRireki1.getYearIndex();
        if(temp==0){
            temp=kensaRireki2.getMonthIndex()-kensaRireki1.getMonthIndex();
            if(temp==0){
                temp=kensaRireki2.getDayIndex()-kensaRireki1.getDayIndex();
                if(temp==0){
                    if (!kensaRireki2.getHead() && kensaRireki1.getHead())
                        temp = -1;
                    if (kensaRireki2.getHead() && !kensaRireki1.getHead())
                        temp = 1;
                }
            }
        }
        return temp;
    }
}

