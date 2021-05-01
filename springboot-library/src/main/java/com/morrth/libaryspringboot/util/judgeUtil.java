package com.morrth.libaryspringboot.util;

import com.morrth.libaryspringboot.entity.User_accout;
import com.morrth.libaryspringboot.repository.UserAccoutRepository;
import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author morrth
 * @create 2021-03-30-17:38
 */
public class judgeUtil {
    @Autowired
    private static UserAccoutRepository userAccoutRepository;
    /**
     * 将中文转换为拼音（小写）
     * @param str
     * @return
     */
    public static String getPingYinLower(String str) {
        str = StringUtils.deleteWhitespace(str);  // 去除空格
        char[] p1 = null;
        p1 = str.toCharArray();
        String[] p2 = new String[p1.length];
        HanyuPinyinOutputFormat p3 = new HanyuPinyinOutputFormat();
        p3.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        p3.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        p3.setVCharType(HanyuPinyinVCharType.WITH_V);
        String p4 = "";
        int t0 = p1.length;
        try {
            for (int i = 0; i < t0; i++) {
                if (java.lang.Character.toString(p1[i]).matches("[\\u4E00-\\u9FA5]+")) {
                    p2 = PinyinHelper.toHanyuPinyinStringArray(p1[i], p3);
                    p4 += p2[0];
                } else
                    p4 += java.lang.Character.toString(p1[i]);
            }
            return p4;
        } catch (BadHanyuPinyinOutputFormatCombination e1) {
            e1.printStackTrace();
        }
        return p4;
    }

    //去除字符串中的标点
    public static String format(String s) {
        String str = s.replaceAll("[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……& amp;*（）——+|{}【】‘；：”“’。，、？|-]", "");
        return str;
    }

    //正则判断是否包含
    public static String simple(String res, String... sarr) {
        res = getPingYinLower(res);
        for (String s : sarr) {
            if (Pattern.matches(".*" + getPingYinLower(s) + ".*", res)) {
                return s;
            }
        }
        return "";
    }

