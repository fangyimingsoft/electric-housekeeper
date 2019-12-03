package com.fym.electrichousekeeper.entiry.po;

import javax.persistence.*;
import java.util.Date;

@lombok.Data
@Entity
@Table(name = "t_data")
public class Data {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;//int(11) NOT NULL
    private String startChar;//varchar(1) NULL起始符号
    private String code;//varchar(20) NOT NULL
    private String frameType;//varchar(2) NULL帧类型
    private Double temperHa;//float NULL温度1
    private Double temperHb;//float NULL
    private Double temperHc;//float NULL
    private Double temperLa;//float NULL
    private Double temperLb;//float NULL
    private Double temperLc;//float NULL
    @Column(name = "temper_n")
    private Double temperN;//float NULL
    @Column(name = "voltage_a")
    private Double voltageA;//float NULLA相电压
    @Column(name = "voltage_b")
    private Double voltageB;//float NULLB相
    @Column(name = "voltage_c")
    private Double voltageC;//float NULLC相
    @Column(name = "current_a")
    private Double currentA;//float NULLA相电流
    @Column(name = "current_b")
    private Double currentB;//float NULLB相
    @Column(name = "current_c")
    private Double currentC;//float NULLC相
    private String powerFactorStr;//varchar(5) NULL电力系数
    @Column(name = "active_power_a")
    private Double activePowerA;//float NULLA相有功功率
    @Column(name = "active_power_B")
    private Double activePowerB;//float NULLB
    @Column(name = "active_power_c")
    private Double activePowerC;//float NULLC
    private Double activePower;//float NULL总有功功率
    @Column(name = "reactive_power_a")
    private Double reactivePowerA;//float NULLA相无功功率
    @Column(name = "reactive_power_b")
    private Double reactivePowerB;//float NULLB
    @Column(name = "reactive_power_c")
    private Double reactivePowerC;//float NULLC
    private Double reactivePower;//float NULL总无功功率
    @Column(name = "power_factor_a")
    private Double powerFactorA;//float NULLa相功率因数
    @Column(name = "power_factor_b")
    private Double powerFactorB;//float NULLb相功率因数
    @Column(name = "power_factor_c")
    private Double powerFactorC;//float NULLc相功率因数
    private Double powerFactor;//float NULL总功率因数
    @Column(name = "apparent_power_a")
    private Double apparentPowerA;//float NULL
    @Column(name = "apparent_power_b")
    private Double apparentPowerB;//float NULL
    @Column(name = "apparent_power_c")
    private Double apparentPowerC;//float NULL
    private Double apparentPower;//float NOT NULL总视在功率
    private Double freq;//float NULL频率
    @Column(name = "voltage_harm_a")
    private Double voltageHarmA;//float NULLA相电压谐波
    @Column(name = "voltage_harm_b")
    private Double voltageHarmB;//float NULLB相电压谐波
    @Column(name = "voltage_harm_c")
    private Double voltageHarmC;//float NULLC相电压谐波
    @Column(name = "current_harm_a")
    private Double currentHarmA;//float NULLA相电流谐波
    @Column(name = "current_harm_b")
    private Double currentHarmB;//float NULLB相电流谐波
    @Column(name = "current_harm_c")
    private Double currentHarmC;//float NULLC相电流谐波
    @Column(name = "voltage_dist_a")
    private Double voltageDistA;//float NULLa相电压畸变率
    @Column(name = "voltage_dist_b")
    private Double voltageDistB;//float NULL
    @Column(name = "voltage_dist_c")
    private Double voltageDistC;//float NULL
    @Column(name = "current_dist_a")
    private Double currentDistA;//float NULLa相电流畸变率
    @Column(name = "current_dist_b")
    private Double currentDistB;//float NULL
    @Column(name = "current_dist_c")
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
