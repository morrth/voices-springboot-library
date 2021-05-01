package com.morrth.libaryspringboot.repository;


import com.morrth.libaryspringboot.entity.Global;
import com.morrth.libaryspringboot.entity.Inlogmes;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

//Spring Data JPA框架
public interface InlogmesRepository extends JpaRepository<Inlogmes,Long> {
    List<Inlogmes> findAllByDateLike(String date);
}
