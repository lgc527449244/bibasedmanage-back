package com.jmu.bibasedmanage.vo;

import com.jmu.bibasedmanage.pojo.BmUser;

/**
 * Created by ljc on 2018/1/6.
 */
public class CurrentUser extends BmUser{

    private String roleName;
    private String roleRemark;

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleRemark() {
        return roleRemark;
    }

    public void setRoleRemark(String roleRemark) {
        this.roleRemark = roleRemark;
    }
}
