package com.jmu.bibasedmanage.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.jmu.bibasedmanage.pojo.BmStudent;
import com.jmu.bibasedmanage.pojo.BmTeacher;
import com.jmu.bibasedmanage.pojo.BmTeacherGroup;
import com.jmu.bibasedmanage.pojo.BmTopic;
import com.jmu.bibasedmanage.service.TeacherService;
import com.jmu.bibasedmanage.util.ResponseUtil;
import com.jmu.bibasedmanage.util.SecurityUtils;
import com.jmu.bibasedmanage.vo.JsonResponse;
import com.jmu.bibasedmanage.vo.Page;

@Controller
@RequestMapping("/teacher")
public class BmTeacherController {
	@Resource
	private TeacherService teacherService;


	/**
	 * 点击查看答辩组的信息。
	 * 
	 * @param teacherId
	 * @return Created by hhq on 2018-1-2.
	 */
	@RequestMapping("/seacher-group")
	public ModelAndView searchGroup(String teacherId) {
		BmTeacherGroup teacherGroup = teacherService.searchGroup(teacherId);
		ModelAndView modelAndView = new ModelAndView("/test.html");
		modelAndView.addObject("teacherGroup", teacherGroup);
		return modelAndView;

	}

	/**
	 * 查看该组学生的信息包括对应的课设题目
	 * 
	 * @param teacherId
	 * @return Created by hhq on 2018-1-2.
	 */
	@RequestMapping("/seacher-students")
	public ModelAndView searchStudent(String teacherId) {
		BmTeacherGroup teacherGroup = teacherService.searchStudent(teacherId);
		ModelAndView modelAndView = new ModelAndView("/test2.html");
		modelAndView.addObject("teacherGroup", teacherGroup);
		return modelAndView;
	}

	/**
	 * 添加答辩的评价和评分
	 * 
	 * @param studentId
	 * @return Created by hhq on 2018-1-2.
	 */
	@RequestMapping("/save-answer")
	@ResponseBody
	public JsonResponse addAnswerInfo(HttpServletRequest request) {
		String answerEvaluate = request.getParameter("answerEvaluate");
		String studentId = request.getParameter("studentId");
		int answerScore = Integer.parseInt(request.getParameter("answerScore"));
		teacherService.saveAnswerInfo(studentId, answerEvaluate, answerScore);
		return ResponseUtil.success();
	}
	   @RequestMapping(value = "/teacher-info.html")
	    public ModelAndView getinfo(String id){
		   ModelAndView modelAndView = new ModelAndView("/teacher/teacher_info.html");
		   if(SecurityUtils.getCurrentUser().getRoleName().equals("admin")){
			   modelAndView.addObject("id",id);  //当前要查询个人id
			  
		   }
		   else{
			   modelAndView.addObject("id", SecurityUtils.getCurrentUser().getTsId());  //当前登入的用户对应个人id
		   }
		   return modelAndView;
           
	    }
	 @RequestMapping(value = "/get", method = RequestMethod.POST)
	    @ResponseBody
	    public JsonResponse get(String id){
	        return ResponseUtil.success(teacherService.getById(id));
	    }
	 @RequestMapping(value = "/update.html",method = RequestMethod.GET)
	    public ModelAndView update(String id){
	        return new ModelAndView("/teacher/form_edit.html")
	                .addObject("id", id);
	    }
	/**
	 * 修改教师信息
	 * 
	 * @param bmTeacher
	 * @return Created by hhq on 2018-1-4.
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	public JsonResponse updateData(BmTeacher bmTeacher) {
		return ResponseUtil.success(teacherService.updateData(bmTeacher));
	}

	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@ResponseBody
	public JsonResponse delete(String id) {
		teacherService.delete(id);
		return ResponseUtil.success();
	}
	@RequestMapping(value = "/add.html", method = RequestMethod.GET)
    public ModelAndView add(){
        return new ModelAndView("teacher/form_add.html");
    }
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ResponseBody
	public JsonResponse addData(BmTeacher bmTeacher) {
		teacherService.add(bmTeacher);
		return ResponseUtil.success();
	}
    /**
     * 快速查询教师信息
     * @param teacherInfo
     * @return
     * Created by hhq on 2018-1-4.
     */
	@RequestMapping("/seacher-teacher")
	public ModelAndView searchTopic(String teacherInfo) {
		List<BmTeacher> BmTeachers = teacherService.selectByLike(teacherInfo);
		ModelAndView modelAndView = new ModelAndView("/teacher_list.html");
		modelAndView.addObject("BmTeachers", BmTeachers);
		return modelAndView;
	}
	/**
	 * 弹框选择教师
	 * @return
	 * Created by hhq on 2018-1-9.
	 */
	@RequestMapping(value = "/choose_list.html", method = RequestMethod.GET)
    public ModelAndView chooseList(){
        return new ModelAndView("teacher/teacher_list.html");
    }
	@RequestMapping(value = "/list.html", method = RequestMethod.GET)
	    public ModelAndView list(){
	        return new ModelAndView("teacher/table.html");
	    }
	 /**
     * 教师列表
     * @param map（pageNo:当前页，pageSize:每页条数）
     * @param page
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ResponseBody
    public JsonResponse listData(@RequestParam Map<String, Object> map, Page<BmTeacher> page){
        return ResponseUtil.success(teacherService.list(map, page));
    }

	@RequestMapping(value = "excel-upload.html")
	public ModelAndView excelUpload(){
		return new ModelAndView("/teacher/excel_upload.html");
	}

	@RequestMapping("/excel-upload")
	@ResponseBody
	public JsonResponse uploadFile(HttpServletRequest request){
		return ResponseUtil.success(teacherService.importExcel(request));
	}
}
