package com.jmu.bibasedmanage.service.impl;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.jmu.bibasedmanage.consts.CommonConst;
import com.jmu.bibasedmanage.dao.BmRoleDao;
import com.jmu.bibasedmanage.dao.BmStudentDao;
import com.jmu.bibasedmanage.dao.BmTeacherDao;
import com.jmu.bibasedmanage.dao.BmUserDao;
import com.jmu.bibasedmanage.pojo.*;
import com.jmu.bibasedmanage.service.UserService;
import com.jmu.bibasedmanage.util.EncryptUtils;
import com.jmu.bibasedmanage.util.UUIDUtils;
import com.jmu.bibasedmanage.vo.CurrentUser;
import com.jmu.bibasedmanage.vo.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.List;


/**
 * Created by ljc on 2017/12/25.
 */
@Transactional
@Service
public class UserSerivceImpl implements UserService{

    @Autowired
    private BmUserDao bmUserDao;
    @Autowired
    private BmTeacherDao bmTeacherDao;
    @Autowired
    private BmStudentDao bmStudentDao;
    @Autowired
    private BmRoleDao bmRoleDao;

    public BmUser selectByLoginName(String loginName) {

        return bmUserDao.selectByLoginName(loginName);
    }

    public CurrentUser getCurrentUserByLoginName(String loginName) {
        return bmUserDao.selectCurrentByLoginName(loginName);
    }


    public String add(BmUser bmUser){
        String id= UUIDUtils.generator();
        bmUser.setId(id);
        //加密密码
        String salt = UUIDUtils.generator();
        bmUser.setSalt(salt);
        bmUser.setLoginPassword(EncryptUtils.getEncryptPwd(
                salt, "MD5", bmUser.getLoginPassword()));

        bmUser.setUserStatus("NORMAL");
        BmRole role = bmRoleDao.selectByPrimaryKey(bmUser.getRoleId());
        //绑定
        if(role.getRemark().equals("教师")){
            BmTeacher teacher = new BmTeacher();
            teacher.setId(bmUser.getTsId());
            teacher.setIsBind(1);
            bmTeacherDao.updateByPrimaryKeySelective(teacher);
        }
        if(role.getRemark().equals("学生")){
            BmStudent student = new BmStudent();
            student.setId(bmUser.getTsId());
            student.setIsBind(1);
            bmStudentDao.updateByPrimaryKeySelective(student);
        }
        bmUserDao.insert(bmUser);
        return id;
    }

    public void update(BmUser bmUser){
        String  lastRoleId;
        if(bmUser.getRoleId().contains(",")){
            lastRoleId = bmUser.getRoleId().split(",")[1];
            bmUser.setRoleId(bmUser.getRoleId().split(",")[0]);
        }else lastRoleId = bmUser.getRoleId();


        if(bmUser.getTsId().contains(",")){
            String tsId1 = bmUser.getTsId().split(",")[0];
            String tsId2 = bmUser.getTsId().split(",")[1];
            System.out.println(tsId1+"===="+tsId2);
            bmUser.setTsId(tsId1);
            BmRole role = bmRoleDao.selectByPrimaryKey(bmUser.getRoleId());
            BmRole lastRole = bmRoleDao.selectByPrimaryKey(lastRoleId);
            //解绑
            if(lastRole.getName().equals("teacher")){
                BmTeacher teacher1 = new BmTeacher();
                teacher1.setId(tsId2);
                teacher1.setIsBind(0);
                bmTeacherDao.updateByPrimaryKeySelective(teacher1);
            }
            if(lastRole.getName().equals("student")){
                BmStudent student1 = new BmStudent();
                student1.setId(tsId1);
                student1.setIsBind(0);
                bmStudentDao.updateByPrimaryKeySelective(student1);
            }
            //绑定用户
            if(role.getName().equals("teacher")){
                BmTeacher teacher = new BmTeacher();
                teacher.setId(tsId1);
                teacher.setIsBind(1);
                bmTeacherDao.updateByPrimaryKeySelective(teacher);
            }
            if(role.getName().equals("student")){
                BmStudent student = new BmStudent();
                student.setId(tsId1);
                student.setIsBind(1);
                bmStudentDao.updateByPrimaryKeySelective(student);
            }
        }
        //加密密码
        if(bmUser.getLoginPassword() != null){
            String salt = UUIDUtils.generator();
            bmUser.setSalt(salt);
            bmUser.setLoginPassword(EncryptUtils.getEncryptPwd(
                    salt, "MD5", bmUser.getLoginPassword()));
        }
        bmUserDao.updateByPrimaryKeySelective(bmUser);

    }


    public void delete(String id){
        BmUser bmUser=new BmUser();
        bmUser.setId(id);
        bmUser.setUserStatus(CommonConst.RECORD_STATUS_DELETED);
        bmUserDao.updateByPrimaryKeySelective(bmUser);
    }

    public BmUser getById(String id) {
        return bmUserDao.selectByPrimaryKey(id);
    }

    public Page<BmUserMerge> list(Map map, Page<BmUserMerge> page) {
        PageBounds pageBounds = new PageBounds(page.getPageNo(), page.getPageSize());
        List<BmUserMerge> users = bmUserDao.selectByPage(pageBounds, map);
        page.setResult(users);
        page.setTotalCountByPageList((PageList) users);
        return page;
    }
}
