package com.jmu.bibasedmanage.pojo;

import java.util.Date;

/**
 * Created by WJQ on 2018/1/11.
 */
//改过
public class BmUserMerge {
    private String id;

    private String roleId;

    private String loginName;

    private String loginPassword;

    private String salt;

    private String userStatus;

    private Date userLockTime;

    private Date lastLoginTime;

    private String tsId;

    private String roleName;

    public String getId() {
        return id;
    }

    public BmUserMerge setId(String id) {
        this.id = id;
        return this;
    }

    public String getRoleId() {
        return roleId;
    }

    public BmUserMerge setRoleId(String roleId) {
        this.roleId = roleId;
        return this;
    }

    public String getLoginName() {
        return loginName;
    }

    public BmUserMerge setLoginName(String loginName) {
        this.loginName = loginName;
        return this;
    }

    public String getLoginPassword() {
        return loginPassword;
    }

    public BmUserMerge setLoginPassword(String loginPassword) {
        this.loginPassword = loginPassword;
        return this;
    }

    public String getSalt() {
        return salt;
    }

    public BmUserMerge setSalt(String salt) {
        this.salt = salt;
        return this;
    }

    public String getUserStatus() {
        return userStatus;
    }

    public BmUserMerge setUserStatus(String userStatus) {
        this.userStatus = userStatus;
        return this;
    }

    public Date getUserLockTime() {
        return userLockTime;
    }

    public BmUserMerge setUserLockTime(Date userLockTime) {
        this.userLockTime = userLockTime;
        return this;
    }

    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    public BmUserMerge setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
        return this;
    }

    public String getTsId() {
        return tsId;
    }

    public BmUserMerge setTsId(String tsId) {
        this.tsId = tsId;
        return this;
    }

    public String getRoleName() {
        return roleName;
    }

    public BmUserMerge setRoleName(String roleName) {
        this.roleName = roleName;
        return this;
    }
}
