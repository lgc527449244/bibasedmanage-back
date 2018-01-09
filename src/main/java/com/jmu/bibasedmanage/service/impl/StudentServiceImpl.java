package com.jmu.bibasedmanage.service.impl;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.jmu.bibasedmanage.consts.CommonConst;
import com.jmu.bibasedmanage.dao.BmStudentDao;
import com.jmu.bibasedmanage.excel.ExcelImportContext;
import com.jmu.bibasedmanage.excel.ExcelImportHandler;
import com.jmu.bibasedmanage.excel.ExcelImportService;
import com.jmu.bibasedmanage.excel.RowData;
import com.jmu.bibasedmanage.pojo.BmStudent;
import com.jmu.bibasedmanage.service.StudentService;
import com.jmu.bibasedmanage.util.FileUtils;
import com.jmu.bibasedmanage.util.UUIDUtils;
import com.jmu.bibasedmanage.vo.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import java.util.List;

/**
 * Created by ljc on 2017/12/18.
 */
@Transactional
@Service
public class StudentServiceImpl implements StudentService{

    @Autowired
    private BmStudentDao bmStudentDao;
    @Autowired
    private ExcelImportService excelImportService;

    public Page<BmStudent> list(Map map, Page<BmStudent> page) {
        PageBounds pageBounds = new PageBounds(page.getPageNo(), page.getPageSize());
        List<BmStudent> students = bmStudentDao.selectByPage(pageBounds, map);
        page.setResult(students);
        page.setTotalCountByPageList((PageList) students);
        return page;
    }

    public String add(BmStudent bmStudent) {
        String id = UUIDUtils.generator();
        bmStudent.setId(id);
        bmStudent.setCreateTime(new Date());
        bmStudent.setRecordStatus(CommonConst.RECORD_STATUS_NORMAL);
        bmStudentDao.insert(bmStudent);
        return id;
    }

    public void update(BmStudent bmStudent) {
        bmStudent.setUpdateTime(new Date());
        bmStudentDao.updateByPrimaryKeySelective(bmStudent);
    }

    public void delete(String id) {
        BmStudent bmStudent = new BmStudent();
        bmStudent.setId(id);
        bmStudent.setRecordStatus(CommonConst.RECORD_STATUS_DELETED);
        bmStudentDao.updateByPrimaryKeySelective(bmStudent);
    }

    public BmStudent getById(String id) {
        return bmStudentDao.selectByPrimaryKey(id);
    }

    public String importExcel(HttpServletRequest request) {
        //文件上传
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String savePath = CommonConst.FILE_UPLOAD_PATH + sdf.format(new Date()) + "//";
        String fileName = FileUtils.upload(request, savePath);
        //导数据进数据库
        String ret = null;

        final List<BmStudent> insertList = new ArrayList<BmStudent>();
        try {
            ret = excelImportService.importExcel(fileName, new ExcelImportHandler() {
                public String importRow(int rowIndex, RowData rowData, ExcelImportContext context) {
                    String studentId = rowData.getContentByColumnName("学号");
                    String grade = rowData.getContentByColumnName("年级");
                    String college = rowData.getContentByColumnName("学院");
                    String major = rowData.getContentByColumnName("专业");
                    String classes = rowData.getContentByColumnName("班级");
                    String name = rowData.getContentByColumnName("姓名");
                    String sexStr = rowData.getContentByColumnName("性别");
                    String email = rowData.getContentByColumnName("电子邮箱");
                    String mobile = rowData.getContentByColumnName("手机");

                    String sex = "";
                    if ("男".equals(sexStr)){
                        sex = "MALE";
                    }else if ("女".equals(sexStr)){
                        sex = "FEMALE";
                    }
                    //数据校验
                    StringBuffer errMsg = new StringBuffer();
                    BmStudent oldStudent = bmStudentDao.selectByStudentId(studentId);
                    if(oldStudent != null){
                        errMsg.append("该学号的学生已存在；");
                    }

                    if(errMsg.length() > 0){
                        return errMsg.toString();
                    }

                    //TODO 设置数据来源
                    BmStudent student = new BmStudent();
                    student.setStudentId(studentId);
                    student.setGrade(Integer.parseInt(grade));
                    student.setCollege(college);
                    student.setMajor(major);
                    student.setClasses(classes);
                    student.setName(name);
                    student.setSex(sex);
                    student.setEmail(email);
                    student.setMobile(mobile);
                    insertList.add(student);

                    return null;
                }
            });
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        insertAll(insertList);
        return ret;
    }

    /**
     * 批量导入，如果失败则全部回滚
     * @param bmStudents
     */
    @Transactional
    public void insertAll(List<BmStudent> bmStudents){
        for (BmStudent student : bmStudents) {
            student.setId(UUIDUtils.generator());
            student.setCreateTime(new Date());
            student.setRecordStatus(CommonConst.RECORD_STATUS_NORMAL);
            student.setStatus(CommonConst.STATUS_ENABLE);
            student.setDataSources(CommonConst.DATA_SOURCES_EXCEL);
            bmStudentDao.insert(student);
        }
    }

}
