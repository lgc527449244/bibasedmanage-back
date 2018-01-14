package com.jmu.bibasedmanage.controller;

import com.jmu.bibasedmanage.exception.BusinessException;
import com.jmu.bibasedmanage.service.UserService;
import com.jmu.bibasedmanage.util.ResponseUtil;
import com.jmu.bibasedmanage.vo.CurrentUser;
import com.jmu.bibasedmanage.vo.JsonResponse;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;

/**
 * Created by ljc on 2017/12/24.
 */
@Controller
@RequestMapping("/")
public class LoginController {

    private final Logger log = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private UserService userService;

    @RequestMapping(value = "login.html", method = RequestMethod.GET)
    public ModelAndView loginHtml(String msg){
        try {
            if(!StringUtils.isBlank(msg)){
                msg = new String(msg.getBytes("ISO8859-1"), "UTF-8");
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return new ModelAndView("login.html")
                .addObject("msg",msg);
    }

    @RequestMapping(value = "login", method = RequestMethod.POST)
    public ModelAndView login(String username, String password, String code, HttpServletRequest request){
        //验证验证码
        String sessionCode = (String) request.getSession().getAttribute("code");
        if(StringUtils.isBlank(code)){
//            return ResponseUtil.error("验证码不能为空");
            return new ModelAndView("redirect:login.html")
                    .addObject("msg","验证码不能为空");
        }
        code = code.toLowerCase();
        sessionCode = sessionCode.toLowerCase();
        if(!code.equals(sessionCode)){
//            return ResponseUtil.error("验证码错误");
            return new ModelAndView("redirect:login.html")
                    .addObject("msg","验证码错误");
        }
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        try {
            subject.login(token);
        }catch (AuthenticationException ae){
            log.debug("登录失败：{}",ae.getMessage());
//            return ResponseUtil.error("用户或密码不正确");
            return new ModelAndView("redirect:login.html")
                    .addObject("msg","用户或密码不正确");
        }
        CurrentUser user = userService.getCurrentUserByLoginName(username);
        com.jmu.bibasedmanage.util.SecurityUtils.processUserLogin(user);
        return new ModelAndView("redirect:index.html");
    }

    @RequestMapping("logout-first")
    public String logout(){
        com.jmu.bibasedmanage.util.SecurityUtils.processUserLogout();
        return "redirect:/logout";
    }
}
