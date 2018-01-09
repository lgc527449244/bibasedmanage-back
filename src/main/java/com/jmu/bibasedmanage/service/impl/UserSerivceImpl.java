package com.jmu.bibasedmanage.service.impl;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.jmu.bibasedmanage.consts.CommonConst;
import com.jmu.bibasedmanage.dao.BmUserDao;
import com.jmu.bibasedmanage.pojo.BmUser;
import com.jmu.bibasedmanage.service.UserService;
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

    public BmUser selectByLoginName(String loginName) {

        return bmUserDao.selectByLoginName(loginName);
    }

    public CurrentUser getCurrentUserByLoginName(String loginName) {
        return bmUserDao.selectCurrentByLoginName(loginName);
    }

    public Page<BmUser> list(Map map, Page<BmUser> page) {
        PageBounds pageBounds = new PageBounds(page.getPageNo(), page.getPageSize());
        List<BmUser> users = bmUserDao.selectByPage(pageBounds, map);
        page.setResult(users);
        page.setTotalCountByPageList((PageList) users);
        return page;
    }

    public String add(BmUser bmUser){
        String id= UUIDUtils.generator();
        bmUser.setId(id);
        bmUserDao.insert(bmUser);
        return id;

    }

    public void update(BmUser bmUser){
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
}
