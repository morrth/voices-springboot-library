package com.morrth.libaryspringboot.config;

/**
 * @author morrth
 * @create 2021-04-22-18:22
 */

import com.morrth.libaryspringboot.util.JwtUitl;
import io.jsonwebtoken.Claims;
import org.apache.ibatis.annotations.ResultType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.Max;
import java.io.PrintWriter;
import java.util.List;

/**
 * 验证token
 */
@Component
public class TokenInterceptor implements HandlerInterceptor {

    /**
     * 简化获取token数据的代码编写（判断是否登录）
     *  通过request获取请求token信息
     */
    @Autowired
    private JwtUitl jwtUitl;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        //在拦截器中设置允许跨域    解决跨域和拦截器冲突
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Headers","*");
        response.setHeader("Access-Control-Allow-Methods","*");
        response.setHeader("Access-Control-Allow-Credentials","true");
        response.setHeader("Access-Control-Max-Age","3600");
        // 1.通过request获取请求token信息
        String authorization = request.getHeader("Authorization");
        //给前端发送响应头数据
        response.setHeader("mes","1");
        response.setHeader("Access-Control-Expose-Headers","mes"); //暴露timeout响应头

        //判断请求头信息是否为空，或者是否token开头
        if(authorization!=null && authorization.startsWith("token")) {
            //获取token数据
            String token = authorization.replace("token ","");
            //通过工具类解析token获取状态
            List<Integer> i = jwtUitl.verify(token);
            if(i.get(0) ==1) {
                String path=request.getServletPath();
                boolean b=path.equals("/adduser")||path.equals("/book/save")||path.equals("/book/update")||path.equals("/book/del_id")||path.startsWith("/book/deleteById");
                //权限管理
                if (b&&i.get(1)!=0){
                    response.setHeader("mes","denied");
                    response.setStatus(999);
                    return false;
                }
                return true;
            }else {
                response.setHeader("mes","overdue");
                response.setStatus(998);
                return false;
            }
        }
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
        // System.out.println("处理请求完成后视图渲染之前的处理操作");
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
        // System.out.println("视图渲染之后的操作");
    }
}
