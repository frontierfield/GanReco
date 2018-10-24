package com.frontierfield.ganreco;

import java.util.Comparator;

public class OkusuriRirekiComparator implements Comparator<OkusuriRireki> {
    @Override
    public int compare(OkusuriRireki okusuriRireki1, OkusuriRireki okusuriRireki2){
        int temp=okusuriRireki2.getYear()-okusuriRireki1.getYear();
        if(temp==0){
            temp=okusuriRireki2.getMonth()-okusuriRireki1.getMonth();
            if(temp==0){
                temp=okusuriRireki2.getDay()-okusuriRireki1.getDay();
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
