package com.frontierfield.ganreco;

import java.util.Comparator;

//listソートする人
public class TsuinYoteiComparator implements Comparator<TsuinYotei> {
    @Override
    public int compare(TsuinYotei ty1, TsuinYotei ty2){
        int temp=ty2.getYearIndex()-ty1.getYearIndex();
        if(temp==0){
            temp=ty2.getMonthIndex()-ty1.getMonthIndex();
            if(temp==0){
                temp=ty2.getDayIndex()-ty1.getDayIndex();
                if(temp==0){
                    if (!ty2.getHead() && ty1.getHead())
                        temp = -1;
                    if (ty2.getHead() && !ty1.getHead())
                        temp = 1;
                    if(temp==0) {
                        temp=ty1.getTime()-ty2.getTime();
                    }
                }
            }
        }
        return temp;
    }
}
