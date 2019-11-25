package com.fym.electrichousekeeper.entiry.po;

import com.sun.beans.editors.IntegerEditor;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@lombok.Data
@Table(name = "tdata")
public class Data {
    @Id
    private IntegerEditor id;//int(11) NOT NULL
    private String startChar;//varchar(1) NULL起始符号
    private String code;//varchar(20) NOT NULL
    private String frameType;//varchar(2) NULL帧类型
    private Double temperHa;//float NULL温度1
    private Double temperHb;//float NULL
    private Double temperHc;//float NULL
    private Double temperLa;//float NULL
    private Double temperLb;//float NULL
    private Double temperLc;//float NULL
    private Double temperN;//float NULL
    private Double voltageA;//float NULLA相电压
    private Double voltageB;//float NULLB相
    private Double voltageC;//float NULLC相
    private Double currentA;//float NULLA相电流
    private Double currentB;//float NULLB相
    private Double currentC;//float NULLC相
    private String powerFactorStr;//varchar(5) NULL电力系数
    private Double activePowerA;//float NULLA相有功功率
    private Double activePowerB;//float NULLB
    private Double activePowerC;//float NULLC
    private Double activePower;//float NULL总有功功率
    private Double reactivePowerA;//float NULLA相无功功率
    private Double reactivePowerB;//float NULLB
    private Double reactivePowerC;//float NULLC
    private Double reactivePower;//float NULL总无功功率
    private Double powerFactorA;//float NULLa相功率因数
    private Double powerFactorB;//float NULLb相功率因数
    private Double powerFactorC;//float NULLc相功率因数
    private Double powerFactor;//float NULL总功率因数
    private Double apparentPowerA;//float NULL
    private Double apparentPowerB;//float NULL
    private Double apparentPowerC;//float NULL
    private Double apparentPower;//float NOT NULL总视在功率
    private Double freq;//float NULL频率
    private Double voltageHarmA;//float NULLA相电压谐波
    private Double voltageHarmB;//float NULLB相电压谐波
    private Double voltageHarmC;//float NULLC相电压谐波
    private Double currentHarmA;//float NULLA相电流谐波
    private Double currentHarmB;//float NULLB相电流谐波
    private Double currentHarmC;//float NULLC相电流谐波
    private Double voltageDistA;//float NULLa相电压畸变率
    private Double voltageDistB;//float NULL
    private Double voltageDistC;//float NULL
    private Double currentDistA;//float NULLa相电流畸变率
    private Double currentDistB;//float NULL
    private Double currentDistC;//float NULL
    private Double secondaryAbsorbActive;//float NULL吸收有功二次侧
    private Double secondaryReleaseActive;//float NULL释放有功二次侧
    private Double secondaryInductiveReactive;//float NULL感性无功二次侧
    private Double secondaryCapacitiveReactive;//float NULL容性无功二次侧
    private Double primaryAbsorbActive;//float NULL吸收有功一次侧
    private Double primaryReleaseActive;//float NULL释放有功一次侧
    private Double primaryInductiveReactive;//float NULL感性无功一次侧
    private Double primaryCapacitiveReactive;//float NULL容性无功二次侧
    private String latitude;//varchar(12) NULL纬度
    private String longitude;//varchar(12) NULL经度
    private Integer gpsEffective;//int(1) NULLgps是否有效
    private String checkDigit;//varchar(4) NULL检验位
    private Date time;//datetime
    private Integer topicOffset;
}
