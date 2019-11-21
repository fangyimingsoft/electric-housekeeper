package com.fym.electrichousekeeper.core;

import lombok.Data;

import java.util.List;

@Data
public class Message_Old {
    //起始帧
    private String startFrame;
    //设备编码
    private String deviceCode;
    //帧类型
    private String frameType;
    //7路温度
    private List<Double> temperatures;
    //电力系数
    private List<Double> powerFactors;

    private List<Double> voltage;

    //电流
    private List<Double> current;

    //有功
    private List<Double> activePower;

    //无功
    private List<Double> reactivePower;

    //视在功率
    private List<Double> apparentPower;

    //频率
    private Double frequency;

    //电压谐波
    private List<Double> voltageHarmonics;

    //电流谐波
    private List<Double> currentHarmonics;

    //电压畸变率
    private List<Double> voltageDistortionRate;

    //电流畸变率
    private List<Double> currentDistortionRate;

    //吸收有功二次侧
    private Double secondarySideAbsorbActive;
    //释放有功二次侧
    private Double secondarySideReleaseActive;
    //感性无功二次侧
    private Double secondarySideInductiveReactive;
    //容性无功电能二次侧
    private Double secondarySideCapacitiveReactive;

    //吸收有功二次侧
    private Double primarySideAbsorbActive;
    //释放有功二次侧
    private Double primarySideReleaseActive;
    //感性无功二次侧
    private Double primarySideInductiveReactive;
    //容性无功电能二次侧
    private Double primarySideCapacitiveReactive;

    //经度
    private Double longitude;

    //纬度
    private Double latitude;

    //gps是否有效
    private boolean gpsEffective;

    //检验位
    private String checkDigit;
}
