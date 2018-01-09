package com.jmu.bibasedmanage.service;


import com.jmu.bibasedmanage.pojo.BmRole;
import com.jmu.bibasedmanage.pojo.BmStudent;
import com.jmu.bibasedmanage.vo.Page;

import java.util.Map;

/**
 * Created by ljc on 2017/12/26.
 */
public interface RoleService {

    BmRole selectById(String roleId);
    Page<BmRole> list(Map map, Page<BmRole> page);

    String add(BmRole bmRole,Map<String,Object> map);

    void update(BmRole bmRole,Map<String,Object> map);

    void delete(String id);


}
