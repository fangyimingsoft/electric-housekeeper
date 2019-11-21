package com.fym.electrichousekeeper.entiry;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Data
@Table(name = "t_device")
public class Device extends BaseData{
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

    @Column
    private Integer status;
}
