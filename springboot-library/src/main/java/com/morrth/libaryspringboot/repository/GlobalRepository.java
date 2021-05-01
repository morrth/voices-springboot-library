package com.morrth.libaryspringboot.repository;


import com.morrth.libaryspringboot.entity.Book;
import com.morrth.libaryspringboot.entity.Global;
import org.springframework.data.jpa.repository.JpaRepository;

//Spring Data JPA框架
public interface GlobalRepository extends JpaRepository<Global,Integer> {

}
