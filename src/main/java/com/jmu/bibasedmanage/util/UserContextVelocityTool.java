package com.jmu.bibasedmanage.util;

import com.jmu.bibasedmanage.vo.CurrentUser;
import org.apache.velocity.tools.config.DefaultKey;
import org.apache.velocity.tools.config.ValidScope;

/**
 * 暂时没打通（用于velocityToolBox）
 * Created by ljc on 2018/1/6.
 */
@DefaultKey("userContext")
@ValidScope("application")
public class UserContextVelocityTool {

    public CurrentUser getCurrentUser(){
        System.out.println(1111);
        return SecurityUtils.getCurrentUser();
    }
}
