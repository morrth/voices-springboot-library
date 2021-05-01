package com.morrth.libaryspringboot.controller;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.morrth.libaryspringboot.entity.Book;
import com.morrth.libaryspringboot.entity.User_accout;
import com.morrth.libaryspringboot.repository.BookRepository;
import com.morrth.libaryspringboot.repository.UserAccoutRepository;
import com.morrth.libaryspringboot.service.UserService;
import com.morrth.libaryspringboot.util.SpeechUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import static com.morrth.libaryspringboot.util.judgeUtil.*;

/**
 * @author morrth
 * @create 2021-03-24-18:15
 */
@RestController
@RequestMapping("/voices")
public class voiceshandle {
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private UserAccoutRepository userAccoutRepository;
    @Autowired
    private UserService userService;
    //录音上传识别接口
    @PostMapping("/upload")
    public Object unload(@RequestPart("file") MultipartFile bfile) throws IOException {
        if (!bfile.isEmpty()) {
            //语音识别结果
            String str = SpeechUtil.SpeechRecognitiondata(bfile.getBytes(), "wav");
            String content = str;
            List<Object> RLIST=new ArrayList<>();
            //获取result对应的字符串并去除特殊符号 将json字符串转化成json对象
            JsonObject jo = JsonParser.parseString(content).getAsJsonObject();
            if (jo.has("result")) {
                String res = jo.get("result").getAsString();
                String r = strjudge(res);
                System.out.println(r);
                //判断字符请求
                if (r.contains("findbook")) {
                    String fb=r.substring(8);
                    RLIST=TTS("findbook",fb);
                    RLIST.add(res);
                }else if("normallogin"==r.intern()){
                    RLIST=TTS(r,userService.nlmorlogin());
                    RLIST.add(res);
                }else {
                    RLIST=TTS(r);
                    RLIST.add(res);
                }
                return RLIST;
            }
            RLIST=TTS("err");
            RLIST.add("err");
            return RLIST;
        }
        System.out.println("没有音频");
        return "err";
    }
//语音合成接口
    @GetMapping("/TTS")
    public String open(@RequestParam("word") String word,@RequestParam(value = "volume") Float volume, @RequestParam(value = "pitch") Float pitch,@RequestParam(value = "speed") Float speed) {
        String b64="";
        b64=SpeechUtil.ToSpeech(word,Integer.parseInt(String.valueOf(speed)),Integer.parseInt(String.valueOf(volume)));
        return b64;
    }

}
