package com.fym.electrichousekeeper;

import com.fym.electrichousekeeper.util.DateComputeUtil;
import org.junit.jupiter.api.Test;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

public class CalenderTest {

    @Test
    public void test1(){
        Date now = new Date();
        System.out.println(now);
        Date  tomorrow = DateComputeUtil.add(now, DateComputeUtil.TYPE_DATE,1);
        System.out.println(tomorrow);
    }
}
