package com.morrth.libaryspringboot.entity;

import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

/**
 * @author morrth
 * @create 2021-04-24-14:40
 */
@Data
@Entity
@Table(name = "log")
public class LogEntity {
    @Id
    @Column(name = "logid")
    private int logid;
    @Column(name = "logdate")
    private Timestamp logdate;
    private String type;
    private String operation;
    private Long userid;
    private String username;
    //非数据库字段
    @Transient
    private String strdate;

}
