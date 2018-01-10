package com.jmu.bibasedmanage.controller;

import com.jmu.bibasedmanage.pojo.BmStudent;
import com.jmu.bibasedmanage.service.StudentService;
import com.jmu.bibasedmanage.util.ResponseUtil;
import com.jmu.bibasedmanage.vo.JsonResponse;
import com.jmu.bibasedmanage.vo.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Created by ljc on 2017/12/18.
 */
@Controller
@RequestMapping("/student")
public class BmStudentController {

    @Autowired
    private StudentService studentService;

    @RequestMapping(value = "/list.html", method = RequestMethod.GET)
    public ModelAndView list(){
        return new ModelAndView("student/table.html");
    }

    /**
     * 学生列表
     * @param map（pageNo:当前页，pageSize:每页条数）
     * @param page
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ResponseBody
    public JsonResponse listData(@RequestParam Map<String, Object> map, Page<BmStudent> page){
        return ResponseUtil.success(studentService.list(map, page));
    }

    @RequestMapping(value = "/add.html", method = RequestMethod.GET)
    public ModelAndView add(){
        return new ModelAndView("student/form_add.html");
    }

    @RequestMapping(value = "/get", method = RequestMethod.POST)
    @ResponseBody
    public JsonResponse get(String id){
        return ResponseUtil.success(studentService.getById(id));
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public JsonResponse addData(BmStudent bmStudent){
        studentService.add(bmStudent);
        return ResponseUtil.success();
    }
    @RequestMapping(value = "/update.html",method = RequestMethod.GET)
    public ModelAndView update(String id){
        return new ModelAndView("/student/form_edit.html")
                .addObject("id", id);
    }
    @RequestMapping(value = "/update",method = RequestMethod.POST)
    @ResponseBody
    public JsonResponse updateData(BmStudent bmStudent){
        studentService.update(bmStudent);
        return ResponseUtil.success();
    }
    @RequestMapping(value = "/delete",method = RequestMethod.POST)
    @ResponseBody
    public JsonResponse delete(String id){
        studentService.delete(id);
        return ResponseUtil.success();
    }

    @RequestMapping(value = "/operation", method = RequestMethod.POST)
    @ResponseBody
    public JsonResponse operation(String id, String status){
        BmStudent bmStudent = new BmStudent();
        bmStudent.setId(id);
        bmStudent.setStatus(status);
        studentService.update(bmStudent);
        return ResponseUtil.success();
    }

    @RequestMapping(value = "excel-upload.html")
    public ModelAndView excelUpload(){
        return new ModelAndView("/student/excel_upload.html");
    }

    @RequestMapping("/excel-upload")
    @ResponseBody
    public JsonResponse uploadFile(HttpServletRequest request){
        return ResponseUtil.success(studentService.importExcel(request));
    }
}
