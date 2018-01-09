package com.jmu.bibasedmanage.service.impl;


import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.jmu.bibasedmanage.dao.BmStudentDao;
import com.jmu.bibasedmanage.dao.BmTopicDao;

import com.jmu.bibasedmanage.pojo.BmStudent;
import com.jmu.bibasedmanage.pojo.BmTeacher;
import com.jmu.bibasedmanage.pojo.BmTopic;
import com.jmu.bibasedmanage.service.TopicService;
import com.jmu.bibasedmanage.util.UUIDUtils;
import com.jmu.bibasedmanage.vo.Page;
@Service
public class TopicServiceImpl implements TopicService{
	@Resource
	private BmTopicDao bmTopicDao;
	@Resource
	private BmStudentDao bmStudentDao;
	public List<BmTopic> selectByLike(String topicInfo){
		return bmTopicDao.selectByLike(topicInfo) ;
	}
	/**
	 * 老师增加课题
	 */
	@Transactional
	public void addTopic(BmTopic bmTopic){
		bmTopic.setId(UUIDUtils.generator());
		bmTopic.setCreateTime(new Date());
		bmTopicDao.insert(bmTopic);
	}
	public BmTopic updateTopic(BmTopic bmTopic){
		bmTopic.setUpdateTime(new Date());
		 bmTopicDao.updateByPrimaryKeySelective(bmTopic);
		 return bmTopic;
	}
	public BmTopic selectTopicById(String id){
		return bmTopicDao.selectByPrimaryKey(id);
	}
	/**
	 * 学生选题
	 */
	@Transactional
	public boolean chooseTopic(String choose,String id,String studentId){
	   BmStudent bmStudent = bmStudentDao.selectByPrimaryKey(studentId);
	   BmTopic bmTopic = bmTopicDao.selectByPrimaryKey(id);
		if(choose.equals("Y")&&(bmStudent.getTopicId()==null ||bmStudent.getTopicId().equals(""))){  //如果选择该课题
			int nowNum =((bmTopic.getStudentNowNum()==null)?0:bmTopic.getStudentNowNum())+1;
			bmTopic.setStudentNowNum(nowNum);
			updateTopic(bmTopic);
			bmStudent.setTopicId(id);
			bmStudent.setUpdateTime(new Date());
			bmStudentDao.updateByPrimaryKeySelective(bmStudent);
			return true;
		}
		else if(choose.equals("N")){
			bmTopic.setStudentNowNum(bmTopic.getStudentNowNum()-1);
			updateTopic(bmTopic);
			bmStudent.setTopicId("");
			bmStudent.setUpdateTime(new Date());
			bmStudentDao.updateByPrimaryKeySelective(bmStudent);
			return true;
		}
		else{
			return false;
		}
	}
	 /**
	  * 分页查询课题信息
	  */
	@Override
	public Page<BmTopic> list(Map map, Page<BmTopic> page) {
		    PageBounds pageBounds = new PageBounds(page.getPageNo(), page.getPageSize());
//		    if(map.containsKey("studentId")){
//			    String studentId = (String) map.get("studentId");
//			    String topicId= bmStudentDao.selectByPrimaryKey(studentId).getTopicId();//获取是否该学生选过课题
//			    map.put("topicId", topicId);
//    }
	        List<BmTopic> bmTopics =  bmTopicDao.selectByLikePage(map,pageBounds);
	        page.setResult(bmTopics);
	        page.setTotalCountByPageList((PageList) bmTopics);
	        return page;
	}
	@Override
	public BmTopic selectTopAndteacherById(String id) {
		// TODO Auto-generated method stub
		return bmTopicDao.selectById(id);
	}
	public BmTopic selectByStudentId(String StudentId){
		return 	selectTopAndteacherById(bmStudentDao.selectByPrimaryKey(StudentId).getTopicId());
		
	}
}
