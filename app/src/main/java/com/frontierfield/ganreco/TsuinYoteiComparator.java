package com.frontierfield.ganreco;

import java.util.Comparator;

//listソートする人
public class TsuinYoteiComparator implements Comparator<tsuin_yotei> {
    @Override
    public int compare(tsuin_yotei ty1,tsuin_yotei ty2){
        int temp=ty2.getYearIndex()-ty1.getYearIndex();
        if(temp==0){
            temp=ty2.getManthIndex()-ty1.getManthIndex();
            if(temp==0){
                temp=ty2.getDayIndex()-ty1.getDayIndex();
                if(temp==0){
                    temp=ty1.getTime()-ty2.getTime();
                    if(temp==0) {
                        if (!ty2.getHead() && ty1.getHead())
                            temp = -1;
                        if (ty2.getHead() && !ty1.getHead())
                            temp = 1;
                    }
                }
            }
        }
        return temp;
    }
}
