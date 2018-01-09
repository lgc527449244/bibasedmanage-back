package com.jmu.bibasedmanage.service.impl;

import com.alibaba.fastjson.JSON;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.jmu.bibasedmanage.consts.CommonConst;
import com.jmu.bibasedmanage.dao.BmRoleDao;
import com.jmu.bibasedmanage.dao.BmRoleOperationDao;
import com.jmu.bibasedmanage.pojo.BmRole;
import com.jmu.bibasedmanage.pojo.BmStudent;
import com.jmu.bibasedmanage.service.RoleService;
import com.jmu.bibasedmanage.util.UUIDUtils;
import com.jmu.bibasedmanage.vo.Page;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 * Created by ljc on 2017/12/26.
 */
@Transactional
@Service
public class RoleServiceImpl implements RoleService{

    @Autowired
    private BmRoleDao bmRoleDao;
    @Autowired
    private BmRoleOperationDao roleOperationDao;

    public Page<BmRole> list(Map map, Page<BmRole> page) {
        PageBounds pageBounds = new PageBounds(page.getPageNo(), page.getPageSize());
        List<BmRole> students = bmRoleDao.selectByPage(pageBounds, map);
        page.setResult(students);
        page.setTotalCountByPageList((PageList) students);
        return page;
    }

   public String add(BmRole bmRole,Map<String,Object> map){
        String id = UUIDUtils.generator();
        bmRole.setId(id);
       int i = 0;
       System.out.println(JSON.toJSONString(map)+"=="+map.size());
       List<Map<String,Object>> list = new ArrayList<Map<String, Object>>();
       for (String key : map.keySet()) {
           if(key.equals("operation"+i))
           {
               String roleOperationid = UUIDUtils.generator();
               Map<String,Object> maps = new HashedMap();
               maps.put("id",roleOperationid);
               maps.put("roleId",id);
               maps.put("operationId",map.get(key));
               System.out.println("key= "+ key + " and value= " + map.get(key));
               i++;
               list.add(maps);
           }
       }
       roleOperationDao.insertList(list);
        bmRoleDao.insertSelective(bmRole);
        return id;
    }

    public void update(BmRole bmRole,Map<String,Object> map) {
       //先全部删除再添加
        roleOperationDao.deleteByRole(bmRole.getId());
        int i = 0;
        System.out.println(JSON.toJSONString(map)+"=="+map.size());
        List<Map<String,Object>> list = new ArrayList<Map<String, Object>>();
        for (String key : map.keySet()) {
            if(key.equals("operation"+i)){
                String roleOperationid = UUIDUtils.generator();
                Map<String,Object> maps = new HashedMap();
                maps.put("id",roleOperationid);
                maps.put("roleId",bmRole.getId());
                maps.put("operationId",map.get(key));
                System.out.println("key= "+ key + " and value= " + map.get(key));
                i++;
                list.add(maps);
            }
        }
        roleOperationDao.insertList(list);
       bmRoleDao.updateByPrimaryKeySelective(bmRole);
    }

    public void delete(String id) {
        bmRoleDao.deleteByPrimaryKey(id);
    }

    public BmRole selectById(String roleId) {
        return bmRoleDao.selectByPrimaryKey(roleId);
    }
}
