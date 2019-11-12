package com.fym.electrichousekeeper.entiry;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Data
@Table(name = "auth_user")
public class User {

    @Column
    @Id
    private Long id;
    @Column(unique = true,nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

}
