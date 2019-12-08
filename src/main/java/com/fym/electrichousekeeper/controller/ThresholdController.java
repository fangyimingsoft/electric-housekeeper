package com.fym.electrichousekeeper.controller;

import com.fym.electrichousekeeper.common.ApiResponse;
import com.fym.electrichousekeeper.entiry.po.Threshold;
import com.fym.electrichousekeeper.service.ThresholdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/threshold")
public class ThresholdController {

    @Autowired
    private ThresholdService thresholdService;

    @GetMapping("/device")
    public ApiResponse getThreshold(@RequestParam(value = "deviceCode",required = false)String code){
        ApiResponse response = ApiResponse.OK();
        Map<String,Object> resultMap = new HashMap<>();
        resultMap.put(Threshold.CODE_CURRENT_OVER_LOAD,thresholdService.getCurrentOverLoadPercent(code));
        resultMap.put(Threshold.CODE_HARMONIC_OVER_LIMIT,thresholdService.getHarmonicOverlimit(code));
        resultMap.put(Threshold.CODE_LOW_VOLTAGE,thresholdService.getLowVoltageStandard(code));
        resultMap.put(Threshold.CODE_OVER_TEMPERATURE,thresholdService.getOverTemperStandard(code));
        resultMap.put(Threshold.CODE_PHASE_UNBALANCE,thresholdService.getThreePhaseUnbalance(code));
        resultMap.put(Threshold.CODE_ELECTRIC_INCREASE,thresholdService.getElectricIncrease(code));
        response.setData(resultMap);
        return response;
    }

    /**
     *currentOver : 10,//电流过限
     *overTemperature: 80,//超温
     *harmLimit : 10,//谐波过限
     *electricIncrease : 10,//电量突增
     *unbalance : 10,//三相不平衡
     *lowVoltage :200,//低电压
     * @param deviceCode
     * @return
     */
    @PostMapping("/update")
    public ApiResponse updateThreshold(@RequestParam(value = "deviceCode",required = false)String deviceCode,
                                       @RequestParam(Threshold.CODE_CURRENT_OVER_LOAD)Integer currentOver,
                                       @RequestParam(Threshold.CODE_OVER_TEMPERATURE)Integer overTemperature,
                                       @RequestParam(Threshold.CODE_HARMONIC_OVER_LIMIT)Integer harmLimit,
                                       @RequestParam(Threshold.CODE_ELECTRIC_INCREASE)Integer electricIncrease,
                                       @RequestParam(Threshold.CODE_PHASE_UNBALANCE)Integer unbalance,
                                       @RequestParam(Threshold.CODE_LOW_VOLTAGE)Integer lowVoltage){
        ApiResponse response = ApiResponse.OK();
        thresholdService.updateThreshold(deviceCode,currentOver,overTemperature,harmLimit,electricIncrease,unbalance,lowVoltage);
        return response;
    }
}
