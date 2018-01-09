package com.jmu.bibasedmanage.service;


import java.util.List;
import java.util.Map;

import com.jmu.bibasedmanage.pojo.BmTeacher;
import com.jmu.bibasedmanage.pojo.BmTopic;
import com.jmu.bibasedmanage.vo.Page;

public interface TopicService {
	public List<BmTopic> selectByLike(String topicInfo);
	public void addTopic(BmTopic bmTopic);
	public BmTopic updateTopic(BmTopic bmTopic);
	public boolean chooseTopic(String choose, String id, String studentId);
	public BmTopic selectTopicById(String id);
	 /**
     * 分页查询
     * @param map（pageNo:当前页,pageSize：每页条数）
     * @param page
     * @return
     */
    Page<BmTopic> list(Map map, Page<BmTopic> page);
    public BmTopic selectTopAndteacherById(String id);
	public BmTopic selectByStudentId(String StudentId);
	

}
