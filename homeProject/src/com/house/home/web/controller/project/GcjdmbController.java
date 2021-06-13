package com.house.home.web.controller.project;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.house.framework.commons.conf.CommonConstant;
import com.house.framework.commons.orm.Page;
import com.house.framework.bean.Result;
import com.house.framework.bean.WebPage;
import com.house.framework.commons.utils.BeanConvertUtil;
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.commons.utils.XmlConverUtil;
import com.house.framework.entity.Role;
import com.house.framework.web.controller.BaseController;
import com.house.home.entity.basic.Employee;
import com.house.home.entity.basic.Roll;
import com.house.home.entity.project.PrjProgTemp;
import com.house.home.entity.project.ProgTempAlarm;
import com.house.home.entity.project.ProgTempDt;
import com.house.home.service.project.GcjdmbService;

@Controller
@RequestMapping("/admin/gcjdmb")
public class GcjdmbController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(GcjdmbController.class);

	@Autowired
	private GcjdmbService gcjdmbService;
	
	//-----表格查询相关

	/**
	 * 主页表格
	 * @param request
	 * @param response
	 * @param prjProgTemp
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goJqGrid(HttpServletRequest request, HttpServletResponse response, PrjProgTemp prjProgTemp) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		gcjdmbService.goJqGrid(page, prjProgTemp);
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * 进度模板表格
	 * @param request
	 * @param response
	 * @param no
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goJqGridProgTempDt")
	@ResponseBody
	public WebPage<Map<String,Object>> goJqGridProgTempDt(HttpServletRequest request, 
			HttpServletResponse response, String no,String prjItems, String custCode) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		gcjdmbService.goJqGridProgTempDt(page, no,prjItems, custCode);
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * 提醒事项表格
	 * @param request
	 * @param response
	 * @param no
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goJqGridProgTempAlarm")
	@ResponseBody
	public WebPage<Map<String,Object>> goJqGridProgTempAlarm(HttpServletRequest request, HttpServletResponse response, String no) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		gcjdmbService.goJqGridProgTempAlarm(page, no);
		return new WebPage<Map<String,Object>>(page);
	}
	//-----页面跳转相关

	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.debug("跳转到工程进度模板首页");
		return new ModelAndView("admin/project/gcjdmb/gcjdmb_list");
	}

	@RequestMapping("/goSave")
	public ModelAndView goSave(HttpServletRequest request, HttpServletResponse response, PrjProgTemp prjProgTemp) throws Exception {
		logger.debug("跳转到工程进度模板新增页");
		prjProgTemp.setNo("保存自动生成");
		prjProgTemp.setM_umState("A");
		prjProgTemp.setExpired("T");
		prjProgTemp.setNowPk(0);
		prjProgTemp.setNowPkTipEvent(0);
		return new ModelAndView("admin/project/gcjdmb/gcjdmb_view").addObject("data", prjProgTemp);
	}

	@RequestMapping("/goUpdate")
	public ModelAndView goUpdate(HttpServletRequest request, HttpServletResponse response, PrjProgTemp prjProgTemp) throws Exception {
		logger.debug("跳转到工程进度模板编辑页");
		PrjProgTemp ppt = null;
		if(prjProgTemp != null && StringUtils.isNotBlank(prjProgTemp.getNo())){
			ppt = gcjdmbService.get(PrjProgTemp.class, prjProgTemp.getNo());
			ppt.setM_umState("E");
		}
		return new ModelAndView("admin/project/gcjdmb/gcjdmb_view").addObject("data", ppt);
	}

	@RequestMapping("/goView")
	public ModelAndView goView(HttpServletRequest request, HttpServletResponse response, PrjProgTemp prjProgTemp) throws Exception {
		logger.debug("跳转到工程进度模板详情页");
		PrjProgTemp ppt = null;
		if(prjProgTemp != null && StringUtils.isNotBlank(prjProgTemp.getNo())){
			ppt = gcjdmbService.get(PrjProgTemp.class, prjProgTemp.getNo());
			ppt.setM_umState("V");
		}
		return new ModelAndView("admin/project/gcjdmb/gcjdmb_view").addObject("data", ppt);
	}

	@RequestMapping("/goSave_tempDetail")
	public ModelAndView goSave_tempDetail(HttpServletRequest request, HttpServletResponse response, ProgTempDt progTempDt) throws Exception {
		logger.debug("跳转到模板明细新增页");
		progTempDt.setM_umState("A");
		progTempDt.setLastUpdatedBy(getUserContext(request).getCzybh());
		progTempDt.setExpired("F");
		return new ModelAndView("admin/project/gcjdmb/gcjdmb_tempDetailView").addObject("data", progTempDt);
	}
	
	@RequestMapping("/goFastSave_tempDetail")
	public ModelAndView goFastSave_tempDetail(HttpServletRequest request, HttpServletResponse response, ProgTempDt progTempDt) throws Exception {
		logger.debug("跳转到模板明细快速新增页");
		progTempDt.setM_umState("P");
		progTempDt.setLastUpdatedBy(getUserContext(request).getCzybh());
		progTempDt.setExpired("F");
		return new ModelAndView("admin/project/gcjdmb/gcjdmb_tempDetailView").addObject("data", progTempDt);
	}
	
	@RequestMapping("/goCopy_tempDetail")
	public ModelAndView goCopy_tempDetail(HttpServletRequest request, HttpServletResponse response, ProgTempDt progTempDt) throws Exception {
		logger.debug("跳转到模板明细复制页");
		progTempDt.setM_umState("C");
		progTempDt.setLastUpdatedBy(getUserContext(request).getCzybh());
		progTempDt.setExpired("F");
		return new ModelAndView("admin/project/gcjdmb/gcjdmb_tempDetailView").addObject("data", progTempDt);
	}
	
	@RequestMapping("/goUpdate_tempDetail")
	public ModelAndView goUpdate_tempDetail(HttpServletRequest request, HttpServletResponse response, ProgTempDt progTempDt) throws Exception {
		logger.debug("跳转到模板明细编辑页");
		ProgTempDt ptd = null;
		if("A".equals(progTempDt.getMm_umState())){
			ptd = progTempDt;
		}else{
			ptd = gcjdmbService.get(ProgTempDt.class, progTempDt.getPk());
		}
		ptd.setMm_umState(progTempDt.getMm_umState());
		ptd.setM_umState("M");
		ptd.setLastUpdatedBy(getUserContext(request).getCzybh());
		ptd.setExpired("F");
		ptd.setTmpType(progTempDt.getTmpType());
		return new ModelAndView("admin/project/gcjdmb/gcjdmb_tempDetailView").addObject("data", ptd);
	}
	
	@RequestMapping("/goView_tempDetail")
	public ModelAndView goView_tempDetail(HttpServletRequest request, HttpServletResponse response, ProgTempDt progTempDt) throws Exception {
		logger.debug("跳转到模板明细详情页");
		ProgTempDt ptd = null;
		if("A".equals(progTempDt.getMm_umState()) || progTempDt.getPk() == null){
			ptd = progTempDt;
		}else{
			ptd = gcjdmbService.get(ProgTempDt.class, progTempDt.getPk());
		}
		ptd.setM_umState("V");
		ptd.setTmpType(progTempDt.getTmpType());
		return new ModelAndView("admin/project/gcjdmb/gcjdmb_tempDetailView").addObject("data", ptd);
	}


	@RequestMapping("/goSave_tipEvent")
	public ModelAndView goSave_tipEvent(HttpServletRequest request, HttpServletResponse response, ProgTempAlarm progTempAlarm) throws Exception {
		logger.debug("跳转到提醒事项新增页");
		progTempAlarm.setM_umState("A");
		progTempAlarm.setLastUpdatedBy(getUserContext(request).getCzybh());
		progTempAlarm.setExpired("F");
		progTempAlarm.setRemindCzyType("0");
		return new ModelAndView("admin/project/gcjdmb/gcjdmb_tipEventView").addObject("data", progTempAlarm);
	}
	
	@RequestMapping("/goFastSave_tipEvent")
	public ModelAndView goFastSave_tipEvent(HttpServletRequest request, HttpServletResponse response, ProgTempAlarm progTempAlarm) throws Exception {
		logger.debug("跳转到提醒事项快速新增页");
		progTempAlarm.setM_umState("P");
		progTempAlarm.setLastUpdatedBy(getUserContext(request).getCzybh());
		progTempAlarm.setExpired("F");
		progTempAlarm.setRemindCzyType("0");
		return new ModelAndView("admin/project/gcjdmb/gcjdmb_tipEventView").addObject("data", progTempAlarm);
	}
	
	@RequestMapping("/goCopy_tipEvent")
	public ModelAndView goCopy_tipEvent(HttpServletRequest request, HttpServletResponse response, ProgTempAlarm progTempAlarm) throws Exception {
		logger.debug("跳转到提醒事项复制页");
		progTempAlarm.setM_umState("C");
		progTempAlarm.setLastUpdatedBy(getUserContext(request).getCzybh());
		progTempAlarm.setExpired("F");
		if(StringUtils.isNotBlank(progTempAlarm.getWorkType1())){
			progTempAlarm.setWorkType1(progTempAlarm.getWorkType1().trim());
		}
		if(StringUtils.isNotBlank(progTempAlarm.getWorkType12())){
			progTempAlarm.setWorkType12(progTempAlarm.getWorkType12().trim());
		}
		return new ModelAndView("admin/project/gcjdmb/gcjdmb_tipEventView").addObject("data", progTempAlarm);
	}
	
	@RequestMapping("/goUpdate_tipEvent")
	public ModelAndView goUpdate_tipEvent(HttpServletRequest request, HttpServletResponse response, ProgTempAlarm progTempAlarm) throws Exception {
		logger.debug("跳转到提醒事项编辑页");
		ProgTempAlarm pta = null;
		if("A".equals(progTempAlarm.getMm_umState())){
			pta = progTempAlarm;
		}else{
			pta = gcjdmbService.get(ProgTempAlarm.class, progTempAlarm.getPk());
		}
		pta.setM_umState("M");
		pta.setLastUpdatedBy(getUserContext(request).getCzybh());
		pta.setExpired("F");
		pta.setMm_umState(progTempAlarm.getMm_umState());
		pta.setPrjItems(progTempAlarm.getPrjItems());
		if(StringUtils.isNotBlank(pta.getWorkType1())){
			pta.setWorkType1(pta.getWorkType1().trim());
		}
		if(StringUtils.isNotBlank(pta.getWorkType12())){
			pta.setWorkType12(pta.getWorkType12().trim());
		}
		if(StringUtils.isNotBlank(pta.getRole())){
			Roll roll = gcjdmbService.get(Roll.class, pta.getRole());
			if(roll != null){
				pta.setRoleDescr(roll.getDescr());
			}
		}
		if(StringUtils.isNotBlank(pta.getCzybh())){
			Employee employee = gcjdmbService.get(Employee.class, pta.getCzybh());
			if(employee != null){
				pta.setCzyDescr(employee.getNameChi());
			}
		}
		return new ModelAndView("admin/project/gcjdmb/gcjdmb_tipEventView").addObject("data", pta);
	}
	
	@RequestMapping("/goView_tipEvent")
	public ModelAndView goView_tipEvent(HttpServletRequest request, HttpServletResponse response, ProgTempAlarm progTempAlarm) throws Exception {
		logger.debug("跳转到提醒事项详情页");
		ProgTempAlarm pta = null;
		if("A".equals(progTempAlarm.getMm_umState()) || progTempAlarm.getPk() == null){
			pta = progTempAlarm;
		}else{
			pta = gcjdmbService.get(ProgTempAlarm.class, progTempAlarm.getPk());
		}
		pta.setM_umState("V");
		if(StringUtils.isNotBlank(pta.getWorkType1())){
			pta.setWorkType1(pta.getWorkType1().trim());
		}
		if(StringUtils.isNotBlank(pta.getWorkType12())){
			pta.setWorkType12(pta.getWorkType12().trim());
		}
		if(StringUtils.isNotBlank(pta.getRole())){
			Roll roll = gcjdmbService.get(Roll.class, pta.getRole());
			if(roll != null){
				pta.setRoleDescr(roll.getDescr());
			}
		}
		if(StringUtils.isNotBlank(pta.getCzybh())){
			Employee employee = gcjdmbService.get(Employee.class, pta.getCzybh());
			if(employee != null){
				pta.setCzyDescr(employee.getNameChi());
			}
		}
		return new ModelAndView("admin/project/gcjdmb/gcjdmb_tipEventView").addObject("data", pta);
	}
	//------操作相关
	/**
	 * 主页导出Excel
	 * @param request
	 * @param response
	 * @param prjProgTemp
	 */
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request, HttpServletResponse response, PrjProgTemp prjProgTemp){
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		page.setPageSize(-1);
		gcjdmbService.goJqGrid(page, prjProgTemp);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"工程进度模板-"+DateUtil.DateToString(new Date(),"yyyyMMddhhmmss"), columnList, titleList, sumList);
	}
	/**
	 * 判断模板是否已存在该节点
	 * @param request
	 * @param response
	 * @param pk
	 * @param tempNo
	 * @param prjItem
	 * @return
	 */
	@RequestMapping("/isPrjItemExist")
	@ResponseBody
	public boolean isPrjItemExist(HttpServletRequest request, HttpServletResponse response, Integer pk, String tempNo, String prjItem){
		Map<String, Object> map = gcjdmbService.isPrjItemExist(pk, tempNo, prjItem);
		if(map != null){
			return true;
		}
		return false;
	}

	/**
	 * 保存进度模板明细
	 * @param request
	 * @param response
	 * @param progTempDt
	 */
	@RequestMapping("/doSaveProgTempDt")
	public void doSaveProgTempDt(HttpServletRequest request, HttpServletResponse response, ProgTempDt progTempDt){
		try{
			progTempDt.setLastUpdate(new Date());
			progTempDt.setLastUpdatedBy(getUserContext(request).getCzybh());
			progTempDt.setActionLog("ADD");
			progTempDt.setExpired("F");
			gcjdmbService.save(progTempDt);
			ServletUtils.outPrintSuccess(request, response, "保存成功");
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "保存出现异常");
			e.printStackTrace();
		}
	}

	/**
	 * 更新进度模板明细
	 * @param request
	 * @param response
	 * @param progTempDt
	 */
	@RequestMapping("/doUpdateProgTempDt")
	public void doUpdateProgTempDt(HttpServletRequest request, HttpServletResponse response, ProgTempDt progTempDt){
		try{
			ProgTempDt ptd = gcjdmbService.get(ProgTempDt.class, progTempDt.getPk());
			ptd.setPrjItem(progTempDt.getPrjItem());
			ptd.setPlanBegin(progTempDt.getPlanBegin());
			ptd.setPlanEnd(progTempDt.getPlanEnd());
			ptd.setLastUpdate(new Date());
			ptd.setLastUpdatedBy(getUserContext(request).getCzybh());
			ptd.setExpired(progTempDt.getExpired());
			ptd.setActionLog("EDIT");
			ptd.setSpaceDay(progTempDt.getSpaceDay());
			ptd.setBefPrjItem(progTempDt.getBefPrjItem());
			ptd.setType(progTempDt.getType());
			ptd.setBaseConDay(progTempDt.getBaseConDay());
			ptd.setBaseWork(progTempDt.getBaseWork());
			ptd.setDayWork(progTempDt.getDayWork());
			gcjdmbService.update(ptd);
			ServletUtils.outPrintSuccess(request, response, "保存成功");
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "保存出现异常");
			e.printStackTrace();
		}
	}
	/**
	 * 删除进度模板明细
	 * @param request
	 * @param response
	 * @param pk
	 * @param tempNo
	 * @param prjItem
	 */
	@RequestMapping("/doDelProgTempDt")
	public void doDelProgTempDt(HttpServletRequest request, HttpServletResponse response, Integer pk, String tempNo, String prjItem){
		try{
			ProgTempDt progTempDt = gcjdmbService.get(ProgTempDt.class, pk);
			gcjdmbService.delete(progTempDt);
			gcjdmbService.doUpdateDispSeq(progTempDt.getTempNo(),progTempDt.getDispSeq());
			
			//删除相应节点的提醒事项
			List<ProgTempAlarm> list = BeanConvertUtil.mapToBeanList(gcjdmbService.getTipEvents(tempNo, prjItem), ProgTempAlarm.class);
			for(int i = 0;i < list.size();i++){
				gcjdmbService.delete(list.get(i));
			}
			
			ServletUtils.outPrintSuccess(request, response, "删除成功");
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "删除出现异常");
			e.printStackTrace();
		}
	}
	
	/**
	 * 判断是否存在进度模板明细
	 * @param request
	 * @param response
	 * @param tempNo
	 * @param prjItem
	 * @return
	 */
	@RequestMapping("/isProgTempDtExist")
	@ResponseBody
	public boolean isProgTempDtExist(HttpServletRequest request, HttpServletResponse response, String tempNo, String prjItem){
		Map<String, Object> map = gcjdmbService.isPrjItemExist(null, tempNo, prjItem);
		if(map != null){
			return true;
		}
		return false;
	}

	/**
	 * 保存提醒事项
	 * @param request
	 * @param response
	 * @param progTempAlarm
	 */
	@RequestMapping("/doSaveProgTempAlarm")
	public void doSaveProgTempAlarm(HttpServletRequest request, HttpServletResponse response, ProgTempAlarm progTempAlarm){
		try{
			progTempAlarm.setLastUpdate(new Date());
			progTempAlarm.setLastUpdatedBy(getUserContext(request).getCzybh());
			progTempAlarm.setActionLog("ADD");
			progTempAlarm.setExpired("F");
			gcjdmbService.save(progTempAlarm);
			ServletUtils.outPrintSuccess(request, response, "保存成功");
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "保存出现异常");
			e.printStackTrace();
		}
	}

	/**
	 * 更新提醒事项
	 * @param request
	 * @param response
	 * @param progTempAlarm
	 */
	@RequestMapping("/doUpdateProgTempAlarm")
	public void doUpdateProgTempAlarm(HttpServletRequest request, HttpServletResponse response, ProgTempAlarm progTempAlarm){
		try{
			ProgTempAlarm pta = gcjdmbService.get(ProgTempAlarm.class, progTempAlarm.getPk());
			pta.setPrjItem(progTempAlarm.getPrjItem());
			pta.setDayType(progTempAlarm.getDayType());
			pta.setAddDay(progTempAlarm.getAddDay());
			pta.setMsgTextTemp(progTempAlarm.getMsgTextTemp());
			pta.setLastUpdatedBy(getUserContext(request).getCzybh());
			pta.setRole(progTempAlarm.getRole());
			pta.setType(progTempAlarm.getType());
			pta.setDealDay(progTempAlarm.getDealDay());	//补充缺失 by zb on 20200413
			pta.setCompleteDay(progTempAlarm.getCompleteDay());
			pta.setItemType1(progTempAlarm.getItemType1());
			pta.setItemType2(progTempAlarm.getItemType2());
			pta.setIsNeedReq(progTempAlarm.getIsNeedReq());
			pta.setMsgTextTemp2(progTempAlarm.getMsgTextTemp2());
			pta.setWorkType1(progTempAlarm.getWorkType1());
			pta.setWorkType12(progTempAlarm.getWorkType12());
			pta.setJobType(progTempAlarm.getJobType());
			pta.setTitle(progTempAlarm.getTitle());
			pta.setIsAutoJob(progTempAlarm.getIsAutoJob());
			pta.setActionLog("EDIT");
			pta.setExpired(progTempAlarm.getExpired());
			pta.setLastUpdate(new Date());
			pta.setCzybh(progTempAlarm.getCzybh());
			pta.setRemindCzyType(progTempAlarm.getRemindCzyType());
			pta.setOfferWorkType2(progTempAlarm.getOfferWorkType2());
			gcjdmbService.update(pta);
			ServletUtils.outPrintSuccess(request, response, "保存成功");
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "保存出现异常");
			e.printStackTrace();
		}
	}

	/**
	 * 删除提醒事项
	 * @param request
	 * @param response
	 * @param pk
	 */
	@RequestMapping("/doDelProgTempAlarm")
	public void doDelProgTempAlarm(HttpServletRequest request, HttpServletResponse response, Integer pk){
		try{
			ProgTempAlarm progTempAlarm = gcjdmbService.get(ProgTempAlarm.class, pk);
			gcjdmbService.delete(progTempAlarm);
			ServletUtils.outPrintSuccess(request, response, "删除成功");
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "删除出现异常");
			e.printStackTrace();
		}
	}

	/**
	 * 保存进度模板
	 * @param request
	 * @param response
	 * @param prjProgTemp
	 */
	@RequestMapping("/doSavePrjProgTemp")
	public void doSavePrjProgTemp(HttpServletRequest request, HttpServletResponse response, PrjProgTemp prjProgTemp){
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try{

			//新增时模板明细和提醒事项采用主从表存储方式
			if("A".equals(prjProgTemp.getM_umState())){
				String progTempAlarm = request.getParameter("progTempAlarm");
				String progTempDt = request.getParameter("progTempDt");
				prjProgTemp.setLastUpdatedBy(getUserContext(request).getCzybh());
				Result result = gcjdmbService.doSavePrjProgTempForProc(prjProgTemp, XmlConverUtil.jsonToXmlNoHead(progTempDt), XmlConverUtil.jsonToXmlNoHead(progTempAlarm));
				resultMap.put("isSaveOk", "1");
				resultMap.put("msg", result.getInfo());
				if(!result.isSuccess()){
					resultMap.put("isSaveOk", "0");
					ServletUtils.outPrintFail(request, response, resultMap);
					return;
				}
			}else if("E".equals(prjProgTemp.getM_umState())){
				//编辑时模板明细和提醒事项采用实时存储,因此只更新模板信息
				PrjProgTemp ppt = gcjdmbService.get(PrjProgTemp.class, prjProgTemp.getNo());
				ppt.setDescr(prjProgTemp.getDescr());
				ppt.setLastUpdate(new Date());
				ppt.setLastUpdatedBy(getUserContext(request).getCzybh());
				ppt.setExpired(prjProgTemp.getExpired());
				ppt.setActionLog("EDIT");
				ppt.setRemarks(prjProgTemp.getRemarks());
				ppt.setType(prjProgTemp.getType());
				ppt.setCustType(prjProgTemp.getCustType());
				gcjdmbService.update(ppt);
				resultMap.put("msg", "保存成功");
			}
			resultMap.put("isTipExit", "0");
			ServletUtils.outPrintSuccess(request, response, resultMap);
		}catch(Exception e){
			resultMap.put("isSaveOk", "0");
			resultMap.put("isTipExit", "0");
			ServletUtils.outPrintFail(request, response, resultMap);
			e.printStackTrace();
		}
	}
	
	@RequestMapping("/doUpdateDispSeq")
	public void doUpdateDispSeq(HttpServletRequest request, HttpServletResponse response, int pk1, int dispSeq2, int pk2, int dispSeq1){
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try{
			ProgTempDt progTempDt1 = gcjdmbService.get(ProgTempDt.class, pk1);
			progTempDt1.setDispSeq(dispSeq2);
			ProgTempDt progTempDt2 = gcjdmbService.get(ProgTempDt.class, pk2);
			progTempDt2.setDispSeq(dispSeq1);
			gcjdmbService.update(progTempDt1);
			gcjdmbService.update(progTempDt2);
			ServletUtils.outPrintSuccess(request, response, resultMap);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, resultMap);
			e.printStackTrace();
		}
	}
}
