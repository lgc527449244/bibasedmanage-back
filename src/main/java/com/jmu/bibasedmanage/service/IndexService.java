package com.jmu.bibasedmanage.service;

import com.jmu.bibasedmanage.vo.Menu;

import java.util.List;

/**
 * Created by ljc on 2018/1/8.
 */
public interface IndexService {
    /**
     * 获取菜单
     * @param roleId
     * @return
     */
    List<Menu> getMenu(String roleId);
}
