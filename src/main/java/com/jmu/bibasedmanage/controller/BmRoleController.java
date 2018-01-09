package com.jmu.bibasedmanage.controller;

import com.alibaba.fastjson.JSON;
import com.jmu.bibasedmanage.dao.BmRoleOperationDao;
import com.jmu.bibasedmanage.pojo.BmRole;
import com.jmu.bibasedmanage.pojo.BmStudent;
import com.jmu.bibasedmanage.service.RoleOperationService;
import com.jmu.bibasedmanage.service.RoleService;
import com.jmu.bibasedmanage.service.StudentService;
import com.jmu.bibasedmanage.util.ResponseUtil;
import com.jmu.bibasedmanage.vo.JsonResponse;
import com.jmu.bibasedmanage.vo.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.management.relation.Role;
import java.util.Map;

/**
 * Created by WJQ on 2018/1/7.
 */
@Controller
@RequestMapping("/role")
public class BmRoleController {

    @Autowired
    private RoleService roleService;
    @Autowired
    private RoleOperationService roleOperationService;

    @RequestMapping(value = "/list.html", method = RequestMethod.GET)
    public ModelAndView list(){
        return new ModelAndView("role/table.html");
    }

    /**
     * 学生列表
     * @param map（pageNo:当前页，pageSize:每页条数）
     * @param page
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ResponseBody
    public JsonResponse listData(@RequestParam Map<String, Object> map, Page<BmRole> page){
        return ResponseUtil.success(roleService.list(map, page));
    }

    @RequestMapping(value = "/add.html", method = RequestMethod.GET)
    public ModelAndView add(){
        return new ModelAndView("role/form_add.html");
    }

    @RequestMapping(value = "/get", method = RequestMethod.POST)
    @ResponseBody
    public JsonResponse get(String id){
        return ResponseUtil.success(roleService.selectById(id));
    }

   @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public JsonResponse addData(BmRole bmRole,@RequestParam Map<String, Object> map){
    //   roleOperationService.insertList(map);
        roleService.add(bmRole,map);
        return ResponseUtil.success();
    }
    @RequestMapping(value = "/update.html",method = RequestMethod.GET)
    public ModelAndView update(String id){
        return new ModelAndView("/role/form_edit.html")
                .addObject("id", id);
    }
    @RequestMapping(value = "/update",method = RequestMethod.POST)
    @ResponseBody
    public JsonResponse updateData(BmRole bmRole,@RequestParam Map<String, Object> map){
        roleService.update(bmRole,map);
        return ResponseUtil.success();
    }
    @RequestMapping(value = "/delete",method = RequestMethod.POST)
    public JsonResponse delete(String id){
        roleService.delete(id);
        return ResponseUtil.success();
    }

}
