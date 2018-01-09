package com.jmu.bibasedmanage.service.impl;

import com.alibaba.fastjson.JSON;
import com.jmu.bibasedmanage.dao.BmRoleOperationDao;
import com.jmu.bibasedmanage.pojo.BmRoleOperation;
import com.jmu.bibasedmanage.service.RoleOperationService;
import com.jmu.bibasedmanage.util.UUIDUtils;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by WJQ on 2018/1/8.
 */
@Transactional
@Service
public class RoleOperationServiceImpl implements RoleOperationService{
    @Autowired
    private BmRoleOperationDao roleOperationDao;

    public int insertList(Map<String, Object> map) {

        return 0;
    }

    public int deleteByRole(String id) {
        return 0;
    }

    public List<BmRoleOperation> selectByRole(String id) {
        return roleOperationDao.selectByRole(id);
    }
}
