package com.jmu.bibasedmanage.service.impl;

import com.jmu.bibasedmanage.dao.BmOperationDao;
import com.jmu.bibasedmanage.dao.BmOperationGroupDao;
import com.jmu.bibasedmanage.pojo.BmOperation;
import com.jmu.bibasedmanage.pojo.BmOperationGroup;
import com.jmu.bibasedmanage.service.IndexService;
import com.jmu.bibasedmanage.vo.ChildMenu;
import com.jmu.bibasedmanage.vo.Menu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ljc on 2018/1/8.
 */
@Service
public class IndexServiceImpl implements IndexService{

    @Autowired
    private BmOperationGroupDao bmOperationGroupDao;
    @Autowired
    private BmOperationDao bmOperationDao;

    public List<Menu> getMenu(String roleId) {
        List<Menu> menus = new ArrayList<Menu>();
        List<BmOperationGroup> bmOperationGroups = bmOperationGroupDao.selectByRoleId(roleId);
        for (BmOperationGroup group : bmOperationGroups) {
            Menu menu = new Menu();
            menu.setTitle(group.getRemark());
            //子菜单
            List<ChildMenu> childMenus = new ArrayList<ChildMenu>();
            List<BmOperation> operations = bmOperationDao.selectByGroupIdAndRoleId(group.getId(), roleId);
            for (BmOperation operation : operations) {
                ChildMenu childMenu = new ChildMenu();
                childMenu.setHref(operation.getEnterUrl());
                childMenu.setTitle(operation.getRemark());
                childMenus.add(childMenu);
            }
            menu.setChildren(childMenus);

            menus.add(menu);
        }
        return menus;
    }
}
