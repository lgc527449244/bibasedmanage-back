package com.jmu.bibasedmanage.controller;

import com.jmu.bibasedmanage.pojo.BmOperation;
import com.jmu.bibasedmanage.service.OperationService;
import com.jmu.bibasedmanage.util.ResponseUtil;
import com.jmu.bibasedmanage.vo.JsonResponse;
import com.jmu.bibasedmanage.vo.Page;
import org.apache.commons.lang3.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

/**
 * Created by WJQ on 2018/1/8.
 */
@Controller
@RequestMapping("/operation")
public class BmOperationController {
    @Autowired
    private OperationService operationService;

    @RequestMapping(value = "/list.html", method = RequestMethod.GET)
    public ModelAndView list(){
        return new ModelAndView("operation/table.html");
    }


    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ResponseBody
    public JsonResponse listData(@RequestParam Map<String, Object> map, Page<Map<String,Object>> page){
        return ResponseUtil.success(operationService.list(map, page));
    }

    @RequestMapping(value = "/add.html", method = RequestMethod.GET)
    public ModelAndView add(){
        return new ModelAndView("operation/form_add.html");
    }

    @RequestMapping(value = "/get", method = RequestMethod.POST)
    @ResponseBody
    public JsonResponse get(String id){

        return ResponseUtil.success(operationService.selectById(id));
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public JsonResponse addData(BmOperation bmOperation){
        operationService.add(bmOperation);
        return ResponseUtil.success();
    }
    @RequestMapping(value = "/get-all", method = RequestMethod.POST)
    @ResponseBody
    public JsonResponse getAll(){
        return ResponseUtil.success(operationService.selectAll());
    }
    @RequestMapping(value = "/update.html",method = RequestMethod.GET)
    public ModelAndView update(String id){
        return new ModelAndView("/operation/form_edit.html")
                .addObject("id", id);
    }
    @RequestMapping(value = "/update",method = RequestMethod.POST)
    @ResponseBody
    public JsonResponse updateData(BmOperation bmOperation){
        operationService.update(bmOperation);
        return ResponseUtil.success();
    }
    @RequestMapping(value = "/delete",method = RequestMethod.POST)
    public JsonResponse delete(String id){
        operationService.delete(id);
        return ResponseUtil.success();
    }
}
