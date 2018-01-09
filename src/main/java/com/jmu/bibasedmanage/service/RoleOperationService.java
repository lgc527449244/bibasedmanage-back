package com.jmu.bibasedmanage.service;

import com.jmu.bibasedmanage.pojo.BmRoleOperation;

import java.util.List;
import java.util.Map;

/**
 * Created by WJQ on 2018/1/8.
 */
public interface RoleOperationService {

    //批量插入
    int insertList(Map<String, Object> map);
    //通过角色删除操作
    int deleteByRole(String id);
    //通过角色删除操作
    List<BmRoleOperation> selectByRole(String id);
}
