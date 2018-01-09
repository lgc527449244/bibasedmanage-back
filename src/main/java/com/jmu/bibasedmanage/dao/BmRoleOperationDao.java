package com.jmu.bibasedmanage.dao;

import com.jmu.bibasedmanage.pojo.BmRoleOperation;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
@Component
public interface BmRoleOperationDao {
    int deleteByPrimaryKey(String id);

    int insert(BmRoleOperation record);

    int insertSelective(BmRoleOperation record);

    BmRoleOperation selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(BmRoleOperation record);

    int updateByPrimaryKey(BmRoleOperation record);
    //批量插入
    int insertList(List<Map<String, Object>> list);
    //通过角色删除操作
    int deleteByRole(String id);
    //通过角色删除操作
    List<BmRoleOperation> selectByRole(String id);

}