package com.frontierfield.ganreco;

import java.util.Comparator;

public class KensaRirekiComparator implements Comparator<KensaRireki> {
    @Override
    public int compare(KensaRireki kensaRireki1, KensaRireki kensaRireki2){
        int temp=kensaRireki2.getYear()-kensaRireki1.getYear();
        if(temp==0){
            temp=kensaRireki2.getMonth()-kensaRireki1.getMonth();
            if(temp==0){
                temp=kensaRireki2.getDay()-kensaRireki1.getDay();
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