    //利用HashMap判断键值对是否同时包含
    private static String double2(String res, HashMap<String[], String[]> reg) {
        res = getPingYinLower(res);
        //遍历Map
        for (Map.Entry<String[], String[]> entry : reg.entrySet()) {
            String[] key = entry.getKey();
            String[] value = entry.getValue();
            for (String s1 : key) {
                if (res.contains(getPingYinLower(s1))) {
                    for (String s2 : value)
                        if (res.contains(getPingYinLower(s2))) {
                            return s1 + s2;
                        }
                }
            }
        }
        return null;
    }
    //去掉尾部不需要的字符
    private static String passstr(String res,String a,String no){
        String pattern = ".*" + a + "(.*[^"+no+"])",s="";
        Matcher m = Pattern.compile(pattern).matcher(res);
        if (m.find()) {
            s = m.group(1);
        }
        return s;
    }
    //字符处理判断区
    public static String strjudge(String res) {
        if (res.isEmpty()) {
            return "err";
        }
        String a;
        HashMap<String[], String[]> reg = new HashMap();
        res = format(res);
        //匹配
        a = simple(res, "查询", "查找", "寻找", "搜索");
        String s = "";
        if (!a.isEmpty()) {
            //正则匹配
            s=passstr(res,a,"图书籍");
            s=s.replaceAll("([所有全部])+","");
            return "findbook" + s;
        }
        a = simple(res, "打开","跳转至","返回");
        if (!a.isEmpty()&&!res.contains("音乐")) {
            //正则匹配
            s=passstr(res,a,"页面");
            if (!s.isEmpty()) return "openroute" + s;
        }
        a = simple(res, "关闭音乐", "停止音乐", "暂停音乐");
        if (!a.isEmpty()) {
            return "pausemusic";
        }
        a = simple(res, "批量删除");
        if (!a.isEmpty()) {
            return "delbooks";
        }
//        close
        a = simple(res, "不需要", "不用", "闭嘴", "关闭助手", "滚");
        if (!a.isEmpty()) {

            return "out";
        }
        //login
        a = simple(res, "游客");
        if (!a.isEmpty()) {
            if (!simple(res, "登录").isEmpty())
            return "normallogin";
        }
        //匹配
        String[] a1 = {"音乐"};
        String[] a2 = {"打开", "启动", "播放"};
        reg.put(a1, a2);
        a1 = new String[]{"背景","主题","模式"};
        a2 = new String[]{"灰暗","明亮","白天","夜间","换", "改变", "更改"};
        reg.put(a1, a2);
        a1 = new String[]{"退出"};
        a2 = new String[]{"登录", "用户", "系统"};
        reg.put(a1, a2);
//        a1 = new String[]{"登录"};
//        a2 = new String[]{"游客"};
//        reg.put(a1, a2);
        a1 = new String[]{"排序"};
        a2 = new String[]{"日期", "页数", "种类","编号"};
        reg.put(a1, a2);
        a1 = new String[]{"关闭"};
        a2 = new String[]{"当前页面", "所有页面"};
        reg.put(a1, a2);
        String rr = double2(res, reg);
        if (rr != null) {
            String r1=rr.substring(0,2).intern();
            String r2=rr.substring(2).intern();
            System.out.println("r1"+r1+"r2"+r2);
            if (r1.equals("音乐")) {
                return "playmusic";
            }
            if (r1.equals("关闭")){
                if(r2.equals("当前页面")){
                    return "closepage";
                }
                return "closeallpage";
            }
            if (r1=="背景"||r1=="模式"||r1=="主题") {
                if (r2=="灰暗"||r2=="夜间") {
                    return "changebg0";
                }
                if (r2=="明亮"||r2=="白天") {
                    return "changebg1";
                }
                return "changebg2";
            }
            if (r1=="退出") {
                return "outlogin";
            }
            if (r1=="排序"){
                String sort="sortid";
                if (r2=="日期") {
                    sort="sortdate";
                }
                else if (r2=="页数"){
                    sort="sortpages";
                }
                else if (r2=="种类"){
                    sort="sortbookcase";
                }
                //正则匹配
                String pattern = ".*" + r2 + "([降减升增])";
                // 创建 Pattern 对象
                Pattern r = Pattern.compile(pattern);
                // 现在创建 matcher 对象
                Matcher m = r.matcher(res);
                if (m.find()) {
                    String py=m.group(1);
                    System.out.println(py);
                    if(py.contains("升")||py.contains("增")){
                        return sort+1;
                    }
                    return sort+0;
                }
                return sort+2;
            }

        }
        return "error";
    }
//语音合成发送以及发送相应数据
    public static List<Object> TTS(String r, Object ...arr) {
        String ok = "好的,已收到。", again = "请问还需要帮助吗";
        Object s2=new Object();
        List<String> s1=new ArrayList<>();
        s2=0;
        if (r.length()>=9&&r.substring(0,8).equals("changebg")){
            s2=r.substring(8);
            r="changebg";
        }
        switch (r) {
            case "normallogin":
                s1.add(ok +"已使用游客账号登录,"+ again);
                s1.add("当前已有账号登录，请先退出登录,"+again);
                s2 = arr[0];
                break;
            case "outlogin":
                s1.add(ok +"现已退出登录，"+ again);
                break;
            case "error":
                s1.add("对不起，我没听清能再说一遍吗");
                break;
            case "err":
                s1.add("未接收到音频,助手已自动退出,需要请重新开启");
                break;
            case "out":
                s1.add( ok + "助手已关闭,需要请重新开启");
                break;
            case "findbook":
                s1.add(ok + again);
                s2=arr[0];
                break;
            default:
                s1.add(ok + again);
                break;
        }
        List<Object> list = new ArrayList();
        list.add(r);   //命令字符
        list.add(s1);  //需要语音合成的中文字符
//        list.add(b64); //语音合成后的base64字符
        list.add(s2);  //额外参数
//        if (arr.length>0) list.addAll(Arrays.asList(arr));
        return list;
    }
}
