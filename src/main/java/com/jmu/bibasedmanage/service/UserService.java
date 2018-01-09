package com.jmu.bibasedmanage.service;

import com.jmu.bibasedmanage.pojo.BmUser;
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
    /**
     * 分页查询
     * @param map（pageNo:当前页,pageSize：每页条数）
     * @param page
     * @return
     */
    Page<BmUser> list(Map map, Page<BmUser> page);

    BmUser getById(String id);
}
