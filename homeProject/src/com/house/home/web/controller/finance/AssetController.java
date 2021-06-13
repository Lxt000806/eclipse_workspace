package com.house.home.web.controller.finance;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.Null;

import com.house.framework.bean.Result;
import com.house.framework.bean.WebPage;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.web.controller.BaseController; 
import com.house.framework.web.token.FormToken;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.house.home.bean.basic.AssetType;
import com.house.home.entity.basic.AssetChg;
import com.house.home.entity.basic.Employee;
import com.house.home.entity.finance.Asset;  
import com.house.home.entity.workflow.Department;
import com.house.home.service.finance.AssetService;
import com.sun.mail.imap.protocol.Status;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/admin/asset")
public class AssetController extends BaseController{
	@Autowired
	private  AssetService assetService;
	
	/**
	 * 
	 * @param request
	 * @param response
	 * @param purchase
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goJqGrid")
	@ResponseBody
	public WebPage<Map<String, Object>> getJqGrid(HttpServletRequest request ,
			HttpServletResponse response ,Asset asset) throws Exception{
		Page<Map<String,Object>> page= this.newPageForJqGrid(request);
		assetService.findPageBySql(page, asset);
		return new WebPage<Map<String,Object>>(page);
	}
	
	@RequestMapping("/getChgDetailBySql")
	@ResponseBody
	public WebPage<Map<String, Object>> getChgDetailBySql(HttpServletRequest request ,
			HttpServletResponse response ,Asset asset) throws Exception{
		Page<Map<String,Object>> page= this.newPageForJqGrid(request);
		assetService.getChgDetailBySql(page, asset);
		return new WebPage<Map<String,Object>>(page);
	}

	@RequestMapping("/getDeprDetailBySql")
	@ResponseBody
	public WebPage<Map<String, Object>> getDeprDetailBySql(HttpServletRequest request ,
			HttpServletResponse response ,Asset asset) throws Exception{
		Page<Map<String,Object>> page= this.newPageForJqGrid(request);
		assetService.getDeprDetailBySql(page, asset);
		return new WebPage<Map<String,Object>>(page);
	}
	
	@RequestMapping("/getDeprGroupListBySql")
	@ResponseBody
	public WebPage<Map<String, Object>> getDeprGroupListBySql(HttpServletRequest request ,
			HttpServletResponse response ,Asset asset) throws Exception{
		Page<Map<String,Object>> page= this.newPageForJqGrid(request);
		assetService.getDeprGroupListBySql(page, asset);
		return new WebPage<Map<String,Object>>(page);
	}
	
	@RequestMapping("/getAssetDeprByCode")
	@ResponseBody
	public WebPage<Map<String, Object>> getAssetDeprByCode(HttpServletRequest request ,
			HttpServletResponse response ,Asset asset) throws Exception{
		Page<Map<String,Object>> page= this.newPageForJqGrid(request);
		assetService.getAssetDeprByCode(page, asset);
		return new WebPage<Map<String,Object>>(page);
	}
	
	@RequestMapping("/getAssetChgByCode")
	@ResponseBody
	public WebPage<Map<String, Object>> getAssetChgByCode(HttpServletRequest request ,
			HttpServletResponse response ,Asset asset) throws Exception{
		Page<Map<String,Object>> page= this.newPageForJqGrid(request);
		assetService.getAssetChgByCode(page, asset);
		return new WebPage<Map<String,Object>>(page);
	}
	
	/** 
	 * 跳转主页面 
	 * @param request
	 * @param response
	 * @param asset
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request ,
			HttpServletResponse response ,Asset asset) throws Exception{
		
		return new ModelAndView("admin/finance/asset/asset_list")
			.addObject("asset",asset);
	}
	
	/**
	 * 跳转新增
	 * @param request
	 * @param response
	 * @param asset
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goSave")
	public ModelAndView goSave(HttpServletRequest request,
			HttpServletResponse response, Asset asset) throws Exception{
		
		asset.setDateFrom(DateUtil.startOfTheMonth(new Date()));
		asset.setDateTo(DateUtil.endOfTheMonth(new Date()));
		
		return new ModelAndView("admin/finance/asset/asset_save")
			.addObject("asset", asset);
	}
	
	/**
	 * 跳转编辑
	 * @param request
	 * @param response
	 * @param asset
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goUpdate")
	public ModelAndView goUpdate(HttpServletRequest request,
			HttpServletResponse response, Asset asset) throws Exception{
		
		Department department = new Department();
		Employee employee = new Employee();
		AssetType assetType = new AssetType();
		
		asset = assetService.get(Asset.class, asset.getCode());
		if(StringUtils.isNotBlank(asset.getDepartment())){
			department = assetService.get(Department.class, asset.getDepartment());
			asset.setDeptDescr(department == null?"":department.getDesc1());
		}
		if(StringUtils.isNotBlank(asset.getUseMan())){
			employee = assetService.get(Employee.class, asset.getUseMan());
			asset.setUseManDescr(employee == null ? "":employee.getNameChi());
		}
		asset.setUom(asset.getUom().trim());
		if(StringUtils.isNotBlank(asset.getAssetType())){
			assetType = assetService.get(AssetType.class, asset.getAssetType());
			asset.setAssetTypeDescr(assetType == null ? "" : assetType.getDescr());
		}
		
		boolean deprFlag = assetService.getAssetDeprFlag(asset.getCode());
		
		asset.setDateFrom(DateUtil.startOfTheMonth(new Date()));
		asset.setDateTo(DateUtil.endOfTheMonth(new Date()));
		
		return new ModelAndView("admin/finance/asset/asset_update")
			.addObject("asset", asset)
			.addObject("deprFlag", deprFlag);
	}
	
	/**
	 * 跳转资产减少
	 * @param request
	 * @param response
	 * @param asset
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goDec")
	public ModelAndView goDec(HttpServletRequest request,
			HttpServletResponse response, Asset asset) throws Exception{
		
		asset = assetService.get(Asset.class, asset.getCode());
		
		return new ModelAndView("admin/finance/asset/asset_dec")
			.addObject("asset", asset);
	}
	
	/**
	 * 原值调整
	 * @param request
	 * @param response
	 * @param asset
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goOriginalValueChg")
	public ModelAndView goOriginalValueChg(HttpServletRequest request,
			HttpServletResponse response, Asset asset) throws Exception{
		
		String chgType = asset.getChgType();
		asset = assetService.get(Asset.class, asset.getCode());
		asset.setChgType(chgType);
		
		return new ModelAndView("admin/finance/asset/asset_chg")
			.addObject("asset", asset);
	}
	
	/**
	 * 累计折旧调整
	 * @param request
	 * @param response
	 * @param asset
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goTotalDeprAmountChg")
	public ModelAndView goTotalDeprAmountChg(HttpServletRequest request,
			HttpServletResponse response, Asset asset) throws Exception{
		
		String chgType = asset.getChgType();
		asset = assetService.get(Asset.class, asset.getCode());
		asset.setChgType(chgType);
		
		return new ModelAndView("admin/finance/asset/asset_chg")
			.addObject("asset", asset);
	}
	
	/**
	 * 使用年限调整
	 * @param request
	 * @param response
	 * @param asset
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goUseYearChg")
	public ModelAndView goUseYearChg(HttpServletRequest request,
			HttpServletResponse response, Asset asset) throws Exception{
		
		String chgType = asset.getChgType();
		asset = assetService.get(Asset.class, asset.getCode());
		asset.setChgType(chgType);
		
		return new ModelAndView("admin/finance/asset/asset_chg")
			.addObject("asset", asset);
	}
	
	/**
	 * 折旧方法调整
	 * @param request
	 * @param response
	 * @param asset
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goDeprTypeChg")
	public ModelAndView goDeprTypeChg(HttpServletRequest request,
			HttpServletResponse response, Asset asset) throws Exception{
		
		String chgType = asset.getChgType();
		asset = assetService.get(Asset.class, asset.getCode());
		asset.setChgType(chgType);
		
		return new ModelAndView("admin/finance/asset/asset_chg")
			.addObject("asset", asset);
	}
	
	/**
	 * 部门转移
	 * @param request
	 * @param response
	 * @param asset
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goDepartmentChg")
	public ModelAndView goDepartmentChg(HttpServletRequest request,
			HttpServletResponse response, Asset asset) throws Exception{
		Department department = new Department();
		String chgType = asset.getChgType();
		asset = assetService.get(Asset.class, asset.getCode());
		asset.setChgType(chgType);
		if(StringUtils.isNotBlank(asset.getDepartment())){
			department = assetService.get(Department.class, asset.getDepartment());
			asset.setDeptDescr(department == null?"":department.getDesc1());
		}
		Employee employee = new Employee();
		
		if(StringUtils.isNotBlank(asset.getUseMan())){
			employee = assetService.get(Employee.class, asset.getUseMan());
			if(employee != null){
				asset.setUseManDescr(employee.getNameChi());
			}
		}
		
		return new ModelAndView("admin/finance/asset/asset_chg")
			.addObject("asset", asset);
	}
	
	/**
	 * 计提折旧
	 * @param request
	 * @param response
	 * @param asset
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("/goCalcDepr")
	public ModelAndView goCalcDepr(HttpServletRequest request,
			HttpServletResponse response, Asset asset) throws Exception{
		
		asset.setDateFrom(DateUtil.startOfTheMonth(new Date()));
		asset.setDateTo(DateUtil.endOfTheMonth(new Date()));
		return new ModelAndView("admin/finance/asset/asset_calcDepr")
			.addObject("asset", asset);
	}
	
	/**
	 * 计提折旧查询界面
	 * @param request
	 * @param response
	 * @param asset
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goDeprDetail")
	public ModelAndView goDeprDetail(HttpServletRequest request,
			HttpServletResponse response, Asset asset) throws Exception{
		
		asset.setPeriod(DateUtil.DateToString(new Date(), "yyyyMM"));
		asset.setDateFrom(new Date());
		return new ModelAndView("admin/finance/asset/asset_deprDetail")
			.addObject("asset", asset);
	}
	
	/**
	 * 查看页面
	 * @param request
	 * @param response
	 * @param asset
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goView")
	public ModelAndView goView(HttpServletRequest request,
			HttpServletResponse response, Asset asset) throws Exception{
		
		Department department = new Department();
		Employee employee = new Employee();
		AssetType assetType = new AssetType();
		Employee decCzy = new Employee();
		
		asset = assetService.get(Asset.class, asset.getCode());
		if(StringUtils.isNotBlank(asset.getDepartment())){
			department = assetService.get(Department.class, asset.getDepartment());
			asset.setDeptDescr(department == null?"":department.getDesc1());
		}
		if(StringUtils.isNotBlank(asset.getUseMan())){
			employee = assetService.get(Employee.class, asset.getUseMan());
			asset.setUseManDescr(employee == null ? "":employee.getNameChi());
		}
		asset.setUom(asset.getUom().trim());
		if(StringUtils.isNotBlank(asset.getAssetType())){
			assetType = assetService.get(AssetType.class, asset.getAssetType());
			asset.setAssetTypeDescr(assetType == null ? "" : assetType.getDescr());
		}
		
		if(StringUtils.isNotBlank(asset.getDecCZY())){
			decCzy = assetService.get(Employee.class, asset.getDecCZY());
			asset.setDecCzyDescr(decCzy == null ? "":decCzy.getNameChi());
		}
		
		return new ModelAndView("admin/finance/asset/asset_view")
			.addObject("asset", asset);
	}
	
	/**
	 * 资产变动查询
	 * @param request
	 * @param response
	 * @param asset
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goChgDetail")
	public ModelAndView goChgDetail(HttpServletRequest request,
			HttpServletResponse response, Asset asset) throws Exception{
		asset.setDateFrom(DateUtil.startOfTheMonth(new Date()));
		asset.setDateTo(DateUtil.endOfTheMonth(new Date()));
		return new ModelAndView("admin/finance/asset/asset_chgDetail")
			.addObject("asset", asset);
	}
	
	/**
	 * 折旧分配查询
	 * @param request
	 * @param response
	 * @param asset
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goDeprGroupList")
	public ModelAndView goDeprGroupList(HttpServletRequest request,
			HttpServletResponse response, Asset asset) throws Exception{
		
		asset.setDateFrom(new Date());
		
		return new ModelAndView("admin/finance/asset/asset_deprGroupList")
			.addObject("asset", asset);
	}
	
	@RequestMapping("/goPeriod")
	public ModelAndView goPeriod(HttpServletRequest request,
			HttpServletResponse response, Asset asset) throws Exception{
		
		asset.setDateFrom(DateUtil.startOfTheMonth(new Date()));
		return new ModelAndView("admin/finance/asset/asset_period")
			.addObject("asset", asset);
	}
	
	/**
	 * 批量变动
	 * @param request
	 * @param response
	 * @param asset
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goBatchChg")
	public ModelAndView goBatchChg(HttpServletRequest request,
			HttpServletResponse response, Asset asset) throws Exception{
		asset.setDateFrom(DateUtil.startOfTheMonth(new Date()));
		asset.setDateTo(DateUtil.endOfTheMonth(new Date()));
		return new ModelAndView("admin/finance/asset/asset_batchChg")
			.addObject("asset", asset);
	}
	
	@RequestMapping("/doSave")
	public void doSave(HttpServletRequest request,HttpServletResponse response, Asset asset){
		logger.debug("添加固定资产开始");
		try{
			String sufCode= assetService.getSufCode(asset.getAssetType());
			
			asset.setCode(asset.getRemCode().trim()+sufCode);
			asset.setCreateCZY(this.getUserContext(request).getCzybh());
			asset.setCreateDate(new Date());
			asset.setLastUpdate(new Date());
			asset.setLastUpdatedBy(this.getUserContext(request).getCzybh());
			asset.setExpired("F");
			asset.setActionLog("ADD");
			if("2".equals(asset.getStatus())){
				asset.setDecCZY(this.getUserContext(request).getCzybh());
				asset.setDecDate(new Date());
			}
			
			assetService.save(asset);
			
			ServletUtils.outPrintSuccess(request, response);
		} catch(Exception e){
			ServletUtils.outPrintFail(request, response, "新增固定资产失败");
		}
	}
	
	@RequestMapping("/doUpdate")
	public void doUpdate(HttpServletRequest request,HttpServletResponse response, Asset asset){
		logger.debug("编辑固定资产开始");
		try{

			Asset assetOld = new Asset();
			assetOld = assetService.get(Asset.class, asset.getCode());
			
			asset.setCreateCZY(assetOld.getCreateCZY());
			asset.setCreateDate(assetOld.getCreateDate());
			asset.setLastUpdate(new Date());
			asset.setLastUpdatedBy(this.getUserContext(request).getCzybh());
			asset.setExpired("F");
			asset.setActionLog("Edit");
			if("2".equals(asset.getStatus())){
				asset.setDecCZY(this.getUserContext(request).getCzybh());
				asset.setDecDate(new Date());
			}
			
			assetService.update(asset);
			
			ServletUtils.outPrintSuccess(request, response);
		} catch(Exception e){
			ServletUtils.outPrintFail(request, response, "编辑固定资产失败");
		}
	}
	
	@RequestMapping("/doDec")
	public void doDec(HttpServletRequest request,HttpServletResponse response, Asset asset){
		logger.debug("编辑固定资产开始");
		try{
			
			Asset oldAsset = new Asset();
			oldAsset = assetService.get(Asset.class, asset.getCode());
			
			oldAsset.setDecRemarks(asset.getDecRemarks());
			oldAsset.setDecType(asset.getDecType());
			oldAsset.setDecCZY(this.getUserContext(request).getCzybh());
			oldAsset.setDecDate(new Date());
			oldAsset.setLastUpdate(new Date());
			oldAsset.setLastUpdatedBy(this.getUserContext(request).getCzybh());
			oldAsset.setActionLog("EDIT");
			oldAsset.setStatus("2");
			assetService.update(oldAsset);
			
			ServletUtils.outPrintSuccess(request, response);
		} catch(Exception e){
			ServletUtils.outPrintFail(request, response, "编辑固定资产失败");
		}
	}
	
	@RequestMapping("/doDecReturn")
	public void doDecReturn(HttpServletRequest request,HttpServletResponse response, Asset asset){
		logger.debug("编辑固定资产开始");
		try{
			
			Asset oldAsset = new Asset();
			oldAsset = assetService.get(Asset.class, asset.getCode());
			
			if(!"2".equals(oldAsset.getStatus())){
				ServletUtils.outPrintFail(request, response, "撤销失败,该固定资产不是减少状态");
				return;
			}
			
			oldAsset.setDecRemarks(null);
			oldAsset.setDecType(null);
			oldAsset.setDecCZY(null);
			oldAsset.setDecDate(null);
			oldAsset.setLastUpdate(new Date());
			oldAsset.setLastUpdatedBy(this.getUserContext(request).getCzybh());
			oldAsset.setActionLog("EDIT");
			oldAsset.setStatus("1");
			assetService.update(oldAsset);
			
			ServletUtils.outPrintSuccess(request, response, "撤销减少成功");
		} catch(Exception e){
			ServletUtils.outPrintFail(request, response, "撤销减少失败");
		}
	}
	
	@RequestMapping("/doChg")
	public void doChg(HttpServletRequest request,HttpServletResponse response, AssetChg assetChg){
		logger.debug("编辑固定资产开始");
		try{
			Asset asset = new Asset();
			asset = assetService.get(Asset.class, assetChg.getAssetCode());
			asset.setLastUpdate(new Date());
			asset.setLastUpdatedBy(this.getUserContext(request).getCzybh());
			asset.setActionLog("EDIT");
			asset.setRemarks(assetChg.getAssetRemarks());
			if("5".equals(assetChg.getChgType())){
				asset.setUseMan(assetChg.getAftValue2());
			}
			assetChg.setLastUpdate(new Date());
			assetChg.setLastUpdatedBy(this.getUserContext(request).getCzybh());
			assetChg.setExpired("F");
			assetChg.setActionLog("ADD");
			assetChg.setDate(new Date());
			assetChg.setChgCZY(this.getUserContext(request).getCzybh());

			// 获取资产变动后数据
			getAssetChgData(assetChg,asset);
			
			if(asset.getOriginalValue() < 0){
				ServletUtils.outPrintFail(request, response, "编辑固定资产失败,原值不能小于0");
				return;
			}
			
			if(asset.getTotalDeprAmount() < 0){
				ServletUtils.outPrintFail(request, response, "编辑固定资产失败,累计折旧额不能小于0");
				return;
			}
			
			if(asset.getTotalDeprAmount() > asset.getOriginalValue()){
				ServletUtils.outPrintFail(request, response, "编辑固定资产失败,累计折旧不能大于原值");
				return;
			}
			
			if(asset.getUseYear()*12 < asset.getDeprMonth()){
				ServletUtils.outPrintFail(request, response, "编辑后使用年限不能小于已计提月份");
				return;
			}
			
			assetService.doChg(assetChg, asset);
			
			ServletUtils.outPrintSuccess(request, response);
		} catch(Exception e){
			ServletUtils.outPrintFail(request, response, "编辑固定资产失败");
		}
	}
	
	@RequestMapping("/doCalcDepr")
	public void doCalcDepr(HttpServletRequest request, HttpServletResponse response,String period){
		logger.debug("变动开始");
		
		Result result = this.assetService.doCalcDepr(period, this.getUserContext(request).getCzybh());
		if(result.isSuccess()){
			ServletUtils.outPrintSuccess(request, response,"生成数据成功");
		} else {
			ServletUtils.outPrintFail(request, response, result.getInfo());
		}

	}
	
	@RequestMapping("/doBatchChg")
	public void doBatchChg(HttpServletRequest request, HttpServletResponse response,AssetChg assetChg){
		logger.debug("批量修改部门开始");
		
		assetChg.setLastUpdate(new Date());
		assetChg.setLastUpdatedBy(this.getUserContext(request).getCzybh());
		assetChg.setExpired("F");
		assetChg.setActionLog("Edit");
		Result result = this.assetService.doBatchChg(assetChg);
		if(result.isSuccess()){
			ServletUtils.outPrint(request, response,true, "批量修改成功", null, true);
		} else {
			ServletUtils.outPrintFail(request, response, result.getInfo());
		}
	}
	
	@ResponseBody
	@RequestMapping("/getAssetTypeData")
	public Map<String, Object> getAssetTypeData(String assetTypeCode){
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		AssetType assetType = new AssetType();
		assetType = assetService.get(AssetType.class, assetTypeCode);

		map.put("deprType", assetType.getDeprType());
		map.put("descr", assetType.getDescr());
		
		return map;
	}
	
	public void getAssetChgData(AssetChg assetChg,final Asset asset){
		if("1".equals(assetChg.getChgType())){
			asset.setOriginalValue(Double.parseDouble(assetChg.getAftValue()));
			asset.setNetValue(asset.getOriginalValue() - asset.getTotalDeprAmount());
		}
		if("2".equals(assetChg.getChgType())){
			asset.setTotalDeprAmount(Double.parseDouble(assetChg.getAftValue()));
			asset.setNetValue(asset.getOriginalValue() - asset.getTotalDeprAmount());
			asset.setDeprAmountPerMonth(asset.getRemainDeprMonth() == 0.0 ? 0 : asset.getNetValue() / asset.getRemainDeprMonth());
		}
		if("3".equals(assetChg.getChgType())){
			asset.setUseYear(Integer.parseInt(assetChg.getAftValue()));
			asset.setRemainDeprMonth(asset.getUseYear() * 12 - asset.getDeprMonth());
		}
		if("4".equals(assetChg.getChgType())){
			if("0".equals(assetChg.getAftValue())){
				asset.setDeprAmountPerMonth(0.0);
			}
			if("1".equals(assetChg.getAftValue()) && !"1".equals(asset.getDeprType())){
				asset.setDeprAmountPerMonth(asset.getRemainDeprMonth() == 0.0 ? 0 : asset.getNetValue() / asset.getRemainDeprMonth());
			}
			asset.setDeprType(assetChg.getAftValue());
		}
		if("5".equals(assetChg.getChgType())){
			asset.setDepartment(assetChg.getAftValue());
			asset.setAddress(assetChg.getAftAddress());
		}
	}
	
	/**
	 * 导出主页面EXCEL
	 * @param request
	 * @param response
	 * @param asset
	 */
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request ,HttpServletResponse response,
			Asset asset){
		Page<Map<String, Object>>page= this.newPage(request);
		page.setPageSize(-1);
		assetService.findPageBySql(page, asset);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
				"固定资产表_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}
	
}
