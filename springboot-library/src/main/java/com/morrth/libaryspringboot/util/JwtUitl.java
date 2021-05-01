package com.morrth.libaryspringboot.util;

import com.morrth.libaryspringboot.entity.User_accout;
import com.morrth.libaryspringboot.repository.UserAccoutRepository;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

/**token生成
 * @author morrth
 * @create 2021-04-22-16:45
 */

@Component
public class JwtUitl {

    @Autowired
    private UserAccoutRepository userAccoutRepository;
    /**
     * 过期时间5分钟
     */
    private static final long EXPIRE_TIME=10*60*1000;
    /**
     * 加密密钥
     */
    private static final String KEY = "morrthzjj";

    /**
     * 生成token
     * @param id    用户id
     * @param userName  用户名
     * @return
     */
    public String createToken(String id,String userName){
        Map<String,Object> header = new HashMap();
        header.put("typ","JWT");
        header.put("alg","HS256");
        //setID:用户ID
        //setExpiration:token过期时间  当前时间+有效时间
        //setSubject:用户名
        //setIssuedAt:token创建时间
        //signWith:加密方式
        JwtBuilder builder = Jwts.builder().setHeader(header)
                .setId(id)
                .setExpiration(new Date(System.currentTimeMillis()+EXPIRE_TIME))
                .setSubject(userName)
                .setIssuedAt(new Date())
                .signWith(SignatureAlgorithm.HS256,KEY);
        return builder.compact();
    }

    /**
     * 验证token是否有效
     * @param token  请求头中携带的token
     * @return  token验证结果  2-token过期；1-token认证通过；0-token认证失败  用户权限 0管理员  1普通用户
     */
    public List<Integer> verify(String token){
        Claims claims = null;
        ArrayList<Integer> integers = new ArrayList<>();
        try {
            //token过期后，会抛出ExpiredJwtException 异常，通过这个来判定token过期，
            claims = Jwts.parser().setSigningKey(KEY).parseClaimsJws(token).getBody();
        }catch (ExpiredJwtException e){
            integers.add(2);
            return integers;
        }
        //从token中获取用户id，查询该Id的用户是否存在，存在则token验证通过
        String id = claims.getId();
        Optional<User_accout> user = userAccoutRepository.findById(Long.valueOf(id));
        User_accout accout = user.get();

        if(user.isPresent()){
            integers.add(1);
            integers.add(accout.getAuthority());
            integers.add(Integer.valueOf(id));
        }else{
            integers.add(0);
        }
        return integers;
    }
}
