package com.jmu.bibasedmanage.service;

import com.jmu.bibasedmanage.pojo.BmTopicTime;

public interface TopicTimeService {

	void updateTopic(BmTopicTime bmTopicTime);

	void add(BmTopicTime bmTopicTime);
	
	BmTopicTime seacherTime(String userId);
    
    int isTimeOut(String state);
}
