package com.fym.electrichousekeeper.entiry.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@Table(name = "t_threshold")
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Threshold {
    public final static String CODE_CURRENT_OVER_LOAD = "current_over_load";
    public final static String CODE_OVER_TEMPERATURE = "over_temperature";
    public final static String CODE_LOW_VOLTAGE = "low_voltage";
    public final static String CODE_PHASE_UNBALANCE = "phase_unbalance";
    public final static String CODE_ELECTRIC_INCREASE = "electric_increase";
    public final static String CODE_HARMONIC_OVER_LIMIT = "harmonic_over_limit";
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String typeCode;

    private Integer value;

    private Boolean isGlobal;

    private String deviceCode;

    private Date updateTime;

}
