package com.morrth.libaryspringboot.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.time.LocalDate;


/**
 * @author morrth
 * @create 2021-04-03-15:51
 */
@Entity
@Data
public class User_accout implements Serializable {
    private static final long serialVersionUID = 4L;
    @Id
    private Long userid;

    private String password;

    private String voiceWord;
    //懒加载
    @Lob
    @Basic(fetch = FetchType.LAZY)
    private byte[] avatar;

    private Integer authority;

    private String name;

    private Date lasttime;
    @Column(insertable = false)
    private Integer bg;
    @Column(insertable = false)
    private float volume;
    @Column(insertable = false)
    private float pitch;
    @Column(insertable = false)
    private double speed;
    @Column(insertable = false)
    private Integer recordtime;
    //非数据库字段
    @Transient
    private String lastdate;
    @Transient
    private String token;

    public User_accout(Long userid, byte[] avatar, Integer authority, String name, Date lasttime) {
        this.userid = userid;
        this.avatar = avatar;
        this.authority = authority;
        this.name = name;
        this.lasttime = lasttime;
    }

    public User_accout() {
    }
}
