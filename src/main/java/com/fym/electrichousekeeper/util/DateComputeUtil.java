package com.fym.electrichousekeeper.util;

import java.util.Calendar;
import java.util.Date;

public class DateComputeUtil {

    public final static int TYPE_YEAR = Calendar.YEAR;

    public final static int TYPE_MONTH = Calendar.MONTH;

    public final static int TYPE_DATE = Calendar.DATE;

    public final static int TYPE_HOUR = Calendar.HOUR;

    public final static int TYPE_MINUTE = Calendar.MINUTE;

    public final static int TYPE_SECOND = Calendar.SECOND;

    public final static int TYPE_MILLISECOND = Calendar.MILLISECOND;

    public static Date add(Date date,int type,int number){
        Calendar instance = Calendar.getInstance();
        instance.setTime(date);
        if(number > 0){
            instance.add(type,number);
        }else{
            instance.roll(type,number);
        }
        return instance.getTime();
    }

    public static Date todayZero(){
        Date date = new Date();
        Calendar ins = Calendar.getInstance();
        ins.setTime(date);
        ins.set(TYPE_HOUR,0);
        ins.set(TYPE_MINUTE,0);
        ins.set(TYPE_SECOND,0);
        ins.set(TYPE_MILLISECOND,0);
        return ins.getTime();
    }
}

