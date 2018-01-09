package com.jmu.bibasedmanage.dao;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.jmu.bibasedmanage.pojo.BmStudent;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public interface BmStudentDao {
    int deleteByPrimaryKey(String id);

    int insert(BmStudent record);

    int insertSelective(BmStudent record);

    BmStudent selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(BmStudent record);

    int updateByPrimaryKey(BmStudent record);

    List<BmStudent> selectByPage(PageBounds pageBounds, Map<String, Object> map);
    //根据学号获取学生信息
    BmStudent selectByStudentId(@Param("studentId") String studentId);
}