package com.house.home.serviceImpl.design;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.house.framework.bean.Result;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.DescrUtil;
import com.house.framework.commons.utils.NoteUtil;
import com.house.framework.commons.utils.Reflections;
import com.house.framework.web.login.UserContext;
import com.house.home.client.service.evt.ResrCustEvt;
import com.house.home.dao.basic.XtdmDao;
import com.house.home.dao.design.CustTagDao;
import com.house.home.dao.design.ResrCustDao;
import com.house.home.entity.basic.Department2;
import com.house.home.entity.basic.Employee;
import com.house.home.entity.basic.Xtdm;
import com.house.home.entity.basic.Xtcs;
import com.house.home.entity.design.CustCon;
import com.house.home.entity.design.ResrCust;
import com.house.home.entity.design.ResrCustExcelFailed;
import com.house.home.entity.design.ResrCustPool;
import com.house.home.service.design.ResrCustService;

@SuppressWarnings("serial")
@Service
public class ResrCustServiceImpl extends BaseServiceImpl implements ResrCustService{
	
	@Autowired
	private ResrCustDao resrCustDao;
	@Autowired
	private XtdmDao xtdmDao;
	@Autowired
	private CustTagDao custTagDao;
	
	@Override
	public Page<Map<String, Object>> findPageBySql(
			Page<Map<String, Object>> page, ResrCust resrCust) {
		if("0".equals(resrCust.getHaveCustTag())){
			resrCust.setCustTag("");
		}
		return resrCustDao.findPageBySql(page, resrCust);
	}
	
	@Override
	public Page<Map<String, Object>> findResrCustMapperPageBySql(
			Page<Map<String, Object>> page, ResrCust resrCust) {
		return resrCustDao.findResrCustMapperPageBySql(page, resrCust);
	}
	@Override
	public void doClientSave(ResrCustEvt evt) {
		ResrCust resrCust = new ResrCust();
		Employee employee = new Employee();
		if(StringUtils.isNotBlank(evt.getCrtCzy())){
			employee = this.get(Employee.class, evt.getCrtCzy());
			resrCust.setCrtCZYDept(employee.getDepartment2());
		}
		String resrCustCode = this.getSeqNo("tResrCust");
		resrCust.setCode(resrCustCode);
		resrCust.setStatus(evt.getStatus());
		resrCust.setDescr(evt.getDescr());
		if(StringUtils.isNotBlank(evt.getAddress())){
			resrCust.setAddress(evt.getAddress());
		}else{
			resrCust.setAddress("");
		}
		resrCust.setMobile1(evt.getMobile1());
		resrCust.setMobile2(evt.getMobile2());
		resrCust.setSource(evt.getSource());
		if(StringUtils.isNotBlank(evt.getBuilderCode())){
			resrCust.setBuilderCode(evt.getBuilderCode());
		}else{
			resrCust.setBuilderCode("");
		}
		resrCust.setBuilderNum(evt.getBuilderNum());
		resrCust.setLayout(evt.getLayout());
		resrCust.setGender(evt.getGender());
		resrCust.setEmail2(evt.getEmail2());
		resrCust.setConstructType(evt.getConstructType());
		resrCust.setNetChanel(evt.getNetChanel());
		resrCust.setRegionCode(evt.getRegionCode());
		resrCust.setCustResStat(evt.getCustResStat());
		resrCust.setCrtCzy(evt.getCrtCzy());
		resrCust.setCrtDate(new Date());
		if(StringUtils.isNotBlank(evt.getBusinessMan())){
			resrCust.setBusinessMan(evt.getBusinessMan());
		}else{
			resrCust.setBusinessMan("");
		}
		resrCust.setDispatchDate(new Date());
		resrCust.setCustKind(evt.getCustKind());
		resrCust.setRemark(evt.getRemark());
		resrCust.setExpired("F");
		resrCust.setActionLog("ADD");
		resrCust.setLastUpdate(new Date());
		resrCust.setLastUpdatedBy(evt.getCzybh());
		resrCust.setArea(evt.getArea());
		resrCust.setAddrProperty(evt.getAddrProperty());
		resrCust.setExtraOrderNo(evt.getExtraOrderNo());
		resrCust.setResrCustPoolNo(evt.getResrCustPoolNo());
		resrCust.setValidDispatchCount(0);
		resrCust.setNoValidCount(0);
		//????????????????????????????????????????????????????????????????????????????????????????????????
		if(StringUtils.isNotBlank(resrCust.getBusinessMan())) {
			Employee businessMan=resrCustDao.get(Employee.class,resrCust.getBusinessMan());
			if(StringUtils.isNotBlank(businessMan.getDepartment3())){
				Department2 department2=resrCustDao.get(Department2.class,businessMan.getDepartment2());
				resrCust.setDepartment(department2.getDepartment());
			}else{
				resrCust.setDepartment(businessMan.getDepartment());
			}
		}
		
		resrCust.setPublicResrLevel("0");
		
		this.save(resrCust);
		//doSaveCustCon("??????",evt.getCzybh(),"2",resrCustCode);
	}

