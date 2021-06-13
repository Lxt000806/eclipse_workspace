package com.house.home.service.basic;

import java.util.List;
import java.util.Map;

import com.house.framework.bean.Result;
import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.basic.SignIn;
import com.house.home.entity.query.SignInPic;

public interface SignInService extends BaseService {

	/**SignIn分页信息
	 * @param page
	 * @param signIn
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, SignIn signIn);
	
	
	/**按签到员汇总
	 * @param page
	 * @param signIn
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql_CZY(Page<Map<String,Object>> page, SignIn signIn);

	/**获取当天地图签到次数
	 * @param signIn
	 * @return
	 */
	public long getSignCountNow(SignIn signIn);
	
	public List<Map<String,Object>> getSignInType1List();
	
	public List<Map<String,Object>> getSignInType2List(String signInType1);
	
	public List<Map<String,Object>> getPrjItemList(String custCode, String prjItem);
	
	public String getSeqNo(String tableName);
	
	public SignInPic getPicByName(String photoName);
	
	public Page<Map<String,Object>> goJqGrid_SignInType2(Page<Map<String,Object>> page, SignIn signIn);
	
	public Page<Map<String,Object>> goJqGrid_DetailSignInType2(Page<Map<String,Object>> page, SignIn signIn);
	
	public List<Map<String,Object>> goJqGridSignInPicList(Page<Map<String,Object>> page, String no);
	
	public List<Map<String, Object>> findNoSendYunPic();
	
	public List<Map<String, Object>> findNoSendYunWorkSignPic();

	public boolean existsFirstPass(String custCode, String prjItem);
	/**
	 * 工地信息——工地签到
	 * @author	created by zb
	 * @date	2019-6-21--上午10:02:18
	 * @param page
	 * @param signIn
	 */
	public Page<Map<String,Object>> getPrjSignList(Page<Map<String, Object>> page, SignIn signIn);
	
	public Result updateEndDate(String custCode,String prjItem1, String signCzy);
	
	public List<Map<String,Object>> getPrjProgList(String custCode);
	
	public List<Map<String, Object>> getSignInPic(String no);
	
	public Boolean existsDesignPicPrgChange(String custCode);
	
	public Long saveFirstAddConfirmApp(String custCode, String prjItem1, String isPass);
}
