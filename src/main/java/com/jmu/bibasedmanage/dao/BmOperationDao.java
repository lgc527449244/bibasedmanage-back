package com.jmu.bibasedmanage.dao;

import com.jmu.bibasedmanage.pojo.BmOperation;

public interface BmOperationDao {
    int deleteByPrimaryKey(String id);

    int insert(BmOperation record);

    int insertSelective(BmOperation record);

    BmOperation selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(BmOperation record);

    int updateByPrimaryKey(BmOperation record);
}