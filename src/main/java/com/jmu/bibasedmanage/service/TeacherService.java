package com.jmu.bibasedmanage.service;

import java.util.List;
import java.util.Map;

import org.springframework.web.servlet.ModelAndView;

import com.jmu.bibasedmanage.pojo.BmStudent;
import com.jmu.bibasedmanage.pojo.BmTeacher;
import com.jmu.bibasedmanage.pojo.BmTeacherGroup;
import com.jmu.bibasedmanage.vo.Page;

public interface TeacherService {
	 public BmTeacherGroup searchGroup(String teacherId);
	 public BmTeacherGroup searchStudent(String teacherId);
	 public void saveAnswerInfo(String studentId, String answerEvaluate, int answerScore);
	 public BmTeacher updateData(BmTeacher bmTeacher);
	 public void delete(String id);
	 public void add(BmTeacher bmTeacher);
	 public List<BmTeacher> selectByLike(String teacherInfo);
	 /**
	     * 分页查询
	     * @param map（pageNo:当前页,pageSize：每页条数）
	     * @param page
	     * @return
	     */
	Page<BmTeacher> list(Map map, Page<BmTeacher> page);
	 public BmTeacher getById(String id);
}
