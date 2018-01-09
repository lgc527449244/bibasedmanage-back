package com.jmu.bibasedmanage.service.impl;

import java.util.Date;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.jmu.bibasedmanage.dao.BmTopicTimeDao;
import com.jmu.bibasedmanage.pojo.BmTopicTime;
import com.jmu.bibasedmanage.service.TopicService;
import com.jmu.bibasedmanage.service.TopicTimeService;
import com.jmu.bibasedmanage.util.UUIDUtils;
@Service
public class TopicTimeServiceImpl implements TopicTimeService{
	@Resource
   private BmTopicTimeDao  bmTopicTimeDao;
	@Override
	public void updateTopic(BmTopicTime bmTopicTime) {
		// TODO Auto-generated method stub
		bmTopicTimeDao.updateByPrimaryKeySelective(bmTopicTime);
	}

	@Override
	public void add(BmTopicTime bmTopicTime) {
		// TODO Auto-generated method stub
		    String id = UUIDUtils.generator();
		    bmTopicTime.setId(id);
		    bmTopicTimeDao.insert(bmTopicTime);
	}

	@Override
	public BmTopicTime seacherTime(String userId) {
		// TODO Auto-generated method stub
		BmTopicTime bTT= bmTopicTimeDao.selectByUserId(userId);
		return bTT;
	}

}
