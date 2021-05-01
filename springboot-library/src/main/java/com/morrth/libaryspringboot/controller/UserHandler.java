package com.morrth.libaryspringboot.controller;


import com.morrth.libaryspringboot.entity.Global;
import com.morrth.libaryspringboot.entity.Inlogmes;
import com.morrth.libaryspringboot.entity.User_accout;
import com.morrth.libaryspringboot.repository.InlogmesRepository;
import com.morrth.libaryspringboot.repository.UserAccoutRepository;
import com.morrth.libaryspringboot.service.UserService;
import com.morrth.libaryspringboot.util.JwtUitl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @author morrth
 * @create 2021-04-03-16:11
 */
@RestController
@RequestMapping()
public class UserHandler {
    @Autowired
    private UserAccoutRepository userAccoutRepository;
    @Autowired
    private com.morrth.libaryspringboot.repository.GlobalRepository GlobalRepository;
    @Autowired
    private InlogmesRepository inlogmesRepository;
    @Autowired
    private JwtUitl jwtUitl;
    @Autowired
    private UserService userService;

    private SimpleDateFormat format1 = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒");;
    @PostMapping("/login")
    public User_accout login(@RequestParam(value = "userid") Long userid, @RequestParam(value = "password") String password) {
        if (userAccoutRepository.findById(userid).isPresent()) {
            User_accout result = userAccoutRepository.findById(userid).get();
            //记录上次登录的时间
            String time = "无";
            if (result.getLasttime() != null) {
                Date lasttime = result.getLasttime();
                time = format1.format(lasttime.getTime());
                System.out.println("格式化结果1：" + time);
            }
            if (result != null && password.equals(result.getPassword())) {//需要intern方法转为直接引用本地常量池再判断是否一样
                result.setLasttime(new Date());
                userAccoutRepository.save(result);
                //返回格式化后的日期数据
                result.setLastdate(time);
                //登入日志
                Inlogmes inlogmes=new Inlogmes(result.getUserid(), format1.format(result.getLasttime().getTime()),result.getName());
                inlogmesRepository.save(inlogmes);
                //用户访问量
                Global global = GlobalRepository.findById(1).get();
                Long flow = global.getUserflow() + 1;
                global.setUserflow(flow);
                GlobalRepository.save(global);
                //token生成
                result.setToken(jwtUitl.createToken(String.valueOf(result.getUserid()),result.getName()));
                result.setPassword(null);
                return result;
            }
        }
        return null;
    }
    //游客登录
    @GetMapping("/nllogin")
    public User_accout normallogin() {
        return userService.nlmorlogin();
    }

    //获取用户列表
    @GetMapping("/userall")
    public List<User_accout> userall() {
        return userAccoutRepository.findallmes();
    }

    //用户访问量
    @GetMapping("/userflow")
    public Long userflow() {
        return GlobalRepository.findById(1).get().getUserflow();
    }

    //用户注册
    @PostMapping("/register")
    public String register(@RequestBody User_accout accout) {
        Long uid = accout.getUserid();
        accout.setAuthority(1);
        if (userAccoutRepository.findById(uid).isPresent()) {
            return "error";
        } else {
            userAccoutRepository.save(accout);
            return "success";
        }
    }
    //用户添加
    @PostMapping("/adduser")
    public String adduser(@RequestBody User_accout accout) {
        Long uid = accout.getUserid();
        if (userAccoutRepository.findById(uid).isPresent()) {
            return "error";
        } else {
            userAccoutRepository.save(accout);
            return "success";
        }
    }
    //更换头像
    @PostMapping("/avatarcg")
    public User_accout avatarcg(@RequestPart("file") MultipartFile avatarfile,@RequestParam(value = "userid") Long userid) throws IOException {
        User_accout accout = userAccoutRepository.findById(userid).get();
        accout.setAvatar(avatarfile.getBytes());
        userAccoutRepository.save(accout);
        accout.setPassword(null);
        return accout;
    }
    //保存语音设置
    @PostMapping("/voiceset")
    public User_accout voiceset(@RequestParam(value = "userid") Long userid,@RequestParam(value = "volume") float volume,@RequestParam(value = "speed") double speed,@RequestParam(value = "pitch") float pitch){
        User_accout accout = userAccoutRepository.findById(userid).get();
        accout.setSpeed(speed);
        accout.setPitch(pitch);
        accout.setVolume(volume);
        userAccoutRepository.save(accout);
        accout.setPassword(null);
        return accout;
    }
    //用户名更改
    @PostMapping("/nameupdate")
    public User_accout updatename(@RequestParam(value = "userid") Long userid,@RequestParam(value = "name") String name){
        if (userid!=0){
            User_accout accout = userAccoutRepository.findById(userid).get();
            accout.setName(name);
            //token重新生成
            accout.setToken(jwtUitl.createToken(String.valueOf(accout.getUserid()),accout.getName()));
            userAccoutRepository.save(accout);
            return accout;
        }
        return userAccoutRepository.findById(userid).get();
    }
    //录音时长更改
    @PostMapping("/recortimeupdate")
    public User_accout updaterecord(@RequestParam(value = "userid") Long userid,@RequestParam(value = "recortime") Integer recortime){
        User_accout accout = userAccoutRepository.findById(userid).get();
        accout.setRecordtime(recortime);
        //token重新生成
        accout.setToken(jwtUitl.createToken(String.valueOf(accout.getUserid()),accout.getName()));
        userAccoutRepository.save(accout);
        return accout;

    }
}
