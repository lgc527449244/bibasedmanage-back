package com.jmu.bibasedmanage.util;

import java.io.*;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jmu.bibasedmanage.exception.BusinessException;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
/**
 *
 * @author ljc
 * @Description: 文件处理工具类
 */
public class FileUtils {
    protected final Log log = LogFactory.getLog(getClass());
    private static long maxFileSize = 200000000;// 默认限制为200M
    private static String encoding = "utf-8";// 编码格式
    private static List prepairedItems = null;



    /**
     * 得到文件路径
     *
     * @param fileNameWithPath
     * @return
     */
    public static String getFilePath(String fileNameWithPath) {
        String filePath = "";
        fileNameWithPath = fileNameWithPath.replace("/", File.separator);
        int idx = fileNameWithPath.lastIndexOf(File.separator);
        if (idx != -1) {
            filePath = fileNameWithPath.substring(0, idx);
        }
        return filePath;
    }


    /**
     * 将内容写入文件
     * @param context 内容
     * @param fileName 目标文件
     * @throws Exception
     */
    public static void writeFile(String context,String fileName) throws Exception{
        byte[] bytes=context.getBytes();
        File file=new File(fileName);
        FileOutputStream fop = new FileOutputStream(file);
        BufferedOutputStream out = new BufferedOutputStream(fop);
        if(!file.exists()){
            file.mkdirs();
        }
        out.write(bytes);
        out.flush();
        out.close();
    }

    /**
     * 复制文件
     *
     * @param srcFileName
     *            要复制的源文件
     * @param destFileName
     *            目标文件
     * @throws Exception
     */
    public static void copyFile(String srcFileName, String destFileName)
            throws Exception {
        BufferedInputStream in = null;
        BufferedOutputStream out = null;
        try {
            in = new BufferedInputStream(new FileInputStream(new File(
                    srcFileName)));
            String destPath = getFilePath(destFileName);
            File pathFile = new File(destPath);
            if (!pathFile.exists()){
                pathFile.mkdirs();
            }
            out = new BufferedOutputStream(new FileOutputStream(new File(
                    destFileName)));
            int len;
            byte[] b = new byte[1024];
            while ((len = in.read(b)) != -1) {
                out.write(b, 0, len);
            }
            out.flush();
        } catch (Exception e) {
            System.out.println("复制文件失败：" + e.getMessage());
            throw e;
        } finally {
            try {
                if (in != null)
                    in.close();
            } catch (Exception ex) {
            }
            try {
                if (out != null)
                    out.close();
            } catch (Exception ex) {
            }
        }
    }

    /**
     *
     * @param time 公文创建时间
     * @param personID 创建用户id
     * @param oldFileName 旧文件名称
     * @return
     * @throws Exception
     * 返回文件名
     * @author lcx
     */
    private static String getDocPath(long time,String personID,String oldFileName)throws Exception{
        String affix="";
        if(StringUtils.isNotEmpty(oldFileName) && oldFileName.lastIndexOf(".")>-1){
            affix = oldFileName.substring(oldFileName.lastIndexOf("."));
        }
        return "doc$".concat(String.valueOf(time)).concat("$").concat(personID).concat("$").concat("newDoc")+affix;
    }

    /**
     *
     * @param request
     * @param savePath 保存地址
     * @return
     * 文件修改后上传保存，如果源文件不是空，则覆盖源文件，如果源文件为空，则创建新文件
     * @author lcx
     */
    @SuppressWarnings("null")
    public static String upload(HttpServletRequest request,String savePath){

        String filename = null;
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;

        Map<String, MultipartFile> map = multipartRequest.getFileMap();

        List<MultipartFile> fileList = new ArrayList<MultipartFile>();
        Set<String> keys = map.keySet();
        for (String key : keys) {
            fileList.add(map.get(key));
        }
        String uploadMethod = request.getParameter("uploadMethod");
        if (savePath == null || savePath.equals(""))
            throw new RuntimeException("没有设置文件在服务器上的保存路径");
        File savePathFile = new File(savePath);
        if (!savePathFile.exists()) {
            if (!savePathFile.mkdirs())
                throw new RuntimeException("创建文件上传路径(" + savePath + ")失败");
        }
        boolean isMultipart = ServletFileUpload.isMultipartContent(request);// 检查表单中是否包含文件
        DiskFileItemFactory fileItemFactory = new DiskFileItemFactory();
        fileItemFactory.setSizeThreshold(4096);// 设置缓存临时文件的临界值
        fileItemFactory.setRepository(savePathFile);
        ServletFileUpload upload = new ServletFileUpload(fileItemFactory);
        upload.setHeaderEncoding(encoding);
        upload.setSizeMax(maxFileSize);

        List items = null;
        if(prepairedItems != null){
            items = prepairedItems;
        }else{
            try {
                items =  ((ServletFileUpload) upload).parseRequest(request);
            } catch (FileUploadException e) {

                e.printStackTrace();
            }

        }

        for (int i = 0; i < fileList.size(); i++) {
            CommonsMultipartFile  dfi = (CommonsMultipartFile ) fileList.get(i);
            DiskFileItem fi = (DiskFileItem)dfi.getFileItem();
            String srcFileName = dfi.getName().trim().replaceAll("\\s*", "");
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("HHmmss");
                filename = savePath + sdf.format(new Date()) + fi.getName();
                //输出到文件目录
                fi.write(new File(filename));
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
            long fileSize = dfi.getSize();
            double fitFileSize = 0;
            String fileSizeStr = "";
            DecimalFormat df = new DecimalFormat("#.00");
            if((double)fileSize > (1024*1024)){
                fitFileSize = (double)fileSize / (1024*1024);
                fileSizeStr = df.format(fitFileSize) + "M";
            }else{
                fitFileSize = (double)fileSize / 1024;
                if(fitFileSize < 1)
                    fileSizeStr = "0" + df.format(fitFileSize) + "kb";
                else
                    fileSizeStr = df.format(fitFileSize) + "kb";
            }
            System.out.println("文件大小是："+fileSizeStr);
        }
        return filename;
    }

    /**
     * 文件下载
     * @param request
     * @param response
     * @throws Exception
     */
    public static void down(HttpServletRequest request,HttpServletResponse response,String fileName) throws Exception{
        //模拟文件，myfile.txt为需要下载的文件
//        String fileName = request.getSession().getServletContext().getRealPath("upload")+"/myfile.txt";
        if (StringUtils.isBlank(fileName)){
            throw new BusinessException("无文件名");
        }
        //获取输入流
        InputStream bis = new BufferedInputStream(new FileInputStream(new File(fileName)));
        //假如以中文名下载的话(返回的默认文件名)
        String filename = fileName.substring(fileName.lastIndexOf("/") + 1);
        //转码，免得文件名中文乱码
        filename = URLEncoder.encode(filename,"UTF-8");
        //设置文件下载头
        response.addHeader("Content-Disposition", "attachment;filename=" + filename);
        //1.设置文件ContentType类型，这样设置，会自动判断下载文件类型
        response.setContentType("multipart/form-data");
        BufferedOutputStream out = new BufferedOutputStream(response.getOutputStream());
        int len = 0;
        while((len = bis.read()) != -1){
            out.write(len);
            out.flush();
        }
        out.close();
    }

    /**
     * inputStream转存文件
     */
    public static void inputStreamToFile(InputStream inputStream, String fileName){
        try {
            OutputStream os = new FileOutputStream(new File(fileName));
            int bytesRead = 0;
            byte[] buffer = new byte[8192];
            while ((bytesRead = inputStream.read(buffer, 0, 8192)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
            os.close();
            inputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}