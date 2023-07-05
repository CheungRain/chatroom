package com.zy.chatroom.interceptor;

import com.auth0.jwt.JWT;
import com.auth0.jwt.exceptions.AlgorithmMismatchException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import com.zy.chatroom.util.JWTUtils;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

@Slf4j
@Component
public class JWTInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //如果请求为 OPTIONS 请求，则返回 true,否则需要通过jwt验证

        if (HttpMethod.OPTIONS.toString().equals(request.getMethod())){
            System.out.println("OPTIONS请求，放行");
            return true;
        }
        String token = request.getHeader("Authorization");
        System.out.println("请求头中的token: "+token);
        Date now = new Date(System.currentTimeMillis());
        Date end = JWT.decode(token).getExpiresAt();
        long nowMS = now.getTime();
        long endMS = end.getTime();
        if(nowMS>endMS){
            return false;
        }
        if(StringUtils.isEmpty(token)){
            throw new Exception("token不能为空");
        }
        try {
            JWTUtils.verifyToken(token);
        } catch (SignatureVerificationException e) {
            log.error("无效签名！ 错误 ->", e);
            return false;
        } catch (TokenExpiredException e) {
            log.error("token过期！ 错误 ->", e);
            System.out.println("过期");
            return false;
        } catch (AlgorithmMismatchException e) {
            log.error("token算法不一致！ 错误 ->", e);
            return false;
        } catch (Exception e) {
            log.error("token无效！ 错误 ->", e);
            return false;
        }
        return true;
    }
}
