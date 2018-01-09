package com.jmu.bibasedmanage.controller;

import com.jmu.bibasedmanage.pojo.BmUser;
import com.jmu.bibasedmanage.service.UserService;
import com.jmu.bibasedmanage.util.ResponseUtil;
import com.jmu.bibasedmanage.util.SecurityUtils;
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
 * Created by ljc on 2017/12/24.
 */
@Controller
@RequestMapping("/user")
public class BmUserController {

    @Autowired
    private UserService userService;

    @RequestMapping("/info")
    @ResponseBody
    public JsonResponse getCurrentUserInfo(){
        return ResponseUtil.success(SecurityUtils.getCurrentUser());
    }

    @RequestMapping(value = "/list.html", method = RequestMethod.GET)
    public ModelAndView list(){
        return new ModelAndView("user/table.html");
    }
    /**
     * 用户列表
     * @param map（pageNo:当前页，pageSize:每页条数）
     * @param page
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ResponseBody
    public JsonResponse listData(@RequestParam Map<String, Object> map, Page<BmUser> page){
        return ResponseUtil.success(userService.list(map, page));
    }

    @RequestMapping(value = "/add.html", method = RequestMethod.GET)
    public ModelAndView add(){
        return new ModelAndView("user/form_add.html");
    }

    @RequestMapping(value = "/get", method = RequestMethod.POST)
    @ResponseBody
    public JsonResponse get(String id){
        return ResponseUtil.success(userService.getById(id));
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public JsonResponse addData(BmUser bmUser){
        userService.add(bmUser);
        return ResponseUtil.success();
    }
    @RequestMapping(value = "/update.html",method = RequestMethod.GET)
    public ModelAndView update(String id){
        return new ModelAndView("/user/form_edit.html")
                .addObject("id", id);
    }
    @RequestMapping(value = "/update",method = RequestMethod.POST)
    @ResponseBody
    public JsonResponse updateData(BmUser bmUser){
        userService.update(bmUser);
        return ResponseUtil.success();
    }
    @RequestMapping(value = "/delete",method = RequestMethod.POST)
    public JsonResponse delete(String id){
        userService.delete(id);
        return ResponseUtil.success();
    }

    @RequestMapping(value = "excel-upload.html")
    public ModelAndView excelUpload(){
        return new ModelAndView("/user/excel_upload.html");
    }

}
