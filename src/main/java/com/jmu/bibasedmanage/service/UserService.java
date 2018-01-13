package com.jmu.bibasedmanage.service;

import com.jmu.bibasedmanage.pojo.BmUser;
import com.jmu.bibasedmanage.pojo.BmUserMerge;
import com.jmu.bibasedmanage.vo.CurrentUser;
import com.jmu.bibasedmanage.vo.Page;

import java.util.Map;

/**
 * Created by ljc on 2017/12/25.
 */
public interface UserService {
    BmUser selectByLoginName(String loginName);

    /**
     * 通过loginName获取当前用户信息
     * @param loginName
     * @return
     */
    CurrentUser getCurrentUserByLoginName(String loginName);

    String add(BmUser bmUser);

    void update(BmUser bmUser);

    void delete(String id);

    BmUser getById(String id);

    Page<BmUserMerge> list(Map map, Page<BmUserMerge> page);
}
