package com.house.home.serviceImpl.project;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.client.service.evt.PrjConfirmAppEvt;
import com.house.home.dao.basic.SignInDao;
import com.house.home.dao.project.PrjConfirmAppDao;
import com.house.home.dao.project.PrjProgConfirmDao;
import com.house.home.entity.basic.PersonMessage;
import com.house.home.entity.basic.PrjItem1;
import com.house.home.entity.project.PrjConfirmApp;
import com.house.home.service.project.PrjConfirmAppService;

@SuppressWarnings("serial")
@Service
public class PrjConfirmAppServiceImpl extends BaseServiceImpl implements PrjConfirmAppService {
	
	@Autowired
	private PrjConfirmAppDao prjConfirmAppDao;
	@Autowired
	private PrjProgConfirmDao prjProgConfirmDao;
	@Autowired
	private SignInDao signInDao;
	
	@Override
	public Page<Map<String,Object>> getPrjConfirmAppList (Page<Map<String,Object>> page ,PrjConfirmAppEvt evt){
		return prjConfirmAppDao.getPrjConfirmAppList(page,evt);
	}
	
	@Override
	public String addPrjConfirmApp(PrjConfirmAppEvt evt){
/*		if("0".equals(evt.getFromInfoAdd()) && prjConfirmAppDao.judgeProgTemp(evt) && !prjConfirmAppDao.existPrjConfirmApp(evt.getCustCode(),evt.getPrjItem(),"0,1,2,3")){
			return "该楼盘是第一次申请验收<br/>必须通过消息中心-施工提醒中的记录进行申请!";
		}*/
		PrjItem1 prjItem1 = this.get(PrjItem1.class, evt.getPrjItem());
		if(prjItem1 != null && "1".equals(prjItem1.getIsFirstPass()) && !signInDao.existsFirstPass(evt.getCustCode(), evt.getPrjItem())){
			return prjItem1.getDescr()+"节点需要初检通过后才允许申请验收";
		}
		
		if(prjConfirmAppDao.existPrjConfirmApp(evt.getCustCode(), evt.getPrjItem(), "0,1")){
			return "1";
		}
		if(prjConfirmAppDao.checkPrjProg(evt.getCustCode(), evt.getPrjItem())){
			return "2";
		}
		
		Map<String,Object> map = prjProgConfirmDao.checkPrjConfirmPassProg(evt.getCustCode(), evt.getPrjItem());
		if(map != null){
			if(map.get("NameChi")!=null){
				StringBuilder stringBuilder = new StringBuilder();
				stringBuilder.append(map.get("NameChi"));
				return "当前施工节点已被 '"+stringBuilder+"' 验收,无法进行添加";
			}
		}else{
			if(!"1".equals(prjItem1.getIsConfirm())){
				return "该楼盘进度里无对应施工节点,无法进行添加";
			}
		}
		PrjConfirmApp prjConfirmApp = new PrjConfirmApp();
		prjConfirmApp.setCustCode(evt.getCustCode());
		prjConfirmApp.setPrjItem(evt.getPrjItem());
		prjConfirmApp.setStatus("1");
		prjConfirmApp.setAppDate(new Date());
		prjConfirmApp.setRemarks(evt.getRemarks());
		prjConfirmApp.setLastUpdate(new Date());
		prjConfirmApp.setLastUpdatedBy(evt.getProjectMan());
		prjConfirmApp.setActionLog("ADD");
		prjConfirmApp.setExpired("F");
		prjConfirmAppDao.save(prjConfirmApp);
		if("1".equals(evt.getFromInfoAdd())){
			PersonMessage pm = prjConfirmAppDao.get(PersonMessage.class, evt.getInfoPk());
			pm.setRcvDate(new Date());
			pm.setRcvStatus("1");
			pm.setDealNo(prjConfirmApp.getPk()+"");
			prjConfirmAppDao.update(pm);
		}
		return "0";
	}
	
	@Override
	public Map<String,Object> getPrjConfirmAppByPk(Integer pk){
		return prjConfirmAppDao.getPrjConfirmAppByPk(pk);
	}
	
	@Override
	public String updatePrjConfirmApp(PrjConfirmAppEvt evt){
/*		if(prjConfirmAppDao.judgeProgTemp(evt) && !prjConfirmAppDao.existPrjConfirmApp(evt.getCustCode(),evt.getPrjItem(),"0,1,2,3")){
			return "该楼盘是第一次申请验收<br/>必须通过消息中心-施工提醒中的记录进行申请!";
		}*/
		PrjConfirmApp prjConfirmApp = prjConfirmAppDao.get(PrjConfirmApp.class, evt.getPk());		
		if("0".equals(prjConfirmApp.getStatus()) && "1".equals(evt.getStatus())){
			prjConfirmApp.setAppDate(new Date());
		}
		if(evt.getOpSign() != null && evt.getOpSign().equals("1")){
			if(!(prjConfirmApp.getCustCode().trim().equals(evt.getCustCode()) && prjConfirmApp.getPrjItem().trim().equals(evt.getPrjItem()))){
				PrjItem1 prjItem1 = this.get(PrjItem1.class, evt.getPrjItem());
				if(prjItem1 != null && "1".equals(prjItem1.getIsFirstPass()) && !signInDao.existsFirstPass(evt.getCustCode(), evt.getPrjItem())){
					return prjItem1.getDescr()+"节点需要初检通过后才允许申请验收";
				}
				if(prjConfirmAppDao.existPrjConfirmApp(evt.getCustCode(), evt.getPrjItem(), "0,1")){
					return "1";
				}
				if(prjConfirmAppDao.checkPrjProg(evt.getCustCode(), evt.getPrjItem())){
					return "2";
				}		
				Map<String,Object> map = prjProgConfirmDao.checkPrjConfirmPassProg(evt.getCustCode(), evt.getPrjItem());;
				if(map != null){
					if(map.get("NameChi")!=null){
						StringBuilder stringBuilder = new StringBuilder();
						stringBuilder.append(map.get("NameChi"));
						return "当前施工节点已被 '"+stringBuilder+"' 验收,无法进行添加";
					}
				}else{
					return 	"该楼盘进度里无对应施工节点,无法进行添加";
				}
			}
			prjConfirmApp.setCustCode(evt.getCustCode());
			prjConfirmApp.setPrjItem(evt.getPrjItem());
			prjConfirmApp.setRemarks(evt.getRemarks());
			if(StringUtils.isNotBlank(evt.getStatus())){
				prjConfirmApp.setStatus(evt.getStatus());
			}
		}else{
			prjConfirmApp.setStatus(evt.getStatus());
		}
		prjConfirmApp.setLastUpdate(new Date());
		prjConfirmApp.setLastUpdatedBy(evt.getProjectMan());
		prjConfirmApp.setActionLog("EDIT");
		prjConfirmAppDao.update(prjConfirmApp);
		return "0";
	}
	
	public Map<String,Object> getMaxPkCustWorker(String custCode,String prjItem){
		return prjConfirmAppDao.getMaxPkCustWorker(custCode, prjItem);
	}
	
	public List<Map<String,Object>> getConfirmStatus(String custCode,String workType12){
		return prjConfirmAppDao.getConfirmStatus(custCode,workType12);
	}
	
	public List<Map<String,Object>> getBefWorkerList(String custCode,String workerCode,String workType12){
		return prjConfirmAppDao.getBefWorkerList(custCode,workerCode,workType12);
	}
	
	public boolean existPrjConfirmApp(String custCode,String prjItem,String status){
		return prjConfirmAppDao.existPrjConfirmApp(custCode, prjItem, status);
	}
}
