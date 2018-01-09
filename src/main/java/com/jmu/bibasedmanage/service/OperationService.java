package com.jmu.bibasedmanage.service;

import com.jmu.bibasedmanage.pojo.BmOperation;
import com.jmu.bibasedmanage.vo.Page;

import java.util.List;
import java.util.Map;

/**
 * Created by ljc on 2017/12/26.
 */
public interface OperationService {
    /**
     * 通过roleId查询操作
     * @param roleId
     * @return
     */
    List<BmOperation> getOperationByRoleId(String roleId);

    BmOperation selectById(String operationId);

    Page<Map<String,Object>> list(Map map, Page<Map<String,Object>> page);

    String add(BmOperation bmOperation);

    void update(BmOperation bmOperation);

    void delete(String id);

    List<BmOperation> selectAll();

}
