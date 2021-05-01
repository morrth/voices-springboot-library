package com.morrth.libaryspringboot.repository;


import com.morrth.libaryspringboot.entity.LogEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.sql.Timestamp;
import java.util.List;

/**
 * @author morrth
 * @create 2021-04-22-14:30
 */
public interface LogEntityRepository extends JpaRepository<LogEntity, Long>, JpaSpecificationExecutor<LogEntity> {
    List<LogEntity> findAllByLogdateLike(Timestamp t);
}
