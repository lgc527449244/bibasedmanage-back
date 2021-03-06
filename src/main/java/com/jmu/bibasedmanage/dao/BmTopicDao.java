package com.jmu.bibasedmanage.dao;

import java.util.List;
import java.util.Map;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.jmu.bibasedmanage.pojo.BmTeacher;
import com.jmu.bibasedmanage.pojo.BmTopic;
import org.springframework.stereotype.Component;

@Component
public interface BmTopicDao {
    int deleteByPrimaryKey(String id);

    int insert(BmTopic record);

    int insertSelective(BmTopic record);

    BmTopic selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(BmTopic record);

    int updateByPrimaryKey(BmTopic record);
    
    List<BmTopic> selectByLike(String topicInfo);
    
    BmTopic selectById(String id);
    
    List<BmTopic> selectByLikePage(Map map, PageBounds pageBounds);

    //根据老师的id查看当前的启用状态下的课题
    BmTopic selectByTeacherId(String id);

}