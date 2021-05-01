package com.morrth.libaryspringboot.controller;

import com.morrth.libaryspringboot.entity.Book;
import com.morrth.libaryspringboot.entity.LogEntity;
import com.morrth.libaryspringboot.repository.BookRepository;
import com.morrth.libaryspringboot.repository.LogEntityRepository;
import com.morrth.libaryspringboot.repository.UserAccoutRepository;
import com.morrth.libaryspringboot.service.impl.BookServiceImpl;
import com.morrth.libaryspringboot.util.JwtUitl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/book")
public class BookHandler {
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private BookServiceImpl bookService;
    @Autowired
    private LogEntityRepository logEntityRepository;
    @Autowired
    private UserAccoutRepository userAccoutRepository;
    @Autowired
    private JwtUitl jwtUitl;
    private SimpleDateFormat format1 = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒");
    @GetMapping("/findAll")
    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    @GetMapping("/cout/{case}")
    public Map<String,Integer> Coutnum(@PathVariable("case") Integer c) {
        // 0为查找图书种类数量  1为图书出版社数量
        return bookService.coutcase(c);
    }
    //带条件或无条件分页查询 与排序
    @GetMapping("/find/{page}/{size}")
    public Page<Book> find(@PathVariable("page") Integer page, @PathVariable("size") Integer size, @RequestParam(value = "select", defaultValue = "") String select,@RequestParam(value = "selectk", defaultValue = "") String selectk, @RequestParam(value = "sortname", defaultValue = "") String sortname, @RequestParam(value = "sortorder") int sortorder) {
        //分页排序部分
        Sort sort = Sort.by("id").ascending();
        if (!sortname.isEmpty()){
            if (sortorder==1)sort = Sort.by(sortname).ascending();
            else if(sortorder==0) sort = Sort.by(sortname).descending();
        }
        PageRequest request = PageRequest.of(page, size, sort);

        return bookRepository.findAllByioaon(select,selectk,request);

    }

    //添加图书
    @Transactional
    @PostMapping("/save")
    public String save(@RequestBody Book book,HttpServletRequest request) {
        book.setDate(LocalDate.now());
        Book result = bookRepository.save(book);
        if (result != null) {
            //日志录入
            List<Integer> list = jwtUitl.verify(request.getHeader("Authorization").replace("token ", ""));
            LogEntity log=new LogEntity();
            log.setType("添加");
            log.setLogdate(new Timestamp(new Date().getTime()));
            log.setUserid(Long.valueOf(list.get(2)));
            log.setOperation("添加了，编号为"+result.getId()+"的图书");
            log.setUsername(userAccoutRepository.findnameByUserid(Long.valueOf(list.get(2))));
            logEntityRepository.save(log);
            return "success";
        } else {
            return "error";
        }
    }

    @GetMapping("/findById/{id}")
    public Book findById(@PathVariable("id") String id) {
        return bookRepository.findById(id).get();
    }
    @GetMapping("/findcaseOrpublish/{n}")
    public List<Book> findcaseOrpublish(@RequestParam(value = "case") String str,@PathVariable Integer i) {
        if (i==0){
            return bookRepository.findAllByBookcase(str);
        }
        if (i==1){
            return bookRepository.findAllByPublish(str);
        }
        return null;
    }
    @DeleteMapping("/del_id")
    public void batch_del_b(@RequestParam("b_id") String b_id, HttpServletRequest request){
        // 接收包含Id的字符串，并将它分割成字符串数组
        String[] bList = b_id.split(",");
        System.out.println(request.getHeader("Authorization"));
        // 调用service层的批量删除函数
        bookService.deleteBatch(bList);
        //日志录入
        List<Integer> list = jwtUitl.verify(request.getHeader("Authorization").replace("token ", ""));
        LogEntity log=new LogEntity();
        log.setType("批量删除");
        log.setLogdate(new Timestamp(new Date().getTime()));
        log.setUserid(Long.valueOf(list.get(2)));
        log.setOperation("批量删除了，编号为"+b_id+"的图书");
        log.setUsername(userAccoutRepository.findnameByUserid(Long.valueOf(list.get(2))));
        logEntityRepository.save(log);
    }
    @Transactional
    @PutMapping("/update")
    public String update(HttpServletRequest request,@RequestBody Book book) {
        Book b= bookRepository.findById(book.getId()).get();
        String str="";
        if (!b.getName().equals(book.getName())){
            str=str+" 图书名从 "+b.getName()+" 修改为 "+book.getName()+",";
        }
        if (!b.getAuthor().equals(book.getAuthor())){
            str=str+"作者名从 "+b.getAuthor()+" 修改为 "+book.getAuthor()+",";
        }
        if (!b.getPages().equals(book.getPages())){
            str=str+"页数从 "+b.getPages()+" 修改为 "+book.getPages()+",";
        }
        if (!b.getBookcase().equals(book.getBookcase())){
            str=str+"种类从 "+b.getBookcase()+" 修改为 "+book.getBookcase()+",";
        }
        if (!b.getPublish().equals(book.getPublish())){
            str=str+"出版社从 "+b.getPublish()+" 修改为 "+book.getPublish()+",";
        }
        if (!b.getDate().equals(book.getDate())){
            str=str+"日期从 "+b.getDate()+" 修改为 "+book.getDate()+",";
        }
        System.out.println("str:"+str);
        str=str.substring(0,str.length()-1);
        if (!str.isEmpty()){
            Book result = bookRepository.save(book);
            List<Integer> list = jwtUitl.verify(request.getHeader("Authorization").replace("token ", ""));
            LogEntity log=new LogEntity();
            log.setType("修改");
            log.setLogdate(new Timestamp(new Date().getTime()));
            log.setUserid(Long.valueOf(list.get(2)));
            log.setOperation("修改了，编号为"+result.getId()+"的图书，"+str);
            log.setUsername(userAccoutRepository.findnameByUserid(Long.valueOf(list.get(2))));
            logEntityRepository.save(log);
            return "success";
        }
        return "error";
    }
    @Transactional
    @DeleteMapping("/deleteById/{id}")
    public void deleteById(HttpServletRequest request,@PathVariable("id") String id) {
        bookRepository.deleteById(id);
        List<Integer> list = jwtUitl.verify(request.getHeader("Authorization").replace("token ", ""));
        LogEntity log=new LogEntity();
        log.setType("删除");
        log.setLogdate(new Timestamp(new Date().getTime()));
        log.setUserid(Long.valueOf(list.get(2)));
        log.setOperation("删除了，编号为"+id+"的图书，");
        log.setUsername(userAccoutRepository.findnameByUserid(Long.valueOf(list.get(2))));
        logEntityRepository.save(log);
    }


}
