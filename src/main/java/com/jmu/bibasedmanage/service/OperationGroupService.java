package com.jmu.bibasedmanage.service;

import com.jmu.bibasedmanage.pojo.BmOperationGroup;
import com.jmu.bibasedmanage.pojo.BmRole;
import com.jmu.bibasedmanage.vo.Page;

import java.util.List;
import java.util.Map;

/**
 * Created by WJQ on 2018/1/7.
 */
public interface OperationGroupService {

    BmOperationGroup selectById(String operationGroupId);
    Page<BmOperationGroup> list(Map map, Page<BmOperationGroup> page);

    String add(BmOperationGroup bmOperationGroup);

    void update(BmOperationGroup bmOperationGroup);
    List<BmOperationGroup> selectAll();
    void delete(String id);
}
