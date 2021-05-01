package com.morrth.libaryspringboot.entity;

import lombok.Data;
import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;
import java.util.Iterator;
import java.util.Objects;

/**
 * @author morrth
 * @create 2021-04-13-15:50
 */
@Entity
@Data
public class Inlogmes implements Serializable {
    private static final long serialVersionUID = 3L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long inlogid;

    private Long uid;

    private String date;

    private String name;
    @Transient
    private boolean hasChildren=false;
    public Inlogmes(Long uid, String date, String name) {
        this.uid = uid;
        this.date = date;
        this.name = name;
    }

    public Inlogmes() {
    }
    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if(o instanceof Inlogmes){
            Inlogmes i2 = (Inlogmes)o;
            this.hasChildren=this.getDate().substring(0,11).equals(i2.getDate().substring(0,11));
            if (this.hasChildren)((Inlogmes) o).hasChildren=true;
            return this.hasChildren;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(date.substring(0,11));
    }
}
