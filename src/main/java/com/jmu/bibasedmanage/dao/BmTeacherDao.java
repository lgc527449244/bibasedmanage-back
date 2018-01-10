package com.jmu.bibasedmanage.dao;

import java.util.List;
import java.util.Map;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.jmu.bibasedmanage.pojo.BmStudent;
import com.jmu.bibasedmanage.pojo.BmTeacher;
import com.jmu.bibasedmanage.pojo.BmTopic;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

@Component
public interface BmTeacherDao {
    int deleteByPrimaryKey(String id);

    int insert(BmTeacher record);

    int insertSelective(BmTeacher record);

    BmTeacher selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(BmTeacher record);

    int updateByPrimaryKey(BmTeacher record);
    
    List<BmTeacher> selectByLikePage(Map map, PageBounds pageBounds);
 
    List<BmTeacher> selectByLike(String teacherInfo);

    BmTeacher selectByJobNumber(@Param("jobNumber") String jobNumber);
}