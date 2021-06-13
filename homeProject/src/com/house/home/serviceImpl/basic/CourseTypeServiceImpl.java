package com.house.home.serviceImpl.basic;

import java.util.Map;

import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.house.home.dao.basic.CourseTypeDao;
import com.house.home.entity.basic.CourseType;
import com.house.home.service.basic.CourseTypeService;

@SuppressWarnings("serial")
@Service 
public class CourseTypeServiceImpl extends BaseServiceImpl implements CourseTypeService {
	@Autowired
	private  CourseTypeDao courseTypeDao;

	@Override
	public Page<Map<String, Object>> findPageBySql(
			Page<Map<String, Object>> page, CourseType courseType) {
		return courseTypeDao.findPageBySql(page, courseType);
	}

	@Override
	public CourseType getByCode(String code) {
		return courseTypeDao.getByCode(code);
	}

	@Override
	public CourseType getByDescr(String descr, String code) {
		return courseTypeDao.getByDescr(descr,code);
	}

}
