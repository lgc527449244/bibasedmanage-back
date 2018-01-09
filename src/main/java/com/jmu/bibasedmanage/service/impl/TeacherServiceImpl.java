package com.jmu.bibasedmanage.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

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
		 bmteacherDao.deleteByPrimaryKey(id);
	 }
	 public void add(BmTeacher bmTeacher){
		 bmTeacher.setId(UUIDUtils.generator());
		 bmTeacher.setCreateTime(new Date());
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
	public Page<BmTeacher> list(Map map, Page<BmTeacher> page) {
		    PageBounds pageBounds = new PageBounds(page.getPageNo(), page.getPageSize());
	        List<BmTeacher> teachers =  bmteacherDao.selectByLikePage(map,pageBounds);
	        page.setResult(teachers);
	        page.setTotalCountByPageList((PageList) teachers);
	        return page;
	}
	 public BmTeacher getById(String id) {
       return bmteacherDao.selectByPrimaryKey(id);
   }
}
