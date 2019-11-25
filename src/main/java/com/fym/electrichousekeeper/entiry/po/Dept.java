package com.fym.electrichousekeeper.entiry.po;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "sys_dept")
@Data
public class Dept {
    @Id
    @Column
    private Integer id;

    @Column
    private String name;

    @Column
    private String code;

    @Column
    private Integer parentId;

}

