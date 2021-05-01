package com.morrth.libaryspringboot.service.impl;

import com.morrth.libaryspringboot.repository.BookRepository;
import com.morrth.libaryspringboot.repository.LogEntityRepository;
import com.morrth.libaryspringboot.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author morrth
 * @since 2021-04-01
 */
@Service
public class BookServiceImpl  implements BookService {
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private LogEntityRepository logEntityRepository;
//    开启事务
    @Transactional
    @Override
    public void deleteBatch(String ...bList) {
        // 批量删除方法， 是通过自定义JPQL语句进行删除，使用的是 where stuId in （）的操作
        bookRepository.deleteBatch(bList);
    }
    //查找对应种类或出版社图书数量
    public Map<String,Integer> coutcase(Integer i){
        Map<String,Integer> map=new HashMap<>();
        if (i==0){
            Set<String> set = bookRepository.findbookcase();
            for (String s:set){
                map.put(s,bookRepository.countAllByBookcase(s));
            }
        }else{
            Set<String> set = bookRepository.findpublish();
            for (String s:set){
                map.put(s,bookRepository.countAllByPublish(s));
            }
        }
        return map;
    }
}
