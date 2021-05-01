package com.morrth.libaryspringboot.controller;

import com.morrth.libaryspringboot.entity.Inlogmes;
import com.morrth.libaryspringboot.entity.LogEntity;
import com.morrth.libaryspringboot.repository.InlogmesRepository;
import com.morrth.libaryspringboot.repository.LogEntityRepository;
import com.morrth.libaryspringboot.repository.UserAccoutRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author morrth
 * @create 2021-03-24-18:15
 */
@RestController
@RequestMapping("/log")
public class Logmeshandle {
    @Autowired
    private InlogmesRepository inlogmesRepository;
    @Autowired
    private UserAccoutRepository userAccoutRepository;
    @Autowired
    private LogEntityRepository logEntityRepository;
    private SimpleDateFormat format1 = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒");
    @GetMapping("/inlogtable")
    public Object inlogtable(@RequestParam(value = "date", defaultValue = "") String date) {
        if (date.isEmpty()){
            List<Inlogmes> all = inlogmesRepository.findAll();
            Set<Inlogmes> res=new HashSet<Inlogmes>();
            for (Inlogmes i:all){
                res.add(i);
            }
            System.out.println(res);
            return res;
        }else {
            List<Inlogmes> allByDateLike = inlogmesRepository.findAllByDateLike("%" + date + "%");
            allByDateLike.remove(0);      //删除已展示的数据
            System.out.println(allByDateLike);
            return allByDateLike;
        }
    }
    @GetMapping("/logtable")
    public Object logtable(@RequestParam(value = "logdate", defaultValue = "") Long datetime) {
        if (datetime==null){
            List<LogEntity> all = logEntityRepository.findAll();
            Set<LogEntity> res=new HashSet<LogEntity>();
            for (LogEntity i:all){
                i.setStrdate(format1.format(i.getLogdate().getTime()));
                res.add(i);
            }
            System.out.println(res);
            return res;
        }else {
            List<LogEntity> allByDateLike = logEntityRepository.findAllByLogdateLike(new Timestamp(datetime));
            allByDateLike.remove(0);      //删除已展示的数据
            for (LogEntity i:allByDateLike){
                i.setStrdate(format1.format(i.getLogdate().getTime()));
            }
            return allByDateLike;
        }
    }
}
