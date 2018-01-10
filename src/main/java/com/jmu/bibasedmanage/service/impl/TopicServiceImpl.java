package com.jmu.bibasedmanage.service.impl;


import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import com.jmu.bibasedmanage.consts.CommonConst;
import com.jmu.bibasedmanage.dao.BmTeacherDao;
import com.jmu.bibasedmanage.excel.ExcelImportContext;
import com.jmu.bibasedmanage.excel.ExcelImportHandler;
import com.jmu.bibasedmanage.excel.ExcelImportService;
import com.jmu.bibasedmanage.excel.RowData;
import com.jmu.bibasedmanage.util.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
	@Autowired
	private ExcelImportService excelImportService;
	@Autowired
	private BmTeacherDao bmTeacherDao;

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

	public String importExcel(HttpServletRequest request) {
		//文件上传
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String savePath = CommonConst.FILE_UPLOAD_PATH + sdf.format(new Date()) + "//";
		String fileName = FileUtils.upload(request, savePath);
		//导数据进数据库
		String ret = null;

		final List<BmTopic> insertList = new ArrayList<BmTopic>();
		try {
			ret = excelImportService.importExcel(fileName, new ExcelImportHandler() {
				public String importRow(int rowIndex, RowData rowData, ExcelImportContext context) {
					String name = rowData.getContentByColumnName("题目");
					String content = rowData.getContentByColumnName("内容");
					String required = rowData.getContentByColumnName("要求");
					String jobNumber = rowData.getContentByColumnName("负责老师工号");
					String teacherName = rowData.getContentByColumnName("负责老师");
					String studentNumStr = rowData.getContentByColumnName("人数限制");

					String teacherId = null;
					int studentNum = 0;

					//数据校验
					StringBuffer errMsg = new StringBuffer();
					BmTeacher oldTeacher = bmTeacherDao.selectByJobNumber(jobNumber);
					if(oldTeacher == null){
						errMsg.append("工号出错；");
					}else{
						teacherId = oldTeacher.getId();
					}
					if(StringUtils.isBlank(studentNumStr)){
						errMsg.append("人数限制不能为空;");
					}else{
						studentNumStr = studentNumStr.trim();
						try{
							studentNum = Integer.parseInt(studentNumStr);
							if(studentNum < 0){
								errMsg.append("人数限制必须大于0;");
							}
						}catch (Exception e){
							errMsg.append(e.getMessage());
						}
					}

					if(errMsg.length() > 0){
						return errMsg.toString();
					}

					//TODO 设置数据来源
					BmTopic topic = new BmTopic();
					topic.setName(name);
					topic.setContent(content);
					topic.setRequired(required);
					topic.setStudentNum(studentNum);
					topic.setTeacherId(teacherId);

					insertList.add(topic);

					return null;
				}
			});
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		insertAll(insertList);
		return ret;
	}

	/**
	 * 批量导入，如果失败则全部回滚
	 * @param bmTopics
	 */
	@org.springframework.transaction.annotation.Transactional
	public void insertAll(List<BmTopic> bmTopics){
		for (BmTopic topic : bmTopics) {
			topic.setId(UUIDUtils.generator());
			topic.setCreateTime(new Date());
			topic.setRecordStatus(CommonConst.RECORD_STATUS_NORMAL);
			topic.setStatus(CommonConst.STATUS_ENABLE);
//			topic.setDataSources(CommonConst.DATA_SOURCES_EXCEL);
			bmTopicDao.insert(topic);
		}
	}
}
