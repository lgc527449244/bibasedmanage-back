package com.jmu.bibasedmanage.controller;

import com.jmu.bibasedmanage.service.RoleOperationService;
import com.jmu.bibasedmanage.util.ResponseUtil;
import com.jmu.bibasedmanage.vo.JsonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by WJQ on 2018/1/8.
 */
@Controller
@RequestMapping("/roleOperation")
public class BmRoleOperationController {

    @Autowired
    RoleOperationService roleOperationService;
    @RequestMapping(value = "/get-all-roleId", method = RequestMethod.POST)
    @ResponseBody
    public JsonResponse getAll(String id){
        return ResponseUtil.success(roleOperationService.selectByRole(id));
    }
}
