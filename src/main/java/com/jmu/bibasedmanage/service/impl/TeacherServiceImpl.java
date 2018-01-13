package com.jmu.bibasedmanage.service.impl;

import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.jmu.bibasedmanage.consts.CommonConst;
import com.jmu.bibasedmanage.excel.ExcelImportContext;
import com.jmu.bibasedmanage.excel.ExcelImportHandler;
import com.jmu.bibasedmanage.excel.ExcelImportService;
import com.jmu.bibasedmanage.excel.RowData;
import com.jmu.bibasedmanage.util.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.jmu.bibasedmanage.dao.BmStudentDao;
import com.jmu.bibasedmanage.dao.BmTeacherDao;
import com.jmu.bibasedmanage.dao.BmTeacherGroupDao;
import com.jmu.bibasedmanage.pojo.BmStudent;
import com.jmu.bibasedmanage.pojo.BmTeacher;
import com.jmu.bibasedmanage.pojo.BmTeacherGroup;
import com.jmu.bibasedmanage.service.TeacherService;
import com.jmu.bibasedmanage.util.SecurityUtils;
import com.jmu.bibasedmanage.util.UUIDUtils;
import com.jmu.bibasedmanage.vo.Page;
/**
 * 
 * @author chongzi
 *
 */
@Service
public class TeacherServiceImpl implements TeacherService{
	@Resource
    private BmTeacherGroupDao bmTeacherGroupDao;
	@Resource
	private BmStudentDao bmStudentDao;
	@Resource
	private BmTeacherDao bmteacherDao;
	@Autowired
	private ExcelImportService excelImportService;

	/**
	 * 点击查看答辩组的信息。
	 * @param teacherId
	 * @return
	 * Created by hhq on 2018-1-2.
	 */
	public BmTeacherGroup searchGroup(String teacherId){
				return bmTeacherGroupDao.selectByTeacherid(teacherId);
	}
	/**
	 * 点击查看答辩组学生的信息。
	 * @param teacherId
	 * @return
	 * Created by hhq on 2018-1-2.
	 */
	 public BmTeacherGroup searchStudent(String teacherId){
		 return bmTeacherGroupDao.searchStudent(teacherId).get(0);
	 }
	    /**
		 * 增加对该学生的评价和评分
		 * @param teacherId,answerEvaluate,answerScore
		 * @return
		 * Created by hhq on 2018-1-2.
		 */
	 public void saveAnswerInfo(String studentId,String answerEvaluate,int answerScore){
		 BmStudent bmStudent= bmStudentDao.selectByPrimaryKey(studentId);
		 bmStudent.setAnswerEvaluate(answerEvaluate);
		 bmStudent.setAnswerScore(answerScore);
		 bmStudentDao.updateByPrimaryKeySelective(bmStudent);
	 }
	   /**
		 * 修改教师信息。
		 * @param bmTeacher
		 * @return
		 * Created by hhq on 2018-1-4.
		 */
	 public BmTeacher updateData(BmTeacher bmTeacher){
		 bmTeacher.setUpdateTime(new Date());
		 bmteacherDao.updateByPrimaryKeySelective(bmTeacher);
		 return bmTeacher;
	 }
	 public void delete(String id){
		BmTeacher teacher = new BmTeacher();
		teacher.setId(id);
		teacher.setRecordStatus(CommonConst.RECORD_STATUS_DELETED);
	 	bmteacherDao.updateByPrimaryKeySelective(teacher);
	 }
	 public void add(BmTeacher bmTeacher){
		 bmTeacher.setId(UUIDUtils.generator());
		 bmTeacher.setCreateTime(new Date());
		 bmTeacher.setRecordStatus(CommonConst.RECORD_STATUS_NORMAL);
		 if(SecurityUtils.getCurrentUser().getRoleName().equals("admin")){
			 bmTeacher.setStatus("ENABLE");    
		 }
		 bmteacherDao.insert(bmTeacher);
	 }
	 /**
	  * 查询教师信息
	  */
	 public List<BmTeacher> selectByLike(String teacherInfo){
		return bmteacherDao.selectByLike(teacherInfo);
		 
	 }
	 /**
	  * 分页查询教师信息
	  */
	@Override
	public Page<BmTeacher> list(Map map, Page<BmTeacher> page) {
		    PageBounds pageBounds = new PageBounds(page.getPageNo(), page.getPageSize());
	        List<BmTeacher> teachers =  bmteacherDao.selectByLikePage(map,pageBounds);
	        page.setResult(teachers);
	        page.setTotalCountByPageList((PageList) teachers);
	        return page;
	}
	@Override
	 public BmTeacher getById(String id) {
       return bmteacherDao.selectByPrimaryKey(id);
   }

	public String importExcel(HttpServletRequest request) {
		//文件上传
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String savePath = CommonConst.FILE_UPLOAD_PATH + sdf.format(new Date()) + "//";
		String fileName = FileUtils.upload(request, savePath);
		//导数据进数据库
		String ret = null;

		final List<BmTeacher> insertList = new ArrayList<BmTeacher>();
		try {
			ret = excelImportService.importExcel(fileName, new ExcelImportHandler() {
				public String importRow(int rowIndex, RowData rowData, ExcelImportContext context) {
					String jobNumber = rowData.getContentByColumnName("工号");
					String college = rowData.getContentByColumnName("学院");
					String jobTitle = rowData.getContentByColumnName("职称");
					String name = rowData.getContentByColumnName("姓名");
					String email = rowData.getContentByColumnName("电子邮箱");
					String mobile = rowData.getContentByColumnName("手机");

					//数据校验
					StringBuffer errMsg = new StringBuffer();
					BmTeacher oldTeacher = bmteacherDao.selectByJobNumber(jobNumber);
					if(oldTeacher != null){
						errMsg.append("该工号的教师已存在；");
					}

					if(errMsg.length() > 0){
						return errMsg.toString();
					}

					//TODO 设置数据来源
					BmTeacher teacher = new BmTeacher();
					teacher.setJobNumber(jobNumber);
					teacher.setCollege(college);
					teacher.setJobTitle(jobTitle);
					teacher.setName(name);
					teacher.setEmail(email);
					teacher.setMobile(mobile);
					insertList.add(teacher);

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
	 * @param bmTeachers
	 */
	@Transactional
	public void insertAll(List<BmTeacher> bmTeachers){
		for (BmTeacher teacher : bmTeachers) {
			teacher.setId(UUIDUtils.generator());
			teacher.setCreateTime(new Date());
			teacher.setRecordStatus(CommonConst.RECORD_STATUS_NORMAL);
			teacher.setStatus(CommonConst.STATUS_ENABLE);
//			teacher.setDataSources(CommonConst.DATA_SOURCES_EXCEL);
			bmteacherDao.insert(teacher);
		}
	}
}
