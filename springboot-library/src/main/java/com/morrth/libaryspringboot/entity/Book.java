package com.morrth.libaryspringboot.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * <p>
 *
 * </p>
 *
 * @author morrth
 * @since 2021-04-01
 */
@Entity
@Data
@EqualsAndHashCode(callSuper = false)
public class Book implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    private String name;

    private String author;

    private String publish;
    @Column(insertable = false)
    private Integer pages;

    private Float price;

    private Integer bookcaseid;
    @Column(insertable = false)
    private Integer stock;

    private LocalDate date;

    private String bookdetail;

    private String bookcase;


}
