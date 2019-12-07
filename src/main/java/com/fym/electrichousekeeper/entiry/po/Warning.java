package com.fym.electrichousekeeper.entiry.po;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "t_warning")
@Data
public class Warning {

    public static final int STATUS_CREATED = 0;

    public static final int STATUS_CONFIRM = 0;

    public static final int STATUS_PUBLISH = 2;

    public static final int STATUS_FINISHED = 3;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String warningType;

    private String deviceCode;

    private String deviceName;

    private Date time;

    private Integer status;

    private Integer dataId;
}
