package com.morrth.libaryspringboot.repository;


import com.morrth.libaryspringboot.entity.User_accout;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author morrth
 * @create 2021-04-03-16:09
 */
public interface UserAccoutRepository extends JpaRepository<User_accout,Long> {
    @Query(value = "select  new User_accout(u.userid,u.avatar,u.authority,u.name,u.lasttime) FROM User_accout u where u.userid not in (0)")
    List<User_accout> findallmes();
    @Query(value = "select u.name from User_accout u where u.userid=?1")
    String findnameByUserid(Long l);
}
