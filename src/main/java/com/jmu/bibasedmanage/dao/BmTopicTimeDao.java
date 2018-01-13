package com.jmu.bibasedmanage.dao;

import com.jmu.bibasedmanage.pojo.BmTopicTime;

public interface BmTopicTimeDao {
    int deleteByPrimaryKey(String id);

    int insert(BmTopicTime record);

    int insertSelective(BmTopicTime record);

    BmTopicTime selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(BmTopicTime record);

    int updateByPrimaryKey(BmTopicTime record);
    
    BmTopicTime selectByUserId();
}