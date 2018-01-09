package com.jmu.bibasedmanage.service.impl;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.jmu.bibasedmanage.dao.BmOperationDao;
import com.jmu.bibasedmanage.pojo.BmOperation;
import com.jmu.bibasedmanage.service.OperationService;
import com.jmu.bibasedmanage.util.UUIDUtils;
import com.jmu.bibasedmanage.vo.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * Created by ljc on 2017/12/26.
 */
@Transactional
@Service
public class OperationServiceImpl implements OperationService{
    @Autowired
    private BmOperationDao bmOperationDao;

    public List<BmOperation> getOperationByRoleId(String roleId) {
        return bmOperationDao.selectByRoleId(roleId);
    }

    public Page<Map<String,Object>> list(Map map, Page<Map<String,Object>> page) {
        PageBounds pageBounds = new PageBounds(page.getPageNo(), page.getPageSize());
        List<Map<String,Object>> operations = bmOperationDao.selectByPage(pageBounds, map);
        page.setResult(operations);
        page.setTotalCountByPageList((PageList) operations);
        return page;
    }

    public String add(BmOperation bmOperation) {
        String id = UUIDUtils.generator();
        bmOperation.setId(id);
        bmOperationDao.insertSelective(bmOperation);
        return id;
    }

    public void update(BmOperation bmOperation) {
        bmOperationDao.updateByPrimaryKeySelective(bmOperation);
    }

    public void delete(String id) {
        bmOperationDao.deleteByPrimaryKey(id);
    }

    public List<BmOperation> selectAll() {
        return bmOperationDao.selectAll();
    }

    public BmOperation selectById(String bmOperationId) {
        return bmOperationDao.selectByPrimaryKey(bmOperationId);
    }

}
