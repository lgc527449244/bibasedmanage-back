package com.jmu.bibasedmanage.dao;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.jmu.bibasedmanage.pojo.BmRole;
import com.jmu.bibasedmanage.pojo.BmStudent;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public interface BmRoleDao {
    int deleteByPrimaryKey(String id);

    int insert(BmRole record);

    int insertSelective(BmRole record);

    BmRole selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(BmRole record);

    int updateByPrimaryKey(BmRole record);
    List<BmRole> selectByPage(PageBounds pageBounds, Map<String, Object> map);
}