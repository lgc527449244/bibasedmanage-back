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
import com.jmu.bibasedmanage.vo.JsonResponse;
import com.jmu.bibasedmanage.vo.Page;

@Controller
@RequestMapping("/topic")
public class TopicController{
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
	    @RequestMapping(value = "/list-topic.html", method = RequestMethod.GET)
	    public ModelAndView listtopic(){
	        return new ModelAndView("topic/student_choose.html");
	    }
	    /**
	     * 管理员对应的课题的视图
	     * @return
	     * Created by hhq on 2018-1-7.
	     */
		@RequestMapping(value = "/list.html", method = RequestMethod.GET)
		    public ModelAndView list(String userId){
		        return new ModelAndView("topic/table.html").addObject("bmTopictime", topicTimeService.seacherTime(userId));
		    }
		/**
		 * 学生自己的课题的视图
		 * @param StudentId
		 * @return
		 * Created by hhq on 2018-1-7.
		 */
		 @RequestMapping(value = "/list-persontopic.html", method = RequestMethod.GET)
		    public ModelAndView listpersontopic(String StudentId){
			    BmTopic bmTopic = topicService.selectByStudentId(StudentId);
		        return new ModelAndView("topic/student_topic.html").addObject("bmTopic", bmTopic);
		    }
		 /**
		  * 老师自己所对应的课题的视图
		  * @return
		  * Created by hhq on 2018-1-7.
		  */
		  @RequestMapping(value = "/list-teacher-topic.html", method = RequestMethod.GET)
		    public ModelAndView listpersontopic(){
			     return new ModelAndView("topic/teacher_topic.html");
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
	    @RequestMapping(value = "/add.html", method = RequestMethod.GET)
	    public ModelAndView add(){
	        return new ModelAndView("topic/form_add.html");
	    }
		/**
		 * 老师增加课题
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
		 @RequestMapping(value = "/update.html",method = RequestMethod.GET)
		    public ModelAndView update(String id){
		        return new ModelAndView("/topic/form_edit.html")
		                .addObject("bmtopic", topicService.selectTopAndteacherById(id));
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
			topicService.updateTopic(bmtopic);
			return ResponseUtil.success(topicService.updateTopic(bmtopic)) ;
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
	    	String studentId = request.getParameter("studentId");//学生的id
			if(topicService.chooseTopic(choose,id,studentId)){
				return ResponseUtil.success();
			}
			else{
				return ResponseUtil.error("你已经选过课题了！");
			}
			
	 }	
		
		 
}