	@Override
	public void doClientUpdate(ResrCustEvt evt,ResrCust oldResrCust) {
		ResrCust resrCust = new ResrCust();
		if(StringUtils.isNotBlank(evt.getCode())){
			resrCust = this.get(ResrCust.class, evt.getCode());
			resrCust.setDescr(evt.getDescr());
			if(StringUtils.isNotBlank(evt.getAddress())){
				resrCust.setAddress(evt.getAddress());
			}else{
				resrCust.setAddress("");
			}
			resrCust.setCustKind(evt.getCustKind());
			if(StringUtils.isNotBlank(evt.getBuilderCode())){
				resrCust.setBuilderCode(evt.getBuilderCode());
			}else{
				resrCust.setBuilderCode("");
			}
			if(StringUtils.isBlank(evt.getResrCustPoolNo())&&StringUtils.isBlank(resrCust.getResrCustPoolNo())){
				resrCust.setResrCustPoolNo("RCP0000001");
			}
			resrCust.setAddrProperty(evt.getAddrProperty());
			resrCust.setBuilderNum(evt.getBuilderNum());
			resrCust.setLayout(evt.getLayout());
			resrCust.setArea(evt.getArea());
			resrCust.setGender(evt.getGender());
			resrCust.setEmail2(evt.getEmail2());
			resrCust.setRegionCode(evt.getRegionCode());
			resrCust.setRemark(evt.getRemark());
			resrCust.setConstructType(evt.getConstructType());
			resrCust.setSource(evt.getSource());
			resrCust.setNetChanel(evt.getNetChanel());
			resrCust.setMobile1(evt.getMobile1());
			resrCust.setMobile2(evt.getMobile2());
			resrCust.setExtraOrderNo(evt.getExtraOrderNo());
			resrCust.setCustResStat(evt.getCustResStat());
			String custConRemarks="?????????????????????\n";//???????????????
			String ignoreArr[]={"actionLog","dispatchDate","lastUpdate","department2","czybh","crtDate",
								"lastUpdatedBy","expired","department","publicResrLevel"};//??????????????????

			Map<String, List<Object>> diffMap=Reflections.compareFields(oldResrCust, resrCust, ignoreArr);//?????????????????????????????????????????????????????????
			if(diffMap!=null){
				for(Map.Entry<String, List<Object>> entry:diffMap.entrySet()){//???????????????????????????map?????????????????????
					String descr=DescrUtil.getDescr(resrCust, entry.getKey());//????????????????????????????????????????????????
					String newValue=entry.getValue().get(1).toString();//???????????????
					String newNote=NoteUtil.getNoteByValue(resrCust,entry.getKey(), newValue); //??????????????????????????????????????????
					custConRemarks+=descr+" : "+newNote+"???\n";
				}
			}
			this.update(resrCust);
			doSaveCustCon(custConRemarks,evt.getCzybh(),"2",evt.getCode());
		}
	}
	
