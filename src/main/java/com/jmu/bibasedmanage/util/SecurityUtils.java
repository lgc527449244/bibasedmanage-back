package com.jmu.bibasedmanage.util;

import com.jmu.bibasedmanage.exception.BusinessException;
import com.jmu.bibasedmanage.vo.CurrentUser;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * 安全工具类
 * Created by ljc on 2018/1/6.
 */
public abstract class SecurityUtils {

    public static final String USER_ATT = "__user__";

    private SecurityUtils() {
    }

    /**
     * 获取当前登录的用户信息
     * @return
     */
    public static CurrentUser getCurrentUser(){
        CurrentUser user = (CurrentUser) getSession().getAttribute(USER_ATT);
        if (user == null){
            throw new BusinessException("用户未登录");
        }
        return user;
    }

    /**
     * 处理用户登录
     * @param user
     */
    public static void processUserLogin(CurrentUser user){
        getSession().setAttribute(USER_ATT, user);
    }

    /**
     * 处理用户注销
     */
    public static void processUserLogout(){
        getSession().removeAttribute(USER_ATT);
    }

    public static HttpServletRequest getRequest() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    }
    public static HttpSession getSession(){
        return getRequest().getSession();
    }
}
