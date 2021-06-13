package com.house.home.serviceImpl.project;

import java.util.Date;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.house.framework.bean.Result;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.framework.web.login.UserContext;
import com.house.home.client.service.evt.BaseItemChgEvt;
import com.house.home.dao.project.BaseItemChgDao;
import com.house.home.entity.project.BaseItemChg;
import com.house.home.service.project.BaseItemChgService;

@SuppressWarnings("serial")
@Service
public class BaseItemChgServiceImpl extends BaseServiceImpl implements BaseItemChgService {

	@Autowired
	private BaseItemChgDao baseItemChgDao;
	
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, BaseItemChg baseItemChg, UserContext userContext){
		return baseItemChgDao.findPageBySql(page, baseItemChg, userContext);
	}

	@Override
	public Page<Map<String,Object>> findPageByCustCode(Page<Map<String,Object>> page, String custCode){
		return baseItemChgDao.findPageByCustCode(page,custCode);
	}

	@Override
	public Result saveForProc(BaseItemChg baseItemChg, String xml) {
		return baseItemChgDao.saveForProc(baseItemChg, xml);
	}

	@Override
	public int getCountByCustCode(String custCode) {
		return baseItemChgDao.getCountByCustCode(custCode);
	}

	@Override
	public double getLastBaseDiscPer(String custCode) {
		return baseItemChgDao.getLastBaseDiscPer(custCode);
	}
	
	@Override
	public double getBaseFeeDirct(String custCode) {
		return baseItemChgDao.getBaseFeeDirct(custCode)+baseItemChgDao.getBaseItemChgDirct(custCode);
	}

	@Override
	public Page<Map<String, Object>> getBaseItemChgList(
			Page<Map<String, Object>> page, BaseItemChgEvt evt) {
		return baseItemChgDao.getBaseItemChgList(page,evt);
	}

	@Override
	public Map<String, Object> getDetailByNo(String no) {
		return baseItemChgDao.getDetailByNo(no);
	}

	@Override
	public void doPrjReceive(String no,String czybh) {
		baseItemChgDao.doPrjReceive(no,czybh);	
	}

	@Override
	public void doPrjReturn(String no,String czybh) {
		baseItemChgDao.doPrjReturn(no,czybh);	
	}

	@Override
	public Page<Map<String, Object>> findDetailPageBySql(
			Page<Map<String, Object>> page, BaseItemChg baseItemChg) {
		return baseItemChgDao.findDetailPageBySql(page, baseItemChg);
	}

	@Override
	public Page<Map<String, Object>> findBaseChgStakeholderPageBySql(
			Page<Map<String, Object>> page, BaseItemChg baseItemChg) {
		return baseItemChgDao.findBaseChgStakeholderPageByNo(page, baseItemChg);
	}

	@Override
	public Result doBaseItemChgTempProc(BaseItemChg baseItemChg) {
		return baseItemChgDao.doBaseItemChgTempProc(baseItemChg);
	}

	@Override
	public Result doRegenBaseItemChgFromPrePlanTemp(BaseItemChg baseItemChg) {
		return baseItemChgDao.doRegenBaseItemChgFromPrePlanTemp(baseItemChg);
	}

	@Override
	public boolean getExistsTemp(String custCode, String no) {
		return baseItemChgDao.getExistsTemp(custCode, no);
	}

    @Override
    public Result doDeptLeaderConfirm(String no, UserContext userContext) {
        
        if (StringUtils.isBlank(no)) {
            return new Result(Result.FAIL_CODE, "确认失败，基装增减单编号为空");
        }
        
        BaseItemChg baseItemChg = baseItemChgDao.get(BaseItemChg.class, no);
        
        if (baseItemChg == null) {
            return new Result(Result.FAIL_CODE, "确认失败，不存在的基装增减单");
        }
        
        if (StringUtils.isBlank(baseItemChg.getPrjStatus())
                && !baseItemChg.getPrjStatus().equals("2")) {
            
            return new Result(Result.FAIL_CODE, "确认失败，非提交状态不允许确认");
        }
        
        if (baseItemChg.getPrjStatus().equals("7")) {
            return new Result(Result.FAIL_CODE, "确认失败，已确认状态不允许再次确认");
        }
        
        if (StringUtils.isBlank(baseItemChg.getStatus())
                && !baseItemChg.getStatus().trim().equals("1")) {
            
            return new Result(Result.FAIL_CODE, "确认失败，非申请状态不允许确认");
        }
        
        baseItemChg.setPrjStatus("7");
        baseItemChg.setDeptLeaderConfirmCzy(userContext.getCzybh());
        baseItemChg.setDeptLeaderConfirmDate(new Date());
        
        baseItemChg.setActionLog("EDIT");
        baseItemChg.setLastUpdate(new Date());
        baseItemChg.setLastUpdatedBy(userContext.getCzybh());
        
        baseItemChgDao.update(baseItemChg);
        
        return new Result(Result.SUCCESS_CODE, "确认成功");
    }

	@Override
	public String getConfirmNotice(String no) {
		
		return baseItemChgDao.getConfirmNotice(no);
	}
    
    
	
}
