package com.fym.electrichousekeeper.service;

import org.springframework.stereotype.Service;

/**
 * 阈值设置
 */
@Service
public class ThresholdService {

    /**
     * 电流过负荷
     * @param code
     * @return
     */
    public double getCurrentOverLoadPercent(String code){
        return 10D;
    }

    /**
     * 低电压
     * @param code
     * @return
     */
    public double getLowVoltageStandard(String code){
        return 210D;
    }

    /**
     * 三相不平衡
     * @param code
     * @return
     */
    public double getThreePhaseUnbalance(String code){
        return 20D;
    }

}
