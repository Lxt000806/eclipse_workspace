package com.house.home.web.controller.insales;

import java.util.Date;
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
import com.house.framework.commons.orm.Page;
import com.house.framework.bean.WebPage;
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.IdUtil;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.web.controller.BaseController;
import com.house.home.entity.basic.WorkType2;
import com.house.home.entity.insales.BaseItemType2;
import com.house.home.service.basic.WorkType1Service;
import com.house.home.service.basic.WorkType2Service;
import com.house.home.service.insales.BaseItemType2Service;

@Controller
@RequestMapping("/admin/baseItemType2")
public class BaseItemType2Controller extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(BaseItemType2Controller.class);

	@Autowired
	private BaseItemType2Service baseItemType2Service;
	@Autowired
	private WorkType1Service workType1Service;
	@Autowired
	private WorkType2Service workType2Service;

	/**
	 * 查询JqGrid表格数据
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> goJqGrid(HttpServletRequest request,
			HttpServletResponse response, BaseItemType2 baseItemType2) throws Exception {
		
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		baseItemType2Service.findPageBySql(page, baseItemType2);
		return new WebPage<Map<String,Object>>(page);
	}
	/**
	 * BaseItemType2列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/insales/baseItemType2/baseItemType2_list");
	}
	/**
	 * BaseItemType2查询code
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goCode")
	public ModelAndView goCode(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/insales/baseItemType2/baseItemType2_code");
	}
	/**
	 * 跳转到新增BaseItemType2页面
	 * @return
	 */
	@RequestMapping("/goSave")
	public ModelAndView goSave(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到新增BaseItemType2页面");
		BaseItemType2 baseItemType2 = null;
		if (StringUtils.isNotBlank(id)){
			baseItemType2 = baseItemType2Service.get(BaseItemType2.class, id);
			baseItemType2.setCode(null);
		}else{
			baseItemType2 = new BaseItemType2();
		}
		
		return new ModelAndView("admin/insales/baseItemType2/baseItemType2_save")
			.addObject("baseItemType2", baseItemType2);
	}
	/**
	 * 跳转到基装类型2复制页面
	 * */
	@RequestMapping("/goCopy")
	public ModelAndView goCopy(HttpServletRequest request,HttpServletResponse response,String id){
		BaseItemType2 baseItemType2 = null;
		if (StringUtils.isNotBlank(id)) {
			baseItemType2 = baseItemType2Service.get(BaseItemType2.class,id);
			if (baseItemType2!=null) {
				WorkType2 workType2=workType2Service.get(WorkType2.class, baseItemType2.getOfferWorkType2());
				WorkType2 workType3=workType2Service.get(WorkType2.class, baseItemType2.getMaterWorkType2());
				if(workType2!=null){
					baseItemType2.setOfferWorkType1(workType2.getWorkType1().trim());
				}
				if (workType3!=null) {
					baseItemType2.setMaterWorkType1(workType3.getWorkType1().trim());
				}
			}
			baseItemType2.setBaseItemType1(baseItemType2.getBaseItemType1().trim());
		}
		return new ModelAndView("admin/insales/baseItemType2/baseItemType2_copy").addObject("baseItemType2", baseItemType2);
	}
	/**
	 * 跳转到修改BaseItemType2页面
	 * @return
	 */
	@RequestMapping("/goUpdate")
	public ModelAndView goUpdate(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到修改BaseItemType2页面");
		BaseItemType2 baseItemType2 = null;
		if (StringUtils.isNotBlank(id)) {
			baseItemType2 = baseItemType2Service.get(BaseItemType2.class,id);
			if (baseItemType2!=null) {
				WorkType2 workType2=workType2Service.get(WorkType2.class, baseItemType2.getOfferWorkType2());
				WorkType2 workType3=workType2Service.get(WorkType2.class, baseItemType2.getMaterWorkType2());
				if(workType2!=null){
					baseItemType2.setOfferWorkType1(workType2.getWorkType1().trim());
				}
				if (workType3!=null) {
					baseItemType2.setMaterWorkType1(workType3.getWorkType1().trim());
				}
			}
			baseItemType2.setBaseItemType1(baseItemType2.getBaseItemType1().trim());
		}
		return new ModelAndView("admin/insales/baseItemType2/baseItemType2_update")
			.addObject("baseItemType2", baseItemType2);
	}
	/**
	 * 跳转到查看BaseItemType2页面
	 * @return
	 */
	@RequestMapping("/goDetail")
	public ModelAndView goDetail(HttpServletRequest request, HttpServletResponse response, 
			String id){
		logger.debug("跳转到查看BaseItemType2页面");
		BaseItemType2 baseItemType2 = null;
		if (StringUtils.isNotBlank(id)) {
			baseItemType2 = baseItemType2Service.get(BaseItemType2.class,id);
			if (baseItemType2!=null) {
				WorkType2 workType2=workType2Service.get(WorkType2.class, baseItemType2.getOfferWorkType2());
				WorkType2 workType3=workType2Service.get(WorkType2.class, baseItemType2.getMaterWorkType2());
				if(workType2!=null){
					baseItemType2.setOfferWorkType1(workType2.getWorkType1().trim());
				}
				if (workType3!=null) {
					baseItemType2.setMaterWorkType1(workType3.getWorkType1().trim());
				}
			}
			baseItemType2.setBaseItemType1(baseItemType2.getBaseItemType1().trim());
		}
		return new ModelAndView("admin/insales/baseItemType2/baseItemType2_detail")
				.addObject("baseItemType2", baseItemType2);
	}
	/**
	 * 添加BaseItemType2
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doSave")
	public void doSave(HttpServletRequest request, HttpServletResponse response, BaseItemType2 baseItemType2){
		logger.debug("添加BaseItemType2开始");
		try{
			if (valideBaseItemCode(baseItemType2.getCode())){
				ServletUtils.outPrintFail(request, response, "基装类型2编号["+baseItemType2.getCode()+"]重复");
				return;
			}
			baseItemType2.setLastUpdate(new Date());
			baseItemType2.setLastUpdatedBy(getUserContext(request).getCzybh());
			baseItemType2.setExpired("F");
			baseItemType2.setActionLog("ADD");
			this.baseItemType2Service.save(baseItemType2);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "添加BaseItemType2失败");
		}
	}
	/**
	 * 复制BaseItemType2
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doCopy")
	public void doCopy(HttpServletRequest request, HttpServletResponse response, BaseItemType2 baseItemType2){
		logger.debug("添加BaseItemType2开始");
		try{
			if (valideBaseItemCode(baseItemType2.getCode())==true){
				ServletUtils.outPrintFail(request, response, "基装类型2编号["+baseItemType2.getCode()+"]重复");
				return;
			}
			baseItemType2.setLastUpdate(new Date());
			baseItemType2.setLastUpdatedBy(getUserContext(request).getCzybh());
			this.baseItemType2Service.save(baseItemType2);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "添加BaseItemType2失败");
		}
	}
	
	/**
	 * 修改BaseItemType2
	 * @param request
	 * @param response
	 * @param role
	 */
	@RequestMapping("/doUpdate")
	public void doUpdate(HttpServletRequest request, HttpServletResponse response, BaseItemType2 baseItemType2){
		logger.debug("修改BaseItemType2开始");
		try{
			baseItemType2.setLastUpdate(new Date());
			baseItemType2.setActionLog("EDIT");
			baseItemType2.setLastUpdatedBy(getUserContext(request).getCzybh());
			this.baseItemType2Service.update(baseItemType2);
			ServletUtils.outPrintSuccess(request, response);
		}catch(Exception e){
			ServletUtils.outPrintFail(request, response, "修改BaseItemType2失败");
		}
	}
	
	/**
	 * 删除BaseItemType2
	 * @param request
	 * @param response
	 * @param roleId
	 */
	@RequestMapping("/doDelete")
	public void doDelete(HttpServletRequest request, HttpServletResponse response, String deleteIds){
		logger.debug("删除BaseItemType2开始");
		if(StringUtils.isBlank(deleteIds)){
			ServletUtils.outPrintFail(request, response, "BaseItemType2编号不能为空,删除失败");
			return;
		}
		List<String> deleteIdList = IdUtil.splitStringIds(deleteIds);
		for(String deleteId : deleteIdList){
			if(deleteId != null){
				BaseItemType2 baseItemType2 = baseItemType2Service.get(BaseItemType2.class, deleteId);
				if(baseItemType2 == null)
					continue;
				baseItemType2.setExpired("T");
				baseItemType2Service.update(baseItemType2);
			}
		}
		logger.debug("删除BaseItemType2 IDS={} 完成",deleteIdList);
		ServletUtils.outPrintSuccess(request, response,"删除成功");
	}

	/**
	 *BaseItemType2导出Excel
	 * @param request
	 * @param response
	 */
	/**
	 *BaseItemType1导出Excel
	 * @param request
	 * @param response
	 */
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request,HttpServletResponse response, BaseItemType2 baseItemType2) {
		Page<Map<String, Object>> page = this.newPage(request);
		page.setPageSize(-1);
		baseItemType2Service.findPageBySql(page,baseItemType2);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response,page.getResult(),
				"基装类型2_" + DateUtil.DateToString(new Date(), "yyyyMMddhhmmss"),
				columnList, titleList, sumList);
	}
	/**
	 * 验证编号是否重复
	 */
	public boolean valideBaseItemCode(String code){
		return baseItemType2Service.valideBaseItemType(code);
	}
}
