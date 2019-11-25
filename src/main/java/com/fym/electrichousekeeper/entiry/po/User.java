package com.fym.electrichousekeeper.entiry.po;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Data
@Table(name = "sys_user")
public class User {

    @Column
    @Id
    private Long id;
    @Column(unique = true,nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

}
