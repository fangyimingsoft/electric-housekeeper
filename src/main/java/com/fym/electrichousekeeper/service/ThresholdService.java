package com.fym.electrichousekeeper.service;

import com.fym.electrichousekeeper.dao.ThresholdRepository;
import com.fym.electrichousekeeper.entiry.po.Threshold;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import java.util.*;

/**
 * 阈值设置
 */
@Service
public class ThresholdService {

    private static Map<String, List<Threshold>> deviceThresholdCache;

    private static Map<String,Threshold> globalThresholdCache;

    @Autowired
    private ThresholdRepository repository;

    @PostConstruct
    private synchronized void initCacheData(){
        deviceThresholdCache = new HashMap<>();
        globalThresholdCache = new HashMap<>();
        List<Threshold> all = repository.findAll();
        all.forEach(item->{
            Boolean isGlobal = item.getIsGlobal();
            if(isGlobal != null && isGlobal){
                String typeCode = item.getTypeCode();
                globalThresholdCache.put(typeCode,item);
            }else{
                String deviceCode = item.getDeviceCode();
                List<Threshold> list = deviceThresholdCache.getOrDefault(deviceCode, new ArrayList<>());
                list.add(item);
                deviceThresholdCache.put(deviceCode,list);
            }
        });
    }

    @Transactional
    public synchronized void updateThreshold(String deviceCode,
                                int currentOver,
                                int overTemperature,
                                int harmLimit,
                                int electricIncrease,
                                int unbalance,
                                int lowVoltage){
        if(StringUtils.isEmpty(deviceCode)){
            //更新全局的配置
            //删除非全局配置
            repository.removeThresholdsByIsGlobal(false);
            //更新
            repository.updateGlobalThreshold(Threshold.CODE_CURRENT_OVER_LOAD,currentOver);
            repository.updateGlobalThreshold(Threshold.CODE_OVER_TEMPERATURE,overTemperature);
            repository.updateGlobalThreshold(Threshold.CODE_HARMONIC_OVER_LIMIT,harmLimit);
            repository.updateGlobalThreshold(Threshold.CODE_ELECTRIC_INCREASE,electricIncrease);
            repository.updateGlobalThreshold(Threshold.CODE_PHASE_UNBALANCE,unbalance);
            repository.updateGlobalThreshold(Threshold.CODE_LOW_VOLTAGE,lowVoltage);
        }else {
            repository.removeDeviceThreshold(deviceCode);
            List<Threshold> thresholds = new ArrayList<>();
            thresholds.add(new Threshold(null, Threshold.CODE_CURRENT_OVER_LOAD, currentOver, false, deviceCode, new Date()));
            thresholds.add(new Threshold(null, Threshold.CODE_OVER_TEMPERATURE, overTemperature, false, deviceCode, new Date()));
            thresholds.add(new Threshold(null, Threshold.CODE_HARMONIC_OVER_LIMIT, harmLimit, false, deviceCode, new Date()));
            thresholds.add(new Threshold(null, Threshold.CODE_ELECTRIC_INCREASE, electricIncrease, false, deviceCode, new Date()));
            thresholds.add(new Threshold(null, Threshold.CODE_PHASE_UNBALANCE, unbalance, false, deviceCode, new Date()));
            thresholds.add(new Threshold(null, Threshold.CODE_LOW_VOLTAGE, lowVoltage, false, deviceCode, new Date()));
            repository.saveAll(thresholds);
        }
        initCacheData();
    }

    public Threshold getThreshold(String deviceCode, String typeCode){
        Threshold threshold = null;
        if(StringUtils.isEmpty(deviceCode)){
            threshold = globalThresholdCache.get(typeCode);
        }else{
            List<Threshold> thresholds = deviceThresholdCache.get(deviceCode);
            if(thresholds != null && thresholds.size() > 0){
                for (Threshold hold : thresholds) {
                    if(typeCode.equals(hold.getTypeCode())){
                        threshold = hold;
                        break;
                    }
                }
            }
        }
        return threshold != null ? threshold : globalThresholdCache.get(typeCode);
    }

    /**
     * 电流过负荷
     * @param code
     * @return
     */
    public double getCurrentOverLoadPercent(String code){
        Threshold threshold = getThreshold(code,Threshold.CODE_CURRENT_OVER_LOAD);
        return threshold == null ? 10D : (threshold.getValue() * 1D);
    }

    /**
     * 低电压
     * @param code
     * @return
     */
    public double getLowVoltageStandard(String code){
        Threshold threshold = getThreshold(code,Threshold.CODE_LOW_VOLTAGE);
        return threshold == null ? 200D : (threshold.getValue() * 1D);
    }

    /**
     * 三相不平衡
     * @param code
     * @return
     */
    public double getThreePhaseUnbalance(String code){
        Threshold threshold = getThreshold(code,Threshold.CODE_PHASE_UNBALANCE);
        return threshold == null ? 20D : (threshold.getValue() * 1D);
    }

    /**
     * 超温
     * @param code
     * @return
     */
    public double getOverTemperStandard(String code){
        Threshold threshold = getThreshold(code,Threshold.CODE_OVER_TEMPERATURE);
        return threshold == null ? 80D : (threshold.getValue() * 1D);
    }

    /**
     * 谐波过限
     * @param code
     * @return
     */
    public double getHarmonicOverlimit(String code){
        Threshold threshold = getThreshold(code,Threshold.CODE_HARMONIC_OVER_LIMIT);
        return threshold == null ? 10 : (threshold.getValue() * 1D);
    }

    /**
     * 电量激增
     * @param code
     * @return
     */
    public double getElectricIncrease(String code){
        Threshold threshold = getThreshold(code,Threshold.CODE_ELECTRIC_INCREASE);
        return threshold == null ? 10 : (threshold.getValue() * 1D);
    }

}
