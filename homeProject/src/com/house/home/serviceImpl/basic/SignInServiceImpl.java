package com.house.home.serviceImpl.basic;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.house.framework.bean.Result;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.client.service.evt.SignDetailEvt;
import com.house.home.dao.basic.SignInDao;
import com.house.home.entity.basic.SignIn;
import com.house.home.entity.query.SignInPic;
import com.house.home.service.basic.SignInService;

@SuppressWarnings("serial")
@Service
public class SignInServiceImpl extends BaseServiceImpl implements SignInService {

	@Autowired
	private SignInDao signInDao;
	
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, SignIn signIn){
		return signInDao.findPageBySql(page, signIn);
	}

	//按签到员汇总
	public Page<Map<String,Object>> findPageBySql_CZY(Page<Map<String,Object>> page, SignIn signIn){
		return signInDao.findPageBySql_CZY(page, signIn);
	}
	
	@Override
	public long getSignCountNow(SignIn signIn) {
		return signInDao.getSignCountNow(signIn);
	}
	
	@Override
	public List<Map<String,Object>> getSignInType1List(){
		return signInDao.getSignInType1List();
	}
	
	@Override
	public List<Map<String,Object>> getSignInType2List(String signInType1){
		return signInDao.getSignInType2List(signInType1);
	}
	
	@Override
	public List<Map<String,Object>> getPrjItemList(String custCode, String prjItem){
		return signInDao.getPrjItemList(custCode, prjItem);
	}

	@Override
	public String getSeqNo(String tableName){
		return super.getSeqNo(tableName);
	}
	
	@Override
	public SignInPic getPicByName(String photoName){
		return signInDao.getPicByName(photoName);
	}
	
	@Override
	public Page<Map<String,Object>> goJqGrid_SignInType2(Page<Map<String,Object>> page, SignIn signIn){
		return signInDao.goJqGrid_SignInType2(page, signIn);
	}
	
	@Override
	public Page<Map<String,Object>> goJqGrid_DetailSignInType2(Page<Map<String,Object>> page, SignIn signIn){
		return signInDao.goJqGrid_DetailSignInType2(page, signIn);
	}
	
	@Override
	public List<Map<String,Object>> goJqGridSignInPicList(Page<Map<String,Object>> page, String no){
		return signInDao.goJqGridSignInPicList(page, no);
	}
	
	@Override
	public List<Map<String, Object>> findNoSendYunPic(){
		return signInDao.findNoSendYunPic();
	}
	
	@Override
	public List<Map<String, Object>> findNoSendYunWorkSignPic(){
		return signInDao.findNoSendYunWorkSignPic();
	}
	
	@Override
	public boolean existsFirstPass(String custCode, String prjItem){
		return signInDao.existsFirstPass(custCode, prjItem);
	}

	@Override
	public Page<Map<String, Object>> getPrjSignList(
			Page<Map<String, Object>> page, SignIn signIn) {
		return signInDao.getPrjSignList(page, signIn);
	}

	@Override
	public Result updateEndDate(String custCode, String prjItem1, String signCzy) {
		Long rows = signInDao.updateEndDate(custCode,prjItem1);
		if (rows < 1) {
            return new Result(Result.FAIL_CODE, "更新结束时间错误");
        }
		
		rows = signInDao.updatePrjProgEndDate(custCode,prjItem1, signCzy);
		if (rows < 1) {
            return new Result(Result.FAIL_CODE, "更新结束时间错误");
        }else {
            return new Result(Result.SUCCESS_CODE, "更新结束时间成功");            
        }
	}

	@Override
	public List<Map<String, Object>> getPrjProgList(String custCode) {
		return signInDao.getPrjProgList(custCode);
	}
	
	@Override
	public List<Map<String, Object>> getSignInPic(String no) {
		return signInDao.getSignInPic(no);
	}
	
	@Override
	public Boolean existsDesignPicPrgChange(String custCode) {
		return signInDao.existsDesignPicPrgChange(custCode);
	}

	@Override
	public Long saveFirstAddConfirmApp(String custCode, String prjItem1,
			String isPass) {

		return signInDao.saveFirstAddConfirmApp(custCode, prjItem1, isPass);
	}
	
	
}
