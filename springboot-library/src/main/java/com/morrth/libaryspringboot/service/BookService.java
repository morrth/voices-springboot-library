package com.morrth.libaryspringboot.service;

import com.morrth.libaryspringboot.entity.Book;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author morrth
 * @since 2021-04-01
 */
public interface BookService {
    public void deleteBatch(String ...bList);
}
