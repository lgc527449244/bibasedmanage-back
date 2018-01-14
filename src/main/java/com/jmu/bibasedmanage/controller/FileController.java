package com.jmu.bibasedmanage.controller;

import com.alibaba.fastjson.JSONObject;
import com.jmu.bibasedmanage.util.FileUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

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
    //上传文件
    /**
     * @param request
     * @param file
     * @return
     * @throws IOException
     * Created by hhq on 2018-1-11.
     */
    @RequestMapping(value = "/uploadFile")
    @ResponseBody
    public String uploadFile(HttpServletRequest request, @Param("file") MultipartFile file) throws IOException {
        //TODO 调用FileUtils
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSS");
        String res = sdf.format(new Date());
        //获取服务器相对路径
        String contextPath = request.getContextPath();
        String basePath = request.getScheme()+"://"+request.getServerName()+":"+
                request.getServerPort()+contextPath+"/";

        //本地使用
        String rootPath ="E:/bm/tempPic/";
        //原始名称
        String originalFilename = file.getOriginalFilename();
        //新的文件名称
        String newFileName = res+originalFilename.substring(originalFilename.lastIndexOf("."));
        //创建年月文件夹
        Calendar date = Calendar.getInstance();
        File dateDirs = new File(date.get(Calendar.YEAR)
                + File.separator + (date.get(Calendar.MONTH)+1));

        String allFileName = rootPath+File.separator+dateDirs+File.separator+newFileName;
        //新文件
        File newFile = new File(allFileName);
        //判断目标文件所在的目录是否存在
        if(!newFile.getParentFile().exists()) {
            //如果目标文件所在的目录不存在，则创建父目录
            newFile.getParentFile().mkdirs();
        }
        System.out.println(newFile);
        //将内存中的数据写入磁盘
        file.transferTo(newFile);
        //完整的url
        Map<String,Object> map = new HashMap<String,Object>();
        Map<String,Object> map2 = new HashMap<String,Object>();
        //填写返回富文本中的Json的数据
        map.put("code",0);//0表示成功，1失败
        map.put("msg","上传成功");//提示消息
        map.put("data",map2);
        map2.put("src", basePath + "file/down?fileName=" + allFileName);//图片url
        map2.put("title",newFileName);//图片名称，这个会显示在输入框里
        String result = new JSONObject(map).toString();
        return result;
    }
}
