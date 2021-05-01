package com.morrth.libaryspringboot.service.impl;

import com.morrth.libaryspringboot.entity.User_accout;
import com.morrth.libaryspringboot.repository.UserAccoutRepository;
import com.morrth.libaryspringboot.service.UserService;
import com.morrth.libaryspringboot.util.JwtUitl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author morrth
 * @create 2021-04-26-20:46
 */
@Service
public class UserServiceimpl implements UserService {
    @Autowired
    private UserAccoutRepository userAccoutRepository;
    @Autowired
    private JwtUitl jwtUitl;
    //游客登录
    public User_accout nlmorlogin(){
        User_accout accout = userAccoutRepository.findById(Long.valueOf(0)).get();
        //token生成
        accout.setToken(jwtUitl.createToken("0",accout.getName()));
        accout.setPassword(null);
        accout.setLastdate("游客用户无法查看");
        return accout;
    }
}
