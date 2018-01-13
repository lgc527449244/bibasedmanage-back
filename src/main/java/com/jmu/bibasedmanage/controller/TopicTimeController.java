package com.jmu.bibasedmanage.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.Resource;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;



import com.jmu.bibasedmanage.mvc.DateConverter;
import com.jmu.bibasedmanage.pojo.BmStudent;
import com.jmu.bibasedmanage.pojo.BmTopicTime;
import com.jmu.bibasedmanage.service.TopicTimeService;
import com.jmu.bibasedmanage.util.ResponseUtil;
import com.jmu.bibasedmanage.vo.JsonResponse;

@Controller
@RequestMapping("/topictime")
public class TopicTimeController  {
	@Resource
	private TopicTimeService topicTimeService;
	 /**
	  *修改课题开始结束时间。日期的转换
	  * 
	   * @return
	   * Created by hhq on 2018-1-8.
	   */
   @RequestMapping(value = "/update-time", method = RequestMethod.POST)
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
	  
    /**
     * 
     * 日期转换
     * @author zhangqijian
     * @param binder
     * <pre>
     * 修改时间	修改人	修改原因
     * 2017-12-7  zhangqijian 新建
     * </pre>
     */
    @InitBinder  
    public void initBinder(WebDataBinder binder) {  
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
        dateFormat.setLenient(false);  
        //true:允许输入空值，false:不能为空值
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true)); 
      
    }
	
}
