package com.jmu.bibasedmanage.dao;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.jmu.bibasedmanage.pojo.BmUser;
import com.jmu.bibasedmanage.pojo.BmUserMerge;
import com.jmu.bibasedmanage.vo.CurrentUser;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Map;

@Component
public interface BmUserDao {
    int deleteByPrimaryKey(String id);

    int insert(BmUser record);

    int insertSelective(BmUser record);

    BmUser selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(BmUser record);

    int updateByPrimaryKey(BmUser record);

    //通过账号查询用户
    BmUser selectByLoginName(String loginName);

    //根据登录名获取当前用户信息
    CurrentUser selectCurrentByLoginName(@Param("loginName") String loginName);

    //改过 BmUserMerge为了在页面展示 多了一个roleName
    List<BmUserMerge> selectByPage(PageBounds pageBounds, Map<String, Object> map);
}