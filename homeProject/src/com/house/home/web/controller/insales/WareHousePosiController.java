package com.house.home.web.controller.insales;

import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.house.framework.bean.WebPage;
import com.house.framework.commons.orm.Page;
import com.house.framework.commons.utils.DateUtil;
import com.house.framework.commons.utils.ServletUtils;
import com.house.framework.web.controller.BaseController;
import com.house.home.entity.insales.WareHousePosi;
import com.house.home.service.insales.WareHousePosiService;

@Controller
@RequestMapping("/admin/wareHousePosi")
public class WareHousePosiController extends BaseController{
	@Autowired
	private  WareHousePosiService wareHousePosiService;

	@RequestMapping("/goJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> getJqGrid(HttpServletRequest request,
			HttpServletResponse response, WareHousePosi wareHousePosi) throws Exception {
		// 传入操作员编号
		wareHousePosi.setCzybh(getUserContext(request).getCzybh());
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		wareHousePosiService.findPageByleftSql(page, wareHousePosi);
		return new WebPage<Map<String,Object>>(page);
	}
	
	/**
	 * @Description: TODO wareHousePosi_code分页查询
	 * @author	created by zb
	 * @date	2018-8-15--下午4:20:30
	 * @param request
	 * @param response
	 * @param wareHousePosi
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goCodeJqGrid")
	@ResponseBody
	public WebPage<Map<String,Object>> getCodeJqGrid(HttpServletRequest request,
			HttpServletResponse response, WareHousePosi wareHousePosi) throws Exception {
		Page<Map<String,Object>> page = this.newPageForJqGrid(request);
		wareHousePosiService.findCodePageBySql(page, wareHousePosi);
		return new WebPage<Map<String,Object>>(page);
	}
	
	@RequestMapping("/goList")
	public ModelAndView goList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView("admin/insales/wareHousePosi/wareHousePosi_list");
	}
	
	/**
	 * @Description: TODO 跳转增删改查窗口
	 * @author	created by zb
	 * @date	2018-8-9--下午4:57:47
	 * @param request
	 * @param response
	 * @param wareHousePosi
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goWin")
	public ModelAndView goSave(HttpServletRequest request,
			HttpServletResponse response, WareHousePosi wareHousePosi) throws Exception {
		if (wareHousePosi.getPk() != null) {// pk存在时，获取数据到M、V
			wareHousePosi.setCzybh(getUserContext(request).getCzybh());
			Page<Map<String,Object>> page = this.newPageForJqGrid(request);
			Map<String, Object> map = wareHousePosiService.findPageByleftSql(page, wareHousePosi).getResult().get(0);
			map.put("m_umState", wareHousePosi.getM_umState());
			return new ModelAndView("admin/insales/wareHousePosi/wareHousePosi_win")
				.addObject("wareHousePosi", map);
		}
		return new ModelAndView("admin/insales/wareHousePosi/wareHousePosi_win")
			.addObject("wareHousePosi", wareHousePosi);
	}
	
	/**
	 * @Description: TODO 检查desc1是否重复
	 * @author	created by zb
	 * @date	2018-8-9--下午4:48:59
	 * @param request
	 * @param response
	 * @param desc1
	 * @param pk
	 * @return
	 */
	@RequestMapping("/checkDesc1")
	@ResponseBody
	public JSONObject checkDesc1(HttpServletRequest request,HttpServletResponse response,
			String desc1, Integer pk){
		if(StringUtils.isEmpty(desc1)){
			return this.out("传入的code为空", false);
		}
		boolean a = this.wareHousePosiService.checkDesc1PK(desc1,pk);
		if(!a){
			return this.out("系统中已存在code="+desc1+"的信息", false);
		}
		return this.out(a, true);
		
	}
	
	@RequestMapping("/doSave")
	public void doSave(HttpServletRequest request,HttpServletResponse response,WareHousePosi wareHousePosi){
		logger.debug("新增保存");
		try {
			wareHousePosi.setLastUpdate(new Date());
			wareHousePosi.setLastUpdatedBy(this.getUserContext(request).getCzybh());
			wareHousePosi.setExpired("F");
			wareHousePosi.setActionLog("ADD");
			
			this.wareHousePosiService.save(wareHousePosi);
			ServletUtils.outPrintSuccess(request, response);
		}catch (Exception e) {
			ServletUtils.outPrintFail(request, response, "新增失败");
		}
	}
	
	@RequestMapping("/doUpdate")
	public void doUpdate(HttpServletRequest request,HttpServletResponse response,WareHousePosi wareHousePosi){
		logger.debug("编辑保存");
		try {
			WareHousePosi whp = this.wareHousePosiService.get(WareHousePosi.class, wareHousePosi.getPk());
			wareHousePosi.setLastUpdate(new Date());
			wareHousePosi.setLastUpdatedBy(this.getUserContext(request).getCzybh());
			wareHousePosi.setActionLog("Edit");
			if (whp==null) {
				this.wareHousePosiService.save(wareHousePosi);
			}else {
				this.wareHousePosiService.update(wareHousePosi);
			}
			ServletUtils.outPrintSuccess(request, response);
		}catch (Exception e) {
			ServletUtils.outPrintFail(request, response, "新增失败");
		}
	}
	
	@RequestMapping("/doExcel")
	public void doExcel(HttpServletRequest request, 
			HttpServletResponse response, WareHousePosi wareHousePosi){
		Page<Map<String,Object>> page = this.newPage(request);
		page.setPageSize(-1);
		wareHousePosi.setCzybh(getUserContext(request).getCzybh());
		wareHousePosiService.findPageByleftSql(page, wareHousePosi);
		getExcelList(request);
		ServletUtils.flushExcelOutputStream(request, response, page.getResult(),
			"库位管理_"+DateUtil.DateToString(new Date(),"yyyyMMdd"), columnList, titleList, sumList);
	}
	
	/**
	 * @Description: TODO 获取库位信息
	 * @author	created by zb
	 * @date	2018-8-30--下午1:56:59
	 * @param request
	 * @param response
	 * @param id
	 * @return
	 */
	@RequestMapping("/getWareHousePosi")
	@ResponseBody
	public JSONObject getWareHousePosi(HttpServletRequest request,HttpServletResponse response,Integer id){
		if(null == id){
			return this.out("传入的id为空", false);
		}
		WareHousePosi wareHousePosi = wareHousePosiService.get(WareHousePosi.class, id);
		if(wareHousePosi == null){
			return this.out("系统中不存在pk="+id+"的库位信息", false);
		}
		return this.out(wareHousePosi, true);
	}
	
	/**
	 * @Description: TODO 库位pk搜索
	 * @author	created by zb
	 * @date	2018-8-15--下午4:11:42
	 * @param request
	 * @param response
	 * @param wareHouse
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/goCode")
	public ModelAndView goCode(HttpServletRequest request,
			HttpServletResponse response,WareHousePosi wareHousePosi) throws Exception {

		return new ModelAndView("admin/insales/wareHousePosi/wareHousePosi_code")
			.addObject("wareHousePosi", wareHousePosi);
	}
}
