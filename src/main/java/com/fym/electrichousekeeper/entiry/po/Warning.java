package com.fym.electrichousekeeper.entiry.po;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "t_warning")
@Data
public class Warning {

    public static final int STATUS_CREATED = 1;

    public static final int STATUS_HANDLING = 2;

    public static final int STATUS_HANDLED = 3;

    @Id
    private Integer id;

    private String warningType;

    private String deviceCode;

    private Date time;

    private Integer status;

    private Integer dataId;
}
