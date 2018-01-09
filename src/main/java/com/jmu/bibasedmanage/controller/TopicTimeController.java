package com.jmu.bibasedmanage.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jmu.bibasedmanage.pojo.BmStudent;
import com.jmu.bibasedmanage.pojo.BmTopicTime;
import com.jmu.bibasedmanage.service.TopicTimeService;
import com.jmu.bibasedmanage.util.ResponseUtil;
import com.jmu.bibasedmanage.vo.JsonResponse;

@Controller
@RequestMapping("/topictime")
public class TopicTimeController{
	@Resource
	private TopicTimeService topicTimeService;
	 /**
	  *修改课题开始结束时间。
	  * 
	   * @return
	   * Created by hhq on 2018-1-8.
	   */
   @RequestMapping("/update-time")
	@ResponseBody
	 public JsonResponse updateTime(BmTopicTime bmTopicTime){
	     topicTimeService.updateTopic(bmTopicTime);
		return ResponseUtil.success() ;
}
   /**
	  *设置课题开始结束时间。
	  * 
	   * @return
	   * Created by hhq on 2018-1-8.
	   */
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ResponseBody
	public JsonResponse addData(BmTopicTime bmTopicTime) {
		topicTimeService.add(bmTopicTime);
		return ResponseUtil.success();
	}
	
}
