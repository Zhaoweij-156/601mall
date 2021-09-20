package com.mall.interceptor;

import com.mall.annotation.PassToken;
import com.mall.annotation.UserLoginToken;
import com.mall.exception.NoLoginException;
import com.mall.exception.TokenVerifyException;
import com.mall.utils.JWTUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * 登录拦截器
 *
 * @author fjzkuroko
 * @version 1.0
 * @datetime 2021/7/25 15:25
 */
@Slf4j
public class LoginInterceptor implements HandlerInterceptor {

    private final static String TOKEN_HEADER = "token";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
                                                                                    throws NoLoginException, TokenVerifyException {
        // 允许跨域
        response.setHeader("Access-Control-Allow-Origin", "*");
        // 设置请求头允许的请求方式
        response.setHeader("Access-Control-Allow-Methods", "GET,POST,OPTIONS,PUT,DELETE");
        // 允许自定义请求头token、uuid(允许head跨域)
        response.setHeader("Access-Control-Allow-Headers", "token, uuid, Accept, Origin, X-Requested-With, Content-Type, Last-Modified");

        // 设置response编码以及contentType
        response.setCharacterEncoding("utf-8");
        response.setContentType("charset=utf-8");

        // 如果不是映射到方法直接通过
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }

        HandlerMethod handlerMethod = (HandlerMethod) handler;
        // 获取方法
        Method method = handlerMethod.getMethod();

        // 检查方法是否有PassToken注解，有直接跳过验证
        if (method.isAnnotationPresent(PassToken.class)) {
            PassToken passToken = method.getAnnotation(PassToken.class);
            if (passToken.required()) {
                return true;
            }
        }

        // 检查是否需要验证
        if (method.isAnnotationPresent(UserLoginToken.class)) {
            UserLoginToken userLoginToken = method.getAnnotation(UserLoginToken.class);
            if (userLoginToken.required()) {
                // 进行验证
                // 获取token
                String tokenHeader = request.getHeader(TOKEN_HEADER);

                // 如果没有token，则抛出异常
                if (null == tokenHeader) {
                    throw new NoLoginException("token获取为空:" + request.getRequestURI());
                }

                try {
                    // TODO 应该只更新过期时间就好，不需要重新验证
                    // 对token更新并验证
                    tokenHeader = JWTUtil.updateToken(tokenHeader);
                } catch (Exception e) {
                    log.error("token验证出现异常, token = {}", tokenHeader);
                    throw new TokenVerifyException("token验证异常：" + tokenHeader);
                }
                // 设置新的token
                response.setHeader(TOKEN_HEADER, tokenHeader);
                return true;
            }
        }

        return true;
    }
}
