package com.jmu.bibasedmanage.dao;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.jmu.bibasedmanage.pojo.BmOperationGroup;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Map;

@Component
public interface BmOperationGroupDao {
    int deleteByPrimaryKey(String id);

    int insert(BmOperationGroup record);

    int insertSelective(BmOperationGroup record);

    BmOperationGroup selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(BmOperationGroup record);

    int updateByPrimaryKey(BmOperationGroup record);

    List<BmOperationGroup> selectByRoleId(@Param("roleId") String roleId);

    List<BmOperationGroup> selectByPage(PageBounds pageBounds, Map<String, Object> map);
    List<BmOperationGroup> selectAll();

}