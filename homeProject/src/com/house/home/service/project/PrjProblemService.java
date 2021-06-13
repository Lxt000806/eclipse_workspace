package com.house.home.service.project;

import java.util.List;
import java.util.Map;

import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.framework.web.login.UserContext;
import com.house.home.entity.project.PrjProblem;
import com.house.home.entity.query.PrjProblemPic;

public interface PrjProblemService extends BaseService{
	
	public Page<Map<String, Object>> getPrjProblemList(Page<Map<String, Object>> page, PrjProblem prjProblem);
	
	public List<Map<String, Object>> getPrjPromDeptList();
	
	public List<Map<String, Object>> getPrjPromTypeList(String prjPromDept);
	
	public String doSavePrjProblem(PrjProblem prjProblem);

	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, PrjProblem pejProblem ,UserContext uc);

	public List<Map<String,Object>> findPromDeptandType(int type,String pCode);
	
	public boolean isExistType(String deptCode);

	public Map<String, Object> getPrjProblem(String no);
	
	public String doUpdatePrjProblem(PrjProblem prjProblem);
	
	public void doUpdate(PrjProblem prjProblem);
	
	public Page<Map<String,Object>> goPicJqGrid(Page<Map<String,Object>> page, PrjProblem prjProblem);
	
	public PrjProblemPic getPicByName(String photoName);
	
	public List<Map<String, Object>> getPhotoList(String no);
	
	public Page<Map<String,Object>> findPrjProblemPageBySql(Page<Map<String,Object>> page, PrjProblem prjProblem );

}
