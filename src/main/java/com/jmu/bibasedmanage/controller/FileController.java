package com.jmu.bibasedmanage.controller;

import com.jmu.bibasedmanage.util.FileUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 文件控制器
 * Created by ljc on 2018/1/7.
 */
@Controller
@RequestMapping("/file")
public class FileController {

    /**
     * 文件下载
     * @param request
     * @param response
     * @param fileName
     */
    @RequestMapping("/down")
    public void uploadFile(HttpServletRequest request, HttpServletResponse response, String fileName){
        try {
            fileName = new String(fileName.getBytes("ISO8859-1"), "UTF-8");
            FileUtils.down(request, response, fileName);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
