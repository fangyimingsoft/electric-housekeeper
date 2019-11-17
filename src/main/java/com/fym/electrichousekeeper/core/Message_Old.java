package com.fym.electrichousekeeper.core;

import lombok.Data;

@Data
public class Message_Old {
    private String firstFrame;
    private String deviceCode;
    private String frameType;
    private String temper_01;
    private String temper_02;
    private String temper_03;
    private String temper_04;
    private String temper_05;
    private String temper_06;
    private String temper_07;
    //电力系数
    private String powerFactor;
    private Double aVoltage;
    private Double bVoltage;
    private Double cVoltage;

    //电流
    private Double aCurrent;
    private Double bCurrent;
    private Double cCurrent;

    //有功
    private Double activePowerA;
    private Double activePowerB;
    private Double activePowerC;
    private Double totalActivePower;

    //无功
    private Double reactivePowerA;
    private Double reactivePowerB;
    private Double reactivePowerC;
    private Double totalReactivePower;

    //视在功率
    private Double apparentPowerA;
    private Double apparentPowerB;
    private Double apparentPowerC;
    private Double totalApparentPower;

    //频率
    private Double frequency;

    //电压谐波
    private Double voltageHarmonicsA;
    private Double voltageHarmonicsB;
    private Double voltageHarmonicsC;

    //电流谐波
    private Double currentHarmonicsA;
    private Double currentHarmonicsB;
    private Double currentHarmonicsC;

    //电压畸变率
    private Double voltageDistortionRateA;
    private Double voltageDistortionRateB;
    private Double voltageDistortionRateC;

    //电流畸变率
    private Double currentDistortionRateA;
    private Double currentDistortionRateB;
    private Double currentDistortionRateC;

    //吸收有功二次侧
    private Double secondarySideAbsorbActive;
    //释放有功二次侧
    private Double secondarySideReleaseActive;
    //感性无功二次侧
    private Double secondarySideInductiveReactive;
    //容性无功电能二次侧
    // private Double secondarySide  Reactive;
}
