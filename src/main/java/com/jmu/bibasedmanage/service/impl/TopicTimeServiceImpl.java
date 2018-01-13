package com.jmu.bibasedmanage.service.impl;

import java.util.Date;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.jmu.bibasedmanage.dao.BmTopicTimeDao;
import com.jmu.bibasedmanage.pojo.BmTopicTime;
import com.jmu.bibasedmanage.service.TopicService;
import com.jmu.bibasedmanage.service.TopicTimeService;
import com.jmu.bibasedmanage.util.SecurityUtils;
import com.jmu.bibasedmanage.util.UUIDUtils;
@Service
public class TopicTimeServiceImpl implements TopicTimeService{
	@Resource
   private BmTopicTimeDao  bmTopicTimeDao;
	@Override
	public void updateTopic(BmTopicTime bmTopicTime) {
		// TODO Auto-generated method stub
		bmTopicTime.setUserId(SecurityUtils.getCurrentUser().getId());
		bmTopicTimeDao.updateByPrimaryKeySelective(bmTopicTime);
	}

	@Override
	public void add(BmTopicTime bmTopicTime) {
		// TODO Auto-generated method stub
		    String id = UUIDUtils.generator();
		    bmTopicTime.setId(id);
		    bmTopicTime.setUserId(SecurityUtils.getCurrentUser().getId());
		    bmTopicTimeDao.insert(bmTopicTime);
	}
    /**
     * 查询时间的方式，原本是由UserID查询，但是最好能改成某个阶段的时间查询。
     * 应急使用当前只做了选题阶段
     * 只有一条记录。
     */
	@Override
	public BmTopicTime seacherTime(String userId) {
		// TODO Auto-generated method stub
		BmTopicTime bTT= bmTopicTimeDao.selectByUserId();
		return bTT;
	}
	public int isTimeOut(String state) {   //应该是查具体某个阶段的时间，
		// TODO Auto-generated method stub
		Date endTime = seacherTime(state).getEndTime();
		Date startTime = seacherTime(state).getBeginTime();
		Date currentTime = new Date();
		int status = 0 ;
		if(currentTime.compareTo(startTime)<0){
			status = 1;
		}
		if(currentTime.compareTo(endTime)>0){
			status = 2;
		}
		return status;
	}
}