	@Override
	public void doClientUpdateCustResStat(ResrCustEvt evt) {
		ResrCust resrCust = new ResrCust();
		String state ="";
		if("1".equals(evt.getCustResStat())){
			state ="????????????";
		}
		if("2".equals(evt.getCustResStat())){
			state ="????????????";
		}
		if(StringUtils.isNotBlank(evt.getCode())){
			resrCust = this.get(ResrCust.class, evt.getCode());
			resrCust.setCustResStat(evt.getCustResStat());
			resrCust.setLastUpdate(new Date());
			resrCust.setLastUpdatedBy(evt.getCzybh());
			resrCust.setActionLog("EDIT");
			this.update(resrCust);
			doSaveCustCon("???????????????"+state,evt.getCzybh(),"2",evt.getCode());
		}
	}
	
	public void doSaveCustCon(String content,String lastUpdatedBy,String type,String code){
		CustCon custCon = new CustCon();
		custCon.setCustCode("");
		custCon.setResrCustCode(code);
		custCon.setType(type);
		custCon.setRemarks(content);
		custCon.setConMan(lastUpdatedBy);
		custCon.setConDate(new Date());
		custCon.setExpired("F");
		custCon.setActionLog("ADD");
		custCon.setLastUpdate(new Date());
		custCon.setLastUpdatedBy(lastUpdatedBy);
		this.save(custCon);
	}

	@Override
	public int getConRemMinLen() {
		return resrCustDao.getConRemMinLen();
	}
	
	public ResrCust getByAddress(String address) {
		return resrCustDao.getByAddress(address);
	}

	@Override
	public String getMapperCustCode(String resrCustCode) {
		return resrCustDao.getMapperCustCode(resrCustCode);
	}

	@Override
	public Page<Map<String, Object>> goConJqGrid(Page<Map<String, Object>> page, ResrCust resrCust) {
		return resrCustDao.goConJqGrid(page, resrCust);
	}

	@Override
	public Page<Map<String, Object>> goUpdateCrtJqGrid(
			Page<Map<String, Object>> page, ResrCust resrCust) {
		return resrCustDao.goUpdateCrtJqGrid(page, resrCust);
	}

	@Override
	public void doUpdateCrtCzy(ResrCust resrCust) {
		resrCustDao.doUpdateCrtCzy(resrCust);
	}

	@Override
	public Page<Map<String, Object>> goFailedExcelJqGrid(
			Page<Map<String, Object>> page,ResrCustExcelFailed resrCustExcelFailed) {
		return resrCustDao.goFailedExcelJqGrid(page, resrCustExcelFailed);
	}

	@Override
	public String hasCustCode(String code) {
		return resrCustDao.hasCustCode(code);
	}

	@Override
	public void doAddCustCode(ResrCust resrCust) {
		resrCustDao.doAddCustCode(resrCust);
	}

	@Override
	public Integer getResrCustNum(String code) {
		return resrCustDao.getResrCustNum(code);
	}

	@Override
	public Result doCustTeamGiveUp(ResrCust rc) {
		Integer failedNum=0;
		String codes[]=rc.getCodes().split(",");
		for(int i=0;i<codes.length;i++){
			ResrCust resrCust = get(ResrCust.class, codes[i]);
			if(StringUtils.isBlank(resrCust.getBusinessMan())){
				failedNum++;
				continue;
			}
			resrCust.setBusinessMan("");
			//resrCust.setDispatchDate(null);
			resrCust.setLastUpdate(new Date());
			resrCust.setLastUpdatedBy(rc.getCzybh());
			resrCust.setActionLog("Edit");
			
			//????????????????????????????????????????????????????????????????????????
			Xtcs xtcs=get(Xtcs.class,"PubRetLevel");
			if(!"0".equals(xtcs.getQz())){
				resrCust.setPublicResrLevel("1");
			}
			
			update(resrCust);
			
			Employee lastUpdatedBy=get(Employee.class,rc.getCzybh() );
			CustCon custCon=new CustCon();
			custCon.setRemarks("??????"+rc.getRemark()+"???????????????");
			custCon.setType("2");
			custCon.setConDate(new Date());
			custCon.setConMan(lastUpdatedBy.getNumber());
			custCon.setResrCustCode(resrCust.getCode());
			custCon.setExpired("F");
			custCon.setActionLog("ADD");
			custCon.setLastUpdate(new Date());
			custCon.setLastUpdatedBy(rc.getCzybh());
			custCon.setCustCode("");
			save(custCon);
		}
		if(failedNum>0){
			return new Result(Result.FAIL_CODE, failedNum+"?????????????????????????????????????????????????????????");
		}else{
			return new Result(Result.SUCCESS_CODE, "????????????");
		}
	}

