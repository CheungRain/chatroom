package com.zy.chatroom.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.zy.chatroom.entity.User;


import java.util.Date;
import java.util.HashMap;

public class JWTUtils {

    private static final long EXPIRE_TIME = 60*60*1000;//ms为单
    private static final String secret = "zySecretKey";
    public static String getToken(User user){
        //过期时间
        Date begin = new Date(System.currentTimeMillis());
        Date end = new Date(System.currentTimeMillis()+EXPIRE_TIME);
        //私钥及加密算法
        Algorithm algorithm = Algorithm.HMAC256(secret);
        //设置头信息
        HashMap<String, Object> headers = new HashMap<>(2);
        headers.put("typ", "JWT");
        headers.put("alg", "HS256");
        System.out.println(begin+" "+end);
        //附带username和userID生成签名
        return JWT.create()
                .withHeader(headers)
                .withClaim("uid",user.getUid()).withClaim("account",user.getAccount())
                .withIssuedAt(begin)
                .withExpiresAt(end)
                .sign(algorithm);
    }


    public static boolean verifyToken(String token){
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            JWTVerifier verifier = JWT.require(algorithm).build();
            verifier.verify(token);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        } catch (JWTVerificationException e) {
            return false;
        }

    }

    public static void main(String[] args){
        User user = new User();
        user.setUid(1);
        user.setAccount("john");
        user.setPassword("123456");
        String token = getToken(user);
        System.out.println(token);

        Integer uid = JWT.decode(token).getClaim("uid").asInt();
        System.out.println(verifyToken(token));
        System.out.println(uid);

        //   JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256("111")).build();
        //   System.out.println(verity("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJsb2dpbk5hbWUiOiJiYWlsZSIsImV4cCI6MTU4NzIxOTUwOSwidXNlcklkIjoiMyJ9.GKfFpNA_PtiR1op0mGp2LL8L26Ig7hiVX1o3KEO8SHY","111"));
    }
}
