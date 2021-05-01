package com.morrth.libaryspringboot.repository;

import com.morrth.libaryspringboot.entity.Book;
import com.morrth.libaryspringboot.entity.User_accout;
import com.morrth.libaryspringboot.service.BookService;
import com.morrth.libaryspringboot.service.impl.BookServiceImpl;
import com.morrth.libaryspringboot.util.judgeUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;

import static com.morrth.libaryspringboot.util.SpeechUtil.ToSpeech;
import static com.morrth.libaryspringboot.util.judgeUtil.TTS;
import static com.morrth.libaryspringboot.util.judgeUtil.simple;

@SpringBootTest
class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private UserAccoutRepository userAccoutRepository;
    @Autowired
    private BookServiceImpl BookServiceImpl;
    @Test
    void findAll(){
        Book book= new Book();
        book.setName("www");
        //将匹配对象封装成Example对象
        Example<Book> example =Example.of(book);

        book.setName(null);
        book.setAuthor("东野圭吾");
        example =Example.of(book);
        System.out.println(bookRepository.findAll(example));

    }
    @Test
    void t(){
        Book book= new Book();
        book.setName("www");
        //将匹配对象封装成Example对象
        Example<Book> example =Example.of(book);

        System.out.println(bookRepository.exists(example));
//        System.out.println(judgeUtil.strjudge("查找编号123").substring(8));

    }
    @Test
    void aVoid(){
    System.out.println(judgeUtil.strjudge("打开首页页"));
    }

    @Test
    void t1(){
//        User_accout accout=userAccoutRepository.findById(0).get();
//        System.out.println("res:"+simple("查找我们三","窝们仨"));
//        System.out.println("res:"+judgeUtil.strjudge("按照日期升序排序"));
//        System.out.println("res:"+judgeUtil.TTS("normallogin",accout));
//        System.out.println(userAccoutRepository.findById(111).get());

//        System.out.println(bookRepository.findAllByBookcase("文学"));
//        List<Integer> LString = new ArrayList<Integer>();
//        LString.add(121312);
//        LString.add(1123);
//        try {
//            BookServiceImpl.deleteBatch(LString);
//        }catch (Exception e){
//            System.out.println(e);
//        }
        Sort sort = Sort.by("id").ascending();

        PageRequest request = PageRequest.of(0, 3,sort);
        System.out.println(bookRepository.findAllByioaon("","",request).getContent());
        System.out.println(bookRepository.findAllByioaon("","",request).getTotalElements());
    }

}