	@Override
	public Result doCancelCust(ResrCust rc) {
		Integer successedNum=0;
		Integer failedNum=0;
		String codes[]=rc.getCodes().split(",");
		Date nowDate =  new Date();
		Xtdm xtdm = new Xtdm();
		Xtcs xtcs=get(Xtcs.class,"PubRetLevel");
		for(int i=0;i<codes.length;i++){
			ResrCust resrCust = get(ResrCust.class, codes[i]);
			resrCust.setCancelRsn(rc.getCancelRsn());
			resrCust.setCancelRemarks(rc.getCancelRemarks());
			resrCust.setCancelDate(nowDate);
			resrCust.setLastUpdate(nowDate);
			resrCust.setLastUpdatedBy(rc.getCzybh());
			resrCust.setActionLog("Edit");
			resrCust.setExpired(resrCust.getExpired());
			resrCust.setCustResStat("4");
			
			ResrCustPool resrCustPool = this.get(ResrCustPool.class, resrCust.getResrCustPoolNo());
			if("0".equals(resrCustPool.getDispatchRule())) {
				update(resrCust);
				CustCon custCon=new CustCon();
				
				if("5".equals(rc.getCancelRsn())){
					custCon.setRemarks("??????"+rc.getCancelRemarks()+"???????????????");
				}else{
					xtdm = xtdmDao.getByIdAndCbm("RESRCANRSN", rc.getCancelRsn());
					custCon.setRemarks("??????"+xtdm.getNote()+"???????????????");
				}
				
				custCon.setType("2");
				custCon.setConDate(new Date());
				custCon.setConMan(rc.getCzybh());
				custCon.setResrCustCode(resrCust.getCode());
				custCon.setExpired("F");
				custCon.setActionLog("ADD");
				custCon.setLastUpdate(new Date());
				custCon.setLastUpdatedBy(rc.getCzybh());
				custCon.setCustCode("");
				save(custCon);
				successedNum ++;
			}else {
				failedNum ++;
			}
			
		}
		if(successedNum>0 || failedNum > 0){
			String resultInfo = "???"+codes.length+"?????????????????????????????????"+successedNum+"???????????????";
			if(codes.length > successedNum) {
				resultInfo += ","+(codes.length-successedNum)+"?????????????????????????????????????????????????????????????????????";
			}
			return new Result(Result.SUCCESS_CODE, resultInfo);
		}else{
			return new Result(Result.FAIL_CODE, "??????????????????");
		}
	}
	
