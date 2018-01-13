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

import com.jmu.bibasedmanage.pojo.BmTeacher;
import com.jmu.bibasedmanage.pojo.BmTopic;
import com.jmu.bibasedmanage.pojo.BmTopicTime;
import com.jmu.bibasedmanage.service.TopicService;
import com.jmu.bibasedmanage.service.TopicTimeService;
import com.jmu.bibasedmanage.util.ResponseUtil;
import com.jmu.bibasedmanage.util.SecurityUtils;
import com.jmu.bibasedmanage.vo.JsonResponse;
import com.jmu.bibasedmanage.vo.Page;

@Controller
@RequestMapping("/topic")
public class TopicController {
	@Resource
	private TopicService topicService;
	@Resource
	private TopicTimeService topicTimeService;
	/**
	 * 点击查询课题信息。
	 * 
	 * @return
	 * Created by hhq on 2018-1-3.
	 */
	 @RequestMapping("/seacher-topic")
	 public ModelAndView searchTopic(String topicInfo){
		List<BmTopic> bmTopics = topicService.selectByLike(topicInfo);
		ModelAndView modelAndView = new ModelAndView("/test3.html");
		modelAndView.addObject("bmTopics", bmTopics);
		return modelAndView;
	 }
	    /**
	     * 学生选题的视图
	     * @return
	     * Created by hhq on 2018-1-7.
	     */
	    @RequestMapping(value = "/choose-topic.html", method = RequestMethod.GET)
	    public ModelAndView listTopic(){
	    	int statu=topicTimeService.isTimeOut(null);
	    	 String StudentId =SecurityUtils.getCurrentUser().getTsId();
			 BmTopic bmTopic = topicService.selectByStudentId(StudentId);
	    	if(statu==0){
	    		ModelAndView modelAndView = new ModelAndView("topic/student_choose.html");
	    		modelAndView.addObject("bmTopic", bmTopic);
	    		return modelAndView;
	    	}
	    	else{   	
	        	return new ModelAndView("topic/topic_choose.html").addObject("status", statu);
	    	}
	    }
	    /**
	     * 管理员对应的课题的视图
	     * @return
	     * Created by hhq on 2018-1-7.
	     */
		@RequestMapping(value = "/list.html", method = RequestMethod.GET)
		    public ModelAndView list(){
			   ModelAndView modelAndView = new ModelAndView("topic/table.html");
			   String userId ="";
			   modelAndView.addObject("bmTopictime", topicTimeService.seacherTime(userId));
				return modelAndView;
		    }
		/**
		 * 学生自己的课题的视图
		 * @param StudentId
		 * @return
		 * Created by hhq on 2018-1-7.
		 */
		 @RequestMapping(value = "/list-persont-topic.html", method = RequestMethod.GET)
		    public ModelAndView listPerTopic(){
			    String StudentId =SecurityUtils.getCurrentUser().getTsId();
			    BmTopic bmTopic = topicService.selectByStudentId(StudentId);
				ModelAndView modelAndView = new ModelAndView("topic/student_topic.html");
				modelAndView.addObject("bmtopic", bmTopic);
				return modelAndView;
		    }
		 /**
		  * 老师自己所对应的课题的视图
		  * @return
		  * Created by hhq on 2018-1-7.
		  */
		  @RequestMapping(value = "/list-teacher-topic.html", method = RequestMethod.GET)
		    public ModelAndView listPersonTopic(){
			     return new ModelAndView("topic/teacher_topic.html");
		    }
		  /**
			  * 老师查看课题的视图
			  * @return
			  * Created by hhq on 2018-1-7.
			  */
			  @RequestMapping(value = "/list-all-topic.html", method = RequestMethod.GET)
			    public ModelAndView listtopic(){
				     return new ModelAndView("topic/teacher_view_table.html");
			    }
		 /**
	     * 查询课题列表
	     * @param map（pageNo:当前页，pageSize:每页条数）
	     * @param page
	     * @return
	     */
	    @RequestMapping(value = "/list", method = RequestMethod.POST)
	    @ResponseBody
	    public JsonResponse listData(@RequestParam Map<String, Object> map, Page<BmTopic> page){
	        return ResponseUtil.success(topicService.list(map, page));
	    }
	    /**
	     * 管理员增加课题
	     * @return
	     * Created by hhq on 2018-1-9.
	     */
	    @RequestMapping(value = "/admin-add.html", method = RequestMethod.GET)
	    public ModelAndView admindAdd(){
	        return new ModelAndView("topic/admin_add.html");
	    }
	    /**
	     *老师增加课题
	     * @return
	     * Created by hhq on 2018-1-9.
	     */
	    @RequestMapping(value = "/add.html", method = RequestMethod.GET)
	    public ModelAndView add(){
	    	    String teacherId =SecurityUtils.getCurrentUser().getTsId();
				ModelAndView modelAndView = new ModelAndView("topic/form_add.html");
				modelAndView.addObject("teacherId", teacherId);
				return modelAndView;
	    }
		/**
		 * 增加课题
		 * @param bmtopic
		 * @return
		 * Created by hhq on 2018-1-3.
		 */
		 @RequestMapping("/add-topic")
		 @ResponseBody
		 public JsonResponse addTopic(BmTopic bmtopic){
			topicService.addTopic(bmtopic);
			return ResponseUtil.success() ;
		 }
		    @RequestMapping(value = "/get", method = RequestMethod.POST)
		    @ResponseBody
		    public JsonResponse get(String id){
		        return ResponseUtil.success(topicService.selectTopAndteacherById(id));
		    }
		    /**
		     * 管理员修改课题
		     * @param id
		     * @return
		     * Created by hhq on 2018-1-9.
		     */
		    @RequestMapping(value = "/admin-update.html",method = RequestMethod.GET)
		    public ModelAndView updateAdmin(String id){
		    	ModelAndView modelAndView =new ModelAndView("topic/admin_edit.html");
		    	modelAndView.addObject("bmtopic", topicService.selectTopAndteacherById(id));
		        return modelAndView;
		    }
		 @RequestMapping(value = "/update.html",method = RequestMethod.GET)
		    public ModelAndView update(String id){
			 ModelAndView modelAndView =new ModelAndView("topic/form_edit.html");
		    	modelAndView.addObject("bmtopic", topicService.selectTopAndteacherById(id));
		        return modelAndView;
		    }
		 /**
		  *修改课题信息。
		  * 
		   * @return
		   * Created by hhq on 2018-1-3.
		   */
	    @RequestMapping("/update-topic")
		@ResponseBody
		 public JsonResponse updateTopic(BmTopic bmtopic){
			return ResponseUtil.success(topicService.updateTopic(bmtopic)) ;
	 }
	    /**
		  *删除课题信息。
		  * 
		   * @return
		   * Created by hhq on 2018-1-3.
		   */
	    @RequestMapping("/delete")
		@ResponseBody
		 public JsonResponse delete(String id){
	    	if(topicService.deleteBmTopic(id)){
	    		return ResponseUtil.success() ;
	    	}
			return ResponseUtil.error("该课题不能被删除");
			
	 }
	     /**
	      * 学生选题
	      * @param bmtopic
	      * @return
	      * Created by hhq on 2018-1-3.
	      */
	     @RequestMapping("/choose-topic")
	 	@ResponseBody
		 public JsonResponse chooseTopic(HttpServletRequest request){
	    	String choose = request.getParameter("choose");
	    	String id = request.getParameter("id");  //课题id
	    	String studentId =  SecurityUtils.getCurrentUser().getTsId(); //request.getParameter("studentId");//学生的id
			if(topicService.chooseTopic(choose,id,studentId)){
				return ResponseUtil.success();
			}
			else{
				return ResponseUtil.error("你已经选过课题了！");
			}
			
	 }	
		
		 
}
