package com.fym.electrichousekeeper.core;

import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;

/**
 * 报警算法
 */
public class WarningAlgorithm {

    /**
     * 电流过负荷
     * 电流大与额定值10%(不固定),报警
     * @param current
     * @param capacity
     * @param percent 过百分比报警
     * @return
     */
    public boolean currentOverLoad(Double current,int capacity,int percent){
        Assert.notNull(current,"电流值不能为null");
        double ratedCurrent = ratedCurrent(capacity);
        Double pt = (current - ratedCurrent) / (ratedCurrent);
        if(pt > (percent * 1D) / 100){
            return true;
        }else{
            return false;
        }
    }


    /**
     * 缺相报警
     *  缺其中一相的数据就报警
     * @param phaseA
     * @param phaseB
     * @param phaseC
     * @return
     */
    public List<String> lackPhase(Double phaseA, Double phaseB, Double phaseC){
        List<String> describe = new ArrayList<>();
        if(phaseA == null){
            describe.add("A相");
        }
        if(phaseB == null){
            describe.add("B相");
        }
        if(phaseC == null){
            describe.add("C相");
        }
        return describe.size() > 0 ? describe : null;
    }

    /**
     * 超温报警
     *
     */
    public void overTemperature(){

    }

    /**
     * 是否低电压
     *  电压低于标准值
     * @param voltage
     * @param standard
     * @return
     */
    public boolean lowVoltage(Double voltage,double standard){
        if(voltage == null){
            throw new NullPointerException();
        }
        if(voltage != null && voltage < standard){
            return  true;
        }
        return false;
    }

    /**
     * 三相不平衡
     * （最大电流-最小电流）/最大电流  > 20%(后台可调)
     * @param ia
     * @param ib
     * @param ic
     * @return
     */
    public static boolean threePhaseUnbalance(Double ia,Double ib,Double ic,int percent){
        if(ia == null || ib == null || ic == null){
            throw new NullPointerException();
        }
        double max = Math.max(ia, ib);
        max = Math.max(max,ic);
        double min = Math.min(ia,ib);
        min = Math.min(min,ic);
        if((max - min) / max > ((percent * 1D) / 100)){
            return true;
        }
        return false;
    }


    /**
     * 电量突增
     */
    public void electricIncrease(){

    }

    /**
     * 谐波过限
     */
    public void harmonicOverLimit(){

    }


    /**
     * 额定电流
     * @param capacity
     * @return
     */
    public static Double ratedCurrent(int capacity){
        return (capacity * 1D)/(1.732D * 0.4D);
    }

}
