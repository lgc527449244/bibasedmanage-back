package com.jmu.bibasedmanage.service.impl;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.jmu.bibasedmanage.dao.BmOperationGroupDao;
import com.jmu.bibasedmanage.dao.BmRoleDao;
import com.jmu.bibasedmanage.pojo.BmOperationGroup;
import com.jmu.bibasedmanage.pojo.BmRole;
import com.jmu.bibasedmanage.service.OperationGroupService;
import com.jmu.bibasedmanage.util.UUIDUtils;
import com.jmu.bibasedmanage.vo.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * Created by WJQ on 2018/1/7.
 */
@Transactional
@Service
public class OperationGroupServiceImpl implements OperationGroupService {
    @Autowired
    private BmOperationGroupDao bmoperationGroupDao;
    public Page<BmOperationGroup> list(Map map, Page<BmOperationGroup> page) {
        PageBounds pageBounds = new PageBounds(page.getPageNo(), page.getPageSize());
        List<BmOperationGroup> students = bmoperationGroupDao.selectByPage(pageBounds, map);
        page.setResult(students);
        page.setTotalCountByPageList((PageList) students);
        return page;
    }

    public String add(BmOperationGroup bmoperationGroup) {
        String id = UUIDUtils.generator();
        bmoperationGroup.setId(id);
        bmoperationGroupDao.insertSelective(bmoperationGroup);
        return id;
    }

    public void update(BmOperationGroup bmoperationGroup) {
        bmoperationGroupDao.updateByPrimaryKeySelective(bmoperationGroup);
    }

    public List<BmOperationGroup> selectAll() {
        return bmoperationGroupDao.selectAll();
    }

    public void delete(String id) {
        bmoperationGroupDao.deleteByPrimaryKey(id);
    }

    public BmOperationGroup selectById(String roleId) {
        return bmoperationGroupDao.selectByPrimaryKey(roleId);
    }
}
