package com.jmu.bibasedmanage.controller;

import com.jmu.bibasedmanage.pojo.BmOperationGroup;
import com.jmu.bibasedmanage.pojo.BmRole;
import com.jmu.bibasedmanage.service.OperationGroupService;
import com.jmu.bibasedmanage.service.RoleService;
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

import java.util.Map;

/**
 * Created by WJQ on 2018/1/7.
 */
@Controller
@RequestMapping("/operationGroup")
public class BmOperationGroupController {

    @Autowired
    private OperationGroupService operationGroupService;
    @RequestMapping(value = "/list.html", method = RequestMethod.GET)
    public ModelAndView list(){
        return new ModelAndView("operationGroup/table.html");
    }

    /**
     * 操作组列表
     * @param map（pageNo:当前页，pageSize:每页条数）
     * @param page
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ResponseBody
    public JsonResponse listData(@RequestParam Map<String, Object> map, Page<BmOperationGroup> page){
        return ResponseUtil.success(operationGroupService.list(map, page));
    }

    @RequestMapping(value = "/add.html", method = RequestMethod.GET)
    public ModelAndView add(){
        return new ModelAndView("operationGroup/form_add.html");
    }

    @RequestMapping(value = "/get-all", method = RequestMethod.POST)
    @ResponseBody
    public JsonResponse getAll(){
        return ResponseUtil.success(operationGroupService.selectAll());
    }
    @RequestMapping(value = "/get", method = RequestMethod.POST)
    @ResponseBody
    public JsonResponse get(String id){
        return ResponseUtil.success(operationGroupService.selectById(id));
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public JsonResponse addData(BmOperationGroup operationGroup){
        operationGroupService.add(operationGroup);
        return ResponseUtil.success();
    }
    @RequestMapping(value = "/update.html",method = RequestMethod.GET)
    public ModelAndView update(String id){
        return new ModelAndView("/operationGroup/form_edit.html")
                .addObject("id", id);
    }
    @RequestMapping(value = "/update",method = RequestMethod.POST)
    @ResponseBody
    public JsonResponse updateData(BmOperationGroup operationGroup){
        operationGroupService.update(operationGroup);
        return ResponseUtil.success();
    }
    @RequestMapping(value = "/delete",method = RequestMethod.POST)
    public JsonResponse delete(String id){
        operationGroupService.delete(id);
        return ResponseUtil.success();
    }

}
