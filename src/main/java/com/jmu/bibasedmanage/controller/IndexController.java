package com.jmu.bibasedmanage.controller;

import com.jmu.bibasedmanage.service.IndexService;
import com.jmu.bibasedmanage.util.EncryptUtils;
import com.jmu.bibasedmanage.util.ResponseUtil;
import com.jmu.bibasedmanage.util.SecurityUtils;
import com.jmu.bibasedmanage.vo.CurrentUser;
import com.jmu.bibasedmanage.vo.JsonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by ljc on 2018/1/3.
 */
@Controller
public class IndexController {

    @Autowired
    private IndexService indexService;

    /**
     * 首页框架
     * @return
     */
    @RequestMapping("/index")
    public String index(){
        return "/index/index.html";
    }
    @RequestMapping("/main.html")
    public String main(){
        return "/index/main.html";
    }

    /**
     * 无用
     * 验证解锁密码
     * @param lockPwd
     * @return
     */
    @RequestMapping("/unlock")
    @ResponseBody
    public JsonResponse unlock(String lockPwd){
        CurrentUser user = SecurityUtils.getCurrentUser();
        SecurityUtils.processUserLogout();
        String salt = user.getSalt();
        String password = user.getLoginPassword();
        String lockPassword = EncryptUtils.getEncryptPwd(salt, "MD5", lockPwd);
        if(password.equals(lockPassword)){
            SecurityUtils.processUserLogin(user);
            return ResponseUtil.success();
        }else{
            return ResponseUtil.error("密码错误");
        }
    }

    @RequestMapping("/table.html")
    public String table(){
        return "/index/table.html";
    }

    @RequestMapping("/edit-pwd.html")
    public String editPwd(){
        return "/index/edit_password.html";
    }

    @RequestMapping("/menu")
    @ResponseBody
    public JsonResponse menu(){
        String roleId = SecurityUtils.getCurrentUser().getRoleId();
        return ResponseUtil.success(indexService.getMenu(roleId));
    }
}