	@Override
	public Result doCustTeamChangeBusiness(ResrCust rc) {
		Integer successedNum=0;
		String codes[]=rc.getCodes().split(",");
		Date nowDate =  new Date();
		
		for(int i=0;i<codes.length;i++){
			ResrCust resrCust = get(ResrCust.class, codes[i]);
			Employee oldBusinessMan = get(Employee.class,resrCust.getBusinessMan());
			resrCust.setBusinessMan(rc.getBusinessMan());
			resrCust.setDispatchDate(rc.getDispatchDate());
			resrCust.setLastUpdate(nowDate);
			resrCust.setLastUpdatedBy(rc.getCzybh());
			resrCust.setActionLog("Edit");
			resrCust.setExpired(resrCust.getExpired());
			if("2".equals(resrCust.getCustResStat())){// ????????????????????????????????????????????? modify by xzy Date:2021???2???23???17:06:13
				resrCust.setCustResStat("0");
			} else {
				resrCust.setCustResStat("1");
			}
			
			//????????????????????????????????????????????????????????????????????????????????????????????????
			Employee newBusinessMan = get(Employee.class,resrCust.getBusinessMan());
			if(StringUtils.isNotBlank(newBusinessMan.getDepartment3())){
				Department2 department2 = get(Department2.class,newBusinessMan.getDepartment2());
				resrCust.setDepartment(department2.getDepartment());
			}else{
				resrCust.setDepartment(newBusinessMan.getDepartment());
			}
			resrCust.setPublicResrLevel("0");
			
			update(resrCust);
			
			CustCon custCon=new CustCon();
			if(oldBusinessMan!=null){
				custCon.setRemarks("??????"+rc.getRemark()+"?????????"+oldBusinessMan.getNameChi()+"????????????"+newBusinessMan.getNameChi());
			}else{
				custCon.setRemarks("????????????????????????"+rc.getRemark()+"???????????????"+newBusinessMan.getNameChi());
			}
			
			custCon.setType("2");
			custCon.setConDate(nowDate);
			custCon.setConMan(rc.getCzybh());
			custCon.setResrCustCode(resrCust.getCode());
			custCon.setExpired("F");
			custCon.setActionLog("ADD");
			custCon.setLastUpdate(nowDate);
			custCon.setLastUpdatedBy(rc.getCzybh());
			custCon.setCustCode("");
			save(custCon);
			successedNum++;
		}
		if(successedNum>0){
			return new Result(Result.SUCCESS_CODE, "???????????????"+successedNum+"???????????????");
		}else{
			return new Result(Result.FAIL_CODE, "??????????????????");
		}
	}

	@Override
	public Map<String, Object> getResrCustDetail(String code) {
		return resrCustDao.getResrCustDetail(code);
	}

	@Override
	public List<Map<String, Object>> getResrCustTagList(String code, String czybh) {
		return custTagDao.getCustTagMapper(code, czybh);
	}

	@Override
	public Result doShareCust(ResrCust resrCust) {
		return resrCustDao.doShareCust(resrCust);
	}
	
	@Override
	public List<Map<String, Object>> getCustNetCnlList() {
		return resrCustDao.findAllNetChannels();
	}

	@Override
	public List<Map<String, Object>> findSourceByAuthority(int type,String pCode, UserContext uc) {
		List<Map<String,Object>> resultList = Lists.newArrayList();
		Map<String,Object> param = new HashMap<String,Object>();
		if(type == 1){
			String itemRight="";
			for(String str:uc.getItemRight().trim().split(",")){
				itemRight+="'"+str+"',";			
			}
			param.put("pCode", itemRight.substring(0, itemRight.length()-1));
			resultList = this.resrCustDao.findCustSource(param);
		}else if(type == 2){//????????????2
			param.put("pCode", pCode);
			resultList = this.resrCustDao.findCustCnl(param);
		}
		return resultList;
	}
	
	@Override
	public List<Map<String, Object>> getCustNetCnlListBySource(String source) {
		return resrCustDao.findNetChannelsBySource(source);
	}
	
	@Override
	public List<Map<String, Object>> getResrCustPoolList(ResrCustEvt evt) {
		return resrCustDao.getResrCustPoolList(evt);
	}
	
	@Override
	public String getDefaultResrCustPoolNo(ResrCustEvt evt) {
		return resrCustDao.getDefaultResrCustPoolNo(evt);
	}

	@Override
	public String getDefPoolNoByCzybm(String czybh) {

		return resrCustDao.getDefPoolNoByCzybm(czybh);
	}

	@Override
	public boolean getCanReceiveCust(String codes, String czybh) {

		return resrCustDao.getCanReceiveCust(codes, czybh);
	}
	
	
	
}
