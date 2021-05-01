package com.morrth.libaryspringboot.repository;


import com.morrth.libaryspringboot.entity.Book;
import javafx.scene.Camera;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

//Spring Data JPA框架
public interface BookRepository extends JpaRepository<Book,String> {

    List<Book> findAllByBookcase(String name);

    List<Book> findAllByPublish(String name);

    Integer countAllByBookcase(String bookcase);

    Integer countAllByPublish(String publish);

    @Query(value="select b from Book b where (b.id like %?1% or b.name like %?1% or b.author like %?1%) and (b.bookcase like %?2% or b.publish like %?2%)")
    Page<Book> findAllByioaon(String seleck,String bp, Pageable pageable);

    // 注意更新和删除是需要加事务的， 并且要加上 @Modify的注解
    @Modifying
    @Transactional
    @Query("delete from Book b where b.id in (?1)")
    void deleteBatch(String ...id);

    @Query("SELECT b.bookcase FROM Book b ")
    Set<String> findbookcase();

    @Query("SELECT b.publish FROM Book b ")
    Set<String> findpublish();

    @Query(value = "SELECT count(b.publish) FROM Book b  ")
    Book findByName(@Param("name") String name);
}
