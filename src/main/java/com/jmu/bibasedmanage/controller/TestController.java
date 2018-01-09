package com.jmu.bibasedmanage.controller;

import com.jmu.bibasedmanage.excel.ExcelImportContext;
import com.jmu.bibasedmanage.excel.ExcelImportHandler;
import com.jmu.bibasedmanage.excel.ExcelImportService;
import com.jmu.bibasedmanage.excel.RowData;
import com.jmu.bibasedmanage.exception.BusinessException;
import com.jmu.bibasedmanage.util.FileUtils;
import com.jmu.bibasedmanage.util.ResponseUtil;
import com.jmu.bibasedmanage.vo.JsonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by ljc on 2017/12/14.
 */
@Controller
@RequestMapping("/test")
public class TestController {

    @Autowired
    private ExcelImportService excelImportService;

    @RequestMapping("/index")
    public ModelAndView test(){
        return new ModelAndView("test.html");
    }
    @RequestMapping("/data")
    @ResponseBody
    public JsonResponse data(){
        String key1 = "test";
        Map<String, String> map = new HashMap<String, String>();
        map.put("ss","111");
        map.put("te","22");
        return ResponseUtil.successMap(key1, map);
    }
    @RequestMapping("/exception")
    public void exception(){
        throw new BusinessException("测试");
    }

    @RequestMapping("/main")
    public String mainYe(){
        return "/demo/container.html";
    }

    @RequestMapping("/file")
    public void uploadFile(HttpServletRequest request, HttpServletResponse response){
        try {
            FileUtils.down(request, response,"E://学生信息导入模板.xls");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args){
        String f = "E://学生信息导入模板.xls";
        try {
            InputStream inputStream = new FileInputStream(new File(f));
            FileUtils.inputStreamToFile(inputStream, "E://test.xls");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
