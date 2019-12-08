package com.fym.electrichousekeeper.entiry.po;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Table(name = "t_device")
public class Device{
    public static final int RUNNING = 1;

    public static final int NOT_RUNNING = 0;

    @Id
    @Column
    private String code;

    @Column
    private String name;

    @Column
    private String type;

    @Column
    public Double capacity;

    @Column
    private Date updateTime;

    private Date infoUpdateTime;

    @Column
    private Integer status;

    @Column
    private Integer deptId;

    private String dept;

    private String lng;

    private String lat;

    @Column(name = "temper_ha")
    private Double temperHa;
    @Column(name = "temper_hb")
    private Double temperHb;
    @Column(name = "temper_hc")
    private Double temperHc;
    @Column(name = "temper_la")
    private Double temperLa;
    @Column(name = "temper_lb")
    private Double temperLb;
    @Column(name = "temper_lc")
    private Double temperLc;
    @Column(name = "temper_n")
    private Double temperN;

    /**电压电流*/
    @Column(name = "current_a")
    private Double currentA;
    @Column(name = "current_b")
    private Double currentB;
    @Column(name = "current_c")
    private Double currentC;
    @Column(name = "voltage_a")
    private Double voltageA;
    @Column(name = "voltage_b")
    private Double voltageB;
    @Column(name = "voltage_c")
    private Double voltageC;

    @Column
    private  Double freq;

    /**有功功率*/
    @Column(name = "active_power_a")
    private Double activePowerA;
    @Column(name = "active_power_b")
    private Double activePowerB;
    @Column(name = "active_power_c")
    private Double activePowerC;
    @Column(name = "active_power")
    private Double activePower;

    /**无功功率*/
    @Column(name = "reactive_power_a")
    private Double reactivePowerA;
    @Column(name = "reactive_power_b")
    private Double reactivePowerB;
    @Column(name = "reactive_power_c")
    private Double reactivePowerC;
    @Column(name = "reactive_power")
    private Double reactivePower;

    /**视在功率*/
    @Column(name = "apparent_power_a")
    private Double apparentPowerA;
    @Column(name = "apparent_power_b")
    private Double apparentPowerB;
    @Column(name = "apparent_power_c")
    private Double apparentPowerC;
    @Column(name = "apparent_power")
    private Double apparentPower;

    /**功率因数*/
    @Column(name = "power_factor_a")
    private Double powerFactorA;
    @Column(name = "power_factor_b")
    private Double powerFactorB;
    @Column(name = "power_factor_c")
    private Double powerFactorC;
    @Column(name = "power_factor")
    private Double powerFactor;

    /**电流电压谐波*/
    @Column(name = "current_harm_a")
    private Double currentHarmA;
    @Column(name = "current_harm_b")
    private Double currentHarmB;
    @Column(name = "current_harm_c")
    private Double currentHarmC;
    @Column(name = "voltage_harm_a")
    private Double voltageHarmA;
    @Column(name = "voltage_harm_b")
    private Double voltageHarmB;
    @Column(name = "voltage_harm_c")
    private Double voltageHarmC;
}
