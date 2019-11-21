package com.fym.electrichousekeeper.entiry;

import javax.persistence.Column;

/**
 * 基本数据：
 * 包括：7路温度、三相电压电流、有功无功功率、视在功率、功率因数、电压电流谐波
 */
public class BaseData {
    @Column(name = "temper_ha")
    private Double temperHA;
    @Column(name = "temper_hb")
    private Double temperHB;
    @Column(name = "temper_hc")
    private Double temperHC;
    @Column(name = "temper_la")
    private Double temperLA;
    @Column(name = "temper_lb")
    private Double temperLB;
    @Column(name = "temper_lc")
    private Double temperLC;
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
